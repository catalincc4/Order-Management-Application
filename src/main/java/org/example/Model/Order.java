package org.example.Model;

import java.util.Date;
/**
 * Order is the  entity we'll be using to represents a order
 *
 *
 * @author Calin Catalin
 *
 */
public class Order {
    /**
     * The unique ID of a order
     */
    private Integer ID;
    /**
     * Customer ID
     */
    private Integer clientID;
    /**
     * Product ID
     */
    private Integer productID;
    /**
     * The date when the client purchase the product
     */
    private Date  purchaseDate;
    /**
     * The amount of products
     */
    private  Integer amount;
    /**
     * Total amount, the client have to pay for the products
     */
    private Integer total;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getID() {
        return ID;
    }

    public Integer getClientID() {
        return clientID;
    }

    public Integer getProductID() {
        return productID;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public void setClientID(Integer clientID) {
        this.clientID = clientID;
    }

    public void setProductID(Integer productID) {
        this.productID = productID;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
