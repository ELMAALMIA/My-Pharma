package com.example.test.Controller;

import com.example.test.Dao.MedicineManager;
import com.example.test.Dao.stockManager;
import com.example.test.Model.Medicine;
import com.example.test.Model.Stock;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class StockController implements Initializable {
    @FXML private FlowPane Flow_Pane;
    @FXML private TextField medicine_id;
    @FXML private TextField quantity;
    @FXML private TextField search_medicine_id;
    @FXML private DatePicker date;



    public void generate() throws SQLException {
        ObservableList<Medicine> list = MedicineManager.MedicineList();
        GridPane gridPane ;
        ImageView imageView ;
        Image image;
        Label label_quantity;
        Label label_name;
        String image_path;
        if(!Flow_Pane.getChildren().isEmpty()){//checks if the flow_pane has any children
            Flow_Pane.getChildren().clear();
        }

        Stock stock;

        for(int i =0;i<list.size();i++) {

            label_quantity=new Label("  Quantity: 0");
            label_quantity.setFont(new Font("Arial", 18));

            gridPane = new GridPane();
            gridPane.getStyleClass().add("gridlayout");

            ObservableList<Medicine> medicine = MedicineManager.Search(list.get(i).getMedicineId());
            label_name= new Label(medicine.get(0).getMedicine_Name());
            label_name.setFont(new Font("Arial", 18));

            //show the quantity of the medicine if the medicine exists in the stock
            if (stockManager.CheckIfMedicineExistsInStock(list.get(i).getMedicineId())){
                stock = stockManager.GetStock(list.get(i).getMedicineId());//fchak ??
                label_quantity.setText("  Quantity: " + stock.getQuantity());
            }
            image_path=MedicineManager.getImage(list.get(i).getMedicineId());

            imageView  = new ImageView();
            File file = new File(image_path);
            image = new Image(file.toURI().toString(),130,128,false,true);
            imageView.setImage(image);
            gridPane.add(imageView,0,0);
            gridPane.add(label_quantity,0,1);
            gridPane.add(label_name,0,2);
            Flow_Pane.getChildren().add(gridPane);
        }

    }

    public void search() throws SQLException {
        String id = search_medicine_id.getText();
        if(!id.trim().equals("")){
            if(MedicineManager.CheckIfIdExist(id)){
                Image image;ImageView imageView;GridPane gridPane;Label label_quantity;File file;String image_path;
                Stock stock; Label label_name;
                gridPane = new GridPane();
                gridPane.getStyleClass().add("gridlayout");
                ObservableList<Medicine>medicine=MedicineManager.Search(id);
                Flow_Pane.getChildren().clear();
                image_path=MedicineManager.getImage(medicine.get(0).getMedicineId());
                label_quantity= new Label();
                stock = stockManager.GetStock(medicine.get(0).getMedicineId());//fchak ??
                label_quantity.setText("  Quantity: " + stock.getQuantity());
                label_quantity.setFont(new Font("Arial", 18));
                label_name= new Label(medicine.get(0).getMedicine_Name());
                label_name.setFont(new Font("Arial", 18));

                imageView  = new ImageView();
                file = new File(image_path);
                image = new Image(file.toURI().toString(),130,128,false,true);
                imageView.setImage(image);
                gridPane.add(imageView,0,0);

                gridPane.add(label_quantity,0,1);
                gridPane.add(label_name,0,2);
                Flow_Pane.getChildren().add(gridPane);
            }
        }else{
            generate();
        }

    }

    public void addbtn() throws SQLException {
        String medicineid = medicine_id.getText();
        Stock stock;
        Boolean test;
        if(!medicineid.trim().equals("") && !quantity.getText().trim().equals("") ){
            if(MedicineManager.CheckIfIdExist(medicine_id.getText())){//medicine exists in the table medicine
                if(!stockManager.CheckIfMedicineExistsInStock(medicineid)){//medicine exists in table stock
                    stock = new Stock(medicine_id.getText(),Integer.parseInt(quantity.getText()),date.getValue());
                    test = stockManager.insert(stock);
                    if(test){
                        System.out.println("inserted");
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setContentText("The medicine is inserted");
                        alert.showAndWait();
                    }
                }else{
                    stock = stockManager.GetStock(medicineid);
                    stock.setQuantity(stock.getQuantity()+Integer.parseInt(quantity.getText()));
                    stockManager.Update(stock);
                    System.out.println("updated");
                }
                generate();
            }else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Invalid inputs");
                alert.setContentText("The medicine that you refer to doesn't exist in the system");
                alert.showAndWait();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid inputs");
            alert.setContentText("Please fill the inputs");
            alert.showAndWait();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            generate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}