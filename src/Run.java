package com.prime.example.soduku;

public class Run {

  public static void main(String[] args) {
  	System.out.println("\t\t\t@Prime Soduku\t\t\t");
  	System.out.println();
		PSoduku game = new PSoduku(3);
		
		game.set(0, 0, 5, 3, 0, 6, 0, 0, 0, 9, 8);
		game.set(0, 1, 0, 7, 0, 1, 9, 5, 0, 0, 0);
		game.set(0, 2, 0, 0, 0, 0, 0, 0, 0, 6, 0);
		game.set(1, 0, 8, 0, 0, 4, 0, 0, 7, 0, 0);
		game.set(1, 1, 0, 6, 0, 8, 0, 3, 0, 3, 0);
		game.set(1, 2, 0, 0, 3, 0, 0, 1, 0, 0, 6);
		game.set(2, 0, 0, 6, 0, 0, 0, 0, 0, 0, 0);
		game.set(2, 1, 0, 0, 0, 4, 1, 9, 0, 8, 0); 
		game.set(2, 2, 2, 8, 0, 0, 0, 5, 0, 7, 9);
	  
		try{  game.fillUp();  }
		catch(IndexOutOfBoundsException i) { 
			System.out.println("\tCannot be solved!");
		}
		game.displayValues2();
  }
}
