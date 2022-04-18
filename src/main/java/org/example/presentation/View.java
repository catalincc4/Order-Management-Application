package org.example.presentation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import org.example.Model.Client;
import org.example.Model.Order;
import org.example.Model.Product;
import org.example.businessLogic.BillBLL;
import org.example.businessLogic.ClientBLL;
import org.example.businessLogic.OrderBLL;
import org.example.businessLogic.ProductBLL;
import org.example.dataAcces.BillDAO;
import org.example.dataAcces.ClientDAO;
import org.example.dataAcces.OrderDAO;
import org.example.dataAcces.ProductDAO;

import java.io.IOException;
import java.util.NoSuchElementException;

/**
 *View class
 */
public class View {
    /**
     * client business logic
     */
    ClientBLL clientBLL;
    /**
     * product business logic
     */
    ProductBLL productBLL;
    /**
     * order business logic
     */
    OrderBLL orderBLL;


    /**
     * Constructor for ClientController
     * @param clientBLL client business logic
     */
    public View(ClientBLL clientBLL) {
        this.clientBLL = clientBLL;
    }

    /**
     * Constructor for ProductController
     * @param productBLL product business logic
     */
    public View(ProductBLL productBLL) {
        this.productBLL = productBLL;
    }

    /**
     * Constructor for OrderController
     */
    public View() {
        this.clientBLL = new ClientBLL(new ClientDAO());
        this.productBLL = new ProductBLL(new ProductDAO());
        this.orderBLL = new OrderBLL(new OrderDAO());
    }

    /**
     * Method to get clients from database
     * @return list of clients
     */
    public ObservableList<Client> itemListClient() {
        ObservableList<Client> clients = FXCollections.observableList(clientBLL.findAll());
        return clients;
    }

    /**
     * Find a client by id
     * @param id id of client
     * @return the client
     * @throws NoSuchElementException the element doesn't exist
     */
    public Client findByIdClient(int id) throws NoSuchElementException {
        return clientBLL.findClientById(id);
    }

    /**
     * Insert a client in database
     * @param client to be insert in database
     */
    public void clientInsert(Client client) {
        clientBLL.insert(client);
    }

    /**
     * Delete a client from database
     * @param client client to be deleted
     */
    public void deleteClient(Client client) {
        clientBLL.delete(client.getID());
    }

    /**
     * Update a client from database
     * @param client client to be updated
     */
    public void updateClient(Client client) {
        clientBLL.update(client);
    }

    /**
     * Method to get products from database
     * @return list of products
     */
    public ObservableList<Product> itemListProduct(){
        return FXCollections.observableList(productBLL.findAll());
    }

    /**
     * Insert a product in database
     * @param product product to be inserted
     */
    public void productInsert(Product product){
        productBLL.insert(product);
    }

    /**
     * Delete a product in database
     * @param product product to be deleted
     */
    public void productDelete(Product product){
        productBLL.delete(product);
    }

    /**
     * Update a product in database
     * @param product product to be updated
     */
    public void updateProduct(Product product){
        productBLL.update(product);
    }

    /**
     * Search a product in database after id
     * @param id ID of the product
     * @return the product
     * @throws NoSuchElementException the element doesn't exist
     */
    public Product findByIdProduct(int id) throws NoSuchElementException {
       return productBLL.findProductById(id);
    }
    /**
     * Method to get orders from database
     * @return list of orders
     */
    public ObservableList<Order> itemListOrder(){
        return FXCollections.observableList(orderBLL.findAll());
    }
    /**
     * Insert a order in database
     * @param order order to be inserted
     */
    public void orderInsert(Order order){
        orderBLL.insert(order);
    }

    /**
     * Delete a order in database
     * @param order order to be deleted
     */
    public void orderDelete(Order order){
        orderBLL.delete(order);
    }

    /**
     * Update a order in database
     * @param order order to be updated
     */
    public void updateOrder(Order order){
        orderBLL.update(order);
    }

    /**
     * Bill maker
     * @param clientID  client to be billed
     * @param productID product details
     * @param order order details
     * @throws IOException error with the file
     */
    public void createBill(int clientID, int productID, Order order) throws IOException {
        BillBLL billBLL= new BillBLL(new BillDAO(findByIdClient(clientID), findByIdProduct(productID), order));
    }

    /**
     * Populate Client Table
     * @param table clientTable
     */
    public void populateClientTable(TableView<Client> table){
        clientBLL.populateTable(itemListClient(), table);
    }

    /**
     * Populate Product Table
     * @param table productTable
     */
    public void populateProductTable(TableView<Product> table){
        productBLL.populateTable(itemListProduct(), table);
    }

    /**
     * Populate Order Table
     * @param table orderTable
     */
    public void populateOrderTable(TableView<Order> table){
        orderBLL.populateTable(itemListOrder(), table);
    }
}
