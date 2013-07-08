package de.bwvaachen.graph.gui.input.visualgraph;

import java.awt.Color;
import java.awt.Point;


public abstract class NodeDisplayProvider {

	public abstract String decorate(String node, Number weight);
	public Point decorationAnchor(VisualNode node)
	{
		return new Point(node.getWidth(),0);
	}
	public abstract String getTooltipText(VisualNode node);
	public abstract boolean opaque();
	public abstract Color labelColor();
	public abstract String label(String node, Number weight);
}
