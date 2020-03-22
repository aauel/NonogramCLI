package com.techelevator.PortfolioProject;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/*******************************************************************
 * 
 * @author Angie Auel
 *
 * ISSUES - This doesn't YET work with completely empty rows/columns.
 * Also, only works for squares, where number of row and columns are equal.
 * 
 * Ideas 
 * - allow ranges to be entered in reverse order
 * - don't turn off a cell if user enters a range to off or blank (like on phone)
 * - Make a cell an object?  
 * - Make row and column objects which are made up of cell objects, so that cells have same memory address
 * - Puzzle is made up of row/column objects?
 * - create constants for on, off, blank
 * 
 *******************************************************************/

public class NonogramGrid {

	private static List<String> GRID_CELL_NAMES;

	private int totalNumberOfCells;
	private double columnRowCount;
	private List<Integer> cellValues;
	

	public NonogramGrid(Nonogram newPuzzle) {
		totalNumberOfCells = newPuzzle.getTotalNumberOfCells();
		columnRowCount = Math.sqrt(totalNumberOfCells);
		cellValues = newPuzzle.getAllCellValues();
	}
	

	public void printGrid() {

		// For each row, create a stack to hold the counts of filled-in squares that are separated by blank squares 
		List<Stack<Integer>> listOfRowCounts = new ArrayList<>();
		for (int i = 0; i < totalNumberOfCells; i += columnRowCount) {
			listOfRowCounts.add(getRowCounts(i));
		}
		
		
		int leadingRowSpacesNeeded = 3 * getMaxCountForHeaders(listOfRowCounts);
		List<String> rowHeaders = getRowHeaders(listOfRowCounts);
		/*
		 * rowHeaders(0) = "  5 "; 
		 * rowHeaders(1) = "1 1 "; 
		 * rowHeaders(2) = "  5 ";
		 * rowHeaders(3) = "1 1 "; 
		 * rowHeaders(4) = "  5 ";
		 */

		// For each column, create a stack to hold the counts of filled-in squares that are separated by blank squares
		List<Stack<Integer>> listOfColumnCounts = new ArrayList<>();
		for (int i = 0; i < columnRowCount; i++) {
			listOfColumnCounts.add(getColumnCounts(i));
		}
		Stack<String> columnHeaders = getColumnHeaders(listOfColumnCounts);
		/*
		 * columnHeaders(2) = "      1       1      "; 
		 * columnHeaders(1) = "  3   1       1   1  "; 
		 * columnHeaders(0) = "  1   1   5   1   3  ";
		 */

		List<String> coreGridRows = getCoreGridRows();
		/*
		 * gridRows(0) = "| O | O | O | O | O |" 
		 * gridRows(1) = "| O |   | O |   |   |"
		 * gridRows(2) = "| O | O | O | O | O |" 
		 * gridRows(3) = "|   |   | O |   | O |"
		 * gridRows(4) = "| O | O | O | O | O |"
		 */

		String leadingSpaces = "";
		for (int i = 0; i < leadingRowSpacesNeeded; i++) {
			leadingSpaces += " ";
		}
		char columnChar = 'A';
		String columnLabels = leadingSpaces;
		String rowMarker = leadingSpaces + "+";
		for (int i = 0; i < columnRowCount; i++, columnChar++) {
			columnLabels += "  " + columnChar + " ";
			rowMarker += "---+";
		}

		// print column header counts
		for (int i = columnHeaders.size(); i > 0; i--) {
			System.out.println(leadingSpaces + columnHeaders.pop());
		}
		System.out.println(rowMarker);

		// print a rowMarker before each row, and at the end to complete the box
		// each row starts with the row header counts, followed by the core grid rows
		char rowChar = 'A';
		for (int i = 0; i < columnRowCount; i++, rowChar++) {
			System.out.println(rowHeaders.get(i) + coreGridRows.get(i) + " " + rowChar);
			System.out.println(rowMarker);
		}
		System.out.println(columnLabels + "\n");

	}
	
	private Stack<Integer> getRowCounts(int cellNumber) {

		Stack<Integer> currentRow = new Stack<>();
		int tempSum = 0;

		for (int i = 0; i < columnRowCount; i++) {	// For each cell in the current row only...			
			if (cellValues.get(cellNumber) == 1) {	// tempSum will increment for every successive 1 found in the row
				tempSum++;
			} else if (tempSum > 0) {				// When a 0 is found after successive 1s, save the tempSum and reset to 0
				currentRow.push(tempSum);
				tempSum = 0;
			}
			cellNumber++;
		}
		if (tempSum > 0) {							// If the row ends with a filled-in square (value=1), push tempSum to the stack
			currentRow.push(tempSum);
		}
		return currentRow;
	}

	private List<String> getRowHeaders(List<Stack<Integer>> listOfRowCounts) {

		/*
		 * stack(0) = { 5 } stack(1) = { 1, 1 } stack(2) = { 5 } stack(3) = { 1, 1 }
		 * stack(4) = { 5 }
		 * 
		 * rowHeaders(0) = "  5 "; rowHeaders(1) = "1 1 "; rowHeaders(2) = "  5 ";
		 * rowHeaders(3) = "1 1 "; rowHeaders(4) = "  5 ";
		 */

		List<String> rowHeaders = new ArrayList<>();

		// determine the max length of the strings in order to add
		// leading spaces so that all counts are right-aligned
		int maxCount = getMaxCountForHeaders(listOfRowCounts); // example: 2

		// create a string of counts for each row of the puzzle
		for (int headerRow = 0; headerRow < columnRowCount; headerRow++) {

			String currentHeaderRow = "";
			Stack<Integer> currentStack = listOfRowCounts.get(headerRow);

			// loop through all stacks, taking the last number from the top of the
			// stack through each loop, and adding to the beginning of the string
			// for that header row, or " " if the stack is empty
			for (int headerColumn = 0; headerColumn < maxCount; headerColumn++) {

				if (currentStack.isEmpty()) {
					currentHeaderRow = "   " + currentHeaderRow;
				} else {
					currentHeaderRow = currentStack.peek() + " " + currentHeaderRow;
					if (currentStack.pop() < 10)
						currentHeaderRow = " " + currentHeaderRow;
				}
			}
			rowHeaders.add(currentHeaderRow);
		}
		return rowHeaders;
	}

	private int getMaxCountForHeaders(List<Stack<Integer>> listOfColumnOrRowCounts) {
		// maxCount will hold the max number of unbroken filled-in squares for the
		// puzzle to help with spacing in the headers when printing the grid
		int maxCount = 0;
		for (Stack<Integer> stack : listOfColumnOrRowCounts) {
			if (stack.size() > maxCount) {
				maxCount = stack.size();
			}
		}
		return maxCount;
	}

	private Stack<Integer> getColumnCounts(int cellNumber) {

		Stack<Integer> currentColumn = new Stack<>();
		int tempSum = 0;

		// For each cell in the current column only...
		while (cellNumber < totalNumberOfCells) {

			// tempSum will increment for every successive 1 found in the column
			if (cellValues[cellNumber] == 1) {
				tempSum++;
			} else if (tempSum > 0) {
				// When a 0 is found after successive 1s, the sum will be stored in the stack,
				// and tempSum will reset to 0
				currentColumn.push(tempSum);
				tempSum = 0;
			} else {
				// successive 0s (blank squares), so no change to tempSum
			}
			cellNumber += columnRowCount; // move to the next cell of the column
		}
		// If the column ends with a filled-in square (value=1),
		// push tempSum to the stack
		if (tempSum > 0) {
			currentColumn.push(tempSum);
			tempSum = 0;
		}
		return currentColumn;
	}

	private Stack<String> getColumnHeaders(List<Stack<Integer>> listOfColumnCounts) {
		/*
		 * stack(0) = { 3, 1 } stack(1) = { 1, 1, 1 } stack(2) = { 5 } stack(3) = { 1,
		 * 1, 1 } stack(4) = { 1, 3 }
		 * 
		 * columnHeaders(2) = "      1       1     "; columnHeaders(1) =
		 * "  3   1       1   1 "; columnHeaders(0) = "  1   1   5   1   3 ";
		 */

		int maxCount = getMaxCountForHeaders(listOfColumnCounts); // example: 3

		Stack<String> columnHeaders = new Stack<>();

		// create maxCount number of strings with the header values across
		// each column
		for (int headerRow = 0; headerRow < maxCount; headerRow++) {

			String currentHeaderRow = "";

			// loop through all stacks, taking the last number from the top of the
			// stack through each loop, and adding to the end of the string for that
			// header row, or " " if the stack is empty
			for (int headerColumn = 0; headerColumn < columnRowCount; headerColumn++) {

				Stack<Integer> currentStack = listOfColumnCounts.get(headerColumn);
				if (currentStack.isEmpty()) {
					currentHeaderRow += "    ";
				} else {
					if (currentStack.peek() < 10)
						currentHeaderRow += " ";
					currentHeaderRow += " " + currentStack.pop() + " ";
				}
			}
			columnHeaders.add(currentHeaderRow);
		}
		return columnHeaders;
	}

	

	private List<String> getCoreGridRows() {
		/*
		 * Example: [1, 1, 1, 1, 1, 1, 0, 1, 0, 0, 1, 1, 1, 1, 1, 0, 0, 1, 0, 1, 1, 1,
		 * 1, 1, 1]
		 * 
		 * gridRows(0) = "|###| O | O | O | O |" 
		 * gridRows(1) = "| O |   | O |   |   |"
		 * gridRows(2) = "| O | O | O | O | O |" 
		 * gridRows(3) = "|   |   | O |   | O |"
		 * gridRows(4) = "| O | O | O | O | O |"
		 */

		List<String> gridRows = new ArrayList<>();

		int cellNumber = 0;
		for (int rowNumber = 0; rowNumber < columnRowCount; rowNumber++) {

			String gridString = "";

			for (int columnNumber = 0; columnNumber < columnRowCount; columnNumber++) {

				if (playerCellValues[cellNumber] == 1) {
					gridString += "|###";
				} else if (playerCellValues[cellNumber] == 2) {
					gridString += "| - ";
				} else {
					gridString += "|   ";
				}
				cellNumber++;
			}
			gridString += "|";
			gridRows.add(gridString);
		}
		return gridRows;
	}

}
