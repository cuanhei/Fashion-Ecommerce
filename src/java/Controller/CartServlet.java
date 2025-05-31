/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import Model.Account;
import Model.Cart;
import Model.CartProduct;
import Model.Category;
import Model.Product;
import Model.Productcategories;
import Model.Productsize;
import Model.Stock;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;

/**
 *
 * @author LIM CUAN HEI
 */
@WebServlet(name = "CartServlet", urlPatterns = {"/user/Cart"})
public class CartServlet extends HttpServlet {
    
    @PersistenceContext EntityManager em;
    @Resource UserTransaction utx;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            HttpSession session = request.getSession(false); // returns null if no session exists
            if(session == null)
            {response.sendRedirect("Shop?categoryID=all");}
            
            Account loggedAcc = (Account)session.getAttribute("loggedAcc");
            if(loggedAcc == null)
            {response.sendRedirect("Shop?categoryID=all");}
            
            List<Cart> carts = em.createNamedQuery("Cart.findByAccountid").setParameter("accountid",loggedAcc.getId()).getResultList();
            List<CartProduct> cart = new ArrayList<CartProduct>();
            if(!carts.isEmpty()){
                List<Cart> rowsToRemove = new ArrayList<Cart>();
                for(Cart row : carts){
                    List<Product> products = em.createNamedQuery("Product.findById").setParameter("id", row.getProductid()).getResultList();
                    Product product = null;
                    if(products.isEmpty()){
                        //Product is deleted
                        rowsToRemove.add(row);
                        continue;
                    }
                    product = products.get(0);
                    product.setCategory(Category.getCategories(em, Productcategories.getByProductID(em, product.getId())));
                    List<Stock> stocks = em.createNamedQuery("Stock.findByProductidSizeid").setParameter("productid",product.getId()).setParameter("sizeid", row.getSizeid()).getResultList();
                    if(stocks.isEmpty()){
                        rowsToRemove.add(row);
                        continue;
                    }
                    Stock stock = stocks.get(0);
                    product.setTotalQty(stock.getQuantity());
                    if(row.getQuantity() > stock.getQuantity()){
                        if(stock.getQuantity() <= 0){
                            //Stock is empty
                            rowsToRemove.add(row);
                            continue;
                        }else{
                            row.setQuantity(stock.getQuantity());
                        }
                        utx.begin();
                        em.merge(row);
                        utx.commit();
                    }
                    
                    List<Productsize> sizes = em.createNamedQuery("Productsize.findById").setParameter("id", stock.getSizeid()).getResultList();
                    if(sizes.isEmpty()){
                        rowsToRemove.add(row);
                        continue;
                    }
                    Productsize size = sizes.get(0);
                    cart.add(new CartProduct(product,size,row.getQuantity()));
                }
                Cart.removeCarts(em, utx, rowsToRemove);
            }else{
                //cart is empty
            }
            
            request.setAttribute("cart", cart);
            RequestDispatcher rd = request.getRequestDispatcher("Cart.jsp");
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
                    update(request, response);
                    break;
                case "remove":
                    remove(request, response);
                    break;
                default:
                    break;
            }
        }catch(Exception ex){
            response.sendRedirect("http://localhost:8080/JavaEcommerce/user/Home");
        }
    }

    private void update(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        try{
            String productIdStr = request.getParameter("productID");
            String sizeIdStr = request.getParameter("sizeID");
            String decrease = request.getParameter("decrease");
            String increase = request.getParameter("increase");

            Integer productID = 0;
            Integer sizeID = 0;

            try{
                productID = Integer.parseInt(productIdStr);
                sizeID = Integer.parseInt(sizeIdStr);
            }catch(Exception ex){
                response.sendRedirect("Cart");
            }

            HttpSession session = request.getSession(false); // returns null if no session exists
            if(session == null)
            {response.sendRedirect("Shop?categoryID=all");}

            Account loggedAcc = (Account)session.getAttribute("loggedAcc");
            if(loggedAcc == null)
            {response.sendRedirect("Shop?categoryID=all");}

            List<Cart> carts = em.createNamedQuery("Cart.findInCartRow")
                    .setParameter("accountid",loggedAcc.getId())
                    .setParameter("productid", productID)
                    .setParameter("sizeid",sizeID).getResultList();
            if(carts.isEmpty())
            {response.sendRedirect("Shop?categoryID=all");}
            Cart cart = carts.get(0);

            if(decrease != null){
                cart.setQuantity(cart.getQuantity()-1);
                if(cart.getQuantity() <= 0){
                    utx.begin();
                    cart = em.merge(cart); // Merge or re-attach the entity to the persistence context
                    em.remove(cart); // Now remove the managed entity
                    utx.commit();
                }else{
                    utx.begin();
                    em.merge(cart);
                    utx.commit();
                }
            }
            
            if(increase != null){
                cart.setQuantity(cart.getQuantity()+1);
                utx.begin();
                em.merge(cart);
                utx.commit();
            }
            response.sendRedirect("Cart");
        }catch(Exception ex){
            response.sendRedirect("http://localhost:8080/JavaEcommerce/user/Home");
        }
    }

    private void remove(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            String productIdStr = request.getParameter("productID");
            String sizeIdStr = request.getParameter("sizeID");
           
            Integer productID = 0;
            Integer sizeID = 0;

            try{
                productID = Integer.parseInt(productIdStr);
                sizeID = Integer.parseInt(sizeIdStr);
            }catch(Exception ex){
                response.sendRedirect("Cart");
            }

            HttpSession session = request.getSession(false); // returns null if no session exists
            if(session == null)
            {response.sendRedirect("Shop?categoryID=all");}

            Account loggedAcc = (Account)session.getAttribute("loggedAcc");
            if(loggedAcc == null)
            {response.sendRedirect("Shop?categoryID=all");}

            List<Cart> carts = em.createNamedQuery("Cart.findInCartRow")
                    .setParameter("accountid",loggedAcc.getId())
                    .setParameter("productid", productID)
                    .setParameter("sizeid",sizeID).getResultList();
            if(carts.isEmpty())
            {response.sendRedirect("Shop?categoryID=all");}
            Cart cart = carts.get(0);
            utx.begin();
            Cart managedCart = em.merge(cart); // Get the managed instance
            em.remove(managedCart);           // Now remove the managed one
            utx.commit();
            response.sendRedirect("Cart");
        }catch(Exception ex){
            response.sendRedirect("http://localhost:8080/JavaEcommerce/user/Home");
        }
    }
}
