package de.bwvaachen.graph.logic;

public class Node implements INode {
	private String name;
	public Node() {
		// TODO Auto-generated constructor stub
	}
	public Node(String name) {
		this.name=name;
	}
	
	public Node(INode node) {
		this.name=new String(node.getName());
	}

	/* (non-Javadoc)
	 * @see de.bwvaachen.graph.logic.INode#getName()
	 */
	@Override
	public String getName() {
		return name;
	}
	/* (non-Javadoc)
	 * @see de.bwvaachen.graph.logic.INode#toString()
	 */
	@Override
	public String toString() {
		return getName();
	}
	/* (non-Javadoc)
	 * @see de.bwvaachen.graph.logic.INode#hashCode()
	 */
	@Override
	public int hashCode() {
		return name.hashCode();
	}
	/* (non-Javadoc)
	 * @see de.bwvaachen.graph.logic.INode#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj==null)
			return false;
		return this.toString().equals(obj.toString());
	}
}
