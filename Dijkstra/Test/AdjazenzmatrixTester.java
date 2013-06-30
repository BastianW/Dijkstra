import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.util.SortedSet;

import javax.swing.JButton;
import javax.swing.JFrame;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import de.bwvaachen.graph.gui.input.AdjazenzmatrixView;
import de.bwvaachen.graph.gui.input.WeightMode;
import de.bwvaachen.graph.logic.Connection;
import de.bwvaachen.graph.logic.Graph;


public class AdjazenzmatrixTester extends GUIElementTester{

	/**
	 * @param args
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
		testAdjazensmatrixView();

	}
	
	
	private static void testAdjazensmatrixView() throws JsonParseException, JsonMappingException, IOException
	{
		JFrame frame=getFrame();
		frame.setLayout(new BorderLayout());
		JButton button =new JButton("Test");
		Graph graph=Graph.load("BigTest.txt");
		final AdjazenzmatrixView adjazenzmatrixView = new AdjazenzmatrixView(graph ,WeightMode.DOUBLE_NULL_ALLOWED_MODE);
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {

							Graph graph=adjazenzmatrixView.getGraph();
							graph.save("BigTest.txt");
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
}
