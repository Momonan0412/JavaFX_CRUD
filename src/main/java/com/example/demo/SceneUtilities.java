package com.example.demo;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.IOException;
public class SceneUtilities {
    private SceneUtilities() {
    }
    public static void changeScene(ActionEvent event, String fxmlFile){
        try{
            FXMLLoader loader = new FXMLLoader(SceneUtilities.class.getResource(fxmlFile));
            Parent root = loader.load();
            if(root == null) System.out.println("The root is null!");
            Scene currentScene = ((Node) event.getSource()).getScene();

            Scene newScene = new Scene(root);
            newScene.setFill(Color.TRANSPARENT);

            Stage stage = (Stage) currentScene.getWindow();
            stage.setScene(newScene);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException("Error loading FXML file: " + fxmlFile, e);
        }
    }
}