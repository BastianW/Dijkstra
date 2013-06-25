package de.bwvaachen.graph.gui.input;

import java.awt.BorderLayout;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

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
		
		AdjazenzmatrixView adjazenzmatrixView = new AdjazenzmatrixView(graph, WeightMode.DOUBLE_MODE);
		graphViewAndMatrixPane.setLeftComponent(adjazenzmatrixView);
		
		VisualGraph visualGraph=new VisualGraph(graph);
		graphViewAndMatrixPane.setRightComponent(visualGraph);

		NodesView nodesView = new NodesView(graph);
		nodesAndAlgorithmusPane.setLeftComponent(nodesView);
		
		graphChangeListener.add(nodesView);
		graphChangeListener.add(adjazenzmatrixView);
		graphChangeListener.add(visualGraph);
		nodesView.addGraphComponentChangedListener(this);
		adjazenzmatrixView.addGraphComponentChangedListener(this);
		visualGraph.addGraphComponentChangedListener(this);
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
