package org.example.businessLogic;

import javafx.scene.control.TableView;
import org.example.Model.Client;
import org.example.dataAcces.ClientDAO;

import java.util.List;
import java.util.NoSuchElementException;

public class ClientBLL {
    ClientDAO clientDAO;

    public ClientBLL(ClientDAO clientDAO) {
        this.clientDAO = clientDAO;
    }

    public Client findClientById(int id) throws NoSuchElementException {
        Client client = clientDAO.findById(id);

        if (client == null) {
            throw new NoSuchElementException("The client with the id:" + id + "dosen't exist");
        }
        return client;
    }

    public List<Client> findAll() {
        return clientDAO.findAll();
    }

    public void insert(Client client) {
        clientDAO.insert(client);
    }

    public void update(Client client) {
        clientDAO.update(client);
    }

    public void delete(int id) {
        clientDAO.delete(id);
    }
    public void populateTable(List<Client> clients, TableView<Client> table){
            clientDAO.populateTable(clients,table);
    }
}
