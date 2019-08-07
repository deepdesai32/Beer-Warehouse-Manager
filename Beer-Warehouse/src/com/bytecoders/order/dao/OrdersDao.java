package com.bytecoders.order.dao;

import java.util.List;

import com.bytecoders.client.Clients;
import com.bytecoders.employee.Employee;
import com.bytecoders.order.Order;

//Interface for handling the Orders Database 
public interface OrdersDao {

	public boolean insertOrder(Order order);	
	
	public List<Order> selectAllOrders();

	public Order selectOrder(Order targetOrder);
	
	public List<Clients> selectAllClients();

	public List<Employee> selectAllEmployees();
}
