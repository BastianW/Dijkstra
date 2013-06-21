package de.bwvaachen.graph.logic;

public class Node {
	private String name;
	public Node() {
		// TODO Auto-generated constructor stub
	}
	public Node(String name) {
		this.name=name;
	}
	
	public Node(Node node) {
		this.name=new String(node.getName());
	}

	public String getName() {
		return name;
	}
	@Override
	public String toString() {
		return getName();
	}
	@Override
	public int hashCode() {
		return name.hashCode();
	}
	@Override
	public boolean equals(Object obj) {
		if(obj==null)
			return false;
		if(!(obj instanceof Node))
			return false;
		return this.toString().equals(obj.toString());
	}
}
