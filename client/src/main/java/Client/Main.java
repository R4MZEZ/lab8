package Client;

import gui.controllers.Controller;
import gui.controllers.StartWindowController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;


public class Main extends Application {
    static boolean connected = false;
    static int PORT = 3125;
    static ResourceBundle bundle = ResourceBundle.getBundle("bundles.Resources");

    public static ResourceBundle getBundle() {
        return bundle;
    }

    public static void setBundle(ResourceBundle bundle) {
        Main.bundle = bundle;
    }

    public static Connector getConnector() {
        return connector;
    }

    static Connector connector = new Connector(PORT);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setResizable(false);
        new StartWindowController().setStage(stage);
        stage.setTitle("FlatsApp");
        stage.getIcons().add(new Image("/gui/scenes/icon.jpg"));


        FXMLLoader root = new FXMLLoader();
        root.setLocation(getClass().getResource("/gui/scenes/start.fxml"));
        Scene scene = new Scene(root.load());

        stage.setScene(scene);
        stage.show();
    }

    public static Stage changeWindow(String window, Stage startStage) {
        try {
            FXMLLoader root = new FXMLLoader();
            root.setLocation(Main.class.getResource(window));
            Scene scene = new Scene(root.load());
            Stage stage = new Stage();

            stage.setScene(scene);
            startStage.close();

            Controller controller = root.getController();
            controller.setStage(stage);

            stage.setResizable(false);
            stage.setTitle("FlatsApp");
            stage.getIcons().add(new Image("/gui/scenes/icon.jpg"));
            stage.show();
            return stage;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void showWindow(double height, double width, String msg, Color color) {

        Label label = new Label(msg);
        label.setTextFill(color);
        label.setFont(new Font(20));
        label.setStyle("-fx-text-fill: lightgray;");
        BorderPane pane = new BorderPane();
        pane.setCenter(label);
        Button ok = new Button("OK");
        pane.setBottom(ok);
        BorderPane.setMargin(ok, new Insets(20));
        ok.setPrefSize(100, 20);
        ok.setStyle("-fx-focus-traversable: false; -fx-text-fill: white;");
        BorderPane.setAlignment(ok, Pos.CENTER);
        pane.setStyle("-fx-accent: #1e74c6;  -fx-focus-color: -fx-accent;-fx-base: #373e43;-fx-control-inner-background: derive(-fx-base, 35%);-fx-control-inner-background-alt: -fx-control-inner-background ;");
        Scene scene = new Scene(pane);
        Stage stage = new Stage();
        ok.setOnAction(event -> {
            if (connected) {
                stage.close();
            } else {
                System.exit(-1);
            }
        });

        stage.setScene(scene);
        stage.setMinWidth(width);
        stage.setMinHeight(height);
        stage.setHeight(height);
        stage.setWidth(width);
        stage.setAlwaysOnTop(true);
        stage.getIcons().add(new Image("/gui/scenes/icon.jpg"));
        stage.show();
    }
}