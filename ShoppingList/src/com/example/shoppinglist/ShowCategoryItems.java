package com.example.shoppinglist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

public class ShowCategoryItems extends ActionBarActivity {

	private DataBaseAdapter mDbHelper;
	private Cursor mCursor;
	private String listName;
	private TextView label;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_showcats);

		// Creates the adapter to be used in the list
		mDbHelper = new DataBaseAdapter(this);
		mDbHelper.open();
		
		// Get the message from the intent
		Intent intent         = getIntent();
		final long listRowId  = intent.getLongExtra("listRowId", -1);
		final String category = intent.getStringExtra("category");

		// Setting the activity title
		TextView listname = (TextView) findViewById(R.id.listname);
		mCursor           = mDbHelper.list(listRowId);
		listName          = mCursor.getString(mCursor.getColumnIndex(DataBaseAdapter.KEY_NAME));
		listname.setText(listName);
		
		// First button simply goes back to the categories
		Button button1 = (Button) findViewById(R.id.button1);
		button1.setText("Categories");
		button1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
	            Intent intent = new Intent(ShowCategoryItems.this, ShowCategories.class);
	            intent.putExtra("listRowId", listRowId);
	            startActivity(intent);
			}
		});		
		
		// Second button does nothing 
		Button button2 = (Button) findViewById(R.id.button2);
		button2.setEnabled(false); 

		// Setting title
		TextView title = (TextView) findViewById(R.id.textView2);
		title.setText("Category -> " + category);

		// Fill in the grid with the application items for that category
		if (listRowId != -1) {
		
			GridView gridview = (GridView) findViewById(R.id.gridCategories);
			gridview.setAdapter(new GridAdapter(this, category));
		
			// Clicking on a grid item saves item in database
			gridview.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> av, View v, int position, long l) {
					try {
						label = (TextView) v.findViewById(R.id.label);

						// Dialog box
						AlertDialog.Builder builder = new AlertDialog.Builder(ShowCategoryItems.this);
						builder.setTitle("Add a new item");
						builder.setMessage("Add " + label.getText() + " to the " + listName + " shopping list?" );

						// OK button: The data is retrieved and saved
						builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								mDbHelper.createItem(listRowId, category, label.getText().toString());
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
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_category_items, menu);
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
}
