package Main;

import content.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedList;

public class DatabaseHandler {
    private final String URL;
    private final String username;
    private final String password;
    private Connection connection;
    private static final String ADD_USER_REQUEST = "INSERT INTO USERS (username, password) VALUES (?, ?)";
    private static final String UPDATE_FLAT_REQUEST = "UPDATE FLATS SET name = ?, coordX = ?, coordY = ?, creationDate = ?, area = ?, numberOfRooms = ?, livingSpace = ?, view = ?, transport = ?, house_name = ?, house_year = ?, house_numberOfFlatsOnFloor = ? WHERE id = ?";
    private static final String ADD_NEW_FLAT_REQUEST = "INSERT INTO FLATS (name, coordX, coordY, creationDate, area, numberOfRooms, livingSpace, view, transport, house_name, house_year, house_numberOfFlatsOnFloor, username) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String CHECK_USER_REQUEST = "SELECT * FROM USERS WHERE username = ?";
    private static final String FLATS_REQUEST = "SELECT * FROM FLATS";
    private static final String USER_BY_ID_REQUEST = "SELECT * FROM FLATS WHERE id = ?";
    private static final String LOGIN_USER_REQUEST = "SELECT * FROM USERS WHERE username = ? AND password = ?";
    private static final String CLEAR_REQUEST = "DELETE FROM FLATS WHERE username = ?";
    private static final String DELETE_REQUEST = "DELETE FROM FLATS WHERE id = ?";
    private static final String GET_BY_ID_REQUEST = "SELECT * FROM FLATS WHERE id = ?";
    private static final String GET_NUMERATED_REQUEST = "SELECT row_number() over (), * FROM flats";




    public DatabaseHandler(String URL, String username, String password) {
        this.URL = URL;
        this.username = username;
        this.password = password;
    }

    public void connectToDatabase() {
        try {
            connection = DriverManager.getConnection(URL, username, password);
            System.out.println("Подключение к базе данных успешно.");
        } catch (SQLException e) {
            System.err.println("Ошибка подключения к базе данных. Завершение работы.");
            System.exit(-1);
        }
    }

    public void registerUser(String username, String password) throws SQLException {
        PreparedStatement addstatement = connection.prepareStatement(ADD_USER_REQUEST);
        addstatement.setString(1,username);
        addstatement.setString(2,password);
        addstatement.executeUpdate();
        addstatement.close();
    }

    public boolean loginUser(String username, String password) throws SQLException {
        PreparedStatement loginstatement = connection.prepareStatement(LOGIN_USER_REQUEST);
        loginstatement.setString(1,username);
        loginstatement.setString(2,password);
        ResultSet result = loginstatement.executeQuery();
        if (result.next()) {
            loginstatement.close();
            return true;
        }
        loginstatement.close();
        return false;
    }


    public boolean userExists(String username) throws SQLException {
        PreparedStatement checkstatement = connection.prepareStatement(CHECK_USER_REQUEST);
        checkstatement.setString(1,username);
        ResultSet result = checkstatement.executeQuery();
        if (result.next()) {
            checkstatement.close();
            return true;
        }
        checkstatement.close();
        return false;

    }

    public LinkedList<Flat> loadCollectionFromDB(){
        LinkedList<Flat> collection = new LinkedList<>();
        try {
            PreparedStatement joinStatement = connection.prepareStatement(FLATS_REQUEST);
            ResultSet result = joinStatement.executeQuery();

            while (result.next()){
                Flat flat = extractFlatFromResult(result);
                collection.add(flat);
            }

            joinStatement.close();
            System.out.println("Коллекция успешно загружена из базы данных. Количество элементов: " + collection.size());
        }catch (SQLException e){
            System.out.println("Произошла ошибка при загрузке коллекции из базы данных. Завершение работы.");
            e.printStackTrace();
            System.exit(-1);
        }
        return collection;
    }

    public void saveCollectionToDB(LinkedList<Flat> collection){
        collection.forEach(this::addFlatToDB);
    }

    public void updateCollectionToDB(LinkedList<Flat> collection){
        collection.forEach(this::updateFlatToDB);
    }

    public void addFlatToDB(Flat flat) {
        try {
            PreparedStatement saveStatement = connection.prepareStatement(ADD_NEW_FLAT_REQUEST);
            saveStatement.setString(1, flat.getName());
            saveStatement.setFloat(2, flat.getCoordinates().getX());
            saveStatement.setLong(3, flat.getCoordinates().getY());
            saveStatement.setDate(4, Date.valueOf(flat.getCreationDate().toLocalDate()));
            saveStatement.setLong(5, flat.getArea());
            saveStatement.setInt(6, flat.getNumberOfRooms());
            saveStatement.setLong(7, flat.getLivingSpace());
            saveStatement.setString(8, flat.getView().toString());
            saveStatement.setString(9, flat.getTransport().toString());
            saveStatement.setString(10, flat.getHouse().getName());
            saveStatement.setInt(11, flat.getHouse().getYear());
            saveStatement.setInt(12, flat.getHouse().getNumberOfFlatsOnFloor());
            saveStatement.setString(13, flat.getUser());
            saveStatement.executeUpdate();
            saveStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateFlatToDB(Flat flat){
        try {
            PreparedStatement saveStatement = connection.prepareStatement(UPDATE_FLAT_REQUEST);
            saveStatement.setString(1, flat.getName());
            saveStatement.setFloat(2, flat.getCoordinates().getX());
            saveStatement.setLong(3, flat.getCoordinates().getY());
            saveStatement.setDate(4, Date.valueOf(flat.getCreationDate().toLocalDate()));
            saveStatement.setLong(5, flat.getArea());
            saveStatement.setInt(6, flat.getNumberOfRooms());
            saveStatement.setLong(7, flat.getLivingSpace());
            saveStatement.setString(8, flat.getView().toString());
            saveStatement.setString(9, flat.getTransport().toString());
            saveStatement.setString(10, flat.getHouse().getName());
            saveStatement.setInt(11, flat.getHouse().getYear());
            saveStatement.setInt(12, flat.getHouse().getNumberOfFlatsOnFloor());
            saveStatement.setLong(13,flat.getId());
            saveStatement.executeUpdate();
            saveStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getUserById(long id) throws SQLException {
        String username;
        PreparedStatement getstatement = connection.prepareStatement(USER_BY_ID_REQUEST);
        getstatement.setLong(1,id);
        ResultSet result = getstatement.executeQuery();
        if (result.next()) {
            username = result.getString("username");
            getstatement.close();
            return username;
        }
        getstatement.close();
        return null;

    }

    public Flat extractFlatFromResult(ResultSet result) throws SQLException {
        Flat flat = new Flat(result.getInt(1),
                        result.getString(2),
                        new Coordinates(result.getFloat(3), result.getInt(4)),
                        LocalDateTime.of(result.getDate(5).toLocalDate(), LocalTime.of(0,0)), //TODO
                        result.getLong(6),
                        result.getInt(7),
                        result.getInt(8),
                        View.valueOf(result.getString(9)),
                        Transport.valueOf(result.getString(10)),
                        new House(result.getString(11), result.getInt(12),result.getInt(13)));
        flat.setUser(result.getString("username"));
        return flat;
    }

    public void deleteByUsername(String username) throws SQLException {
        PreparedStatement clearstatement = connection.prepareStatement(CLEAR_REQUEST);
        clearstatement.setString(1,username);
        clearstatement.executeUpdate();
        clearstatement.close();
    }

    public boolean deleteById(String username, long id) throws SQLException {
        PreparedStatement getstatement = connection.prepareStatement(GET_BY_ID_REQUEST);
        getstatement.setLong(1,id);
        ResultSet result = getstatement.executeQuery();
        if (result.next()) {
            if (result.getString("username").equals(username)) {
                PreparedStatement deletestatement = connection.prepareStatement(DELETE_REQUEST);
                deletestatement.setLong(1, id);
                deletestatement.executeUpdate();
                deletestatement.close();
                getstatement.close();
                return true;
            }
        }
        getstatement.close();
        return false;
    }

    public String removeAt(String username, int index) {
        try(PreparedStatement getstatement = connection.prepareStatement(GET_NUMERATED_REQUEST);) {
            ResultSet result = getstatement.executeQuery();
            while (result.next()) {
                if (result.getInt("row_number") == index) {
                    if (deleteById(username, result.getInt("id"))) {
                        return "Элемент успешно удалён.";
                    }
                    return "Элемент принадлежит не вам.";
                }
            }
            return "Элемента с указанным номером не существует.";
        }catch (SQLException e){
            e.printStackTrace();
            return "Ошибка доступа к базе данных.";
        }
    }

    public String removeLast(String username){
        try(PreparedStatement getstatement = connection.prepareStatement(GET_NUMERATED_REQUEST);) {
            ResultSet result = getstatement.executeQuery();
            if(!result.next())return "Коллекция пуста.";

            while (true){
                int id = result.getInt("id");
                if (!result.next()) {
                    if(deleteById(username, id)){
                        return "Элемент успешно удален.";
                    }
                    return "Элемент принадлежит не вам.";
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
            return "Ошибка доступа к базе данных.";
        }
    }
}
