package gui.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class StartWindowController implements Controller{
    public Label flatsLabel;
    public Button authorizationButton;
    public Button registerButton;
    public Button exitButton;
    public ChoiceBox<String> language;
    static Stage stage;

    public void setStage(Stage stage) {
        StartWindowController.stage = stage;
    }

    public void authorization(ActionEvent actionEvent){
        LogInWindowController.setPrevWindow("/gui/scenes/start.fxml");
        new LogInWindowController().setStage(Client.Main.changeWindow("/gui/scenes/logging.fxml", stage));
    }

    public void register(ActionEvent actionEvent) {
        RegisterWindowController.setPrevWindow("/gui/scenes/start.fxml");
        new RegisterWindowController().setStage(Client.Main.changeWindow("/gui/scenes/register.fxml", stage));
    }

    public void exit(ActionEvent actionEvent) {
        System.exit(-1);
    }
}
