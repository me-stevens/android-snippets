package com.example.shoppinglist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GridAdapter extends BaseAdapter {
	private Context mContext;
	private Integer[] iconsArray;
	private String[] labelsArray;

	// CATEGORIES
	private Integer[] catIcons = {R.drawable.bebidas, R.drawable.carnes, R.drawable.congelados, R.drawable.embutidos, 
			R.drawable.frutas, R.drawable.lacteos, R.drawable.panaderia, R.drawable.pescado, R.drawable.verduras};
	private String[] catLabels = {"Drinks", "Meats", "Frozen", "Cold meat", "Fruits", "Dairy", "Bakery", "Fish", "Vegetables"};

	// DRINKS
	private Integer[] drinksIcons = {R.drawable.bebidas, R.drawable.bebidas, R.drawable.bebidas, R.drawable.bebidas};
	private String[] drinksLabels = {"Water", "Coffee", "Tea", "Beer"};

	// MEATS
	private Integer[] meatsIcons = {R.drawable.carnes, R.drawable.carnes, R.drawable.carnes};
	private String[] meatsLabels = {"Cow", "Chicken", "Pork"};
	
	// FROZEN
	private Integer[] frozenIcons = {R.drawable.congelados, R.drawable.congelados};
	private String[] frozenLabels = {"Lasagna", "Ice cream"};

	// COLD MEAT
	private Integer[] coldMeatIcons = {R.drawable.embutidos};
	private String[] coldMeatLabels = {"Saussages"};

	// FRUITS
	private Integer[] fruitsIcons = {R.drawable.frutas, R.drawable.frutas, R.drawable.frutas};
	private String[] fruitsLabels = {"Apple", "Orange", "Banana"};

	// DAIRY
	private Integer[] dairyIcons = {R.drawable.lacteos};
	private String[] dairyLabels = {"Cheese"};
	
	// BAKERY
	private Integer[] bakeryIcons = {R.drawable.panaderia, R.drawable.panaderia};
	private String[] bakeryLabels = {"Bread", "Muffins"};

	// FISH
	private Integer[] fishIcons = {R.drawable.pescado, R.drawable.pescado, R.drawable.pescado};
	private String[] fishLabels = {"Shrimps", "Tuna", "Lobster"};

	// VEGGIES
	private Integer[] veggiesIcons = {R.drawable.verduras, R.drawable.verduras, R.drawable.verduras};
	private String[] veggiesLabels = {"Lettuce", "Tomato", "Carrots"};
	
	// Constructor for the categories
	public GridAdapter(Context c) {
		mContext    = c;
		iconsArray  = catIcons;
		labelsArray = catLabels;
	}
	
	// Constructor for the items of a category
	public GridAdapter (Context c, String category) {
		this(c);
		
		if (category.equalsIgnoreCase("Drinks")) {
			iconsArray  = drinksIcons;
			labelsArray = drinksLabels;
		}
		else if (category.equalsIgnoreCase("Meats")) {
			iconsArray  = meatsIcons;
			labelsArray = meatsLabels;
		}
		else if (category.equalsIgnoreCase("Frozen")) {
			iconsArray  = frozenIcons;
			labelsArray = frozenLabels;
		}
		else if (category.equalsIgnoreCase("Cold meat")) {
			iconsArray  = coldMeatIcons;
			labelsArray = coldMeatLabels;
		}
		else if (category.equalsIgnoreCase("Fruits")) {
			iconsArray  = fruitsIcons;
			labelsArray = fruitsLabels;
		}
		else if (category.equalsIgnoreCase("Dairy")) {
			iconsArray  = dairyIcons;
			labelsArray = dairyLabels;
		}
		else if (category.equalsIgnoreCase("Bakery")) {
			iconsArray  = bakeryIcons;
			labelsArray = bakeryLabels;
		}
		else if (category.equalsIgnoreCase("Fish")) {
			iconsArray  = fishIcons;
			labelsArray = fishLabels;
		}
		else if (category.equalsIgnoreCase("Vegetables")) {
			iconsArray  = veggiesIcons;
			labelsArray = veggiesLabels;
		}
	}

	@Override
	public int getCount() {
		return iconsArray.length;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	// Creates a view for each element using the grid_cell layout
	@Override
	public View getView (int position, View convertView, ViewGroup parent) {
		View gridView;
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		// If the object doesn't exist, create it
		if (convertView == null) {
			gridView = new View(mContext);

			// get layout from activity cell
			gridView            = inflater.inflate(R.layout.grid_cell, null);
			ImageView imageView = (ImageView) gridView.findViewById(R.id.icon);
			imageView.setImageResource(iconsArray[position]);
			TextView textView   = (TextView) gridView.findViewById(R.id.label);
			textView.setText(labelsArray[position]);

		} else {
			gridView = (View) convertView;
		}
		return gridView;
	}
}