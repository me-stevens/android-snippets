package com.example.shoppinglist;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CatItemsAdapter extends BaseAdapter {
	
	private Context mContext;
	String category;
	ArrayList<CatItems> catItems = new ArrayList<CatItems>();

	public CatItemsAdapter (Context mContext, ArrayList<CatItems> catItems) {
		this.mContext = mContext;
		this.catItems = catItems;
	}

	@Override
	public int getCount() {
		return catItems.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	// For each element of the list
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View catrow;
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		if (convertView == null) {
			catrow = new View(mContext);
			catrow = inflater.inflate(R.layout.cat_row, parent, false);

			// Outer wrapper of cat_row.xml 
			//LinearLayout catitems = (LinearLayout) catrow.findViewById(R.id.catitems);
			
			// Category name
			TextView catname = (TextView) catrow.findViewById(R.id.catname);
			catname.setText(catItems.get(position).getCatName());

			// Category items
			ArrayList<View> itemsAL = new ArrayList<View>();
			for (String item : catItems.get(position).getCatItems()) {

//				View itemrow = new View(mContext);
//				itemrow      = inflater.inflate(R.id.itemrow, parent, false);
//
//				TextView catitem = (TextView) itemrow.findViewById(R.id.catitem);
//				View itemdiv     = (View) itemrow.findViewById(R.id.itemdiv);
//				
//				catitem.setText(item);
//				catitems.addView(itemrow);
				
				TextView catitem = new TextView(mContext);
				catitem.setText(item);
				itemsAL.add(catitem);
			}
			
			catrow.addTouchables(itemsAL);		
			
//		    catitem.setId(5);
//		    catitem.setLayoutParams(new LayoutParams(
//		            LayoutParams.FILL_PARENT,
//		            LayoutParams.WRAP_CONTENT));
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int)LayoutParams.WRAP_CONTENT, (int)LayoutParams.WRAP_CONTENT);
//            params.leftMargin=0;
//            params.topMargin=80;
//            catitem.setPadding(10, 0, 0, 0);
//            catitem.setText("" + name + "" + record + "" + age);
//            catitem.setTextSize((float) 20);
//            catitem.setLayoutParams(params);

//			ArrayAdapter<String> aa   = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, itemsAL);
//			catitems.setAdapter(aa);

		} else {
			catrow = (View) convertView;
		}

		return catrow;
	}
}
