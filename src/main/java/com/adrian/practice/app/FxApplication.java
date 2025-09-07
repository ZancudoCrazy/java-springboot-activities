package com.adrian.practice.app;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import com.adrian.practice.app.components.StageManager;
import com.adrian.practice.app.enums.FxmlView;

import javafx.application.Application;
import javafx.stage.Stage;

public class FxApplication  extends Application{
    private static Stage stage;
    private ConfigurableApplicationContext applicationContext;
    private StageManager stageManager;
    
    @Override
    public void init() {
        applicationContext = new SpringApplicationBuilder(MainEntry.class).run();
        System.out.println("JavaFX Application initializing");
    }
    
    @Override
    public void stop() {
        System.out.println("JavaFX Application stopped");
        applicationContext.close();
        stage.close();
    }

    @Override
    public void start(Stage primaryStage) {
        // Your JavaFX application logic goes here
        stage = primaryStage;
        stageManager = applicationContext.getBean(StageManager.class, primaryStage);
        index();
    }

    private void index() {
        stageManager.switchScene(FxmlView.INDEX);
    }
    
}
