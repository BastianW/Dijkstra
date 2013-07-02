package de.bwvaachen.graph.logic.algorithm.dijkstra;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import javax.swing.JSplitPane;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.ImageIcon;

import de.bwvaachen.graph.gui.input.VisualGraph;
import de.bwvaachen.graph.gui.input.visualgraph.NodeDisplayProvider;
import de.bwvaachen.graph.gui.input.visualgraph.VisualGraphContainer;
import de.bwvaachen.graph.gui.input.visualgraph.VisualNode;
import de.bwvaachen.graph.logic.Graph;
import de.bwvaachen.graph.logic.Node;

import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

public class DijkstraVisualisation extends JPanel {

	private static final String NEXT_PNG = "icons\\next.png";
	private static final String PREVIOUS_PNG = "icons\\previous.png";
	private VisualGraph visualGraph;
	Dijkstra dijkstra;
	private JProgressBar progressBar;
	/**
	 * Create the panel.
	 */
	public DijkstraVisualisation(Graph graph) {
		Set<Node> nodes = graph.getNodes();
		Node startNode=null;
		if(!nodes.isEmpty())
		{
			StartNodeChooser startNodeChooser=new StartNodeChooser(nodes);
			startNodeChooser.setVisible(true);
			startNode=startNodeChooser.getNode();
			if(startNode==null)
				throw new IllegalArgumentException();
		}
		init(graph,startNode);
	}
	public DijkstraVisualisation(VisualGraphContainer graphContainer) {
		Set<Node> nodes = graphContainer.getGraph().getNodes();
		Node startNode=null;
		if(!nodes.isEmpty())
		{
			StartNodeChooser startNodeChooser=new StartNodeChooser(nodes);
			startNodeChooser.setVisible(true);
			startNode=startNodeChooser.getNode();
			if(startNode==null)
				throw new IllegalArgumentException();
		}
		init(graphContainer,startNode);
	}
	public DijkstraVisualisation(Graph graph, Node startNode) {
		init(graph, startNode);
	}
	
	private void init(Object graphContainer, Node startNode) {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane);
		
		JPanel panel = new JPanel();
		scrollPane.setViewportView(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		if(graphContainer instanceof VisualGraphContainer)
			visualGraph=new VisualGraph((VisualGraphContainer)graphContainer,false);
		else
			visualGraph=new VisualGraph((Graph) graphContainer,false);
		visualGraph.setNodeDisplayProvider(new NodeDisplayProvider() {
			
			@Override
			public String label(String node, Number weight) {
				return node;
			}
			@Override
			public Point decorationAnchor(VisualNode node) {
				return new Point (node.getWidth()-30,node.getHeight()/2);
			}
			@Override
			public String decorate(String node, Number weight) {
				if(weight==null)
					return Character.toString('\u221E');
				return weight.toString();
			}
		});

		
		DijkstraVisualisationLogger logger=new DijkstraVisualisationLogger();
		if(graphContainer instanceof VisualGraphContainer)
		dijkstra=new Dijkstra(((VisualGraphContainer) graphContainer).getGraph(), startNode);
		else
			dijkstra=new Dijkstra((Graph) graphContainer, startNode);
		dijkstra.setLogger(logger);
		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.7);
		splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		splitPane.setLeftComponent(visualGraph);
		splitPane.setRightComponent(logger);
		panel.add(splitPane, BorderLayout.CENTER);
		JPanel control_Panel = new JPanel();
		panel.add(control_Panel, BorderLayout.SOUTH);
		
		JButton btn_Previous = new JButton("");
		btn_Previous.setIcon(new ImageIcon(PREVIOUS_PNG));
		btn_Previous.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dijkstra.stepBackward();
				Graph g=dijkstra.getCurrentGraph();
				visualGraph.graphChanged(g);
				progressBar.setValue(dijkstra.getCurrentSteps());
				progressBar.setString(dijkstra.getCurrentSteps()+"/"+dijkstra.getMaxSteps());
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
				progressBar.setValue(dijkstra.getCurrentSteps());
				progressBar.setString(dijkstra.getCurrentSteps()+"/"+dijkstra.getMaxSteps());
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
				progressBar.setValue(dijkstra.getCurrentSteps());
				progressBar.setString(dijkstra.getCurrentSteps()+"/"+dijkstra.getMaxSteps());
			}
		});
		control_Panel.add(btnRunThrough);
		
		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		progressBar.setMaximum(dijkstra.getMaxSteps());
		progressBar.setString(dijkstra.getCurrentSteps()+"/"+dijkstra.getMaxSteps());
		panel.add(progressBar, BorderLayout.NORTH);
	}


}
