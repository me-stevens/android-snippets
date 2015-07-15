package com.example.shoppinglist;

import java.util.ArrayList;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class ShowList extends ActionBarActivity {

	private DataBaseAdapter mDbHelper;
	private long listRowId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_showlist);

		// Creates the adapter to be used in the list
		mDbHelper = new DataBaseAdapter(this);
		mDbHelper.open();

		// Get the message from the intent
		Intent intent = getIntent();
		listRowId     = intent.getLongExtra("listRowId", -1);
		
		// Retrieve the row of the relevant table
		if (listRowId != -1) {
			
			// Setting the activity title
			TextView listname = (TextView) findViewById(R.id.listname);
			Cursor c          = mDbHelper.list(listRowId);
			listname.setText(c.getString(c.getColumnIndex(DataBaseAdapter.KEY_NAME)));
			
			// First button does nothing
			Button button1 = (Button) findViewById(R.id.button1);
			button1.setEnabled(false); 
			
			// Second button opens category list
			Button button2 = (Button) findViewById(R.id.button2);
			button2.setText("Add items");
			button2.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
		            Intent intent = new Intent(ShowList.this, ShowCategories.class);
		            intent.putExtra("listRowId", listRowId);
		            startActivity(intent);
				}
			});

			// Filling in the list
			ListView listitems = (ListView) findViewById(R.id.listitems);
			fillData(listitems);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_list, menu);
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

	private void fillData(ListView listitems) {

		// Get the categories
		Cursor mCursor = mDbHelper.allCatsOfList(listRowId);
		ArrayList<CatItems> listItemsArray = new ArrayList<CatItems>();

		if (mCursor != null && mCursor.moveToFirst()) {
			
			do {
				String catname             = mCursor.getString(mCursor.getColumnIndex(DataBaseAdapter.KEY_CAT));
				ArrayList<String> catitems = new ArrayList<String>();
				Cursor itemscur            = mDbHelper.allItemsOfCat(listRowId, catname);
				if (itemscur != null) {
					itemscur.moveToFirst();
					do {
						catitems.add(itemscur.getString(itemscur.getColumnIndex(DataBaseAdapter.KEY_ITEM)));
					} while (itemscur.moveToNext());
				}
				
				// Before moving to the next category, add the data of the current one 
				listItemsArray.add(new CatItems(catname, catitems));
				//drawViews(listitems, catname, catitems);
				
			} while (mCursor.moveToNext());

			CatItemsAdapter itemsByCat = new CatItemsAdapter(this, listItemsArray);
			listitems.setAdapter(itemsByCat);
		}
	}
	
	// This function is called for each category
//	private void drawViews(View listitems, String catname, ArrayList<String> catitems) {
//		LinearLayout showlist   = (LinearLayout) findViewById(R.id.showlist);
//		LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);		
//		View catrow             = inflater.inflate(R.layout.cat_row, showlist, false);
//		showlist.addView(catrow);
//
//		TextView catnameT = (TextView) catrow.findViewById(R.id.catname);
//		catnameT.setText(catname);
//
//		for (String item : catitems) {
//			Toast.makeText(this, item, Toast.LENGTH_SHORT).show();
//			View itemrow  = inflater.inflate(R.id.itemrow, (ViewGroup) catrow, false);
//			showlist.addView(itemrow);
//
//			TextView catitem = (TextView) itemrow.findViewById(R.id.catitem);
//			View itemdiv     = (View) itemrow.findViewById(R.id.itemdiv);
//			catitem.setText(item);
//			
//			showlist.addView(catitem);
//			showlist.addView(itemdiv);
//		}
//	}
}
