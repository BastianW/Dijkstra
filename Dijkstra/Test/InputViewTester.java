import java.awt.BorderLayout;

import javax.swing.JFrame;

import de.bwvaachen.graph.gui.input.AdjazenzmatrixInput;


public class InputViewTester extends GUIElementTester {
	public static void main(String[] args) {
		JFrame frame=getFrame();
		frame.setLayout(new BorderLayout());
		
		frame.add(new AdjazenzmatrixInput(null));

	}
}
