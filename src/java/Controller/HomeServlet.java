/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import Model.Product;
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
import javax.transaction.UserTransaction;

/**
 *
 * @author LIM CUAN HEI
 */
@WebServlet(name = "HomeServlet", urlPatterns = {"/user/Home"})
public class HomeServlet extends HttpServlet {

    @PersistenceContext EntityManager em;
    @Resource UserTransaction utx;
  
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            String jpql = "SELECT ol.productid, SUM(ol.quantity) FROM Orderlist ol GROUP BY ol.productid";
            List<Object[]> results = em.createQuery(jpql, Object[].class).getResultList();
            List<Product> products = new ArrayList<Product>();
            for(Object[] result : results){
                List<Product> temp = em.createNamedQuery("Product.findById").setParameter("id", (Integer)result[0]).getResultList();
                if(!temp.isEmpty()){
                    Product prod = temp.get(0);
                    //prod.setTotalQty((Integer)result[1]);
                    products.add(prod);
                }
            }
            request.setAttribute("topSoldProducts", products);
            RequestDispatcher rd = request.getRequestDispatcher("Home.jsp");
            rd.forward(request, response);
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }

}
