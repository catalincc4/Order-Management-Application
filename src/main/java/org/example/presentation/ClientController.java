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
import org.example.Model.Client;
import org.example.businessLogic.ClientBLL;
import org.example.dataAcces.ClientDAO;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for client graphic interface
 */
public class ClientController implements Initializable {

    /**
     * add button for a new client
     */
    @FXML
    private Button addButton;

    /**
     * Text Filed to introduce the address of a new client
     */
    @FXML
    private TextField addressField;

    /**
     * Address column from clientTable
     */
    @FXML
    private TableColumn<Client, String> addressColumn;
    /**
     * Button to return to main interface
     */
    @FXML
    private FontAwesomeIconView cancelButton;

    /**
     * clientID column
     */
    @FXML
    private TableColumn<Client, Integer> clientIDColumn;

    /**
     * edit column
     */
    @FXML
    private TableColumn<Client, String> editColumn;

    /**
     * name column
     */
    @FXML
    private TableColumn<Client, String> nameColumn;
    /**
     * Text field to introduce the name for a new client
     */
    @FXML
    private TextField nameField;

    /**
     * table of clients
     */
    @FXML
    private TableView<Client> clientTable;

    /**
     * button to save changes after edit a client
     */
    @FXML
    private Button saveButton;

    private Client client = new Client();
    private ObservableList<Client> observableList;
    private View view = new View(new ClientBLL(new ClientDAO()));


    /**
     * save the changes
     * @param event button pressed
     */
    @FXML
    void saveButton(MouseEvent event){
        if(validate()){
            setError();
        client.setName(nameField.getText());
        client.setAddress(addressField.getText());
        view.updateClient(client);
       addButton.setDisable(false);
       saveButton.setDisable(true);
       addButton.setVisible(true);
       saveButton.setVisible(false);
       nameField.setText("");
       addressField.setText("");
       refresh();
        }else{
            setError();
        }
    }

    /**
     * add a new client
     * @param event button pressed
     */
    @FXML
    void addButton(MouseEvent event) {
        if (validate()) {
            setError();
            client.setName(nameField.getText());
            client.setAddress(addressField.getText());
            view.clientInsert(client);
            refresh();
        }else{
            setError();
        }
    }

    /**
     * return to main interface
     * @param event button pressed
     * @throws IOException error
     */
    @FXML
    void cancelButton(MouseEvent event) throws IOException {
        App.setRoot("primary-view");
    }

    /**
     * initialize the interface
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


        Callback<TableColumn<Client, String>, TableCell<Client, String>> cellFactory = (TableColumn<Client, String> param) -> {
            // make cell containing buttons
            final TableCell<Client, String> cell = new TableCell<Client, String>() {
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
                            client = clientTable.getSelectionModel().getSelectedItem();
                            view.deleteClient(client);
                            refresh();
                        });

                        editIcon.setOnMouseClicked((MouseEvent event) -> {
                            client = clientTable.getSelectionModel().getSelectedItem();
                            nameField.setText(client.getName());
                            addressField.setText(client.getAddress());
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

    /**
     * refresh the interface
     */
    public void refresh() {
        observableList = view.itemListClient();
        clientTable.setItems(observableList);
    }

    /**
     * validate provided dat for client
     * @return bool
     */
    public boolean validate(){
        if(nameField.getText().equals("") || addressField.getText().equals("")){
            return false;
        }
        return true;
    }

    /**
     * set error on interface to aware the user
     */
    public void setError(){
        if(nameField.getText().equals("")){
            nameField.setStyle("-fx-background-color: #c97272;" + "-fx-border-width: 1px;" + "-fx-border-color: red" );
        }else{
            nameField.setStyle("-fx-background-color: #ffffff;" + "-fx-border-width: 0px;");
        }
        if(addressField.getText().equals("")){
            addressField.setStyle("-fx-background-color: #c97272;" + "-fx-border-width: 1px;" + "-fx-border-color: red" );
        }
        else{
            addressField.setStyle("-fx-background-color: #ffffff;" + "-fx-border-width: 0px;");
        }
    }

}

