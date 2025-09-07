package com.adrian.practice.app.controllers;


import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.stereotype.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TitledPane;

@Controller
public class IndexController implements Initializable {
    @FXML
    private TitledPane title1;
    @FXML
    private TitledPane title2;
    @FXML
    private TitledPane title3;
    @Override

    public void initialize(URL location, ResourceBundle resources) {
        title1.setText("Usuarios");
        title2.setText("Panel de control");
        title3.setText("Notificaciones");
    }
}
