package de.bwvaachen.graph.gui.input.visualgraph;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;

import de.bwvaachen.graph.logic.Graph;
import de.bwvaachen.graph.logic.Node;

public class SaveContainer
	{
		private SavePropertiesContainer properties;
		private Graph graph;
		
		private HashMap<Node, MyPoint>pointMap=new HashMap<Node, MyPoint>();
		
		
		
		public SaveContainer(Graph graph, HashMap<Node, Point> pointMap, VisualGraphProperties properties) {
			this.properties=new SavePropertiesContainer(properties);
			this.graph = graph;
			for(Entry<Node,Point>entry:pointMap.entrySet())
			this.pointMap.put(entry.getKey(), new MyPoint(entry.getValue()));
			
		}

		protected SaveContainer() {

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
		public SavePropertiesContainer getProperties() {
			return properties;
		}

		public void setProperties(SavePropertiesContainer properties) {
			this.properties = properties;
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
			 ObjectNode properties_Node=(ObjectNode) jsonTree.get("properties");
			SavePropertiesContainer properties= mapper.readValue(properties_Node.toString(),SavePropertiesContainer.class);
			 ObjectNode graph_Node=(ObjectNode) jsonTree.get("graph");
			 Graph graph=Graph.load(mapper,graph_Node);
			ObjectNode pointMap_Node=(ObjectNode) jsonTree.get("pointMap");
			HashMap<Node, MyPoint>pointMap=new HashMap<Node, MyPoint>();
			for(Node node:graph.getNodes())
			{
				ObjectNode objectNode=(ObjectNode)pointMap_Node.get(node.getName());
				if(objectNode!=null)
				{
					MyPoint myPoint= mapper.readValue(objectNode.toString(), MyPoint.class);
					pointMap.put(node,myPoint);
				}
				
			}
			SaveContainer saveContainer = new SaveContainer();
			saveContainer.pointMap=pointMap;
			saveContainer.properties=properties;
			saveContainer.graph=graph;
			 return saveContainer;//new SaveContainer(graph, pointMap);
		}
		
		public static class SavePropertiesContainer
		{
			private int scaleFactor;
			private int connectionWeight;
			private int pathWeight;
			private int connectionColor;
			private int pathColor;
			private int lblColor;
			private boolean lblOpaque=true;
			private boolean backgroundImageIsShown=false;
			private boolean scaleFactorIsUsed=false;
			private String backgroundImage=null;
			private MyPoint size;
			
			public SavePropertiesContainer() {
			}
			public SavePropertiesContainer(VisualGraphProperties properties)
			{
				scaleFactor=properties.getScaleFactor();
				connectionWeight=properties.getConnectionWeight();
				pathWeight=properties.getPathWeight();
				connectionColor=properties.getConnectionColor().getRGB();
				pathColor=properties.getPathColor().getRGB();
				lblColor=properties.getLblColor().getRGB();
				lblOpaque=properties.isLblOpaque();
				backgroundImageIsShown=properties.isBackgroundImageIsShown();
				scaleFactorIsUsed=properties.isScaleFactorIsUsed();
				ByteArrayOutputStream baos=new ByteArrayOutputStream();
				Image image=properties.getBackgroundImage();
				if(image!=null)
				{
					int width=image.getWidth(null);
					int height=image.getHeight(null);
					BufferedImage bufferedImage = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
					Graphics2D g2d=(Graphics2D) bufferedImage.getGraphics();
					g2d.drawImage(image,0,0,width,height, null);
				try {
					ImageIO.write(bufferedImage, "jpg", baos );
					backgroundImage=Base64.encodeBase64String(baos.toByteArray() );
				} catch (IOException e) {
					e.printStackTrace();
				}
				}
				size=new MyPoint(properties.getSize().width, properties.getSize().height);
			}
			@JsonIgnore
			public VisualGraphProperties getProperties()
			{
				Image backgroundImage=null;
				if(this.backgroundImage!=null)
				{
					ByteArrayInputStream bis = new ByteArrayInputStream(Base64.decodeBase64(this.backgroundImage));
					try {
						backgroundImage= ImageIO.read(bis);
						bis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
		            
				}
				return new VisualGraphProperties(scaleFactor, connectionWeight, pathWeight, new Color(connectionColor), new Color(pathColor), new Color(lblColor), lblOpaque, backgroundImageIsShown, scaleFactorIsUsed, backgroundImage, new Dimension(size.getX(),size.getY()));
			}
			public int getScaleFactor() {
				return scaleFactor;
			}
			public void setScaleFactor(int scaleFactor) {
				this.scaleFactor = scaleFactor;
			}
			public int getConnectionWeight() {
				return connectionWeight;
			}
			public void setConnectionWeight(int connectionWeight) {
				this.connectionWeight = connectionWeight;
			}
			public int getPathWeight() {
				return pathWeight;
			}
			public void setPathWeight(int pathWeight) {
				this.pathWeight = pathWeight;
			}
			public int getConnectionColor() {
				return connectionColor;
			}
			public void setConnectionColor(int connectionColor) {
				this.connectionColor = connectionColor;
			}
			public int getPathColor() {
				return pathColor;
			}
			public void setPathColor(int pathColor) {
				this.pathColor = pathColor;
			}
			public int getLblColor() {
				return lblColor;
			}
			public void setLblColor(int lblColor) {
				this.lblColor = lblColor;
			}
			public boolean isLblOpaque() {
				return lblOpaque;
			}
			public void setLblOpaque(boolean lblOpaque) {
				this.lblOpaque = lblOpaque;
			}
			public boolean isBackgroundImageIsShown() {
				return backgroundImageIsShown;
			}
			public void setBackgroundImageIsShown(boolean backgroundImageIsShown) {
				this.backgroundImageIsShown = backgroundImageIsShown;
			}
			public boolean isScaleFactorIsUsed() {
				return scaleFactorIsUsed;
			}
			public void setScaleFactorIsUsed(boolean scaleFactorIsUsed) {
				this.scaleFactorIsUsed = scaleFactorIsUsed;
			}
			public String getBackgroundImage() {
				return backgroundImage;
			}
			public void setBackgroundImage(String backgroundImage) {
				this.backgroundImage = backgroundImage;
			}
			public MyPoint getSize() {
				return size;
			}
			public void setSize(MyPoint size) {
				this.size = size;
			}
			
		}
	}