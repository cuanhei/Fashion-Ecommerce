package Controller;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import Model.Orders;
import Model.Payment;
import Model.Product;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;

/**
 *
 * @author LIM CUAN HEI
 */
@WebServlet(urlPatterns = {"/admin/Dashboard"})
public class DashboardServlet extends HttpServlet {

    @PersistenceContext EntityManager em;
    @Resource UserTransaction utx;
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            String reportType = request.getParameter("reportType");
            String jpql = "SELECT ol.productid, SUM(ol.quantity) FROM Orderlist ol GROUP BY ol.productid";
            List<Object[]> results = em.createQuery(jpql, Object[].class).getResultList();
            List<Product> products = new ArrayList<Product>();
            for(Object[] result : results){
                List<Product> temp = em.createNamedQuery("Product.findById").setParameter("id", (Integer)result[0]).getResultList();
                if(!temp.isEmpty()){
                    Product prod = temp.get(0);
                    prod.setTotalSold((Long)result[1]);
                    products.add(prod);
                }
            }
            products.sort((p1, p2) -> Long.compare(p2.getTotalSold(), p1.getTotalSold()));
            
            List<Object[]>result = null;
            if(reportType.equals("Monthly")){
                result = getMonthReport();
            }else if(reportType.equals("Daily")){
                result = getDayReport();
            }else{
                reportType = "Yearly";
                result = getYearReport();
            }
            request.setAttribute("reportType", reportType);
            request.setAttribute("topSoldProducts", products);
            request.setAttribute("result", result);
            RequestDispatcher rd = request.getRequestDispatcher("Dashboard.jsp");
            rd.forward(request, response);
        }catch(Exception ex){
            response.sendRedirect("http://localhost:8080/JavaEcommerce/user/Signout");
        }
    }

  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    private List<Object[]> getYearReport(){
        List<Orders> orders = em.createNamedQuery("Orders.findAll").getResultList();
        for(Orders order : orders){
            order.setPayment((Payment)em.createNamedQuery("Payment.findById").setParameter("id", order.getPaymentid()).getSingleResult());
        }
        Map<Integer, Double> salesByYear = new TreeMap<>();

        for (Orders order : orders) {
            if (order.getOrderdate() != null && order.getPayment() != null) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(order.getOrderdate());
                int year = cal.get(Calendar.YEAR);

                double amount = order.getPayment().getAmount();

                salesByYear.put(year, salesByYear.getOrDefault(year, 0.0) + amount);
            }
        }
        List<Object[]> result = new ArrayList<>();
        for (Map.Entry<Integer, Double> entry : salesByYear.entrySet()) {
            result.add(new Object[] { entry.getKey(), entry.getValue() });
        }
        return result;
    }
    
    private List<Object[]> getMonthReport(){
        List<Orders> orders = em.createNamedQuery("Orders.findAll").getResultList();
        for(Orders order : orders){
            order.setPayment((Payment)em.createNamedQuery("Payment.findById").setParameter("id", order.getPaymentid()).getSingleResult());
        }
        Map<String, Double> salesByMonth = new TreeMap<>();

        for (Orders order : orders) {
            if (order.getOrderdate() != null && order.getPayment() != null) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(order.getOrderdate());
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH) + 1; // 0-based

                String key = year + "-" + String.format("%02d", month);
                double amount = order.getPayment().getAmount();

                salesByMonth.put(key, salesByMonth.getOrDefault(key, 0.0) + amount);
            }
        }

        List<Object[]> result = new ArrayList<>();
        for (Map.Entry<String, Double> entry : salesByMonth.entrySet()) {
            result.add(new Object[] { entry.getKey(), entry.getValue() });
        }
        return result;
    }
    
    private List<Object[]> getDayReport(){
        List<Orders> orders = em.createNamedQuery("Orders.findAll").getResultList();
        for(Orders order : orders){
            order.setPayment((Payment)em.createNamedQuery("Payment.findById").setParameter("id", order.getPaymentid()).getSingleResult());
        }
        Map<String, Double> salesByDay = new TreeMap<>();

        for (Orders order : orders) {
            if (order.getOrderdate() != null && order.getPayment() != null) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(order.getOrderdate());
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH) + 1;
                int day = cal.get(Calendar.DAY_OF_MONTH);

                String key = year + "-" + String.format("%02d", month) + "-" + String.format("%02d", day);
                double amount = order.getPayment().getAmount();

                salesByDay.put(key, salesByDay.getOrDefault(key, 0.0) + amount);
            }
        }

        List<Object[]> result = new ArrayList<>();
        for (Map.Entry<String, Double> entry : salesByDay.entrySet()) {
            result.add(new Object[] { entry.getKey(), entry.getValue() });
        }
        return result;
    }
    
}
