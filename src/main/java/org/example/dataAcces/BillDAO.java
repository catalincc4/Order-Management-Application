package org.example.dataAcces;

import org.example.Model.Bill;
import org.example.Model.Client;
import org.example.Model.Order;
import org.example.Model.Product;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Bill Data Access operation
 */
public class BillDAO {
    /**
     * Bill maker
     * @param client  client to be billed
     * @param product product details
     * @param order order details
     * @throws IOException file error
     */
    public BillDAO(Client client, Product product, Order order) throws IOException {
        Bill bill = new Bill();
        bill.setFileWriter(new FileWriter("D:\\PT2022_30222_Calin_Catalin_Assigment_3\\src\\main\\resources\\bill\\comanda" + order.getID() + ".txt"));
        bill.getFileWriter().append("*****************************************************\n");
        bill.getFileWriter().append("     Id comanda: " + order.getID() + "\n");
        bill.getFileWriter().append("     Nume Client: " + client.getName() + "\n");
        bill.getFileWriter().append("     Adresa Client: " + client.getAddress() + "\n");
        bill.getFileWriter().append("     Produs: " + product.getName() + "\n");
        bill.getFileWriter().append("     Pret/produs: " + product.getPrice() + "\n");
        bill.getFileWriter().append("     Cantitate: " + order.getAmount() + "\n");
        bill.getFileWriter().append("     Total: " + order.getTotal() + "\n");
        bill.getFileWriter().append("*****************************************************\n");
         bill.getFileWriter().close();
    }
}
