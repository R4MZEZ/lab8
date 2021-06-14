package gui.controllers;

import Commands.Command;
import Commands.CommandAdd;
import content.Flat;
import content.Transport;
import content.View;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import static Client.Main.*;
import static Client.Main.getConnector;

public class AddWindowController implements Controller, Initializable{

    private static Stage stage;
    private static Flat updatedFlat = null;

    public static void setUpdatedFlat(Flat updatedFlat) {
        AddWindowController.updatedFlat = updatedFlat;
    }

    @FXML
    private Label livingAreaError;
    @FXML
    private Label numberError;
    @FXML
    private Label areaError;
    @FXML
    private Label YError;
    @FXML
    private Label XError;
    @FXML
    private Label nameError;
    @FXML
    private Label houseNameError;
    @FXML
    private Label houseYearError;
    @FXML
    private Label houseNumberError;

    public Label getLivingAreaError() {
        return livingAreaError;
    }

    public Label getNumberError() {
        return numberError;
    }

    public Label getAreaError() {
        return areaError;
    }

    public Label getYError() {
        return YError;
    }

    public Label getXError() {
        return XError;
    }

    public Label getNameError() {
        return nameError;
    }

    public Label getHouseNameError() {
        return houseNameError;
    }

    public Label getHouseYearError() {
        return houseYearError;
    }

    public Label getHouseNumberError() {
        return houseNumberError;
    }

    @FXML
    private Label nameLabel;

    @FXML
    private TextField name;

    @FXML
    private Label coordLabel;

    @FXML
    private TextField coordX;

    @FXML
    private TextField coordY;

    @FXML
    private Label areaLabel;

    @FXML
    private TextField area;

    @FXML
    private Label numberOfRoomsLabel;

    @FXML
    private TextField number_of_rooms;

    @FXML
    private Label livingAreaLabel;

    @FXML
    private TextField living_area;

    @FXML
    private Label viewLabel;

    @FXML
    private ChoiceBox<View> view;

    @FXML
    private Label transportLabel;

    @FXML
    private ChoiceBox<Transport> transport;

    @FXML
    private Label houseNameLabel;

    @FXML
    private TextField house_name;

    @FXML
    private Label houseYearLabel;

    @FXML
    private TextField house_year;

    @FXML
    private Label numberOfFlatsOnFloorLabel;

    @FXML
    private TextField numberOfFlatsOnFloor;

    @FXML
    private Button enter;

    @FXML
    private Button back;

    @FXML
    void back(ActionEvent event) {
        changeWindow("/gui/scenes/mainscene.fxml",stage);
    }

    @FXML
    void enter(ActionEvent event) {
        StringBuilder builder = new StringBuilder();
        builder.append(name.getText()).append("-_-");
        builder.append(coordX.getText()).append("-_-");
        builder.append(coordY.getText()).append("-_-");
        builder.append(area.getText()).append("-_-");
        builder.append(number_of_rooms.getText()).append("-_-");
        builder.append(living_area.getText()).append("-_-");
        builder.append(house_name.getText()).append("-_-");
        builder.append(house_year.getText()).append("-_-");
        builder.append(numberOfFlatsOnFloor.getText()).append("-_-");
        builder.append(view.getValue().toString()).append("-_-");
        builder.append(transport.getValue().toString()).append("-_-");

        Command command = new CommandAdd(this);

        if (command.validate(builder.toString())) {
            getConnector().send(command);
            showWindow(200, 600, getConnector().receive(), Color.BLACK);
        }
    }

    @Override
    public static void setStage(Stage stage) {
        AddWindowController.stage = stage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        view.setItems(FXCollections.observableList(Arrays.asList(View.values())));
        transport.setItems(FXCollections.observableList(Arrays.asList(Transport.values())));
    }

    public void checkBoxes(ActionEvent actionEvent) {
        enter.setDisable(view.getValue() == null || transport.getValue() == null);
    }
}
