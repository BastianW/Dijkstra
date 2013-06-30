package de.bwvaachen.graph.gui.input.visualgraph;

import java.awt.Color;
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
	public Node getNode() {
		return node;
	}

	private NodeDisplayProvider nodeDisplayProvider=new DefaultNodeDisplayProvider();;
	private Number weight;


	public VisualNode(VisualGraph graph, Node node, Number weight) {
		super(node.getName());
		this.weight=weight;
		setHorizontalAlignment(CENTER);
		this.visualGraph = graph;
		setSize(80, 20);// TODO
		this.node = node;
		//setOpaque(true);
		addMouseMotionListener(new MouseMotionAdapter() {
			private long last=System.currentTimeMillis();
			private Point offset=new Point(0,0);
			@Override
			public void mouseDragged(MouseEvent e) {
				if(System.currentTimeMillis()-last>500)
				{
					Point point = visualGraph.getMousePosition();
					Point location = VisualNode.this.getLocation();
					if(point==null)
					{
						offset=new Point(0,0);
					}
					else
					{
						offset=new Point(point.x-location.x,point.y-location.y);
					}
				}
				if(e.isAltDown())
				{
				
				Point point = visualGraph.getMousePosition();
				if (point != null) {
				point.x-=offset.x;
				point.y-=offset.y;
					setBounds(new Rectangle(point, getSize()));
					visualGraph.repaint();
				}}
				last=System.currentTimeMillis();
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

	public Number getWeight() {
		return weight;
	}

	public void setWeight(Number weight) {
		this.weight = weight;
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
	public void paintComponent(Graphics g) {
		setText(getLabel());
		Color c=g.getColor();
		g.setColor(Color.green);
		if(recView)
		g.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
		else
		g.fillOval(0, 0, getWidth() - 1, getHeight() - 1);
		g.setColor(c);
		drawDeco(g);
		super.paintComponent(g);
	}


	private String getLabel()
	{
		return this.nodeDisplayProvider.label(getName(), this.weight);
	}
	private void drawDeco(Graphics g)
	{
		Point p=this.nodeDisplayProvider.decorationAnchor(this);
		String deco=this.nodeDisplayProvider.decorate(getName(), weight);
		if(deco!=null)
		{
			g.drawString(deco, p.x, p.y);
		}
	}
	
	
	public void setNodeDisplayProvider(NodeDisplayProvider provider) {
		if(provider!=null)
		this.nodeDisplayProvider=provider;
		else
		{
			this.nodeDisplayProvider=new DefaultNodeDisplayProvider();
		}
		
	}
	
	class DefaultNodeDisplayProvider extends NodeDisplayProvider
	{
		@Override
		public String label(String node, Number weight) {
			return node;
		}
		@Override
		public String decorate(String node, Number weight) {
			return null;
		}
	}
	
}