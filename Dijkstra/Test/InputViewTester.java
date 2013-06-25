import java.awt.BorderLayout;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;

import de.bwvaachen.graph.gui.input.AdjazenzmatrixInput;
import de.bwvaachen.graph.logic.Connection;
import de.bwvaachen.graph.logic.Edge;
import de.bwvaachen.graph.logic.Graph;
import de.bwvaachen.graph.logic.Node;
import de.bwvaachen.graph.logic.Path;


public class InputViewTester extends GUIElementTester {
	public static void main(String[] args) {
		JFrame frame=getFrame();
		frame.setLayout(new BorderLayout());
		Set <Node>nodes=new HashSet<Node>();
		Node bastian=new Node("Bastian");
		nodes.add(bastian);
		Node michi=new Node("Michi");
		nodes.add(michi);
		Node dennis= new Node("Dennis");
		nodes.add(dennis);
		Node franz=new Node("Franz");
		nodes.add(franz);
		
		Connection bastian_michi=new Connection(bastian, michi, new Edge(12));
		Connection michi_dennis=new Connection(michi,dennis,new Edge(3));
		
		List<Connection>connections=new LinkedList<Connection>();
		connections.add(bastian_michi);
		connections.add(michi_dennis);
		Graph graph =new Graph(nodes, connections, new LinkedList<Path>());
		frame.add(new AdjazenzmatrixInput(graph));

	}
}
