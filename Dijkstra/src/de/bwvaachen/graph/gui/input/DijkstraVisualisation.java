package de.bwvaachen.graph.gui.input;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.ImageIcon;

import de.bwvaachen.graph.logic.Graph;
import de.bwvaachen.graph.logic.Node;
import de.bwvaachen.graph.logic.algorithm.Dijkstra;

public class DijkstraVisualisation extends JPanel {

	private static final String NEXT_PNG = "E:\\Programme\\GitStack\\repositories\\git\\Dijkstra\\Dijkstra\\icons\\next.png";
	private static final String PREVIOUS_PNG = "E:\\Programme\\GitStack\\repositories\\git\\Dijkstra\\Dijkstra\\icons\\previous.png";
	private VisualGraph visualGraph;
	Dijkstra dijkstra;
	/**
	 * Create the panel.
	 */
	public DijkstraVisualisation(Graph graph, Node startNode) {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane);
		
		JPanel panel = new JPanel();
		scrollPane.setViewportView(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		visualGraph=new VisualGraph(graph);
		dijkstra=new Dijkstra(graph, startNode);
		panel.add(visualGraph,BorderLayout.CENTER);
		
		JPanel control_Panel = new JPanel();
		panel.add(control_Panel, BorderLayout.SOUTH);
		
		JButton btn_Previous = new JButton("");
		btn_Previous.setIcon(new ImageIcon(PREVIOUS_PNG));
		btn_Previous.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dijkstra.stepForward();
				Graph g=dijkstra.getCurrentGraph();
				visualGraph.graphChanged(g);
			}
		});
		control_Panel.add(btn_Previous);
		
		JButton btn_Next = new JButton("");
		btn_Next.setIcon(new ImageIcon(NEXT_PNG));
		btn_Next.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dijkstra.stepForward();
				Graph g=dijkstra.getCurrentGraph();
				visualGraph.graphChanged(g);
			}
		});
		control_Panel.add(btn_Next);
		
		JButton btnRunThrough = new JButton("Run through");
		btnRunThrough.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dijkstra.doDijkstra();
				Graph g=dijkstra.getCurrentGraph();
				visualGraph.graphChanged(g);
			}
		});
		control_Panel.add(btnRunThrough);
	}

}
