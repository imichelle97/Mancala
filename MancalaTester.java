/**Project: Mancala 
 * Author: Michelle Luong, Anthony Pun, Jiajie Yu
 * Copyright (C) 2017. All Rights Reserved.
 * Version: 1.01 5/6/2017
 */

import java.awt.Dimension;
import java.awt.Toolkit;

/**
 * Main method to run the program
 *
 */

public class MancalaTester {
	public static void main(String[]args) {
		MainView view = new MainView();
		view.setSize(new Dimension(1440, 500));
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		view.setLocation(d.width/2 - view.getSize().width/2, d.height/2 - view.getSize().height/2);
		MancalaController controller = new MancalaController(3, view);
		view.setData(controller);
	}
}
