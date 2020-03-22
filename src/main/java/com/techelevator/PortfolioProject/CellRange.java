package com.techelevator.PortfolioProject;

import java.util.ArrayList;
import java.util.List;

public class CellRange {
	
	private final String name;
	private List<Cell> cells;
	
	public CellRange (String name) {
		this.name = name;
		setCells(new ArrayList<Cell>());
	}

	public String getName() {
		return name;
	}

	public List<Cell> getCells() {
		return cells;
	}

	public void setCells(List<Cell> cells) {
		this.cells = cells;
	}
	
	public int getNumberOfCells() {
		return cells.size();
	}

}
