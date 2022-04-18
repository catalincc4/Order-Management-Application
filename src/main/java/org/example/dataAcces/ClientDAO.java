package org.example.dataAcces;

import org.example.Model.Client;

import java.util.logging.Logger;

/**
 * Client Data access operations
 */
public class ClientDAO extends AbstractDAO<Client> {

    /**
     * logger to database
     */
    protected static final Logger LOGGER = Logger.getLogger(ClientDAO.class.getName());
    /**
     * insert statement for a client
     */
    private static final String insertStatementString = "INSERT INTO Client (name,address)"
            + " VALUES (?,?)";
    /**
     * delete statement for a client
     */
    private final static String deleteStatementString ="DELETE FROM Client WHERE ID = ?";
    /**
     * update statement for a client
     */
    private final static String updateStatementString ="UPDATE Client SET name = ?, address = ? where ID = ?";

}
