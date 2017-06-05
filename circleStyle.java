/**Project: Mancala 
 * Author: Michelle Luong, Anthony Pun, Jiajie Yu
 * Copyright (C) 2017. All Rights Reserved.
 * Version: 1.01 5/6/2017
 */

import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RectangularShape;

/**
 * Circle representation of the game board
 *
 */

public class circleStyle implements MancalaStyle {
	
	public RectangularShape getShape() {
		return new Ellipse2D.Double();
	}
	
	/**
	 * Get the color of the board
	 * @return boardColor
	 */
	public Color getBoardColor() {
		return new Color(66, 203, 244);
	}
	
	/**
	 * Get the color of the stone
	 * @return stoneColor
	 */
	public Color getStoneColor() {
		return new Color(0,0,0);
		
	}

}
