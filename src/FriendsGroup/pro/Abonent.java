package FriendsGroup.pro;

public class Abonent {

    String getNameAbonent(String Number) {
        String response;

        // База данных
        PostgresDB database = new PostgresDB();
        database.setDatabaseOptions("postgres", "postgres");
        response = database.getNameOfAbonent(Number);

        if (Number.compareTo("0") == 0) {
            response =  "Без сим-карты";
        }

        if (response == null || response.isEmpty()) {
            response = Number;
        }


        return response;
    }

}
