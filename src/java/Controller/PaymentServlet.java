/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import Model.Account;
import Model.Cart;
import Model.CartProduct;
import Model.Category;
import Model.Orderlist;
import Model.Orders;
import Model.Payment;
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
@WebServlet(name = "PaymentServlet", urlPatterns = {"/user/Payment"})
public class PaymentServlet extends HttpServlet {

    @PersistenceContext EntityManager em;
    @Resource UserTransaction utx;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            request.setAttribute("cart", getCart(request, response));
            RequestDispatcher rd = request.getRequestDispatcher("Payment.jsp");
            rd.forward(request, response);
            
        }catch(Exception ex){
            response.sendRedirect("http://localhost:8080/JavaEcommerce/user/Home");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            Account loggedAcc = Account.getLoggedAccount(request, response);
            String address = request.getParameter("address");
            String paymentMethod = request.getParameter("paymentMethod");
            Payment payment = null;
            
            List<CartProduct> cart = getCart(request, response);
            if(cart.isEmpty()){response.sendRedirect("Cart");}
            
            double total = getTotalAmount(loggedAcc, cart);

            if(paymentMethod==null){
                response.sendRedirect("Cart");
            }else if(paymentMethod.equals("card")){
                String cardNo = request.getParameter("cardNo");
                payment = new Payment(paymentMethod, cardNo, total);
            }else if (paymentMethod.equals("cash")){
                payment = new Payment(paymentMethod, "Pay on delivered.", total);
            }
            
            if(payment != null){
                utx.begin();
                em.persist(payment);
                utx.commit();
            }

            createOrder(cart, payment, address, loggedAcc);
            
            if(!loggedAcc.isVIP()){
                List<Orders> orders = em.createNamedQuery("Orders.findByAccountid").setParameter("accountid", loggedAcc.getId()).getResultList();
                double totalSpend = 0.0;
                if(!orders.isEmpty()){
                    
                    for(Orders order : orders){
                        List <Payment> payments = em.createNamedQuery("Payment.findById").setParameter("id", order.getId()).getResultList();
                        if(!payments.isEmpty()){
                            totalSpend+= payments.get(0).getAmount();
                        }
                    }
                }
                if(totalSpend >= 5000){
                    loggedAcc.setRole('V');
                    utx.begin();
                    em.merge(loggedAcc);
                    utx.commit();
                }
            }
            response.sendRedirect("Order");
            
        }catch(Exception ex){
            response.sendRedirect("http://localhost:8080/JavaEcommerce/user/Home");
        }
    }
    
    private List<CartProduct> getCart(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<CartProduct> cart = new ArrayList<CartProduct>();
        try{
            Account loggedAcc = Account.getLoggedAccount(request, response);

            List<Cart> carts = em.createNamedQuery("Cart.findByAccountid").setParameter("accountid",loggedAcc.getId()).getResultList();

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
        }catch(Exception ex){
            
        }
        return cart;
    }

    private void createOrder(List<CartProduct> cart, Payment payment, String address, Account loggedAcc) {
        try{ 
            Orders order = new Orders(address, "Packing", loggedAcc.getId(), payment.getId());
            utx.begin();
            em.persist(order);
            utx.commit();
            
            for(CartProduct row : cart){
                Orderlist orderlist = new Orderlist(order.getId(), row.getProduct().getId(), row.getSize().getId(), row.getQuantity());
                List<Stock> stocks = em.createNamedQuery("Stock.findByProductidSizeid")
                                .setParameter("productid", row.getProduct().getId())
                                .setParameter("sizeid", row.getSize().getId())
                                .getResultList();
                if(!stocks.isEmpty()){
                    Stock stock = stocks.get(0);
                    stock.setQuantity(stock.getQuantity()-row.getQuantity());
                    utx.begin();
                    em.merge(stock);
                    utx.commit();
                }
                utx.begin();
                em.persist(orderlist);
                utx.commit();
            }
            
            //Clear cart
            Cart.clearUserCart(em, utx, loggedAcc);
        }catch(Exception ex){
        
        }
    }

    private double getTotalAmount(Account loggedAcc, List<CartProduct> cart) {
        double subtotal = 0;
        double productSubAmount = 0;
        for (CartProduct cartItem : cart) {

            productSubAmount = 0;
            productSubAmount += cartItem.getProduct().getSellingprice() * cartItem.getQuantity();
            subtotal += productSubAmount;
        }
        double shippingFee = (subtotal>=1000)?0:25;
        double tax = subtotal *0.1;
        double total = subtotal + tax + shippingFee;
        if(loggedAcc.isVIP()){
            total *= 0.95;
        }
        
        return total;
    }
}
