package org.unibl.etf.sell.model;

public class Order {

	String buyer;
	String buydate;
	String paydate;
	String address;
	Item[] items;
	
	public Order() {
		
	}
	
	public Order(String buyer, String buydate, String paydate, String address, Item[] items) {
		super();
		this.buyer = buyer;
		this.buydate = buydate;
		this.paydate = paydate;
		this.address = address;
		this.items = items;
	}

	public String getBuyer() {
		return buyer;
	}
	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}
	public String getBuydate() {
		return buydate;
	}
	public void setBuydate(String buydate) {
		this.buydate = buydate;
	}
	public String getPaydate() {
		return paydate;
	}
	public void setPaydate(String paydate) {
		this.paydate = paydate;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Item[] getItems() {
		return items;
	}
	public void setItems(Item[] items) {
		this.items = items;
	}
	
	
}
