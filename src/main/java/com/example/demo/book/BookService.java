// Business logic for managing books
// provides methods for book controller
// is dependency of bookRepository with methods from JPA repo
// checks if new book title already exists
package com.example.demo.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
// BookService has to be a bean -> needs to be instantiated -> so BookController knows how to find BookService class
public class BookService {

    //dependency injection
    private final BookRepository bookRepository; // needs book repository to fetch from db

    @Autowired
    public BookService(BookRepository bookRepository) { //constructor
        this.bookRepository = bookRepository;
    } // constructor

    // methods
    public List<Book> getBooks() {
        return bookRepository.findAll(); // returns a list. + i don't see anything in book repository - no any links to book config. findAll() is applied on what?
    } // extends JpaRepository and uses method findAll() from there

    public void addNewBook(Book book) {
        Optional<Book> bookOptional = bookRepository.findBooksByTitle(book.getTitle()); // first checks if book title exists
        if (bookOptional.isPresent()) { //if true
            throw new IllegalStateException("book with title " + book + " already exists"); //throws an exception and doesn't return books
        }
        bookRepository.save(book); // if false - saves the book (method from crud repository)
    };

    public void deleteBook(Long id) {
        boolean exists = bookRepository.existsById(id); // method from crud repository
        if (!exists) {
            throw new IllegalStateException("book with id = " + id + " does not exist");
        }
        bookRepository.deleteById(id); // method from crud repository
    }

    // transactional - don't have to implement jbql - use service to update entity if it's possible
    @Transactional
    public void updateBook(Long bookId, String author, String title) {
        Book book = bookRepository.findById(bookId) // method from crud repo
                .orElseThrow(() -> new IllegalStateException("book doesn't exist")); // If a value is present, returns the value, otherwise throws an exception produced by the exception supplying function

        if (author != null && // if author of this id differs - update it
                author.length() > 0 &&
                !Objects.equals(book.getAuthor(), author)) {
            book.setAuthor(author);
        }

        if (title != null &&
                title.length() > 0 &&
                !Objects.equals(book.getTitle(), title)) { // if passed title is the title of other book with different id -> exception
            Optional<Book> bookOptional = bookRepository.findBooksByTitle(title);
            if (bookOptional.isPresent()) {
                throw new IllegalStateException("title already exists");
            }
            book.setTitle(title);
        }
    }
}
