package FriendsGroup.pro;

import java.sql.*;

public class PostgresDB {

    private String nameDatabase;
    private String loginDatabase;
    private String passwordDatabase;
    private String urlDatabase;

    // Сеттеры
    public void setParametrsDatabase(String loginDatabase, String passwordDatabase) {
        nameDatabase = "LogSI3000";
        this.nameDatabase = nameDatabase;
        this.loginDatabase = loginDatabase;
        this.passwordDatabase = passwordDatabase;
        urlDatabase = "jdbc:postgresql://localhost:5432/";
        this.urlDatabase = urlDatabase;
    }

    // Геттеры
    public String getNameDatabase() {return nameDatabase;}
    public String getLoginDatabase() {return loginDatabase;}
    public String getPasswordDatabase() {return passwordDatabase;}
    public String getUrlDatabase() {return urlDatabase;}

    // Соединение с базой данных
    private Connection getDBConnection() {
        Connection dbConnection = null;
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver не найден.");
            System.out.println(e.getMessage());
        }
        try {
            dbConnection = DriverManager.getConnection(getUrlDatabase() + getNameDatabase(), getLoginDatabase(), getPasswordDatabase());
            return dbConnection;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return dbConnection;
    }

    // Создание таблицы
    public void createTable(String nameTable) throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS " + nameTable + " (" +
                "ПорядковыйНомерЗвонка NUMERIC(6), " +
                "ДатаВремяЗвонка timestamp NOT NULL, " +
                "Год NUMERIC(4), " +
                "Месяц NUMERIC(2), " +
                "День NUMERIC(2), " +
                "Час NUMERIC(2), " +
                "ВремяЗвонка time, " +
                "ВызывающийНомер VARCHAR(11), " + // DN
                "ИмяВызывающегоАбонента VARCHAR, " +
                "ВызываемыйНомер VARCHAR(11), " +
                "ИмяВызываемогоАбонента VARCHAR, " +
                "PRIMARY KEY (ПорядковыйНомерЗвонка))";

        Connection dbConnection = null;
        Statement statement = null;

            try {
                dbConnection = getDBConnection();
                statement = dbConnection.createStatement();

                // выполнить SQL запрос
                statement.execute(createTableSQL);
                System.out.println("Таблица " + nameTable + " создана.");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            } finally {
                if (statement != null) {
                    statement.close();
                }
                if (dbConnection != null) {
                    dbConnection.close();
                }
            }
    }

    // Тестирование подключения к базе данных
    public boolean testDatabase() {
        try {
            Connection con = DriverManager.getConnection(urlDatabase + nameDatabase, loginDatabase, passwordDatabase);
            try {
                Statement stmt = con.createStatement();
                stmt.close();
            } finally {
                con.close();
                System.out.println("Доступ к базе успешно проверен!");
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Доступ к базе отсутствует!");
            return false;
        }
    }

    public void clearTable(String nameTable){
        try {
            Connection con = DriverManager.getConnection(urlDatabase + nameDatabase, loginDatabase, passwordDatabase);
            try {
                Statement stmt = con.createStatement();
                stmt.executeUpdate("DELETE FROM " + nameTable);
                stmt.close();
            } finally {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeCall(String nameTable, PhoneRing ring) {
        String insertTableSQL = "INSERT INTO " + nameTable + " (" +
                "ПорядковыйНомерЗвонка, " +
                "ДатаВремяЗвонка, " +
                "Год, " +
                "Месяц, " +
                "День, " +
                "Час, " +
                "ВремяЗвонка, " +
                "ВызывающийНомер, " + // DN
                "ИмяВызывающегоАбонента, " +
                "ВызываемыйНомер, " +
                "ИмяВызываемогоАбонента" +
                ") VALUES (" +
                String.valueOf(ring.getCallID()) + ", " +
                "to_timestamp('" + ring.getDate() + " " +ring.getTime() + "', 'YYYY.MM.DD HH24:MI:SS'), " +
                String.valueOf(ring.getYear()) + ", " +
                String.valueOf(ring.getMonth()) + ", " +
                String.valueOf(ring.getDay()) + ", " +
                String.valueOf(ring.getHour()) + ", " +
                String.valueOf(ring.getTime()) + ", " +
                String.valueOf(ring.getDefiantNumber()) + ", '" +
                String.valueOf(ring.getDefiantName()) + "', " +
                String.valueOf(ring.getCalledNumber()) + ", '" +
                String.valueOf(ring.getCalledName()) + "')";

        // Запись
        Connection dbConnection = null;
        Statement statement = null;

        try {
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();

            // выполнить SQL запрос
            System.out.println(insertTableSQL);
            statement.execute(insertTableSQL);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
//            if (statement != null) {
            try {
                statement.close();
                dbConnection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

