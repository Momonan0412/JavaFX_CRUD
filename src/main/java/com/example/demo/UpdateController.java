package com.example.demo;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class UpdateController implements Initializable {
    @FXML
    public TextField txtFld_OldUsername;
    @FXML
    public TextField txtFld_OldPassword;
    @FXML
    public TextField txtFld_NewUsername;
    @FXML
    public TextField txtFld_NewPassword;
    @FXML
    public TextField txtFld_ReenterNewPassword;
    @FXML
    public Button btnUpdate;
    @FXML
    public Button btnVerify;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setDefaultSettings();
        txtFld_NewUsername.textProperty().addListener((observable, oldValue, newValue) -> updateRegisterButtonState());
        txtFld_NewPassword.textProperty().addListener((observable, oldValue, newValue) -> updateRegisterButtonState());
        txtFld_ReenterNewPassword.textProperty().addListener((observable, oldValue, newValue) -> updateRegisterButtonState());
        btnVerify.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (DatabaseUtilities.checkIfDataExistInTable(txtFld_OldUsername.getText(), txtFld_OldPassword.getText())) {
                    System.out.println("Success!");
                    txtFld_NewUsername.setDisable(false);
                    txtFld_NewPassword.setDisable(false);
                    txtFld_ReenterNewPassword.setDisable(false);
                } else {
                    System.out.println("Failed!");
                }
            }
        });
        btnUpdate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!txtFld_NewUsername.getText().isEmpty()) {
                    if(txtFld_NewPassword.getText().equals(txtFld_ReenterNewPassword.getText())) {
                        System.out.println("Successfully entered, before the updateTableData method");
                        DatabaseUtilities.updateTableData(txtFld_NewPassword.getText(),txtFld_NewPassword.getText());
                        System.out.println("Successfully entered, after the updateTableData method");
                        SceneUtilities.changeScene(event, "Login.fxml");
                    } else {
                        System.out.println("Error: Failed to enter the new password and the updateTableData method!");
                    }
                }
            }
        });
    }

    private void updateRegisterButtonState() {
        boolean disableButton = txtFld_NewPassword.getText().isEmpty() ||
                txtFld_NewPassword.getText().isEmpty() ||
                txtFld_NewUsername.getText().isEmpty();
        btnUpdate.setDisable(disableButton);
    }
    private void setDefaultSettings() {
        txtFld_NewUsername.setDisable(true);
        txtFld_NewPassword.setDisable(true);
        txtFld_ReenterNewPassword.setDisable(true);
        btnUpdate.setDisable(true);
    }
}
