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
@WebServlet(name = "UserOrderServlet", urlPatterns = {"/user/Order"})
public class UserOrderServlet extends HttpServlet {

    
    @PersistenceContext EntityManager em;
    @Resource UserTransaction utx;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            Account acc = Account.getLoggedAccount(request, response);
            List<Orders> orders = em.createNamedQuery("Orders.findByAccountid").setParameter("accountid",acc.getId()).getResultList();
            if(!orders.isEmpty()){
                for(Orders order : orders){
                    order.setOrderLists(em.createNamedQuery("Orderlist.findByOrderid").setParameter("orderid", order.getId()).getResultList());
                    order.setPayment((Payment)em.createNamedQuery("Payment.findById").setParameter("id", order.getPaymentid()).getSingleResult());
                    
                    if(!order.getOrderLists().isEmpty()){
                        for(Orderlist row : order.getOrderLists()){
                            row.setSize((Productsize)em.createNamedQuery("Productsize.findById").setParameter("id", row.getSizeid()).getSingleResult());
                            row.setProduct((Product)em.createNamedQuery("Product.findById").setParameter("id", row.getProductid()).getSingleResult());
                        }
                    }
                }
            }
            
            String orderIdStr = request.getParameter("orderID");
            if(orderIdStr != null && !orders.isEmpty()){
                int orderId = 0;
                try{
                    orderId = Integer.parseInt(orderIdStr);
                }catch(Exception ex){
                    orderId = 0;
                }
                for(Orders order : orders){
                    if(order.getId() == orderId){
                        orders = new ArrayList<Orders>();
                        orders.add(order);
                        break;
                    }
                }
                request.setAttribute("searchedOrderID", orderIdStr);
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

    }
}
