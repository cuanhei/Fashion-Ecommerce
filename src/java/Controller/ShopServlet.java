/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import Model.Category;
import Model.Product;
import Model.Productcategories;
import Model.Stock;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
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
import javax.transaction.UserTransaction;

/**
 *
 * @author LIM CUAN HEI
 */
@WebServlet(name = "ShopServlet", urlPatterns = {"/user/Shop"})
public class ShopServlet extends HttpServlet {

    @PersistenceContext EntityManager em;
    @Resource UserTransaction utx;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            String productName = request.getParameter("productName");
            String categoryID = request.getParameter("categoryID");
            
            Query categoryQuery = em.createNamedQuery("Category.findAll");
            List<Category> categories = (List<Category>)categoryQuery.getResultList();
            request.setAttribute("categories", categories);
            
            List<Product> products = new ArrayList<Product>();
            
            if(productName == null){
                if(categoryID.equals("all")){
                    //Find all
                    Query productQuery = em.createNamedQuery("Product.findAll");
                    products = (List<Product>)productQuery.getResultList();

                    categoryID = "all";
                    request.setAttribute("categoryID", categoryID);
                }
                else {
                    //Find with category
                    Query productIdQuery = em.createNamedQuery("Productcategories.findByCategoryid");
                    productIdQuery.setParameter("categoryid",Integer.parseInt(categoryID));
                    List<Integer> productIDs = (List<Integer>)productIdQuery.getResultList();
                    
                    if(!productIDs.isEmpty()){
     
                        products = em
                            .createNamedQuery("Product.findByIds", Product.class)
                            .setParameter("id", productIDs)
                            .getResultList();
                    }
                }
            }
            else{
                //Find with product
                Integer productID = null;
                boolean isID = true;
                try{
                    productID = Integer.parseInt(productName);//Check wheter find by id or name
                }catch(NumberFormatException ex){
                    isID = false;
                }
                if(!isID){
                    Query query = em.createNamedQuery("Product.findLikeName");
                    query.setParameter("name", "%" + productName + "%");
                    products = query.getResultList();
                }else{
                    products = em.createNamedQuery("Product.findById").setParameter("id",productID).getResultList();
                }
 
                request.setAttribute("productName", productName);
                PrintWriter out = response.getWriter();
                out.print(products);
            }
            
            for(Product product : products){
                List<Stock> stocks = em.createNamedQuery("Stock.findByProductid").setParameter("productid", product.getId()).getResultList();
                int totalQty = 0;
                if(!stocks.isEmpty()){
                    for(Stock stock : stocks){
                        totalQty+=stock.getQuantity();
                    }
                }
                product.setTotalQty(totalQty);
                product.setCategory(Category.getCategories(em, Productcategories.getByProductID(em, product.getId())));
            }
            

            request.setAttribute("products", products);
            
            RequestDispatcher rd = request.getRequestDispatcher("Shop.jsp");
            rd.forward(request, response);
            
        }catch(Exception ex){
            response.sendRedirect("http://localhost:8080/JavaEcommerce/user/Home");
        }
    }
}
