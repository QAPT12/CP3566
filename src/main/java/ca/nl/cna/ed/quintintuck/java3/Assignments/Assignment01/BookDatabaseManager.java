package ca.nl.cna.ed.quintintuck.java3.Assignments.Assignment01;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import ca.nl.cna.ed.quintintuck.java3.JDBC.MariaDBProperties;

import javax.xml.transform.Result;

public class BookDatabaseManager {
    public static final String DatabaseName= "books";
    private static final String CONNECTION_STRING = MariaDBProperties.DATABASE_URL + DatabaseName;

    private Connection createConnection() throws SQLException {
        return DriverManager.getConnection(CONNECTION_STRING, MariaDBProperties.DATABASE_USER, MariaDBProperties.DATABASE_PASSWORD);
    }

    public void addBook(Book book) {
        String insertBookQuery = String.format("INSERT INTO titles VALUES ('%s', '%s', '%s', '%s');",
                book.getIsbn(),
                book.getTitle(),
                book.getCopyright(),
                book.getEditionNumber());

        executeQuery(insertBookQuery);

        for (Author author : book.getAuthorList()) {
            String insertBookAuthorRelationshipQuery = String.format("INSERT INTO authorISBN VALUES (%s, %s);",
                    author.getAuthorID(),
                    book.getIsbn());

            executeQuery(insertBookAuthorRelationshipQuery);
        }
    }

    public void addAuthor(Author author) {
        String insertAuthorQuery = String.format("INSERT INTO authors (firstName, lastName) values ('%s', '%s');",
                author.getFirstName(),
                author.getLastName());

        executeQuery(insertAuthorQuery);
    }

    public void updateDatabase(String table,
                               String column,
                               String updatedValue,
                               String keyColumn,
                               String oldKeyValue) throws SQLException {
        String Query = String.format("Update %s set %s = '%s' where %s = '%s'",
                table, column, updatedValue, keyColumn, oldKeyValue);
        executeQuery(Query);
    }

    public ResultSet selectLibrary() {
        String Query = "SELECT t.isbn, t.title, t.editionNumber, t.copyright, a.FirstName,  a.lastName FROM titles t JOIN authorISBN ai ON t.isbn = ai.isbn JOIN authors a ON ai.authorID = a.authorID;";
        return executeSelectQuery(Query);
    }

    public ResultSet selectAllAuthors() {
        String Query = "SELECT * FROM authors;";
        return executeSelectQuery(Query);
    }

    public ResultSet selectAllBooks() {
        String Query = "SELECT * FROM titles;";
        return executeSelectQuery(Query);
    }

    public ResultSet selectISBNs(){
        String Query = "SELECT * FROM authorISBN;";
        return executeSelectQuery(Query);
    }

    public ResultSet selectIndividualRow(String table, String comparator, String value) {
        String Query = String.format("SELECT * FROM %s WHERE %s = '%s';", table, comparator, value);
        return executeSelectQuery(Query);
    }

    private ResultSet executeSelectQuery(String query) {
        try (Connection conn = createConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            return ps.executeQuery();
        } catch (SQLException e) {
            System.err.println("Failed to execute query: " + query);
            e.printStackTrace();
            return null;
        }
    }

    private void executeQuery(String query) {
        try (Connection conn = createConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
