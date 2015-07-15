package com.example.shoppinglist;

public class List {

	private String listname;
	private int itemscount;
	
	public List (String listname, int itemscount) {
		this.listname   = listname;
		this.itemscount = itemscount;
	}
	
	public void setListname (String listname) {
		this.listname = listname;
	}
	
	public String getListname () {
		return listname;
	}

	public void setItemscount (int itemscount) {
		this.itemscount = itemscount;
	}
	
	public int getItemscount () {
		return itemscount;
	}	
}
