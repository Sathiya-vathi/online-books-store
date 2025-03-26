package com.bookstore.Controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.bookstore.entities.Book;
import com.bookstore.service.BookService;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    private Book book;
    private List<Book> books;

    @BeforeEach
    void setUp() {
        book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        book.setPrice(new BigDecimal("29.99"));
        book.setPublishedDate(java.time.LocalDate.now());

        books = Arrays.asList(book);
    }

    @Test
    void addBook_ShouldReturnCreatedBook() {
        when(bookService.addBook(any(Book.class))).thenReturn(book);

        ResponseEntity<Book> response = bookController.addBook(book);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(book.getId(), response.getBody().getId());
        assertEquals(book.getTitle(), response.getBody().getTitle());
        verify(bookService).addBook(book);
    }

    @Test
    void getAllBooks_ShouldReturnListOfBooks() {
        when(bookService.getAllBooks()).thenReturn(books);

        ResponseEntity<List<Book>> response = bookController.getAllBooks();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(bookService).getAllBooks();
    }

   /* @Test
    void getBookById_ShouldReturnBook() {
        when(bookService.getBookById(1L)).thenReturn(book);

        ResponseEntity<Book> response = bookController.getBookById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(book.getId(), response.getBody().getId());
        verify(bookService).getBookById(1L);
    }*/

    @Test
    void updateBook_ShouldReturnUpdatedBook() {
        Book updatedBook = new Book();
        updatedBook.setTitle("Updated Title");
        updatedBook.setAuthor("Updated Author");
        updatedBook.setPrice(new BigDecimal("39.99"));

        when(bookService.updateBook(eq(1L), any(Book.class))).thenReturn(updatedBook);

        ResponseEntity<Book> response = bookController.updateBook(1L, updatedBook);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(updatedBook.getTitle(), response.getBody().getTitle());
        assertEquals(updatedBook.getAuthor(), response.getBody().getAuthor());
        assertEquals(updatedBook.getPrice(), response.getBody().getPrice());
        verify(bookService).updateBook(1L, updatedBook);
    }

    @Test
    void deleteBook_ShouldReturnOkResponse() {
        doNothing().when(bookService).deleteBook(1L);

        ResponseEntity<Void> response = bookController.deleteBook(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(bookService).deleteBook(1L);
    }
} 