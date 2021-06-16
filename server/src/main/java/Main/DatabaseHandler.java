package Main;

import content.*;
import tools.Checker;
import tools.LocalDateTimeAdapter;
import tools.Reconnector;
import tools.ServerLogger;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class DatabaseHandler {
    private final String URL;
    private final String username;
    private final String password;
    private Connection connection;
    private static final String ADD_USER_REQUEST = "INSERT INTO USERS (username, password) VALUES (?, ?)";
    private static final String UPDATE_FLAT_REQUEST = "UPDATE FLATS SET name = ?, coordx = ?, coordy = ?, creationdate = ?, area = ?, numberofrooms = ?, livingspace = ?, view = ?, transport = ?, house_name = ?, house_year = ?, house_numberofflatsonfloor = ? WHERE id = ?";
    private static final String ADD_NEW_FLAT_REQUEST = "INSERT INTO FLATS (name, coordX, coordY, creationDate, area, numberOfRooms, livingSpace, view, transport, house_name, house_year, house_numberOfFlatsOnFloor, username) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String CHECK_USER_REQUEST = "SELECT * FROM USERS WHERE username = ?";
    private static final String FLATS_REQUEST = "SELECT * FROM FLATS";
    private static final String USER_BY_ID_REQUEST = "SELECT * FROM FLATS WHERE id = ?";
    private static final String LOGIN_USER_REQUEST = "SELECT * FROM USERS WHERE username = ? AND password = ?";
    private static final String CLEAR_REQUEST = "DELETE FROM FLATS WHERE username = ?";
    private static final String DELETE_REQUEST = "DELETE FROM FLATS WHERE id = ?";
    private static final String GET_BY_ID_REQUEST = "SELECT * FROM FLATS WHERE id = ?";
    private static final String GET_NUMERATED_REQUEST = "SELECT row_number() over (), * FROM flats";
    private static final String GET_DATE_REQUEST = "SELECT to_char(creationdate,'dd.mm.yyyy,HH24:mi:ss') from flats where id = ?";




    public DatabaseHandler(String URL, String username, String password) {
        this.URL = URL;
        this.username = username;
        this.password = password;
    }

    public void connectToDatabase() throws SQLException {
        connection = DriverManager.getConnection(URL, username, password);
        System.out.println("Подключение к базе данных успешно.");

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

    public List<Flat> loadCollectionFromDB(){
        List<Flat> collection = Collections.synchronizedList(new LinkedList<>());
        try {
            PreparedStatement joinStatement = connection.prepareStatement(FLATS_REQUEST);
            ResultSet result = joinStatement.executeQuery();

            while (result.next()) {
                if (validateFlatFromDB(result)) {
                    Flat flat = extractFlatFromResult(result);
                    collection.add(flat);
                } else
                    System.err.printf("Ошибка. Квартира с id = %d не может быть загружена из БД, одно или несколько полей указаны в недопустимом формате/диапазоне.\n", result.getInt("id"));

            }

            joinStatement.close();
        }catch (SQLException e){
            System.err.println("Произошла ошибка при загрузке коллекции из базы данных.");
            ServerLogger.logger.error("Ошибка доступа к базе", e);
            //System.exit(-1);
            collection = null;
            new Thread(new Reconnector(this)).start();
        }
        return collection;
    }

    public void saveCollectionToDB(List<Flat> collection){
        collection.forEach(this::addFlatToDB);
    }

    public void addFlatToDB(Flat flat) {
        try {
            PreparedStatement saveStatement = connection.prepareStatement(ADD_NEW_FLAT_REQUEST);
            saveStatement.setString(1, flat.getName());
            saveStatement.setFloat(2, flat.getCoordinates().getX());
            saveStatement.setLong(3, flat.getCoordinates().getY());
            saveStatement.setTimestamp(4, Timestamp.valueOf(flat.getCreationDate()));
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
            System.err.println("Ошибка доступа к базе данных.");
            ServerLogger.logger.error("Ошибка доступа к базе", e);
        }
    }

    public void updateFlatToDB(Flat flat){
        try {
            PreparedStatement saveStatement = connection.prepareStatement(UPDATE_FLAT_REQUEST);
            saveStatement.setString(1, flat.getName());
            saveStatement.setFloat(2, 300F);
            saveStatement.setLong(3, flat.getCoordinates().getY());
            saveStatement.setTimestamp(4, Timestamp.valueOf(flat.getCreationDate()));
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
        Transport transport;
        try {
            transport = Transport.valueOf(result.getString(10));
        } catch (NullPointerException e) {
            transport = null;
        }

        PreparedStatement statement = connection.prepareStatement(GET_DATE_REQUEST);
        statement.setInt(1, result.getInt("id"));
        ResultSet dateResult = statement.executeQuery();
        dateResult.next();
        LocalDateTime dateTime = LocalDateTimeAdapter.unmarshal(dateResult.getString("to_char"));
        statement.close();

        Flat flat = new Flat(result.getInt(1),
                result.getString(2),
                new Coordinates(result.getFloat(3), result.getInt(4)),
                dateTime,
                result.getLong(6),
                result.getInt(7),
                result.getInt(8),
                View.valueOf(result.getString(9)),
                transport,
                new House(result.getString(11), result.getInt(12), result.getInt(13)));
        flat.setUser(result.getString("username"));
        return flat;
    }

    public boolean validateFlatFromDB(ResultSet result) throws SQLException {
        return result.getInt("id") >= 0 &&
                !Checker.isNotString(result.getString("name")) &&
                result.getInt("coordy") <= 368 &&
                result.getLong("area") >= 0 &&
                result.getInt("numberofrooms") >= 0 &&
                Checker.isView(result.getString("view")) &&
                !(result.getString("transport") != null &
                        !Checker.isTransport(result.getString("transport"))) &&
                !Checker.isNotString(result.getString("house_name")) &&
                result.getInt("house_year") >= 0 &&
                result.getInt("house_numberofflatsonfloor") >= 0;
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

    public String removeAt(String username, int index) throws SQLException {
        PreparedStatement getstatement = connection.prepareStatement(GET_NUMERATED_REQUEST);
        ResultSet result = getstatement.executeQuery();
        while (result.next()) {
            if (result.getInt("row_number") == index+1) {
                if (deleteById(username, result.getInt("id"))) {
                    getstatement.close();
                    return "Элемент успешно удалён.";
                }
                getstatement.close();
                return "Элемент принадлежит не вам.";
            }
        }
        getstatement.close();
        return "Элемента с указанным номером не существует.";

    }

    public String removeLast(String username) throws SQLException {
        PreparedStatement getstatement = connection.prepareStatement(GET_NUMERATED_REQUEST);
        ResultSet result = getstatement.executeQuery();
        if (!result.next()) {
            getstatement.close();
            return "Коллекция пуста.";
        }

        while (true) {
            int id = result.getInt("id");
            if (!result.next()) {
                if (deleteById(username, id)) {
                    getstatement.close();
                    return "Элемент успешно удален.";
                }
                getstatement.close();
                return "Элемент принадлежит не вам.";
            }
        }
    }

}
