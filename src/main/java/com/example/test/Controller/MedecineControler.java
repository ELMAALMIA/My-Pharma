package com.example.test.Controller;

import com.example.test.Dao.medicineManager;
import com.example.test.Model.Medicine;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MedecineControler  implements Initializable {
    @FXML private TextField medicine_id;
    @FXML
    private TextField company_name;
    @FXML private TextField medicine_name;
    @FXML private ComboBox<String> type;
    @FXML private TextField price;
    @FXML private TextField Description;
    @FXML private Button add_btn;


    /*public void buttonPressed(KeyEvent event) throws SQLException {
        if(event.getCode().toString().equals("ENTER")){
            ActionEvent actionEvent= new ActionEvent(event.getSource(),event.getTarget());
            addbtn(actionEvent);
        }
    }*/

    public void addbtn(ActionEvent event) throws SQLException {
        Alert dialog;
        if(medicine_id.getText().equals("") || company_name.getText().equals("") || type.getSelectionModel().getSelectedItem()==null
                || medicine_name.getText().equals("") || Description.getText().equals("")
        ){
            Medicine medicine=null;
            medicine.setMedecinId(medicine_id.getText());
            medicine.setBrand(company_name.getText());
            medicine.setMedicine_Name(medicine_name.getText());
            medicine.setType(type.getSelectionModel().getSelectedItem());
            //medicine.setDescription(desciption_txt.getText());
            medicine.setPrice(Double.valueOf(price.getText()));
            if(medicineManager.insert(medicine)){
                System.out.println("inserted");
            }

        }
    }
    public ObservableList<Medicine> MedicineList(){
        ObservableList<Medicine> list = FXCollections.observableArrayList();
        return null;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
