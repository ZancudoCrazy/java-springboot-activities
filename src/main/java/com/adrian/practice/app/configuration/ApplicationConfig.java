package com.adrian.practice.app.configuration;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import com.adrian.practice.app.components.FxmlLoader;
import com.adrian.practice.app.components.StageManager;

import javafx.stage.Stage;

@Configuration
public class ApplicationConfig {
    
    private final FxmlLoader fxmlLoader;
    private final String applicationTitle;


    public ApplicationConfig(FxmlLoader fxmlLoader,
        @Value("${application.title}") String applicationTitle) {
        this.fxmlLoader = fxmlLoader;
        this.applicationTitle = applicationTitle;
    }

    @Bean
    @Lazy
    public StageManager stageManager(Stage stage) throws IOException {
        return new StageManager(fxmlLoader, stage, applicationTitle);
    }
}
