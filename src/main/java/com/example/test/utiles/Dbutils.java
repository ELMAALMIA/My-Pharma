package com.example.test.utiles;

import java.sql.*;

public class Dbutils {
    	private static final String USERNAME = "root";
	private static final String PASSWORD = "123456789";
	private static final String CONN_STRING = "jdbc:mysql://localhost:3306/pharmacy";
    private static Connection cnx=null;


    public static Connection getConnection(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			return DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
		} catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
			return null;
		}

    }

    public static boolean authentification(String username,String password) throws SQLException{
        PreparedStatement stat=null;
        ResultSet res=null;
        Connection con=null;
        String sql = "SELECT * from users WHERE username= ? and password = ?";
        try {
            con = Dbutils.getConnection();
            stat = con.prepareStatement(sql);
            stat.setString(1, username);
            stat.setString(2, password);
            res = stat.executeQuery();
            if(res.next())
                return res.getString("username").equalsIgnoreCase(username) && res.getString("password").equalsIgnoreCase(password);
            else
                return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        finally{
            res.close();
            stat.close();
            con.close();
        }
    }
    public static void chanePassword(String username,String password) throws SQLException{
        PreparedStatement stat=null;
        Connection con=null;
        username = username.toLowerCase();
        password = password.toLowerCase();
        System.out.println("username " + username+"pass "+password);
        String sql = "UPDATE users SET password=? WHERE username= ?";
        try {
            con = Dbutils.getConnection();
            stat = con.prepareStatement(sql);
            stat.setString(1, password);
            stat.setString(2, username);
            int affected = stat.executeUpdate();
            if(affected == 1)
                System.out.println("password changed");
            else
                System.err.println("password changing failed");
        } catch (Exception e) {
            System.err.println("password changing failed");
        }
        finally{
            stat.close();
            con.close();
        }
    }
   }
