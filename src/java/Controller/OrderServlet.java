/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import Model.Account;
import Model.Orderlist;
import Model.Orders;
import Model.Payment;
import Model.Product;
import Model.Productsize;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
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
@WebServlet(name = "OrderServlet", urlPatterns = {"/admin/Order"})
public class OrderServlet extends HttpServlet {
    
    @PersistenceContext EntityManager em;
    @Resource UserTransaction utx;
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            String status = request.getParameter("status");
            String orderIdStr = request.getParameter("OrderID");
            
            List<Orders> orders = new ArrayList<Orders>();
            if(status != null){
                if(status.equals("all")){
                    orders = em.createNamedQuery("Orders.findAll").getResultList();
                }else{
                    orders = em.createNamedQuery("Orders.findByStatus").setParameter("status", status).getResultList();
                }
                request.setAttribute("status", status);
            }else if(orderIdStr != null && !orderIdStr.equals("")){
                Integer orderId = 0;
                try{
                    orderId = Integer.parseInt(orderIdStr);
                }catch(Exception ex){
                    orderId = 0;
                }
                orders = em.createNamedQuery("Orders.findById").setParameter("id", orderId).getResultList();
                request.setAttribute("searchedOrderID", orderIdStr);
                request.setAttribute("status", "all");
            }else{
                orders = em.createNamedQuery("Orders.findAll").getResultList();
                request.setAttribute("status", "all");
            }

            if(!orders.isEmpty()){
                for(Orders order : orders){
                    order.setOrderLists(em.createNamedQuery("Orderlist.findByOrderid").setParameter("orderid", order.getId()).getResultList());
                    order.setPayment((Payment)em.createNamedQuery("Payment.findById").setParameter("id", order.getPaymentid()).getSingleResult());
                    
                    List<Account> customers = em.createNamedQuery("Account.findById").setParameter("id", order.getAccountid()).getResultList();
                    if(!customers.isEmpty()){
                        order.setCustomer(customers.get(0));
                    }
                    
                    if(!order.getOrderLists().isEmpty()){
                        for(Orderlist row : order.getOrderLists()){
                            row.setSize((Productsize)em.createNamedQuery("Productsize.findById").setParameter("id", row.getSizeid()).getSingleResult());
                            List<Product> products = em.createNamedQuery("Product.findById").setParameter("id", row.getProductid()).getResultList();
                            if(!products.isEmpty()){
                                row.setProduct(products.get(0));
                            }
                        }
                    }
                }
            }
            
            request.setAttribute("orders", orders);
            RequestDispatcher rd = request.getRequestDispatcher("Order.jsp");
            rd.forward(request, response);
            
        }catch(Exception ex){
            response.sendRedirect("http://localhost:8080/JavaEcommerce/user/Home");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            String status = request.getParameter("status");
            String orderIdStr = request.getParameter("orderID");
            Integer orderId = 0;
            try{
                orderId = Integer.parseInt(orderIdStr);
            }catch(Exception ex){
                orderId = 0;
            }
            
            List<Orders> orders = em.createNamedQuery("Orders.findById").setParameter("id", orderId).getResultList();
            if(!orders.isEmpty()){
                Orders order = orders.get(0);
                order.setStatus(status);
                utx.begin();
                em.merge(order);
                utx.commit();
            }
            response.sendRedirect("Order?status=all");
        }catch(Exception ex){
            response.sendRedirect("http://localhost:8080/JavaEcommerce/user/Home");
        }
    }

}
