package de.bwvaachen.graph.gui.input.visualgraph;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JSpinner;

public class AddConnection extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			AddConnection dialog = new AddConnection();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public AddConnection() {
		setTitle("New Connection");
		setBounds(100, 100, 443, 201);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(0, 2, 0, 0));
		{
			JLabel lblStartnode = new JLabel("Startnode:");
			contentPanel.add(lblStartnode);
		}
		{
			JComboBox comboBox = new JComboBox();
			contentPanel.add(comboBox);
		}
		{
			JLabel lblEndnode = new JLabel("Endnode:");
			contentPanel.add(lblEndnode);
		}
		{
			JComboBox comboBox = new JComboBox();
			contentPanel.add(comboBox);
		}
		{
			JLabel lblWeight = new JLabel("Weight:");
			contentPanel.add(lblWeight);
		}
		{
			JSpinner spinner = new JSpinner();
			contentPanel.add(spinner);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
