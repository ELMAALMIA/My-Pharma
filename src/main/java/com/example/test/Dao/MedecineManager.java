package com.example.test.Dao;


import com.example.test.utiles.Dbutils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MedecineManager {
    public MedecineManager(){
        
    }

    public static double getmedecineprice() throws SQLException {
        PreparedStatement stat = null;
        ResultSet res = null;
        Connection con = null;
        String sql = "Select * from medecine";

        try {
            con = Dbutils.getConnection();
            stat = con.prepareStatement(sql);
//            stat.setString(1, DepartNam);
            res = stat.executeQuery();
            if (res.next()) {
                return res.getInt("price");
            } else
                return 0;
        } catch (Exception e) {
            return 0;
        } finally {
            res.close();
            stat.close();
            con.close();
        }
    }


//    public static void  typeMedecine(ComboBox<?> type_push) throws SQLException {
//        String sql = " select * from medecine where type = 'Avaible' ";
//        PreparedStatement stat = null;
//        ResultSet res = null;
//
//        Connection con = Dbutils.getConnection();
//        ObservableList listData = FXCollections.observableArrayList();
//        stat = con.prepareStatement(sql);
//        res = stat.executeQuery();
//        while(res.next()){
//            listData.add(res.getString("type"));
//        }
////        type_push.setItems(listData);
////        addMedecinId(type_push);
//
//    }

    public  static  void addMedecinId(ComboBox<?> type_push) throws SQLException {


    }
}