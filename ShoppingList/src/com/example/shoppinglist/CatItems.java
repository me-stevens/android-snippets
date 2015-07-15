package com.example.shoppinglist;

import java.util.ArrayList;

public class CatItems {

	private String catname;
	private ArrayList<String> catitems;
	
	public CatItems (String catname, ArrayList<String> catitems) {
		this.catname  = catname;
		this.catitems = catitems;
	}
	
	public void setCatName (String catname) {
		this.catname = catname;
	}
	
	public String getCatName () {
		return catname;
	}

	public void setCatItems (ArrayList<String> catitems) {
		this.catitems = catitems;
	}
	
	public ArrayList<String> getCatItems () {
		return catitems;
	}	
}
