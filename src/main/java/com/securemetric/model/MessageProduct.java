package com.securemetric.model;

import java.util.ArrayList;
import java.util.List;

import com.securemetric.entity.Product;

public class MessageProduct {

	private String message = "";
	private List<Product> products = new ArrayList<Product>();
	private String error;
	
	public MessageProduct(String message, List<Product> products, String error) {
		this.message = message;
		this.products = products;
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
}
