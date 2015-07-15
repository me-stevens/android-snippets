package com.example.shoppinglist;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends ListActivity {

	private DataBaseAdapter mDbHelper;
	private Cursor mCursor;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Creates the adapter to be used in the list
		mDbHelper = new DataBaseAdapter(this);
		mDbHelper.open();
		
		// Fill in the list
		fillData();
		
		// Button add that pops-up the dialog box
		Button buttonAdd = (Button) findViewById(R.id.buttonAdd);
		buttonAdd.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					// Dialog box
					AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
					builder.setTitle("Add a new shopping list");
					builder.setMessage("Name of the new list:");

					// EditView for the name
					final EditText inputField = new EditText(MainActivity.this);
					builder.setView(inputField);

					// OK button: The data is retrieved and saved
					builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							if (mDbHelper.createList(inputField.getText().toString()) != -1)
								fillData();
						}
					});

					// Cancel button: Dismiss
					builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});
					
					// Finally, show dialog
					builder.show();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		// When a list item is clicked.
		getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> av, View v, int position, long l) {
		        // Prepare to send the list row id
		        Intent intent     = new Intent(MainActivity.this, ShowList.class);
		        TextView listname = (TextView) v.findViewById(R.id.listname);
		        long listRowId    = mDbHelper.getListRowId(listname.getText().toString());
		        intent.putExtra("listRowId", listRowId);
		        startActivity(intent);
			}
		});
		
		// When a list item is long-clicked, 
		getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> av, View v, int position, long l) {
				try {
					// Get the view with the list name
					final TextView listname = (TextView) v.findViewById(R.id.listname);

					// Dialog box
					AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
					builder.setTitle("Delete shopping list");
					builder.setMessage("Delete " + listname.getText().toString() + " list?");

					// OK button: The list is deleted
					builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							long listRowId = mDbHelper.getListRowId(listname.getText().toString());
							if(mDbHelper.deleteList(listRowId)) {
								mDbHelper.deleteAllItemsOfList(listRowId);
								fillData();
							}
						}
					});

					// Cancel button: Dismiss
					builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});
					
					// Finally, show dialog
					builder.show();
					
				} catch (Exception e) {
					e.printStackTrace();
				}

				return true;
			}
		}); 	
	}

	
//  @Override
//  protected void onListItemClick(ListView l, View v, int position, long id) {
//      super.onListItemClick(l, v, position, id);
//
//      // Prepare to send the list row id
//      Intent intent     = new Intent(MainActivity.this, ShowList.class);
//      TextView listname = (TextView) v.findViewById(R.id.listname);
//      long listRowId    = mDbHelper.getListRowId(listname.getText().toString());
//      intent.putExtra("listRowId", listRowId);
//      
//      startActivity(intent);
//  }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

    /***********************************************************
	 * Method to fill in data from tblLists into 
	 * list ListView using the list_row definition
	 * (has a listicon, listname and listcount) and
	 * a custom adapter ListsAdapter
	 ***********************************************************/
	private void fillData() {

		mCursor = mDbHelper.allLists();
		ArrayList<List> listsArray = new ArrayList<List>();
		
		if (mCursor != null && mCursor.moveToFirst()) {
			
			do {
				long listRowId  = mCursor.getLong(mCursor.getColumnIndex(DataBaseAdapter.KEY_ROWID));
				String listname = mCursor.getString(mCursor.getColumnIndex(DataBaseAdapter.KEY_NAME));
				int itemscount  = 0;
				
				Cursor itemscur = mDbHelper.allItemsOfList(listRowId);
				if (itemscur   != null)
					itemscount  = itemscur.getCount();

				listsArray.add(new List(listname, itemscount));
				
			} while (mCursor.moveToNext());	
		}

		ListsAdapter lists = new ListsAdapter(this, listsArray);
		setListAdapter(lists);
		
		// If there are lists, hide textview
		TextView noLists = (TextView) findViewById(R.id.nolists);
		if (mCursor != null)
			noLists.setVisibility(View.INVISIBLE);
		else
			noLists.setVisibility(View.VISIBLE);
	}
}
