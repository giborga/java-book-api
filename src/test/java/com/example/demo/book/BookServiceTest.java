package com.example.demo.book;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class) // this extension is used to open and close mocks


class BookServiceTest {

//    @Mock
//    private BookRepository bookRepository;

    //private BookService underTest;

    @BeforeEach
    void setUp() {
        //underTest = new BookService(bookRepository);
    }

    @Test
    void getBooks() {
    }

    @Test
    void addNewBook() {
    }

    @Test
    void deleteBook() {
    }

    @Test
    void updateBook() {
    }

    @Test
    void toStringConvertsProperly() {

        // when
        Book book1 = new Book(
                "Fyodor Dostoevsky",
                "Special military operation and Peace");

        book1.setId(1L);

        // then - crafted string
        String craftString = "Book{" + "id=" + book1.getId() + ", author='" + book1.getAuthor() + '\'' + ", title='" + book1.getTitle() + '\'' + '}';

        //assert
        assertEquals(craftString, book1.toString()); // object and string compared and we convert object t string

    }
}