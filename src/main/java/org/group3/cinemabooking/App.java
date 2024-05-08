package org.group3.cinemabooking;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    private static Scene scene;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Client/ClientView.fxml"));
        scene = new Scene(fxmlLoader.load(), 1344, 756);

        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();
    }

    private static Parent loadFXML(String url) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(url + ".fxml"));
        return fxmlLoader.load();
    }

    public static void setRoot(String fxml, String title) throws Exception {
        scene.setRoot(loadFXML(fxml));
        Stage primaryStage = (Stage) scene.getWindow();
        primaryStage.setTitle(title);
    }

    public static void main(String[] args) {
        launch();
    }
}
