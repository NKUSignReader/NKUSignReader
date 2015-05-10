package com.example.signreader.domain;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.example.signreader.db.SQLiteHelper;

//import com.example.signreader.db.DBAdapter;

public class BookOperations {
	private SQLiteHelper dbHelper;
	private SQLiteDatabase database;
	private static final String DB_TABLE = "bookinfo";//数据库表格的名称 
	public static final String KEY_ID = "_id";
	public static final String KEY_NAME = "name";
	public static final String KEY_WORD = "word";
	public static final String KEY_PATH = "path";
	private String[] BOOK_TABLE_COLUMNS = {KEY_ID,KEY_NAME,KEY_WORD,KEY_PATH};
	public BookOperations(Context context)
	{
		dbHelper = new SQLiteHelper(context);
	}
	public void open() throws SQLiteException{
		database = dbHelper.getWritableDatabase();
	}
	public void close(){
		dbHelper.close();
	}
	public long insert(Book book){
		ContentValues newValues = new ContentValues();
		
		newValues.put(KEY_NAME, book.getName());
		newValues.put(KEY_WORD, book.getWord());
		newValues.put(KEY_PATH, book.getBookPath());	
		return database.insert(DB_TABLE, null, newValues);
	}
	public long deleteAllData(){
		return database.delete(DB_TABLE, null, null);
	}
	
	public long deleteOneData(long id){
		return database.delete(DB_TABLE, KEY_ID + "="+ id, null);
	}
	public long updateOneData(long id, Book book){
		ContentValues updateValues = new ContentValues();
		updateValues.put(KEY_NAME, book.getName());
		updateValues.put(KEY_PATH, book.getBookPath());
		updateValues.put(KEY_WORD, book.getWord());
		return database.update(DB_TABLE, updateValues, KEY_ID + "=" + id, null);
	}
	/*
	private Book[] ConvertToBook(Cursor cursor){
		int resultCounts = cursor.getCount();
		if(resultCounts == 0 || !cursor.moveToFirst()){
			return null;
		}
		Book[] books = new Book[resultCounts];
		for(int i=0; i<resultCounts; i++){
			books[i] = new Book();
			books[i].setId(cursor.getInt(0));
			books[i].setWord(cursor.getColumnIndex(KEY_WORD));
			books[i].setBookPath(cursor.getString(cursor.getColumnIndex(KEY_PATH)));
			books[i].setName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
			cursor.moveToNext();
		}
		return books;
	}*/
	
	public List<Book> getAllClasses()
	{
		List<Book> classlist=new ArrayList<Book>();
		Cursor cursor=database.query(DB_TABLE, BOOK_TABLE_COLUMNS, null, null, null, null, null);
		cursor.moveToFirst();
		while(!cursor.isAfterLast())
		{
			Book newclass=parseBook(cursor);
			//Log.e("gelAllClasses",newclass.getName());
			classlist.add(newclass);
			cursor.moveToNext();
		}
		cursor.close();
		return classlist;
	}
	
	public List<Book> searchClass(String bookName)
	{
		List<Book> searchResult = new ArrayList<Book>();
		Cursor cursor=database.query(DB_TABLE, BOOK_TABLE_COLUMNS, KEY_NAME+" like "+'\''+bookName+"%\'", null, null, null, null);
		cursor.moveToFirst();
		while(!cursor.isAfterLast())
		{
			Book newclass=parseBook(cursor);
			//Log.e("gelAllClasses",newclass.getName());
			searchResult.add(newclass);
			cursor.moveToNext();
		}
		cursor.close();
		return searchResult;
	}
	
	private Book parseBook(Cursor cursor)
	{
		Book nbook=new Book();
		nbook.setId((cursor.getInt(0)));
		nbook.setId(cursor.getInt(0));
		nbook.setWord(cursor.getColumnIndex(KEY_WORD));
		nbook.setBookPath(cursor.getString(cursor.getColumnIndex(KEY_PATH)));
		nbook.setName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
		return nbook;
	}

}
