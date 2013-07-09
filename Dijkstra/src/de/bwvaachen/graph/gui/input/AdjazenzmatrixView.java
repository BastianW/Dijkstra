package de.bwvaachen.graph.gui.input;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;

import de.bwvaachen.graph.gui.input.controller.IGraphChangedListener;
import de.bwvaachen.graph.gui.input.controller.IGraphComponentChangedListener;
import de.bwvaachen.graph.logic.Connection;
import de.bwvaachen.graph.logic.Edge;
import de.bwvaachen.graph.logic.Graph;
import de.bwvaachen.graph.logic.Node;
import de.bwvaachen.graph.logic.Path;
import de.bwvaachen.graph.logic.algorithm.WeightedNode;

public class AdjazenzmatrixView extends JPanel implements IGraphChangedListener {

	private static final Color GRID_COLOR = new Color(15717835);
	static final Color DEFAULTCOLOR_JTEXTFIELD = UIManager
			.getColor("TextField.background");
	static final Color IVALID_INPUT = new Color(1.f, 0f, 0f, 0.5f);
	private JTextField[] nodeNames;
	private JTextField[][] nodeWeigths;
	private JLabel[] labels;
	private WeightMode mode;
	private Integer focusX = null;
	private Integer focusY = null;
	boolean shouldCommit=false;
	private HashSet<IGraphComponentChangedListener> graphComponentListener = new HashSet<IGraphComponentChangedListener>();

	/**
	 * Create the panel.
	 */
	public AdjazenzmatrixView(int countNodes, WeightMode mode) {
		Set<Node> nodes = new HashSet<Node>();
		for (int i = 0; i < countNodes; i++) {
			nodes.add(new Node("" + (char) (65 + i)));
		}

		List<Connection> connections = new LinkedList<Connection>();
		List<Path> paths = new LinkedList<Path>();
		Graph graph = new Graph(nodes, connections, paths);
		init(graph, mode);
	}

	public AdjazenzmatrixView(Graph graph, WeightMode mode) {
		init(graph, mode);
	}

	private void init(Graph graph, WeightMode mode) {
		ArrayList<Node> nodes = new ArrayList<Node>(graph.getNodes());
		Collections.sort(nodes, new Comparator<Node>() {
			@Override
			public int compare(Node o1, Node o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});
		LinkedList<Connection> connections = new LinkedList<Connection>(
				graph.getSortedConnections());
		int countNodes = nodes.size();
		setLayout(new BorderLayout(0, 0));
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane);
		JPanel panel = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				if (nodeNames != null && nodeNames.length > 0) {
					Graphics2D g2d = (Graphics2D) g;
					Stroke stroke = g2d.getStroke();
					g2d.setStroke(new BasicStroke(3));
					Rectangle rec = nodeNames[0].getBounds();
					g.drawLine(rec.width + 3, 0, rec.width + 3,
							this.getHeight());
					g.drawLine(0, rec.height + 2, this.getWidth(),
							rec.height + 2);
					g2d.setStroke(stroke);
				}
			}
		};

		this.mode = mode;

		panel.setLayout(new GridLayout(0, countNodes, 10, 5));
		nodeNames = new JTextField[countNodes];
		int countWeights = countNodes - 1;

		labels = new JLabel[countWeights - 1];

		nodeWeigths = new JTextField[countWeights][];
		for (int i = 0; i < countWeights; i++) {
			nodeWeigths[i] = new JTextField[countWeights - i];
		}

		panel.add(new JLabel());
		for (int i = 1; i < countNodes; i++) {
			JTextField textField = createNodeNameInput(i);
			nodeNames[i] = textField;
			nodeNames[i].setText(nodes.get(i).toString());
			panel.add(textField);
		}
		for (int i = 0; i < countWeights; i++) {
			if (i == 0) {
				JTextField textField = createNodeNameInput(i);
				textField.setBackground(GRID_COLOR);
				nodeNames[i] = textField;
				nodeNames[i].setText(nodes.get(i).toString());
				panel.add(textField);
			} else {
				JLabel label = new JLabel();
				label.setOpaque(true);
				if(i%2==0)
					label.setBackground(GRID_COLOR);
				labels[i - 1] = label;
				labels[i - 1].setText(nodeNames[i].getText());
				panel.add(label);
			}

			for (int emptyFieldsCounter = 0; emptyFieldsCounter < countWeights
					- nodeWeigths[i].length; emptyFieldsCounter++) {
				panel.add(new JLabel());
			}
			for (int inputFieldIndex = 0; inputFieldIndex < nodeWeigths[i].length; inputFieldIndex++) {
				JTextField nodeWeightInput = createNodeWeightInput(i,inputFieldIndex);
				nodeWeigths[i][inputFieldIndex] = nodeWeightInput;
				panel.add(nodeWeightInput);
			}
		}
		scrollPane.setViewportView(panel);
		while (!connections.isEmpty()) {
			Connection connection = connections.removeFirst();

			int rawIndex1 = nodes.indexOf(connection.getEndNode());
			int rawIndex2 = nodes.indexOf(connection.getStartNode());
			int index1 = rawIndex1 < rawIndex2 ? rawIndex1 : rawIndex2;
			int index2 = (rawIndex1 > rawIndex2 ? rawIndex1 : rawIndex2)
					- index1 - 1;
			nodeWeigths[index1][index2].setText(connection.getEdge()
					.getWeight().toString());
		}
		doLayout();
	}

	@Override
	public void graphChanged(Graph graph) {
		removeAll();
		init(graph, mode);
		if(focusX!=null)
		{
			if(focusY==null)
			nodeNames[focusX].requestFocus();
			else
			nodeWeigths[focusX][focusY].requestFocus();

		}
	}

	public void commitChange() {
		Graph graph = null;
		try {
			graph = getGraph();
			for (IGraphComponentChangedListener listener : graphComponentListener) {
				listener.graphChanged(graph);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void addGraphComponentChangedListener(
			IGraphComponentChangedListener listener) {
		graphComponentListener.add(listener);
	}

	public void removeGraphComponentChangedListener(
			IGraphComponentChangedListener listener) {
		graphComponentListener.remove(listener);
	}

	private JTextField createNodeWeightInput(int indexX, int indexY) {
		JTextField textField = new JTextField();
		textField.setHorizontalAlignment(JTextField.CENTER);
		NodeWeightListener weightListener = new NodeWeightListener(indexX,indexY);
		textField.setInputVerifier(weightListener);
		textField.addKeyListener(weightListener);
		textField.addFocusListener(weightListener);
		if(indexX%2==0)
		{
			textField.setBackground(GRID_COLOR);
		}
		return textField;
	}

	private JTextField createNodeNameInput(int index) {
		final JTextField textField = new JTextField();
		textField.setHorizontalAlignment(JTextField.CENTER);
		NodeNameListener nodeNameListener = new NodeNameListener(index);

		textField.setInputVerifier(nodeNameListener);
		textField.addFocusListener(nodeNameListener);
		textField.addKeyListener(nodeNameListener);
		return textField;
	}

	private List<Connection> getSortedConnectionList(Node[] nodes) {
		List<Connection> resultSet = new LinkedList<Connection>();
		for (int i = 0; i < nodes.length - 1; i++) {
			for (int j = 0; j < nodeWeigths[i].length; j++) {
				Number number = transformToNumber(nodeWeigths[i][j]);
				if (number != null) {
					Edge edge = new Edge(number);
					Node node1 = nodes[i];
					Node node2 = nodes[i + j + 1];
					Connection connection = new Connection(node1, node2, edge);
					resultSet.add(connection);
				}
			}
		}

		return resultSet;
	}

	private Node[] getNodes() {
		Node[] nodes = new Node[nodeNames.length];
		for (int i = 0; i < nodes.length; i++) {
			nodes[i] = new Node(nodeNames[i].getText().trim());
		}
		return nodes;
	}

	public Graph getGraph() throws Exception// TODO Exception should be specific
	{
		if (!checkNames())
			throw new Exception();
		Node[] nodes = getNodes();

		List<Connection> sortedConnectionSet = getSortedConnectionList(nodes);
		Set<Node> nodeSet = new HashSet<Node>();
		for (Node n : nodes) {
			nodeSet.add(n);
		}
		Set<Path> paths = new HashSet<Path>();

		Graph graph = new Graph(nodeSet, sortedConnectionSet, paths);
		return graph;// TODO
	}

	private Number transformToNumber(JTextField field) {
		String text = field.getText().trim();
		Number number = null;
		switch (mode) {
		case DOUBLE_NULL_ALLOWED_MODE:
		case DOUBLE_MODE:
			try {
				number = Double.parseDouble(text);
			} catch (Exception e) {
			}
			break;
		case INTEGER_NULL_ALLOWED_MODE:
		case INTEGER_MODE:
			try {
				number = Integer.parseInt(text);
			} catch (Exception e) {
			}
			break;
		}
		return number;
	}

	private boolean checkNames() {
		HashSet<String> testSet = new HashSet<String>();
		for (JTextField field : nodeNames) {
			String id = field.getText().trim();
			if (testSet.contains(id))
				return false;
			testSet.add(id);
		}
		return true;
	}

	class NodeNameListener extends InputVerifier implements KeyListener,
			FocusListener {

		private int index;
		private boolean wasWrong = false;
		private String lastCommit;

		public NodeNameListener(int index) {
			this.index = index;
		}

		@Override
		public boolean verify(JComponent input) {
			JTextField textfield = ((JTextField) input);
			String currentText = textfield.getText().trim();
			if (currentText.isEmpty()) {
				textfield.setBackground(IVALID_INPUT);
				wasWrong = true;
				return false;
			}
			for (int i = 0; i < nodeNames.length; i++) {
				if (i == index)
					continue;
				String tmpText = nodeNames[i].getText().trim();
				if (currentText.equals(tmpText)) {
					textfield.setBackground(IVALID_INPUT);
					wasWrong = true;
					return false;
				}
			}
			wasWrong = false;
			return true;
		}

		@Override
		public void focusLost(FocusEvent e) {
			JTextField textfield = (JTextField) e.getComponent();
			if (index - 1 >= 0 && index - 1 < labels.length) {
				String text = textfield.getText();
				labels[index - 1].setText(text);
			}
			if (lastCommit != null && !lastCommit.equals(textfield.getText())) {
				lastCommit = null;
				shouldCommit=true;
			}
		}

		@Override
		public void keyTyped(KeyEvent e) {
			JTextField textfield = (JTextField) e.getComponent();
			if (wasWrong) {
				textfield.setBackground(DEFAULTCOLOR_JTEXTFIELD);
			}
		}

		@Override
		public void keyPressed(KeyEvent e) {
			JTextField textfield = (JTextField) e.getComponent();
			if (lastCommit == null)
				lastCommit = textfield.getText();
		}

		@Override
		public void keyReleased(KeyEvent e) {
		}

		@Override
		public void focusGained(FocusEvent e) {
			if(shouldCommit)
			{
				shouldCommit=false;
			AdjazenzmatrixView.this.focusX=index;
			AdjazenzmatrixView.this.focusY=null;
			commitChange();
			}
		}

		// class BlinkActivity implements Runnable
		// {
		// JTextField blinkingTextField;
		// private boolean blink;
		// public BlinkActivity(JTextField blinkingTextField) {
		// this.blinkingTextField=blinkingTextField;
		// blink=true;
		// }
		// @Override
		// public void run() {
		// while(blink)
		// {
		// Color color = blinkingTextField.getBackground();
		// blinkingTextField.setBackground(Color.RED);
		// try {
		// Thread.sleep(150);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		// blinkingTextField.setBackground(color);
		// try {
		// Thread.sleep(500);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		// }
		// }
		//
		// public void stopBlinking()
		// {
		// blink=false;
		// }
		// }
	}

	class NodeWeightListener extends InputVerifier implements KeyListener,
			FocusListener {

		boolean wasWrong = false;
		private String lastCommit;
		private int indexX;
		private int indexY;

		public NodeWeightListener(int indexX, int indexY) {
			this.indexX = indexX;
			this.indexY = indexY;
		}

		@Override
		public boolean verify(JComponent input) {
			JTextField field = (JTextField) input;
			String currentText = field.getText().trim();
			boolean result = false;
			if (currentText.isEmpty()) {
				result = _verify(true, field);
			} else {
				switch (mode) {
				case DOUBLE_NULL_ALLOWED_MODE:
					result = _verify(verifyDouble(currentText, true), field);
					break;
				case INTEGER_NULL_ALLOWED_MODE:
					result = _verify(verifyInteger(currentText, 1), field);
					break;
				case DOUBLE_MODE:
					result = _verify(verifyDouble(currentText, false), field);
					break;
				case INTEGER_MODE:
					result = _verify(verifyInteger(currentText, 0), field);
					break;
				}
			}
			field.setText(currentText);

			return result;
		}

		private boolean _verify(boolean b, JTextField field) {
			wasWrong = !b;
			if (!b)
				field.setBackground(IVALID_INPUT);
			return b;
		}

		private boolean verifyInteger(String str, int nullAllowed) {
			int i = 0 - nullAllowed;
			try {
				i = Integer.parseInt(str);
			} catch (Exception e) {
				return false;
			}
			return i > 0;
		}

		private boolean verifyDouble(String str, boolean nullAllowed) {
			double d = 0;
			try {
				d = Double.parseDouble(str);
			} catch (Exception e) {
				return false;
			}
			if (nullAllowed)
				return d >= 0;
			else
				return d > 0;
		}

		@Override
		public void keyTyped(KeyEvent e) {
			JTextField textfield = (JTextField) e.getComponent();
			if (wasWrong) {
				wasWrong = false;
				textfield.setBackground(DEFAULTCOLOR_JTEXTFIELD);
			}

		}

		@Override
		public void keyPressed(KeyEvent e) {
			JTextField textfield = (JTextField) e.getComponent();
			if (lastCommit == null)
				lastCommit = textfield.getText();
		}

		@Override
		public void keyReleased(KeyEvent e) {
		}

		@Override
		public void focusGained(FocusEvent e) {
			if(shouldCommit)
			{
				shouldCommit=false;
			AdjazenzmatrixView.this.focusX=indexX;
			AdjazenzmatrixView.this.focusY=indexY;
			commitChange();
			}
		}

		@Override
		public void focusLost(FocusEvent e) {
			JTextField textfield = (JTextField) e.getComponent();
			if (lastCommit != null && !lastCommit.equals(textfield.getText())) {
				lastCommit = null;
			shouldCommit=true;
			}

		}
	}
}
