/**Project: Mancala 
 * Author: Michelle Luong, Anthony Pun, Jiajie Yu
 * Copyright (C) 2017. All Rights Reserved.
 * Version: 1.01 5/6/2017
 */

import java.util.ArrayList;
import java.util.Collections;

/**
 * Representation of the game board
 * This class will track the mancala pits and stones using an ArrayList
 *
 */

public class BoardRep {
	
	public ArrayList<Integer> mancalaBoard;
	public int boardSize = 14;
	public int MancalaA = 13; 	//Mancala A is at index 13
	public int MancalaB = 6;	//Mancala B is at index 6
	public int numOfStones;
	public boolean isPlayerA;	//Default will be set to true to track Player A or Player B
	
	/**
	 * Intitialize the starting number of stones, either 3 or 4
	 * @param num Number of stones
	 */
	public BoardRep(int num) {
		mancalaBoard = new ArrayList<Integer>(boardSize);
		setStones(num);
		isPlayerA = true;		//Player A will always have the first turn
		numOfStones = num;
	}
	
	/**
	 * Copy of the constructor to return the current/updated board
	 * @return
	 */
	public BoardRep copy() {
		BoardRep rep = new BoardRep(numOfStones);
		rep.isPlayerA = this.isPlayerA;
		Collections.copy(rep.mancalaBoard, mancalaBoard);
		return rep;
	}
	
	/**
	 * Empty constructor; may or may not need this
	 */
	public BoardRep() {
		mancalaBoard = new ArrayList<Integer>(boardSize);
	}
	
	/**
	 * This method will set the stones to all of the pits
	 * However, if the index of the for loop lands on 13 for
	 * Mancala A or 6 or Mancala B, then we add zero stones 
	 * because we want those mancala pits to start off empty.
	 * @param numOfMarbles
	 */
	public void setStones(int numOfStones) {
		for(int i = 0; i < boardSize; i++) {
			if(i == MancalaA || i == MancalaB) {
				mancalaBoard.add(0);
			}
			else {
				mancalaBoard.add(numOfStones);
			}
		}
	}
	
	public int getNumOfStonesInPit(int index) {
		return mancalaBoard.get(index);
	}
	
	public void addStones(int stone) {
		if(isPlayerA) 
			mancalaBoard.set(MancalaA, mancalaBoard.get(MancalaA) + stone);
		else
			mancalaBoard.set(MancalaB, mancalaBoard.get(MancalaB) + stone);
	}
	
	public void removeStones(int index) {
		mancalaBoard.set(index, 0);
	}
	
	public ArrayList<Integer> getStonesA() {
		ArrayList<Integer> a = new ArrayList<Integer>();
		for(int i = 7; i < 13; i++) {
			a.add(mancalaBoard.get(i));
		}
		return a;
	}
	
	public ArrayList<Integer> getStonesB() {
		ArrayList<Integer> b = new ArrayList<Integer>();
		for(int i = 5; i >= 0; i--) {
			b.add(mancalaBoard.get(i));
		}
		return b;
	}
	
	/**
	 * This method is distributing the marbles based on the index
	 * of the mancalaBoard. So if it is Player A's turn, and the index
	 * lands on MancalaA (index = 13), then it adds one stone to the 
	 * MancalaA, and if it is Player B's turn and the index lands on 
	 * MancalaB (index = 6), then it adds one stone to the MancalaB.
	 * This is all happening while the number of stone to distribute 
	 * (stonesDistribute) is != to 0.  
	 * @param index This is the index of the pit
	 * @return The last index that the player left off on
	 */
	public int move(int index) {
		int stonesDistribute = mancalaBoard.get(index);
		
		if(stonesDistribute > 0) {
			mancalaBoard.set(index, 0);
			index++;
			
			while(stonesDistribute != 0) {
				if(isPlayerA && index == MancalaA) {
					System.out.println("Mancala A now has " + (mancalaBoard.get(index)+1) + " stones");
					mancalaBoard.set(index, mancalaBoard.get(index) + 1);
					stonesDistribute--;					
				}
				else if(!isPlayerA && index == MancalaB) {
					System.out.println("Mancala B now has" + (mancalaBoard.get(index)+1) + " stones");
					mancalaBoard.set(index, mancalaBoard.get(index)+1);
					stonesDistribute--;
				}
				else {
					mancalaBoard.set(index, mancalaBoard.get(index)+1);
					stonesDistribute--;
				}
				
				if(index == 13) {
					if(stonesDistribute == 0 && isPlayerA) {
						return 13;
					}
					index = -1;
				}
				index ++;
			}
			return index - 1;
		}
		else 
			return -1;
	}
	
	/**
	 * Player A Wins - Return 1
	 * Player B Wins - Return -1
	 * Tie/Draw		 - Return 0
	 * If the game is still in progress, aka, the pit is not empty, break the game
	 * @return
	 */
	public int checkWin() {
		boolean isPitEmpty = true;
		
		for(int i = 0; i < 6; i++) {
			if(mancalaBoard.get(i) == 0) {
				isPitEmpty = true;
			}
			else {
				isPitEmpty = false;
				break;
			}
		}
		
		//Player B Wins
		if(isPitEmpty) {
			return -1;
		}
		
		isPitEmpty = true;
		for(int i = 7; i < 13; i++) {
			if(mancalaBoard.get(i) == 0) {
				isPitEmpty = true;
			}
			else {
				isPitEmpty = false;
				break;
			}
		}
		
		//Player A Wins
		if(isPitEmpty) {
			return 1;
		}
		
		//Tie
		return 0;
	}
	

}
