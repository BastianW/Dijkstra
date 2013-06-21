package de.bwvaachen.graph.gui.input.nodesview;

import java.util.List;

import de.bwvaachen.graph.logic.Connection;
import de.bwvaachen.graph.logic.Node;
import de.bwvaachen.graph.logic.Path;

public class PathModel implements INodesViewTreeModel{
		private Path path;
		private Node startNode;

		public PathModel(Node start, Path path) {
			path.sort(start);
			this.path=path;
			this.startNode=start;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			double weight = 0.0;
			if(path.isSorted(startNode))
			{
				path.sort(startNode);
			}
			List<Connection> connections= path.getConnections();
			Connection start = connections.get(0);
			builder.append(start.getStartNode() + "(" + getWeight(weight) + ")");
			for (Connection connection : connections) {
				weight += connection.getEdge().getWeight().doubleValue();
				builder.append("-" + connection.getEndNode() + "("
						+ getWeight(weight) + ")");
			}
			return builder.toString();
		}

		public String getWeight(double weight) {
			if (weight == (int) weight) {
				return new Integer((int) weight).toString();
			} else
				return weight + "";
		}

		@Override
		public Object getChild(int index) {
			return null;
		}

		@Override
		public int getChildCount() {
			return 0;
		}

		@Override
		public boolean isLeaf() {
			return true;
		}

		@Override
		public int getIndexOfChild(Object child) {
			return -1;
		}
	}