/**Project: Mancala 
 * Author: Michelle Luong, Anthony Pun, Jiajie Yu
 * Copyright (C) 2017. All Rights Reserved.
 * Version: 1.01 5/6/2017
 */

import java.awt.Color;
import java.awt.geom.RectangularShape;

/**
 * Interface that will be implements by sub class for different type of user interfaces of the Mancala game
 *
 */

public interface MancalaStyle {
	
	/**
	 * Get shape of the game board
	 * @return shape
	 */
	public RectangularShape getShape();
	
	/**
	 * Get the color of the board
	 * @return boardColor
	 */
	public Color getBoardColor();
	
	/**
	 * Get the color of the stone
	 * @return stoneColor
	 */
	public Color getStoneColor();

}
