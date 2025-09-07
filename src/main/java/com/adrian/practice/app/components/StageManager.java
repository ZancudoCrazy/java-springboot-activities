package com.adrian.practice.app.components;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.adrian.practice.app.enums.FxmlView;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

@Component
public class StageManager {

    private final Stage primaryStage;
    private final FxmlLoader fxmlLoader;
    private final String applicationTitle;

    public StageManager(FxmlLoader fxmlLoader, Stage primaryStage, String applicationTitle) {
        this.fxmlLoader = fxmlLoader;
        this.primaryStage = primaryStage;
        this.applicationTitle = applicationTitle;
    }
    
    public void switchScene(final FxmlView view) {
        primaryStage.setTitle(applicationTitle);
        Parent rootNode = loadRootNode(view.getFxmlPath());
        Scene scene = new Scene(rootNode);
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public void switchToNewScene(final FxmlView view) {
        Parent rootNode = loadRootNode(view.getFxmlPath());
        
        primaryStage.getScene().setRoot(rootNode);
        primaryStage.show();
    }

    public Parent loadRootNode(String fxmlPath) {
        try {
            return fxmlLoader.load(fxmlPath);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load FXML file: " + fxmlPath, e);
        }
    }
}
