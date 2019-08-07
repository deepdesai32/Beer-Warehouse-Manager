package com.bytecoders.client;



public class Clients {

	
	//clientID is autoimplemented in the database
	//however to update and delete a client, the user needs to know the client ID
	private String clientID;
	//name, address and phone number are used to add and update the client
	private String name;
	private String address;
	private String phoneNum;
	
	//default constructor
	public Clients() {
		
	}
	
	//constructor to pass all variables and encapsulate them in the private strings
	public Clients(String clientID, String name, String address, String phoneNum) {
		
		this.clientID = clientID;
		this.name = name;
		this.address = address;
		this.phoneNum = phoneNum;
	}

	//getters and setters for all variables
	public String getClientID() {
		return clientID;
	}

	public void setClientID(String clientID) {
		this.clientID = clientID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	//to string for Thiago's function
	@Override
	public String toString() {
		return name;
	}
	
	

}
