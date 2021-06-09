package gui.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LogInWindowController implements Controller{
    public Label enterLabel;
    public Label loginLabel;
    public TextField login;
    public Label passwordLabel;
    public PasswordField password;
    public Button enterButton;
    public Button back;
    static Stage stage;
    static String prevWindow;

    public static void setPrevWindow(String prevWindow) {
        LogInWindowController.prevWindow = prevWindow;
    }

    public void setStage(Stage stage) {
        LogInWindowController.stage = stage;
    }

    public void enter(ActionEvent actionEvent) {
    }

    public void back(ActionEvent actionEvent) {
        Client.Main.changeWindow(prevWindow,stage,400,250);
    }
}
