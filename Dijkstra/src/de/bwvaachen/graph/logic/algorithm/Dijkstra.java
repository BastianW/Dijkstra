package de.bwvaachen.graph.logic.algorithm;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import de.bwvaachen.graph.logic.Connection;
import de.bwvaachen.graph.logic.Graph;
import de.bwvaachen.graph.logic.INode;
import de.bwvaachen.graph.logic.Node;
import de.bwvaachen.graph.logic.Path;

public class Dijkstra {

	List<Connection> connectionList;

	HashMap<Node, Node> previousNodes;
	LinkedList<WeightedNode> nodes;

	private boolean isFinished = false;
	private int currentStepCounter = 0;

	private Graph graph;

	private Node start;

	private Iterator<Connection> connectIt;

	private List<Connection> deleteList;

	private WeightedNode lightestNode;

	private Node neighbour;

	private Integer maxSteps;

	public Dijkstra(Graph graph, Node startNode) {
		if (graph == null || startNode == null)
			throw new IllegalArgumentException();
		this.graph = graph;
		this.start = startNode;
		init();
	}

	private void init() {
		currentStepCounter=0;
		connectionList = new LinkedList<Connection>(
				graph.getSortedConnections());
		deleteList = new LinkedList<Connection>();
		connectIt = connectionList.iterator();
		previousNodes = new HashMap<Node, Node>();
		nodes = new LinkedList<WeightedNode>();
		Set<Node> nodeSet = graph.getNodes();
		for (Node node : nodeSet) {
			if (node.equals(start)) {
				nodes.add(new WeightedNode(node, new Integer(0)));
			} else {
				nodes.add(new WeightedNode(node, Double.MAX_VALUE));
			}
			previousNodes.put(node, null);
		}
		Collections.sort(nodes);
		lightestNode = nodes.removeFirst();
		neighbour = null;
	}

	private WeightedNode getWeightedNode(Node node) {
		for (WeightedNode wNode : nodes) {
			if (wNode.equals(node)) {
				return wNode;
			}
		}
		return null;
	}

	public void doDijkstra() {

		while (!nodes.isEmpty()) {
			stepForward();
		}
	}
	public void stepForward()
	{
		if(!nodes.isEmpty())
		{
		if(!connectIt.hasNext())
		{
			lightestNode = nodes.removeFirst();
			neighbour = null;
			connectionList.removeAll(deleteList);
			connectIt = connectionList.iterator();
			deleteList=new LinkedList<Connection>();
			Collections.sort(nodes);
		}
		while ( connectIt.hasNext()) {
			Connection connection=connectIt.next();
			if (connection.containsNode(lightestNode)) {
				deleteList.add(connection);
				neighbour = connection.getEndNode();
				if (neighbour.equals(lightestNode))
					neighbour = connection.getStartNode();
				WeightedNode weightedNode = null;
				if ((weightedNode = getWeightedNode(neighbour)) != null) {
					if(distance_Update(lightestNode, weightedNode, connection.weight()))
						break;
				}
				else
					System.out.println("Fehler");
			}
		}

		}
	}
	public void processConnection()
	{
		
	}
	public void stepBackward()
	{
		int steps=currentStepCounter-1;
		init();
		while(currentStepCounter<steps)
		{
			stepForward();
		}
	}

	private boolean distance_Update(WeightedNode node, WeightedNode neighbour,
			double weightBetween) {
		double distance =weightBetween + node.getWeight().doubleValue();
		double oldDistance=neighbour.getWeight().doubleValue();
		if (distance < oldDistance) {
			if (distance == (int) distance)
				neighbour.setWeight(new Integer((int) distance));
			else
				neighbour.setWeight(new Double(distance));
			previousNodes.put(neighbour, node);
			currentStepCounter++;
			return true;
		}
		return false;
		
	}

	public Path getShortestPath(Node end) {
		List<Connection> connections = graph.getSortedConnections();
		Node node = end;

		LinkedList<Connection> pathConnections = new LinkedList<Connection>();
		for (Node previousNode = previousNodes.get(node); previousNode != null; previousNode = previousNodes
				.get(node)) {
			for (Connection connection : connections) {
				if (connection.connectNodes(node, previousNode)) {
					pathConnections.addFirst(connection);
					break;
				}
			}
			node = previousNode;
		}
		if(pathConnections.isEmpty())
			return null;
		return new Path(pathConnections);
	}

	public List<Path> getShortestPaths() {
		List<Path> results = new LinkedList<Path>();
		for (Node node : graph.getNodes()) {
//			if (nodes.contains(node))
//				continue;
			Path path = getShortestPath(node);
			if (path==null||path.isEmpty())
				continue;
			//path.sort(start);
			results.add(path);
		}
		return results;
	}

	public Graph getCurrentGraph() {
		Graph newGraph=new Graph(graph.getNodes(),graph.getSortedConnections(),getShortestPaths());
		return newGraph;
	}
	public int getMaxSteps()
	{
		if(maxSteps==null)
			{
			Dijkstra dijkstra=new Dijkstra(graph, start);
			dijkstra.doDijkstra();
			maxSteps=dijkstra.currentStepCounter;
			}
		return maxSteps; 
	}
	public int getCurrentSteps()
	{
		return currentStepCounter;
	}

}
