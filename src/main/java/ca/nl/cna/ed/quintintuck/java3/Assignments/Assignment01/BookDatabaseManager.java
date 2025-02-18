package ca.nl.cna.ed.quintintuck.java3.Assignments.Assignment01;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import ca.nl.cna.ed.quintintuck.java3.JDBC.MariaDBProperties;

import javax.xml.transform.Result;

/**
 * The BookDatabaseManager class is responsible for managing database operations related to books and authors.
 * It provides methods to add books, add authors, update database records, and query various data from the database.
 */
public class BookDatabaseManager {
    public static final String DatabaseName= "books";
    private static final String CONNECTION_STRING = MariaDBProperties.DATABASE_URL + DatabaseName;

    /**
     * Establishes a connection to the database using the connection string and credentials
     * specified in the {@code MariaDBProperties} class.
     *
     * @return a {@code Connection} object for interacting with the database.
     * @throws SQLException if a database access error occurs or the connection is invalid.
     */
    private Connection createConnection() throws SQLException {
        return DriverManager.getConnection(CONNECTION_STRING, MariaDBProperties.DATABASE_USER, MariaDBProperties.DATABASE_PASSWORD);
    }

    /**
     * Adds a book to the database by inserting the book's details and associating it with its authors.
     * This method inserts the book's ISBN, title, edition number, and copyright information
     * into the "titles" table, and also creates relationships in the "authorISBN" table between
     * the book and each author from the book's author list.
     *
     * @param book the {@code Book} object containing details of the book to be added to the database,
     *             including its ISBN, title, edition number, copyright information, and associated authors
     */
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

    /**
     * Adds an author to the database by inserting the author's first name and last name
     * into the "authors" table.
     *
     * @param author the {@code Author} object containing the first name and last name
     *               of the author to be added to the database
     */
    public void addAuthor(Author author) {
        String insertAuthorQuery = String.format("INSERT INTO authors (firstName, lastName) values ('%s', '%s');",
                author.getFirstName(),
                author.getLastName());

        executeQuery(insertAuthorQuery);
    }

    /**
     * Updates a specific entry in the database table by modifying the value of a specified column
     * based on a matching key column and its corresponding value.
     *
     * @param table        the name of the database table to update
     * @param column       the name of the column in the table to set the new value
     * @param updatedValue the new value to be assigned to the specified column
     * @param keyColumn    the name of the column used to identify the row to update
     * @param oldKeyValue  the value in the key column used to locate the row to update
     * @throws SQLException if a database access error occurs or the update query fails
     */
    public void updateDatabase(String table,
                               String column,
                               String updatedValue,
                               String keyColumn,
                               String oldKeyValue) throws SQLException {
        String Query = String.format("Update %s set %s = '%s' where %s = '%s'",
                table, column, updatedValue, keyColumn, oldKeyValue);
        executeQuery(Query);
    }

    /**
     * Retrieves a {@code ResultSet} containing details of all books and their associated authors
     * by executing a SQL query that joins the "titles", "authorISBN", and "authors" tables.
     *
     * @return a {@code ResultSet} with columns for book ISBN, title, edition number, copyright,
     *         and associated author first and last names. Returns null if the query execution fails.
     */
    public ResultSet selectLibrary() {
        String Query = "SELECT t.isbn, t.title, t.editionNumber, t.copyright, a.FirstName, a.lastName FROM titles t JOIN authorISBN ai ON t.isbn = ai.isbn JOIN authors a ON ai.authorID = a.authorID;";
        return executeSelectQuery(Query);
    }

    /**
     * Retrieves all author records from the "authors" database table.
     *
     * @return a {@code ResultSet} object containing all rows from the "authors" table,
     *         or {@code null} if the query execution fails.
     */
    public ResultSet selectAllAuthors() {
        String Query = "SELECT * FROM authors;";
        return executeSelectQuery(Query);
    }

    /**
     * Retrieves all book records from the "titles" table in the database.
     *
     * @return a {@code ResultSet} containing all rows from the "titles" table, or {@code null} if
     *         the query execution fails.
     */
    public ResultSet selectAllBooks() {
        String Query = "SELECT * FROM titles;";
        return executeSelectQuery(Query);
    }

    /**
     * Executes a database query to retrieve all records from the "authorISBN" table.
     *
     * @return a {@code ResultSet} object containing the results of the query,
     *         or {@code null} if the query execution fails.
     */
    public ResultSet selectISBNs(){
        String Query = "SELECT * FROM authorISBN;";
        return executeSelectQuery(Query);
    }

    /**
     * Executes a query to select a specific row from a specified table in the database
     * based on a given comparator and value, and returns the result set.
     *
     * @param table      the name of the database table to query
     * @param comparator the column name to compare values against
     * @param value      the value to match in the specified comparator column
     * @return a ResultSet object containing the selected row(s) from the table, or null if an error occurs
     */
    public ResultSet selectIndividualRow(String table, String comparator, String value) {
        String Query = String.format("SELECT * FROM %s WHERE %s = '%s';", table, comparator, value);
        return executeSelectQuery(Query);
    }

    /**
     * Executes a given SQL SELECT query and retrieves the result as a {@code ResultSet}.
     * Utilizes a prepared statement to execute the query. If an error occurs during execution,
     * it logs the error and returns {@code null}.
     *
     * @param query the SQL SELECT query to be executed
     * @return a {@code ResultSet} containing the results of the executed query or {@code null}
     *         if an error occurs during query execution
     */
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

    /**
     * Executes a given SQL query for updates or modifications on the database.
     * This method creates a connection to the database, executes the query using
     * a Statement object, and handles potential SQL exceptions.
     *
     * @param query the SQL query to execute. Should be a valid SQL statement for
     *              update, insert, or delete operations.
     */
    private void executeQuery(String query) {
        try (Connection conn = createConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
