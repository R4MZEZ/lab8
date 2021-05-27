package tools;

import Main.DatabaseHandler;

import java.sql.SQLException;

public class Reconnector implements Runnable{
    private final DatabaseHandler databaseHandler;
    private boolean stop;

    public Reconnector(DatabaseHandler databaseHandler) {
        this.databaseHandler = databaseHandler;
    }

    @Override
    public void run() {
        while (!stop) {
            try {
                databaseHandler.connectToDatabase();
                System.out.println("Успешное переподключение к базе данных.");
                stop = true;
            } catch (SQLException e) {
                System.err.println("Неудачная попытка подключения к БД...");
                synchronized (this) {
                    try {
                        wait(5000);
                    } catch (InterruptedException ignored) {
                    }
                }
            }
        }
        stop = false;
    }
}
