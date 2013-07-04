package de.bwvaachen.graph.gui.input.visualgraph;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.LinkedList;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

import de.bwvaachen.graph.gui.MyDialog;
import de.bwvaachen.graph.logic.Connection;
import de.bwvaachen.graph.logic.Edge;
import de.bwvaachen.graph.logic.Graph;
import de.bwvaachen.graph.logic.Node;

public class AddConnection extends MyDialog {

	private final JPanel contentPanel = new JPanel();
	private JComboBox endNode_comboBox;
	private JComboBox startNode_comboBox;
	private JSpinner spinner;
	private boolean isConnectionAdded;
	private Connection connection;

	/**
	 * Create the dialog.
	 */
	public AddConnection(final Graph graph) {
		Set<Node> nodes = graph.getNodes();
		Object[] array = nodes.toArray();
		init(graph, true, array, array);
	}
	public AddConnection(Graph graph, Node node) {
		LinkedList<Node> tmpHash=new LinkedList<Node>(graph.getNodes());
		tmpHash.remove(node);
		for(Connection c:graph.getSortedConnections())
		{
			if(c.containsNode(node))
			{
				Node n=c.getTheOtherNode(node);
				tmpHash.remove(n);
			}
		}
		if(tmpHash.isEmpty())
		{
			JOptionPane.showMessageDialog(AddConnection.this, "Connected to every other Node","No new connection possible",JOptionPane.ERROR_MESSAGE);
			setVisible(false);
		}
		else
		init(graph,true,new Object[]{node},tmpHash.toArray());
	}
	private void init(final Graph graph , boolean setVisible, Object[] array1, Object[] array2)
	{
		setModalityType(ModalityType.APPLICATION_MODAL);
		Set<Node> nodes = graph.getNodes();
		if(!nodes.isEmpty()){
		setModalityType(ModalityType.APPLICATION_MODAL);
		setTitle("New Connection");
		setBounds(100, 100, 443, 201);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(0, 2, 0, 0));
		JLabel lblStartnode = new JLabel("Startnode:");
		contentPanel.add(lblStartnode);
		
		
		startNode_comboBox = new JComboBox(array1);
		startNode_comboBox.setSelectedIndex(0);
		startNode_comboBox.addMouseWheelListener(new MouseWheelListener() {
			
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
					int i=startNode_comboBox.getSelectedIndex();
					int count=startNode_comboBox.getItemCount();
					int newIndex=0;
				if(e.getWheelRotation()>0)
				{
					newIndex=(i+count+1)%count;
				}
				else
				{
					newIndex=(i+count-1)%count;
				}
				startNode_comboBox.setSelectedIndex(newIndex);
			}
		});
		contentPanel.add(startNode_comboBox);

		JLabel lblEndnode = new JLabel("Endnode:");
		contentPanel.add(lblEndnode);


		endNode_comboBox = new JComboBox(array2);
		endNode_comboBox.setSelectedIndex(0);
		endNode_comboBox.addMouseWheelListener(new MouseWheelListener() {
			
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
					int i=endNode_comboBox.getSelectedIndex();
					int count=endNode_comboBox.getItemCount();
					int newIndex=0;
				if(e.getWheelRotation()>0)
				{
					newIndex=(i+count+1)%count;
				}
				else
				{
					newIndex=(i+count-1)%count;
				}
				endNode_comboBox.setSelectedIndex(newIndex);
			}
		});
		contentPanel.add(endNode_comboBox);

		JLabel lblWeight = new JLabel("Weight:");
		contentPanel.add(lblWeight);

		spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(new Double(1), null, null,
				new Double(1)));
		spinner.addMouseWheelListener(new MouseWheelListener() {
			
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
					double value=(Double) spinner.getValue();

					double newValue=0;
				if(e.getWheelRotation()>0)
				{
					newValue=value-1;
					if(newValue<0)
						newValue=0;
				}
				else
				{
					newValue=value+1;
				}
				spinner.setValue(newValue);
			}
		});
		contentPanel.add(spinner);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {


			@Override
			public void actionPerformed(ActionEvent e) {
				Node node1=(Node) startNode_comboBox.getSelectedItem();
				Node node2=(Node) endNode_comboBox.getSelectedItem();
				Number n=(Number) spinner.getValue();
				if(node1==node2)
				{
					JOptionPane.showMessageDialog(AddConnection.this, "Cannot connect a node with itself","Unvalid connection",JOptionPane.ERROR_MESSAGE);
					return;
				}
				if(n==null||n.doubleValue()<0)
				{
					JOptionPane.showMessageDialog(AddConnection.this, "No valid Edge","Unvalid weight",JOptionPane.ERROR_MESSAGE);
					return;
				}
				Edge edge=new Edge(n);
				
				Connection connection =new Connection(node1,node2, edge);
				try
				{
				graph.addConnection(connection);
				isConnectionAdded=true;
				AddConnection.this.connection=connection;
				}
				catch(Exception e1)
				{
					JOptionPane.showMessageDialog(AddConnection.this, "Connection already exists","Doubled Connection",JOptionPane.ERROR_MESSAGE);
					return;
				}
				setVisible(false);
				dispose();
			}
		});
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});
		buttonPane.add(cancelButton);
		doLayout();
		adjust();
		if(setVisible)
		{
			setVisible(setVisible);
			startNode_comboBox.requestFocus();
		}
		
	}
		else
			dispose();
	}

	public boolean isConnectionAdded()
	{
		return isConnectionAdded;
	}
	public Connection getConnection()
	{
		return this.connection;
	}

}
