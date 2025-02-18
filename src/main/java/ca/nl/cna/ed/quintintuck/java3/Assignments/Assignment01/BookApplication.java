package ca.nl.cna.ed.quintintuck.java3.Assignments.Assignment01;

import java.util.Scanner;
import java.sql.*;

public class BookApplication {
    private static final Scanner scanner = new Scanner(System.in);
    private static final BookDatabaseManager db = new BookDatabaseManager();

    public static void main(String[] args) throws SQLException {
        System.out.println("Welcome to the Book Library Management System!");

        Library library = new Library(db);

        while (true) {

            printMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    printAllBooks(library);
                    break;
                case 2:
                    printAllAuthors(library);
                    break;
                case 3:
                    editAttributes(library);
                    break;
                case 4:
                    addBook(library);
                    break;
                case 5:
                    System.out.println("Exiting program...");
                    return;
                default:
                    System.out.println("Please choose a valid option.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("Select an option:");
        System.out.println("1. Print all books");
        System.out.println("2. Print all authors");
        System.out.println("3. Edit a book's or an author's attributes");
        System.out.println("4. Add a book to the database for a new or existing author");
        System.out.println("5. Quit");
    }

    private static void printAllBooks(Library library) {
        for (Book book : library.getBooks()) {
            System.out.println("Title: " + book.getTitle());
            System.out.println("Authors:");
            for (Author author : book.getAuthorList()) {
                System.out.println("\t" + author.getFullName());
            }
        }
    }

    private static void printAllAuthors(Library library) {
        for (Author author : library.getAuthors()) {
            System.out.println("Author: " + author.getFullName());
            System.out.println("Books:");
            for (Book book : author.getBooksForAuthor()) {
                System.out.println("\t" + book.getTitle());
            }
        }
    }

    private static void editAttributes(Library library) {
        System.out.println("Do you want to edit a book or an author?");
        System.out.println("1. Edit a Book");
        System.out.println("2. Edit an Author");
        int editChoice = scanner.nextInt();
        scanner.nextLine();

        switch (editChoice) {
            case 1:
                editBookAttribute(library);
                break;
            case 2:
                editAuthorAttribute(library);
                break;
            default:
                System.out.println("Please choose a valid option.");
        }
    }

    private static void editBookAttribute(Library library) {
        System.out.println("Here are the books available in the library:");
        // Print all books with their index
        int index = 0;
        for (Book book : library.getBooks()) {
            System.out.println(index++ + ". " + book.getTitle());
        }

        // Ask the user for the index of the book to edit
        System.out.println("Enter the number of the book you want to edit:");
        int bookIndex = scanner.nextInt();
        scanner.nextLine();  // Consume the newline character

        // Get the selected book
        Book selectedBook = library.getBooks().get(bookIndex);

        // Display current book details
        System.out.println("Current Title: " + selectedBook.getTitle());
        System.out.println("Current Edition: " + selectedBook.getEditionNumber());
        System.out.println("Current Copyright: " + selectedBook.getCopyright());

        // Prompt user for new values, keeping the current values if input is blank
        System.out.println("Enter a new title (leave blank to keep the same): ");
        String newTitle = scanner.nextLine();
        System.out.println("Enter a new edition number (leave blank to keep the same): ");
        String newEdition = scanner.nextLine();
        System.out.println("Enter a new copyright (leave blank to keep the same): ");
        String newCopyright = scanner.nextLine();

        // Update the attributes if new values are provided
        if (!newTitle.isBlank()) {
            selectedBook.setTitle(newTitle);
        }
        if (!newEdition.isBlank()) {
            selectedBook.setEditionNumber(newEdition);
        }
        if (!newCopyright.isBlank()) {
            selectedBook.setCopyright(newCopyright);
        }

        System.out.println("Book details updated successfully.");
    }

    private static void editAuthorAttribute(Library library) {
        System.out.println("Here are the authors available in the library:");
        // Print all authors with their index
        int index = 0;
        for (Author author : library.getAuthors()) {
            System.out.println(index++ + ". " + author.getFullName());
        }

        // Ask the user for the index of the author to edit
        System.out.println("Enter the number of the author you want to edit:");
        int authorIndex = scanner.nextInt();
        scanner.nextLine();  // Consume the newline character

        // Get the selected author
        Author selectedAuthor = library.getAuthors().get(authorIndex);

        // Display current author details
        System.out.println("Current First Name: " + selectedAuthor.getFirstName());
        System.out.println("Current Last Name: " + selectedAuthor.getLastName());

        // Prompt user for new values, keeping the current values if input is blank
        System.out.println("Enter a new first name (leave blank to keep the same): ");
        String newFirstName = scanner.nextLine();
        System.out.println("Enter a new last name (leave blank to keep the same): ");
        String newLastName = scanner.nextLine();

        // Update the attributes if new values are provided
        if (!newFirstName.isBlank()) {
            selectedAuthor.setFirstName(newFirstName);
        }
        if (!newLastName.isBlank()) {
            selectedAuthor.setLastName(newLastName);
        }

        System.out.println("Author details updated successfully.");

    }

    private static void addBook(Library library) throws SQLException {
        System.out.println("Enter the book details:");

        System.out.print("Title: ");
        String title = scanner.nextLine();

        System.out.print("ISBN: ");
        String isbn = scanner.nextLine();

        System.out.print("Edition Number: ");
        String editionNumber = scanner.nextLine();

        System.out.print("Copyright: ");
        String copyright = scanner.nextLine();

        Book newBook = new Book(isbn, title, editionNumber, copyright);

        while (true) {
            System.out.println("Options for adding authors:");
            System.out.println("1. An existing author");
            System.out.println("2. A new author");
            System.out.println("3. Finish adding authors");
            int authorChoice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            if (authorChoice == 1) {
                System.out.println("Here are the existing authors:");
                int index = 0;
                for (Author author : library.getAuthors()) {
                    System.out.println(index++ + ". " + author.getFullName());
                }

                System.out.println("Enter the number of the author to add to the book:");
                int authorIndex = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                Author selectedAuthor = library.getAuthors().get(authorIndex);
                newBook.addAuthorToBook(selectedAuthor);
                selectedAuthor.addBookForAuthor(newBook);

            } else if (authorChoice == 2) {
                System.out.print("Enter the new author's first name: ");
                String firstName = scanner.nextLine();

                System.out.print("Enter the new author's last name: ");
                String lastName = scanner.nextLine();

                int newAuthorID = library.getAuthors().size() + 1; // Generate a new ID
                Author newAuthor = new Author(newAuthorID, firstName, lastName);
                newBook.addAuthorToBook(newAuthor);
                newAuthor.addBookForAuthor(newBook);
                library.addAuthor(db, newAuthor);

            } else if (authorChoice == 3) {
                break;

            } else {
                System.out.println("Please choose a valid option.");
            }
        }

        library.addBook(db, newBook);
        System.out.println("Book added successfully!");
    }
}
