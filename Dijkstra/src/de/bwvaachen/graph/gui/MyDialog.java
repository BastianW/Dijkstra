package de.bwvaachen.graph.gui;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;

import javax.swing.JDialog;

public class MyDialog extends JDialog {
	public void adjust()
	{
		PointerInfo info = MouseInfo.getPointerInfo();
	    Point location = info.getLocation();
	    setLocation(location.x-getWidth()/2,location.y-getHeight()/2);
	}
	

}
