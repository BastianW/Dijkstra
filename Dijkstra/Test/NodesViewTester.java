import java.awt.BorderLayout;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;

import de.bwvaachen.graph.gui.input.NodesView;
import de.bwvaachen.graph.logic.Connection;
import de.bwvaachen.graph.logic.Edge;
import de.bwvaachen.graph.logic.Node;
import de.bwvaachen.graph.logic.Path;


public class NodesViewTester extends GUIElementTester{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame=getFrame();
		frame.setLayout(new BorderLayout());
		
		List <Node>nodes=new LinkedList<Node>();
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
		
		
		frame.add(new NodesView(nodes,connections, new LinkedList<Path>()));

	}

}
