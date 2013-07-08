package de.bwvaachen.graph.gui.input.visualgraph;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;

public class VisualGraphProperties {


	private int scaleFactor=300;
	private int connectionWeight=1;
	private int pathWeight=3;
	private Color connectionColor;
	private Color pathColor;
	private Color lblColor;
	private boolean lblOpaque=true;
	private boolean backgroundImageIsShown=false;
	private boolean scaleFactorIsUsed;
	private Image backgroundImage=null;
	private Dimension size;
	
	public VisualGraphProperties() {
	}
		public VisualGraphProperties(int scaleFactor, int connectionWeight,
			int pathWeight, Color connectionColor, Color pathColor,
			Color lblColor, boolean lblOpaque, boolean backgroundImageIsShown,
			boolean scaleFactorIsUsed, Image backgroundImage, Dimension size) {
		this.scaleFactor = scaleFactor;
		this.connectionWeight = connectionWeight;
		this.pathWeight = pathWeight;
		this.connectionColor = connectionColor;
		this.pathColor = pathColor;
		this.lblColor = lblColor;
		this.lblOpaque = lblOpaque;
		this.backgroundImageIsShown = backgroundImageIsShown;
		this.scaleFactorIsUsed = scaleFactorIsUsed;
		this.backgroundImage = backgroundImage;
		this.size = size;
	}
		public int getScaleFactor() {
			return scaleFactor;
		}
		public void setScaleFactor(int scaleFactor) {
			this.scaleFactor = scaleFactor;
		}
		public int getConnectionWeight() {
			return connectionWeight;
		}
		public void setConnectionWeight(int connectionWeight) {
			this.connectionWeight = connectionWeight;
		}
		public int getPathWeight() {
			return pathWeight;
		}
		public void setPathWeight(int pathWeight) {
			this.pathWeight = pathWeight;
		}
		public Color getConnectionColor() {
			return connectionColor;
		}
		public void setConnectionColor(Color connectionColor) {
			this.connectionColor = connectionColor;
		}
		public Color getPathColor() {
			return pathColor;
		}
		public void setPathColor(Color pathColor) {
			this.pathColor = pathColor;
		}
		public Color getLblColor() {
			return lblColor;
		}
		public void setLblColor(Color lblColor) {
			this.lblColor = lblColor;
		}
		public boolean isLblOpaque() {
			return lblOpaque;
		}
		public void setLblOpaque(boolean lblOpaque) {
			this.lblOpaque = lblOpaque;
		}
		public boolean isBackgroundImageIsShown() {
			return backgroundImageIsShown;
		}
		public void setBackgroundImageIsShown(boolean backgroundImageIsShown) {
			this.backgroundImageIsShown = backgroundImageIsShown;
		}
		public boolean isScaleFactorIsUsed() {
			return scaleFactorIsUsed;
		}
		public void setScaleFactorIsUsed(boolean scaleFactorIsUsed) {
			this.scaleFactorIsUsed = scaleFactorIsUsed;
		}
		public Image getBackgroundImage() {
			return backgroundImage;
		}
		public void setBackgroundImage(Image backgroundImage) {
			this.backgroundImage = backgroundImage;
		}
		public Dimension getSize() {
			return size;
		}
		public void setSize(Dimension size) {
			this.size = size;
		}
}
