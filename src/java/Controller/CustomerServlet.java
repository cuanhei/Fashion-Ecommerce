/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import Model.Account;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author LIM CUAN HEI
 */
public class CustomerServlet extends HttpServlet {

    @PersistenceContext EntityManager em;
     
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try{
            String username = request.getParameter("username");
            List<Character> roles = Arrays.asList('m', 'V');
            if(username == null || username.equals("")){
                
                
                List<Account> customerAccounts = em.createNamedQuery("Account.findByRoles", Account.class)
                                  .setParameter("roles", roles)
                                  .getResultList();
                request.setAttribute("customers", customerAccounts);
            }else{
                TypedQuery<Account> query = em.createQuery(
                    "SELECT a FROM Account a WHERE LOWER(a.username) LIKE LOWER(:username) AND a.role IN :roles", Account.class);
                query.setParameter("username", "%" + username + "%");
                query.setParameter("roles", roles);
                List<Account> customerAccounts = query.getResultList();
               
                request.setAttribute("customers", customerAccounts);
                request.setAttribute("searchedUsername", username);
            }

            RequestDispatcher rd = request.getRequestDispatcher("Customer.jsp");
            rd.forward(request, response);
            
        }catch(Exception ex){
            response.sendRedirect("http://localhost:8080/JavaEcommerce/user/Home");
        }
    }
}
