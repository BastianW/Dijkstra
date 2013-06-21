package de.bwvaachen.graph.logic;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class Connection implements Comparable<Connection>{

	private Node startNode,endNode;//TODO NO NULLS
	private Edge edge;
	
	public Connection() {
		// TODO Auto-generated constructor stub
	}
	public Connection(Node node1, Node node2, Edge edge) {
		startNode=node1;
		endNode=node2;
		this.edge=edge;
	}

	public Connection(Connection c) {
		startNode=new Node(c.getStartNode());
		endNode=new Node(c.getEndNode());
		edge=new Edge(c.getEdge());
	}
	
	public double weight() {
		return edge.getWeight().doubleValue();
	}

	@Override
	public int compareTo(Connection o) {
		if(weight()>o.weight())
			return 1;
		else if(weight()<o.weight())
				return -1;
		return 0;
	}
	
	public void switchNodes()
	{
		Node tmpNode=startNode;
		startNode=endNode;
		endNode=tmpNode;
	}
	
	public Node getStartNode() {
		return startNode;
	}

	public Node getEndNode() {
		return endNode;
	}

	public Edge getEdge() {
		return edge;
	}
	public boolean connectNodes(Node start, Node end)
	{
		return (start.equals(startNode)||start.equals(endNode) )&&(end.equals(endNode)||end.equals(startNode));
	}
	@Override
	public String toString() {
		return "Connection from "+ startNode +" to "+endNode+" with weight "+edge.getWeight()+".";
	}
	@Override
	public boolean equals(Object obj) {
		if(obj==null)
			return false;
		if(obj==this)
			return true;
		if(obj instanceof Connection)
		{
			Connection c2=(Connection)obj;
			boolean boolNode1=(getStartNode().equals(c2.getStartNode())|| getStartNode().equals(c2.getEndNode()));
			boolean boolNode2=(getEndNode().equals(c2.getStartNode())|| getEndNode().equals(c2.getEndNode()));
			boolean boolEdge=c2.weight()==weight();
			return boolEdge && boolNode1 && boolNode2;
		}
		return false;
	}
	@Override
	public int hashCode() {
		return startNode.hashCode()*endNode.hashCode();
	}

	public boolean containsNode(Node node) {
		return node.equals(startNode)||node.equals(endNode);
	}
}

