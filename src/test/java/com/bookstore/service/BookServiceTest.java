package com.bookstore.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bookstore.dao.BookRepository;
import com.bookstore.entities.Book;
import com.bookstore.exception.ResourceNotFoundException;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

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
    void addBook_ShouldReturnSavedBook() {
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book savedBook = bookService.addBook(book);

        assertNotNull(savedBook);
        assertEquals(book.getId(), savedBook.getId());
        assertEquals(book.getTitle(), savedBook.getTitle());
        verify(bookRepository).save(book);
    }

    @Test
    void getAllBooks_ShouldReturnListOfBooks() {
        when(bookRepository.findAll()).thenReturn(books);

        List<Book> result = bookService.getAllBooks();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(bookRepository).findAll();
    }

    @Test
    void getBookById_WhenBookExists_ShouldReturnBook() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Book result = bookService.getBookById(1L);

        assertNotNull(result);
        assertEquals(book.getId(), result.getId());
        verify(bookRepository).findById(1L);
    }

    @Test
    void getBookById_WhenBookDoesNotExist_ShouldThrowException() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> bookService.getBookById(1L));
        verify(bookRepository).findById(1L);
    }

    @Test
    void updateBook_WhenBookExists_ShouldUpdateAndReturnBook() {
        Book updatedBook = new Book();
        updatedBook.setTitle("Updated Title");
        updatedBook.setAuthor("Updated Author");
        updatedBook.setPrice(new BigDecimal("39.99"));

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book result = bookService.updateBook(1L, updatedBook);

        assertNotNull(result);
        assertEquals(updatedBook.getTitle(), result.getTitle());
        assertEquals(updatedBook.getAuthor(), result.getAuthor());
        assertEquals(updatedBook.getPrice(), result.getPrice());
        verify(bookRepository).findById(1L);
        verify(bookRepository).save(any(Book.class));
    }

    @Test
    void deleteBook_WhenBookExists_ShouldDeleteBook() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        doNothing().when(bookRepository).delete(book);

        bookService.deleteBook(1L);

        verify(bookRepository).findById(1L);
        verify(bookRepository).delete(book);
    }
} 