package de.bwvaachen.graph.gui.input;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;

import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import de.bwvaachen.graph.gui.input.controller.IGraphChangedListener;
import de.bwvaachen.graph.gui.input.controller.IGraphComponentChangedListener;
import de.bwvaachen.graph.gui.input.nodesview.ConnectionModel;
import de.bwvaachen.graph.gui.input.nodesview.ConnectionsModel;
import de.bwvaachen.graph.gui.input.nodesview.INodesViewTreeModel;
import de.bwvaachen.graph.gui.input.nodesview.IconTreeRenderer;
import de.bwvaachen.graph.gui.input.nodesview.NodePopup;
import de.bwvaachen.graph.gui.input.nodesview.NodesPopup;
import de.bwvaachen.graph.gui.input.nodesview.PathModel;
import de.bwvaachen.graph.gui.input.nodesview.PathsModel;
import de.bwvaachen.graph.logic.Connection;
import de.bwvaachen.graph.logic.Graph;
import de.bwvaachen.graph.logic.Node;
import de.bwvaachen.graph.logic.Path;

public class NodesView extends JPanel implements IGraphChangedListener {

	LinkedList<Connection> connections;
	LinkedList<Node> nodes;
	LinkedList<Path> paths;
	private JTree tree;
	private HashSet<IGraphComponentChangedListener> graphComponentListener = new HashSet<IGraphComponentChangedListener>();
	NodesPopup nodesPopup=new NodesPopup(this);
	NodePopup nodePopup=new NodePopup(this);


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
		tree.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				int selRow = tree.getRowForLocation(e.getX(), e.getY());
				TreePath selPath = tree.getPathForLocation(e.getX(), e.getY());
				if (selRow != -1) {
					Object obj = selPath.getLastPathComponent();
					if (obj instanceof String) {
						nodesPopup.show(e.getComponent(), e.getX(), e.getY());
					} else if (obj instanceof Node)
					{
						nodePopup.show((Node) obj,e.getComponent(), e.getX(), e.getY());
					} else if (obj instanceof ConnectionModel) {
						
					} else if (obj instanceof ConnectionsModel) {
						
					}
				}
			}
		});

		scrollPane.setViewportView(tree);

		init(nodes, connections, paths);

	}

	public NodesView(Graph graph) {
		this(graph.getNodes(), graph.getSortedConnections(), graph.getPaths());

	}

	private void init(Collection<Node> nodes,
			Collection<Connection> connections, Collection<Path> paths) {
		initCollections(nodes, connections, paths);
		tree.setModel(new NodeModel());
	}

	private void initCollections(Collection<Node> nodes,
			Collection<Connection> connections, Collection<Path> paths) {
		this.nodes = new LinkedList<Node>(nodes);
		Collections.sort(this.nodes, new Comparator<Node>() {
			@Override
			public int compare(Node o1, Node o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});
		this.connections = new LinkedList<Connection>(connections);
		this.paths = new LinkedList<Path>(paths);
	}

	@Override
	public void graphChanged(Graph graph) {
        TreePath[] paths = tree.getSelectionPaths();
		initCollections(graph.getNodes(), graph.getSortedConnections(), graph.getPaths());
		tree.setModel(new NodeModel());
		tree.setSelectionPaths(paths);
	}

	public void addGraphComponentChangedListener(
			IGraphComponentChangedListener listener) {
		graphComponentListener.add(listener);
	}

	public void removeGraphComponentChangedListener(
			IGraphComponentChangedListener listener) {
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

	public Graph getGraph() {
		return new Graph(new HashSet<Node>(nodes), connections,paths);
	}

	public void addNode(Node node) {
		nodes.add(node);
		commitChange();
	}
	public void removeNode(Node node)
	{
		nodes.remove(node);
		LinkedList<Connection> deleteList=new LinkedList<Connection>();
		for(Connection connection: connections)
		{
			if(connection.containsNode(node))
				deleteList.add(connection);
		}
		connections.removeAll(deleteList);
		commitChange();
	}
	public void addConnection(Connection connection)
	{
		connections.add(connection);
		commitChange();
	}
	public void removeConnection(Node node1, Node node2)
	{
		Connection c=null;
		for(Connection connection: connections)
		{
			if(connection.connectNodes(node1,node2))
				c=connection;
		}
		connections.remove(c);
		commitChange();
	}
	
	public void commitChange() {
		Graph graph=getGraph();
		for (IGraphComponentChangedListener listener : graphComponentListener) {
			listener.graphChanged(graph);
		}
	}
}
