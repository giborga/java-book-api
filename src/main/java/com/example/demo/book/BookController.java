// API layer
// creates CRUD methods using methods from BookService
package com.example.demo.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController //serves REST endpoints
@RequestMapping(path="api/v1/book")  // + why path is here separately?
public class BookController {

    private final BookService bookService; // reference to BookService to use methods and add it to the constructor

    // Spring container “injects” objects into other objects or “dependencies”.
    @Autowired // + says that bookService will be instantiated for us into BookController constructor
    public BookController(BookService bookService) {
        this.bookService = bookService; // to make it work we need an instance of BookService, otherwise: "Could not autowire. No beans of 'BookService' type found. "
    }

    @GetMapping // get endpoint (that returns an array of books)
    public List<Book> getBooks() {
        return bookService.getBooks(); //use method from book service
    }

    // if title does not exist - add to db
    // else - throw an exception
    @PostMapping
    public void registerNewBook(@RequestBody Book book) { //map this new Student with json request body we pass
        bookService.addNewBook(book);
    }; //request body json connect

    // if student id exists - delete, else - illegal exception
    @DeleteMapping(path = "{bookId}") // path variable
    public void deleteBook(@PathVariable("bookId") Long bookId) {
        bookService.deleteBook(bookId);
    }

    @PutMapping(path = "{bookId}") //http://localhost:8082/api/v1/book/1?title=War+and+Piece
    public void updateBook(@PathVariable("bookId") Long bookId,
                              @RequestParam(required = false) String author,
                              @RequestParam(required = false) String title) {
        bookService.updateBook(bookId, author, title);
    }
}
