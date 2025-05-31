/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import Model.Category;
import Model.Product;
import Model.Productcategories;
import Model.Productsize;
import Model.Stock;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
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
public class ProductServlet extends HttpServlet {

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
                Query query = em.createNamedQuery("Product.findLikeName");
                query.setParameter("name", "%" + productName + "%");
                products = query.getResultList();
 
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
            
            RequestDispatcher rd = request.getRequestDispatcher("Product.jsp");
            rd.forward(request, response);
            
        }catch(Exception ex){
            response.sendRedirect("http://localhost:8080/JavaEcommerce/user/Home");
        }
    }
    
    //Create Product
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            Part imagePart = request.getPart("productPicInput"); //Product Image
            String name = request.getParameter("name");
            String details = request.getParameter("details");
            double sellingPrice = Double.parseDouble(request.getParameter("sellingPrice"));
            double supplyPrice = Double.parseDouble(request.getParameter("supplyPrice"));
            String[] categories = request.getParameterValues("categories");
            //Validation
            boolean valid = true;
            
            if(valid){
                //Create product
                Product product = new Product(name, details, sellingPrice, supplyPrice);
                
                if (imagePart != null && imagePart.getSize() != 0) {
                    String uploadPath = getServletContext().getRealPath("") +"/Img/Product";
                    String uploadedPath = FileManager.uploadImage(uploadPath, imagePart);
                    product.setImgpath("../Img/Product/"+uploadedPath);
                }
                
                utx.begin();
                em.persist(product);
                utx.commit();
                
                //Add Product Categories
                for(String categoryID : categories){
                    Productcategories prodCategory = new Productcategories(product.getId(), Integer.parseInt(categoryID));
                    utx.begin();
                    em.persist(prodCategory);
                    utx.commit();
                }
                
                List<Productsize> sizes = em.createNamedQuery("Productsize.findAll").getResultList();
                for(Productsize size : sizes){
                    Stock stock = new Stock(product.getId(), size.getId(), 0);
                    utx.begin();
                    em.persist(stock);
                    utx.commit();
                }
            }
            
            response.sendRedirect("Product?categoryID=all");

        }catch(Exception ex){
            response.sendRedirect("http://localhost:8080/JavaEcommerce/user/Home");
        }
    }
    
}
