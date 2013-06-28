package de.bwvaachen.graph.gui.input;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;

import de.bwvaachen.graph.gui.input.controller.IGraphChangedListener;
import de.bwvaachen.graph.logic.Graph;
import de.bwvaachen.graph.logic.algorithm.AlgorithmVisualatorProvider;
import de.bwvaachen.graph.logic.algorithm.AlgorithmVisualtorProviderCollector;
import de.bwvaachen.graph.logic.algorithm.dijkstra.DijkstraProvider;

public class AlgorithmChooser extends JPanel{

	private JComboBox comboBox;
	private JButton btnCreateAlg;

	/**
	 * Create the panel.
	 */
	public AlgorithmChooser() {
		setLayout(new BorderLayout(0, 0));
		
		JLabel lblAlg = new JLabel("Algorithm");
		add(lblAlg, BorderLayout.WEST);
		Object[] array = AlgorithmVisualtorProviderCollector.getProvider().toArray();
		comboBox = new JComboBox(array);
		add(comboBox, BorderLayout.CENTER);
		
		btnCreateAlg = new JButton("Create Algorithmview");
		add(btnCreateAlg, BorderLayout.SOUTH);

	}
	public void addActionListener(ActionListener listener)
	{
		btnCreateAlg.addActionListener(listener);
	}
	public AlgorithmVisualatorProvider getChoose()
	{
		return (AlgorithmVisualatorProvider) comboBox.getSelectedItem();
	}
}
