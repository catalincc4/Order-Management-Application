package org.example.Model;

/**
 * Product is the  entity we'll be using to represents a product
 *
 *
 * @author Calin Catalin
 *
 */
public class Product {
    /**
     * The unique ID of a product
     */
    private Integer ID;
    /**
     * Name of the product
     */
    private String name;

    /**
     * Price per one product
     */
    private Integer price;

    /**
     * How many products are in stock
     */
    private Integer stock;


    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
