package de.bwvaachen.graph.gui.input;

import java.awt.BorderLayout;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import de.bwvaachen.graph.gui.input.controller.IGraphChangedListener;
import de.bwvaachen.graph.gui.input.controller.IGraphComponentChangedListener;
import de.bwvaachen.graph.logic.Connection;
import de.bwvaachen.graph.logic.Edge;
import de.bwvaachen.graph.logic.Graph;
import de.bwvaachen.graph.logic.Node;
import de.bwvaachen.graph.logic.Path;

public class AdjazenzmatrixInput extends JPanel implements IGraphComponentChangedListener{

	Graph graph;
	LinkedList<IGraphChangedListener>graphChangeListener=new LinkedList<IGraphChangedListener>();
	/**
	 * Create the panel.
	 */
	public AdjazenzmatrixInput(Graph graph) {
		this.graph=graph;
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
		AdjazenzmatrixView adjazenzmatrixView = new AdjazenzmatrixView(7, WeightMode.DOUBLE_MODE);
		graphViewAndMatrixPane.setLeftComponent(adjazenzmatrixView);

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
		NodesView nodesView = new NodesView(nodes,connections, new LinkedList<Path>());
		nodesAndAlgorithmusPane.setLeftComponent(nodesView);
	}

	private void notifyGraphChangeListener()
	{
		for(IGraphChangedListener listener:graphChangeListener)
		{
			listener.graphChanged(graph);
		}
	}
	
	@Override
	public void nodeAdded(Node node) {
		graph.addNode(node);
		notifyGraphChangeListener();
	}

	@Override
	public void nodeRemoved(Node node) {
		graph.removeNode(node);
		notifyGraphChangeListener();
	}

	@Override
	public void connectionAdded(Connection connection) {
		graph.addConnection(connection);
		notifyGraphChangeListener();		
	}

	@Override
	public void connectionRemoved(Connection connection) {
		graph.removeConnection(connection);
		notifyGraphChangeListener();
	}

	@Override
	public void pathAdded(Path path) {
		graph.addPath(path);
		notifyGraphChangeListener();
	}

	@Override
	public void pathRemoved(Path path) {
		graph.removePath(path);
		notifyGraphChangeListener();
	}

	@Override
	public void graphChanged(Graph graph) {
		if(graph!=null)
			this.graph=graph;
		notifyGraphChangeListener();
	}

}
