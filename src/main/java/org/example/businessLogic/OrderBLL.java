package org.example.businessLogic;

import javafx.scene.control.TableView;
import org.example.Model.Order;
import org.example.dataAcces.OrderDAO;

import java.util.List;
import java.util.NoSuchElementException;

public class OrderBLL {
    OrderDAO orderDAO;

    public OrderBLL(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    public Order findOrderById(int id) throws NoSuchElementException {
        Order order = orderDAO.findById(id);

        if(order == null){
            throw new NoSuchElementException("The order with the id:" + id + "dosen't exist");
        }

        return order;
    }

    public List<Order> findAll(){
        return orderDAO.findAll();
    }
    public void insert(Order order){
        orderDAO.insert(order);
    }
    public void update(Order order){
        orderDAO.update(order);
    }
    public void delete(Order order) { orderDAO.delete(order.getID());}
    public void populateTable(List<Order> orders, TableView<Order> table){
        orderDAO.populateTable(orders,table);
    }
}
