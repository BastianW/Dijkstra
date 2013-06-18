package de.bwvaachen.graph.logic.algorithm1;

import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import de.bwvaachen.graph.logic.Connection;
import de.bwvaachen.graph.logic.Graph;
import de.bwvaachen.graph.logic.Node;
import de.bwvaachen.graph.logic.Path;

public class Dijkstra {

	List<Connection>rawConnectionList;
	
	private boolean isFinished=false;
	private int currentStepCounter=0;
	
	private Graph graph;

	private Node start;

	private Node end;

	private LinkedList<Connection> freeConnections;
	private SortedSet<Path>paths;	
	
	public Dijkstra(Graph g, Node start , Node end) {
		this.graph=g;
		this.start=start;
		this.end=end;
		init();
	}
	
	public List<Path>getCurrentPaths()
	{
		return null;//TODO
	}
	public void stepForward()
	{
		if(isFinished)
			return;
		currentStepCounter++;
		
		//TODO
	}
	public void calculatePaths(Connection connection)
	{
		
		LinkedList<Path>newPaths=new LinkedList<Path>();
		for(Path path: paths)
		{
			
			try{
				Path newPath=null;//path.createNewSortedPath(connection);
				newPaths.add(newPath);

				Path deletePaths=calculateReduction(newPath);
				if(deletePaths!=null)
				{
					paths.remove(deletePaths);
				}
				
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	public Path calculateReduction(Path path)
	{
		Node node1=path.getStartNode();
		Node node2=path.getEndNode();
		double weight=path.getWeight().doubleValue();
		for(Path comparePath: paths)
		{
			//-1=kleiner 1=größer 0=gleich -1>notcomparable
				int compValue=comparePath.compare(node1,node2, weight);
				switch(compValue)
				{
				case -1:
					return path;
				case 1:
					return comparePath; 
				}
		}
		return null;
	}
	
	public void finish()
	{
		//TODO
	}
	private boolean checkIfFinished(){
		//TODO
		
		return isFinished;
	}
	public boolean isFinished()
	{
		return isFinished;
	}
	public void stepBackward()
	{
		if(currentStepCounter==0)
			return;
		isFinished=false;
		int stepCounter=--currentStepCounter;
		init();
		for(int i=0; i<stepCounter;i++)
		{
			stepForward();
		}
	}
	public void saveState()
	{
		//TODO
	}
	public void init()
	{
		freeConnections=new LinkedList<Connection>(graph.getSortedConnections());
	
	}
	public void reset()
	{
		init();
	}
}
