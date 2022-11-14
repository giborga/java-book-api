// responsible for access to db
//will be used in BookService

package com.example.demo.book;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository // its interface of class
public interface BookRepository // from JpaRepository receives .findAll()
        extends JpaRepository<Book, Long> { // type of object we want to work this repository with/datatype of id
    // + why do we need datatype of id if we know the class (Book) to find objects?
    // custom method to find book by title
   //@Query("SELECT book FROM Book book WHERE book.title = ?1")
    Optional<Book> findBooksByTitle(String title); // finds word automatically without query; Optional is intended to be used as a method return type where there is a clear need to represent "no result," and where using null is likely to cause errors.
} // findBooksByTitle(String title) connects to db
