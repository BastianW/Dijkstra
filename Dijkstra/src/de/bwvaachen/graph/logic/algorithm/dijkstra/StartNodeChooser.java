package de.bwvaachen.graph.logic.algorithm.dijkstra;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListDataListener;

import de.bwvaachen.graph.gui.input.visualgraph.AddNodeDialog;
import de.bwvaachen.graph.logic.Node;
import java.awt.Dialog.ModalityType;

public class StartNodeChooser extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JComboBox comboBox;


	/**
	 * Create the dialog.
	 */
	public StartNodeChooser(Set<Node> nodes) {
		setModalityType(ModalityType.APPLICATION_MODAL);
		setBounds(100, 100, 327, 104);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(1, 0, 0, 0));

			JLabel lblNode = new JLabel("Startnode:");
			contentPanel.add(lblNode);

			comboBox = new JComboBox(nodes.toArray());
			comboBox.setSelectedIndex(0);
			contentPanel.add(comboBox);

			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);

			JButton okButton = new JButton("OK");
			okButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
								setVisible(false);		
				}
			});
			buttonPane.add(okButton);
			getRootPane().setDefaultButton(okButton);
//			JButton cancelButton = new JButton("Cancel");
//			cancelButton.addActionListener(new ActionListener() {
//				public void actionPerformed(ActionEvent e) {
//					setVisible(false);
//					dispose();
//				}
//			});
//			buttonPane.add(cancelButton);		
	}


	public Node getNode() {
		return (Node)comboBox.getSelectedItem();
	}

}
