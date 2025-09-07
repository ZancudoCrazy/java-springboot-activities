package com.adrian.practice.app.enums;

public enum FxmlView {
    INDEX {
        @Override
        public String getFxmlPath() {
            return "/views/index.fxml";
        }
    }
    ;

    public abstract String getFxmlPath();
}
