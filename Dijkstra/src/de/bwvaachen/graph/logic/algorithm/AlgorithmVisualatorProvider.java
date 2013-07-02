package de.bwvaachen.graph.logic.algorithm;

import javax.swing.JPanel;

import de.bwvaachen.graph.gui.input.visualgraph.VisualGraphContainer;
import de.bwvaachen.graph.logic.Graph;

public abstract class AlgorithmVisualatorProvider {
//	public AlgorithmVisualatorProvider() {
//		AlgorithmVisualtorProviderCollector.addAlgorithmVisualatorProvider(this);
//	}
	public abstract JPanel createVisualation(Graph graph);
	public abstract String getName();
	public abstract JPanel createVisualation(VisualGraphContainer graphContainer);
	@Override
	public String toString() {
		return getName();
	}
}
