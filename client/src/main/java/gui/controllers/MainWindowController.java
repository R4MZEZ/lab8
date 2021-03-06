package gui.controllers;

import Client.Commander;
import Client.Main;
import Commands.*;
import content.Flat;
import content.Transport;
import content.View;
import javafx.animation.FadeTransition;
import javafx.animation.StrokeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import tools.FlatCircle;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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
    public Pane canvas;
    public ToggleButton visualizeButton;
    public Tab visualizationTab;
    public Label label = new Label();
    private final Lock lock = new ReentrantLock();
    public TableColumn house;

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
    private TableColumn<Flat, String> creationDate;

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
        lock.lock();
        try {
            getConnector().send(new CommandShow());
            ObservableList<Flat> list = FXCollections.observableList(getConnector().receive());
            table.setItems(list);
            lostConnLabel.setVisible(false);
        } catch (ClassCastException e) {
            lostConnLabel.setVisible(true);
        }
        lock.unlock();
    }

    public void clear(ActionEvent actionEvent) {
        getConnector().send(new CommandClear());
        showWindow(200, 500, getConnector().receive(), Color.BLACK);
    }

    public void remove_by_id(ActionEvent actionEvent) {
        command = new CommandRemoveById();
        if (command.validate(removeByIdField.getText())) {
            getConnector().send(command);
            showWindow(150, 900, getConnector().receive(), Color.BLACK);
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        coordX.setCellValueFactory(new PropertyValueFactory<>("coordX"));
        coordY.setCellValueFactory(new PropertyValueFactory<>("coordY"));
        creationDate.setCellValueFactory(new PropertyValueFactory<>("stringCreationDate"));
        area.setCellValueFactory(new PropertyValueFactory<>("area"));
        numberOfRooms.setCellValueFactory(new PropertyValueFactory<>("numberOfRooms"));
        livingSpace.setCellValueFactory(new PropertyValueFactory<>("livingSpace"));
        view.setCellValueFactory(new PropertyValueFactory<>("view"));
        transport.setCellValueFactory(new PropertyValueFactory<>("Transport"));
        house_name.setCellValueFactory(new PropertyValueFactory<>("house_name"));
        house_year.setCellValueFactory(new PropertyValueFactory<>("house_year"));
        numberOfFlatsOnFloor.setCellValueFactory(new PropertyValueFactory<>("house_numberOfFlatsOnFloor"));
        user.setCellValueFactory(new PropertyValueFactory<>("user"));

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
        visualizeButton.setText(bundle.getString("showButton"));
        visualizationTab.setText(bundle.getString("visualization"));
        id.setText(bundle.getString("id"));
        name.setText(bundle.getString("name"));
        creationDate.setText(bundle.getString("creationDate"));
        area.setText(bundle.getString("area"));
        numberOfRooms.setText(bundle.getString("number_of_rooms"));
        livingSpace.setText(bundle.getString("living_space"));
        view.setText(bundle.getString("view"));
        transport.setText(bundle.getString("transport"));
        house_name.setText(bundle.getString("house_name"));
        house_year.setText(bundle.getString("house_year"));
        numberOfFlatsOnFloor.setText(bundle.getString("number_of_flats"));
        user.setText(bundle.getString("user"));
        house.setText(bundle.getString("house"));

        show(null);
        try {
            canvas.getChildren().add(label);
        } catch (IllegalArgumentException ignored) {
        }
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

            } else showWindow(200, 500, "???????????????? ?? ?????????????????? id ???? ??????????????.", Color.BLACK);

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
                    !answer.toString().startsWith("====  ???????????? " + path) &&
                    !answer.toString().startsWith("????????"));
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
        initialize(null, Main.getBundle());
    }

    public void setEnglish(ActionEvent actionEvent) {
        Main.setBundle(ResourceBundle.getBundle("bundles.Resources_en_CA"));
        initialize(null, Main.getBundle());
    }

    public void setAlbanian(ActionEvent actionEvent) {
        Main.setBundle(ResourceBundle.getBundle("bundles.Resources_sq"));
        initialize(null, Main.getBundle());
    }

    public void setSlovak(ActionEvent actionEvent) {
        Main.setBundle(ResourceBundle.getBundle("bundles.Resources_sk"));
        initialize(null, Main.getBundle());
    }

    public void visualizeAll() {
        if (visualizeButton.isSelected()) {
            for (Flat flat : table.getItems()) {
                visualizeFlat(flat);
            }
            thread = new Thread(new Shower());
            thread.start();
        } else {
            canvas.getChildren().clear();
            thread.interrupt();
        }
    }

    public void visualizeFlat(Flat flat) {
        Color color = Color.color(
                ((double) Math.abs(flat.getUser().hashCode() - 111) % 10) / 10,
                ((double) Math.abs(flat.getUser().hashCode() - 333) % 10) / 10,
                ((double) Math.abs(flat.getUser().hashCode() - 555) % 10) / 10);
        FlatCircle circle = new FlatCircle(0, 0, (flat.getArea()) / 5, color);
        circle.setStrokeWidth(circle.getRadius() / 12);

        circle.setFlat(flat);

        circle.setOnMouseEntered(event -> {
            label.setText(flat.toString());
            label.setFont(Font.font(11));
            label.setLayoutX(flat.getCoordX() + 480);
            label.setLayoutY(flat.getCoordY() + 265);
            label.setVisible(true);
            label.toFront();
        });

        circle.setOnMouseExited(event -> label.setVisible(false));

        circle.setOnMouseClicked(event -> {
            updateField.setText(String.valueOf(circle.getFlat().getId()));
            removeByIdField.setText(String.valueOf(circle.getFlat().getId()));
            table.getSelectionModel().select(circle.getFlat());
            removeAtField.setText(String.valueOf(table.getSelectionModel().getSelectedIndex()));
        });

        StrokeTransition transition = new StrokeTransition(Duration.millis(1000), circle, color.darker(), color.brighter());
        transition.setCycleCount(-1);
        transition.setAutoReverse(true);

        TranslateTransition transition1 = new TranslateTransition(Duration.seconds(2), circle);
        transition1.setToX(flat.getCoordX() + 455);
        transition1.setByY(flat.getCoordY() + 265);

        DropShadow ds = new DropShadow();
        ds.setOffsetY(3.0);
        ds.setColor(Color.color(0.4, 0.4, 0.4));
        circle.setEffect(ds);

        Platform.runLater(() -> {
            canvas.getChildren().add(circle);
            transition.play();
            transition1.play();
        });
    }

    public void vanishFlat(Flat flat) {
        for (Node circle : canvas.getChildren()) {
            if (circle.getClass().equals(FlatCircle.class))
                if (((FlatCircle) circle).getFlat().getId() == flat.getId()) {
                    FadeTransition transition1 = new FadeTransition(Duration.seconds(3), circle);
                    transition1.setFromValue(1.0);
                    transition1.setToValue(0.0);
                    transition1.play();
                    transition1.setOnFinished(event -> canvas.getChildren().remove(circle));
                    return;
                }
        }
    }


    public List<Flat> getNewElements(List<Flat> serverList) {
        List<Flat> result = new ArrayList<>();
        for (Flat flat : serverList) {
            if (!table.getItems().contains(flat))
                result.add(flat);
        }
        return result;
    }

    public List<Flat> getDeletedElements(List<Flat> serverList) {
        List<Flat> result = new ArrayList<>();
        for (Flat flat : table.getItems()) {
            if (!serverList.contains(flat))
                result.add(flat);
        }
        return result;
    }

    class Shower implements Runnable {

        @Override
        public void run() {
            try {
                while (true) {
                    getConnector().send(new CommandShow());
                    List<Flat> serverList = getConnector().receive();
                    List<Flat> deletedFlats = getDeletedElements(serverList);
                    List<Flat> newFlats = getNewElements(serverList);
                    for (Flat flat : deletedFlats)
                        vanishFlat(flat);
                    for (Flat flat : newFlats)
                        visualizeFlat(flat);
                    show(null);
                    Thread.sleep(3000);
                }
            } catch (InterruptedException ignored) {
            }
        }

    }
}
