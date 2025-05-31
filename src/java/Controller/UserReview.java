/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import Model.Account;
import Model.Orderlist;
import Model.Review;
import Model.Reviewimg;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.transaction.UserTransaction;

/**
 *
 * @author LIM CUAN HEI
 */

@MultipartConfig
@WebServlet(name = "UserReview", urlPatterns = {"/user/CreateReview"})
public class UserReview extends HttpServlet {

    @PersistenceContext EntityManager em;
    @Resource UserTransaction utx;
   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            String orderlistIdStr = request.getParameter("orderlistID");
            String productIdStr = request.getParameter("productID");
            String rateStr = request.getParameter("rate");
            String details = request.getParameter("details");
            Collection<Part> parts = request.getParts();
            Account acc = Account.getLoggedAccount(request, response);
            Integer rate = 0;
            Integer productID = 0;
            Integer orderListID = 0;
            try{
                rate = Integer.parseInt(rateStr);
                productID = Integer.parseInt(productIdStr);
                orderListID = Integer.parseInt(orderlistIdStr);
            }catch(Exception ex){
                response.sendRedirect("http://localhost:8080/JavaEcommerce/user/Home");
            }
            
            Review review = new Review(rate,details,acc.getId(),productID);
            List<Orderlist> orderlists = em.createNamedQuery("Orderlist.findById").setParameter("id", orderListID).getResultList();
            if(!orderlists.isEmpty()){
                
                utx.begin();
                em.persist(review);
                utx.commit();
                
                Orderlist orderlist = orderlists.get(0);
                orderlist.setReviewid(review.getId());
                utx.begin();
                em.merge(orderlist);
                utx.commit();
                
                if(!parts.isEmpty()){
                    for (Part part : parts) {
                        if ("images[]".equals(part.getName()) && part.getSize() > 0) {
                            String uploadPath = getServletContext().getRealPath("") +"/Img/Review";
                            String uploadedPath = FileManager.uploadImage(uploadPath, part);
                            Reviewimg reviewImg = new Reviewimg(review.getId(),"../Img/Review/"+uploadedPath);
                            utx.begin();
                            em.persist(reviewImg);
                            utx.commit();
                        }
                    }
                }
            }
            
            response.sendRedirect("ProductDetails?productID="+productID);
        }catch(Exception ex){
            response.sendRedirect("http://localhost:8080/JavaEcommerce/user/Home");
        }
    }

}
