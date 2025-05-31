/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import Model.Account;
import Model.Category;
import Model.Product;
import Model.Productcategories;
import Model.Review;
import Model.Reviewimg;
import Model.Reviewreply;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "ReviewServlet", urlPatterns = {"/admin/Review"})
public class ReviewServlet extends HttpServlet {
    
    @PersistenceContext EntityManager em;
    @Resource UserTransaction utx;
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            String productIdStr = request.getParameter("productID");
            Integer productID = 0;
            
            try{
                productID = Integer.parseInt(productIdStr);
            }catch(Exception ex){
                response.sendRedirect("Product?categoryID=all");
            }
            
            List<Product> products = em.createNamedQuery("Product.findById").setParameter("id", productID).getResultList();
            Product product = null;
            
            if(!products.isEmpty()){
                product = products.get(0);
                product.setCategory(Category.getCategories(em, Productcategories.getByProductID(em, product.getId())));
            }
            if(product == null){
                response.sendRedirect("Product?categoryID=all");
            }
            
            List<Review> reviews = em.createNamedQuery("Review.findByProductid").setParameter("productid", productID).getResultList();
            Double rate = 0.0;
            if(!reviews.isEmpty()){                
                for(Review review : reviews){
                    rate+=review.getRate();
                    List<Reviewimg> reviewImgs = em.createNamedQuery("Reviewimg.findByReviewid").setParameter("reviewid", review.getId()).getResultList();
                    review.setReviewImg(reviewImgs);
                    List<Account> accounts = em.createNamedQuery("Account.findById").setParameter("id", review.getAccountid()).getResultList();
                    if(!accounts.isEmpty()){
                        review.setAccount(accounts.get(0));
                    }
                    List<Reviewreply> replies = em.createNamedQuery("Reviewreply.findByReviewid").setParameter("reviewid", review.getId()).getResultList();
                    if(!replies.isEmpty()){
                        for(Reviewreply reply : replies){
                            List<Account> accounts2 = em.createNamedQuery("Account.findById").setParameter("id", reply.getAccountid()).getResultList();
                            if(!accounts2.isEmpty()){
                                reply.setAccount(accounts2.get(0));
                            }
                        }
                    }
                    review.setReplies(replies);
                }
                rate /= reviews.size();
            }
            product.setRate(rate);
            request.setAttribute("product", product);
            request.setAttribute("reviews", reviews);
            
            RequestDispatcher rd = request.getRequestDispatcher("Review.jsp");
            rd.forward(request, response);
            
        }catch(Exception ex){
            response.sendRedirect("http://localhost:8080/JavaEcommerce/user/Home");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            String reviewIDStr = request.getParameter("reviewID");
            String productIDStr = request.getParameter("productID");
            String purpose = request.getParameter("purpose");
            
            if(reviewIDStr==null || purpose==null || productIDStr==null){
                response.sendRedirect("Product?categoryID=all");
            }

            Integer reviewID = 0;
            Integer productID = 0;
            
            try{
                reviewID = Integer.parseInt(reviewIDStr);
                productID = Integer.parseInt(productIDStr);
            }catch(Exception ex){
                response.sendRedirect("Product?categoryID=all");
            }
            
            if(purpose.equals("delete")){
                deleteReview(request,response, reviewID);
            }else if(purpose.equals("reply")){
                replyReview(request,response,reviewID);
            }
            response.sendRedirect("Review?productID="+productIDStr);
        }catch(Exception ex){
            response.sendRedirect("http://localhost:8080/JavaEcommerce/user/Home");
        }
    }

    private void deleteReview(HttpServletRequest request, HttpServletResponse response, Integer reviewID) 
            throws ServletException, IOException {
        try{
            
            List<Review> reviews = em.createNamedQuery("Review.findById").setParameter("id", reviewID).getResultList();
            if(!reviews.isEmpty()){
                for(Review review : reviews){
                    utx.begin();
                    Review temp = em.merge(review);
                    em.remove(temp);
                    utx.commit();
                }
                List<Reviewimg> reviewImgs = em.createNamedQuery("Reviewimg.findByReviewid").setParameter("reviewid", reviewID).getResultList();
                if(!reviewImgs.isEmpty()){
                    for(Reviewimg reviewImg : reviewImgs){
                        utx.begin();
                        Reviewimg temp = em.merge(reviewImg);
                        em.remove(reviewImg);
                        utx.commit();
                    }
                }
                List<Reviewreply> reviewReplies = em.createNamedQuery("Reviewreply.findByReviewid").setParameter("reviewid", reviewID).getResultList();
                if(!reviewReplies.isEmpty()){
                    for(Reviewreply reviewReply : reviewReplies){
                        utx.begin();
                        Reviewreply temp = em.merge(reviewReply);
                        em.remove(temp);
                        utx.commit();
                    }
                }
            }
        }catch(Exception ex){
            
        }
    }

    private void replyReview(HttpServletRequest request, HttpServletResponse response, Integer reviewID)        
            throws ServletException, IOException {
        try{
            String message = request.getParameter("message");
            Account loggedAcc = Account.getLoggedAccount(request, response);
            Reviewreply reply = new Reviewreply(message, loggedAcc.getId(), reviewID);
            utx.begin();
            em.persist(reply);
            utx.commit();
        }catch(Exception ex){
        
        }
    }

}
