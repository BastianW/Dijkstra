package de.bwvaachen.graph.gui;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JDialog;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import de.bwvaachen.graph.gui.input.GraphInputView;
import de.bwvaachen.graph.logic.Graph;

public class MainWindow extends JFrame {

	private JPanel contentPane;
	private JProgressBar progressBar;
	private JMenuBar menuBar;
	private JTabbedPane tabbedPane;
	private GraphInputView graphInputView;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow frame = new MainWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 800);

		JMenuBar menuBar = new JMenuBar();
		this.menuBar = menuBar;
		setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		JMenu mnNew = new JMenu("New");
		mnFile.add(mnNew);
		mnFile.addSeparator();
		JMenuItem mnSave = new JMenuItem("Save");
		mnSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser();
				int returnVal = chooser.showOpenDialog(MainWindow.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File selectedFile = chooser.getSelectedFile();
					try {
						System.out.println(selectedFile.getAbsolutePath());
						graphInputView.save(selectedFile.getAbsolutePath());
					} catch (JsonProcessingException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}

				}
			}
		});
		mnFile.add(mnSave);
		JMenuItem mnLoad = new JMenuItem("Load");
		mnLoad.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser();
				int returnVal = chooser.showOpenDialog(MainWindow.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File selectedFile = chooser.getSelectedFile();
					try {
						System.out.println(selectedFile.getAbsolutePath());
						graphInputView.load(selectedFile.getAbsolutePath());
					} catch (JsonProcessingException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}

				}
			}
		});
		mnFile.add(mnLoad);
		
		mnFile.addSeparator();
		JMenuItem mntmNewGraph = new JMenuItem("New Graph");
		mnNew.add(mntmNewGraph);
		mnFile.add(mntmExit);

		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);

		
		JMenuItem mntmAboutTolleflugbuchung = new JMenuItem("About TolleFlugBuchung");
		mntmAboutTolleflugbuchung.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					AboutDialog dialog = new AboutDialog();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		mnHelp.add(mntmAboutTolleflugbuchung);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 1, 0, 0));

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane);
		Graph graph = null;
		try {
			graph = Graph.load("BigTest.txt");
		} catch (JsonParseException e2) {
			e2.printStackTrace();
		} catch (JsonMappingException e2) {
			e2.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		graphInputView = new GraphInputView(graph, this);
		tabbedPane.addTab("Input", null, graphInputView, null);

	}

	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}

		public void actionPerformed(ActionEvent e) {
		}
	}

	public void newTab(JPanel component, String name) {
		tabbedPane.addTab(name, null, component, null);
		tabbedPane.setSelectedIndex(tabbedPane.getComponentCount() - 1);
	}
}
