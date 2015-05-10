package com.example.signreader.db;

import com.example.signreader.domain.Book;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

//This class is temporarily unused. 2015--5--10


public class DBAdapter {
	private static final String DB_NAME = "book.db";//鏁版嵁搴撴枃浠剁殑鍚嶇О
	private static final String DB_TABLE = "bookinfo";//鏁版嵁搴撹〃鏍肩殑鍚嶇О 
	private static final int DB_VERSION = 1;//鏁版嵁搴撶増鏈�
	
	//鏁版嵁搴撹〃涓殑灞炴�у悕绉�
	public static final String KEY_ID = "_id";
	public static final String KEY_NAME = "name";
	public static final String KEY_WORD = "word";
	public static final String KEY_PATH = "path";
	
	private SQLiteDatabase db;
	private final Context context;
	
	private DBOpenHelper dbOpenHelper;
	
	private static class DBOpenHelper extends SQLiteOpenHelper{
		public DBOpenHelper(Context context, String name, CursorFactory factory, int version){
			super(context, name, factory, version);
		}
		
		private static final String DB_CREATE = " create table" + DB_TABLE + 
				" ( " + KEY_ID +" integer primary key autoincrement, "+
				KEY_NAME+" text not null, "+KEY_WORD+" integer,"+ KEY_PATH + " text);";
		
		public void onCreate(SQLiteDatabase _db){
			_db.execSQL(DB_CREATE);
		}
		
		public void onUpgrade(SQLiteDatabase _db, int _oldVersion, int _newVersion){
			_db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
			onCreate(_db);
		}
	}
	
	public DBAdapter(Context _context){
		context = _context;
	}
	
	//鎵撳紑鏁版嵁搴�
	public void open() throws SQLiteException{
		dbOpenHelper = new DBOpenHelper(context, DB_NAME, null, DB_VERSION);
		try{
			db = dbOpenHelper.getWritableDatabase();
		}catch(SQLiteException ex){
			db = dbOpenHelper.getReadableDatabase();
		}
	}
	
	public void close(){
		if(db != null){
			db.close();
			db = null;
		}
	}
	
	public long insert(Book book){
		ContentValues newValues = new ContentValues();
		
		newValues.put(KEY_NAME, book.getName());
		newValues.put(KEY_WORD, book.getWord());
		newValues.put(KEY_PATH, book.getBookPath());
		
		return db.insert(DB_TABLE, null, newValues);
	}
	
	public long deleteAllData(){
		return db.delete(DB_TABLE, null, null);
	}
	
	public long deleteOneData(long id){
		return db.delete(DB_TABLE, KEY_ID + "="+ id, null);
	}
	
	public long updateOneData(long id, Book book){
		ContentValues updateValues = new ContentValues();
		updateValues.put(KEY_NAME, book.getName());
		updateValues.put(KEY_PATH, book.getBookPath());
		updateValues.put(KEY_WORD, book.getWord());
		return db.update(DB_TABLE, updateValues, KEY_ID + "=" + id, null);
	}
	
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
	}
}
