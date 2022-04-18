package org.example.presentation;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.Callback;
import org.example.App;
import org.example.Model.Client;
import org.example.Model.Order;
import org.example.Model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;

/**
 * Controller for order view
 */
public class OrderController implements Initializable {


    /**
     * edit column
     */
    @FXML
    private TableColumn<Order, String> editCol;

    /**
     * add button
     */
    @FXML
    private Button addButton;

    /**
     * Text field for product ID
     */
    @FXML
    private TextField productIDField;

    /**
     * Text field for client ID
     */
    @FXML
    private TextField clientIDField;

    /**
     * address column
     */
    @FXML
    private TableColumn<Client, String> addressColumn;

    /**
     * amount column
     */
    @FXML
    private TableColumn<Order, Integer> amountColumn;

    /**
     * cancel button
     */
    @FXML
    private FontAwesomeIconView cancelButton;

    /**
     * client ID column
     */
    @FXML
    private TableColumn<Client, Integer> clientIDColumn;

    /**
     * client ID column
     */
    @FXML
    private TableColumn<Order, Integer> clientIDOrderColumn;

    /**
     * clients table
     */
    @FXML
    private TableView<Client> clientTable;

    /**
     * text for error
     */
    @FXML
    private Text errorText;

    @FXML
    private TableColumn<Client, String> nameClientColumn;

    @FXML
    private TextField amountField;

    @FXML
    private TableColumn<Product, String> nameProductColumn;

    @FXML
    private TableColumn<Order, Integer> orderIDColumn;

    @FXML
    private TableView<Order> orderTable;

    @FXML
    private TableColumn<Product, Integer> priceProductColumn;

    @FXML
    private TableView<Product> productTable;

    @FXML
    private TableColumn<Product, Integer> productIDColumn;

    @FXML
    private TableColumn<Order, Integer> productIDOrderCoulmn;

    @FXML
    private TableColumn<Order, Date> purchaseDateCoulmn;

    @FXML
    private Button saveButton;

    @FXML
    private TableColumn<Product, Integer> stockColumn;

    @FXML
    private TableColumn<Order, Integer> totalCoulmn;


    Client client = new Client();
    Product product = new Product();
    Order order = new Order();


    private ObservableList<Product> observableListProduct;
    private ObservableList<Client> clientObservableList;
    private ObservableList<Order> orderObservableList;
    private View view = new View();

    /**
     * add a new order
     * @param event button pressed
     */
    @FXML
    void addButton(MouseEvent event) {
        if (validate()) {
            errorText.setText("");
            setError();
            order.setClientID(Integer.parseInt(clientIDField.getText()));
            order.setProductID(Integer.parseInt(productIDField.getText()));
            order.setAmount(Integer.parseInt(amountField.getText()));
            order.setPurchaseDate(new Date());
            view.orderInsert(order);
            refresh();
            try {
                view.createBill(order.getClientID(), order.getProductID(),orderObservableList.get(orderObservableList.size()-1));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } else {
            setError();
        }
    }

    /**
     * switch to main view
     * @param event button pressed
     * @throws IOException
     */
    @FXML
    void cancelButton(MouseEvent event) throws IOException {
        App.setRoot("primary-view");
    }

    /**
     * select a client
     * @param event -
     */
    @FXML
    void getClientTableElement(MouseEvent event) {
        client = clientTable.getSelectionModel().getSelectedItem();
        if(client != null){
            clientIDField.setText(String.valueOf(client.getID()));
        }
    }

    /**
     * select a product
     * @param event -
     */
    @FXML
    void getProductTableElement(MouseEvent event) {
        product = productTable.getSelectionModel().getSelectedItem();
        if(product != null){
            productIDField.setText(String.valueOf(product.getID()));
        }
    }

    /**
     * save changes
     * @param event -
     */
    @FXML
    void saveButton(MouseEvent event) {
        if (validate()) {
            errorText.setText("");
            setError();
            order.setClientID(Integer.parseInt(clientIDField.getText()));
            order.setProductID(Integer.parseInt(productIDField.getText()));
            order.setAmount(Integer.parseInt(amountField.getText()));
            order.setPurchaseDate(new Date());
            view.updateOrder(order);
            addButton.setDisable(false);
            saveButton.setDisable(true);
            addButton.setVisible(true);
            saveButton.setVisible(false);
            clientIDField.setText("");
            productIDField.setText("");
            amountField.setText("");
            refresh();
        } else {
            setError();
        }
    }


    /**
     * refresh interface
     */
    public void refresh() {
        observableListProduct = view.itemListProduct();
        productTable.setItems(observableListProduct);

        clientObservableList = view.itemListClient();
        clientTable.setItems(clientObservableList);

        orderObservableList = view.itemListOrder();
        orderTable.setItems(orderObservableList);
    }

    /**
     * validate data
     * @return bool
     */
    public boolean validate() {
        try{
            Integer.parseInt(amountField.getText());
            Integer.parseInt(clientIDField.getText());
            Integer.parseInt(productIDField.getText());
        }catch (NumberFormatException e){
            return false;
        }
        try {
            product = view.findByIdProduct(Integer.parseInt(productIDField.getText()));
            if(product.getStock() < Integer.parseInt(amountField.getText())){
                return false;
            }
            client = view.findByIdClient(Integer.parseInt(clientIDField.getText()));
        }catch (NoSuchElementException e){
            return false;
        }
        return true;
    }

    /**
     * aware user about errors
     */
    public void setError() {
        try {
            Integer.parseInt(amountField.getText());
            amountField.setStyle("-fx-background-color: #ffffff;" + "-fx-border-width: 0px;");
        } catch (NumberFormatException e) {
            amountField.setStyle("-fx-background-color: #c97272;" + "-fx-border-width: 1px;" + "-fx-border-color: red");
        }
        try {
            Integer.parseInt(clientIDField.getText());
            clientIDField.setStyle("-fx-background-color: #ffffff;" + "-fx-border-width: 0px;");
        } catch (NumberFormatException e) {
            clientIDField.setStyle("-fx-background-color: #c97272;" + "-fx-border-width: 1px;" + "-fx-border-color: red");
        }

        try {
            Integer.parseInt(productIDField.getText());
            productIDField.setStyle("-fx-background-color: #ffffff;" + "-fx-border-width: 0px;");

        } catch (NumberFormatException e) {
            productIDField.setStyle("-fx-background-color: #c97272;" + "-fx-border-width: 1px;" + "-fx-border-color: red");
        }
      int i=0;
        try {
            product = view.findByIdProduct(Integer.parseInt(productIDField.getText()));
            i++;
            client = view.findByIdClient(Integer.parseInt(clientIDField.getText()));
            if(product.getStock() < Integer.parseInt(amountField.getText())){
                errorText.setText("Stoc depasit");
            }
        }catch (NoSuchElementException | NumberFormatException e){
            if(i==0){
                errorText.setText("Produsul nu exista");
            }else {
                errorText.setText("Clientul nu exista");
            }
        }

    }


    /**
     * initialize
     * @param url -
     * @param resourceBundle -
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        saveButton.setDisable(true);
        saveButton.setVisible(false);
        loadData();
    }

    /**
     * load data for interface
     */
    public void loadData() {

        view.populateClientTable(clientTable);
        view.populateProductTable(productTable);
        view.populateOrderTable(orderTable);

        Callback<TableColumn<Order, String>, TableCell<Order, String>> cellFactory = (TableColumn<Order, String> param) -> {
            // make cell containing buttons
            final TableCell<Order, String> cell = new TableCell<Order, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                        FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE);

                        editIcon.setStyleClass("editIcon");
                        deleteIcon.setStyleClass("deleteIcon");

                        deleteIcon.setOnMouseClicked((MouseEvent event) -> {
                            order = orderTable.getSelectionModel().getSelectedItem();
                            view.orderDelete(order);
                            refresh();
                        });

                        editIcon.setOnMouseClicked((MouseEvent event) -> {
                            order = orderTable.getSelectionModel().getSelectedItem();
                            clientIDField.setText(String.valueOf(order.getClientID()));
                            productIDField.setText(String.valueOf(order.getProductID()));
                            amountField.setText(String.valueOf(order.getAmount()));
                            addButton.setVisible(false);
                            saveButton.setVisible(true);
                            addButton.setDisable(true);
                            saveButton.setDisable(false);
                        });

                        HBox managebtn = new HBox(editIcon, deleteIcon);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));
                        HBox.setMargin(editIcon, new Insets(2, 3, 0, 2));
                        setGraphic(managebtn);
                        setText(null);

                    }
                }

            };

            return cell;
        };
        editCol.setCellFactory(cellFactory);
        refresh();
    }

}
