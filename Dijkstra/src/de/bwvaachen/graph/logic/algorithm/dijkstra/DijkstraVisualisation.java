package de.bwvaachen.graph.logic.algorithm.dijkstra;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import javax.swing.JSlider;
import javax.swing.JSplitPane;

import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class DijkstraVisualisation extends JPanel {

	private static final String NEXT_PNG = "icons\\next.png";
	private static final String PREVIOUS_PNG = "icons\\previous.png";
	private VisualGraph visualGraph;
	Dijkstra dijkstra;
	private JProgressBar progressBar;
	private JSlider slider;
	/**
	 * Create the panel.
	 * @wbp.parser.constructor
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
		setLayout(new BorderLayout());
		
//		JScrollPane scrollPane = new JScrollPane();
//		add(scrollPane,BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
//		scrollPane.setViewportView(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		add(panel,BorderLayout.CENTER);
		if(graphContainer instanceof VisualGraphContainer)
		{
			visualGraph=new VisualGraph((VisualGraphContainer)graphContainer,false);
		}
		else
		{
			visualGraph=new VisualGraph((Graph) graphContainer,false);
		}
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
			@Override
			public String getTooltipText(VisualNode node) {
				return node.getName();
			}
			@Override
			public boolean opaque() {
				return visualGraph.getProperties().isLblOpaque();
			}
			@Override
			public Color labelColor() {
				return visualGraph.getProperties().getLblColor();
			}
		});

		
		DijkstraVisualisationLogger logger=new DijkstraVisualisationLogger();
		if(graphContainer instanceof VisualGraphContainer)
		dijkstra=new Dijkstra(((VisualGraphContainer) graphContainer).getGraph(), startNode);
		else
			dijkstra=new Dijkstra((Graph) graphContainer, startNode);
		dijkstra.setLogger(logger);
		
		JPanel visualGraphPanel=new JPanel();
		visualGraphPanel.setLayout(new BorderLayout());
		visualGraphPanel.add(visualGraph,BorderLayout.CENTER);

		slider = new JSlider(0,100);
		slider.setValue(10);
		slider.setOrientation(JSlider.VERTICAL);
		slider.setMajorTickSpacing( 10 );
		slider.setMinorTickSpacing( 1 );
		slider.setBorder( BorderFactory.createTitledBorder("Scale"));
		slider.setPaintTicks( true );
	    slider.setPaintLabels( true );
	    slider.setSnapToTicks( true );
	    slider.addChangeListener( new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				visualGraph.setScaleFactor(slider.getValue()/10.0);
				visualGraph.update();
			}
		});
		visualGraphPanel.add(slider,BorderLayout.WEST);
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.7);
		splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		splitPane.setLeftComponent(visualGraphPanel);
		splitPane.setRightComponent(logger);
		panel.add(splitPane, BorderLayout.CENTER);
		JPanel control_Panel = new JPanel();
		add(control_Panel, BorderLayout.SOUTH);
		
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
