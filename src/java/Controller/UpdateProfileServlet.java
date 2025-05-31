/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import Model.Account;
import Model.Orders;
import Model.Person;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.transaction.UserTransaction;

/**
 *
 * @author LIM CUAN HEI
 */


@MultipartConfig
@WebServlet(urlPatterns = {"/user/UpdateProfile", "/admin/UpdateProfile", "/admin/UpdateCustomer", "/admin/UpdateStaff"})
public class UpdateProfileServlet extends HttpServlet {

    @PersistenceContext EntityManager em;
    @Resource UserTransaction utx;
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try{
            String customerIDStr = request.getParameter("customerID");
            String staffIDStr = request.getParameter("staffID");
            if(customerIDStr != null){
                //Update customer profile
                updateCustProfile(request, response, Integer.parseInt(customerIDStr));
            }else if (staffIDStr != null){
                //Update staff profile
                updateStaffProfile(request, response, staffIDStr);
            }else{
                updateOwnProfile(request, response);
            }
            
        }catch(Exception ex){
            response.sendRedirect("http://localhost:8080/JavaEcommerce/user/Home");
        }
    }
    
    private void updateOwnProfile(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            PrintWriter out = response.getWriter();
            
            Part imagePart = request.getPart("profilePicInput"); //Profile Image
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String address = request.getParameter("address");
            String ic = request.getParameter("ic");
            String contact = request.getParameter("contact");
            Character gender = request.getParameter("gender").charAt(0);
            
            //Validation
            boolean valid = true;
            //Do Validation
            if(valid){
                Account acc = Account.getLoggedAccount(request, response);
                Person person =(Person) acc.getPerson();

                if (imagePart != null && imagePart.getSize() != 0) {
                    String uploadPath = getServletContext().getRealPath("") +"/Img/Profile";
                    String uploadedPath = FileManager.uploadImage(uploadPath, imagePart);
                    acc.setImgpath("../Img/Profile/"+uploadedPath);
                }

                acc.setUsername(username);
                acc.setPassword(password);
                person.setName(name);
                person.setEmail(email);
                person.setAddress(address);
                person.setGender(gender);
                person.setIc(ic);
                person.setContact(contact);
                
                updateProfile(acc, person);
                if(acc.isAdmin()){
                    response.sendRedirect("/JavaEcommerce/admin/Profile.jsp");
                }
                else{
                    response.sendRedirect("/JavaEcommerce/user/Profile.jsp");
                }
            }
            
        }catch(Exception ex){
            response.sendRedirect("http://localhost:8080/JavaEcommerce/user/Home");
        }
    }
    
    private void updateCustProfile(HttpServletRequest request, HttpServletResponse response, Integer customerID)
            throws ServletException, IOException {
        try{
            Account acc =(Account) em.find(Account.class, customerID);
            Person person =(Person)  em.find(Person.class, acc.getPersonid());
            
            boolean isBan = (request.getParameter("ban")!=null) ;
            boolean isDelete = (request.getParameter("delete")!=null) ;
            
            if(isBan){
                ban(acc);
                updateProfile(acc, person);
                acc.setPerson(person);
            }else if(isDelete){
                delete(acc,person);
                response.sendRedirect("Customer");
            }else{
                Part imagePart = request.getPart("profilePicInput"); //Profile Image
                String username = request.getParameter("username");
                String password = request.getParameter("password");
                String name = request.getParameter("name");
                String email = request.getParameter("email");
                String address = request.getParameter("address");
                String ic = request.getParameter("ic");
                String contact = request.getParameter("contact");
                Character gender = request.getParameter("gender").charAt(0);

                //Validation
                boolean valid = true;
                //Do Validation
                if(valid){

                    if (imagePart != null && imagePart.getSize() != 0) {
                        String uploadPath = getServletContext().getRealPath("") +"/Img/Profile";
                        String uploadedPath = FileManager.uploadImage(uploadPath, imagePart);
                        acc.setImgpath("../Img/Profile/"+uploadedPath);
                    }

                    acc.setUsername(username);
                    acc.setPassword(password);
                    person.setName(name);
                    person.setEmail(email);
                    person.setAddress(address);
                    person.setGender(gender);
                    person.setIc(ic);
                    person.setContact(contact);

                    updateProfile(acc, person);
                    acc.setPerson(person);
                }
            }
            List<Orders> orders = em.createNamedQuery("Orders.findByAccountid").setParameter("accountid",acc.getId()).getResultList();
            request.setAttribute("orders", orders);
            request.setAttribute("customer", acc);

            RequestDispatcher rd = request.getRequestDispatcher("CustomerDetails.jsp");
            rd.forward(request, response);
            
        }catch(Exception ex){
            PrintWriter out = response.getWriter();
            out.print(ex.getMessage());
        }
    }
    
    private void updateProfile(Account acc, Person person) throws Exception{
       
        utx.begin();
        em.merge(acc);
        utx.commit();
        
        utx.begin();
        em.merge(person);
        utx.commit();

    }

    private void updateStaffProfile(HttpServletRequest request, HttpServletResponse response, String accountIDStr)
            throws ServletException, IOException {
        try{
            Integer accountID = Integer.parseInt(accountIDStr);
            Account acc =(Account) em.find(Account.class, accountID);
            Person person =(Person)  em.find(Person.class, acc.getPersonid());
            
            boolean isBan = (request.getParameter("ban")!=null) ;
            boolean isDelete = (request.getParameter("delete")!=null) ;
            
            if(isBan){
                ban(acc);
                updateProfile(acc, person);
            }else if(isDelete){
                delete(acc,person);
                response.sendRedirect("Staff");
            }else{
                Part imagePart = request.getPart("profilePicInput"); //Profile Image
                String username = request.getParameter("username");
                String password = request.getParameter("password");
                String name = request.getParameter("name");
                String email = request.getParameter("email");
                String address = request.getParameter("address");
                String ic = request.getParameter("ic");
                String contact = request.getParameter("contact");
                Character gender = request.getParameter("gender").charAt(0);

                //Validation
                boolean valid = true;
                //Do Validation
                if(valid){

                    if (imagePart != null && imagePart.getSize() != 0) {
                        String uploadPath = getServletContext().getRealPath("") +"/Img/Profile";
                        String uploadedPath = FileManager.uploadImage(uploadPath, imagePart);
                        acc.setImgpath("../Img/Profile/"+uploadedPath);
                    }

                    acc.setUsername(username);
                    acc.setPassword(password);
                    person.setName(name);
                    person.setEmail(email);
                    person.setAddress(address);
                    person.setGender(gender);
                    person.setIc(ic);
                    person.setContact(contact);  
                }
                updateProfile(acc, person);
                acc.setPerson(person);
            }

            response.sendRedirect("StaffDetails?staffID="+accountIDStr);
            
        }catch(Exception ex){
            PrintWriter out = response.getWriter();
            out.print(ex.getMessage());
        }
    }
    
    private void ban(Account acc){
        acc.setStatus(!acc.getStatus());
    }
    
    private void delete(Account acc, Person person) throws Exception {
        utx.begin();
        Person managedPerson = em.merge(person); 
        em.remove(managedPerson);
        utx.commit();

        utx.begin();
        Account managedAcc = em.merge(acc); 
        em.remove(managedAcc);
        utx.commit();
    }

}
