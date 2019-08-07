package com.bytecoders.losses;


public class Expired {

	//string for date
	private String date;

	//defualt constructor
	Expired() {

	}

	//constructor passes date to encapsulate
	Expired(String date) {
		this.date = date;
	}

	//getter and setter for date
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
