package main;

import ui.Ui;

import javax.swing.*;
import java.io.IOException;
import java.text.ParseException;

public class Main
{
	public static void main(String ... args) throws ParseException, IOException {
		Ui ui = new Ui();
		ui.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		ui.setVisible(true);
		ui.setBounds(0, 0, 950,600);
	}
}
