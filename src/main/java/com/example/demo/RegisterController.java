package com.example.demo;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import org.mindrot.jbcrypt.BCrypt;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {
    @FXML
    public Button btn_AccountExistProceedToLoginScene;
    @FXML
    public Button btn_Register;
    @FXML
    public CheckBox cBox_Java;
    @FXML
    public CheckBox cBox_CPlusPlus;
    @FXML
    public CheckBox cBox_C;
    @FXML
    public TextField txtFld_StudentID;
    @FXML
    public TextField txtFld_Email;
    @FXML
    public RadioButton rBtn_Others;
    @FXML
    public RadioButton rBtn_Male;
    @FXML
    public RadioButton rBtn_Female;
    @FXML
    public TextField txtFld_LastName;
    @FXML
    public TextField txtFld_FirstName;
    @FXML
    public TextField txtFld_Username;
    @FXML
    public PasswordField txtFld_Password;
    @FXML
    public ImageView ImageViewAlert;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btn_Register.setDisable(true);
        txtFld_Username.textProperty().addListener((observable, oldValue, newValue) -> updateRegisterButtonState());
        txtFld_Password.textProperty().addListener((observable, oldValue, newValue) -> updateRegisterButtonState());
        rBtn_Female.selectedProperty().addListener((observable, oldValue, newValue) -> updateRegisterButtonState());
        rBtn_Others.selectedProperty().addListener((observable, oldValue, newValue) -> updateRegisterButtonState());
        btn_AccountExistProceedToLoginScene.setOnAction(event -> SceneUtilities.changeScene(event, "Login.fxml"));
        btn_Register.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(DatabaseUtilities.insertDataToTable(
                        txtFld_Username.getText(),
                        BCrypt.hashpw(txtFld_Password.getText(), BCrypt.gensalt()),
                        txtFld_FirstName.getText(),
                        txtFld_LastName.getText(),
                        getSelectedGender(),
                        txtFld_Email.getText(),
                        txtFld_StudentID.getText(),
                        getSelectedProgrammingLanguage()
                ).equals("Data inserted successfully!")){
                    SceneUtilities.changeScene(event, "Login.fxml");
                }
            }
        });
    }

    private void updateRegisterButtonState() {
        boolean disableButton = txtFld_Username.getText().isEmpty() ||
                txtFld_Password.getText().isEmpty() ||
                txtFld_FirstName.getText().isEmpty() ||
                txtFld_LastName.getText().isEmpty() ||
                getSelectedGender().isEmpty() ||
                txtFld_Email.getText().isEmpty() ||
                txtFld_StudentID.getText().isEmpty() ||
                getSelectedProgrammingLanguage().isEmpty();
        btn_Register.setDisable(disableButton);
    }

    private String getSelectedGender() {
        if (rBtn_Female.isSelected()) {
            return "Female";
        } else if (rBtn_Others.isSelected()) {
            return "Others";
        } else {
            return "Male";
        }
    }
    private String getSelectedProgrammingLanguage() {
        StringBuilder progLanguages = new StringBuilder();
        if (cBox_C.isSelected()) {
            progLanguages.append("C ");
        }
        if (cBox_Java.isSelected()) {
            progLanguages.append("Java ");
        }
        if (cBox_CPlusPlus.isSelected()) {
            progLanguages.append("C++ ");
        }
        return progLanguages.toString().trim();
    }
}