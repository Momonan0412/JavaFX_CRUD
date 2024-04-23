package com.example.demo;

import javafx.scene.control.Alert;

public class AlertController {
    // TODO: MAKE AN FXML FILE WITH DESIGN!
    static void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
