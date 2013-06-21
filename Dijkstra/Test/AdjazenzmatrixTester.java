import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.SortedSet;

import javax.swing.JButton;
import javax.swing.JFrame;

import de.bwvaachen.graph.gui.input.AdjazenzmatrixView;
import de.bwvaachen.graph.gui.input.WeightMode;
import de.bwvaachen.graph.logic.Connection;
import de.bwvaachen.graph.logic.Graph;


public class AdjazenzmatrixTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		testAdjazensmatrixView();

	}
	
	
	private static void testAdjazensmatrixView()
	{
		JFrame frame=getFrame();
		frame.setLayout(new BorderLayout());
		JButton button =new JButton("Test");
		final AdjazenzmatrixView adjazenzmatrixView = new AdjazenzmatrixView(3 ,WeightMode.INTEGER_MODE);
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {

							Graph graph=adjazenzmatrixView.getGraph();
							List<Connection> sortedConnectionList =graph.getSortedConnections();
					for(Connection c: sortedConnectionList)
					{
						System.out.println(c);
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
			}
		});
		frame.add(button,BorderLayout.SOUTH);
		frame.add( adjazenzmatrixView, BorderLayout.CENTER);
		frame.doLayout();
	}
	
	private static JFrame getFrame()
	{
		JFrame frame=new JFrame("Test Adjazenzmatrix");
		frame.setSize(500,500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		return frame;
	}

}
