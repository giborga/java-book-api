package com.example.demo.book;

import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @AfterEach
    void tearDown() {bookRepository.deleteAll();}

    @Test
    void checksIfBookExistsByTitle() { // works with h2 only

        String title = "Unfound Door";
        Book book = new Book("Tbilisi", title);
        bookRepository.save(book);
        Optional<Book> foundBook  = bookRepository.findBooksByTitle(title);
        assertThat(foundBook).isPresent();
    }

    @Test
    void checksIfBookNotExistsByTitle() { // works with h2 only
        String title = "Unfound Door";
        //Book book = new Book("Davit Aghnashenebeli", title);
        Optional<Book> foundBook  = bookRepository.findBooksByTitle(title);
        assertThat(foundBook).isNotPresent();
    }
}