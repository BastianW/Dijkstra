package de.bwvaachen.graph.gui.input.nodesview;

import java.util.Collection;

import de.bwvaachen.graph.logic.Connection;
import de.bwvaachen.graph.logic.Node;

public class ConnectionsModel implements INodesViewTreeModel {

	private Collection<Connection> connections;
	private Node parent;

	public ConnectionsModel(Node node,Collection<Connection> connections) {
		this.connections = connections;
		this.parent=node;
	}

	@Override
	public String toString() {
		return "Connections";
	}

	@Override
	public Object getChild(int index) {
		int i = 0;
		Node node = parent;
		for (Connection connection : connections) {
			if (connection.containsNode(node)) {
				if (i == index)
					return new ConnectionModel(node, connection);
				i++;
			}
		}
		return null;
	}

	@Override
	public int getChildCount() {
		int counter = 0;
		for (Connection connection : connections) {
			if (connection.containsNode(parent)) {
				counter++;
			}
		}
		return counter;
	}

	@Override
	public boolean isLeaf() {
		return getChildCount() == 0;
	}

	@Override
	public int getIndexOfChild(Object child) {
		int index = 0;
		Connection connection = ((ConnectionModel) child).getConnection();
		for (Connection c : connections) {
			if (connection.equals(c))
				return index;
			if (c.containsNode(parent))
				index++;
		}
		return index;
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
