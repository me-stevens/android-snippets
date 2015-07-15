package com.example.shoppinglist;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListsAdapter extends BaseAdapter {
	
	private Context mContext;
	ArrayList<List> ListsArray = new ArrayList<List>();

	public ListsAdapter(Context mContext, ArrayList<List> ListsArray) {
		this.mContext   = mContext;
		this.ListsArray = ListsArray;
	}

	@Override
	public int getCount() {
		return ListsArray.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	// For each element of the view
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View v;
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		if (convertView == null) {
			v = new View(mContext);
			v = inflater.inflate(R.layout.list_row, parent, false);

			TextView listname  = (TextView) v.findViewById(R.id.listname);
			listname.setText(ListsArray.get(position).getListname());

			TextView listcount = (TextView) v.findViewById(R.id.listcount);
			listcount.setText(ListsArray.get(position).getItemscount() + " products");
		} else {
			v = (View) convertView;
		}

		return v;
	}
}