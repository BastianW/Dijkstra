package de.bwvaachen.graph.gui;

import java.awt.BorderLayout;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingConstants;


public class AboutDialog extends JDialog {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			AboutDialog dialog = new AboutDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public AboutDialog() {
		setResizable(false);
		setTitle("About TolleFlugBuchung");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setAlwaysOnTop(true);
		setModal(true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout(0, 0));
		{
			JLabel lblEntwicklung = new JLabel("<html><center>Entwicklung<br>--------------------------------------------------<br>Bastian Winzen<br>Kevin Griesbach<center><br><br><br><br><br>Version 1.0 (Iteration 4)</html>");
			lblEntwicklung.setHorizontalAlignment(SwingConstants.CENTER);
			getContentPane().add(lblEntwicklung, BorderLayout.CENTER);
		}
	}

}
