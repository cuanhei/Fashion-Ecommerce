/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import Model.Account;
import Model.Person;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author LIM CUAN HEI
 */
@WebServlet(name = "SignInServlet", urlPatterns = {"/user/SignIn"})
public class SignInServlet extends HttpServlet {
    
    @PersistenceContext EntityManager em;
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
      try{
            ServletContext context = request.getServletContext(); // or session.getServletContext();
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            boolean rememberMe = Boolean.parseBoolean(request.getParameter("rememberMe"));
            
            Query accQuery = em.createNamedQuery("Account.findByUsername");
            accQuery.setParameter("username", username);
            List<Account> accounts = accQuery.getResultList();
            HttpSession session = request.getSession();
            
            if(!accounts.isEmpty()){
                Account acc = accounts.get(0);
                if(password.equals(acc.getPassword())){
                    //Valid Password
                    Query personQuery = em.createNamedQuery("Person.findById");
                    personQuery.setParameter("id", acc.getPersonid());
                    Person person = (Person) personQuery.getSingleResult();
                    acc.setPerson(person);
                    
                    
                    if(acc.isBanned()){
                        Map<String, String> error = new HashMap<>();
                        error.put("username", username);
                        error.put("password",password);
                        error.put("banned", "<b>Account is BANNED</b>, please contact "+context.getInitParameter("companyEmail")+" for more information.");

                        session.setAttribute("error", error);
                        response.sendRedirect("/JavaEcommerce/user/SignIn.jsp");
                    }else{
                    
                        session.setAttribute("loggedAcc", acc);
                        if(acc.isManager()){
                            response.sendRedirect("/JavaEcommerce/admin/Dashboard?reportType=Yearly");
                        }else if(acc.isAdmin()){
                            response.sendRedirect("/JavaEcommerce/admin/Profile.jsp");
                        }else{
                            response.sendRedirect("/JavaEcommerce/user/Home");
                        }
                    }
                }
                else{
                    //Invalid Password
                    Map<String, String> error = new HashMap<>();
                    error.put("username", username);
                    error.put("password",password);
                    error.put("passwordError", "<b>Incorrect password</b>, please try again with another password.");
                    
                    session.setAttribute("error", error);
                    response.sendRedirect("/JavaEcommerce/user/SignIn.jsp");
                }
            }else{
                //Username not found
                Map<String, String> error = new HashMap<>();
                error.put("username", username);
                error.put("password",password);
                error.put("usernameError", "<b>Username not found</b>, please register an account if you don't have an account.");

                session.setAttribute("error", error);
                response.sendRedirect("/JavaEcommerce/user/SignIn.jsp");
            }
         }catch(Exception ex){
             PrintWriter our = response.getWriter();
             our.print(ex.getMessage());
//response.sendRedirect("http://localhost:8080/JavaEcommerce/user/Home");
         }
    }

  
}
