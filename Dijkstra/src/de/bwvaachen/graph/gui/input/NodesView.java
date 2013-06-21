package de.bwvaachen.graph.gui.input;

import java.awt.BorderLayout;
import java.util.Collection;
import java.util.LinkedList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import de.bwvaachen.graph.gui.input.nodesview.ConnectionsModel;
import de.bwvaachen.graph.gui.input.nodesview.INodesViewTreeModel;
import de.bwvaachen.graph.gui.input.nodesview.IconTreeRenderer;
import de.bwvaachen.graph.gui.input.nodesview.PathModel;
import de.bwvaachen.graph.gui.input.nodesview.PathsModel;
import de.bwvaachen.graph.logic.Connection;
import de.bwvaachen.graph.logic.Node;
import de.bwvaachen.graph.logic.Path;

public class NodesView extends JPanel {

	LinkedList<Connection> connections;
	LinkedList<Node> nodes;
	LinkedList<Path> paths;

	/**
	 * Create the panel.
	 */
	public NodesView(Collection<Node> nodes,
			Collection<Connection> connections, Collection<Path> paths) {
		setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane);

		JTree tree = new JTree();
		this.nodes = new LinkedList<Node>(nodes);
		this.connections = new LinkedList<Connection>(connections);
		tree.setModel(new NodeModel());
		tree.setCellRenderer(new IconTreeRenderer());
		scrollPane.setViewportView(tree);

		this.nodes = new LinkedList<Node>(nodes);
		this.connections = new LinkedList<Connection>(connections);
		this.paths = new LinkedList<Path>(paths);

	}

	class NodeModel implements TreeModel {
		@Override
		public Object getRoot() {
			return "Nodes";
		}

		@Override
		public Object getChild(Object parent, int index) {
			if (parent instanceof String) {
				return nodes.get(index);
			}
			if (parent instanceof INodesViewTreeModel) {
				return ((INodesViewTreeModel) parent).getChild(index);
			}
			if (parent instanceof Node) {
				Node node = (Node) parent;
				if (index == 0)
					return new ConnectionsModel(node, connections);
				else if (index == 1)
					return new PathsModel(node, paths);
			}

			return null;
		}

		@Override
		public int getChildCount(Object parent) {
			if (parent instanceof String) {
				return nodes.size();
			}
			if (parent instanceof INodesViewTreeModel) {
				return ((INodesViewTreeModel) parent).getChildCount();
			}
			if (parent instanceof Node) {
				return 2;
			}
			return 0;
		}

		@Override
		public boolean isLeaf(Object object) {
			if (object instanceof String) {
				return nodes == null || nodes.size() == 0;
			}
			if (object instanceof INodesViewTreeModel) {
				return ((INodesViewTreeModel) object).isLeaf();
			}
			if (object instanceof Node)
				return false;
			return true;
		}

		@Override
		public void valueForPathChanged(TreePath path, Object newValue) {
			System.out.println("Value changed");// TODO

		}

		@Override
		public int getIndexOfChild(Object parent, Object child) {
			if (parent instanceof INodesViewTreeModel) {
				return ((INodesViewTreeModel) parent).getIndexOfChild(child);
			}
			int index = -1;
			if (parent instanceof Node)
				if (parent instanceof ConnectionsModel)
					return 0;
				else if (parent instanceof PathsModel)
					return 1;
			return index;
		}

		@Override
		public void addTreeModelListener(TreeModelListener l) {
			// TODO Auto-generated method stub

		}

		@Override
		public void removeTreeModelListener(TreeModelListener l) {
			// TODO Auto-generated method stub

		}

	}
}
