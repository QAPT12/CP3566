package ca.nl.cna.ed.quintintuck.java3.JDBC;

public class MariaDBProperties {
    public static final String DATABASE_URL = "jdbc:mariadb://localhost:3306/";

    public static final String DATABASE_USER = "root";

    public static final String DATABASE_PASSWORD = "root";

    public static final String DATABASE_URL_COMPLETE = "jdbc:mariadb://localhost:3306?user="
            + DATABASE_USER +"&password=" + DATABASE_PASSWORD;


}
