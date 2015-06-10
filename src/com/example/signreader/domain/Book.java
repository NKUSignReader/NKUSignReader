package com.example.signreader.domain;

public class Book {
	private int id;
	private String bookPath;
	private int word;
	private String name;
	//private int length;
	private int mark;
	private int current;
	
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
	
	public int getCurrent()
	{
		return this.current;
	}
	
	public void setCurrent(int cur)
	{
		this.current=cur;
	}
	
	public int getMark()
	{
		return this.mark;
	}
	
	public void setMark(int mar)
	{
		this.mark=mar;
	}
	
	@Override
	public String toString() {
		return "Book [id=" + id + ", bookPath=" + bookPath + ", word=" + word
				+ ", name=" + name + "mark" + mark + "current"+ current +"]";
	}
	
	public Book(String bookPath, int word, String name,int mar,int cur) {
		super();
		this.id = 0;
		this.bookPath = bookPath;
		this.word = word;
		this.name = name;
		//this.length = len;
		this.mark = mar;
		this.current = cur;
	}
	
	public Book() {
		super();
		// TODO Auto-generated constructor stub
	}

}
