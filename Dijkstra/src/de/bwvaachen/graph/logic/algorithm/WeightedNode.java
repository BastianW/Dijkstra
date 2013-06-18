package de.bwvaachen.graph.logic.algorithm;

import de.bwvaachen.graph.logic.Node;

public class WeightedNode extends Node implements Comparable<WeightedNode>{

	
	private Number weight;

	public WeightedNode(Node node, Number weight) {
		super(node);
		this.weight=weight;
	}

	public Number getWeight() {
		return weight;
	}

	public void setWeight(Number weight) {
		this.weight = weight;
	}

	@Override
	public int compareTo(WeightedNode o) {
		if(o==null)
			return -1;
		if(o instanceof WeightedNode)
		{
			if(getWeight().doubleValue()>o.getWeight().doubleValue())
				return 1;
			if(getWeight().doubleValue()<o.getWeight().doubleValue())
				return -1;
		}
		return 0;
	}
	
	

}
