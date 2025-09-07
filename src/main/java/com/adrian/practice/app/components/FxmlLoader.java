package com.adrian.practice.app.components;

import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

@Component
public class FxmlLoader {
    private final ApplicationContext applicationContext;

    public FxmlLoader(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public Parent load(String fxmlPath) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory(applicationContext::getBean);
        loader.setLocation(getClass().getResource(fxmlPath));
        return loader.load();
        
    }
}
