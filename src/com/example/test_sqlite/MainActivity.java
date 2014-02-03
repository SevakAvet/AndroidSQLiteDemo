package com.example.test_sqlite;

import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity implements OnClickListener {
	private static final String TAG = "sevakLogs";
	private static final String TABLE_NAME = "contacts";
	
	private EditText etName;
	private EditText etEmail;
	private EditText etId;
	
	private Button btnInsert;
	private Button btnShow;
	private Button btnClear;
	private Button btnUpdate;
	private Button btnDelete;
	
	private MyDatabaseHelper dbHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_view);
		
		init();		
	}
	
	private void init() {
		findViews();
		initListeners();
		
		dbHelper = new MyDatabaseHelper(this, TABLE_NAME);
	}
	
	private void findViews() {
		etName = (EditText) findViewById(R.id.etName);
		etEmail = (EditText) findViewById(R.id.etEmail);
		etId = (EditText) findViewById(R.id.etId);
		
		btnInsert = (Button) findViewById(R.id.btnInsert);
		btnShow = (Button) findViewById(R.id.btnShow);
		btnClear = (Button) findViewById(R.id.btnClear);
		btnUpdate = (Button) findViewById(R.id.btnUpdate);
		btnDelete = (Button) findViewById(R.id.btnDelete);
	}
	
	private void initListeners() {
		btnInsert.setOnClickListener(this);
		btnShow.setOnClickListener(this);
		btnClear.setOnClickListener(this);
		btnUpdate.setOnClickListener(this);
		btnDelete.setOnClickListener(this);
	}

	private void insert(SQLiteDatabase db) {
		ContentValues cv = new ContentValues();
		
		String name = etName.getText().toString();
		String email = etEmail.getText().toString();
		
		Log.d(TAG, "---- INSERTING IN " + TABLE_NAME + " ----");
		
		cv.put("name", name);
		cv.put("email", email);
		
		long rowId = db.insert(TABLE_NAME, null, cv);
		Log.d(TAG, "Row inserted, id =  " + rowId);
	}
	
	private void showAll(SQLiteDatabase db) {
		Log.d(TAG, "---- SHOWING ALL DATA FROM " + TABLE_NAME + " ----");
		
		Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
		
		if(cursor.moveToFirst()) {
			int idColIndex = cursor.getColumnIndex("id");
			int nameColIndex = cursor.getColumnIndex("name");
			int emailColIndex = cursor.getColumnIndex("email");
			
			do {
				Log.d(TAG, "Id = " + cursor.getInt(idColIndex) + ","
						+ "Name = " + cursor.getString(nameColIndex) + ","
						+ "Email = " + cursor.getString(emailColIndex));
			} while(cursor.moveToNext());
		} else {
			Log.d(TAG, "TABLE IS EMPTY");
		}
		
		cursor.close();
	}
	
	private void clearTable(SQLiteDatabase db) {
		Log.d(TAG, "---- REMOVING TABLE " + TABLE_NAME + " ----");
		
		int deletedCount = db.delete(TABLE_NAME, null, null);
		Log.d(TAG, "Deleted " + deletedCount + " rows");
	}
	
	private void update(SQLiteDatabase db) {
		String id = etId.getText().toString();
		if(id.equals("")) {
			Log.d(TAG, "Id is empty!");
			return;
		}
		
		Log.d(TAG, "---- UPDATING " + TABLE_NAME + " id = " + id + " ----");
		
		String name = etName.getText().toString();
		String email = etEmail.getText().toString();
		
		ContentValues cv = new ContentValues();
		cv.put("name", name);
		cv.put("email", email);
		
		int updateCount = db.update(TABLE_NAME, cv, "id = ?", new String[]{ id });
		Log.d(TAG, "Updated rows count = " + updateCount);
	}
	
	private void delete(SQLiteDatabase db) {
		Log.d(TAG, "---- Deleting row ----");
		
		String id = etId.getText().toString();
		if(id.equals("")) {
			Log.d(TAG, "Id is empty!");
			return;
		}
		
		int delCount = db.delete(TABLE_NAME, "id = " + id, null);
		Log.d(TAG, "Deleted rows count = " + delCount);		
	}
	
	@Override
	public void onClick(View v) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();		
		
		switch (v.getId()) {
		case R.id.btnInsert:
			insert(db);			
			break;
			
		case R.id.btnShow:
			showAll(db);
			break;
			
		case R.id.btnClear:
			clearTable(db);
			break;
		
		case R.id.btnUpdate:
			update(db);
			break;
			
		case R.id.btnDelete:
			delete(db);
			break;
		}
		
		dbHelper.close();
	}

}
