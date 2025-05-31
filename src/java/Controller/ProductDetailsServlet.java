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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class ProductDetailsServlet extends HttpServlet {

    @PersistenceContext EntityManager em;
    @Resource UserTransaction utx;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            String productID = request.getParameter("productID");
            Query productQuery = em.createNamedQuery("Product.findById");
            productQuery.setParameter("id", Integer.parseInt(productID));
            List<Product> products = productQuery.getResultList();
            Product product = null;
            
            if(!products.isEmpty()){
                product = products.get(0);
                product.setCategory(Category.getCategories(em, Productcategories.getByProductID(em, product.getId())));
            }
            
            if(product == null){
                response.sendRedirect("Product?categoryID=all");
            }
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
            String purpose = request.getParameter("purpose");
            
            switch (purpose) {
                case "update":
                    update(request,response);
                    break;
                case "purchaseStock":
                    purchaseStock(request,response);
                    break;
                case "delete":
                    delete(request,response);
                    break;
                default:
                    break;
            }
        }catch(Exception ex){
            response.sendRedirect("http://localhost:8080/JavaEcommerce/user/Home");
        }
    }
    
    private void update(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            String productIdStr = request.getParameter("productID");
            Part imagePart = request.getPart("productPicInput"); //Product Image
            String name = request.getParameter("name");
            String details = request.getParameter("details");
            double sellingPrice = Double.parseDouble(request.getParameter("sellingPrice"));
            double supplyPrice = Double.parseDouble(request.getParameter("supplyPrice"));
            String[] categories = request.getParameterValues("category");
            Integer productID = 0;
            
            try{
                productID = Integer.parseInt(productIdStr);
            }catch(Exception ex){
                productID = 0;
            }
            
            List<Product> products = em.createNamedQuery("Product.findById").setParameter("id",productID).getResultList();
            Product product = null;
            if(!products.isEmpty()){
                product = products.get(0);
            }else{
                response.sendRedirect("Product?categoryID=all");
            }
            
            if (imagePart != null && imagePart.getSize() != 0) {
                String uploadPath = getServletContext().getRealPath("") +"/Img/Product";
                String uploadedPath = FileManager.uploadImage(uploadPath, imagePart);
                product.setImgpath("../Img/Product/"+uploadedPath);
            }
            
            product.setName(name);
            product.setDescription(details);
            product.setSellingprice(sellingPrice);
            product.setSupplierprice(supplyPrice);
            
            utx.begin();
            em.merge(product);
            utx.commit();
            
            utx.begin();
            em.createNamedQuery("Productcategories.deleteByProductid")
                .setParameter("productid", productID)
                .executeUpdate();
            utx.commit();

            //Add Product Categories
            for(String categoryID : categories){
                Productcategories prodCategory = new Productcategories(product.getId(), Integer.parseInt(categoryID));
                utx.begin();
                em.persist(prodCategory);
                utx.commit();
            }
            response.sendRedirect("ProductDetails?productID="+productIdStr);
        }catch(Exception ex){
            PrintWriter out = response.getWriter();
            out.print(ex.getMessage());
        }
    }

    private void purchaseStock(HttpServletRequest request, HttpServletResponse response) 
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
                response.sendRedirect("Product?categoryID=all");
            }
            
            List<Stock> stocks = em.createNamedQuery("Stock.findByProductidSizeid").setParameter("sizeid", sizeID).setParameter("productid", productID).getResultList();
            Stock stock = null;
            if(!stocks.isEmpty()){
                stock = stocks.get(0);
            }else{response.sendRedirect("Product?categoryID=all");}
            
            int oldQty = stock.getQuantity();
            stock.setQuantity(quantity+oldQty);
            utx.begin();
            em.merge(stock);
            utx.commit();
            
             response.sendRedirect("ProductDetails?productID="+productIdStr);
        }catch(Exception ex){
        
        }
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
        try{
            String productIdStr = request.getParameter("productID");
            Integer productID = 0;
            try{
                productID = Integer.parseInt(productIdStr);
            }catch(Exception ex){
                productID = 0;
            }
            
            List<Product> products = em.createNamedQuery("Product.findById").setParameter("id",productID).getResultList();
            Product product = null;
            if(!products.isEmpty()){
                product = products.get(0);
            }else{
                response.sendRedirect("Product?categoryID=all");
            }
            
            utx.begin();
            em.createNamedQuery("Stock.deleteByProductid")
                .setParameter("productid", productID)
                .executeUpdate();
            utx.commit();
 
            utx.begin();
            em.createNamedQuery("Product.deleteById")
                .setParameter("id", productID)
                .executeUpdate();
            utx.commit();

            response.sendRedirect("Product?categoryID=all");
        }catch(Exception ex){
        }
    }
}
