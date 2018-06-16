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

    // Выполнение строки SQL
    private void ExecuteString(String exStringSQL) {
        // Запись
        Connection dbConnection = null;
        Statement statement = null;

        try {
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();

            // выполнить SQL запрос
            statement.execute(exStringSQL);

        } catch (SQLException e) {
            System.out.println(exStringSQL);
            System.out.println(e.getMessage());
        } finally {
            try {
                statement.close();
                dbConnection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Выполнение запроса SQL запроса
    private ResultSet ExecuteQuery(String exStringSQL) {
        Connection dbConnection = null;
        Statement statement = null;

        try {
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();

            // выполнить SQL запрос
            return statement.executeQuery(exStringSQL);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                dbConnection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    // Создание таблицы звонков
    public void createTableCalls(String nameTable) throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS " + nameTable + " (" +
                "ПорядковыйНомерЗвонка NUMERIC(6), " +
                "ДатаВремяЗвонка timestamp NOT NULL, " +
                "Год NUMERIC(4), " +
                "Месяц NUMERIC(2), " +
                "День NUMERIC(2), " +
                "Час NUMERIC(2), " +
                "ВремяЗвонка VARCHAR(8), " +
                "ДлительностьЗвонка NUMERIC(5), " +
                "ВызывающийНомер VARCHAR(16), " + // DN
                "ИмяВызывающегоАбонента VARCHAR, " +
                "ВызываемыйНомер VARCHAR(16), " +
                "ИмяВызываемогоАбонента VARCHAR, " +
                "НаправлениеЗвонка VARCHAR, " +
                "PRIMARY KEY (ПорядковыйНомерЗвонка))";

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

    // Запись звонка
    public void writeCall(String nameTable, PhoneRing ring) {
        String insertTableSQL = "INSERT INTO " + nameTable + " (" +
                "ПорядковыйНомерЗвонка, " +
                "ИмяЛогФайла, " +
                "ДатаВремяЗвонка, " +
                "Год, " +
                "Месяц, " +
                "День, " +
                "Час, " +
                "ВремяЗвонка, " +
                "ДлительностьЗвонка, " +
                "ВызывающийНомер, " + // DN
                "ВызываемыйНомер, " +
                "НаправлениеЗвонка" +
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
                String.valueOf(ring.getDefiantNumber()) + ", " +
                String.valueOf(ring.getCalledNumber()) + ", '" +
                ring.getDirection() + "')";

        // выполнить SQL запрос
        ExecuteString(insertTableSQL);
    }

    // Создание таблицы абонентов
    public void createTableAbonent() throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS Subscribers (" +
                "Номер VARCHAR(16), " + // DN
                "Абонент VARCHAR, " +
                "PRIMARY KEY (Номер))";

        // выполнить SQL запрос
        ExecuteString(createTableSQL);
    }

    // Запись абонента
    public void writeAbonent(String NumberOfAbonent, String NameOfAbonent) {
        String insertTableSQL = "INSERT INTO Subscribers (" +
                "Номер, " + // DN
                "Абонент" +
                ") VALUES ('" +
                NumberOfAbonent + "', '" +
                NameOfAbonent + "')";

        // выполнить SQL запрос
        ExecuteString(insertTableSQL);
    }

    // Чтение имени абонента
    public String getNameOfAbonent(String NumberOfAbonent) {
        String selectTableSQL = "SELECT Абонент FROM subscribers WHERE Номер = '" + NumberOfAbonent + "' ";
        String response = "Неизвестный";
        try {
            // выполнить SQL запрос
            ResultSet ResultString = ExecuteQuery(selectTableSQL);

            while (ResultString.next()) {
                response = ResultString.getString("Абонент");
            }
            return response;

        } catch (SQLException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
        return response;
    }

    public long getIDLastCall () {
        String selectTableSQL = "SELECT MAX(ПорядковыйНомерЗвонка) FROM calls";
        long response = 0;
        try {
            // выполнить SQL запрос
            ResultSet ResultString = ExecuteQuery(selectTableSQL);

            while (ResultString.next()) {
                response = Long.parseLong(ResultString.getString("max"));
            }
            return response;

        } catch (SQLException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    return response;
    }

    public String getDateLastCall () {
        String selectTableSQL = "SELECT MAX(ДатаВремяЗвонка) FROM calls";
        String response = "";
        try {
            // выполнить SQL запрос
            ResultSet ResultString = ExecuteQuery(selectTableSQL);

            while (ResultString.next()) {
                response = ResultString.getString("max");
            }
            return response;

        } catch (SQLException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
        return response;
    }
}

