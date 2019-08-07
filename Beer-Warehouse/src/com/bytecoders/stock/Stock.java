package com.bytecoders.stock;

import com.bytecoders.batch.Batch;

//This class holds the info of a single stock (a stock is defined by an aisle and a shelf)
public class Stock {

	private int aisle;
	private int shelf;
	private Batch batch;

	public Stock() {
	}

	public int getAisle() {
		return aisle;
	}

	public void setAisle(int aisle) {
		this.aisle = aisle;
	}

	public int getShelf() {
		return shelf;
	}

	public void setShelf(int shelf) {
		this.shelf = shelf;
	}

	public Batch getBatch() {
		return batch;
	}

	public void setBatch(Batch batch) {
		this.batch = batch;
	}

	@Override
	public String toString() {
		return batch.toString();
	}
	
	

}
