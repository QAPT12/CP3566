package ca.nl.cna.ed.quintintuck.java3.Assignments.Assignment01;

import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.sql.ResultSet;

public class Library {
    private final List<Book> books;
    private final List<Author> authors;

    public Library(List<Book> books, List<Author> authors) {
        this.books = books;
        this.authors = authors;
    }

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
        ResultSet libraryResultSet = dbManager.selectLibrary();
        while (libraryResultSet.next()) {
            String isbn = libraryResultSet.getString("isbn");
            int authorID = Integer.parseInt(libraryResultSet.getString("authorID"));

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

    public List<Book> getBooks() {
        return books;
    }

    public void addBook(BookDatabaseManager dbManager, Book book) throws SQLException {
        dbManager.addBook(book);
        this.books.add(book);
    }

    public void editBook(BookDatabaseManager dbManager, int bookIndex, String newTitle, String newEditionNumber, String newCopyright) throws SQLException {
        Book book = books.get(bookIndex);
        book.setTitle(newTitle);
        book.setCopyright(newCopyright);
        book.setEditionNumber(newEditionNumber);
        dbManager.updateDatabase("titles", "title", newTitle, "isbn", book.getIsbn());
        dbManager.updateDatabase("titles", "copyright", newCopyright, "isbn", book.getIsbn());
        dbManager.updateDatabase("titles", "editionNumber", newEditionNumber, "isbn", book.getIsbn());
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void addAuthor(BookDatabaseManager dbManager, Author author) {
        dbManager.addAuthor(author);
        this.authors.add(author);
    }

    public void editAuthor(BookDatabaseManager dbManager, int authorIndex, String newFirstName, String newLastName) throws SQLException {
        Author author = authors.get(authorIndex);
        author.setFirstName(newFirstName);
        author.setLastName(newLastName);
        dbManager.updateDatabase("authors", "firstName", newFirstName, "authorID", String.valueOf(authors.get(authorIndex).getAuthorID()));
        dbManager.updateDatabase("authors", "lastName", newLastName, "authorID", String.valueOf(authors.get(authorIndex).getAuthorID()));
    }
}
