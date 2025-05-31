/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import Model.Account;
import Model.Cart;
import Model.Category;
import Model.Product;
import Model.Productcategories;
import Model.Productsize;
import Model.Review;
import Model.Reviewimg;
import Model.Reviewreply;
import Model.Stock;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
@WebServlet(name = "UserProductDetailsServlet", urlPatterns = {"/user/ProductDetails", "/user/AddToCart"})
public class UserProductDetailsServlet extends HttpServlet {

    @PersistenceContext EntityManager em;
    @Resource UserTransaction utx;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            getProductDetails(request,response);
            getReview(request,response);
            RequestDispatcher rd = request.getRequestDispatcher("ProductDetails.jsp");
            rd.forward(request, response);
            
        }catch(Exception ex){
            response.sendRedirect("http://localhost:8080/JavaEcommerce/user/Home");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            String productIdStr = request.getParameter("productID");
            String sizeIdStr = request.getParameter("sizeID");
            String quantityStr = request.getParameter("quantity");
            
            Integer productID = 0;
            Integer sizeID = 0;
            Integer quantity = 0;
            
            try{
                productID = Integer.parseInt(productIdStr);
                sizeID = Integer.parseInt(sizeIdStr);
                quantity = Integer.parseInt(quantityStr);
            }catch(Exception ex){
                response.sendRedirect("Shop?categoryID=all");
            }
            
            List<Stock> stocks = em.createNamedQuery("Stock.findByProductidSizeid").setParameter("sizeid", sizeID).setParameter("productid", productID).getResultList();
            Stock stock = null;
            if(!stocks.isEmpty()){
                stock = stocks.get(0);
            }else{response.sendRedirect("Shop?categoryID=all");}
            
           
            Account loggedAcc = Account.getLoggedAccount(request, response);

            if(stock.getQuantity() >= quantity){
                Cart.addToCart(em, utx, loggedAcc.getId(), productID, sizeID, quantity);
            }

            response.sendRedirect("Cart");
        }catch(Exception ex){
            response.sendRedirect("http://localhost:8080/JavaEcommerce/user/Home");
        }
    }
    
    private void getProductDetails(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String productID = request.getParameter("productID");
        Query productQuery = em.createNamedQuery("Product.findById");
        productQuery.setParameter("id", Integer.parseInt(productID));
        List<Product> products = productQuery.getResultList();
        Product product = null;

        if(!products.isEmpty()){
            product = products.get(0);
            product.setCategory(Category.getCategories(em, Productcategories.getByProductID(em, product.getId())));
        }else{ response.sendRedirect("Shop?categoryID=all"); }

        List<Category> categories = em.createNamedQuery("Category.findAll").getResultList();
        List<Productsize> sizes = em.createNamedQuery("Productsize.findAll").getResultList();
        List<Stock> stocks = em.createNamedQuery("Stock.findByProductid").setParameter("productid", product.getId()).getResultList();
        Map<Productsize,Integer>stockQuantities = new HashMap<>();
        int totalQty = 0;
        if(!stocks.isEmpty()){
            for(Productsize size : sizes){
                for(Stock stock : stocks){
                    if(stock.getSizeid() == size.getId()){
                        stockQuantities.put(size, stock.getQuantity());
                        totalQty+=stock.getQuantity();
                        break;
                    }
                }
            }
        }
        product.setTotalQty(totalQty);
        request.setAttribute("product", product);
        request.setAttribute("sizes", sizes);
        request.setAttribute("categories", categories);
        request.setAttribute("stockQuantities", stockQuantities);
    }

    private void getReview(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String productIdStr = request.getParameter("productID");
        Integer productID = Integer.parseInt(productIdStr);
        List<Review> reviews = em.createNamedQuery("Review.findByProductid").setParameter("productid", productID).getResultList();
        if(!reviews.isEmpty()){
            for(Review review : reviews){
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
        }
        request.setAttribute("reviews", reviews);
    }
}
