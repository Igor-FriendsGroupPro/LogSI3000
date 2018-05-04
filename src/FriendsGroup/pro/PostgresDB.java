package FriendsGroup.pro;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class PostgresDB {

    private String nameDatabase;
    private String loginDatabase;
    private String passwordDatabase;
    private String urlDatabase;
    private String nameTableInDatabase;

    // Сеттеры
    public void setNameDatabase(String nameDatabase) {this.nameDatabase = nameDatabase;}
    public void setLoginDatabase(String loginDatabase) {this.loginDatabase = loginDatabase;}
    public void setPasswordDatabase(String passwordDatabase) {this.passwordDatabase = passwordDatabase;}
    public void setnameTableInDatabase(String nameTableInDatabase) {this.nameTableInDatabase = nameTableInDatabase;}
    public void setUrlDatabase(String urlDatabase) {
        urlDatabase = "jdbc:postgresql://localhost:5432/";
        this.urlDatabase = urlDatabase;
    }

    // Геттеры
    public String getNameDatabase() {return nameDatabase;}
    public String getLoginDatabase() {return loginDatabase;}
    public String getPasswordDatabase() {return passwordDatabase;}
    public String getNameTableInDatabase() {return nameTableInDatabase;}
    public String getUrlDatabase() {return urlDatabase;}

    // Тестирование подключения к базе данных
    Boolean testDatabase() {
        try {
            try {
                Class.forName( "org.postgresql.Driver");
            } catch (ClassNotFoundException e) {
                System.out.println("PostgreSQL JDBC Driver не найден.");
                e.printStackTrace();
                return false;
            }

            Connection con = DriverManager.getConnection(urlDatabase + nameDatabase, loginDatabase, passwordDatabase);
            try {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM " + nameTableInDatabase);
                rs.close();
                stmt.close();
            } finally {
                con.close();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Инициализация подключения к базе данных
//    void closeDatabase() {
//        try {
//            try {
//                Class.forName( "org.postgresql.Driver");
//            } catch (ClassNotFoundException e) {
//                System.out.println("PostgreSQL JDBC Driver не найден.");
//                e.printStackTrace();
//                return false;
//            }
//
//            Connection con = DriverManager.getConnection(urlDatabase + nameDatabase, loginDatabase, passwordDatabase);
//            try {
//                Statement stmt = con.createStatement();
//                stmt.close();
//                return true;
//            } finally {
//                con.close();
////                return false;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }

    public void ClearTable(){
        try {
            Connection con = DriverManager.getConnection(urlDatabase + nameDatabase, loginDatabase, passwordDatabase);
            try {
                Statement stmt = con.createStatement();
                stmt.executeUpdate("DELETE FROM " + nameTableInDatabase);
                stmt.close();
            } finally {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    String writeDatabase(String lineForWrite) {
//        try {
//            Connection con = DriverManager.getConnection(urlDatabase + nameDatabase, loginDatabase, passwordDatabase);
//            try {
//                Statement stmt = con.createStatement();
//                ResultSet rs = stmt.executeQuery("SELECT " + nameColumn + " FROM " + nameTableInDatabase);
//                stmt.executeUpdate()
//                while (rs.next()) {
//                    System.out.println(rs.getString(nameColumn));
//                }
//                rs.close();
//                stmt.close();
//            } finally {
//                con.close();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

}

