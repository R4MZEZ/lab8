package gui.controllers;

import Client.Commander;
import Commands.*;
import content.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import static Client.Main.*;


public class MainWindowController implements Controller, Initializable {
    static Stage stage;
    static CommandUpdate commandUpdate;
    public TextField removeByIdField;
    public Label lostConnLabel;
    public TextField viewField;
    public TextField removeAtField;
    public TextField scriptField;
    public TextField updateField;
    Command command;
    @FXML
    private Menu languageMenu;
    @FXML
    private TableView<Flat> table;

    @FXML
    private TableColumn<Flat, Integer> id;

    @FXML
    private TableColumn<Flat, String> name;

    @FXML
    private TableColumn<Flat, Float> coordX;

    @FXML
    private TableColumn<Flat, Integer> coordY;

    @FXML
    private TableColumn<Flat, LocalDateTime> creationDate;

    @FXML
    private TableColumn<Flat, Integer> area;

    @FXML
    private TableColumn<Flat, Integer> numberOfRooms;

    @FXML
    private TableColumn<Flat, Integer> livingSpace;

    @FXML
    private TableColumn<Flat, View> view;

    @FXML
    private TableColumn<Flat, Transport> transport;

    @FXML
    private TableColumn<Flat, String> house_name;

    @FXML
    private TableColumn<Flat, Integer> house_year;

    @FXML
    private TableColumn<Flat, Integer> numberOfFlatsOnFloor;

    @FXML
    private TableColumn<Flat, String> user;

    @FXML
    private Label username;

    @Override
    public void setStage(Stage stage) {
        MainWindowController.stage = stage;
        username.setText(Commander.getUsername());
    }

    public void help(ActionEvent actionEvent) {
        getConnector().send(new CommandHelp());
        showWindow(600, 900, getConnector().receive(), Color.BLACK);
    }

    public void info(ActionEvent actionEvent) {
        getConnector().send(new CommandInfo());
        showWindow(200, 500, getConnector().receive(), Color.BLACK);
    }


    public void show(ActionEvent actionEvent) {
        try {
            getConnector().send(new CommandShow());
            ObservableList<Flat> list = FXCollections.observableList(getConnector().receive());
            table.setItems(list);
            lostConnLabel.setVisible(false);
        } catch (ClassCastException e) {
            lostConnLabel.setVisible(true);
        }
    }

    public void clear(ActionEvent actionEvent) {
        getConnector().send(new CommandClear());
        showWindow(200, 500, getConnector().receive(), Color.BLACK);
        show(null);
    }

    public void remove_by_id(ActionEvent actionEvent) {
        command = new CommandRemoveById();
        if (command.validate(removeByIdField.getText())) {
            getConnector().send(command);
            showWindow(150, 900, getConnector().receive(), Color.BLACK);
            show(null);
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        coordX.setCellValueFactory(new PropertyValueFactory<>("coordX"));
        coordY.setCellValueFactory(new PropertyValueFactory<>("coordY"));
        creationDate.setCellValueFactory(new PropertyValueFactory<>("creationDate"));
        area.setCellValueFactory(new PropertyValueFactory<>("area"));
        numberOfRooms.setCellValueFactory(new PropertyValueFactory<>("numberOfRooms"));
        livingSpace.setCellValueFactory(new PropertyValueFactory<>("livingSpace"));
        view.setCellValueFactory(new PropertyValueFactory<>("view"));
        transport.setCellValueFactory(new PropertyValueFactory<>("Transport"));
        house_name.setCellValueFactory(new PropertyValueFactory<>("house_name"));
        house_year.setCellValueFactory(new PropertyValueFactory<>("house_year"));
        numberOfFlatsOnFloor.setCellValueFactory(new PropertyValueFactory<>("house_numberOfFlatsOnFloor"));
        user.setCellValueFactory(new PropertyValueFactory<>("user"));
    }


    public void add(ActionEvent actionEvent) {
        new AddWindowController().setStage(changeWindow("/gui/scenes/add.fxml", stage));
    }

    public void update(ActionEvent actionEvent) {
        commandUpdate = new CommandUpdate();

        if (commandUpdate.validate(updateField.getText())){
            if (table.getItems().stream()
                    .anyMatch(flat -> flat.getId() == Integer.parseInt(updateField.getText()))) {

                AddWindowController.setUpdatedFlat(table.getItems().stream()
                        .filter(flat -> flat.getId() == Integer.parseInt(updateField.getText()))
                        .findFirst().get());
                new AddWindowController().setStage(changeWindow("/gui/scenes/add.fxml", stage));

            }else showWindow(200,500,"Элемента с указанным id не найдено.", Color.BLACK);

        }
    }

    public void remove_last(ActionEvent actionEvent) {
        getConnector().send(new CommandRemoveLast());
        showWindow(200, 600, getConnector().receive(), Color.BLACK);
    }

    public void average_of_living_space(ActionEvent actionEvent) {
        getConnector().send(new CommandAverage());
        showWindow(200, 600, getConnector().receive(), Color.BLACK);
    }

    public void max_by_house(ActionEvent actionEvent) {
        getConnector().send(new CommandMaxByHouse());
        showWindow(400, 600, getConnector().receive(), Color.BLACK);
    }

    public void remove_at(ActionEvent actionEvent) {
        command = new CommandRemoveAt();
        if (command.validate(removeAtField.getText())) {
            getConnector().send(command);
            showWindow(150, 900, getConnector().receive(), Color.BLACK);
            show(null);
        }
    }

    public void filter_less_than_view(ActionEvent actionEvent) {
        command = new CommandFilter();
        if (command.validate(viewField.getText())) {
            getConnector().send(command);
            showWindow(600, 1000, getConnector().receive(), Color.BLACK);
        }
    }

    public void exit(ActionEvent actionEvent) {
        getConnector().send(new CommandExit());
        System.exit(1);
    }

    public void execute_script(ActionEvent actionEvent) {
        Command command = new CommandExecuteScript();
        String path = scriptField.getText();
        command.validate(path);
        getConnector().send(command);
        Object answer = getConnector().receive();
        while (answer != null && !answer.toString().startsWith("====  Скрипт " + path + " успешно выполнен  ====")) {
            if (answer.getClass().getName().equals("".getClass().getName()))
                showWindow(400, 800, (String) answer, Color.BLACK);
            else show(null);
            answer = getConnector().receive();
        }
    }

    public void tableClick(MouseEvent mouseEvent) {
        Flat flat = table.getSelectionModel().getSelectedItem();
        int index = table.getSelectionModel().getSelectedIndex();
        if (flat != null) {
            updateField.setText(String.valueOf(flat.getId()));
            removeByIdField.setText(String.valueOf(flat.getId()));
            removeAtField.setText(String.valueOf(index));
        }

    }
}
