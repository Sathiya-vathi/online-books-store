package com.bookstore.entities;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "books")
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String title;
	
	@Column(nullable = false)
	private String author;
	
	@Column(nullable = false)
	private BigDecimal price;
	
	private LocalDate publishedDate;

	// Getters and Setters
	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }
	
	public String getTitle() { return title; }
	public void setTitle(String title) { this.title = title; }
	
	public String getAuthor() { return author; }
	public void setAuthor(String author) { this.author = author; }
	
	public BigDecimal getPrice() { return price; }
	public void setPrice(BigDecimal price) { this.price = price; }
	
	public LocalDate getPublishedDate() { return publishedDate; }
	public void setPublishedDate(LocalDate publishedDate) { this.publishedDate = publishedDate; }

	@Override
	public String toString() {
		return "Book [id=" + id + ", title=" + title + ", author=" + author + ", price=" + price + ", publishedDate=" + publishedDate + "]";
	}

	public Book() {
		super();
		// TODO Auto-generated constructor stub
	}
}
