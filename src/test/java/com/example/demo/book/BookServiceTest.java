package com.example.demo.book;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // this extension is used to open and close mocks
class BookServiceTest {

    @Mock private BookRepository bookRepository;

    private BookService bookService;

    @BeforeEach
    void setUp() {
        bookService = new BookService(bookRepository);
    }

    @Test
    void findsAllBooksSuccess() { // There is only one expected behavior for this method, so you only test it
        bookService.getBooks();
        verify(bookRepository).findAll();// verify invocation of mock
        //also may verify number of invocations of mock
        verify(bookRepository, times(1)).findAll();
    }


    @Test
    void addsNewBook() {
        Book book = new Book("Unfound Door", "Davit Aghnashenebeli");
        bookService.addNewBook(book);
        ArgumentCaptor<Book> argumentCaptor = ArgumentCaptor.forClass(Book.class);
        verify(bookRepository).save(argumentCaptor.capture());

        Book capturedBook = argumentCaptor.getValue();
        assertEquals(book, capturedBook);

    }

    @Test
    void canNotAddNewBookTitleExists() {
        Book book = new Book("Unfound Door", "Davit Aghnashenebeli");

        // в случае вызова метода делай то... то ...
        given(bookRepository.findBooksByTitle(anyString())).willReturn(Optional.of(book));

        assertThatThrownBy(() -> bookService.addNewBook(book)) // assertThatThrownBy() takes lambda + what  is lambda
                .isInstanceOf(IllegalStateException.class);// throw new BadRequestException

    // command + shift + up/dpwn to move a line
        verify(bookRepository, times(1)).findBooksByTitle(book.getTitle());

        verify(bookRepository, never()).save(any());
    }

    @Test
    void deletesBookByIdSuccess() {
        Book book = new Book("Unfound Door", "Davit Aghnashenebeli");
        book.setId(1L);

        // given
        given(bookRepository.existsById(anyLong())).willReturn(true);
        bookService.deleteBook(book.getId());

        // then
        ArgumentCaptor<Long> idArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(bookRepository)
                .deleteById(idArgumentCaptor.capture());
        Long capturedBookId = idArgumentCaptor.getValue();

        // assert
        assertThat(capturedBookId).isEqualTo(book.getId());
    }


    @Test
    void DeleteThrowsExceptionWhenBookIdDoesNotExist() {
        // when
        // then
        Book book = new Book("Unfound Door", "Davit Aghnashenebeli");
        book.setId(0L);

        given(bookRepository
                .existsById(anyLong()))
                .willReturn(false);

        // assert
        assertThatThrownBy(() -> bookService.deleteBook(book.getId()))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("book with id = " + book.getId() + " does not exist");

        verify(bookRepository, never()).deleteById(any());
    }

    @Test
    void updateAuthorAndNotUpdateTitleFindByBookIdSuccess() {
        // conditions: book id exists in bookRepository
        // author not null
        // more than zero
        // old author and new author are different -> setAuthor
        Book book_old = new Book("Unfound Door", "Davit Aghnashenebeli");
        Book book_new = new Book("Unfound Door 1", null); // null blocks the second if in the method: if (book.getTitle() != null
        book_old.setId(1L);

        // when
        given(bookRepository.findById(1L)).willReturn(Optional.of(book_old));
        bookService.updateBook(book_old.getId(), book_new);

        // then
        assertEquals(book_old.getAuthor(), book_new.getAuthor()); // check that author was updated
        assertNotEquals(book_old.getTitle(), book_new.getTitle()); // check that title was updated

    }

    @Test
    void updateTitleAndNotUpdateAuthorFindByBookIdSuccess() {
        // conditions: book id exists in bookRepository
        // title not null
        // more than zero
        // old title and new title are different -> setTitle
        Book book_old = new Book("Unfound Door", "Davit Aghnashenebeli");
        Book book_new = new Book(null, "Davit Aghnashenebeli 1"); // null blocks the second if in the method: if (book.getTitle() != null
        book_old.setId(1L);

        // when
        given(bookRepository.findById(1L)).willReturn(Optional.of(book_old));
        given(bookRepository.findBooksByTitle(book_new.getTitle())).willReturn(Optional.empty()); // book with new title does not exist
        bookService.updateBook(book_old.getId(), book_new);

        // then
        assertEquals(book_old.getTitle(), book_new.getTitle()); // check that title was updated
        assertNotEquals(book_old.getAuthor(), book_new.getAuthor()); // check that author was updated
    }


    @Test
    void updateBookThrowsExceptionTitleAlreadyExistsInRepository() {
        Book book_old = new Book("Unfound Door", "Davit Aghnashenebeli");
        Book book_new = new Book(null, "Davit Aghnashenebeli 1"); // null blocks the second if in the method: if (book.getTitle() != null
        book_old.setId(1L);

        // when
        given(bookRepository.findById(1L)).willReturn(Optional.of(book_old));
        given(bookRepository.findBooksByTitle(book_new.getTitle())).willReturn(Optional.of(book_new)); // book with new title exist

        // assert
        assertThatThrownBy(() -> bookService.updateBook(book_old.getId(), book_new))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("title already exists");
    }


    @Test
    void updateBookThrowsExceptionBookIdDoesNotExist() {

        Book book = new Book("Unfound Door", "Davit Aghnashenebeli");
        book.setId(0L);

        given(bookRepository
                .findById(0L))
                .willReturn(Optional.empty());

        // assert
        assertThatThrownBy(() -> bookService.updateBook(book.getId(), book))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("book doesn't exist");

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