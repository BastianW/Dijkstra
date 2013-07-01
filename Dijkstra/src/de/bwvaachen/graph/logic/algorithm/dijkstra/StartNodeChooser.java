package de.bwvaachen.graph.logic.algorithm.dijkstra;

import java.awt.BorderLayout;
import java.awt.Dialog.ModalityType;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import de.bwvaachen.graph.gui.MyDialog;
import de.bwvaachen.graph.logic.Node;

public class StartNodeChooser extends MyDialog {

	private final JPanel contentPanel = new JPanel();
	private JComboBox comboBox;
	protected boolean selected;


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
			comboBox.addMouseWheelListener(new MouseWheelListener() {
				
				@Override
				public void mouseWheelMoved(MouseWheelEvent e) {
						int i=comboBox.getSelectedIndex();
						int count=comboBox.getItemCount();
						int newIndex=0;
					if(e.getWheelRotation()>0)
					{
						newIndex=(i+count+1)%count;
					}
					else
					{
						newIndex=(i+count-1)%count;
					}
					comboBox.setSelectedIndex(newIndex);
				}
			});
			comboBox.setSelectedIndex(0);
			contentPanel.add(comboBox);

			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);

			JButton okButton = new JButton("OK");
			okButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
								setVisible(false);
								selected=true;
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
			comboBox.requestFocus();
			doLayout();
			adjust();
	}


	public Node getNode() {
		if(selected)
		return (Node)comboBox.getSelectedItem();
		return null;
	}

}
