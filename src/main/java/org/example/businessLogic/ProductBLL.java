package org.example.businessLogic;

import javafx.scene.control.TableView;
import org.example.Model.Product;
import org.example.dataAcces.ProductDAO;

import java.util.List;
import java.util.NoSuchElementException;

public class ProductBLL {

    ProductDAO productDAO;

    public ProductBLL(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    public Product findProductById(int id) throws NoSuchElementException {
         Product product = productDAO.findById(id);

        if(product == null){
            throw new NoSuchElementException("The client with the id:" + id + "dosen't exist");
        }

        return product;
    }

    public List<Product> findAll(){
        return productDAO.findAll();
    }
    public void insert(Product client){
        productDAO.insert(client);
    }
    public void update(Product product){
        productDAO.update(product);
    }
    public void delete(Product product) {productDAO.delete(product.getID());}
    public void populateTable(List<Product> products, TableView<Product> table){
        productDAO.populateTable(products,table);
    }
}
