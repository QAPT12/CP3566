package ca.nl.cna.ed.quintintuck.java3.Assignments.Assignment01;

import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.sql.ResultSet;

/**
 * The {@code Library} class represents a collection of books and authors,
 * and provides methods to manage and interact with these entities.
 * It supports the addition, editing, and retrieval of books and authors,
 * as well as establishing relationships between them.
 */
public class Library {

    private final List<Book> books;
    /**
     * Represents the list of authors associated with the library.
     * Each author in the list is an instance of the {@code Author} class
     * and contains details such as their first name, last name, and unique author ID.
     *
     * This collection is used to manage operations related to authors, including
     * retrieving, adding, and editing author information.
     */
    private final List<Author> authors;

    /**
     * Constructs a new Library instance with the specified lists of books and authors.
     *
     * @param books   the list of Book objects to initialize the library's book collection
     * @param authors the list of Author objects to initialize the library's author collection
     */
    public Library(List<Book> books, List<Author> authors) {
        this.books = books;
        this.authors = authors;
    }

    /**
     * Constructs a new Library instance, initializing its book and author collections
     * from the given database manager. Establishes the relationships between books
     * and their authors based on the database data.
     *
     * TODO: Josh Recommend moving this into the DBManager to simplify the library class.
     *
     * @param dbManager the BookDatabaseManager instance used to retrieve book and author
     *                  information and establish relationships.
     * @throws SQLException if a database access error occurs during the initialization process.
     */
    public Library(BookDatabaseManager dbManager) throws SQLException {
        this.books = new ArrayList<>();
        this.authors = new ArrayList<>();

        ResultSet booksResultSet = dbManager.selectAllBooks();
        while (booksResultSet.next()) {
            Book book = new Book(
                    booksResultSet.getString("isbn"),
                    booksResultSet.getString("title"),
                    booksResultSet.getString("editionNumber"),
                    booksResultSet.getString("copyright")
            );
            this.books.add(book);
        }

        ResultSet authorsResultSet = dbManager.selectAllAuthors();
        while (authorsResultSet.next()) {
            Author author = new Author(
                    authorsResultSet.getInt("authorID"),
                    authorsResultSet.getString("firstName"),
                    authorsResultSet.getString("lastName")
            );
            this.authors.add(author);
        }

        // Create relationships between authors and books
        ResultSet authorISBN = dbManager.selectISBNs();
        while (authorISBN.next()) {
            String isbn = authorISBN.getString("isbn");
            int authorID = Integer.parseInt(authorISBN.getString("authorID"));

            // Find relevant book and author
            Book book = this.books.stream()
                    .filter(b -> isbn.equals(b.getIsbn()))
                    .findFirst()
                    .orElse(null);

            Author author = this.authors.stream()
                    .filter(a -> authorID == (a.getAuthorID()))
                    .findFirst()
                    .orElse(null);

            if (book != null && author != null) {
                book.addAuthorToBook(author); // Link author to book
                author.addBookForAuthor(book); // Link book to author
            }
        }
    }

    /**
     * Retrieves the list of books in the library.
     *
     * @return a list containing all books in the library.
     */
    public List<Book> getBooks() {
        return books;
    }

    /**
     * Adds a book to the library by updating the database and the local book list.
     *
     * @param dbManager the {@code BookDatabaseManager} used to interact with the database
     * @param book the {@code Book} object to be added to the database and the library's book collection
     * @throws SQLException if a database access error occurs during the operation
     */
    public void addBook(BookDatabaseManager dbManager, Book book) throws SQLException {
        dbManager.addBook(book);
        this.books.add(book);
    }

    /**
     * Edits the details of an existing book in the list and updates the database with the new information.
     *
     * @param dbManager         the {@code BookDatabaseManager} instance used to interact with the database
     * @param bookIndex         the index of the book in the list to be edited
     * @param newTitle          the new title of the book
     * @param newEditionNumber  the new edition number of the book
     * @param newCopyright      the new copyright information of the book
     * @throws SQLException     if a database access error occurs during the update
     */
    public void editBook(BookDatabaseManager dbManager, int bookIndex, String newTitle, String newEditionNumber, String newCopyright) throws SQLException {
        Book book = books.get(bookIndex);
        book.setTitle(newTitle);
        book.setCopyright(newCopyright);
        book.setEditionNumber(newEditionNumber);
        dbManager.updateDatabase("titles", "title", newTitle, "isbn", book.getIsbn());
        dbManager.updateDatabase("titles", "copyright", newCopyright, "isbn", book.getIsbn());
        dbManager.updateDatabase("titles", "editionNumber", newEditionNumber, "isbn", book.getIsbn());
    }

    /**
     * Retrieves the list of authors associated with the library.
     *
     * @return a list containing all authors in the library.
     */
    public List<Author> getAuthors() {
        return authors;
    }

    /**
     * Adds an author to the library by updating the database and the local author list.
     *
     * @param dbManager the {@code BookDatabaseManager} responsible for handling database operations
     * @param author the {@code Author} object to be added to the database and the library's author collection
     */
    public void addAuthor(BookDatabaseManager dbManager, Author author) {
        dbManager.addAuthor(author);
        this.authors.add(author);
    }

    /**
     * Edits the information of an existing author in the authors list and updates the database.
     *
     * @param dbManager     the {@code BookDatabaseManager} instance used to interact with the database
     * @param authorIndex   the index of the author in the authors list to be edited
     * @param newFirstName  the new first name of the author
     * @param newLastName   the new last name of the author
     * @throws SQLException if a database access error occurs during the update
     */
    public void editAuthor(BookDatabaseManager dbManager, int authorIndex, String newFirstName, String newLastName) throws SQLException {
        Author author = authors.get(authorIndex);
        author.setFirstName(newFirstName);
        author.setLastName(newLastName);
        dbManager.updateDatabase("authors", "firstName", newFirstName, "authorID", String.valueOf(authors.get(authorIndex).getAuthorID()));
        dbManager.updateDatabase("authors", "lastName", newLastName, "authorID", String.valueOf(authors.get(authorIndex).getAuthorID()));
    }
}
