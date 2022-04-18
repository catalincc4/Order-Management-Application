package org.example.dataAcces;

import org.example.Model.Order;

import java.util.logging.Logger;

/**
 * Order data access operations
 */
public class OrderDAO extends AbstractDAO<Order> {
    /**
     * logger to database
     */
    protected static final Logger LOGGER = Logger.getLogger(OrderDAO.class.getName());
    /**
     * insert statement for a order
     */
    private static final String insertStatementString = "INSERT INTO Orders (clientID, productID, purchaseDate, amount, total)"
            + " VALUES (?,?,?,?,?)";
    /**
     * delete statement for a order
     */
    private final static String deleteStatementString ="DELETE FROM Orders WHERE ID = ?";
    /**
     * update statement for a order
     */
    private final static String updateStatementString ="UPDATE Orders SET clientID = ?, productID = ?, purchaseDate = ?, amount = ?, total = ?  where ID = ?";
}
