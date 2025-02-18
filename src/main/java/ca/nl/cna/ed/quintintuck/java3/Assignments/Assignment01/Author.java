package ca.nl.cna.ed.quintintuck.java3.Assignments.Assignment01;

import java.util.ArrayList;
import java.util.List;

public class Author {
    private List<Book> booksForAuthor;
    private String firstName;
    private String lastName;
    private int authorID;

    public Author(int authorID, String firstName, String lastName) {
        this.authorID = authorID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.booksForAuthor = new ArrayList<>();
    }

    public void addBookForAuthor(Book book) {
        if (!this.booksForAuthor.contains(book)) {
            this.booksForAuthor.add(book);
        }
    }

    public List<Book> getBooksForAuthor() {
        return booksForAuthor;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public int getAuthorID() {
        return authorID;
    }
}
