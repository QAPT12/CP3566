package ca.nl.cna.ed.quintintuck.java3.JDBC.SimpleExample;

import ca.nl.cna.ed.quintintuck.java3.JDBC.DBProperties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class FunWithCreatingDBs {

    public static void main(String[] args) throws SQLException {
        //open a connection
        try(Connection conn = DriverManager.getConnection(
                DBProperties.DATABASE_URL, DBProperties.DATABASE_USER, DBProperties.DATABASE_PASSWORD
        )
    }



}
