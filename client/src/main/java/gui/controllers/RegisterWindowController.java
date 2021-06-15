package gui.controllers;

import Client.Commander;
import Client.Main;
import Commands.CommandRegister;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterWindowController implements Controller, Initializable {
    public Label registrationLabel;
    public Label loginLabel;
    public TextField login;
    public Label passwordLabel;
    public PasswordField password;
    public Button registerButton;
    public Button backButton;
    private static Stage stage;
    private static String prevWindow;
    public Label infoLabel;

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
        if (new CommandRegister().validate(login.getText())) {
            Client.Main.getConnector().send(new CommandRegister(login.getText(), password.getText()));
            String ans = Client.Main.getConnector().receive();
            if (ans.startsWith("Добро пожаловать")) {
                Commander.setUsername(login.getText());
                Client.Main.changeWindow("/gui/scenes/mainscene.fxml", stage);
            }
            infoLabel.setText(ans);
            infoLabel.setOpacity(100);
        }
    }

    public void back(ActionEvent actionEvent) {
        Client.Main.changeWindow(prevWindow,stage);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ResourceBundle bundle = Main.getBundle();
        registerButton.setText(bundle.getString("register"));
        backButton.setText(bundle.getString("back"));
        loginLabel.setText(bundle.getString("login"));
        passwordLabel.setText(bundle.getString("password"));
        registrationLabel.setText(bundle.getString("registration"));
    }
}
