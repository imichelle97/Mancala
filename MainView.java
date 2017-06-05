/**Project: Mancala 
 * Author: Michelle Luong, Anthony Pun, Jiajie Yu
 * Copyright (C) 2017. All Rights Reserved.
 * Version: 1.01 5/6/2017
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 * Print the actual board, and update the board when there are changes, then repaints the new, updated board
 *
 */

public class MainView extends JFrame {
	
	private MancalaController controller;
	private final JPanel board;
	private final double SCALE = 1.8;
	private final JLabel displayMessage = new JLabel();
	private final JButton undo;
	private final ButtonGroup group1;
	private final JRadioButton three;	//Button to click "3" as starting number of stones
	private final JRadioButton four;	//Button to click "4" as starting number of stones
	private MancalaStyle userChoice = new circleStyle();
	private final JLabel mancalaA = new JLabel("Mancala A");
	private final JLabel mancalaB = new JLabel("Mancala B");
	private JLabel playerAUndo;
	private JLabel playerBUndo;
	private final JButton newGame;
	
	public MainView() {
		setLayout(new BorderLayout());		
		board = new JPanel();
		board.setPreferredSize(new Dimension((int)(800*SCALE), (int)(250*SCALE)));
		add(board, BorderLayout.CENTER);
		
		displayMessage.setText("Mancala A");
		displayMessage.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
		add(displayMessage, BorderLayout.SOUTH);
		
		JPanel buttons = new JPanel();
		JPanel temp = new JPanel(new BorderLayout());
		
		//UNDO BUTTON
		undo = new JButton("Undo");
		undo.setBackground(Color.LIGHT_GRAY);
		undo.setEnabled(false);
		undo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.undoMove();
				undo.setEnabled(false);
			}
		});
		temp.add(undo, BorderLayout.EAST);
		
		//NEW GAME BUTTON
		newGame = new JButton("New Game");
		newGame.setBackground(Color.BLUE);
		newGame.setForeground(Color.WHITE);
		newGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(three.isSelected()) 
					controller.newGame(3);
				else
					controller.newGame(4);
				undo.setEnabled(false);
			}
		});
		buttons.add(newGame);
		
		JLabel numOfStones = new JLabel("Number of Stones: ");
		group1 = new ButtonGroup();
		three = new JRadioButton("3");
		three.setSelected(true);
		four = new JRadioButton("4");
		group1.add(three);
		group1.add(four);
		buttons.add(numOfStones);
		buttons.add(three);
		buttons.add(four);
		
		//CIRCLE STYLE BUTTON
		JButton circle = new JButton("Circle Style");
		circle.setBackground(Color.WHITE);
		circle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				userChoice = new circleStyle();
				setUpBoard();
			}
		});
		buttons.add(circle);
		
		//SQUARE STYLE BUTTON
		JButton square = new JButton("Square Style");
		square.setBackground(Color.WHITE);
		square.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				userChoice = new squareStyle();
				setUpBoard();
			}
		});
		buttons.add(square);
		
		temp.add(buttons, BorderLayout.CENTER);
		add(temp, BorderLayout.NORTH);
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);		
	}
	
	public void setUpBoard() {
		board.removeAll();
		board.revalidate();
		board.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5,5,5,5);
		
		//Set up location on the grid layout for each pits 
		for(int i = 0; i < 14; i++) {
			final BoardPits pit;
			final JLabel labels;
			//Set up location for Mancala B
			if(i == 0) {
				c.gridheight = 2;
				c.gridx = 0;
				c.gridy = 1;
				pit = new BoardPits((int)(80*SCALE), (int)(160*SCALE), controller.getMancalaB());
				pit.setName("6");
				
			}
			//Set up location for Mancala A
			else if(i == 13) {
				c.gridheight = 2;
				c.gridx = 7;
				c.gridy = 1;
				pit = new BoardPits((int)(80*SCALE), (int)(160*SCALE), controller.getMancalaA());
				pit.setName("13");
			}
			//A row
			else if(i > 6) {
				c.gridheight = 1;
				c.gridx = i - 6;
				c.gridy = 2;
				pit = new BoardPits((int)(80*SCALE), (int)(80*SCALE), controller.getPlayerAStone().get(i-7));
				pit.setName(i + "");
				labels = new JLabel("A"+(i-6));
				labels.setForeground(Color.white);
				board.add(labels, c);
			}
			//B row
			else {
				c.gridheight = 1;
				c.gridx = i;
				c.gridy = 1;
				pit = new BoardPits((int)(80*SCALE), (int)(80*SCALE), controller.getPlayerBStone().get(i-1));
				pit.setName((6-i) + "");
				labels = new JLabel("B"+(7-i));
				labels.setForeground(Color.white);
				board.add(labels, c);
			}
			
			//Check for invalid moves
			pit.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					if(controller.checkWin() == 4) {
						int state = controller.makeMove(Integer.parseInt(pit.getName()));
						if(state == 0) {
							String notClickable = displayMessage.getText();
							displayMessage.setText((notClickable.split(": ")[0] + ": Invalid Move! The mancalas are not clickable"));
						}
						else if(state == 1) {
							String opponentBoard = displayMessage.getText();
							displayMessage.setText((opponentBoard.split(": ")[0] + ": Invalid Move! You cannot clock on oppenent's board"));
						}
						else if(state == 2) {
							String emptyPit = displayMessage.getText();
							displayMessage.setText((emptyPit.split(": ")[0] + ": Invalid Move! The pit is empty!"));
						}
						undo.setEnabled(true);
					}
				}
			});
			
			pit.setBoardStyle(userChoice);
			board.add(pit, c);

		}
		
		mancalaB.setFont(new Font("Serif", Font.BOLD, 18));
		c.gridheight = 1;
		c.gridx = 0;
		c.gridy = 3;
		board.add(mancalaB, c);
		
		mancalaA.setFont(new Font("Serif", Font.BOLD, 18));
		c.gridheight = 1;
		c.gridx = 7;
		c.gridy = 3;
		board.add(mancalaA,c);

		
		String undoB = "Undo remaining: " + (3 - controller.getUndoB());
		playerBUndo = new JLabel(undoB);
		playerBUndo.setFont(new Font("Serif", Font.BOLD, 18));
		c.gridheight = 1;
		c.gridx = 0;
		c.gridy = 4;
		board.add(playerBUndo, c);
		
		String undoA = "Undo remaining: " + (3 - controller.getUndoA());
		playerAUndo = new JLabel(undoA);
		playerAUndo.setFont(new Font("Serif", Font.BOLD, 18));
		c.gridheight = 1;
		c.gridx = 7;
		c.gridy = 4;
		board.add(playerAUndo, c);
		
		//Display the results of the game in a Message Dialog
        if (controller.checkWin() == 1) {
            displayMessage .setText("Player A: " + controller.getMancalaA() + " v.s. "+ "Player B: " + controller.getMancalaB() + " --------------- Player A Wins!");
            JOptionPane.showMessageDialog(board, "Player A: " + controller.getMancalaA() + " v.s. "+ "Player B: " + controller.getMancalaB() + " --------------- Player A Wins!");
        } 
        else if (controller.checkWin() == 2) {
            displayMessage .setText("Player A: " + controller.getMancalaA() +" v.s. "+ "Player B: " + controller.getMancalaB() + " --------------- Player B wins!");
            JOptionPane.showMessageDialog(board, "Player A: " + controller.getMancalaA() + " v.s. "+ "Player B: " + controller.getMancalaB() + " --------------- Player B wins!");
        } 
        else if (controller.checkWin() == 3) {
        	displayMessage .setText("Player A: " + controller.getMancalaA() + " v.s. "+ "Player B: " + controller.getMancalaB() + " Draw");
        	JOptionPane.showMessageDialog(board, "Player A: " + controller.getMancalaA() + " v.s. "+ "Player B: " + controller.getMancalaB() + " --------------- Tie");
        }
        else if (controller.isPlayerATurn()) {
            displayMessage.setText("Player A's Turn");
        } 
        else {
            displayMessage.setText("Player B's Turn");
        }
        
        board.repaint();
    }
	
	void setData(MancalaController c) {
		this.controller = c;
		setUpBoard();
	}
}


