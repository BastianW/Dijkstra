package de.bwvaachen.graph.gui.input.visualgraph;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.GridLayout;
import javax.swing.JCheckBox;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.JComboBox;
import javax.swing.SpinnerModel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.prefs.BackingStoreException;

import javax.swing.SpinnerNumberModel;

public class VisualGraphPropertiesDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	
	private JButton applyButton;
	private JCheckBox chckbxScale;
	private JCheckBox chckbxBackgroundImage;
	private JButton backgroundImageBtn;
	private JCheckBox chckbxLabelOpaque;
	private JButton labelColorBtn;
	
	private JTextField txt_Path;
	private JTextField txt_ConnectionColor;
	private JTextField txt_PathColor;
	private JTextField txt_LabelColor;
	
	private JSpinner scale_spinner;
	private int apply_ScaleFactor;
	
	
	private JSpinner width_spinner;
	private JSpinner height_spinner;
	private Dimension apply_Dimension;
	
	private Color connectionColor;
	private Color apply_ConnectionColor;
	
	private JSpinner pathWeight_spinner;
	private int apply_PathWeigth;
	
	private Color pathColor;
	private Color apply_PathColor;
	
	private JSpinner connectionWeightSpinner;
	private int apply_ConnectionWeight;
	
	private Color labelColor;
	private Color apply_LabelColor;

	private boolean apply_ScaleFactorIsUsed;
	
	private Image apply_BackgroundImage;

	private boolean apply_LabelOpaque;
	



	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			VisualGraphPropertiesDialog dialog = new VisualGraphPropertiesDialog(new Dimension(800,800),1,Color.BLACK,3,Color.BLUE, true, Color.GREEN);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public VisualGraphPropertiesDialog(Dimension dimension,int connectionWeight, Color connectionColor, int pathWeight, Color pathColor, boolean lblOpaque, Color lblColor) {
		setTitle("Properties");
		
		this.connectionColor=connectionColor;
		this.pathColor=pathColor;
		this.labelColor=lblColor;
		
		setModalityType(ModalityType.APPLICATION_MODAL);
		setBounds(100, 100, 462, 381);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(0, 1, 0, 0));
		JPanel panel = new JPanel();
		contentPanel.add(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 87, 0, 89, 29, 82, 28, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);
		
		JLabel lblViewSize = new JLabel("View Size");
		GridBagConstraints gbc_lblViewSize = new GridBagConstraints();
		gbc_lblViewSize.anchor = GridBagConstraints.WEST;
		gbc_lblViewSize.insets = new Insets(0, 0, 5, 5);
		gbc_lblViewSize.gridx = 0;
		gbc_lblViewSize.gridy = 0;
		panel.add(lblViewSize, gbc_lblViewSize);
		
		JPanel size_panel = new JPanel();
		GridBagConstraints gbc_size_panel = new GridBagConstraints();
		gbc_size_panel.gridwidth = 3;
		gbc_size_panel.insets = new Insets(0, 0, 5, 5);
		gbc_size_panel.fill = GridBagConstraints.BOTH;
		gbc_size_panel.gridx = 2;
		gbc_size_panel.gridy = 0;
		panel.add(size_panel, gbc_size_panel);
		size_panel.setLayout(new GridLayout(0, 2, 0, 0));
		
		width_spinner = new JSpinner();
		size_panel.add(width_spinner);
		width_spinner.setModel(new SpinnerNumberModel(new Integer(100), new Integer(100), null, new Integer(1)));
		width_spinner.setValue(dimension.width);
		
		height_spinner = new JSpinner();
		size_panel.add(height_spinner);
		height_spinner.setModel(new SpinnerNumberModel(new Integer(100), new Integer(100), null, new Integer(1)));
		height_spinner.setValue(dimension.height);
		
		JLabel lblConnection = new JLabel("Connection");
		GridBagConstraints gbc_lblConnection = new GridBagConstraints();
		gbc_lblConnection.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblConnection.insets = new Insets(0, 0, 5, 5);
		gbc_lblConnection.gridx = 0;
		gbc_lblConnection.gridy = 1;
		panel.add(lblConnection, gbc_lblConnection);
		JLabel lblConnectionWeight = new JLabel("Connection weight");
		GridBagConstraints gbc_lblConnectionWeight = new GridBagConstraints();
		gbc_lblConnectionWeight.gridwidth = 2;
		gbc_lblConnectionWeight.anchor = GridBagConstraints.WEST;
		gbc_lblConnectionWeight.insets = new Insets(0, 0, 5, 5);
		gbc_lblConnectionWeight.gridx = 0;
		gbc_lblConnectionWeight.gridy = 2;
		panel.add(lblConnectionWeight, gbc_lblConnectionWeight);
		connectionWeightSpinner = new JSpinner();
		connectionWeightSpinner.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		connectionWeightSpinner.setValue(connectionWeight);
		GridBagConstraints gbc_connectionWeightSpinner = new GridBagConstraints();
		gbc_connectionWeightSpinner.gridwidth = 2;
		gbc_connectionWeightSpinner.fill = GridBagConstraints.HORIZONTAL;
		gbc_connectionWeightSpinner.anchor = GridBagConstraints.NORTH;
		gbc_connectionWeightSpinner.insets = new Insets(0, 0, 5, 5);
		gbc_connectionWeightSpinner.gridx = 2;
		gbc_connectionWeightSpinner.gridy = 2;
		panel.add(connectionWeightSpinner, gbc_connectionWeightSpinner);
		JLabel lblConnectionColor = new JLabel("Connection Color");
		GridBagConstraints gbc_lblConnectionColor = new GridBagConstraints();
		gbc_lblConnectionColor.gridwidth = 2;
		gbc_lblConnectionColor.anchor = GridBagConstraints.WEST;
		gbc_lblConnectionColor.insets = new Insets(0, 0, 5, 5);
		gbc_lblConnectionColor.gridx = 0;
		gbc_lblConnectionColor.gridy = 3;
		panel.add(lblConnectionColor, gbc_lblConnectionColor);
		txt_ConnectionColor = new JTextField();
		printColor(txt_ConnectionColor,this.connectionColor);
		txt_ConnectionColor.setEditable(false);
		GridBagConstraints gbc_txtConnectionColor = new GridBagConstraints();
		gbc_txtConnectionColor.gridwidth = 2;
		gbc_txtConnectionColor.insets = new Insets(0, 0, 5, 5);
		gbc_txtConnectionColor.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtConnectionColor.gridx = 2;
		gbc_txtConnectionColor.gridy = 3;
		panel.add(txt_ConnectionColor, gbc_txtConnectionColor);
		txt_ConnectionColor.setColumns(10);
		JButton colorChooser1 = new JButton("...");
		colorChooser1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {				
				Color tmp=JColorChooser.showDialog(VisualGraphPropertiesDialog.this, "Color Chooser",VisualGraphPropertiesDialog.this.connectionColor);
				if(tmp!=null)
				{
					VisualGraphPropertiesDialog.this.connectionColor=tmp;
					printColor(txt_ConnectionColor, tmp);
				}
			}
		});
		GridBagConstraints gbc_colorChooser1 = new GridBagConstraints();
		gbc_colorChooser1.insets = new Insets(0, 0, 5, 5);
		gbc_colorChooser1.gridx = 4;
		gbc_colorChooser1.gridy = 3;
		panel.add(colorChooser1, gbc_colorChooser1);
		JLabel lblPath = new JLabel("Path");
		GridBagConstraints gbc_lblPath = new GridBagConstraints();
		gbc_lblPath.insets = new Insets(0, 0, 5, 5);
		gbc_lblPath.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblPath.gridx = 0;
		gbc_lblPath.gridy = 4;
		panel.add(lblPath, gbc_lblPath);
		JLabel lblPathWeight = new JLabel("Path weight");
		GridBagConstraints gbc_lblPathWeight = new GridBagConstraints();
		gbc_lblPathWeight.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblPathWeight.insets = new Insets(0, 0, 5, 5);
		gbc_lblPathWeight.gridx = 0;
		gbc_lblPathWeight.gridy = 5;
		panel.add(lblPathWeight, gbc_lblPathWeight);
		pathWeight_spinner = new JSpinner();
		pathWeight_spinner.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		pathWeight_spinner.setValue(pathWeight);
		GridBagConstraints gbc_pathWeight_spinner = new GridBagConstraints();
		gbc_pathWeight_spinner.fill = GridBagConstraints.HORIZONTAL;
		gbc_pathWeight_spinner.gridwidth = 2;
		gbc_pathWeight_spinner.insets = new Insets(0, 0, 5, 5);
		gbc_pathWeight_spinner.gridx = 2;
		gbc_pathWeight_spinner.gridy = 5;
		panel.add(pathWeight_spinner, gbc_pathWeight_spinner);
		JLabel lblPathColor = new JLabel("Path Color");
		GridBagConstraints gbc_lblPathColor = new GridBagConstraints();
		gbc_lblPathColor.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblPathColor.insets = new Insets(0, 0, 5, 5);
		gbc_lblPathColor.gridx = 0;
		gbc_lblPathColor.gridy = 6;
		panel.add(lblPathColor, gbc_lblPathColor);
		txt_PathColor = new JTextField();
		txt_PathColor.setText("00 00 00");
		txt_PathColor.setEditable(false);
		GridBagConstraints gbc_txtPathColor = new GridBagConstraints();
		gbc_txtPathColor.gridwidth = 2;
		gbc_txtPathColor.insets = new Insets(0, 0, 5, 5);
		gbc_txtPathColor.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPathColor.gridx = 2;
		gbc_txtPathColor.gridy = 6;
		panel.add(txt_PathColor, gbc_txtPathColor);
		txt_PathColor.setColumns(10);
		printColor(txt_PathColor,this.pathColor);
		JButton colorChooser2 = new JButton("...");
		colorChooser2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Color tmp=JColorChooser.showDialog(VisualGraphPropertiesDialog.this, "Color Chooser",VisualGraphPropertiesDialog.this.pathColor);
				if(tmp!=null)
				{
					VisualGraphPropertiesDialog.this.pathColor=tmp;
					printColor(txt_PathColor,tmp);
				}
			}
		});
		GridBagConstraints gbc_colorChooser2 = new GridBagConstraints();
		gbc_colorChooser2.insets = new Insets(0, 0, 5, 5);
		gbc_colorChooser2.gridx = 4;
		gbc_colorChooser2.gridy = 6;
		panel.add(colorChooser2, gbc_colorChooser2);
		JLabel lblLabel = new JLabel("Label");
		GridBagConstraints gbc_lblLabel = new GridBagConstraints();
		gbc_lblLabel.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblLabel.gridx = 0;
		gbc_lblLabel.gridy = 7;
		panel.add(lblLabel, gbc_lblLabel);
		chckbxLabelOpaque = new JCheckBox("Label opaque");
		chckbxLabelOpaque.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				if(chckbxLabelOpaque.isSelected())
				{
					labelColorBtn.setEnabled(true);
				}
				else
				{
					labelColorBtn.setEnabled(false);
				}
			}
		});
		GridBagConstraints gbc_chckbxLabelOpaque = new GridBagConstraints();
		gbc_chckbxLabelOpaque.anchor = GridBagConstraints.WEST;
		gbc_chckbxLabelOpaque.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxLabelOpaque.gridx = 0;
		gbc_chckbxLabelOpaque.gridy = 8;
		panel.add(chckbxLabelOpaque, gbc_chckbxLabelOpaque);
		txt_LabelColor = new JTextField();
		txt_LabelColor.setText("00 00 00");
		txt_LabelColor.setEditable(false);
		printColor(txt_LabelColor, this.labelColor);
		GridBagConstraints gbc_txtLabelColor = new GridBagConstraints();
		gbc_txtLabelColor.anchor = GridBagConstraints.SOUTH;
		gbc_txtLabelColor.gridwidth = 2;
		gbc_txtLabelColor.insets = new Insets(0, 0, 5, 5);
		gbc_txtLabelColor.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtLabelColor.gridx = 2;
		gbc_txtLabelColor.gridy = 8;
		panel.add(txt_LabelColor, gbc_txtLabelColor);
		txt_LabelColor.setColumns(10);
		labelColorBtn = new JButton("...");
		labelColorBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Color tmp=JColorChooser.showDialog(VisualGraphPropertiesDialog.this, "Color Chooser",VisualGraphPropertiesDialog.this.labelColor);
				if(tmp!=null)
				{
					VisualGraphPropertiesDialog.this.labelColor=tmp;
					printColor(txt_LabelColor, tmp);
				}
			}
		});
		labelColorBtn.setEnabled(false);
		GridBagConstraints gbc_labelColorBtn = new GridBagConstraints();
		gbc_labelColorBtn.insets = new Insets(0, 0, 5, 5);
		gbc_labelColorBtn.gridx = 4;
		gbc_labelColorBtn.gridy = 8;
		panel.add(labelColorBtn, gbc_labelColorBtn);
		chckbxScale = new JCheckBox("Scale");
		chckbxScale.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				if(chckbxScale.isSelected())
				{
					scale_spinner.setEnabled(true);
				}
				else
				{
					scale_spinner.setEnabled(false);
				}
			}
		});
		chckbxBackgroundImage = new JCheckBox("Background Image");
		chckbxBackgroundImage.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				if(chckbxBackgroundImage.isSelected())
				{
					txt_Path.setEditable(true);
					backgroundImageBtn.setEnabled(true);
					anythingChanged();
				}
				else
				{
					txt_Path.setEditable(false);
					backgroundImageBtn.setEnabled(false);
					anythingChanged();
				}
				
			}
		});
		GridBagConstraints gbc_chckbxBackgroundImage = new GridBagConstraints();
		gbc_chckbxBackgroundImage.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxBackgroundImage.gridx = 0;
		gbc_chckbxBackgroundImage.gridy = 10;
		panel.add(chckbxBackgroundImage, gbc_chckbxBackgroundImage);
		txt_Path = new JTextField();
		GridBagConstraints gbc_path_txt = new GridBagConstraints();
		gbc_path_txt.gridwidth = 2;
		gbc_path_txt.fill = GridBagConstraints.HORIZONTAL;
		gbc_path_txt.insets = new Insets(0, 0, 5, 5);
		gbc_path_txt.gridx = 2;
		gbc_path_txt.gridy = 10;
		panel.add(txt_Path, gbc_path_txt);
		txt_Path.setColumns(35);
		txt_Path.setEditable(false);
		backgroundImageBtn = new JButton("...");
		GridBagConstraints gbc_backgroundImageBtn = new GridBagConstraints();
		gbc_backgroundImageBtn.insets = new Insets(0, 0, 5, 5);
		gbc_backgroundImageBtn.gridx = 4;
		gbc_backgroundImageBtn.gridy = 10;
		panel.add(backgroundImageBtn, gbc_backgroundImageBtn);
		backgroundImageBtn.setEnabled(false);

		GridBagConstraints gbc_chckbxScale = new GridBagConstraints();
		gbc_chckbxScale.anchor = GridBagConstraints.WEST;
		gbc_chckbxScale.insets = new Insets(0, 0, 0, 5);
		gbc_chckbxScale.gridx = 0;
		gbc_chckbxScale.gridy = 11;
		panel.add(chckbxScale, gbc_chckbxScale);
		JLabel label = new JLabel("1:");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.anchor = GridBagConstraints.EAST;
		gbc_label.insets = new Insets(0, 0, 0, 5);
		gbc_label.gridx = 1;
		gbc_label.gridy = 11;
		panel.add(label, gbc_label);
		scale_spinner = new JSpinner();
		scale_spinner.setEnabled(false);
		scale_spinner.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		GridBagConstraints gbc_scale_spinner = new GridBagConstraints();
		gbc_scale_spinner.fill = GridBagConstraints.HORIZONTAL;
		gbc_scale_spinner.gridwidth = 2;
		gbc_scale_spinner.insets = new Insets(0, 0, 0, 5);
		gbc_scale_spinner.gridx = 2;
		gbc_scale_spinner.gridy = 11;
		panel.add(scale_spinner, gbc_scale_spinner);
		
		//**********************************************************************
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		applyButton = new JButton("Apply");
		applyButton.setEnabled(false);
		buttonPane.add(applyButton);
		getRootPane().setDefaultButton(applyButton);
		
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		buttonPane.add(cancelButton);
	}
	
	private void init()
	{
		
	}
	private void apply()
	{
		apply_Dimension=new Dimension(((Number)width_spinner.getValue()).intValue(), ((Number)height_spinner.getValue()).intValue());
		apply_LabelColor=labelColor;
		
		apply_ConnectionWeight=((Number)connectionWeightSpinner.getValue()).intValue();
		apply_ConnectionColor=connectionColor;
		
		apply_PathWeigth=((Number)pathWeight_spinner.getValue()).intValue();
		apply_PathColor=pathColor;
		
		apply_ScaleFactorIsUsed=chckbxScale.isSelected();
		if(chckbxScale.isSelected())
		apply_ScaleFactor=((Number)scale_spinner.getValue()).intValue();
		apply_BackgroundImage=new ImageIcon(txt_Path.getText()).getImage();
		apply_LabelOpaque=chckbxLabelOpaque.isSelected();
	}
	private void anythingChanged()
	{
		applyButton.setEnabled(true);
	}

	private static void printColor(JTextField field, Color color)
	{
	 field.setText(String.format("%02x %02x %02x",color.getRed(),color.getGreen(),color.getBlue()).toUpperCase());
	}
	
	public Dimension getSize()
	{
		return apply_Dimension;
	}
	public Color getConnectionColor()
	{
		return apply_ConnectionColor; 
	}
	public Color getPathColor()
	{
		return apply_PathColor;
	}
	public int getPathWeight()
	{
		return apply_PathWeigth;
	}
	public int getConnectionWeight()
	{
		return apply_ConnectionWeight;
	}
	public Color getLabelBackgroundColor()
	{
		return apply_LabelColor;
	}
	public boolean isLabelOpaque()
	{
		return apply_LabelOpaque;
	}
	public int getScaleFactor()
	{
		return apply_ScaleFactor;
	}
	public boolean isScaleFactorUsed()
	{
		return apply_ScaleFactorIsUsed;
	}
}
