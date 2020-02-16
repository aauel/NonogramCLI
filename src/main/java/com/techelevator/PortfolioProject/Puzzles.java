package com.techelevator.PortfolioProject;

import java.util.ArrayList;
import java.util.List;

public class Puzzles {
		
		List<Nonogram> puzzlesList = new ArrayList<>();
		private static int puzzleNum = 0;
	
		public Puzzles() {

			//puzzlesList.add(new Nonogram("Dollar Sign", 25, new int[] {6,1,1,2,5,2,1,1,6}));
			puzzlesList.add(new Nonogram("Sail Boat", 100, new int[] {0,4,1,8,3,6,1,1,2,5,1,2,3,4,1,2,3,4,2,1,4,4,6,6,1,5,10,1,8,1}));
		}
		
		public Nonogram getNextPuzzle() { 			
			Nonogram nextPuzzle = puzzlesList.get(puzzleNum);
			puzzleNum++;
			return nextPuzzle;
		}
		
		public boolean hasNextPuzzle() {
			return puzzleNum < puzzlesList.size();
		}
		
	
}
