package de.bwvaachen.graph.logic;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class Graph {
	HashSet<Node>nodes=new HashSet<Node>();
	SortedSet<Connection>connections=new TreeSet<Connection>();
	HashSet<Path>paths=new HashSet<Path>();
	
	public SortedSet<Connection> getSortedConnections() {
		return Collections.unmodifiableSortedSet(connections);
	}
	
	public Graph(Set<Node>nodes,Set<Connection>connections,Set<Path>paths ) {
		this.nodes.addAll(nodes);
		this.connections.addAll(connections);
		this.paths.addAll(paths);
	}
	
	public void addConnection(Connection c)
	{
		if(!(nodes.contains(c.getStartNode())&& nodes.contains(c.getEndNode())) || c==null)
			throw new IllegalArgumentException();
		connections.add(c);
	}
	
	public void removeConnection(Connection c)
	{
		if(c!=null)
		connections.remove(c);
	}
	
	public void addNode(Node node)
	{
		if(node!=null)
		nodes.add(node);
	}
	public void removeNode(Node node)
	{
		if(node!=null)
		nodes.remove(node);
	}
	
	public Set<Node> getNodes()
	{
		return Collections.unmodifiableSet(nodes);
	}
	
	public void addPath(Path path)
	{
		boolean testConnectionContained=true;
		for(Connection c: path.getConnections())
		{
			if(!connections.contains(c))
			{
				testConnectionContained=false;
			}
		}
		
		if(path==null || false)
			throw new IllegalArgumentException();
		
		paths.add(path);
	}
	
	public void removePath(Path path)
	{
		paths.remove(path);
	}
	
}
