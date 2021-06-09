package gui.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegisterWindowController implements Controller{
    public Label registrationLabel;
    public Label loginLabel;
    public TextField login;
    public Label passwordLabel;
    public PasswordField password;
    public Button registerButton;
    public Button backButton;
    private static Stage stage;
    private static String prevWindow;

    public void setStage(Stage stage) {
        RegisterWindowController.stage = stage;
    }

    public static void setPrevWindow(String s) {
        RegisterWindowController.prevWindow = s;
    }

    public void getLogin(ActionEvent actionEvent) {
    }

    public void getPassword(ActionEvent actionEvent) {
    }

    public void register(ActionEvent actionEvent) {
    }

    public void back(ActionEvent actionEvent) {
        Client.Main.changeWindow(prevWindow,stage,400,250);
    }
}
