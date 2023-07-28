package com.mockito;

public class Validator {
	
	public void validate(Email email) {
		if (email.getAddress() == null) {
			throw new IllegalArgumentException();
		}
	}
	
}
