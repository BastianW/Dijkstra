package de.bwvaachen.graph.logic.algorithm.dijkstra;

import javax.swing.JPanel;

import de.bwvaachen.graph.logic.algorithm.ILogger;
import javax.swing.JTextField;
import javax.swing.DropMode;
import javax.swing.JTextPane;

import java.awt.Dimension;
import java.awt.GridLayout;

public class DijkstraVisualisationLogger extends JPanel implements ILogger {

	
	private JTextPane textPane;

	public DijkstraVisualisationLogger() {
		setLayout(new GridLayout(0, 1, 0, 0));
		
		textPane = new JTextPane();
		add(textPane);
	}
	@Override
	public void writeLine(String line) {
		textPane.setText(textPane.getText()+line+"\n");
	}

	@Override
	public void resetLogger() {
		textPane.setText("");
	}

}
