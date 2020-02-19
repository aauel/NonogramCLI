package com.techelevator.PortfolioProject;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class NonogramPlayer {
	
	Nonogram thisPuzzle;
	List<String> thisPuzzleOnCellNames;
	private Map<String, Integer> playerCellValues;
	

	public NonogramPlayer(Nonogram newPuzzle) {
		thisPuzzle = newPuzzle;
		thisPuzzleOnCellNames = thisPuzzle.getListOfOnCellNames();
		playerCellValues = new LinkedHashMap<String, Integer>();
	}
	
	public void setupPlayerCellValues() {
	/*
	 *	Populate the HashMap of cellNames mapped to all 0's to represent blank cells to start with
	 *	Example:  For a 5x5 grid puzzle
	 *	Result:   [AA, BA, CA, DA, EA, AB, BB, CB, DB, EB, AC, BC, CC, DC, EC, AD, BD, CD, DD, ED, AE, BE, CE, DE, EE]
	 *			  [ 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0]
	 */
		int totalNumberOfCells = thisPuzzle.getTotalNumberOfCells();
		List<String> cellNames = thisPuzzle.getListOfCellNames();
		for (int cellNumber = 0; cellNumber < totalNumberOfCells; cellNumber++) {
			String currentCellName = cellNames.get(cellNumber);
			playerCellValues.put(currentCellName, 0);
		}
	}
	
	public boolean isPlayerPuzzleComplete() {
	/*
	 *  Go through the values of playerCellValues and compare to thisPuzzleOnCellNames
	 *  if playerValue = 1, check that playerKey is in thisPuzzleOnCellNames, because cell SHOULD be ON
	 *  else, if playerValue = 0 or -1, check that playerKey is NOT in thisPuzzleOnCellNames, because cell SHOULD be OFF or blank
	 */
		for (String cellName : playerCellValues.keySet()) {
			int playerValue = playerCellValues.get(cellName);
			if (playerValue == 1) {
				if (thisPuzzleOnCellNames.contains(cellName) == false) {
					return false;
				}
			} else {  // 0 or -1
				if (thisPuzzleOnCellNames.contains(cellName)) {
					return false;
				}
			}
		}
		return true;		
	}
	
	public boolean changeCell(String changeInput) {
	/*
	 * -Single Cell- changeInput is 2 or 3 characters  
	 * 		*Examples:  AA | AA.
	 * -Cell Ranges- changeInput is 5 or 6 characters
	 * 		*Examples:  AA:AE | AA:AE_ (cells in same column)
	 * 				    AA:EA | AA:EA_ (cells in same row)
	 */
		boolean result = false;							// method will return false if user input is invalid
		
		if (changeInput != null) {						// check for empty user input
			int inputLen = changeInput.length();		// length of input will determine cell range and action
			if (inputLen == 2 || inputLen == 3) {		// single cell, with or without an action character at end
				result = changeSingleCell(changeInput, inputLen);
				
			} else if (inputLen == 5 || inputLen == 6) {	// range of cells, with or without an action character
				result = parseCellRange(changeInput, inputLen);
			}
		}
		return result;
	}
	
	private boolean parseCellRange(String changeInput, int inputLen) {		
		boolean result = false;	 // method will return false if user input is invalid
		
		// changeChar will get appended to each cell in turn
		// made it a String here in order to have an empty string for when no action is specified
		String changeChar = "";
		
		if (inputLen == 6) {
			changeChar = changeInput.substring(5,6);
		}  // else changeChar will stay ""
		if (changeInput.charAt(0) == changeInput.charAt(3)) { 				  // Column example - AA:AE | AA:AE_
			result = changeCellsInColumn(changeInput, inputLen, changeChar);  // Same letters:    0  3  | 0  3
			
		} else if (changeInput.charAt(1) == changeInput.charAt(4)) {		  // Row example - AA:EA | AA:EA_
			result = changeCellsInRow(changeInput, inputLen, changeChar);	  // Same letters:  1  4 |  1  4
		}
		return result;
	}
	
	private boolean changeCellsInColumn(String changeInput, int inputLen, String changeChar) {
		
		// Column example - AA:AE | AA:AE_
		//                  01234 | 012345
		char column = changeInput.charAt(0);
		char rowStart = changeInput.charAt(1);
		char rowStop = changeInput.charAt(4);
		
		int numberOfCellsInRange = rowStop - rowStart;
		for (int i=0; i<=numberOfCellsInRange; i++, rowStart++) {
			String singleCell = Character.toString(column) + rowStart + changeChar;
			if (changeSingleCell(singleCell, inputLen) == false) {
				return false;
			}
		}
		return true;
	}
	
	private boolean changeCellsInRow(String changeInput, int inputLen, String changeChar) {
		
		// Row example - AA:EA | AA:EA_
		//               01234 | 012345
		char row = changeInput.charAt(1);
		char colStart = changeInput.charAt(0);
		char colStop = changeInput.charAt(3);
		
		int numberOfCells = colStop - colStart;
		for (int i=0; i<=numberOfCells; i++, colStart++) {
			String singleCell = Character.toString(colStart) + row + changeChar;
			if (changeSingleCell(singleCell, inputLen) == false) {
				return false;
			}
		}
		return true;
	}	
	
	
	private boolean changeSingleCell(String changeInput, int inputLen) {
		
		// cell name should be the first 2 characters of the user input
		String cellName = changeInput.substring(0, 2).toUpperCase();
		
		if (playerCellValues.containsKey(cellName)) {		// check that 2 characters entered are a valid cell reference for this puzzle
			int actionValue = 1; 							// actionValue = 1 (ON) as default
			if (inputLen == 3) {
				actionValue = determineAction(changeInput);	// get action from the 3rd character
			}
			playerCellValues.put(cellName, actionValue);
			return true;
		}
		return false;
	}
	
	
	private int determineAction(String changeInput) {
		
		char changeChar = changeInput.charAt(2);  // action determined by the 3rd character in user input		
		int actionValue = 0;  					  // action = 0 (blank) as default, even if the user enters something other than . or _
		if (changeChar == '.') actionValue = -1;  // check specifically for '.' (OFF)
		return actionValue;
	}
}


