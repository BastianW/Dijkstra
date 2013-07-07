package de.bwvaachen.graph.logic.algorithm;

import java.util.HashSet;
import java.util.Set;

import de.bwvaachen.graph.logic.algorithm.dijkstra.DijkstraProvider;

public class AlgorithmVisualatorProviderCollector {
	private static Set<AlgorithmVisualatorProvider> provider=new HashSet<AlgorithmVisualatorProvider>();
	static
	{
		provider.add(new DijkstraProvider());
	}
	public static Set<AlgorithmVisualatorProvider> getProvider()
	{
		return provider;
	}
	public static void addAlgorithmVisualatorProvider(AlgorithmVisualatorProvider provider)
	{
		AlgorithmVisualatorProviderCollector.provider.add(provider);
	}
	

}
