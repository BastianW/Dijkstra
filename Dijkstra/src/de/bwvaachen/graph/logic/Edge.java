package de.bwvaachen.graph.logic;

public class Edge {

	private Number weight;
	private boolean isMarked;
	
	public Number getWeight() {
		return weight;
	}


	public void setWeight(Number weight) {
		if(weight==null || weight.doubleValue()<0)
			throw new IllegalArgumentException();
		this.weight = weight;
	}

	public Edge(Number weight) {
		setWeight(weight);
	}

	
	public Edge(Edge edge) {
		if(edge.getWeight() instanceof Integer)
		weight=new Integer(edge.getWeight().intValue());
		else if(edge.getWeight() instanceof Double)
		weight=new Double(edge.getWeight().doubleValue());
		else
			throw new IllegalArgumentException();
	}


	public boolean isMarked() {
		return isMarked;
	}
	
	public void setMarked(boolean isMarked) {
		this.isMarked = isMarked;
	}

	
}
