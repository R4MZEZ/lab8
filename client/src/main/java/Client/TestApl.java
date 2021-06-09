package Client;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class TestApl extends Application {

    @Override
    public void start(Stage primaryStage){
        primaryStage.setTitle("hello");
        FlowPane root = new FlowPane();
        Label label = new Label("пасаси");
        label.setFont(Font.font(90));
        Button button = new Button("OK");
        root.getChildren().add(label);
        root.getChildren().add(button);
        button.setOnMouseMoved((ae) -> label.setText("Привeт!"));
        primaryStage.setScene(new Scene(root,240,120));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
