package com.techelevator.PortfolioProject;

import java.util.Scanner;
import com.techelevator.PortfolioProject.Menu;

public class NonogramCLI {
	
	private Menu menu;
	private Scanner in;
	private Puzzles puzzles;

	public NonogramCLI(Menu menu) {
		this.menu = menu;		
		in = new Scanner(System.in);
		puzzles = new Puzzles();
	}
	
	public void run() {
		System.out.println(
			"Hello! Here is your first Nonogram puzzle!\n");
		System.out.println(
			"Each square, or cell, can be referenced by combining the column letter with the row letter.\n" +
			"For example, the top-left cell is AA, the bottom-left cell is AE, and the bottom-right cell is EE.\n");
		System.out.println(
			"In each turn, you can set a square, or a set of squares, to one of 3 values:\n" +
			"  - ON, or filled in\n" +
			"  - OFF, to mark a cell that should not be filled in\n" +
			"  - blank, when you don't yet know the final value\n");
		System.out.println(
			"To set a single cell to ON, enter the column/row reference (such as AE) and hit Enter.\n");
		System.out.println(
			"To set a *group* of cells to ON, enter the cell range, from left-to-right OR top-to-bottom.\n" +
			"... for example:  AA:CA would turn on the first 3 cells in the top row, " + 
			"while AA:AC would turn on the first 3 cells in the first column.");
		System.out.println(
				"To turn off cells, include a period (.) after the column/row reference (such as AD.)\n" +
				"To turn a cell back to blank, include an underscore (_) after the cell reference (such as AA_)\n");

		
		while (puzzles.hasNextPuzzle()) {
			NonogramGrid nextPuzzle = new NonogramGrid(puzzles.getNextPuzzle());
			boolean done = false;
			while (!done) {
				nextPuzzle.printGrid();
				System.out.println(
					"Enter a cell reference, or cell range, followed by the optional instruction " + 
					"(such as AA, BC., CD:CE_):");
				String input = in.nextLine();
				if (nextPuzzle.changeCell(input) == false) {
					System.out.println("** SORRY, that isn't a valid instruction. "
							+ "Please try again!\n");
				}
			}
			
		}
	}
	

	public static void main(String[] args) {
		Menu menu = new Menu(System.in, System.out);
		NonogramCLI cli = new NonogramCLI(menu);
		cli.run();
	}

}
