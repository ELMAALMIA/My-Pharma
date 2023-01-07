package com.example.test.Model;


import java.sql.Date;
import java.time.LocalDate;

public class Orders {

    private String medicine_id;
    private String medicine_name;
    private  Integer qte;
    private LocalDate date;

    private String  status;

    @Override
    public String toString() {
        return "Orders{" +
                "medicine_id='" + medicine_id + '\'' +
                ", medicine_name='" + medicine_name + '\'' +
                ", qte=" + qte +
                ", date=" + date +
                ", status='" + status + '\'' +
                '}';
    }

    public Orders(String medicine_id, String medicine_name, Integer qte, LocalDate date) {
        this.medicine_id = medicine_id;
        this.medicine_name = medicine_name;
        this.qte = qte;
        this.date=date;
//        this.status = status;
    }

    public String getMedicine_id() {
        return medicine_id;
    }

    public void setMedicine_id(String medicine_id) {
        this.medicine_id = medicine_id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getMedicine_name() {
        return medicine_name;
    }

    public void setMedicine_name(String medicine_name) {
        this.medicine_name = medicine_name;
    }

    public Integer getQte() {
        return qte;
    }

    public void setQte(Integer qte) {
        this.qte = qte;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
