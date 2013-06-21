package de.bwvaachen.graph.gui.input;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JSplitPane;
import javax.swing.JScrollPane;

import de.bwvaachen.graph.logic.Connection;
import de.bwvaachen.graph.logic.Edge;
import de.bwvaachen.graph.logic.Node;
import de.bwvaachen.graph.logic.Path;

public class AdjazenzmatrixInput extends JPanel {

	/**
	 * Create the panel.
	 */
	public AdjazenzmatrixInput() {
		setLayout(new BorderLayout(0, 0));
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.7);
		add(splitPane, BorderLayout.CENTER);
		
		JSplitPane nodesAndAlgorithmusPane = new JSplitPane();
		nodesAndAlgorithmusPane.setResizeWeight(0.7);
		nodesAndAlgorithmusPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setRightComponent(nodesAndAlgorithmusPane);
		
		JScrollPane algorithmen_ScrollPane = new JScrollPane();
		nodesAndAlgorithmusPane.setRightComponent(algorithmen_ScrollPane);
		
		JSplitPane graphViewAndMatrixPane = new JSplitPane();
		graphViewAndMatrixPane.setResizeWeight(0.4);
		graphViewAndMatrixPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setLeftComponent(graphViewAndMatrixPane);
		
		graphViewAndMatrixPane.setLeftComponent(new AdjazenzmatrixView(7, WeightMode.DOUBLE_MODE));

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
		nodesAndAlgorithmusPane.setLeftComponent(new NodesView(nodes,connections, new LinkedList<Path>()));
	}

}
