package de.bwvaachen.graph.gui.input.visualgraph;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Point;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.GridLayout;
import javax.swing.JTextField;

import de.bwvaachen.graph.logic.Graph;
import de.bwvaachen.graph.logic.Node;

import java.awt.Dialog.ModalExclusionType;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AddNodeDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	boolean nodeWasCreated=false;
	protected Node node;



	/**
	 * Create the dialog.
	 */
	public AddNodeDialog(final Graph graph, final Point position) {
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setTitle("New Node");
		setBounds(100, 100, 356, 101);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(1, 0, 0, 0));
		JLabel lblName = new JLabel("Name:");
		contentPanel.add(lblName);

		textField = new JTextField();
		contentPanel.add(textField);
		textField.setColumns(10);
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nodeName=textField.getText();
				if(!nodeName.isEmpty())
				{
					Node node=new Node(nodeName);
					if(!graph.getNodes().contains(node))
					{
						setVisible(false);
						AddNodeDialog.this.node=node;
						nodeWasCreated=true;
					}
				}
				
			}
		});
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);
		setLocation(position.x-getWidth()/2,position.y-getHeight()/2);
	}



	public boolean newNodeWasCreated() {
		return nodeWasCreated;
	}



	public Node getNode() {
		return node;
		
	}

}
