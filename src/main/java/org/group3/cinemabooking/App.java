package org.group3.cinemabooking;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class App extends Application {
    private static Scene scene;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LoginView.fxml"));
        scene = new Scene(fxmlLoader.load(), 1344, 756);

        stage.setScene(scene);
        stage.setTitle("Login");
        InputStream inputStream = new FileInputStream("src/main/resources/Images/CinemaPLUSLogo.png");
        Image logo = new Image(inputStream);
        stage.getIcons().add(logo);
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

    public static void setTableEventHBox(String fxmlUrl, AnchorPane detailTableEvent, int X, int Y) throws IOException {
        detailTableEvent.getChildren().clear();
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxmlUrl));
        HBox hBox = fxmlLoader.load();
        detailTableEvent.getChildren().add(hBox);
        hBox.setLayoutX(X);
        hBox.setLayoutY(Y);
    }

    public static void setTableEventVBox(String fxmlUrl, AnchorPane detailTableEvent, int X, int Y) throws IOException {
        detailTableEvent.getChildren().clear();
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxmlUrl));
        VBox vBox = fxmlLoader.load();
        detailTableEvent.getChildren().add(vBox);
        vBox.setLayoutX(X);
        vBox.setLayoutY(Y);
    }

    public static void main(String[] args) {
        launch();
    }
}
