package FriendsGroup.pro;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class PostgresDB {

    String nameDatabase;
    String loginDatabase;
    String passwordDatabase;

//    public void setPasswordDatabase(String passwordDatabase) {
//        this.passwordDatabase = passwordDatabase;
//    }

//    void setNameDatabase(String nameDB) {
//        nameDatabase = nameDB;
//    }
//
//    public String getNameDatabase() {
//        return nameDatabase;
//    }
//
//    void setLoginDatabase(String nameDB) {
//        nameDatabase = nameDB;
//    }
//
//    void setLoginDatabaseDB(String nameDB) {
//        nameDatabase = nameDB;
//    }

//    Boolean initDatabase(String nameDB, String loginDB, String  passwordDB) {
//        try {
//            try {
//                Class.forName( "org.postgresql.Driver");
//            } catch (ClassNotFoundException e) {
//                System.out.println("PostgreSQL JDBC Driver не найден.");
//                e.printStackTrace();
//                return false;
//            }
//
//            String url = "jdbc:postgresql://localhost:5432/" + nameDB;
//            Connection con = DriverManager.getConnection(url, loginDB, passwordDB);
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

//    void testDatabase(String nameDB, String loginDB, String  passwordDB, String nameTable, String nameColumn) {
//            String url = "jdbc:postgresql://localhost:5432/" + nameDB;
//            Connection con = DriverManager.getConnection(url, loginDB, passwordDB);
//            try {
//                Statement stmt = con.createStatement();
//                ResultSet rs = stmt.executeQuery("SELECT " + nameColumn + " FROM " + nameTable);
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

//    String readDatabase(String nameDB, String loginDB, String  passwordDB, String nameTable, String nameColumn) {
//        try {
//            try {
//                Class.forName( "org.postgresql.Driver");
//            } catch (ClassNotFoundException e) {
//                System.out.println("PostgreSQL JDBC Driver не найден.");
//                e.printStackTrace();
//                return;
//            }
//
//            String url = "jdbc:postgresql://localhost:5432/" + nameDB;
//
//            Connection con = DriverManager.getConnection(url, loginDB, passwordDB);
//            try {
//                Statement stmt = con.createStatement();
//                ResultSet rs = stmt.executeQuery("SELECT " + nameColumn + " FROM " + nameTable);
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

