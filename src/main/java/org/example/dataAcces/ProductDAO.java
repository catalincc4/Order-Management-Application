package org.example.dataAcces;

import org.example.Model.Product;

import java.util.logging.Logger;

/**
 * product data access operations
 */
public class ProductDAO extends AbstractDAO<Product> {

    /**
     * logger to database
     */
    protected static final Logger LOGGER = Logger.getLogger(ProductDAO.class.getName());
    /**
     * insert statement for a product
     */
    private static final String insertStatementString = "INSERT INTO Product (name, price, stock)"
            + " VALUES (?,?,?)";
    /**
     * delete statement for a product
     */
    private final static String deleteStatementString ="DELETE FROM Product WHERE ID = ?";
    /**
     * update statement for a product
     */
    private final static String updateStatementString ="UPDATE Product SET name = ?, price = ?, stock = ?  where ID = ?";

}
