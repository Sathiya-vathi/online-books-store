package com.bookstore.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.dao.BookRepository;
import com.bookstore.entities.Book;
import com.bookstore.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class BookService {
	
	@Autowired
	private BookRepository bookRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(BookService.class);
	
	// Create
	public Book addBook(Book book) {
		logger.info("Adding new book: {}", book);
		return bookRepository.save(book);
	}
	
	// Read all
	public List<Book> getAllBooks() {
		logger.info("Fetching all books");
		return bookRepository.findAll();
	}
	
	// Read by id
	public Book getBookById(Long id) {
		logger.info("Fetching book with ID: {}", id);
		return bookRepository.findById(id)
			.orElseThrow(() -> {
				logger.warn("Failed to retrive book");
				logger.error("Book not found with ID: {}", id);
				return new ResourceNotFoundException("Book not found with id: " + id);
			});
	}
	
	// Update
	public Book updateBook(Long id, Book bookDetails) {
		logger.info("Updating book with ID: {}", id);
		Book book = getBookById(id);
		book.setTitle(bookDetails.getTitle());
		book.setAuthor(bookDetails.getAuthor());
		book.setPrice(bookDetails.getPrice());
		book.setPublishedDate(bookDetails.getPublishedDate());
		Book updatedBook = bookRepository.save(book);
		logger.info("Book updated successfully: {}", updatedBook);
		return updatedBook;
	}
	
	// Delete
	public void deleteBook(Long id) {
		logger.info("Deleting book with ID: {}", id);
		Book book = getBookById(id);
		bookRepository.delete(book);
		logger.info("Book deleted successfully");
	}
}
