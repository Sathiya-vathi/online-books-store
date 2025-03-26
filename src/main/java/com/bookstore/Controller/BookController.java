package com.bookstore.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bookstore.entities.Book;
import com.bookstore.service.BookService;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
	
	@Autowired
	private BookService bookService;
	
	// Create
	@PostMapping
	public ResponseEntity<Book> addBook(@RequestBody Book book) {
		return ResponseEntity.ok(bookService.addBook(book));
	}
	
	// Read all
	@GetMapping
	public ResponseEntity<List<Book>> getAllBooks() {
		return ResponseEntity.ok(bookService.getAllBooks());
	}
	
	
	// Update
	@PutMapping("/{id}")
	public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book) {
		return ResponseEntity.ok(bookService.updateBook(id, book));
	}
	
	// Delete
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
		bookService.deleteBook(id);
		return ResponseEntity.ok().build();
	}
}
