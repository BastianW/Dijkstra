package de.bwvaachen.graph.gui.input.nodesview;

import de.bwvaachen.graph.logic.Node;

public interface INodesViewTreeModel {
	public Object getChild(int index);
	public int getChildCount();
	public boolean isLeaf();
//	public void valueForPathChanged(TreePath path, Object newValue);
	public int getIndexOfChild(Object child) ;
}
