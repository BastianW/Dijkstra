package de.bwvaachen.graph.gui.input;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import de.bwvaachen.graph.logic.Connection;
import de.bwvaachen.graph.logic.Edge;
import de.bwvaachen.graph.logic.Graph;
import de.bwvaachen.graph.logic.Node;
import de.bwvaachen.graph.logic.Path;

public class AdjazenzmatrixView extends JPanel {

	static final Color DEFAULTCOLOR_JTEXTFIELD = UIManager
			.getColor("TextField.background");
	static final Color IVALID_INPUT = new Color(1.f, 0f, 0f, 0.5f);
	JTextField[] nodeNames;
	JTextField[][] nodeWeigths;
	JLabel[] labels;
	private WeightMode mode;

	/**
	 * Create the panel.
	 */
	public AdjazenzmatrixView(int countNodes, WeightMode mode) {
		this.mode = mode;

		setLayout(new GridLayout(0, countNodes, 10, 5));
		nodeNames = new JTextField[countNodes];
		int countWeights = countNodes - 1;

		labels = new JLabel[countWeights - 1];
		nodeWeigths = new JTextField[countWeights][];
		for (int i = 0; i < countWeights; i++) {
			nodeWeigths[i] = new JTextField[countWeights - i];
		}

		add(new JLabel());
		for (int i = 1; i < countNodes; i++) {
			JTextField textField = createNodeNameInput(i);
			nodeNames[i] = textField;
			add(textField);
		}
		for (int i = 0; i < countWeights; i++) {
			if (i == 0) {
				JTextField textField = createNodeNameInput(i);
				nodeNames[i] = textField;
				add(textField);
			} else {
				JLabel label = new JLabel();
				labels[i - 1] = label;
				add(label);
			}

			for (int emptyFieldsCounter = 0; emptyFieldsCounter < countWeights
					- nodeWeigths[i].length; emptyFieldsCounter++) {
				add(new JLabel());
			}
			for (int inputFieldIndex = 0; inputFieldIndex < nodeWeigths[i].length; inputFieldIndex++) {
				JTextField nodeWeightInput = createNodeWeightInput();
				nodeWeigths[i][inputFieldIndex]=nodeWeightInput;
				add(nodeWeightInput);
			}
		}

		System.out.println();
	}

	private JTextField createNodeWeightInput() {
		JTextField textField = new JTextField(""+(int)(Math.random()*10));
		textField.setHorizontalAlignment(JTextField.CENTER);
		NodeWeightListener weightListener = new NodeWeightListener();
		textField.setInputVerifier(weightListener);
		textField.addKeyListener(weightListener);
		return textField;
	}

	private JTextField createNodeNameInput(int index) {
		final JTextField textField = new JTextField(""+(char)(65+index));
		textField.setHorizontalAlignment(JTextField.CENTER);
		NodeNameListener nodeNameListener = new NodeNameListener(index);

		textField.setInputVerifier(nodeNameListener);
		textField.addFocusListener(nodeNameListener);
		textField.addKeyListener(nodeNameListener);
		return textField;
	}

	private Set<Connection> getSortedConnectionSet(Node[] nodes) 
	{
		Set<Connection> resultSet = new TreeSet<Connection>();

		

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
	private Node[] getNodes()
	{
		Node[] nodes = new Node[nodeNames.length];
		for (int i = 0; i < nodes.length; i++) {
			nodes[i] = new Node(nodeNames[i].getText().trim());
		}
		return nodes;
	}
	public Graph getGraph()throws Exception// TODO
	{
		if (!checkNames())
			throw new Exception();
		Node[] nodes = getNodes();

		Set<Connection> sortedConnectionSet = getSortedConnectionSet(nodes);
		Set<Node>nodeSet=new HashSet<Node>();
		for(Node n: nodes)
		{
			nodeSet.add(n);
		}
		Set<Path> paths=new HashSet<Path>();
		
		Graph graph=new Graph(nodeSet,sortedConnectionSet, paths);
		return graph;//TODO
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

		int index;
		boolean wasWrong = false;

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
			if (index - 1 >= 0 && index-1<labels.length) {
				String text = ((JTextField) e.getComponent()).getText();
				labels[index - 1].setText(text);
			}
		}

		@Override
		public void keyTyped(KeyEvent e) {
			if (wasWrong) {
				JTextField textfield = (JTextField) e.getComponent();
				textfield.setBackground(DEFAULTCOLOR_JTEXTFIELD);
			}

		}

		@Override
		public void keyPressed(KeyEvent e) {
		}

		@Override
		public void keyReleased(KeyEvent e) {
		}

		@Override
		public void focusGained(FocusEvent e) {
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

	class NodeWeightListener extends InputVerifier implements KeyListener {

		boolean wasWrong = false;

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
			if (wasWrong) {
				wasWrong = false;
				JTextField textfield = (JTextField) e.getComponent();
				textfield.setBackground(DEFAULTCOLOR_JTEXTFIELD);
			}

		}

		@Override
		public void keyPressed(KeyEvent e) {
		}

		@Override
		public void keyReleased(KeyEvent e) {
		}
	}
}
