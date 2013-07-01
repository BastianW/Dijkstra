package de.bwvaachen.graph.logic.algorithm;

import javax.swing.JComponent;
import javax.swing.JPanel;

import de.bwvaachen.graph.logic.Graph;

public abstract class AlgorithmVisualatorProvider {
//	public AlgorithmVisualatorProvider() {
//		AlgorithmVisualtorProviderCollector.addAlgorithmVisualatorProvider(this);
//	}
	public abstract JPanel createVisualation(Graph graph);
	public abstract String getName();

	@Override
	public String toString() {
		return getName();
	}
}
