package de.bwvaachen.graph.gui.input;

import java.awt.BorderLayout;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import de.bwvaachen.graph.gui.input.controller.IGraphChangedListener;
import de.bwvaachen.graph.gui.input.controller.IGraphComponentChangedListener;
import de.bwvaachen.graph.gui.input.nodesview.ConnectionsModel;
import de.bwvaachen.graph.gui.input.nodesview.INodesViewTreeModel;
import de.bwvaachen.graph.gui.input.nodesview.IconTreeRenderer;
import de.bwvaachen.graph.gui.input.nodesview.PathsModel;
import de.bwvaachen.graph.logic.Connection;
import de.bwvaachen.graph.logic.Graph;
import de.bwvaachen.graph.logic.Node;
import de.bwvaachen.graph.logic.Path;

public class NodesView extends JPanel implements IGraphChangedListener{

	LinkedList<Connection> connections;
	LinkedList<Node> nodes;
	LinkedList<Path> paths;
	private JTree tree;
	private HashSet<IGraphComponentChangedListener>graphComponentListener=new HashSet<IGraphComponentChangedListener>();

	/**
	 * Create the panel.
	 */
	public NodesView(Collection<Node> nodes,
			Collection<Connection> connections, Collection<Path> paths) {
		setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane);

		tree = new JTree();
		tree.setCellRenderer(new IconTreeRenderer());
		
		
		scrollPane.setViewportView(tree);

		init(nodes, connections, paths);

	}

	public NodesView(Graph graph) {
		this(graph.getNodes(),graph.getSortedConnections(),graph.getPaths());

	}
	private void init(Collection<Node> nodes,
			Collection<Connection> connections, Collection<Path> paths) {
		this.nodes = new LinkedList<Node>(nodes);
		this.connections = new LinkedList<Connection>(connections);
		this.paths = new LinkedList<Path>(paths);
		tree.setModel(new NodeModel());
	}
	
	@Override
	public void graphChanged(Graph graph) {
		init(graph.getNodes(),graph.getSortedConnections(),graph.getPaths());		
	}
	public void addGraphComponentChangedListener(IGraphComponentChangedListener listener)
	{
		graphComponentListener.add(listener);
	}
	public void removeGraphComponentChangedListener(IGraphComponentChangedListener listener)
	{
		graphComponentListener.remove(listener);
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
