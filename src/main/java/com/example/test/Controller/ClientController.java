package com.example.test.Controller;

import com.example.test.Dao.ClientManager;
import com.example.test.Model.Custummer;
import com.example.test.Model.MedicineCommande;
import com.example.test.utiles.Dbutils;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

public class ClientController  implements Initializable {

    @FXML
    private TextField nom;
    @FXML
    private TextField id;
    @FXML
    private TextField adress;
    @FXML
    private DatePicker date;

    @FXML
    private TableView<Custummer> tableid_client;

    @FXML
    private TableColumn<Custummer, String> numuro_table;

    @FXML
    private TableColumn<Custummer,String > name_table;

    @FXML
    private TableColumn<Custummer, String> adresse_table;

    @FXML
    private TableColumn<Custummer, Date> date_table;


    @FXML
    private void nvclient(ActionEvent event) throws IOException, SQLException {
        if (nom.getText().isEmpty() || id.getText().isEmpty() || adress.getText().isEmpty() ){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("all of input are required");
            alert.showAndWait();
        }else {
            ClientManager.addCustomer(id.getText(),nom.getText(),adress.getText(),date.getValue());
            ShowDataTable();
            resetdata();
        }

    }

    @FXML
    private void btnsuppr(ActionEvent event) throws IOException ,SQLException {
        if (nom.getText().isEmpty() || id.getText().isEmpty()  ){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("the name and number of  custommer are required");
            alert.showAndWait();
        }else {
            ClientManager.deleteCustomer(nom.getText());
            ShowDataTable();
            resetdata();
        }

    }

    public void updateclient(ActionEvent actionEvent) throws SQLException  {
        if (nom.getText().isEmpty() || id.getText().isEmpty() || adress.getText().isEmpty() ){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("all of input are required");
            alert.showAndWait();
        }else {
            ClientManager.updateCustomer(id.getText(),nom.getText(),adress.getText(),date.getValue());
            ShowDataTable();
            resetdata();
        }

    }

    public void ShowDataTable() throws SQLException {
        ObservableList<Custummer> listDataCustummer = ClientManager.getCustomerData();
        numuro_table.setCellValueFactory(new PropertyValueFactory<Custummer,String>("id"));
        name_table.setCellValueFactory(new PropertyValueFactory<Custummer,String>("nom"));
        date_table.setCellValueFactory(new PropertyValueFactory<Custummer,Date>("date"));
        adresse_table.setCellValueFactory(new PropertyValueFactory<Custummer,String>("adress"));
        tableid_client.setItems(listDataCustummer);
    }
    // make table interactive with the mouse ( get date from table )
    public  void handlingMouse(){
        Custummer custummer= tableid_client.getSelectionModel().getSelectedItem();
        nom.setText(custummer.getNom());
        id.setText(custummer.getId());
        adress.setText(custummer.getAdress());
        date.setValue(Instant.ofEpochMilli(custummer.getDate().getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate());

    }


    public void reset(MouseEvent mouseEvent) {
        resetdata();
    }
    public void resetdata(){
        nom.setText("");
        id.setText("");
        adress.setText("");
        date.setValue(LocalDate.now());
    }





    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        try {
            ShowDataTable();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
