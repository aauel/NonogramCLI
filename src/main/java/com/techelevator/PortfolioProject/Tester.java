package com.techelevator.PortfolioProject;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Tester {

	public static void main(String[] args) {
		
		int[] arr = {6,1,1,2,5,2,1,1,6};

		List<Integer> list = Arrays.stream(arr)		// IntStream
									.boxed()  		// Stream<Integer>
									.collect(Collectors.toList());
		Nonogram n = new Nonogram("Dollar Sign", 25, list);
//		n.initializeCellNames();
		System.out.println(n.getAllCellValues());
	}

}
