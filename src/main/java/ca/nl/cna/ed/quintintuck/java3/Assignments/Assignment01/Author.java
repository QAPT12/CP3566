package ca.nl.cna.ed.quintintuck.java3.Assignments.Assignment01;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an author with attributes such as first name, last name, and a unique author ID.
 * This class provides methods to manage books written by the author and obtain author details.
 */
public class Author {
    private List<Book> booksForAuthor;
    private String firstName;
    private String lastName;
    private int authorID;

    /**
     * Constructs an Author object with the given author ID, first name, and last name.
     *
     * @param authorID  the unique identifier for the author
     * @param firstName the first name of the author
     * @param lastName  the last name of the author
     */
    public Author(int authorID, String firstName, String lastName) {
        this.authorID = authorID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.booksForAuthor = new ArrayList<>();
    }

    /**
     * Adds a book to the list of books associated with the author if it is not already in the list.
     *
     * @param book the book to be added to the author's list of books
     */
    public void addBookForAuthor(Book book) {
        if (!this.booksForAuthor.contains(book)) {
            this.booksForAuthor.add(book);
        }
    }

    /**
     * Retrieves the list of books associated with an author.
     *
     * @return a list containing all the books written by the author.
     */
    public List<Book> getBooksForAuthor() {
        return booksForAuthor;
    }

    /**
     * Retrieves the first name of the author.
     *
     * @return the first name of the author.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the author.
     *
     * @param firstName the first name to be set for the author
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Retrieves the last name of the author.
     *
     * @return the last name of the author.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the author.
     *
     * @param lastName the new last name of the author
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Retrieves the full name of the author by concatenating the first name and the last name
     * with a single space in between.
     *
     * @return the full name of the author as a string, consisting of the first name followed by the last name.
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }

    /**
     * Retrieves the unique identifier of the author.
     *
     * @return the unique author ID as an integer.
     */
    public int getAuthorID() {
        return authorID;
    }
}
