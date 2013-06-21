package de.bwvaachen.graph.gui.input.nodesview;

import java.awt.Component;
import java.io.File;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

import de.bwvaachen.graph.logic.Node;

public class IconTreeRenderer extends DefaultTreeCellRenderer {
	  private static final Icon pathIcon = new ImageIcon( "icons"+File.separator+"path.jpg" );
	  private static final Icon connectionsIcon = new ImageIcon( "icons"+File.separator+"connection.png" );
	  private static final Icon nodeIcon = new ImageIcon( "icons"+File.separator+"node.jpg" );
	  
	  public IconTreeRenderer() 
	  {
	    
	    super();
	  }
	  
	  
	  public Component getTreeCellRendererComponent( JTree tree, Object value,
	                                                 boolean sel,
	                                                 boolean expanded,
	                                                 boolean leaf, int row,
	                                                 boolean hasFocus ) {
	    String stringValue = tree.convertValueToText( value, sel,
	                                                  expanded, leaf, row, hasFocus );
	    
	    if (value instanceof Node) //Sollte immer erfuellt sein
	     setIcon(nodeIcon);
	    else if(value instanceof ConnectionModel)
	    	setIcon(nodeIcon);
	    else if(value instanceof ConnectionsModel)
	    	setIcon(connectionsIcon);
	    else if(value instanceof PathsModel)
	    	setIcon(pathIcon);
	    else if(value instanceof PathModel)
	    	setIcon(pathIcon);
	    else 
	    	setIcon(nodeIcon);
	    setText( stringValue );
	    if ( sel )
	      setForeground( getTextSelectionColor() );
	    else
	      setForeground( getTextNonSelectionColor() );	 
	    selected = sel;
	    return this;
	  }
	 
	}