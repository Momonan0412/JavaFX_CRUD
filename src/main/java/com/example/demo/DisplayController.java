package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class DisplayController implements Initializable {
    @FXML
    public Text txt_FirstName;
    @FXML
    public Text txt_LastName;
    @FXML
    public Text txt_UserName;
    @FXML
    public Text txt_Email;
    @FXML
    public Text txt_StudentNumber;
    @FXML
    public Text txt_Gender;
    @FXML
    public Text txt_FavoriteProgLanguage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try{
            String[] dataStringArray = DatabaseUtilities.getDataFromTable();
            assert dataStringArray != null;
            txt_FirstName.setText(dataStringArray[0]);
            txt_LastName.setText(dataStringArray[1]);
            txt_Gender.setText(dataStringArray[2]);
            txt_Email.setText(dataStringArray[3]);
            txt_StudentNumber.setText(dataStringArray[4]);
            txt_FavoriteProgLanguage.setText(dataStringArray[5]);
            txt_UserName.setText(DatabaseUtilities.getUserNameOfTheLoggedInUser());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
