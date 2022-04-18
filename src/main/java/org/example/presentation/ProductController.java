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
import javafx.util.Callback;
import org.example.App;
import org.example.Model.Product;
import org.example.businessLogic.ProductBLL;
import org.example.dataAcces.ProductDAO;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for product view
 */
public class ProductController implements Initializable {

    @FXML
    private Button addButton;

    @FXML
    private FontAwesomeIconView cancelButton;


    @FXML
    private TableView<Product> productTable;

    @FXML
    private TableColumn<Product, String> editColumn;

    @FXML
    private TableColumn<Product, String> nameColumn;

    @FXML
    private TextField nameField;

    @FXML
    private TableColumn<Product, Integer> priceColumn;

    @FXML
    private TextField priceField;

    @FXML
    private TableColumn<Product, Integer> productIDColumn;

    @FXML
    private Button saveButton;

    @FXML
    private TableColumn<Product, Integer> stockColumn;

    @FXML
    private TextField stockField;
    Product product = new Product();

    private ObservableList<Product> observableList;
    private View view = new View(new ProductBLL(new ProductDAO()));

    /**
     * add a new product
     * @param event -
     */
    @FXML
    void addButton(MouseEvent event) {
        if (validate()) {
            setError();
            product.setName(nameField.getText());
            product.setPrice(Integer.parseInt(priceField.getText()));
            product.setStock(Integer.parseInt(stockField.getText()));
            view.productInsert(product);
            refresh();
        } else {
            setError();
        }
    }

    /**
     * switch to primary view
     * @param event -
     * @throws IOException -
     */
    @FXML
    void cancelButton(MouseEvent event) throws IOException {
        App.setRoot("primary-view");
    }

    /**
     * save changes
     * @param event -
     */
    @FXML
    void saveButton(MouseEvent event) {
        if (validate()) {
            setError();
            product.setName(nameField.getText());
            product.setPrice(Integer.parseInt(priceField.getText()));
            product.setStock(Integer.parseInt(stockField.getText()));
            view.updateProduct(product);
            addButton.setDisable(false);
            saveButton.setDisable(true);
            addButton.setVisible(true);
            saveButton.setVisible(false);
            nameField.setText("");
            priceField.setText("");
            stockField.setText("");
            refresh();
        } else {
            setError();
        }
    }

    /**
     * refresh interface
     */
    public void refresh() {
        observableList = view.itemListProduct();
        productTable.setItems(observableList);
    }

    /**
     * validate data
     * @return -
     */
    public boolean validate() {
        if (nameField.getText().equals("")) {
            return false;
        }
        try{
            Integer.parseInt(priceField.getText());
            Integer.parseInt(stockField.getText());
        }catch (NumberFormatException e){
            return false;
        }
        return true;
    }

    /**
     * aware user about errors
     */
    public void setError() {
        if (nameField.getText().equals("")) {
            nameField.setStyle("-fx-background-color: #c97272;" + "-fx-border-width: 1px;" + "-fx-border-color: red");
        } else {
            nameField.setStyle("-fx-background-color: #ffffff;" + "-fx-border-width: 0px;");
        }
        try {
            Integer.parseInt(priceField.getText());
            priceField.setStyle("-fx-background-color: #ffffff;" + "-fx-border-width: 0px;");
        } catch (NumberFormatException e) {
            priceField.setStyle("-fx-background-color: #c97272;" + "-fx-border-width: 1px;" + "-fx-border-color: red");
        }

        try {
            Integer.parseInt(stockField.getText());
            stockField.setStyle("-fx-background-color: #ffffff;" + "-fx-border-width: 0px;");

        } catch (NumberFormatException e) {
            stockField.setStyle("-fx-background-color: #c97272;" + "-fx-border-width: 1px;" + "-fx-border-color: red");
        }
    }

    /**
     * initialize interface
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

    view.populateProductTable(productTable);

        Callback<TableColumn<Product, String>, TableCell<Product, String>> cellFactory = (TableColumn<Product, String> param) -> {
            // make cell containing buttons
            final TableCell<Product, String> cell = new TableCell<Product, String>() {
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
                            product = productTable.getSelectionModel().getSelectedItem();
                            view.productDelete(product);
                            refresh();
                        });

                        editIcon.setOnMouseClicked((MouseEvent event) -> {
                            product = productTable.getSelectionModel().getSelectedItem();
                            nameField.setText(product.getName());
                            priceField.setText(String.valueOf(product.getPrice()));
                            stockField.setText(String.valueOf(product.getStock()));
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
        editColumn.setCellFactory(cellFactory);
        refresh();
    }
}
