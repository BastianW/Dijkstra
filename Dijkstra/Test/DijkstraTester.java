import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.SortedSet;

import javax.swing.JButton;
import javax.swing.JFrame;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import de.bwvaachen.graph.gui.input.AdjazenzmatrixView;
import de.bwvaachen.graph.gui.input.WeightMode;
import de.bwvaachen.graph.logic.Connection;
import de.bwvaachen.graph.logic.Graph;
import de.bwvaachen.graph.logic.Node;
import de.bwvaachen.graph.logic.algorithm.Dijkstra;


public class DijkstraTester extends GUIElementTester{
	static Graph graph;
	public static void main(String[] args) {
	JFrame frame=getFrame();
	frame.setLayout(new BorderLayout());
	JButton button =new JButton("Test");
	
	try {
		graph = Graph.load("BigTest.txt");
	} catch (JsonParseException e2) {
		// TODO Auto-generated catch block
		e2.printStackTrace();
	} catch (JsonMappingException e2) {
		// TODO Auto-generated catch block
		e2.printStackTrace();
	} catch (IOException e2) {
		// TODO Auto-generated catch block
		e2.printStackTrace();
	}
	final AdjazenzmatrixView adjazenzmatrixView = new AdjazenzmatrixView(graph ,WeightMode.INTEGER_MODE);
	button.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
			graph=		adjazenzmatrixView.getGraph();
			Node startNode=new Node("B");
			Node endNode=new Node("A");
			System.out.println("Startnode: "+startNode);
			Dijkstra dijkstra=new Dijkstra(graph, startNode);
			dijkstra.doDijkstra();
			System.out.println(dijkstra.getShortestPath(endNode));
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
		}
	});
	frame.add(button,BorderLayout.SOUTH);
	frame.add( adjazenzmatrixView, BorderLayout.CENTER);
	frame.doLayout();
	button.doClick();
}
}
