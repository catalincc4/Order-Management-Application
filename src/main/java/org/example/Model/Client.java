package org.example.Model;
/**
 * Client is the  entity we'll be using to represents a customer
 *
 *
 * @author Calin Catalin
 *
 */
public class Client {
    /**
     * The customer unique ID
     */
    private int ID;
    /**
     * The customer name
     */
    private String name;
    /**
     * The customer address
     */
    private String address;


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
