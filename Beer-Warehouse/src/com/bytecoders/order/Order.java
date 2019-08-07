package com.bytecoders.order;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.bytecoders.batch.Batch;
import com.bytecoders.client.Clients;
import com.bytecoders.employee.Employee;

//This class holds the information of an Order, including a list of batches it may have
public class Order {
	private int ID;
	private LocalDate orderDate;
	private double totalPrice; 
	private Employee employee;
	private Clients client;
	private List<Batch> batchList = new ArrayList<Batch>();
	
	//Calculate the cost of all the batches in this order
	public double calcTotalCost() {
		double cost = 0;
		//Loop through all the batches on this object, summing the Batch Cost to the order cost
		for (Batch batch : batchList) {
			cost += batch.getCost();
		}
		return cost;
	}
	
	//Calculates the profit based on the cost of all the orders and the price of the sale
	public double calcProfit() {
		//Calculates the total cost of this Order, and subtract it from the order selling price
		return (this.totalPrice - this.calcTotalCost()); 
	}
	

	public double getTotalPrice() {
		return totalPrice;
	}


	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}


	public Order() {
		super();
	}
	
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public LocalDate getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public Clients getClient() {
		return client;
	}
	public void setClient(Clients client) {
		this.client = client;
	}
	public List<Batch> getBatchList() {
		return batchList;
	}
	public void setBatchList(List<Batch> batchList) {
		this.batchList = batchList;
	}
	
	public void addBatch(Batch batch) {
		this.batchList.add(batch);
	}
	
	//Remove an informed batch from this order
	public void removeBatch(Batch batch) {
		this.batchList.remove(batch);
	}

}
