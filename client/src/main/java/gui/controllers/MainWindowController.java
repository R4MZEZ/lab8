package gui.controllers;

import Client.Commander;
import Client.Main;
import Commands.*;
import content.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
    public MenuItem changeUserButton;
    public Button helpButton;
    public Button infoButton;
    public Button showButton;
    public Button addButton;
    public Button clearButton;
    public Button removeLastButton;
    public Button averageOfLivingSpaceButton;
    public Button maxByHouseButton;
    public Button updateButton;
    public Button removeByIdButton;
    public Button removeAtButton;
    public Button executeScriptButton;
    public Button filterLessThanViewButton;
    public Button exitButton;
    public Menu settingsMenu;
    public Tab listOfFlats;
    public Tab visualization;
    public Pane canvas;

    Command command;
    Thread thread = new Thread(new Shower());

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
        synchronized (this) {
            try {
                getConnector().send(new CommandShow());
                ObservableList<Flat> list = FXCollections.observableList(getConnector().receive());
                table.setItems(list);
                lostConnLabel.setVisible(false);
            } catch (ClassCastException e) {
                lostConnLabel.setVisible(true);
            }
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

        try {
            thread.start();
        }catch (IllegalThreadStateException ignored){
            //thread is already running
        }

        ResourceBundle bundle = Main.getBundle();
        settingsMenu.setText(bundle.getString("settings"));
        languageMenu.setText(bundle.getString("language"));
        changeUserButton.setText(bundle.getString("change_user"));
        helpButton.setText(bundle.getString("help"));
        infoButton.setText(bundle.getString("info"));
        showButton.setText(bundle.getString("show"));
        addButton.setText(bundle.getString("add"));
        clearButton.setText(bundle.getString("clear"));
        removeLastButton.setText(bundle.getString("remove_last"));
        averageOfLivingSpaceButton.setText(bundle.getString("average"));
        maxByHouseButton.setText(bundle.getString("max"));
        updateButton.setText(bundle.getString("update"));
        removeByIdButton.setText(bundle.getString("remove_by_id"));
        removeAtButton.setText(bundle.getString("remove_at"));
        executeScriptButton.setText(bundle.getString("execute_script"));
        filterLessThanViewButton.setText(bundle.getString("filter"));
        exitButton.setText(bundle.getString("exit"));
        listOfFlats.setText(bundle.getString("list"));
        visualization.setText(bundle.getString("visualization"));


    }


    public void add(ActionEvent actionEvent) {
        new AddWindowController().setStage(changeWindow("/gui/scenes/add.fxml", stage));
        thread.interrupt();
    }

    public void update(ActionEvent actionEvent) {
        commandUpdate = new CommandUpdate();

        if (commandUpdate.validate(updateField.getText())) {
            if (table.getItems().stream()
                    .anyMatch(flat -> flat.getId() == Integer.parseInt(updateField.getText()))) {

                AddWindowController.setUpdatedFlat(table.getItems().stream()
                        .filter(flat -> flat.getId() == Integer.parseInt(updateField.getText()))
                        .findFirst().get());
                new AddWindowController().setStage(changeWindow("/gui/scenes/add.fxml", stage));
                thread.interrupt();

            } else showWindow(200, 500, "Элемента с указанным id не найдено.", Color.BLACK);

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
        if (command.validate(path)) {
            getConnector().send(command);
            Object answer = getConnector().receive();
            do {
                if (answer.getClass().getName().equals("".getClass().getName()))
                    showWindow(400, 800, (String) answer, Color.BLACK);
                else show(null);
                answer = getConnector().receive();
            } while (!answer.toString().startsWith("Server") &&
                    !answer.toString().startsWith("====  Скрипт " + path) &&
                    !answer.toString().startsWith("Файл"));
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

    public void changeUser(ActionEvent actionEvent) {
        new StartWindowController().setStage(changeWindow("/gui/scenes/start.fxml", stage));
        thread.interrupt();

    }

    public void setRussian(ActionEvent actionEvent) {
        Main.setBundle(ResourceBundle.getBundle("bundles.Resources"));
        initialize(null,Main.getBundle());
    }

    public void setEnglish(ActionEvent actionEvent) {
        Main.setBundle(ResourceBundle.getBundle("bundles.Resources_en_CA"));
        initialize(null,Main.getBundle());
    }

    public void setAlbanian(ActionEvent actionEvent) {
        Main.setBundle(ResourceBundle.getBundle("bundles.Resources_sq"));
        initialize(null,Main.getBundle());
    }

    public void setSlovak(ActionEvent actionEvent) {
        Main.setBundle(ResourceBundle.getBundle("bundles.Resources_sk"));
        initialize(null,Main.getBundle());
    }

    public void visualiseAll(Event event) {
        for (Flat flat: table.getItems()) {
            Circle circle = new Circle(flat.getCoordX(),flat.getCoordY(),
                    (flat.getArea())/5,Color.color(
                    ((double) Math.abs(flat.getUser().hashCode()-111)%10)/10,
                    ((double) Math.abs(flat.getUser().hashCode()-333)%10)/10,
                    ((double) Math.abs(flat.getUser().hashCode()-555)%10)/10));
            circle.setStroke(Paint.valueOf("BLACK"));
            circle.setStrokeWidth(circle.getRadius()/10);
            canvas.getChildren().add(circle);
        }
    }

    public List<Flat> getNewElements(List<Flat> serverList){
        List<Flat> temp = new ArrayList<>(serverList);
        if (!table.getItems().containsAll(serverList)){
            temp.retainAll(table.getItems());
            serverList.removeAll(temp);
            return serverList;
        }
        return null;
    }

    public List<Flat> getDeletedElements(List<Flat> serverList){
        List<Flat> result = new ArrayList<>(table.getItems());
        List<Flat> temp = new ArrayList<>(table.getItems());
        if (!serverList.containsAll(table.getItems())){
            temp.retainAll(serverList);
            result.removeAll(temp);
            return result;
        }
        return null;
    }

    class Shower implements Runnable {

        @Override
        public void run() {
            try {
                while (true) {
                    show(null);
                    Thread.sleep(3000);
                }
            } catch (InterruptedException ignored) { }
        }
    }
}
