package com.mockito;

public class TemplateEngine {

	public String prepareMessage(Client client, Template template) {
		return "Some template";
	}
	
	public boolean evaluateTemplate(Template template) {
		return false;
	}
	
}
