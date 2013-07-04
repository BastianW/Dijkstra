package de.bwvaachen.graph.gui.input.nodesview;

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

public class NodesPopup extends JPopupMenu {
	public NodesPopup(final NodesView nodesView) {
		
		JMenuItem mntmAddNode = new JMenuItem("Add Node");
		mntmAddNode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PointerInfo info = MouseInfo.getPointerInfo();
				Point location = info.getLocation();
				AddNodeDialog addNodeDialog = new AddNodeDialog(nodesView.getGraph(),
						location);
				addNodeDialog.setVisible(true);
				if (addNodeDialog.newNodeWasCreated()) {
					Node node = addNodeDialog.getNode();
					nodesView.addNode(node);
				}
			}

		});
		add(mntmAddNode);
		
		JMenuItem mntmAddConnection = new JMenuItem("Add Connection");
		mntmAddConnection.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				AddConnection addConnection = new AddConnection(nodesView.getGraph());
				if(addConnection.isConnectionAdded())
				{
					nodesView.addConnection(addConnection.getConnection());
				}
				
			}
		});
		add(mntmAddConnection);
		
//		JMenuItem mntmRemoveConnection = new JMenuItem("Remove Connection");
//		add(mntmRemoveConnection);
	}
}
