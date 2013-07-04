package de.bwvaachen.graph.gui.input.nodesview;

import java.util.Collection;
import java.util.LinkedList;

import de.bwvaachen.graph.logic.Node;
import de.bwvaachen.graph.logic.Path;

public class PathsModel implements INodesViewTreeModel {

	LinkedList<Path> paths=new LinkedList<Path>();
	
	public PathsModel(Node node,Collection<Path>paths) {
		for(Path path :paths)
		{
			if(path.startsWith(node))
			{
				if(!path.isSorted(node))
				{
					path.sort(node);
					paths.add(new Path(path));
				}
			}
			else if(path.endsWith(node))
			{
				Path newPath=new Path(path);
				newPath.turnPath();
				paths.add(newPath);
			}
		}
	}
	@Override
	public Object getChild(int index) {
		return paths.get(index);
	}

	@Override
	public int getChildCount() {
		return paths.size();
	}

	@Override
	public boolean isLeaf() {
		return getChildCount()==0;
	}

	@Override
	public int getIndexOfChild(Object child) {
		return paths.indexOf(child);
	}
	@Override
	public String toString() {
		return "Paths";
	}
	@Override
	public int hashCode() {
		return toString().hashCode();
	}
	@Override
	public boolean equals(Object obj) {
		return toString().equals(obj);
	}
}
