package com.example.test.Controller;
import com.example.test.Dao.MedicineManager;
import com.example.test.Dao.OrdersManager;
import com.example.test.Dao.PaymentManager;
import com.example.test.Dao.stockManager;
import com.example.test.Model.Medicine;
import com.example.test.Model.MedicineCommande;
import com.example.test.Model.Orders;
import com.example.test.Model.Stock;
import com.example.test.utiles.Dbutils;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class OrdersController implements Initializable {

    @FXML
    private Button button_add;

    @FXML
    private Button button_search;

    @FXML
    private Spinner<Integer> spinner_id;
    @FXML
    private ListView<Orders> panel_orders;

    @FXML
    private TextField text_medicine;
    @FXML
    private Button update_btn;


    @FXML
    private TextField text_name;
    @FXML
    private ImageView medicineImage;

    public Connection conn = Dbutils.getConnection();

    private SpinnerValueFactory spinnerValueFactory;
    public void  showValueSpinner(){
        spinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,20,0);
//        spinner_id.setEditable(true);
        spinner_id.setValueFactory(spinnerValueFactory);

    }
    private int qte;
    public  void pushQuantite(){
        qte = spinner_id.getValue();
    }

    @FXML
    void addCommande(ActionEvent event) throws SQLException {
        if (OrdersManager.getDataOrders(text_medicine.getText())){
            alertdialog("The medicine exist in table order you should update it");
        }else if (null!= searh()){
            Medicine medicine =searh();
            LocalDate date = java.time.LocalDate.now();
            Orders orders=new Orders(medicine.getMedicineId(),medicine.getMedicine_Name(),qte,date);
            OrdersManager.addOrderstoDatabase(orders);
            alertdialog("order added");
            ShowDataList();
        }
    }

    @FXML
   public void searchMedicine(ActionEvent event) throws SQLException {
        Medicine medicine;
        if (!text_medicine.getText().equals("")){
           medicine= OrdersManager.medicineDatabyId(text_medicine.getText());
           if (medicine ==null){
               alertdialog("medecine not found");
           }else  {
               dataMedecine(medicine);
           }
        }else if (!text_name.getText().equals("")){
            medicine= OrdersManager.medicineDatabyName(text_name.getText());
            System.out.printf(medicine.getMedicineId());

            if (medicine ==null){
                alertdialog("medecine not found");
            }else  {
                dataMedecine(medicine);
            }
        }
        else {
            alertdialog("you should type medicine id or medicine name to search ");
        }
    }
    public void dataMedecine(Medicine medicine) throws SQLException {
        text_name.setText(medicine.getMedicine_Name());
        text_medicine.setText(medicine.getMedicineId());
        Orders orders = OrdersManager.getItemDataOrders(medicine);
        spinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(orders.getQte(),20,0);
        spinner_id.setValueFactory(spinnerValueFactory);

        try {
            File file = new File(MedicineManager.getImage(text_medicine.getText()));
            Image image = new Image(file.toURI().toString(),101,128,false,true);
            medicineImage.setImage(image);
        }catch (Exception e){
        }
    }
    public Medicine searh() throws SQLException {
        Medicine medicine;
        if (!text_medicine.getText().equals("")){
            medicine= OrdersManager.medicineDatabyId(text_medicine.getText());
            if (medicine ==null){
                alertdialog("medicine not found");
            }else  {
                return medicine;
            }

        }else if (!text_name.getText().equals("")){
            medicine= OrdersManager.medicineDatabyName(text_name.getText());
            if (medicine ==null){
                alertdialog("medicine not found");
            }else  {
                return medicine;
            }
        }
            alertdialog("you should chose a medicine  ");
            return null;
    }
    public void alertdialog(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    @FXML
    void updateData(ActionEvent event) throws SQLException {
     if (null!= searh() && OrdersManager.getDataOrders(text_medicine.getText())){
            Medicine medicine =searh();
            Orders orders = OrdersManager.getItemDataOrders(medicine);
            LocalDate date = java.time.LocalDate.now();
            orders.setDate(date);
            if (qte!=0)orders.setQte(qte);
            OrdersManager.updateOrderstoDatabase(orders);
            alertdialog("orders updated");
            ShowDataList();
        }
    }


    public void ShowDataList() throws SQLException {
        ObservableList<Orders> list =OrdersManager.getItemDataOrders();
        panel_orders.getItems().clear();
        panel_orders.setCellFactory(lv -> new ListCell<Orders>() {
            @Override
            public void updateItem(Orders order, boolean empty) {
                super.updateItem(order, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    String name = "Medicine Name : " + order.getMedicine_name();
                    Label labelname = new Label(name);
                    labelname.setFont(new Font("Arial", 17));
                    String quantity = "Quantity: " + order.getQte();
                    Label labelQuantity = new Label(quantity);
                    labelQuantity.setFont(new Font("Arial", 17));
                    String id_med = "Medicine ID : "+order.getMedicine_id();
                    Label labelid = new Label(id_med);
                    labelid.setFont(new Font("Arial", 17));

                    GridPane gridPane = new GridPane();

//                    gridPane.getStyleClass().add("gridlayout");
                    gridPane.setHgap(55);  // add 10 pixels of horizontal space between elements
                    gridPane.setVgap(10);
                    Button button = new Button("Delete");
                    button.getStyleClass().add("delete-btn");
                    button.setOnAction(event -> {
                        try {
                            confirmdelete(order);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        System.out.println("delete btn clicketd");

                    });

                    Button buttonconf = new Button("confirm");
                    buttonconf.getStyleClass().add("update-btn");
                    buttonconf.setOnAction(event -> {
                        Stock stock = new Stock(order.getMedicine_id(), order.getQte(),order.getDate());
                        try {
                            confirmStoksAdd(order,stock);
                            System.out.println("btn confirm clicketd");
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }

                    });

                    gridPane.add(labelname,0,0);
                    gridPane.add(labelid,1,0);
                    gridPane.add(labelQuantity, 2, 0);
                    gridPane.add(button, 3, 0);
                    gridPane.add(buttonconf, 4, 0);
                    setGraphic(gridPane);
                }
            }
        });

        for (Orders order : list) {
            panel_orders.getItems().add(order);
        }
    }

    public  void handlingMouse() throws SQLException {
        Orders orders = panel_orders.getSelectionModel().getSelectedItem();
        if (orders != null) {
            Medicine medicine;
            medicine = OrdersManager.medicineDatabyId(orders.getMedicine_id());
            if (medicine == null) {
                alertdialog("medecine not found");
            } else {
                dataMedecine(medicine);
            }
        }

    }

    public void confirmdelete(Orders orders) throws SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                " Are you suer to delete the order?",
                   ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            OrdersManager.deleteOrderstoDatabase(orders.getMedicine_id());
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("success Dialog");
            alert1.setHeaderText(" deleted  with success ");
            alert1.showAndWait();
            ShowDataList();

        }
    }

    public void confirmStoksAdd(Orders orders,Stock stock) throws SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                " Are you suer to confirm the order?",
                ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            Stock stockancient = stockManager.GetStock(orders.getMedicine_id()) ;

            if (null!=stockancient){
                Integer  temp = stockancient.getQuantity()+stock.getQuantity();
                stock.setQuantity(temp);
                stockManager.Update(stock);
            }else {
                stockManager.insert(stock);
            }

            OrdersManager.deleteOrderstoDatabase(orders.getMedicine_id());
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("success Dialog");
            alert1.setHeaderText(" the command are confirmed with success ");
            alert1.showAndWait();
            ShowDataList();

        }
    }





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showValueSpinner();
        try {
            ShowDataList();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
