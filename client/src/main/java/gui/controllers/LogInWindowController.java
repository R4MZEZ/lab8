package gui.controllers;

import Client.Commander;
import Commands.CommandLogin;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class LogInWindowController implements Controller {
    public Label enterLabel;
    public Label loginLabel;
    public TextField login;
    public Label passwordLabel;
    public PasswordField password;
    public Button enterButton;
    public Button back;
    static Stage stage;
    static String prevWindow;
    public Label infoLabel;

    public static void setPrevWindow(String prevWindow) {
        LogInWindowController.prevWindow = prevWindow;
    }

    public void setStage(Stage stage) {
        LogInWindowController.stage = stage;
    }

    public void enter(ActionEvent actionEvent) {
        if (new CommandLogin().validate(login.getText())) {
            Client.Main.getConnector().send(new CommandLogin(login.getText(), password.getText()));
            String ans = Client.Main.getConnector().receive();
            if (ans.startsWith("С возвращением")) {
                Commander.setUsername(login.getText());
                Client.Main.changeWindow("/gui/scenes/mainscene.fxml", stage);
            }
            infoLabel.setText(ans);
            infoLabel.setOpacity(100);
        }
    }

    public void back(ActionEvent actionEvent) {
        Client.Main.changeWindow(prevWindow, stage);
    }

}
