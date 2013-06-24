package de.bwvaachen.graph.gui.input.nodesview;

import de.bwvaachen.graph.logic.Connection;
import de.bwvaachen.graph.logic.INode;
import de.bwvaachen.graph.logic.Node;

public class ConnectionModel implements INodesViewTreeModel{

	Node parent;
	private Connection connection;
	public ConnectionModel(Node parent,Connection connection) {
		this.parent=parent;
		this.connection=connection;
}
	@Override
	public String toString() {
		Node node=connection.getTheOtherNode(parent);
		return node+"("+getWeight(connection)+")";
	}
	private String getWeight(Connection connection) {
		double weight=connection.weight();
		if (weight == (int) weight) {
			return new Integer((int) weight).toString();
		} else
			return weight + "";
	}
	@Override
	public Object getChild(int index) {
		return null;
	}
	@Override
	public int getChildCount() {
		return 0;
	}
	@Override
	public boolean isLeaf() {
		return true;
	}
	@Override
	public int getIndexOfChild(Object child) {
		return -1;
	}
	public Connection getConnection() {
		return connection;
	}
}