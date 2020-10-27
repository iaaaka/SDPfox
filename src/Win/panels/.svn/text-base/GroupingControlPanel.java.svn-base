package Win.panels;

import java.awt.*;
import javax.swing.JPanel;
import java.awt.event.*;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import java.util.*;
import Objects.*;
import Win.panels.Event.*;
import javax.swing.JScrollPane;
import java.awt.Dimension;

public class GroupingControlPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JPanel jPanel = null;
	private JPanel jPanel1 = null;  //  @jve:decl-index=0:visual-constraint="605,144"
	private JButton moveR = null;
	private JButton moveL = null;
	private JComboBox groupR = null;
	private JComboBox groupL = null;
	private JList seqsL = null;
	private Vector<GroupingChangeListener> groupingChangeListeners = new Vector<GroupingChangeListener>();
	private Project project = null;  //  @jve:decl-index=0:
	private JScrollPane jScrollPane = null;
	private JScrollPane jScrollPane1 = null;
	private JList seqsR = null;
	/**
	 * This is the default constructor
	 */
	public GroupingControlPanel() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		GridLayout gridLayout = new GridLayout();
		gridLayout.setRows(1);
		this.setLayout(gridLayout);
		this.setSize(300, 200);
		this.add(getJPanel(), null);
		this.add(getJPanel1(), null);
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jPanel.add(getMoveL(), BorderLayout.SOUTH);
			jPanel.add(getGroupL(), BorderLayout.NORTH);
			jPanel.add(getJScrollPane(), BorderLayout.CENTER);
		}
		return jPanel;
	}

	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(new BorderLayout());
			jPanel1.add(getMoveR(), BorderLayout.SOUTH);
			jPanel1.add(getGroupR(), BorderLayout.NORTH);
			jPanel1.add(getJScrollPane1(), BorderLayout.CENTER);
		}
		return jPanel1;
	}

	/**
	 * This method initializes moveR	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getMoveR() {
		if (moveR == null) {
			moveR = new JButton();
			moveR.setText("<<<");
			moveR.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					moveSequences(seqsR,groupL.getSelectedIndex());
				}
			});
		}
		return moveR;
	}

	/**
	 * This method initializes moveL	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getMoveL() {
		if (moveL == null) {
			moveL = new JButton();
			moveL.setText(">>>");
			moveL.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					moveSequences(seqsL,groupR.getSelectedIndex());
				}
			});
		}
		return moveL;
	}

	/**
	 * This method initializes groupR	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getGroupR() {
		if (groupR == null) {
			groupR = new JComboBox();
			groupR.addItemListener(new ItemListener(){
				public void itemStateChanged(ItemEvent e) {
					changeGroup(seqsR,project.getGroupNo((String)e.getItem()));
				}
			});
		}
		return groupR;
	}

	/**
	 * This method initializes groupL	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getGroupL() {
		if (groupL == null) {
			groupL = new JComboBox();
			groupL.addItemListener(new ItemListener(){
				public void itemStateChanged(ItemEvent e) {
					changeGroup(seqsL,project.getGroupNo((String)e.getItem()));
				}
			});
		}
		return groupL;
	}

	public void setProject(Project p){
		project = p;
		setGrouping();
	}
	
	private void setGrouping(){
		if(project == null || project.getGroupng() == null)
			return;
		groupL.removeAllItems();
		groupR.removeAllItems();
		for(int i=0;i<project.getGroupCount();i++){
			groupL.addItem(project.getGroupName(i));
			groupR.addItem(project.getGroupName(i));
		}
		groupL.setSelectedIndex(0);
		groupR.setSelectedIndex(0);
	}
	
	public void addGroupingChangeListener(GroupingChangeListener l){
		this.groupingChangeListeners.add(l);
	}
	
	public void fireGroupingChange(){
		if(project == null || project.getGroupng() == null){
			return;
		}
		for(int i=0;i<groupingChangeListeners.size();i++){
			groupingChangeListeners.get(i).groupingChange();
		}
	}
	
	private void moveSequences(JList seqs,int newGr){
		Object[] o = seqs.getSelectedValues();
		int[] seqNos = new int[o.length];
		for(int i=0;i<o.length;i++){
			seqNos[i] = project.getSequenceNo((String)o[i]);
		}
		project.moveSequences(newGr, seqNos);
		int lsel = groupL.getSelectedIndex();
		int rsel = groupR.getSelectedIndex();
		setGrouping();
		groupL.setSelectedIndex(lsel);
		groupR.setSelectedIndex(rsel);
		fireGroupingChange();
	}
	
	private void changeGroup(JList l,int grNo){
		int[] group = project.getGroupng()[grNo];
		String[] names = new String[group[0]];
		for(int i=1;i<=group[0];i++){
			names[i-1] = project.getSeqName((group[i]));
		}
		l.setListData(names);
	}

	/**
	 * This method initializes seqsL	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JList getSeqsL() {
		if (seqsL == null) {
			seqsL = new JList();
		}
		return seqsL;
	}

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getSeqsL());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jScrollPane1	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getSeqsR());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes seqsR	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JList getSeqsR() {
		if (seqsR == null) {
			seqsR = new JList();
		}
		return seqsR;
	}

}
