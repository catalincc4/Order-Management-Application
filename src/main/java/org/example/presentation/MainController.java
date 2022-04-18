package org.example.presentation;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import org.example.App;

import java.io.IOException;

/**
 * Controller for main interface
 */
public class MainController {

    /**
     * add client button
     */
    @FXML
    private Button addClientButton;

    /**
     * add order button
     */
    @FXML
    private Button addOrderButton;

    /**
     * add product button
     */
    @FXML
    private Button addProductButton;

    /**
     * switch to client view
     * @param event button pressed
     * @throws IOException -
     */
    @FXML
    void addClient(MouseEvent event) throws IOException {
      App.setRoot("client-view");
    }

    /**
     * switch to order view
     * @param event button pressed
     * @throws IOException -
     */
    @FXML
    void addOrder(MouseEvent event) throws IOException {
       App.setRoot("order-view");
    }

    /**
     * swtch to product view
     * @param event button pressed
     * @throws IOException -
     */
    @FXML
    void addProduct(MouseEvent event) throws IOException {
      App.setRoot("product-view");
    }

}
