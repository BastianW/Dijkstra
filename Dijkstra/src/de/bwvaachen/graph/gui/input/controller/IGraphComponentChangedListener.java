package de.bwvaachen.graph.gui.input.controller;

import de.bwvaachen.graph.logic.Connection;
import de.bwvaachen.graph.logic.Graph;
import de.bwvaachen.graph.logic.Node;
import de.bwvaachen.graph.logic.Path;

public interface IGraphComponentChangedListener extends IGraphChangedListener {
	
	public void nodeAdded(Node node);
	public void nodeRemoved(Node node);
	public void connectionAdded(Connection connection);
	public void connectionRemoved(Connection connection);
	public void pathAdded(Path path);
	public void pathRemoved(Path path);
}


