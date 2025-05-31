/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author LIM CUAN HEI
 */
public class CartProduct {
    
    private Product product;
    private Productsize size;
    private Integer quantity;
    
    public CartProduct(){}
    
    public CartProduct(Product product, Productsize size, Integer quantity){
        this.product = product;
        this.size = size;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Productsize getSize() {
        return size;
    }

    public void setSize(Productsize size) {
        this.size = size;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    
    
    @Override
    public String toString() {
        return "CartProduct{" + "product=" + product + ", size=" + size + ", quantity=" + quantity + '}';
    }
}
