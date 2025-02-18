package ca.nl.cna.ed.quintintuck.java3.Assignments.Assignment01;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a book with attributes such as ISBN, title, edition number,
 * copyright information, and a list of assigned authors.
 */
public class Book {
    private String isbn;
    private String title;
    private String editionNumber;
    private String copyright;
    private List<Author> authorList;



    /**
     * Constructs a new Book object with the specified ISBN, title, edition number, and copyright information.
     *
     * @param isbn          the ISBN of the book
     * @param title         the title of the book
     * @param editionNumber the edition number of the book
     * @param copyright     the copyright information of the book
     */
    public Book(String isbn, String title, String editionNumber, String copyright) {
        this.isbn = isbn;
        this.title = title;
        this.editionNumber = editionNumber;
        this.copyright = copyright;
        this.authorList = new ArrayList<>();
    }

    /**
     * Adds an author to the book's list of authors if the author is not already present.
     *
     * @param author the author to be added to the book's list of authors
     */
    public void addAuthorToBook(Author author) {
        if (!this.authorList.contains(author)) {
            this.authorList.add(author);
        }
    }

    /**
     * Gets the ISBN of the book.
     *
     * @return the ISBN of the book.
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Sets the ISBN of the book.
     *
     * @param isbn the new ISBN of the book.
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * Gets the title of the book.
     *
     * @return the title of the book.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the book.
     *
     * @param title the new title of the book.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the edition number of the book.
     *
     * @return the edition number of the book.
     */
    public String getEditionNumber() {
        return editionNumber;
    }

    /**
     * Sets the edition number of the book.
     *
     * @param editionNumber the new edition number of the book.
     */
    public void setEditionNumber(String editionNumber) {
        this.editionNumber = editionNumber;
    }

    /**
     * Gets the copyright information of the book.
     *
     * @return the copyright information of the book.
     */
    public String getCopyright() {
        return copyright;
    }

    /**
     * Sets the copyright information of the book.
     *
     * @param copyright the new copyright information of the book.
     */
    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    /**
     * Gets the list of authors assigned to this book.
     *
     * @return the list of authors.
     */
    public List<Author> getAuthorList() {
        return authorList;
    }

    /**
     * Sets the list of authors for this book.
     *
     * @param authorList the new list of authors.
     */
    public void setAuthorList(List<Author> authorList) {
        this.authorList = authorList;
    }
}
