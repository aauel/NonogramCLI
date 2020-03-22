package com.techelevator.PortfolioProject;

public class Cell {
	
	private final String name;
	private int status;
	
	public Cell (String name) {
		this.name = name;
		setStatus(0);
	}

	public String getName() {
		return name;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	
}
