package com.example.test.Dao;

import com.example.test.Model.MedicineCommande;
import com.example.test.utiles.Dbutils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PaymentManager {
    private static Connection conn  = Dbutils.getConnection();


    public static void payer(String cucustummer_name, Double tDouble) throws SQLException {
        Statement instruction = conn.createStatement();
        String requet = "INSERT INTO salles(custumer,price,DATE_PAYER) "
                + " values('"+cucustummer_name+"','"+tDouble+
                "',to_date('"+java.time.LocalDate.now() +"','yyyy-mm-dd'))";
        ResultSet resultat = instruction.executeQuery(requet);
        String sqldelete = "delete from itemcommandes";
        ResultSet resultSet =instruction.executeQuery(sqldelete);
    }

    // get medecie data ( use in the table )
    public static ObservableList<MedicineCommande> getMedecineData() throws SQLException {
        ObservableList<MedicineCommande> medecinelist = FXCollections.observableArrayList();
        ResultSet resultat = null;
        Statement instruction = conn.createStatement();
        String requet = "Select * from medicine , ITEMCOMMANDES where medicine.medecine_id like ITEMCOMMANDES.IDMEDECINE";
        resultat = instruction.executeQuery(requet);
        MedicineCommande medicineCommande;
        while (resultat.next()){
            medicineCommande = new MedicineCommande(resultat.getString("medecine_id")
                    ,resultat.getString("product_name"), resultat.getDouble("price"),
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
        String requet = "Select sum(medicine.price*ITEMCOMMANDES.QUANTITEDEMANDER) as total from medicine ,ITEMCOMMANDES where medicine.medecine_id like ITEMCOMMANDES.IDMEDECINE";
        resultat = instruction.executeQuery(requet);
        while (resultat.next()){
            totale_price =resultat.getDouble("TOTAL");
        }
        return totale_price ;
    }

    public static void deletedatafromtable(String id_medecine) throws SQLException {
        Statement instruction = conn.createStatement();
        String requet = "delete from  ITEMCOMMANDES  where IDMEDECINE = '"+id_medecine+"'";
        ResultSet resultat = instruction.executeQuery(requet);
    }
    public static void deletedatafromtable() throws SQLException {
        Statement instruction = conn.createStatement();
        String sqldelete = "delete from ITEMCOMMANDES";
        ResultSet res = instruction.executeQuery(sqldelete);
    }

    public static boolean getDataFromMedecine(String id_medecine) throws SQLException {
        Statement instruction = conn.createStatement();
        String sqlrequest = "Select * from medicine where medecine_id = '"+id_medecine+"'";

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
        System.out.println(requet);
        ResultSet resultat = instruction.executeQuery(requet);
    }
    public static void addData(String id_medecine , Integer qte) throws SQLException {
        Statement instruction = conn.createStatement();
        String requet = "INSERT INTO ITEMCOMMANDES values( '"+id_medecine+"','"+qte+"')";
        ResultSet resultat = instruction.executeQuery(requet);
    }


}
