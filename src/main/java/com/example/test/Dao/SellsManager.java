package com.example.test.Dao;

import com.example.test.Model.Sells;
import com.example.test.utiles.Dbutils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SellsManager {

    public SellsManager() {
    }

    public List<Sells> Sells(){
        List<Sells> list = new ArrayList<>();

        String sql = "SELECT SUM(PRICE),DATE_PAYER FROM salles GROUP BY DATE_PAYER;";
        try (
                Connection conn = Dbutils.getConnection();
                Statement stat = conn.createStatement();
                ResultSet res = stat.executeQuery(sql);
        ){

            while (res.next()) {
                Sells sells = new Sells();
                sells.setPrice(res.getInt("SUM(PRICE)"));
                sells.setDate_pay(res.getString("DATE_PAYER"));

                list.add(sells);
            }
            return list;
        } catch (SQLException e) {
            System.err.println("load failed");
            e.printStackTrace();
            return null;
        }


    }

    public String getMinDate(){


        String sql = "SELECT MIN(DATE_PAYER) FROM `salles`;";
        try (
                Connection conn = Dbutils.getConnection();
                Statement stat = conn.createStatement();
                ResultSet res = stat.executeQuery(sql);
        ){
            if (res.next()) {
                String count = String.valueOf(res.getInt("MIN(DATE_PAYER)"));
                return count;
            }

        } catch (SQLException e) {
            System.err.println("load failed");
            e.printStackTrace();

        }
        return null;
    }
    public String getMaxDate(){


        String sql = "SELECT Max(DATE_PAYER) FROM `salles` ;";
        try (
                Connection conn = Dbutils.getConnection();
                Statement stat = conn.createStatement();
                ResultSet res = stat.executeQuery(sql);
        ){
            if (res.next()) {
                String count = String.valueOf(res.getInt("Max(DATE_PAYER)"));
                return count;
            }
        } catch (SQLException e) {
            System.err.println("load failed");
            e.printStackTrace();

        }
        return null;
    }
    public String getMaxPrice(){


        String sql = "SELECT Max(PRICE) FROM `salles` ;";
        try (
                Connection conn = Dbutils.getConnection();
                Statement stat = conn.createStatement();
                ResultSet res = stat.executeQuery(sql);
        ){
            if (res.next()) {
                String count = String.valueOf(res.getInt("Max(PRICE)"));
                return count;
            }
        } catch (SQLException e) {
            System.err.println("load failed");
            e.printStackTrace();

        }
        return null;
    }

}