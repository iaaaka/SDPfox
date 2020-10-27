package Win;

import javax.swing.*;
import Win.panels.*;
import java.awt.*;

import Objects.*;
import java.awt.event.*;

public class Main extends JFrame {
	private Project project = new Project();
	private final JFrame thisFrame = this;
	JTabbedPane tabbedPane = new JTabbedPane();
	AlignmentPanel alignmentPanel;

	public Main() {
		super("SDPproff 1.1");
		JMenuBar menu = new JMenuBar();
		this.setJMenuBar(menu);
		JMenu load = new JMenu("Load");
		JMenu save = new JMenu("Save");
		JMenu file = new JMenu("File");
		JMenuItem about = new JMenuItem("About");
		JMenuItem exit = new JMenuItem("Exit");
		JMenuItem help = new JMenuItem("help");
		JMenuItem loadAlg = new JMenuItem("Alignment");
		JMenuItem loadGrMatrix = new JMenuItem("Grouping matrix");
		JMenuItem loadDist = new JMenuItem("Distance matrix");
		JMenuItem loadTree = new JMenuItem("Tree");
		JMenuItem saveAlg = new JMenuItem("Alignment");
		JMenuItem saveTree = new JMenuItem("Tree");
		JMenuItem saveDist = new JMenuItem("Distance matrix");
		JMenuItem saveLogo = new JMenuItem("Logo");

		file.add(help).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		file.add(about).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		file.add(exit).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exit();
			}
		});
		save.add(saveAlg).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		save.add(saveTree).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		save.add(saveDist).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		save.add(saveLogo).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});

		load.add(loadAlg).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final JFileChooser fileChooser = new JFileChooser(
						"in/alignment/");
				final JDialog chooser = new JDialog(thisFrame, true);
				chooser.getContentPane().add(fileChooser);
				chooser.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				fileChooser.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						fileChooser.setCursor(new Cursor(Cursor.WAIT_CURSOR));
						try {
							setAlignment(fileChooser.getSelectedFile()
									.getPath());
							chooser.dispose();
						} catch (Exception i) {
							project = new Project();
							new ErrorFrame(thisFrame, i);
						}
						fileChooser.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					}
				});
				chooser.setSize(400, 400);
				chooser.setVisible(true);
			}
		});

		load.add(loadGrMatrix).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		load.add(loadDist).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		load.add(loadTree).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});

		save.setEnabled(false);
		saveAlg.setEnabled(false);
		saveDist.setEnabled(false);
		saveTree.setEnabled(false);
		saveLogo.setEnabled(false);
		menu.add(file);
		menu.add(load);
		menu.add(save);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				exit();
			}
		});
		this.setSize(300, 300);
		this.setVisible(true);
	}

	private void exit() {
		this.dispose();
		System.exit(0);
	}

	public AlignmentPanel setNewAlignmentPanel() {
		tabbedPane.removeAll();
		alignmentPanel = new AlignmentPanel();
		JSplitPane allAlignmentPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		JPanel allControlPanel = new JPanel();
		allControlPanel.setLayout(new GridLayout(1,2));
		allControlPanel.add(new JPanel());
		GroupingControlPanel gcp = new GroupingControlPanel();
		gcp.setProject(project);
		gcp.addGroupingChangeListener(alignmentPanel.getGroupingChangeListener());
		allControlPanel.add(gcp);
		allAlignmentPane.add(allControlPanel);
		allAlignmentPane.add(alignmentPanel);
		tabbedPane.add(allAlignmentPane, "Alignment");
		return alignmentPanel;
	}

	private void setAlignment(String fname) throws Exception {
		project.setAlignmentData(fname);
		setNewAlignmentPanel().setAlignment(project);
		this.getContentPane().add(tabbedPane);
		this.setSize(500, 600);

	}

	public static void main(String[] args) {
		new Main();
	}
}
