package com.example.test_sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper {
	private String tableName;
	
	public MyDatabaseHelper(Context context, String tableName) {
		super(context, tableName, null, 1);
		this.tableName = tableName;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table " + tableName + " ("
				+ "id integer primary key autoincrement,"
				+ "name text,"
				+ "email text);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}

}
