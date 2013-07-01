package de.bwvaachen.graph.logic.algorithm.dijkstra;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import de.bwvaachen.graph.logic.Connection;
import de.bwvaachen.graph.logic.Edge;
import de.bwvaachen.graph.logic.Graph;
import de.bwvaachen.graph.logic.Node;
import de.bwvaachen.graph.logic.Path;
import de.bwvaachen.graph.logic.algorithm.ConsoleLogger;
import de.bwvaachen.graph.logic.algorithm.ILogger;
import de.bwvaachen.graph.logic.algorithm.WeightedNode;

public class Dijkstra {

	private List<Connection> connectionList;

	private HashMap<Node, Node> previousNodes;
	private LinkedList<WeightedNode> nodes;

	private boolean isFinished = false;
	private int currentStepCounter = 0;

	private Graph graph;

	private Node start;

	private Iterator<Connection> connectIt;

	private List<Connection> deleteList;

	private WeightedNode lightestNode;

	private Node neighbour;

	private Integer maxSteps;
	private ILogger logger=new ConsoleLogger();

	public Dijkstra(Graph graph, Node startNode) {
		if (graph == null || startNode == null)
			throw new IllegalArgumentException();
		this.graph = graph;
		this.start = startNode;
		init();
	}

	private void init() {
		logger.resetLogger();
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
		if(nodes.isEmpty()||connectionList.isEmpty())
		{
			return;
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
					distance_Update(lightestNode, weightedNode, connection.weight());
						return;
				}
				else
					System.out.println("Fehler");
			}
		}
		if(!connectIt.hasNext())
		{
			lightestNode = nodes.removeFirst();
			neighbour = null;
			connectionList.removeAll(deleteList);
			connectIt = connectionList.iterator();
			deleteList=new LinkedList<Connection>();
			stepForward();
		}
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
		
		logger.writeLine("Step "+ currentStepCounter+" ("+neighbour+") compare: "+node.getWeight().toString()+"+"+weightBetween+"<"+(neighbour.getWeight().doubleValue()==Double.MAX_VALUE?Character.toString('\u221E'):neighbour.getWeight()));
		currentStepCounter++;
		double distance =weightBetween + node.getWeight().doubleValue();
		double oldDistance=neighbour.getWeight().doubleValue();
		if (distance < oldDistance) {
			if (distance == (int) distance)
				neighbour.setWeight(new Integer((int) distance));
			else
				neighbour.setWeight(new Double(distance));
			previousNodes.put(neighbour, node);
			Collections.sort(nodes);
			logger.writeLine(neighbour+" gets new weight "+neighbour.getWeight());
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
		results.add(new Path(new Connection(start,start, new Edge(0))));
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
			dijkstra.setLogger(new ILogger() {
				@Override
				public void writeLine(String line) {
				}
				@Override
				public void resetLogger() {
				}
			});
			dijkstra.doDijkstra();
			maxSteps=dijkstra.currentStepCounter;
			}
		return maxSteps; 
	}
	public int getCurrentSteps()
	{
		return currentStepCounter;
	}

	public ILogger getLogger() {
		return logger;
	}

	public void setLogger(ILogger logger) {
		this.logger = logger;
	}


}
