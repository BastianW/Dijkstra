package de.bwvaachen.graph.gui.input.visualgraph;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;

import de.bwvaachen.graph.logic.Connection;
import de.bwvaachen.graph.logic.Graph;
import de.bwvaachen.graph.logic.Node;

public class SaveContainer
	{
		private Graph graph;
		private HashMap<Node, MyPoint>pointMap=new HashMap<Node, MyPoint>();
		
		public SaveContainer(Graph graph, HashMap<Node, Point> pointMap) {
			this.graph = graph;
			for(Entry<Node,Point>entry:pointMap.entrySet())
			this.pointMap.put(entry.getKey(), new MyPoint(entry.getValue()));
		}

		public Graph getGraph() {
			return graph;
		}

		public void setGraph(Graph graph) {
			this.graph = graph;
		}

		public HashMap<Node, MyPoint> getPointMap() {
			return pointMap;
		}

		public void setPointMap(HashMap<Node, MyPoint> pointMap) {
			this.pointMap = pointMap;
		}
		public void save(String filePath) throws JsonGenerationException, JsonMappingException, IOException
		{
			  ObjectMapper mapper = new ObjectMapper();
			  mapper.enable(SerializationFeature.INDENT_OUTPUT);
			  mapper.writeValue(new File(filePath), this);
		}
		public static SaveContainer load(String filePath) throws JsonProcessingException, IOException
		{
			 ObjectMapper mapper = new ObjectMapper();
			 JsonNode jsonTree = mapper.readTree(new File(filePath));
			 ObjectNode graph_Node=(ObjectNode) jsonTree.get("graph");
			 Graph graph=Graph.load(mapper,graph_Node);
			ObjectNode pointMap_Node=(ObjectNode) jsonTree.get("pointMap");
			HashMap<Node, Point>pointMap=new HashMap<Node, Point>();
			for(Node node:graph.getNodes())
			{
				ObjectNode objectNode=(ObjectNode)pointMap_Node.get(node.getName());
				if(objectNode!=null)
				{
					MyPoint myPoint= mapper.readValue(objectNode.toString(), MyPoint.class);
					pointMap.put(node,myPoint.getPoint());
				}
				
			}
			 return new SaveContainer(graph, pointMap);//new SaveContainer(graph, pointMap);
		}
		
	}