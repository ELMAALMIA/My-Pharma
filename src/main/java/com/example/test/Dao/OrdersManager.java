package com.example.test.Dao;

import com.example.test.Model.Medicine;
import com.example.test.Model.Orders;
import com.example.test.utiles.Dbutils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;


public class OrdersManager {

    private static Connection conn  = Dbutils.getConnection();

    public static Medicine medicineDatabyId(String id) throws SQLException {

        Medicine medicine=null;
        Statement instruction = conn.createStatement();
        String requet = "Select * from medicine where medicine.medicine_id like '"+id+"'";
        ResultSet  resultat = instruction.executeQuery(requet);
        while (resultat.next()){
            medicine = new Medicine(
                    id,resultat.getString("company_name"),
                    resultat.getString("medicine_name"),
                    resultat.getString("type_product"),
                    resultat.getDouble("price"),
                    resultat.getString("Image"),
                    resultat.getString("Description")
            );
        }
        return medicine;
    }


    public static Medicine medicineDatabyName(String name) throws SQLException {
        Medicine medicine=null;
        Statement instruction = conn.createStatement();
        String requet = "Select * from medicine where medicine_name like '"+name+"'";
        ResultSet  resultat = instruction.executeQuery(requet);
        while (resultat.next()){
            medicine = new Medicine(
                    resultat.getString("medicine_id"),
                    resultat.getString("company_name"),
                    resultat.getString("medicine_name"),
                    resultat.getString("type_product"),
                    resultat.getDouble("price"),
                    resultat.getString("Image"),
                    resultat.getString("Description")
            );
        }
        return medicine;
    }



    public static void addOrderstoDatabase(Orders orders) throws SQLException {
        Statement instruction = conn.createStatement();
        LocalDate date = java.time.LocalDate.now();
        String requet = "INSERT INTO OrderMedecine(medecine_id,qte,date_order)"+
                " values('" +orders.getMedicine_id() + "','" + orders.getQte() +  "',STR_TO_DATE('" + orders.getDate()+ "','%Y-%m-%d'))";
        int resultat = instruction.executeUpdate(requet);
    }

    public static Boolean getDataOrders(String id) throws SQLException {
        Statement instruction = conn.createStatement();
        String requet = "Select * from OrderMedecine where medecine_id like '"+id+"'";
        ResultSet  resultat = instruction.executeQuery(requet);

        if (resultat.next()) return true;
        return false;
    }

    public static void updateOrderstoDatabase(Orders orders) throws SQLException {
        Statement instruction = conn.createStatement();
        LocalDate date = java.time.LocalDate.now();
        String requet = "update OrderMedecine set qte ='"+orders.getQte()+"', date_order = STR_TO_DATE('"+ date+ "','%Y-%m-%d') " +
                "where medecine_id like '"+orders.getMedicine_id()+"'";
        int resultat = instruction.executeUpdate(requet);
    } public static void deleteOrderstoDatabase(String id) throws SQLException {
        Statement instruction = conn.createStatement();
        String requet = "delete from OrderMedecine where medecine_id like '"+id+"'";
        int resultat = instruction.executeUpdate(requet);
    }

    public static ObservableList<Orders> getItemDataOrders() throws SQLException {
        Statement instruction = conn.createStatement();
        ObservableList<Orders> orderslist =  FXCollections.observableArrayList();
        String requet = "Select * from OrderMedecine ,medicine where medicine.medicine_id like OrderMedecine.medecine_id ";
        ResultSet  resultat = instruction.executeQuery(requet);
        while (resultat.next()){
            Orders orders = new Orders(
                    resultat.getString("medecine_id"),
                    resultat.getString("medicine_name"),
                    resultat.getInt("qte"),
                    resultat.getDate("date_order").toLocalDate()
            );

            orderslist.add(orders);
        }
        return orderslist;
    }
    public static Orders getItemDataOrders(Medicine medicine) throws SQLException {
        Statement instruction = conn.createStatement();
        Orders ordersItem =  null;
        String requet = "Select * from OrderMedecine ,medicine where medicine.medicine_id like OrderMedecine.medecine_id " +
                "and medicine.medicine_id like '"+medicine.getMedicineId()+"'";
        ResultSet  resultat = instruction.executeQuery(requet);
        while (resultat.next()){
            ordersItem = new Orders(
                    resultat.getString("medecine_id"),
                    resultat.getString("medicine_name"),
                    resultat.getInt("qte"),
                    resultat.getDate("date_order").toLocalDate()
            );
        }
        return ordersItem;
    }
}