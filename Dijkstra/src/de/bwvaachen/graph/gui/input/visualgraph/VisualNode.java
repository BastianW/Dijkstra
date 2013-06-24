package de.bwvaachen.graph.gui.input.visualgraph;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JLabel;

import de.bwvaachen.graph.gui.input.VisualGraph;
import de.bwvaachen.graph.logic.INode;
import de.bwvaachen.graph.logic.Node;

public class VisualNode extends JLabel implements INode {
	public boolean recView = false;
	public VisualGraph visualGraph = null;
	private Node node;

	public VisualNode(VisualGraph graph, Node node) {
		super(node.getName());
		setHorizontalAlignment(CENTER);
		this.visualGraph = graph;
		setSize(50, 20);// TODO
		this.node = node;
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				Point point = visualGraph.getMousePosition();
				if (point != null) {
					setBounds(new Rectangle(point, getSize()));
					visualGraph.repaint();
				}
			}
		});
	}

	public Point getOutputAnchor(VisualNode node) {
		Point result = null;
		if (recView) {

			Rectangle rec = this.getBounds();
			Point[] points = new Point[8];
			int i = 0;
			/* Point north_East */points[i++] = new Point(rec.x + rec.width,
					rec.y);
			/* Point south_East */points[i++] = new Point(rec.x + rec.width,
					rec.y + rec.height);
			/* Point south_West */points[i++] = new Point(rec.x, rec.y
					+ rec.height);
			/* Point north_West */points[i++] = new Point(rec.x, rec.y);
			/* Point north */points[i++] = new Point(rec.x + rec.width / 2,
					rec.y);
			/* Point east */points[i++] = new Point(rec.x + rec.width, rec.y
					+ rec.height / 2);
			/* Point west */points[i++] = new Point(rec.x, rec.y + rec.height
					/ 2);
			/* Point south */points[i++] = new Point(rec.x + rec.width / 2,
					rec.y + rec.height);

			double min = Double.MAX_VALUE;
			Point point = node.getCenter();

			for (i = 0; i < points.length; i++) {
				double tmp = Math.sqrt(Math.pow(point.x - points[i].x, 2)
						+ Math.pow(point.y - points[i].y, 2));
				if (tmp <= min) {
					min = tmp;
					result = points[i];
				}

			}
		}
		else
		{
			return getCenter();
		}

		return result;
	}

	public Point getCenter() {
		Rectangle rec = this.getBounds();
		Point point = new Point(rec.x + rec.width / 2, rec.y + rec.height/2);
		return point;
	}

	@Override
	public String getName() {
		return node.getName();
	}

	public String toString() {
		return node.toString();
	}

	public int hashCode() {
		return node.hashCode();
	}

	public boolean equals(Object obj) {
		return node.equals(obj);
	}

	@Override
	protected void paintComponent(Graphics g) {
		if(recView)
		g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
		else
		g.drawOval(0, 0, getWidth() - 1, getHeight() - 1);
		super.paintComponent(g);
	}
}