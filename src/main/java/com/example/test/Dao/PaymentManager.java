package com.example.test.Dao;

import com.example.test.Model.MedicineCommande;
import com.example.test.Model.Stock;
import com.example.test.utiles.Dbutils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;

public class PaymentManager {
    private static Connection conn  = Dbutils.getConnection();


    public static void payer(String cucustummer_name, Double tDouble) throws SQLException {
        Statement instruction = conn.createStatement();
        LocalDate date = java.time.LocalDate.now();
        String requet = "INSERT INTO SALLES(custumer,price,DATE_PAYER) "+
        " values('" + cucustummer_name + "','" + tDouble +  "',STR_TO_DATE('" + date + "','%Y-%m-%d'))";
        int resultat = instruction.executeUpdate(requet);
        String sqldelete = "delete from ITEMCOMMANDES";
        int resultSet =instruction.executeUpdate(sqldelete);
    }

    // get medecie data ( use in the table )
    public static ObservableList<MedicineCommande> getMedecineData() throws SQLException {
        ObservableList<MedicineCommande> medecinelist = FXCollections.observableArrayList();
        ResultSet resultat = null;
        Statement instruction = conn.createStatement();
        String requet = "Select * from medicine , ITEMCOMMANDES where medicine.medicine_id like ITEMCOMMANDES.IDMEDECINE";
        resultat = instruction.executeQuery(requet);
        MedicineCommande medicineCommande;
        while (resultat.next()){
            medicineCommande = new MedicineCommande(resultat.getString("medicine_id")
                    ,resultat.getString("medicine_name"), resultat.getDouble("price"),
                    resultat.getInt("QUANTITEDEMANDER"));
            medecinelist.add(medicineCommande);
        }
        return medecinelist;
    }


    // fuction  to manipulate the labeel price
    public static Double showDataLabelPriceTotale() throws SQLException {
        ResultSet resultat = null;
        double totale_price =0.0;
        Statement instruction = conn.createStatement();
        String requet = "Select sum(medicine.price*ITEMCOMMANDES.QUANTITEDEMANDER) as total from medicine ,ITEMCOMMANDES where medicine.medicine_id like ITEMCOMMANDES.IDMEDECINE";
        resultat = instruction.executeQuery(requet);
        while (resultat.next()){
            totale_price =resultat.getDouble("TOTAL");
        }
        return totale_price ;
    }

    public static void deletedatafromtable(String id_medecine) throws SQLException {
        Statement instruction = conn.createStatement();
        String requet = "delete from  ITEMCOMMANDES  where IDMEDECINE = '"+id_medecine+"'";
        int resultat = instruction.executeUpdate(requet);
    }
    public static void deletedatafromtable() throws SQLException {
        Statement instruction = conn.createStatement();
        String sqldelete = "delete from ITEMCOMMANDES";
         instruction.executeUpdate(sqldelete);
    }

    public static boolean getDataFromMedecine(String id_medecine) throws SQLException {
        Statement instruction = conn.createStatement();
        String sqlrequest = "Select * from medicine where medicine_id = '"+id_medecine+"'";

        ResultSet res = instruction.executeQuery(sqlrequest);
        return res.next();
    }
    public  static boolean getDtaFromCommandeMedecine(String id_medecine) throws SQLException {
        Statement instruction = conn.createStatement();
        String sqlrequest = "Select *  from  ITEMCOMMANDES where IDMEDECINE = '"+id_medecine+"'";
        ResultSet res = instruction.executeQuery(sqlrequest);
        return res.next();
    }

    public static void updateData(Integer qte , String id_medecine) throws SQLException {
        Statement instruction = conn.createStatement();
        String requet = "update ITEMCOMMANDES set quantitedemander =  '"+qte+"' where IDMEDECINE = '"+id_medecine+"'";
       int resultat = instruction.executeUpdate(requet);
    }
    public static void addData(String id_medicine , Integer qte) throws SQLException {
        Statement instruction = conn.createStatement();
        String requet = "INSERT INTO ITEMCOMMANDES values( '"+id_medicine+"','"+qte+"')";
        int resultat = instruction.executeUpdate(requet);
    }
    public static  Integer  getStoksData(String id_medecine) throws SQLException {
        Statement instruction = conn.createStatement();
        String sqlrequest = "Select sum(Quantity) as somme from  stock where medicine_id = '"+id_medecine+"'";
        ResultSet res = instruction.executeQuery(sqlrequest);
        Integer sum =0;
        while (res.next()){
            sum = res.getInt("somme");
        }
        return sum;
    }


}
