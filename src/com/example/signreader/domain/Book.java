package com.example.signreader.domain;

public class Book {
	private int id;
	private String bookPath;
	private int word;
	private String name;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBookPath() {
		return bookPath;
	}
	public void setBookPath(String bookPath) {
		this.bookPath = bookPath;
	}
	public int getWord() {
		return word;
	}
	public void setWord(int word) {
		this.word = word;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "Book [id=" + id + ", bookPath=" + bookPath + ", word=" + word
				+ ", name=" + name + "]";
	}
	
	public Book(int id, String bookPath, int word, String name) {
		super();
		this.id = id;
		this.bookPath = bookPath;
		this.word = word;
		this.name = name;
	}
	
	public Book() {
		super();
		// TODO Auto-generated constructor stub
	}

}
