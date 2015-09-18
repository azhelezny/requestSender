package main;

import javax.swing.*;

import ui.Ui;

public class Main
{
	public static void main(String ... args)
	{
		Ui ui = new Ui();
		ui.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		ui.setVisible(true);
		ui.setBounds(0, 0, 950,600);
	}
}
