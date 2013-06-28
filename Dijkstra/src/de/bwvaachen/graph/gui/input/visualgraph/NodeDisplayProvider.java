package de.bwvaachen.graph.gui.input.visualgraph;

import java.awt.Point;

import de.bwvaachen.graph.logic.Node;


public abstract class NodeDisplayProvider {

	public abstract String decorate(String node, Number weight);
	public Point decorationAnchor(VisualNode node)
	{
		return new Point(node.getWidth(),0);
	}
	public abstract String label(String node, Number weight);
}
