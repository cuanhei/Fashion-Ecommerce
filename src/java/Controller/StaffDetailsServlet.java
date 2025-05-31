/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import Model.Account;
import Model.Person;
import java.io.IOException;
import java.io.PrintWriter;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author LIM CUAN HEI
 */
@WebServlet(name = "StaffDetailsServlet", urlPatterns = {"/admin/StaffDetails"})
public class StaffDetailsServlet extends HttpServlet {

    @PersistenceContext EntityManager em;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try{
        String staffID = request.getParameter("staffID");
        
        if(staffID == null){ response.sendRedirect("Staff"); }
        
        Integer accountID = 0;
        try{
            accountID = Integer.parseInt(staffID);
        }catch(Exception ex){
            response.sendRedirect("Staff");
        }
        
        Query accQuery = em.createNamedQuery("Account.findById");
        accQuery.setParameter("id", accountID);
        Account staff = (Account) accQuery.getSingleResult();
        request.setAttribute("staff", staff);

        Query personQuery = em.createNamedQuery("Person.findById");
        personQuery.setParameter("id", staff.getPersonid());
        Person person = (Person) personQuery.getSingleResult();
        staff.setPerson(person);
        
        request.setAttribute("staff", staff);
        RequestDispatcher rd = request.getRequestDispatcher("StaffDetails.jsp");
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
