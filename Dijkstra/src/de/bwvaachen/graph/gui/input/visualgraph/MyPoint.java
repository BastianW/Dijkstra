package de.bwvaachen.graph.gui.input.visualgraph;

import java.awt.Point;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class MyPoint
		{
			public MyPoint() {
				x=0;
				y=0;
				
			}
			public MyPoint(int x, int y) {
				this.x = x;
				this.y = y;
			}
			public MyPoint(Point p) {
				this.x = p.x;
				this.y = p.y;
			}
			private int x;
			private int y;
			public int getX() {
				return x;
			}
			public void setX(int x) {
				this.x = x;
			}
			public int getY() {
				return y;
			}
			public void setY(int y) {
				this.y = y;
			}
			@JsonIgnore
			public Point getPoint()
			{
				return new Point(x,y);
			}
		}