package com.example.test.Controller;
import com.example.test.utiles.Dbutils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ProfilController implements Initializable {
    private String username;
    @FXML
    private TextField user;
    @FXML
    private Label userProfil ;
    @FXML private PasswordField oldPassword;
    @FXML private PasswordField newPassword;
    @FXML private PasswordField reNewPassword;

    @FXML
    public void buttonPressed(KeyEvent event) throws SQLException, IOException {
        if(event.getCode().toString().equals("ENTER"))
        {
            ActionEvent actionEvent = new ActionEvent(event.getSource(),event.getTarget());
            okBtn(actionEvent);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        user.setText(userProfil.getText());
        user.setEditable(false);
    }
    public void okBtn(ActionEvent e) throws SQLException, IOException {

        if(Dbutils.authentification(user.getText(), oldPassword.getText())){

            if(newPassword.getText().equals(reNewPassword.getText())){
                Dbutils.chanePassword(user.getText(), newPassword.getText());
                ((Node)e.getSource()).getScene().getWindow().hide();
                TrayNotification notif = new TrayNotification();
                notif.setTray("User Panel", user.getText()+"' password was successfully changed", NotificationType.INFORMATION);
                notif.setAnimationType(AnimationType.POPUP);
                notif.setRectangleFill(Paint.valueOf("#2A9A84"));
                notif.showAndDismiss(Duration.seconds(2));

                Stage stage = new Stage();
                Parent root = (Parent) FXMLLoader.load(this.getClass().getResource("/Login.fxml"));
                Scene scene = new Scene(root);
                //scene.getStylesheets().add(this.getClass().getResource("DashbordDesign.css").toExternalForm());
                stage.setScene(scene);
                //stage.getIcons().add(new Image("/assets/icon.png"));
                stage.setTitle("Pharmacy system managment");
                stage.setResizable(false);
                stage.sizeToScene();
                stage.show();
            }
            else{
                Alert dialog = new Alert(Alert.AlertType.WARNING);
                dialog.setTitle("Error");
                dialog.setHeaderText(null);
                dialog.setContentText("Passwords don't match");
                Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
                //stage.getIcons().add(new Image("/assets/icon.png"));
                dialog.initOwner((Stage)((Node)e.getSource()).getScene().getWindow());
                dialog.showAndWait();
            }

        } else{

            Alert dialog = new Alert(Alert.AlertType.WARNING);
            dialog.setTitle("Error");
            dialog.setHeaderText(null);
            dialog.setContentText("Old Password is incorrect");
            Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
            //stage.getIcons().add(new Image("/assets/icon.png"));
            dialog.initOwner((Stage)((Node)e.getSource()).getScene().getWindow());
            dialog.showAndWait();
        }
    }


}