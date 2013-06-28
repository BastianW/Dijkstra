package de.bwvaachen.graph.gui.input;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;

import de.bwvaachen.graph.gui.input.controller.IGraphChangedListener;
import de.bwvaachen.graph.gui.input.controller.IGraphComponentChangedListener;
import de.bwvaachen.graph.gui.input.visualgraph.AddNodeDialog;
import de.bwvaachen.graph.gui.input.visualgraph.NodeDisplayProvider;
import de.bwvaachen.graph.gui.input.visualgraph.VisualNode;
import de.bwvaachen.graph.logic.Connection;
import de.bwvaachen.graph.logic.Graph;
import de.bwvaachen.graph.logic.Node;
import de.bwvaachen.graph.logic.Path;

public class VisualGraph extends JPanel implements IGraphChangedListener{

	private HashMap<Node,VisualNode>nodes=new HashMap<Node, VisualNode>();
	private Graph graph;
	private JPanel panel;
	private HashSet<IGraphComponentChangedListener>graphComponentListener=new HashSet<IGraphComponentChangedListener>();
	private NodeDisplayProvider nodeDisplayProvider;

	/**
	 * Create the panel.
	 */
	public VisualGraph() {
		setLayout(new GridLayout(0, 1));
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane);
		
		panel=new JPanel()
		{
			@Override
			public void paintComponent(Graphics g) {
//				g.setColor(Color.WHITE);
//				g.fillRect(0, 0, getWidth(), getHeight());
//				g.setColor(Color.BLACK);
				super.paintComponent(g);
				drawPathsAndConnection((Graphics2D) g);
				
				//for(VisualNode node:nodes.values())
				//node.testpaintComponent(g);
//				
				
			}
		};
		
		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(panel, popupMenu);
		
		JMenuItem mntmAddNode = new JMenuItem("Add Node");
		mntmAddNode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddNodeDialog addNodeDialog=new AddNodeDialog();
				addNodeDialog.setVisible(true);
			}
		});
		popupMenu.add(mntmAddNode);
		
		JMenuItem mntmAddConnection = new JMenuItem("Add Connection");
		popupMenu.add(mntmAddConnection);
		
		JMenuItem mntmAddPath = new JMenuItem("Add Path");
		popupMenu.add(mntmAddPath);
		panel.setLayout(null);
		//add(panel);
		panel.setSize(430, 430);
		scrollPane.setViewportView(panel);
	}
	
	public VisualGraph(Graph graph)
	{	
		this();
		initGraph(graph);
	}
	@Override
	public void graphChanged(Graph graph) {
		panel.removeAll();
		HashMap<Node, VisualNode> equalElements = equalElements(graph);
		if(equalElements.size()==0)
		{
			initGraph(graph);
		}
		else
		{
			nodes=new HashMap<Node, VisualNode>();
			Point position=new Point(0,0);
			for(Node node:graph.getNodes())
			{
				Number weight = calculateWeight(graph, node);
				VisualNode visualNode=null;
				if((visualNode=equalElements.get(node))!=null)
				{
					visualNode.setWeight(weight);
					
				}
				else
				{
					visualNode=new VisualNode(this, node, weight);
					visualNode.setLocation(position);
					position.x+=visualNode.getWidth();
					if(position.x+30>this.getWidth());
					{
						position.x=0;
						position.y+=30;
					}
					
				}
				panel.add(visualNode);
				nodes.put(node, visualNode);
			}
			this.graph=graph;
		}
		repaint(0, 0, getSize().width, getSize().height);
	}
	private HashMap<Node,VisualNode> equalElements(Graph newGraph)
	{
		HashMap<Node, VisualNode>result=new HashMap<Node, VisualNode>();
		for(Node node:newGraph.getNodes())
		{

			if(nodes.containsKey(node));
			{
				result.put(node, nodes.get(node));
			}
		}
		return result;
	}
	public void setNodeDisplayProvider(NodeDisplayProvider provider)
	{
		if(this.nodeDisplayProvider!=provider)
		{
			this.nodeDisplayProvider=provider;
			
			for(VisualNode node:nodes.values())
			{
				node.setNodeDisplayProvider(provider);
			}
		}
	}
	public void initGraph(Graph graph) {
		this.graph=graph;
		nodes=new HashMap<Node, VisualNode>();
		LinkedList<Node> nodeList =new LinkedList<Node>( graph.getNodes());
		LinkedList<Connection> connections=new LinkedList<Connection>(graph.getSortedConnections()); 
		
		if(nodeList.isEmpty())
			return;
		Node currentNode=nodeList.removeFirst();
		LinkedList<VisualNode>visualSortedNodesList=new LinkedList<VisualNode>();
		
		while(!nodeList.isEmpty())
		{
			Number weight = calculateWeight(graph, currentNode);
			VisualNode visualNode=new VisualNode(this,currentNode, weight);
			visualSortedNodesList.addLast(visualNode);
			panel.add(visualNode);
			nodes.put(currentNode,visualNode);
			nodeList.remove(currentNode);
			if(!connections.isEmpty())
			{
				LinkedList<Connection>deleteList=new LinkedList<Connection>();
				for(Connection connection:connections)
				{
					if(connection.containsNode(currentNode))
					{
						deleteList.add(connection);
					}
				}
				if(!deleteList.isEmpty())
				{
					connections.removeAll(deleteList);
					Connection connection= deleteList.getFirst();
					currentNode=(Node) connection.getTheOtherNode(currentNode);
					continue;
				}
			}
			if(!nodeList.isEmpty())
				currentNode=nodeList.getFirst();
		}//end while
		
		calculatePositionsAnWeights(visualSortedNodesList);
		for(VisualNode node:nodes.values())
		{
			node.setNodeDisplayProvider(this.nodeDisplayProvider);
		}
		repaint(0, 0, getSize().width, getSize().height);
	}

	private Number calculateWeight(Graph graph, Node currentNode) {
		Number weight=null;
		for(Path path:graph.getPaths())
		{
			if(path.endsWith(currentNode))
			{
				weight=path.getWeight();
			}
		}
		return weight;
	}
	public void addGraphComponentChangedListener(IGraphComponentChangedListener listener)
	{
		graphComponentListener.add(listener);
	}
	public void removeGraphComponentChangedListener(IGraphComponentChangedListener listener)
	{
		graphComponentListener.remove(listener);
	}
	private void drawPathsAndConnection(Graphics2D g2d)
	{
		if(graph!=null)
		{
		List<Connection> sortedConnections = graph.getSortedConnections();
		for(Connection connection: sortedConnections)
		{
			VisualNode startNode=nodes.get(connection.getStartNode());
			VisualNode endNode=nodes.get(connection.getEndNode());
			
			Point startPoint=startNode.getOutputAnchor(endNode);
			Point endPoint=endNode.getOutputAnchor(startNode);
			
			g2d.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
			//g2d.drawString(connection.getEdge().getWeight().toString(), (startPoint.x-endPoint.x)/2+endPoint.x,(startPoint.y-endPoint.y)/2+endPoint.y);
		}
		List<Path>paths=graph.getPaths();
		HashSet<Connection>connections=new HashSet<Connection>();
		for(Path path:paths)
		{
			connections.addAll(path.getConnections());
		}
		Stroke stroke = g2d.getStroke();
		g2d.setStroke(new BasicStroke(3));
		for(Connection connection: connections)
		{
			VisualNode startNode=nodes.get(connection.getStartNode());
			VisualNode endNode=nodes.get(connection.getEndNode());
			
			Point startPoint=startNode.getOutputAnchor(endNode);
			Point endPoint=endNode.getOutputAnchor(startNode);

			g2d.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
			
		}
		g2d.setStroke(stroke);
		Color color = g2d.getColor();
		g2d.setColor(Color.RED);
		for(Connection connection: sortedConnections)
		{
			VisualNode startNode=nodes.get(connection.getStartNode());
			VisualNode endNode=nodes.get(connection.getEndNode());
			
			Point startPoint=startNode.getOutputAnchor(endNode);
			Point endPoint=endNode.getOutputAnchor(startNode);
			
			g2d.drawString(connection.getEdge().getWeight().toString(), (startPoint.x-endPoint.x)/2+endPoint.x,(startPoint.y-endPoint.y)/2+endPoint.y);
		}
		g2d.setColor(color);
		}
	}
	



	private void calculatePositionsAnWeights(LinkedList<VisualNode> visualSortedNodesList) {
		System.out.println("Calculate Position");
		Rectangle rec2=getBounds();
		int width=panel.getWidth();
		int height=panel.getHeight();
		
		int counter=0;
		int size=visualSortedNodesList.size();
		int radius=100;//TODO relativer Radius
		for(VisualNode visualNode:visualSortedNodesList)
		{

			double angle=calculateAngle(counter,size);
			Point point=calculateAnchor(radius,angle);
			Rectangle rec=visualNode.getBounds();
			if(point.x<=width/2)
				point.x-=rec.width;
			else
				point.x+=rec.width;
			
			if(point.y<=height/2)
				point.y-=rec.height;
			else
				point.y+=rec.height;
			visualNode.setLocation(point);
			counter++;
		}	
	}

	private Point calculateAnchor(int radius, double angle) {
		Point p=new Point();
		int width=panel.getWidth();
		int height=panel.getHeight();
		p.y=(int) (Math.cos(angle)*radius)+height/2;
		p.x=(int) (Math.sin(angle)*radius)+width/2;
		return p;
	}

	private double calculateAngle(int counter, int size) {
		return Math.PI * 2.0 *counter/ size;
	}
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
