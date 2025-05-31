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
import java.util.Map;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;

/**
 *
 * @author LIM CUAN HEI
 */
@WebServlet(name = "SignUpServlet", urlPatterns = {"/user/SignUp"})
public class SignUpServlet extends HttpServlet {

    @PersistenceContext EntityManager em;
    @Resource UserTransaction utx;
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         try{
             
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String conPassword = request.getParameter("conPassword");
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String contact = request.getParameter("contact");
            HttpSession session = request.getSession();
            
            if(!password.equals(conPassword)){
                
                Map<String, String> error = new HashMap<>();
                error.put("username", username);
                error.put("password",password);
                error.put("conPassword",conPassword);
                error.put("name",name);
                error.put("email",email);
                error.put("contact",contact);
                error.put("conPasswordError", "<b>Confirm password not match</b>, please double confirm your password and confirm password.");

                session.setAttribute("error", error);
                response.sendRedirect("/JavaEcommerce/user/SignUp.jsp");
            }
            else if(Account.isUsernameExist(em, username)){
                
                Map<String, String> error = new HashMap<>();
                error.put("username", username);
                error.put("password",password);
                error.put("conPassword",conPassword);
                error.put("name",name);
                error.put("email",email);
                error.put("contact",contact);
                error.put("usernameError", "<b>Username already exists</b>, please try again with another username.");

                session.setAttribute("error", error);
                response.sendRedirect("/JavaEcommerce/user/SignUp.jsp");
                
            }else if(name==null || name.equals("")){
                
                Map<String, String> error = new HashMap<>();
                error.put("username", username);
                error.put("password",password);
                error.put("conPassword",conPassword);
                error.put("name",name);
                error.put("email",email);
                error.put("contact",contact);
                error.put("nameError", "<b>Name is empty</b>, please input your name.");

                session.setAttribute("error", error);
                response.sendRedirect("/JavaEcommerce/user/SignUp.jsp");
                
            }else if(email==null || email.equals("")){
                
                Map<String, String> error = new HashMap<>();
                error.put("username", username);
                error.put("password",password);
                error.put("conPassword",conPassword);
                error.put("name",name);
                error.put("email",email);
                error.put("contact",contact); 
                error.put("emailError", "<b>Email is empty</b>, please input your email.");

                session.setAttribute("error", error);
                response.sendRedirect("/JavaEcommerce/user/SignUp.jsp");
                
            }else if(contact==null || contact.equals("")){
                
                Map<String, String> error = new HashMap<>();
                error.put("username", username);
                error.put("password",password);
                error.put("conPassword",conPassword);
                error.put("name",name);
                error.put("email",email);
                error.put("contact",contact);
                error.put("contactError", "<b>Contact is empty</b>, please input your contact.");

                session.setAttribute("error", error);
                response.sendRedirect("/JavaEcommerce/user/SignUp.jsp");
                
            }else{
                
                Person person = new Person(name,email,contact);
                utx.begin();
                em.persist(person);
                utx.commit();
                
                Account acc = new Account(username, password, 'm', true, person.getId());
                utx.begin();
                em.persist(acc);
                utx.commit();

                session.setAttribute("successNoti", "Successfully Created A New Account.");
                response.sendRedirect("/JavaEcommerce/user/SignUp.jsp");
            }
            
         }catch(Exception ex){
             response.sendRedirect("http://localhost:8080/JavaEcommerce/user/Home");
         }

    }

}
