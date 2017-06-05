/**Project: Mancala 
 * Author: Michelle Luong, Anthony Pun, Jiajie Yu
 * Copyright (C) 2017. All Rights Reserved.
 * Version: 1.01 5/6/2017
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RectangularShape;

import javax.swing.JPanel;

/**
 * Paints the board pits 
 *
 */

public class BoardPits extends JPanel {
	
	private int width;
	private int height;
	private int numOfStones;
	RectangularShape shape;
	Color boardColor;
	Color stoneColor;
	
	public boolean isSelected = false;
	
	public BoardPits(int width, int height, int numOfStones) {
		this.width = width;
		this.height = height;
		this.numOfStones = numOfStones;
		setPreferredSize(new Dimension(width, height));
		this.setLocation(0, 0);		
	}
	
	public void setBoardStyle(MancalaStyle m) {
		shape = m.getShape();
		boardColor = m.getBoardColor();
		stoneColor = m.getStoneColor();
	}
	
	/**
	 * Paints the stone for each pit
	 */
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		shape.setFrame(0, 0, width, height);
		g2.setColor(boardColor);
		g2.fill(shape);
		g2.setPaint(stoneColor);
		Ellipse2D.Double stone = new Ellipse2D.Double(30, 20, 20, 20);
		
		for(int i = 0; i < numOfStones; i++) {
			if(i % 4 == 0) {
				stone.y += 20;
				stone.x = 30;
			}
			else {
				stone.x += 20;
			}
			
			g2.fill(stone);
			g2.draw(stone);
				
			}
		}		
	}