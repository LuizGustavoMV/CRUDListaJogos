package com.template.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        // A / no início indica a raiz do classpath (ex: /com/template/main.fxml)
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/com/template/main.fxml"));

        // Se o FXML estiver dentro de com/template/main/, usa:
        // FXMLLoader loader = new FXMLLoader(Main.class.getResource("main.fxml"));

        Scene scene = new Scene(loader.load(), 600, 400);

        stage.setTitle("Lista Jogos");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}