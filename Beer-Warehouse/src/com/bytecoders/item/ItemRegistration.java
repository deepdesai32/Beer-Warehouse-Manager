package com.bytecoders.item;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ItemRegistration {

	private IntegerProperty idBeer;
	private StringProperty name;
	private StringProperty type;
	private StringProperty gradding;
	private StringProperty ibu;
	private DoubleProperty price;
	
	public ItemRegistration() {
		this.idBeer = new SimpleIntegerProperty();
		this.name = new SimpleStringProperty();
		this.type = new SimpleStringProperty();
		this.gradding = new SimpleStringProperty();
		this.ibu = new SimpleStringProperty();
		this.price = new SimpleDoubleProperty();	
	}

	public int getIdBeer() {
		return idBeer.get();
	}

	public void setIdBeer(int idBeer) {
		this.idBeer.set(idBeer);
	}

	public IntegerProperty getIdBeerProperty() {
		return idBeer;
	}
	//------------------------------------------------
	
	public String getName() {
		return name.get();
	}

	public void setName(String name) {
		this.name.set(name);
	}

	public StringProperty getNameProperty() {
		return name;
	}
	//------------------------------------------------
	
	public String getType() {
		return type.get();
	}

	public void setType(String type) {
		this.type.set(type);
	}

	public StringProperty getTypeProperty() {
		return type;
	}
	//------------------------------------------------
	
	public String getGradding() {
		return gradding.get();
	}

	public void setGradding(String gradding) {
		this.gradding.set(gradding);
	}
	
	public StringProperty getGraddingProperty() {
		return gradding;
	}
	//------------------------------------------------

	public String getIbu() {
		return ibu.get();
	}

	public void setIbu(String ibu) {
		this.ibu.set(ibu);
	}
	
	public StringProperty getIbuProperty() {
		return ibu;
	}
	//------------------------------------------------	
	
	public double getPrice() {
		return price.get();
	}

	public void setPrice(double price) {
		this.price.set(price);
	}
	
	public DoubleProperty getPriceProperty() {
		return price;
	}

	@Override
	public String toString() {
		return name.get();
	}	
	
	
}
