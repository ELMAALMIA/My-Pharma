package com.example.test.Dao;

import com.example.test.Model.Custummer;
import com.example.test.utiles.Dbutils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;

public class ClientManager {
    public static void addCustomer(String id , String nom , String adress , LocalDate date)  {
        try{
            Connection conn = Dbutils.getConnection();
            ResultSet resultat = null;
            Statement instruction = conn.createStatement();
            String requet;
            if (null!=date){
                requet = "INSERT INTO custumer"
                        + " values(" + id + ",'" + nom + "','" + adress + "',to_date('" + date + "','yyyy-mm-dd'))";
            }else {
                requet = "INSERT INTO custumer"
                        + " values(" + id + ",'" + nom + "','" + adress + "',to_date('" + LocalDate.now() + "','yyyy-mm-dd'))";
            }
            resultat = instruction.executeQuery(requet);
        }
        catch(SQLException e){
            e.printStackTrace();
        }

    }

    public static void deleteCustomer(String nom) {
        try{
            Connection conn = Dbutils.getConnection();
            ResultSet resultat = null;
            Statement instruction = conn.createStatement();
            String requet = "delete from custumer where nom='"+nom+"'";
            resultat = instruction.executeQuery(requet);

        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateCustomer(String id , String nom , String adress , LocalDate date){
        try{
            Connection conn = Dbutils.getConnection();
            ResultSet resultat = null;
            Statement instruction = conn.createStatement();
            String requet;
            if (null != date){
                requet = "update custumer  set NOM ='" + nom + "', ADRESSE ='" + adress + "',DATEINSCRIT = to_date('" + date + "','yyyy-mm-dd') where ID='" + id + "'";
            }else {
                requet = "update custumer  set NOM ='" + nom + "', ADRESSE ='" + adress + "',DATEINSCRIT = to_date('" + LocalDate.now() + "','yyyy-mm-dd') where ID='" + id + "'";

            }
            resultat = instruction.executeQuery(requet);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static ObservableList<Custummer> getCustomerData() throws SQLException {
        ObservableList<Custummer> custummers = FXCollections.observableArrayList();
        Connection conn = Dbutils.getConnection();
        ResultSet resultat = null;
        Statement instruction = conn.createStatement();
        String requet = "Select * from custumer";
        resultat = instruction.executeQuery(requet);
        Custummer custummer;
        while (resultat.next()){
            custummer= new Custummer(resultat.getString("ID")
                    ,resultat.getString("NOM"), resultat.getString("adresse"),
                    resultat.getDate("DATEINSCRIT"));
        custummers.add(custummer);
        }
        return custummers;
    }

}
