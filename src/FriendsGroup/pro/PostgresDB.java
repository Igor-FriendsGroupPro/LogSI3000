package FriendsGroup.pro;

import java.sql.*;

public class PostgresDB {

    /** Параметры доступа к базе данных */
    private String nameDatabase;
    private String loginDatabase;
    private String passwordDatabase;
    private String urlDatabase;

    /** Установка параметров базы данных
     * @param loginDatabase Логин для доступа к БД
     * @param passwordDatabase Пароль для доступа к БД
     * */
    public void setDatabaseOptions(String loginDatabase, String passwordDatabase) {
        // Имя БД
        nameDatabase = "LogSI3000";
        // Адрес БД
        urlDatabase = "jdbc:postgresql://localhost:5432/";

        this.loginDatabase = loginDatabase;
        this.passwordDatabase = passwordDatabase;
    }

    /** Геттеры параметров БД */
    public String getNameDatabase() {return nameDatabase;}
    public String getLoginDatabase() {return loginDatabase;}
    public String getPasswordDatabase() {return passwordDatabase;}
    public String getUrlDatabase() {return urlDatabase;}

    /** Соединение с базой данных
     * @return Возвращает соединение с БД
     * */
    private Connection getDBConnectionPostgres() {
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
            dbConnection = getDBConnectionPostgres();
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
            dbConnection = getDBConnectionPostgres();
            statement = dbConnection.createStatement();

            // выполнить SQL запрос
            ResultSet resultString = statement.executeQuery(exStringSQL);
            while (resultString.next()) {
                response = resultString.getString("response");
            }

            //resultString.first();
            //response = resultString.getString("response");
            dbConnection.close();
            return response;

        } catch (SQLException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
        return response;

    }

/*    // Выполнение запроса SQL запроса с получением результата
    public ResultSet ExecuteQueryFull(String exStringSQL) {
        Connection dbConnection;
        Statement statement;
        String response = null;

        try {
            // Соединение с базой данных
            dbConnection = getDBConnectionPostgres();
            statement = dbConnection.createStatement();

            // выполнить SQL запрос
            ResultSet resultSet = statement.executeQuery(exStringSQL);

            dbConnection.close();
            return resultString;

        } catch (SQLException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
        return null;

    }
*/
    /** Создание таблицы звонков
     * @param nameTable Имя таблицы
     * */
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

    /** Очистка таблицы
     * @param nameTable Имя таблицы
     * */
    public void clearTable(String nameTable){
        // выполнить SQL запрос
        ExecuteString("DELETE FROM " + nameTable);
    }

    // Запись ЧНН
/*    public void writeHourOfMaximumLoad(String dateHML)
      String insertTableSQL = "(" +
SELECT COUNT(*) AS amountcalls, SUM(CallDuration) AS amountduration, hml, hmldate
FROM (

 SELECT MAKE_DATE(EXTRACT(YEAR FROM CallDate)::integer, EXTRACT(MONTH FROM CallDate)::integer, EXTRACT(DAY FROM CallDate)::integer) AS HMLdate,
	EXTRACT(HOUR FROM CallDate) AS HML, CallDuration,
    CASE WHEN (CN.SubscriberName IS NULL) AND (DN.SubscriberName IS NOT NULL) THEN 'Входящий'
    WHEN (CN.SubscriberName IS NOT NULL) AND (DN.SubscriberName IS NULL) THEN 'Исходящий'
    WHEN (CN.SubscriberName IS NOT NULL) AND (DN.SubscriberName IS NOT NULL) THEN 'Внутренний'
       ELSE 'Неизвестный' END AS calldirection
 FROM calls LEFT JOIN subscribers AS CN ON calls.CalledNumber = CN.SubscriberNumber
           LEFT JOIN subscribers AS DN ON calls.DestinationNumber = DN.SubscriberNumber
 WHERE CallDate BETWEEN '2018-07-01' AND '2018-07-02'
 ) AS TempTable

WHERE CallDirection = 'Входящий'
GROUP BY HMLDate, HML
ORDER BY amountcalls DESC
LIMIT 1
            "')";

    // выполнить SQL запрос
    //System.out.println(insertTableSQL);
    ExecuteString(insertTableSQL);

    )
**/
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
                "DestinationNumber" +
//                "CallDirection, " +
//                "CalledName, " +
//                "DestinationName" +
                ") VALUES (" +
                String.valueOf(ring.getCallID()) + ", '" +
                ring.getFileName() + "', " +
                "to_timestamp('" + ring.getDate() + " " + ring.getTime() + "', 'YYYY.MM.DD HH24:MI:SS'), " +
                String.valueOf(ring.getYear()) + ", " +
                String.valueOf(ring.getMonth()) + ", " +
                String.valueOf(ring.getDay()) + ", " +
                String.valueOf(ring.getHour()) + ", '" +
                String.valueOf(ring.getTime()) + "', " +
                String.valueOf(ring.getDuration()) + ", '" +
                ring.getDefiantNumber() + "', '" +
                ring.getCalledNumber() + //"', '" +
//                ring.getDirection() + "', '" +
//                ring.getCalledName() + "', '" +
//                ring.getDestinationName() +
                "')";

        // выполнить SQL запрос
        //System.out.println(insertTableSQL);
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
    String getNameOfAbonent(String NumberOfAbonent) {
        // null если нет в базе данных
        return ExecuteQuery("SELECT SubscriberName AS response FROM subscribers WHERE SubscriberNumber = '" + NumberOfAbonent + "'");
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

