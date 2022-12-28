package com.example.test.Dao;

import com.example.test.Model.Medicine;
import com.example.test.utiles.Dbutils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class medicineManager {

        public static boolean insert(Medicine medicine) throws SQLException {
            String query = "INSERT INTO medicine (medicine_id,brand,medicine_name,type_product,price,Image,Description) Values (?,?,?,?,?,?,?)";
            Connection con = Dbutils.getConnection();
            PreparedStatement stat = con.prepareStatement(query);
            stat.setString(1,String.valueOf(medicine.getMedecinId()));
            stat.setString(2,medicine.getBrand());
            stat.setString(3,medicine.getMedicine_Name());
            stat.setString(4,medicine.getType());
            stat.setString(5,String.valueOf(medicine.getPrice()));
            stat.setString(6,medicine.getDescription());
            int a = stat.executeUpdate();
            con.close();
            if(a ==1){
                return true;
            }else {
                return false;
            }
    }

}
