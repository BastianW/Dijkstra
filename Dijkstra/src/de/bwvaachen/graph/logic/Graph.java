package de.bwvaachen.graph.logic;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class Graph {
	HashSet<Node>nodes=new HashSet<Node>();
	ArrayList<Connection>connections=new ArrayList<Connection>();
	LinkedList<Path>paths=new LinkedList<Path>();
	
	
public Graph() {
}
	public Graph(Set<Node>nodes,Collection<Connection>connections,Collection<Path>paths ) {
		this.nodes.addAll(nodes);
		this.connections.addAll(connections);
		this.paths.addAll(paths);
	}
	public Graph(Graph graph) {
		this(graph.getNodes(),graph.getSortedConnections(),graph.getPaths());
	}
	public List<Connection> getSortedConnections() {
		Collections.sort(connections);
		return Collections.unmodifiableList(connections);
	}
	public List<Path>getPaths()
	{
		return Collections.unmodifiableList(paths);
	}
	
	public void addConnection(Connection c)
	{
		if(!(nodes.contains(c.getStartNode())&& nodes.contains(c.getEndNode())) || c==null)
			throw new IllegalArgumentException();
		if(connections.contains(c))
		{
			throw new IllegalArgumentException();
		}
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
		LinkedList<Connection>removeList=new LinkedList<Connection>();
		for(Connection c : connections)
		{
			if(c.containsNode(node))
				removeList.add(c);
		}
		connections.removeAll(removeList);
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
	public void save(String filePath) throws JsonGenerationException, JsonMappingException, IOException
	{
		  ObjectMapper mapper = new ObjectMapper();
		  mapper.enable(SerializationFeature.INDENT_OUTPUT);
		  mapper.writeValue(new File(filePath), this);
	}
	public static Graph load(String filePath) throws JsonParseException, JsonMappingException, IOException
	{
		 ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonTree = mapper.readTree(new File(filePath));
		ArrayNode json_Nodes=(ArrayNode) jsonTree.get("nodes");
		Iterator<JsonNode> elements = json_Nodes.elements();
		HashSet<Node> nodes=new HashSet<Node>();
		while(elements.hasNext())
		{
			Node node = mapper.readValue(elements.next().toString(), Node.class);
			nodes.add(node);
		}
		
		ArrayNode json_Connections=(ArrayNode) jsonTree.get("sortedConnections");
		elements = json_Connections.elements();
		Set<Connection>connections=new HashSet<Connection>();
		while(elements.hasNext())
		{
			Connection connection= mapper.readValue(elements.next().toString(), Connection.class);
			connections.add(connection);
		}
		
		
		ArrayNode json_Paths=(ArrayNode) jsonTree.get("paths");
		elements = json_Paths.elements();
		Set<Path>paths=new HashSet<Path>();
		while(elements.hasNext())
		{
			Path path= mapper.readValue(elements.next().toString(), Path.class);
			paths.add(path);
		}
		return new Graph( nodes, connections,paths);
	}
}
