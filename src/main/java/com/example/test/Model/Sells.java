package com.example.test.Model;

import java.util.Date;

public class Sells implements Comparable<Sells>{


    String cus;
    double price ;
    String date_pay;


    public Sells() {
    }

    public Sells(String cus, double price, Date date_pay){
        this.cus = cus ;
        this.price = price;
        this.date_pay = String.valueOf(date_pay);

    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDate_pay() {
        return date_pay;
    }

    public void setDate_pay(String date_pay) {
        this.date_pay = date_pay;
    }

    public String getCus() {
        return cus;
    }

    public void setCus(String cus) {
        this.cus = cus;
    }

    @Override
    public int compareTo(Sells o) {
        return this.getDate_pay().compareTo(o.getDate_pay());
    }
}
