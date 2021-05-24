package Main;

import content.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.LinkedList;

public class DatabaseHandler {
    private String URL;
    private String username;
    private String password;
    private Connection connection;
    private static final String ADD_USER_REQUEST = "INSERT INTO USERS (username, password) VALUES (?, ?)";
    private static final String CHECK_USER_REQUEST = "SELECT * FROM USERS WHERE username = ?";
    private static final String JOIN_FLATS_HOUSES_REQUEST = "SELECT * FROM FLATS INNER JOIN HOUSES ON FLATS.house_id = HOUSES.id";


    public DatabaseHandler(String URL, String username, String password) {
        this.URL = URL;
        this.username = username;
        this.password = password;
    }

    public void con8nectToDatabase() {
        try {
            connection = DriverManager.getConnection(URL, username, password);
            System.out.println("Подключение к базе данных успешно.");
        } catch (SQLException e) {
            System.err.println("Ошибка подключения к базе данных. Завершение работы.");
            System.exit(-1);
        }
    }

    public boolean registerUser(String username, String password) throws SQLException {
        if (!userExists(username)) return false;
        PreparedStatement addstatement = connection.prepareStatement(ADD_USER_REQUEST);
        addstatement.setString(1,username);
        addstatement.setString(2,password);
        addstatement.executeUpdate();
        addstatement.close();
        return true;
    }

    public boolean userExists(String username) throws SQLException {
        PreparedStatement checkstatement = connection.prepareStatement(CHECK_USER_REQUEST);
        checkstatement.setString(1,username);
        ResultSet result = checkstatement.executeQuery();
        if (result.next()) {
            checkstatement.close();
            return true;
        };
        checkstatement.close();
        return false;

    }

    public LinkedList<Flat> loadCollectionFromDB(){
        LinkedList<Flat> collection = new LinkedList<>();
        try {
            PreparedStatement joinStatement = connection.prepareStatement(JOIN_FLATS_HOUSES_REQUEST);
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

    public Flat extractFlatFromResult(ResultSet result) throws SQLException {
        return new Flat(result.getInt(1),
                        result.getString(2),
                        new Coordinates(result.getFloat(3), result.getInt(4)),
                        LocalDateTime.of(result.getDate(5).toLocalDate(), result.getTime(5).toLocalTime()),
                        result.getLong(6),
                        result.getInt(7),
                        result.getInt(8),
                        View.valueOf(result.getString(9)),
                        Transport.valueOf(result.getString(10)),
                        new House(result.getString(12), result.getInt(13),result.getInt(14)));
    }
}
