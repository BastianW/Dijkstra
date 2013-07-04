package de.bwvaachen.graph.gui.input.nodesview;

import java.awt.Component;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import de.bwvaachen.graph.gui.input.NodesView;
import de.bwvaachen.graph.gui.input.visualgraph.AddConnection;
import de.bwvaachen.graph.gui.input.visualgraph.AddNodeDialog;
import de.bwvaachen.graph.logic.Node;

public class NodePopup extends JPopupMenu {
	private Node node;

	public NodePopup(final NodesView nodesView) {
		JMenuItem mntmAddNode = new JMenuItem("Remove Node");
		mntmAddNode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(node!=null)
					nodesView.removeNode(node);
			}

		});
		add(mntmAddNode);
		
		JMenuItem mntmAddConnection = new JMenuItem("Add Connection");
		mntmAddConnection.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(node!=null)
				{
					AddConnection addConnection = new AddConnection(nodesView.getGraph(), node);
				if(addConnection.isConnectionAdded())
				{
					nodesView.addConnection(addConnection.getConnection());
				}}
				
			}
		});
		add(mntmAddConnection);
	}

	public void show(Node node, Component invoker, int x, int y) {
		this.node=node;
		super.show(invoker, x, y);
	}
	/**
	 * Use show(Node node, Component invoker, int x, int y)
	 */
	@Override
	@Deprecated
	public void show(Component invoker, int x, int y) {
		// TODO Auto-generated method stub
		super.show(invoker, x, y);
	}
}
