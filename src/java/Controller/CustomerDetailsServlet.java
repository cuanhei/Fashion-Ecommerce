/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import Model.Account;
import Model.Orderlist;
import Model.Orders;
import Model.Payment;
import Model.Person;
import Model.Product;
import Model.Productsize;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author LIM CUAN HEI
 */
public class CustomerDetailsServlet extends HttpServlet {

    @PersistenceContext EntityManager em;
        
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            String idStr = request.getParameter("ID");
            Integer ID = 0; 
            try{
                ID = Integer.parseInt(idStr);
            }catch(Exception ex){
                response.sendRedirect("Customer");
            }
            
            List<Account> customers = em.createNamedQuery("Account.findById").setParameter("id", ID).getResultList();
            if(customers.isEmpty()){
                response.sendRedirect("Customer");
            }
            Account customer = customers.get(0);
            request.setAttribute("customer", customer);
            
            Query personQuery = em.createNamedQuery("Person.findById");
            personQuery.setParameter("id", customer.getPersonid());
            Person person = (Person) personQuery.getSingleResult();
            customer.setPerson(person);
            
            List<Orders> orders = getOrders(customer.getId());
            request.setAttribute("orders", orders);
            
            RequestDispatcher rd = request.getRequestDispatcher("CustomerDetails.jsp");
            rd.forward(request, response);
            
        }catch(Exception ex){
            response.sendRedirect("http://localhost:8080/JavaEcommerce/user/Signout");
        }
    }
    
    private List<Orders> getOrders(Integer customerID){
        List<Orders> orders = em.createNamedQuery("Orders.findByAccountid").setParameter("accountid",customerID).getResultList();
        return orders;
    }
    
}
