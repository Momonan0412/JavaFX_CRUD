package com.example.demo;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

import static com.example.demo.AlertController.showAlert;

public class UpdateController implements Initializable {
    @FXML
    public TextField txtFld_OldUsername;
    @FXML
    public TextField txtFld_NewUsername;
    @FXML
    public Button btnUpdate;
    @FXML
    public Button btnVerify;
    @FXML
    public PasswordField passTxtFld_OldPassword;
    @FXML
    public PasswordField passTxtFld_NewPassword;
    @FXML
    public PasswordField passTxtFld_ReenterNewPassword;
    public Button btnBack;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setDefaultSettings();
        txtFld_NewUsername.textProperty().addListener((observable, oldValue, newValue) -> updateRegisterButtonState());
        passTxtFld_NewPassword.textProperty().addListener((observable, oldValue, newValue) -> updateRegisterButtonState());
        passTxtFld_ReenterNewPassword.textProperty().addListener((observable, oldValue, newValue) -> updateRegisterButtonState());
        btnBack.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                SceneUtilities.changeScene(actionEvent, "Display.fxml");
            }
        });
        btnVerify.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (DatabaseUtilities.checkIfDataExistInTable(txtFld_OldUsername.getText(), passTxtFld_OldPassword.getText())) {
                    System.out.println("Success!");
                    showAlert("Success!", "Data exists in the table.");
                    txtFld_NewUsername.setDisable(false);
                    passTxtFld_NewPassword.setDisable(false);
                    passTxtFld_ReenterNewPassword.setDisable(false);
                } else {
                    System.out.println("Failed!");
                    showAlert("Failed!", "Data does not exist in the table.");
                }
            }
        });
        btnUpdate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!txtFld_NewUsername.getText().isEmpty()) {
                    if(passTxtFld_NewPassword.getText().equals(passTxtFld_ReenterNewPassword.getText())) {
                        System.out.println("Successfully entered, before the updateTableData method");
                        DatabaseUtilities.updateTableData(txtFld_NewUsername.getText(),passTxtFld_NewPassword.getText());
                        System.out.println("Successfully entered, after the updateTableData method");
                        SceneUtilities.changeScene(event, "Login.fxml");
                    } else {
                        showAlert("Error!", "Failed to enter the new password and the updateTableData method!");
                        System.out.println("Error: Failed to enter the new password and the updateTableData method!");
                    }
                }
            }
        });
    }

    private void updateRegisterButtonState() {
        boolean disableButton = passTxtFld_NewPassword.getText().isEmpty() ||
                passTxtFld_ReenterNewPassword.getText().isEmpty() ||
                txtFld_NewUsername.getText().isEmpty();
        btnUpdate.setDisable(disableButton);
    }
    private void setDefaultSettings() {
        txtFld_NewUsername.setDisable(true);
        passTxtFld_NewPassword.setDisable(true);
        passTxtFld_ReenterNewPassword.setDisable(true);
        btnUpdate.setDisable(true);
    }
}
