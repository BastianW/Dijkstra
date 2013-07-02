package de.bwvaachen.graph.gui.input.visualgraph;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map.Entry;

import de.bwvaachen.graph.logic.Graph;
import de.bwvaachen.graph.logic.Node;

public class VisualGraphContainer {

	
	private Graph graph;
	private HashMap<Node, Point>visualNodeMap=new HashMap<Node, Point>();
	public VisualGraphContainer(Graph graph,  HashMap<Node, VisualNode> visualNodeMap) {
		this.graph=graph;
		for(Entry<Node,VisualNode>entry:visualNodeMap.entrySet())
		this.visualNodeMap.put(entry.getKey(), entry.getValue().getLocation());
	}
	public Graph getGraph() {
		return graph;
	}
	public HashMap<Node, Point> getVisualNodeMap() {
		return visualNodeMap;
	}
}
