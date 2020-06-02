package com.ahankoob.wamcalculator;

/**
 * Created by abolfazl on 4/21/2020.
 */

public class HomeNav {
	private int Id;
	private String Title;
	private int Icon;
	public HomeNav(){}
	public HomeNav(int id, String title, int icon) {
		Id = id;
		Title = title;
		Icon = icon;
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public int getIcon() {
		return Icon;
	}

	public void setIcon(int icon) {
		Icon = icon;
	}
}
