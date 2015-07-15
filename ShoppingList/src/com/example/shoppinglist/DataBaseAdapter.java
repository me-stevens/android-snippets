package com.example.shoppinglist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DataBaseAdapter {

	// Tables in the database
	private static final String DATABASE_TABLE_LISTS = "tblLists";
	private static final String DATABASE_TABLE_ITEMS = "tblItems";

	// Table tblLists columns
	public static final String KEY_ROWID = "_id";
	public static final String KEY_NAME  = "name";
	
	// Table tblItems columns
	public static final String KEY_LISTID = "listId";
	public static final String KEY_CAT    = "category";
	public static final String KEY_ITEM   = "item";

	private Context mCtx;
	private SQLiteDatabase mDB;
	private DataBaseHelper mDBHelper;

	/*****************************************************************
	 * CONSTRUCTOR
	 ***************************************************************** */
	public DataBaseAdapter(Context context) {
		this.mCtx = context;
	}

	// Opens database. If it can't be opened, it creates it.
	// launches an exception if it can't open it or create it
	public DataBaseAdapter open() throws SQLException {
		mDBHelper = new DataBaseHelper(mCtx);
		mDB       = mDBHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		mDBHelper.close();
	}
	
	/*****************************************************************
	 * LISTS TABLE
	 ***************************************************************** */
	// Creates a new list. Returns rowID or -1 if there was an error
	public long createList(String name) {
		ContentValues cv = createListContentValues(name);
		return mDB.insert(DATABASE_TABLE_LISTS, null, cv);
	}

	// Updates list. Returns rowID or -1 if there was an error
	public boolean updateList(long rowId, String name) {
		ContentValues cv = createListContentValues(name);
		return (mDB.update(DATABASE_TABLE_LISTS, cv, KEY_ROWID + "=" + rowId, null) > 0);
	}

	// Deletes list. Takes the rowID. Returns true if it's deleted, false if not
	public boolean deleteList(long rowId) {
		return mDB.delete(DATABASE_TABLE_LISTS, KEY_ROWID + "=" + rowId, null) > 0;
	}

	// Returns a Cursor containing all items
	public Cursor allLists() {
		return mDB.query(DATABASE_TABLE_LISTS, new String[] { KEY_ROWID, KEY_NAME }, null, null, null, null, null);
	}

	// Returns the rowID or -1 if it doesn't exist
	public long getListRowId (String name) {
		
		Cursor mCursor = mDB.query(
				true, 
				DATABASE_TABLE_LISTS, 
				new String[] {KEY_ROWID, KEY_NAME }, 
				KEY_NAME + "='" + name + "'", 
				null, null, null, null, null);

		if (mCursor != null) {
			mCursor.moveToFirst();
			return mCursor.getLong(mCursor.getColumnIndex(KEY_ROWID));
		}

		return -1;
	}
	
	// Returns a Cursor pointing to the List of the specified rowID
	public Cursor list(long rowId) throws SQLException {
		Cursor mCursor = mDB.query(
				true, 
				DATABASE_TABLE_LISTS, 
				new String[] {KEY_ROWID, KEY_NAME }, 
				KEY_ROWID + "=" + rowId, 
				null, null, null, null, null);

		if (mCursor != null)
			mCursor.moveToFirst();

		return mCursor;
	}

	private ContentValues createListContentValues(String name) {
		ContentValues cv = new ContentValues();
		cv.put(KEY_NAME, name);
		return cv;
	}

	/*****************************************************************
	 * ITEMS TABLE
	 ***************************************************************** */
	// Creates a new item. Returns rowID or -1 if there was an error
	public long createItem(long listId, String category, String item) {
		ContentValues cv = createItemContentValues(listId, category, item);
		return mDB.insert(DATABASE_TABLE_ITEMS, null, cv);
	}

	// Updates item. Returns rowID or -1 if there was an error
	public boolean updateItem(long rowId, long listId, String category, String item) {
		ContentValues cv = createItemContentValues(listId, category, item);
		return (mDB.update(DATABASE_TABLE_ITEMS, cv, KEY_ROWID + "=" + rowId, null) > 0);
	}

	// Deletes list. Takes the rowID. Returns true if it's deleted, false if not
	public boolean deleteItem(long rowId) {
		return mDB.delete(DATABASE_TABLE_ITEMS, KEY_ROWID + "=" + rowId, null) > 0;
	}

	// Returns a Cursor containing all items
	public Cursor allItems() {
		return mDB.query(DATABASE_TABLE_ITEMS, new String[] { KEY_ROWID, KEY_LISTID, KEY_CAT, KEY_ITEM }, null, null, null, null, null);
	}
	
	// Returns a Cursor containing all items of a list
	public Cursor allItemsOfList(long listRowId) {
		return mDB.query(
				true, 
				DATABASE_TABLE_ITEMS, 
				new String[] {KEY_ROWID, KEY_LISTID, KEY_CAT, KEY_ITEM }, 
				KEY_LISTID + "=" + listRowId, 
				null, null, null, null, null);
	}
	
	// Deletes all items of list
	public boolean deleteAllItemsOfList(long listRowId) {
		return mDB.delete(DATABASE_TABLE_ITEMS,	KEY_LISTID + "=" + listRowId, null) > 0;
	}
	
	// Returns a Cursor containing all items of a category
	public Cursor allItemsOfCat(long listRowId, String category) {
		return mDB.query(
				true,
				DATABASE_TABLE_ITEMS, 
				new String[] {KEY_ROWID, KEY_LISTID, KEY_CAT, KEY_ITEM }, 
				KEY_LISTID + "=? AND " + KEY_CAT + "=?", 
	            new String[]{String.valueOf(listRowId), category}, 
	            null, null, null, null);	
	}

	// Returns a Cursor pointing to the Item of the specified rowID
	public Cursor item(long rowId) throws SQLException {
		Cursor mCursor = mDB.query(
				true, 
				DATABASE_TABLE_ITEMS, 
				new String[] {KEY_ROWID, KEY_LISTID, KEY_CAT, KEY_ITEM }, 
				KEY_ROWID + "=" + rowId, 
				null, null, null, null, null);

		if (mCursor != null)
			mCursor.moveToFirst();

		return mCursor;
	}
	
	private ContentValues createItemContentValues(long listId, String category, String item) {
		ContentValues cv = new ContentValues();
		cv.put(KEY_LISTID, listId);
		cv.put(KEY_CAT,    category);
		cv.put(KEY_ITEM,   item);
		return cv;
	}	

	/*****************************************************************
	 * ITEMS TABLE
	 ***************************************************************** */
	// Returns a Cursor containing all categories of a list
	public Cursor allCatsOfList(long listRowId) {
		return mDB.query(
				true, 
				DATABASE_TABLE_ITEMS, 
				new String[] {KEY_CAT }, 
				KEY_LISTID + "=" + listRowId, 
				null, null, null, null, null);
	}
}
