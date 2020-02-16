package com.techelevator.PortfolioProject;

/*								  
+---+---+---+---+---+
|00 |01 |02 |03 |O4 |   | 1 | 1 | 1 | 1 | 1 |	5
+---+---+---+---+---+
|05 |06 |07 |08 |09 |	| 1 | 0 | 1 | 0 | 0 |	1 1
+---+---+---+---+---+
|10 |11 |12 |13 |14 |	| 1 | 1 | 1 | 1 | 1 |   5
+---+---+---+---+---+
|15 |16 |17 |18 |19 |	| 0 | 0 | 1 | 0 | 1 |	1 1
+---+---+---+---+---+
|20 |21 |22 |23 |24 |	| 1 | 1 | 1 | 1 | 1 |	5
+---+---+---+---+---+	+---+---+---+---+---+
						  3   1   1   1   1
						  1   1   1   1   3
						      1       1    
*/

public class Nonogram {
	
	private String name;  				// name of the puzzle
	private int totalNumberOfCells;     // ex. 5x5 grid = 25
	private int[] onOffCounts;  		/* contains the number of alternating on/off values, 
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
	
	public Nonogram(String name, int totalNumberOfCells, int[] onOffCounts) {
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

	public int[] getOnOffCounts() {
		return this.onOffCounts;
	}
	
	public int[] getCellValues() {

	/*  This method converts int[] onOffValues to a new int[] of 0s and 1s, where the length
	    of the array is equal to the number of cells in the puzzle
	    
	    Example:  getCellValues([6, 1, 1, 2, 5, 2, 1, 1, 6]) -> 		 
		    [1, 1, 1, 1, 1, 1, 0, 1, 0, 0, 1, 1, 1, 1, 1, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1]
		 i = 00 01 02 03 04 05 06 07 08 09 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24	 
	*/		
		int[] thisPuzzleOnOffCounts = getOnOffCounts();
		int[] thisPuzzleCellValues = new int[getTotalNumberOfCells()];
		
		int cellIndex = 0;
		boolean on = true;  // start with the 'on' count first
		for (int num : thisPuzzleOnOffCounts) {  // ex. [6, 1, 1, 2, 5, 2, 1, 1, 6]
			while (num > 0) {
				if (on) thisPuzzleCellValues[cellIndex] = 1;
				else thisPuzzleCellValues[cellIndex] = 0;
				cellIndex++;
				num--;
			}
			on = !on;
		}		
		return thisPuzzleCellValues;
	}
}
