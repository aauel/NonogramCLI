package com.techelevator.PortfolioProject;

import java.util.ArrayList;
import java.util.List;

/*								  
+---+---+---+---+---+	+---+---+---+---+---+	+---+---+---+---+---+
|00 |01 |02 |03 |O4 |   |AA |BA |CA |DA |EA |   | 1 | 1 | 1 | 1 | 1 | 5
+---+---+---+---+---+	+---+---+---+---+---+	+---+---+---+---+---+
|05 |06 |07 |08 |09 |	|AB |BB |CB |DB |EB |	| 1 | 0 | 1 | 0 | 0 | 1 1
+---+---+---+---+---+	+---+---+---+---+---+	+---+---+---+---+---+
|10 |11 |12 |13 |14 |	|AC |BC |CC |DC |EC |	| 1 | 1 | 1 | 1 | 1 | 5
+---+---+---+---+---+	+---+---+---+---+---+	+---+---+---+---+---+
|15 |16 |17 |18 |19 |	|AD |BD |CD |DD |ED |	| 0 | 0 | 1 | 0 | 1 | 1 1
+---+---+---+---+---+	+---+---+---+---+---+	+---+---+---+---+---+
|20 |21 |22 |23 |24 |	|AE |BE |CE |DE |EE |	| 1 | 1 | 1 | 1 | 1 | 5
+---+---+---+---+---+	+---+---+---+---+---+	+---+---+---+---+---+
												  3   1   1   1   1
												  1   1   1   1   3
												      1       1    
*/

public class Nonogram {
	
	
	private String name;  				// name of the puzzle
	private int totalNumberOfCells;     // ex. 5x5 grid = 25
	private List<Integer> onOffCounts;  /* contains the number of alternating on/off values, 
										   starting with on.
										   onOffCounts[0] will be 0 if row[1]:column[1] is off
										   Ex. [6, 1, 1, 2, 5, 2, 1, 1, 6]
										       [6-on, 1-off, 1-on, 2-off, 5-on, 2-off, 1-on, 1-off, 6-on]
										       1 1 1 1 1
										       1 0 1 0 0
										       1 1 1 1 1
										       0 0 1 0 1
										       1 1 1 1 1										       
										 */
	
	public Nonogram(String name, int totalNumberOfCells, List<Integer> onOffCounts) {
		this.name = name;
		this.totalNumberOfCells = totalNumberOfCells;
		this.onOffCounts = onOffCounts;
	}

	public String getName() {
		return this.name;
	}
	public int getTotalNumberOfCells() {
		return this.totalNumberOfCells;
	}
	
	public List<Integer> getAllCellValues() {	
	/*  
	    This method converts the List onOffValues to a new list of 0s and 1s,
		representing the on/off value for every cell.
		 
		Example:  getCellValues([6, 1, 1, 2, 5, 2, 1, 1, 6]) --> 
		Result:   [1, 1, 1, 1, 1, 1, 0, 1, 0, 0, 1, 1, 1, 1, 1, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1]
	*/
		List<Integer> cellValues = new ArrayList<Integer>();
		boolean on = true;  // start with the 'on' count first
		for (int num : onOffCounts) {  // ex. [6, 1, 1, 2, 5, 2, 1, 1, 6]
			for (; num > 0; num--) {
				if (on) cellValues.add(1);
				else cellValues.add(0);
			}
			on = !on;
		}		
		return cellValues;
	}
	
	public List<String> getListOfOnCellNames() {
	/* 
	  	This method takes the list of all cell values (0s and 1s)
	  	and returns a list of cell names for only those cells that are on.
	    
	    Example:  [ 1,  1,  1,  1,  1,  1,  0,  1,  0,  0,  1,  1,  1,  1,  1,  0,  0,  1,  0,  1,  1,  1,  1,  1,  1] -->
				  [AA, BA, CA, DA, EA, AB, BB, CB, DB, EB, AC, BC, CC, DC, EC, AD, BD, CD, DD, ED, AE, BE, CE, DE, EE]
		    	  [on, on, on, on, on, on,   , on,   ,   , on, on, on, on, on,   ,   , on,   , on, on, on, on, on, on] -->
		Result:   [AA, BA, CA, DA, EA, AB, CB, AC, BC, CC, DC, EC, CD, ED, AE, BE, CE, DE, EE]
	*/	
		List<Integer> allCellValues = getAllCellValues();
		List<String> gridCellNames = getListOfCellNames();
		
		List<String> onCellNames = new ArrayList<String>();
		
		for (int cellIndex = 0; cellIndex < totalNumberOfCells; cellIndex++) {
			if (allCellValues.get(cellIndex) == 1) {
				String cellName = gridCellNames.get(cellIndex);
				onCellNames.add(cellName);
			}
		}
		return onCellNames;
	}
	
	public List<String> getListOfCellNames() {
	/*
	 	Returns a list of cellNames moving from left-to-right,
	 	and top-to-bottom, starting with AA.
	 	
	 	Example:  getListOfCellNames(25) -->		  
	 	Result:   [AA, BA, CA, DA, EA, AB, BB, CB, DB, EB, AC, BC, CC, DC, EC, AD, BD, CD, DD, ED, AE, BE, CE, DE, EE]
	 */	
		List<String> gridCellNames = new ArrayList<String>();
		double columnCount = Math.sqrt(totalNumberOfCells);
		char row = 'A';
		for (int rowNum = 0; rowNum < columnCount; rowNum++, row++) {
			char col = 'A';
			for (int colNum = 0; colNum < columnCount; colNum++, col++) {
				gridCellNames.add(Character.toString(col) + row);
			}
		}
		return gridCellNames;
	}
	
	
}
