package org.unibl.etf.sell.model;



public class Item {
	
	int plateId;
	double price;
	double quantity;
	
	public Item() {
		
	}
	
	public Item(int plateId, double price, double quantity) {
		super();
		this.plateId = plateId;
		this.price = price;
		this.quantity = quantity;
	}
	public int getPlateId() {
		return plateId;
	}
	public void setPlateId(int plateId) {
		this.plateId = plateId;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getQuantity() {
		return quantity;
	}
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
	
	@Override
	public String toString() {
		return "- plate ID: " + plateId + ", price: " + price + ", quantity: " + ((Double)quantity).intValue();
	}
	
	
}
