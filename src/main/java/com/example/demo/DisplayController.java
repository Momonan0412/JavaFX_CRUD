package com.example.demo;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

import static com.example.demo.AlertController.showAlert;

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
    @FXML
    public Button btnChangePassword;
    public Button btnDeleteYourself;
    public ImageView imgView_Furina;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setTextValues();
        imgView_Furina.setImage(new Image("FurinaWut.png"));
        btnDeleteYourself.setOnMouseExited(event -> {
            imgView_Furina.setImage(new Image("FurinaWut.png"));
        });
        btnDeleteYourself.setOnMouseEntered(event -> {
            imgView_Furina.setImage(new Image("Furina.gif"));
        });
        btnChangePassword.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SceneUtilities.changeScene(event,"Update.fxml");
            }
        });
        btnDeleteYourself.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DatabaseUtilities.deleteCurrentLoggedInUser();
                showAlert("Success!", "Bye bye!");
                SceneUtilities.changeScene(event, "Login.fxml");
            }
        });
    }

    private void setTextValues() {
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
