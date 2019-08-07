package com.bytecoders.expired;



public class RemoveExpired {

	//batch ID to remove batch based on batchid
	private int batchID;
	
	//default constructor
	RemoveExpired() {

	}
	
	//constructor to encapsulate batchID
	RemoveExpired(int batchID) {

		this.batchID = batchID;

	}
	
	//Batch ID getters and setters
	public int getBatchID() {
		return batchID;
	}

	public void setBatchID(int batchID) {
		this.batchID = batchID;
	}

}
