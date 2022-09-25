package com.chick.dto;

public class Criteria {
	private int currentPage;
	private int rowPerPage;
	private String category;
	private String key;
	
	
	public Criteria() {
		super();
	}

	public Criteria(int currentPage, int rowPerPage) {
		super();
		this.currentPage = currentPage;
		this.rowPerPage = rowPerPage;
	}


	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public int getCurrentPage() {
		return currentPage;
	}



	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}



	public int getRowPerPage() {
		return rowPerPage;
	}



	public void setRowPerPage(int rowPerPage) {
		this.rowPerPage = rowPerPage;
	}



}
