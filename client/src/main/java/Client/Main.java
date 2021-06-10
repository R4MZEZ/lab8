package Client;

//import Commands.CommandReady;
import gui.controllers.Controller;
import gui.controllers.LogInWindowController;
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

public class Main extends Application {
    static boolean connected = false;
    static int PORT = 3125;

    public static Connector getConnector() {
        return connector;
    }

    static Connector connector = new Connector(PORT);

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        new StartWindowController().setStage(stage);
        stage.setTitle("FlatsApp");
        stage.getIcons().add(new Image("/gui/scenes/2hpWoQJPELU.jpg"));


        FXMLLoader root = new FXMLLoader();
//        StartWindowController.setBundle(bundleRu);
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
        BorderPane pane = new BorderPane();
        pane.setCenter(label);
        Button ok = new Button("OK");
        pane.setBottom(ok);
        BorderPane.setMargin(ok, new Insets(20));
        ok.setPrefSize(100, 20);
        BorderPane.setAlignment(ok, Pos.CENTER);
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
        stage.show();
    }
}