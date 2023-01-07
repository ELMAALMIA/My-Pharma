package com.example.test;

import com.example.test.Dao.OrdersManager;
import com.example.test.Model.Medicine;
import com.example.test.Model.Orders;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) throws SQLException {
        Medicine medicine = new Medicine("123","bjkj","","",1.0,"","");
        medicine.setMedicineId("123");
     Orders orders = OrdersManager.getItemDataOrders(medicine);
        System.out.printf(orders.toString());

    }
}
