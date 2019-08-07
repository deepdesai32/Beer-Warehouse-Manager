package com.bytecoders.employee;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Employee {
	
	//empolyee variables
	private IntegerProperty idEmployee;
	private StringProperty firstName;
	private StringProperty lastName;
	private StringProperty email;

	//employee constructor using property method for tableview purpose
	public Employee() {
		this.idEmployee = new SimpleIntegerProperty();
		this.firstName = new SimpleStringProperty();
		this.lastName = new SimpleStringProperty();
		this.email = new SimpleStringProperty();
	}

	//get and setters
	public int getIdEmployee() {
		return idEmployee.get();
	}

	public void setIdEmployee(int idEmployee) {
		this.idEmployee.set(idEmployee);
	}
	
	public IntegerProperty getEmployeeId() {
		return idEmployee;
	}
	//-----------------------	
	public String getFirstName() {
		return firstName.get();
	}

	public void setFirstName(String firstName) {
		this.firstName.set(firstName);
	}
	public StringProperty getEmployeeFirstName() {
		return firstName;
	}
	//-----------------------	
	public String getLastName() {
		return lastName.get();
	}

	public void setLastName(String lastName) {
		this.lastName.set(lastName);;
	}
	public StringProperty getEmployeeLastName() {
		return lastName;
	}
	//-----------------------	
	public String getEmail() {
		return email.get();
	}

	public void setEmail(String email) {
		this.email.set(email);
	}
	public StringProperty getEmployeeEmail() {
		return email;
	}
	//-----------------------	

	//override method returning to string
	@Override
	public String toString() {
		return firstName.get() + " " + lastName.get();
	}

}