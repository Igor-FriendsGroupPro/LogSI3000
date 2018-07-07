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
//        this.nameDatabase = nameDatabase;
        urlDatabase = "jdbc:postgresql://localhost:5432/";
//        this.urlDatabase = urlDatabase;

        this.loginDatabase = loginDatabase;
        this.passwordDatabase = passwordDatabase;
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

    // Выполнение строки SQL без возврата результата
    private void ExecuteString(String exStringSQL) {
        // Запись
        Connection dbConnection = null;
        Statement statement = null;

//        System.out.println(exStringSQL);

        try {
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();

            // выполнить SQL строку
            statement.execute(exStringSQL);
            dbConnection.close();

        } catch (SQLException e) {
            System.out.println("Ошибка: " + e.getMessage());
        } finally {
            try {
                statement.close();
                dbConnection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Выполнение запроса SQL запроса с получением результата
    private String ExecuteQuery(String exStringSQL) {
        Connection dbConnection;
        Statement statement;
        String response = null;

        try {
            // Соединение с базой данных
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();

            // выполнить SQL запрос
            ResultSet resultString = statement.executeQuery(exStringSQL);
            while (resultString.next()) {
                response = resultString.getString("response");
            }

            dbConnection.close();
            return response;

        } catch (SQLException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
        return response;

    }

    // Создание таблицы звонков
    public void createTableCalls(String nameTable) throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS " + nameTable + " (" +
                "callid NUMERIC(6), " +
                "namelogfile VARCHAR, " +
                "calldate timestamp NOT NULL, " +
                "callyear NUMERIC(4), " +
                "callmonth NUMERIC(2), " +
                "callday NUMERIC(2), " +
                "callhour NUMERIC(2), " +
                "calltime VARCHAR(8), " +
                "callduration NUMERIC(5), " +
                "callednumber VARCHAR(16), " +
                "destinationnumber VARCHAR(16), " +
                "calldirection VARCHAR, " +
                "calledname VARCHAR, " +
                "destinationname VARCHAR, " +
                "PRIMARY KEY (callid))";

        // выполнить SQL запрос
        ExecuteString(createTableSQL);
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

    // Очистка таблицы
    public void clearTable(String nameTable){
        // выполнить SQL запрос
        ExecuteString("DELETE FROM " + nameTable);
    }

    // Запись звонка в базу данных
    public void writeCall(String nameTable, PhoneRing ring) {
        String insertTableSQL = "INSERT INTO " + nameTable + " (" +
                "CallID, " +
                "NameLogFile, " +
                "CallDate, " +
                "CallYear, " +
                "CallMonth, " +
                "CallDay, " +
                "CallHour, " +
                "CallTime, " +
                "CallDuration, " +
                "CalledNumber, " + // DN
                "DestinationNumber, " +
                "CallDirection, " +
                "CalledName, " +
                "DestinationName" +
                ") VALUES (" +
                String.valueOf(ring.getCallID()) + ", '" +
                ring.getFileName() + "', " +
                "to_timestamp('" + ring.getDate() + " " + ring.getTime() + "', 'YYYY.MM.DD HH24:MI:SS'), " +
                String.valueOf(ring.getYear()) + ", " +
                String.valueOf(ring.getMonth()) + ", " +
                String.valueOf(ring.getDay()) + ", " +
                String.valueOf(ring.getHour()) + ", '" +
                String.valueOf(ring.getTime()) + "', " +
                String.valueOf(ring.getDuration()) + ", " +
                ring.getDefiantNumber() + ", " +
                ring.getCalledNumber() + ", '" +
                ring.getDirection() + ", '" +
                ring.getCalledName() + ", '" +
                ring.getDestinationName() + "')";

        // выполнить SQL запрос
        ExecuteString(insertTableSQL);
    }

    // Создание таблицы абонентов
    public void createTableAbonent(String nameTableSuscribers) throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS " + nameTableSuscribers + " (" +
                "subscribernumber VARCHAR(16), " + // DN
                "subscribername VARCHAR, " +
                "PRIMARY KEY (subscribernumber))";

        // выполнить SQL запрос
        ExecuteString(createTableSQL);
    }

    // Запись абонента
    public void writeAbonent(String NumberOfAbonent, String NameOfAbonent) {
        String insertTableSQL = "INSERT INTO Subscribers (" +
                "SubscriberNumber, " +
                "SubscriberName" +
                ") VALUES ('" +
                NumberOfAbonent + "', '" +
                NameOfAbonent + "')";

        // выполнить SQL запрос
        ExecuteString(insertTableSQL);
    }

    // Чтение имени абонента
    public String getNameOfAbonent(String NumberOfAbonent) {
        // null если нет в базе данных
        return ExecuteQuery("SELECT SubscriberName AS response FROM subscribers WHERE Номер = '" + NumberOfAbonent + "'");
    }

    public long getIDLastCall (String nameTable) {
        String response = ExecuteQuery("SELECT MAX(CallID) AS response FROM " + nameTable);
        return Long.parseLong(response);
    }

    public String getFileNameLast (String nameTable) {
        return ExecuteQuery("SELECT MAX(NameLogFile) AS response FROM " + nameTable);
    }

    public boolean existEntry (String column, String value, String nameTable) {
        String response = ExecuteQuery("SELECT EXISTS( SELECT " + column + " FROM " + nameTable + " WHERE " + column + " = '" + value + "') AS response");
        return response.compareTo("t") == 0;
    }
}

