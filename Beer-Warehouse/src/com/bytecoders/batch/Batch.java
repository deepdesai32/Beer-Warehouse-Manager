package com.bytecoders.batch;

import java.time.LocalDate;

import com.bytecoders.item.ItemRegistration;


//This Class holds the information of a Batch, and also has a reference to a Item Object
public class Batch {
	private int ID;
	private LocalDate receivedDate;
	private LocalDate expiryDate;
	private double cost;
	private int itemQuantity;
	private double totalWeight;
	private ItemRegistration item;

	
	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public LocalDate getReceivedDate() {
		return receivedDate;
	}

	public void setReceivedDate(LocalDate receivedDate) {
		this.receivedDate = receivedDate;
	}

	public LocalDate getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(LocalDate expiryDate) {
		this.expiryDate = expiryDate;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public int getItemQuantity() {
		return itemQuantity;
	}

	public void setItemQuantity(int itemQuantity) {
		this.itemQuantity = itemQuantity;
	}

	public double getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(double totalWeight) {
		this.totalWeight = totalWeight;
	}

	public ItemRegistration getItem() {
		return item;
	}

	public void setItem(ItemRegistration item) {
		this.item = item;
	}

	@Override
	public String toString() {
		return "Batch ID=" + ID + " cost=" + cost;
	}

	
	
}
