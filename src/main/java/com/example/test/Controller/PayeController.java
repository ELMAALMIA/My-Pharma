package com.example.test.Controller;


import com.example.test.Dao.PaymentManager;
import com.example.test.Model.MedicineCommande;
import com.example.test.utiles.Dbutils;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.fxml.FXML;


import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;



public class PayeController  implements Initializable {

    @FXML
    private Button button_add;

    @FXML
    private Label text_total;

    @FXML
    private Button button_pay;

    @FXML
    private Spinner<Integer> id_spinner;

    @FXML
    private TextField custummer_name_text;

    @FXML
    private TextField id_medecine_text;

    @FXML
    private TableView<MedicineCommande> table_id;

    @FXML
    private TableColumn<MedicineCommande, String> id_medecine_table;

    @FXML
    private TableColumn<MedicineCommande, String> product_name_table;

    @FXML
    private TableColumn<MedicineCommande, Integer> qte_table;

    @FXML
    private TableColumn<MedicineCommande, Double> price_table;
    private SpinnerValueFactory spinnerValueFactory;


// initialisation de spinner

    public void  showValueSpinner(){
        spinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,20,0);
        id_spinner.setEditable(true);
        id_spinner.setValueFactory(spinnerValueFactory);

    }


    // getter for quantiter
    private int qte;
    public  void pushQuantite(){
        qte = id_spinner.getValue();
    }

    public Connection conn = Dbutils.getConnection();





// function  show data in table
    public void ShowDataTable() throws SQLException {
        ObservableList<MedicineCommande> listDataCommandeMedecine = PaymentManager.getMedecineData();
        id_medecine_table.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getMedecinId()));
        product_name_table.setCellValueFactory(new PropertyValueFactory<MedicineCommande,String>("productName"));
        qte_table.setCellValueFactory(new PropertyValueFactory<MedicineCommande,Integer>("qte"));
        price_table.setCellValueFactory(new PropertyValueFactory<MedicineCommande,Double>("price"));
        table_id.setItems(listDataCommandeMedecine);
    }




    // initialisation  de prix
    public void paymentPrice() throws SQLException {
        Double tDouble = 0.0;
        tDouble =  PaymentManager.showDataLabelPriceTotale();
        text_total.setText(tDouble.toString()+" MAD");
    }

     // function payment button (facture payment)
    public  void  paymentFinal() throws SQLException {
        Double tDouble = PaymentManager.showDataLabelPriceTotale();
        if (custummer_name_text.getText() =="" || tDouble ==0.0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("problem");
            alert.setHeaderText(" Trasaction not complet , you should ad name or add medecine ");
            alert.showAndWait();
        }else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, " Are you suer to finish the transaction ?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                PaymentManager.payer(custummer_name_text.getText(),tDouble);
                ShowDataTable();
                resetallcolums();
                paymentPrice();

                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("succes Dialog");
                alert1.setHeaderText(" Trasaction with succes ");
                alert1.showAndWait();

            }


        }
    }



    // reset colum after add ; delete ; update date in table
    public void  resetcolums() throws SQLException {
        showValueSpinner();
        paymentPrice();
        id_medecine_text.setText("");
    }

// reset all of colums after pay or cancel action
    public void resetallcolums() throws SQLException {
        resetcolums();
        custummer_name_text.setText("");
    }


    // make table interactive with the mouse ( get date from table )
    public  void handlingMouse(){
        MedicineCommande medicineCommande = table_id.getSelectionModel().getSelectedItem();
        if (medicineCommande != null) id_medecine_text.setText(""+medicineCommande.getMedecinId());

    }

    // function to add  new  medecine to table
    @FXML
    private void nouveauxCommandeMedecine(ActionEvent event) throws IOException,SQLException {
            int qtestoks =PaymentManager.getStoksData(id_medecine_text.getText());

            if(qte > qtestoks){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Quantite Information");
                alert.setHeaderText(null);
                alert.setContentText("The Quantite in the stoks is "+qtestoks);
                alert.showAndWait();
            }else if (qte == 0 || id_medecine_text.getText()==null){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText(null);
                alert.setContentText("The quantity and id medecine are required  is required  !");
                alert.showAndWait();

                //// couriger cette partie
            } else if (PaymentManager.getDataFromMedecine(id_medecine_text.getText()) && !PaymentManager.getDtaFromCommandeMedecine(id_medecine_text.getText()) ) {
                PaymentManager.addData(id_medecine_text.getText(), qte);
                ShowDataTable();
                PaymentManager.showDataLabelPriceTotale();
                resetcolums();
            }else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information");
                    alert.setHeaderText(null);
                    alert.setContentText("The id medecine must be in the database !");
                    alert.showAndWait();
            }
            }




    public void updateCommandeMedecine(ActionEvent actionEvent) throws SQLException {
        int qtestoks =PaymentManager.getStoksData(id_medecine_text.getText());
        if(qte > qtestoks){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Quantity Information");
            alert.setHeaderText(null);
            alert.setContentText("The Quantity in the stoks is "+qtestoks);
            alert.showAndWait();
        }else if (qte == 0 || id_medecine_text.getText()==null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("The quantity and id medecine are required  is required  !");
            alert.showAndWait();
        } else if (PaymentManager.getDataFromMedecine(id_medecine_text.getText()) && PaymentManager.getDtaFromCommandeMedecine(id_medecine_text.getText()) ) {
            PaymentManager.updateData(qte, id_medecine_text.getText());
            ShowDataTable();
            PaymentManager.showDataLabelPriceTotale();
            resetcolums();

        }else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText(null);
                alert.setContentText("The id medecine must be in the database !");
                alert.showAndWait();
            }
        }


    public void deleteCommandeMedecine(ActionEvent actionEvent) throws SQLException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
      if (PaymentManager.getDataFromMedecine(id_medecine_text.getText() ) && PaymentManager.getDtaFromCommandeMedecine(id_medecine_text.getText())){
                PaymentManager.deletedatafromtable(id_medecine_text.getText());
                ShowDataTable();
                PaymentManager.showDataLabelPriceTotale();
                resetcolums();
        }else {
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("The id of medecine must be in the DB !");
            alert.showAndWait();
        }
    }

    public void canceltransaction(ActionEvent actionEvent) throws SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, " Are you suer to Delete  ?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            PaymentManager.deletedatafromtable();
            ShowDataTable();
            resetallcolums();
            paymentPrice();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showValueSpinner();
        text_total.setText("0 MAD");
        try {
            ShowDataTable();
            paymentPrice();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
