package com.example.shoppinglist;

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

public class ShowCategories extends ActionBarActivity {

	private DataBaseAdapter mDbHelper;
	private Cursor mCursor;
	private long listRowId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_showcats);		

		// Creates the adapter to be used in the title
		mDbHelper = new DataBaseAdapter(this);
		mDbHelper.open();

		// Get the message from the intent
		Intent intent = getIntent();
		listRowId     = intent.getLongExtra("listRowId", -1);

		// Setting the activity title
		TextView listname = (TextView) findViewById(R.id.listname);
		mCursor           = mDbHelper.list(listRowId);
		listname.setText(mCursor.getString(mCursor.getColumnIndex(DataBaseAdapter.KEY_NAME)));

		// First button simply goes back
		Button button1 = (Button) findViewById(R.id.button1);
		button1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
	            Intent intent = new Intent(ShowCategories.this, ShowList.class);
	            intent.putExtra("listRowId", listRowId);
	            startActivity(intent);
			}
		});		
		
		// Second button does nothing
		Button button2 = (Button) findViewById(R.id.button2);
		button2.setEnabled(false); 

		// Setting the subtitle
		TextView title = (TextView) findViewById(R.id.textView2);
		title.setText("Categories");
		
		// Fill in the grid with the application categories
		if (listRowId != -1) {
		
			GridView gridview = (GridView) findViewById(R.id.gridCategories); 
			gridview.setAdapter(new GridAdapter(this));
		
			// Clicking on a grid item opens items list
			gridview.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> av, View v, int position, long l) {
					TextView label = (TextView) v.findViewById(R.id.label);
					Intent intent  = new Intent(ShowCategories.this, ShowCategoryItems.class);
					intent.putExtra("listRowId", listRowId);
					intent.putExtra("category" , label.getText().toString());
					startActivity(intent);
				}
			});
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_categories, menu);
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
