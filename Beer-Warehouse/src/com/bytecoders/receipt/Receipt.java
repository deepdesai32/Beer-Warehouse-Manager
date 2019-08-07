package com.bytecoders.receipt;

import java.sql.Date;

// This object holds the data used to generate a Receipt.
// it is fed by the DAOImpl class from this own package.
public class Receipt {
	// Client info
	private int cliID;
	private String cliName;
	private String cliAddress;
	//private Date cliRegDate;
	
	// order info
	private int orderID;
	private Date orderDate;
	
	// batch info
	private int batchID;
	private Date deliverDate;
	private Date expDate;
	private double cost;
	private double totalWeight;
	
	// item info for this batch
	private int itemID;
	private int itemQty;
	private String itemType;
	private double itemPrice;
	private String beerName;
	private int beercode;
	private double beerABV;
	private double beerPrice;
	private double beerIBU;

	// constructors
	public Receipt() {} 
	
	public Receipt(int cliID, String cliName, String cliAddress, int orderID, Date orderDate, int batchID,
			Date deliverDate, Date expDate, double cost, double totalWeight, int itemID, int itemQty, String itemType,
			double itemPrice, String beerName, int beercode, double beerABV, double beerPrice, double beerIBU) {
		this.cliID = cliID;
		this.cliName = cliName;
		this.cliAddress = cliAddress;
		this.orderID = orderID;
		this.orderDate = orderDate;
		this.batchID = batchID;
		this.deliverDate = deliverDate;
		this.expDate = expDate;
		this.cost = cost;
		this.totalWeight = totalWeight;
		this.itemID = itemID;
		this.itemQty = itemQty;
		this.itemType = itemType;
		this.itemPrice = itemPrice;
		this.beerName = beerName;
		this.beercode = beercode;
		this.beerABV = beerABV;
		this.beerPrice = beerPrice;
		this.beerIBU = beerIBU;
	}

	// this is the toString method that will generate the data to be returned ,
	// which will be printed to the screen an, optionally, saved to a text file.
	@Override
	public String toString() {
		StringBuilder data = new StringBuilder();
		data.append("orderID: " + orderID).append("\n");
		data.append("cliID=" + cliID).append("\n");
		data.append("cliName: " + cliName).append("\n");
		data.append("cliAddress: " + cliAddress).append("\n");
		data.append("orderDate: " + orderDate).append("\n");
		data.append("deliverDate: " + deliverDate).append("\n");
		data.append("expDate: " + expDate).append("\n");
		data.append("batchID: " + batchID).append("\n");
		data.append("cost: " + cost).append("\n");
		data.append("totalWeight: " + totalWeight).append("\n");
		data.append("itemID: " + itemID).append("\n");
		data.append("itemQty: " + itemQty).append("\n");
		data.append("itemType: " + itemType).append("\n");
		data.append("itemPrice: " + itemPrice).append("\n");
		data.append("beerName: " + beerName).append("\n");
		data.append("beercode: " + beercode).append("\n");
		data.append("beerABV: " + beerABV).append("\n");
		data.append("beerPrice: " + beerPrice).append("\n");
		data.append("beerIBU: " + beerIBU);
		
		return data.toString();
	}

	// getters / setters
	public int getCliID() {
		return cliID;
	}

	public void setCliID(int cliID) {
		this.cliID = cliID;
	}

	public String getCliName() {
		return cliName;
	}

	public void setCliName(String cliName) {
		this.cliName = cliName;
	}

	public String getCliAddress() {
		return cliAddress;
	}

	public void setCliAddress(String cliAddress) {
		this.cliAddress = cliAddress;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public int getOrderID() {
		return orderID;
	}

	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}

	public int getBatchID() {
		return batchID;
	}

	public void setBatchID(int batchID) {
		this.batchID = batchID;
	}

	public Date getDeliverDate() {
		return deliverDate;
	}

	public void setDeliverDate(Date deliverDate) {
		this.deliverDate = deliverDate;
	}

	public Date getExpDate() {
		return expDate;
	}

	public void setExpDate(Date expDate) {
		this.expDate = expDate;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public double getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(double totalWeight) {
		this.totalWeight = totalWeight;
	}

	public int getItemID() {
		return itemID;
	}

	public void setItemID(int itemID) {
		this.itemID = itemID;
	}

	public int getItemQty() {
		return itemQty;
	}

	public void setItemQty(int itemQty) {
		this.itemQty = itemQty;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public double getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(double itemPrice) {
		this.itemPrice = itemPrice;
	}

	public String getBeerName() {
		return beerName;
	}

	public void setBeerName(String beerName) {
		this.beerName = beerName;
	}

	public int getBeercode() {
		return beercode;
	}

	public void setBeercode(int beercode) {
		this.beercode = beercode;
	}

	public Double getBeerABV() {
		return beerABV;
	}

	public void setBeerABV(Double beerABV) {
		this.beerABV = beerABV;
	}

	public Double getBeerPrice() {
		return beerPrice;
	}

	public void setBeerPrice(Double beerPrice) {
		this.beerPrice = beerPrice;
	}

	public Double getBeerIBU() {
		return beerIBU;
	}

	public void setBeerIBU(Double beerIBU) {
		this.beerIBU = beerIBU;
	}

	
}
