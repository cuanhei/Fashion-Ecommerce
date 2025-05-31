/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import Model.Account;
import Model.Person;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;

/**
 *
 * @author LIM CUAN HEI
 */
public class StaffServlet extends HttpServlet {

    @PersistenceContext EntityManager em;
    @Resource UserTransaction utx;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            String username = request.getParameter("username");
            List<Account> staffs = new ArrayList<Account>();
            if(username == null || username.equals("")){
                 staffs = em.createNamedQuery("Account.findByRole", Account.class)
                                  .setParameter("role", 'S')
                                  .getResultList();
            }else{
                TypedQuery<Account> query = em.createQuery(
                    "SELECT a FROM Account a WHERE LOWER(a.username) LIKE LOWER(:username) AND a.role = :role", Account.class);
                query.setParameter("username", "%" + username + "%");
                query.setParameter("role", 'S');
                staffs = query.getResultList();
                
                request.setAttribute("searchedUsername", username);
            }
            
            if(!staffs.isEmpty()){
                for(Account staff : staffs){
                    staff.setPerson((Person)em.createNamedQuery("Person.findById").setParameter("id", staff.getPersonid()).getSingleResult());
                }
            }
            request.setAttribute("staffs", staffs);
            RequestDispatcher rd = request.getRequestDispatcher("Staff.jsp");
            rd.forward(request, response);
            
        }catch(Exception ex){
            response.sendRedirect("http://localhost:8080/JavaEcommerce/user/Home");
        }

    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            
            boolean valid = true;
            valid = (!Account.isUsernameExist(em, username));
            if(valid){
                
                Person person = new Person(name, email);
                utx.begin();
                em.persist(person);
                utx.commit();
                
                Account acc = new Account(username, password, 'S', true, person.getId());
                utx.begin();
                em.persist(acc);
                utx.commit();
                
                response.sendRedirect("Staff");
            }else{
                response.sendRedirect("Staff");
            }
        }catch(Exception ex){
            PrintWriter out = response.getWriter();
            out.print(ex.getMessage());
        }
    }

}
