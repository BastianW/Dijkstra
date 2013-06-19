package de.bwvaachen.graph.logic;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class Path implements Comparable<Path> {

	private Node startNode,endNode;
	private LinkedList<Connection> connections=new LinkedList<Connection>();
	public Path() {
		// TODO Auto-generated constructor stub
	}
	public Path(Connection ... connections) {
		if(connections==null||connections.length==0)
			throw new IllegalArgumentException();
		for(Connection c: connections)
		{
			addConnection(c);
		}
	}
	public Path(Collection<Connection>connections) {
		if(connections==null||connections.size()==0)
			throw new IllegalArgumentException();
		for(Connection c: connections)
		{
			addConnection(c);
		}
	}
	public Path(Path path) {
		
		for(Connection c: path.getConnections())
		{
			connections.add(new Connection(c));
		}
		startNode=new Node(connections.getLast().getEndNode());
		endNode=new Node(connections.getFirst().getStartNode());
	}
	public Number getWeight()
	{
		double weight=0;
		for(Connection connection:connections)
		{
			weight+=connection.weight();
		}
		Double d=new Double(weight);
		if(d.intValue()==d.doubleValue())
			return new Integer(d.intValue());
		return weight;
	}

	public Node getStartNode() {
		return startNode;
	}

	public void setStartNode(Node startNode) {
		this.startNode = startNode;
	}

	public Node getEndNode() {
		return endNode;
	}

	public void setEndNode(Node endNode) {
		this.endNode = endNode;
	}
	public List<Connection> getConnections()
	{
		return Collections.unmodifiableList(connections);
	}
	private LinkedList<Connection> getModifieableConnections()
	{
		return connections;
	}
	public boolean connectNodes(Node start, Node end)
	{
		return (start.equals(startNode)||start.equals(endNode) )&&(end.equals(endNode)||end.equals(startNode));
	}
	public void addConnection(Connection c)
	{
		if(startNode==null )
		{
			startNode=c.getStartNode();
			endNode=c.getEndNode();
		}
		connections.add(c);
		if(!valid())
		{
			connections.remove();
			throw new IllegalArgumentException();
		}
		updateStartEnd();
	}
	private boolean valid()
	{
		return sortConnections();
	}
	private boolean sortConnections()
	{
		//TODO  
		return true;
	}
	private void updateStartEnd()
	{
		startNode=connections.getFirst().getStartNode();
		endNode=connections.getLast().getEndNode();
	}
	private Number getNodeWeight(Node node)
	{
		//TODO
		return null;
	}
	@Override
	public int compareTo(Path o) {
		if(o==null)
			throw new IllegalArgumentException();
		if(this.getWeight().doubleValue()>o.getWeight().doubleValue())
			return 1;
		if(this.getWeight().doubleValue()<o.getWeight().doubleValue())
			return -1;
		return 0;
	}
	public int compare(Node node1, Node node2, double weight) {
		boolean nodes=(node1.equals(startNode)||node2.equals(endNode))&&(node2.equals(startNode)||node2.equals(endNode));
		if(!nodes)
		{
			return -2;
		}
		else
		{
			if(this.getWeight().doubleValue()>weight)
				return 1;
			if(this.getWeight().doubleValue()<weight)
				return -1;
			return 0;
		}
	}
	public List<Path> createNewSortedPaths(Connection connection) throws Exception {
		sortConnections();
		Path newPath1=new Path(this);
		int[] position=newPath1.getPositionsOfIntgegration(connection);
		if(position.length==0)
			throw new Exception();
		else if(position.length==1)
		{
			//Split path
			LinkedList<Connection>connections=newPath1.getModifieableConnections();
			Connection neightbour=connections.get(position[0]);
			Node startNodeNeighbour=neightbour.getStartNode();
			if(startNodeNeighbour.equals(connection.getEndNode()))
			{
				for(int i=0; i<position[0];i++)
				connections.removeFirst();
				connections.addFirst(neightbour);
			}
			else
			{
				int size=connections.size();
				for(int i=position[0]++; i<size;i++)
				connections.removeLast();
				connections.addLast(connection);
			}	
		}
		else
		{
			//
		}
		return null;
	}
	private int[] getPositionsOfIntgegration(Connection connection)
	{
		int indexN1=getNodePosition(connection.getStartNode());
		int indexN2=getNodePosition(connection.getEndNode());
		int counter=0;
		if(indexN1>-1)
			counter++;
		if(indexN2>-1)
			counter++;
		int[] result=new int[counter];
		counter=0;
		if(indexN1>-1)
			result[counter++]=indexN1;
		if(indexN2>-1)
			result[counter]=indexN2;
		return result;
	}
	public boolean containsNode(Node node)
	{
		return getNodePosition(node)>=0;
	}
	private int getNodePosition(Node node)
	{
		int index=0;
		for(Connection c: connections)
		{
			if(c.containsNode(node))
				return index;
			index++;
		}
		return -1;
	}
	
	@Override
	public String toString() {
		StringBuilder builder=new StringBuilder();
		builder.append("Path:\n");
		
		for(Connection c:getConnections())
		{
			builder.append("\t\t"+c+"\n");
		}
		builder.append('\n');
		
		return builder.toString();
	}
}
