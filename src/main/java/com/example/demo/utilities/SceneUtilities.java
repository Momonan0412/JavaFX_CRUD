package com.example.demo.utilities;
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
    public static void changeScene(ActionEvent event, String fxmlFile, String title){
        Parent root = null; // Assign root node for the new scene
        FXMLLoader loader = null;
        try{
            loader = new FXMLLoader(SceneUtilities.class.getResource(fxmlFile));
            root = loader.load();
            if(root == null) System.out.println("The root is null!");
        } catch (IOException e) {
            throw new RuntimeException("Error loading FXML file: " + fxmlFile, e);
        } finally {
            Scene currentScene = ((Node) event.getSource()).getScene();

            Scene newScene = new Scene(root);
            newScene.setFill(Color.TRANSPARENT);

            Stage stage = (Stage) currentScene.getWindow();
            stage.setTitle(title);
            stage.setScene(newScene);
            assert loader != null;
            stage.centerOnScreen();
            stage.show();
        }
    }
}