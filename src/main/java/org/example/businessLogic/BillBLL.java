package org.example.businessLogic;

import org.example.dataAcces.BillDAO;

public class BillBLL {
    private  BillDAO billDAO;

    public BillBLL(BillDAO billDAO) {
        this.billDAO = billDAO;
    }
}
