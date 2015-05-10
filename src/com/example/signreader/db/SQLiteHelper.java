package com.example.signreader.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class SQLiteHelper extends SQLiteOpenHelper{
	private static final String DB_NAME = "book.db";//鏁版嵁搴撴枃浠剁殑鍚嶇О
	private static final String DB_TABLE = "bookinfo";//鏁版嵁搴撹〃鏍肩殑鍚嶇О 
	private static final int DB_VERSION = 1;//鏁版嵁搴撶増鏈�
	public static final String KEY_ID = "_id";
	public static final String KEY_NAME = "name";
	public static final String KEY_WORD = "word";
	public static final String KEY_PATH = "path";
	
    public SQLiteHelper(Context context)
    {
    	super(context,DB_NAME,null,DB_VERSION);
    }
    
	public SQLiteHelper(Context context, String name, CursorFactory factory, int version){
		super(context, name, factory, version);
	}
	
	private static final String DB_CREATE = " create table " + DB_TABLE + 
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