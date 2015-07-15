package com.example.shoppinglist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "ShoppingList";
	private static final int DATABASE_VERSION = 1;
	
	/*********************************************************************
	 *  Information separated in two tables because we have one screen
	 *  to just add shopping lists or delete them:
	 *********************************************************************/
	
	// Lists table
	private static final String DATABASE_CREATE_1 = "create table tblLists "
			+ "(_id integer primary key autoincrement, "
			+ "name text not null)";
	
	// Items table, listId is a reference to tblLists _id column
	private static final String DATABASE_CREATE_2 = "create table tblItems "
			+ "(_id integer primary key autoincrement, "
			+ "listId integer not null,"
			+ "category text not null,"
			+ "item text not null);";

	public DataBaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE_1);
		database.execSQL(DATABASE_CREATE_2);
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {

		Log.w(DataBaseHelper.class.getName(), "Updating " + oldVersion + " to " + newVersion + ". All stored data will be destroyed.");
		database.execSQL("DROP TABLE IF EXISTS tblLists");

		onCreate(database);
	}
}
