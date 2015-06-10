package com.example.signreader.domain;

import java.util.ArrayList;
import java.util.List;

import com.example.signreader.db.SQLiteHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

//import com.example.signreader.db.SQLiteHelper;

//import com.example.signreader.db.DBAdapter;

public class BookOperations {
	private SQLiteHelper dbHelper;
	private SQLiteDatabase database;
	private static final String DB_TABLE = "bookinfo";//数据库表格的名称 
	private String[] BOOK_TABLE_COLUMNS = {SQLiteHelper.KEY_ID,SQLiteHelper.KEY_NAME,SQLiteHelper.KEY_WORD,SQLiteHelper.KEY_PATH,SQLiteHelper.KEY_MARK,SQLiteHelper.KEY_CURRENT};
	public BookOperations(Context context)
	{
		dbHelper = new SQLiteHelper(context);
	}
	public void open() throws SQLiteException{
		database = dbHelper.getWritableDatabase();
	}
	public SQLiteDatabase returnDatabase()
	{
		return database;
	}
	public void close(){
		dbHelper.close();
	}
	public long insert(Book book){
		ContentValues newValues = new ContentValues();
		//newValues.put(SQLiteHelper.KEY_ID, book.getId());
		newValues.put(SQLiteHelper.KEY_NAME, book.getName());
		newValues.put(SQLiteHelper.KEY_WORD, book.getWord());
		newValues.put(SQLiteHelper.KEY_PATH, book.getBookPath());	
		//newValues.put(SQLiteHelper.KEY_LEN, book.getLength());	
		newValues.put(SQLiteHelper.KEY_MARK, book.getMark());
		newValues.put(SQLiteHelper.KEY_CURRENT, book.getCurrent());
		return database.insert(DB_TABLE, null, newValues);
	}
	public long deleteAllData(){
		return database.delete(DB_TABLE, null, null);
	}
	
	public long deleteOneData(long id){
		return database.delete(DB_TABLE, SQLiteHelper.KEY_ID + "="+ id, null);
	}
	public long updateOneData(long id, Book book){
		ContentValues updateValues = new ContentValues();
		updateValues.put(SQLiteHelper.KEY_NAME, book.getName());
		updateValues.put(SQLiteHelper.KEY_WORD, book.getWord());
		updateValues.put(SQLiteHelper.KEY_PATH, book.getBookPath());
	//	updateValues.put(SQLiteHelper.KEY_LEN, book.getBookPath());
		updateValues.put(SQLiteHelper.KEY_MARK, book.getMark());
		return database.update(DB_TABLE, updateValues, SQLiteHelper.KEY_ID + "=" + id, null);
	}
	
	public int updateSchedue(long id, int current){
		ContentValues updateValues = new ContentValues();
		//updateValues.put(SQLiteHelper.KEY_NAME, book.getName());
		//updateValues.put(SQLiteHelper.KEY_WORD, book.getWord());
		//updateValues.put(SQLiteHelper.KEY_PATH, book.getBookPath());
	//	updateValues.put(SQLiteHelper.KEY_LEN, book.getBookPath());
		//updateValues.put(SQLiteHelper.KEY_MARK, book.getMark());
		updateValues.put(SQLiteHelper.KEY_CURRENT, current);
		/*
		try
		{
			String sql = "update " + DB_TABLE + " set " + SQLiteHelper.KEY_CURRENT + " = " +current + " where "+ " mark = " + 10;
			database.execSQL(sql);
			Log.e("data",sql);
		}
		catch(SQLiteException e)
		{
			e.printStackTrace();
		}*/
		//String strFilter = "_id=" + id;
		//String strFilter1 = SQLiteHelper.KEY_MARK +" = " + 10; 
		String[] args = {String.valueOf(id)};
		return database.update(DB_TABLE, updateValues, SQLiteHelper.KEY_ID+"=?", args);
		//return database.update(DB_TABLE, updateValues, null, null);
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
			books[i].setWord(cursor.getColumnIndex(SQLiteHelper.KEY_WORD));
			books[i].setBookPath(cursor.getString(cursor.getColumnIndex(SQLiteHelper.KEY_PATH)));
			books[i].setName(cursor.getString(cursor.getColumnIndex(SQLiteHelper.KEY_NAME)));
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
			Log.e("gelAllClasses",newclass.toString());
			classlist.add(newclass);
			cursor.moveToNext();
		}
		cursor.close();
		return classlist;
	}
	
	public List<Book> searchClass(String bookName)
	{
		List<Book> searchResult = new ArrayList<Book>();
		Cursor cursor=database.query(DB_TABLE, BOOK_TABLE_COLUMNS, SQLiteHelper.KEY_NAME+" like "+'\''+bookName+"%\'", null, null, null, null);
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
	
	public List<Book> searchClass(long id)
	{
		List<Book> searchResult = new ArrayList<Book>();
		Cursor cursor=database.query(DB_TABLE, BOOK_TABLE_COLUMNS, "_id " + "=" + id, null, null, null, null);
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
		//nbook.setId((cursor.getColumnIndex(SQLiteHelper.KEY_ID)));
		nbook.setId(cursor.getInt(cursor.getColumnIndex(SQLiteHelper.KEY_ID)));
		nbook.setWord(cursor.getInt(cursor.getColumnIndex(SQLiteHelper.KEY_WORD)));
		nbook.setBookPath(cursor.getString(cursor.getColumnIndex(SQLiteHelper.KEY_PATH)));
		nbook.setName(cursor.getString(cursor.getColumnIndex(SQLiteHelper.KEY_NAME)));
		//nbook.setLength(cursor.getInt(cursor.getColumnIndex(SQLiteHelper.KEY_LEN)));
		nbook.setMark(cursor.getInt(cursor.getColumnIndex(SQLiteHelper.KEY_MARK)));
		nbook.setCurrent(cursor.getInt(cursor.getColumnIndex(SQLiteHelper.KEY_CURRENT)));
		return nbook;
	}

}
