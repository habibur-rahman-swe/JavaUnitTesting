package com.mockito;

public class Email {
	
	private String msgContent;
	private String address;
	
	public void setContent(String msgContent) {
		this.msgContent = msgContent;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getAddress() {
		return this.address;
	}
	
}
