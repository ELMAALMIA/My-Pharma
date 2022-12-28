package com.example.test.Model;

import java.sql.Date;

public class Medicine {
  private String medecinId ;
  private String brand ;
  private String medicine_Name ;
  private String type;
  private  Double price ;
  private String Description;

  public Medicine(String medecinId, String brand,  String type,  Double price,String medicine_Name,String Description) {
    this.medecinId = medecinId;
    this.brand = brand;
    this.medicine_Name = medicine_Name;
    this.type = type;
    this.price = price;
    this.Description=Description;
  }


  public String getMedecinId() {
    return medecinId;
  }

  public void setMedecinId(String medecinId) {
    this.medecinId = medecinId;
  }

  public String getBrand() {
    return brand;
  }

  public void setBrand(String brand) {
    this.brand = brand;
  }

  public String getMedicine_Name() {
    return medicine_Name;
  }

  public void setMedicine_Name(String productName) {
    this.medicine_Name = productName;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getDescription() {return Description;}

  public void setDescription(String description) {Description = description;}

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }



}
