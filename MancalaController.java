/**Project: Mancala 
 * Author: Michelle Luong, Anthony Pun, Jiajie Yu
 * Copyright (C) 2017. All Rights Reserved.
 * Version: 1.01 5/6/2017
 */

import java.util.ArrayList;

/**
 * Controller class, make changes and update to the model (BoardRep), and then display by view (MainView) 
 * RULES OF GAME ARE IMPLEMENTED HERE
 *
 */

public class MancalaController {
	
	public int undoA;
	public int undoB;
	public BoardRep currentData;
	public BoardRep previousData;
	public MainView view;
	
	/**
	 * Initializing the start of a new game by using the user's info
	 * @param numOfStones Number of stones
	 * @param view View controlled by MainView class that contains the user's info
	 */
	public MancalaController(int numOfStones, MainView view) {
		undoA = 0;
		undoB = 0;
		currentData = new BoardRep(numOfStones);
		previousData = currentData;
		this.view = view;
	}
	
	/**
	 * Initializing the start of a new game
	 * @param stones Number of stones
	 */
	public void newGame(int stones) {
		currentData.numOfStones = stones;
		undoA = 0;
		undoB = 0;
		currentData = new BoardRep(currentData.numOfStones);
		previousData = currentData;
		currentData.isPlayerA = true;
		view.setUpBoard();
	}
	
	/**
	 * Gets the array list of Player A's stones
	 * @return An array list representing Player A's pits
	 */
	public ArrayList<Integer> getPlayerAStone() {
		return currentData.getStonesA();
	}
	
	/**
	 * Gets the array list of Player B's stones
	 * @return An array list representing Player B's pits
	 */
	public ArrayList<Integer> getPlayerBStone() {
		return currentData.getStonesB();
	}
	
	/**
	 * This will help determine the winner of the game (checkWin())
	 * @return Number of stones in Mancala A
	 */
	public int getMancalaA() {
		return currentData.getNumOfStonesInPit(currentData.MancalaA);
	}
	
	/**
	 * This will help determine the winner of the game (checkWin())
	 * @return Number of stones in Mancala B
	 */
	public int getMancalaB() {
		return currentData.getNumOfStonesInPit(currentData.MancalaB);
	}
	
	/**
	 * Checks if it is Player A's turn to play
	 * @return Boolean true if it is Player A's turn to play
	 */
	public boolean isPlayerATurn() {
		return currentData.isPlayerA;
	}
	
	/**
	 * This method helps the makeMove method determine if the action made by
	 * the user is valid or not
	 * @param index Index of the pit that the user clicked on
	 * @return Boolean true = If move is valid
	 * 	       Boolean false = If move is invalid
	 */
	public boolean isValidMove(int index) {
		if(currentData.isPlayerA && index >= 0 && index <= 6) 
			return true;
		else if(!currentData.isPlayerA && index >= 7 && index <= 12) 
			return true;
		else
			return false;
	}
	
	/**
	 * This method will find the adjacent index to the ending index.  This will help
	 * to keep track of stealing stones from Pit A/B and adding those stolen stones to 
	 * Mancala A/B respectively
	 * @param endIndex The current ending index
	 * @return The adjacent index
	 */
	public int getAdjacentIndex(int endIndex) {
		return 12 - endIndex;
	}
	
	/**
	 * This function will check if the hole is empty. If it is empty, steal
	 * @param endIndex The ending index
	 */
	public void isHoleEmpty(int endIndex) {
		//Steal the stones from pit B to add to Mancala A
		if(currentData.isPlayerA 
				&& endIndex >= 7 
				&& endIndex <= 12
				&& currentData.getNumOfStonesInPit(endIndex) == 1) {
			int adjacentIndex = getAdjacentIndex(endIndex);
			System.out.println("Current adjacent index to steal  from B to A is " + adjacentIndex);
			System.out.println("Current ending index to steal from B to A is " + endIndex);
			int stoneInMancala = currentData.getNumOfStonesInPit(adjacentIndex);
			currentData.removeStones(adjacentIndex);
			currentData.addStones(stoneInMancala);
		}
		
		//Steal the stones from pit A to add to Mancala B
		if(currentData.isPlayerA
				&& endIndex >= 0
				&& endIndex <= 5
				&& currentData.getNumOfStonesInPit(endIndex) == 1) {
			int adjacentIndex = getAdjacentIndex(endIndex);
			System.out.println("Current adjacent index to steal  from A to B is " + adjacentIndex);
			System.out.println("Current ending index to steal from A to B is " + endIndex);
			int stoneInMancala = currentData.getNumOfStonesInPit(adjacentIndex);
			currentData.removeStones(adjacentIndex);
			currentData.addStones(stoneInMancala);			
		}
	}
	
	/**
	 * This method will move the stones and return the current state of the game
	 * depending on the index of the pit that is clicked by the user.  
	 * @param index The index of the pit that is clicked by the user.
	 * @return 0 = The user clicks on the mancala (index 13 for Mancala A and index 6 or Mancala B
	 *         1 = The user clocks on the opponent's side of the board (Invalid Move)
	 *         2 = The user clocks on the empty pit (isHoleEmpty())
	 */
	public int makeMove(int index) {
		previousData = currentData.copy();
		
		if(index == currentData.MancalaA || index == currentData.MancalaB) {
			return 0;
		}
		
		if(isValidMove(index)) {
			return 1;
		}
		
		if(currentData.getNumOfStonesInPit(index) == 0) {
			return 2;
		}
		
		int endingIndex = currentData.move(index);
		System.out.println("The endingIndex is " + endingIndex);
		
		isHoleEmpty(endingIndex);
		
		//Conditions that apply to Player A getting another turn
		if(currentData.isPlayerA && endingIndex == currentData.MancalaA) {
			currentData.isPlayerA = true;
		}
		else if(!currentData.isPlayerA && endingIndex == currentData.MancalaB) {
			currentData.isPlayerA = false;
		}
		else {
			currentData.isPlayerA = !currentData.isPlayerA;
		}
		view.setUpBoard();
		return 3;
	}
	
	/**
	 * Get the number of times Player A can undo
	 * @return Undo number of player A
	 */
	public int getUndoA() {
		return undoA;
	}
	
	/**
	 * Get the number of times Player B can undo
	 * @return Undo number of player B
	 */
	public int getUndoB() {
		return undoB;
	}
	
	/**
	 * This method will undo the move made by Player A or Player B.
	 * In other words, it will place the previousData to the currentData.
	 */
	public void undoMove() {
		if(currentData.isPlayerA && undoA < 3) {
			undoA++;
			currentData = previousData;
		}
		
		if(!currentData.isPlayerA && undoB < 3) {
			undoB++;
			currentData = previousData;
		}
		
		view.setUpBoard();
	}	
	
	/**
	 * This function will check who won the game
	 * @return 1 = Player A Wins
	 *         2 = Player B Wins
	 *         3 = Tie
	 *         4 = Game is still in progress
	 */
	public int checkWin() {
		if(currentData.checkWin() != 0) {
			if(getMancalaA() > getMancalaB()) 
				return 1;
			else if(getMancalaB() > getMancalaA()) 
				return 2;
			else
				return 3;
			}
		return 4;
		}
	

}
