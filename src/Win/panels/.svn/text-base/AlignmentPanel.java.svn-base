package Win.panels;

import java.awt.*;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import java.awt.GridBagConstraints;
import java.awt.BorderLayout;
import java.awt.event.*;
import javax.swing.border.*;
import javax.swing.JLabel;
import Objects.*;
import Win.textPanel.*;
import Math.StaticFunction;
import java.awt.GridBagLayout;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Rectangle;
import java.beans.*;
import Win.panels.Event.*;

public class AlignmentPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JSplitPane jSplitPane = null;
	private JPanel names = null;
	private JPanel body = null;
	private JLabel numbers = null;
	private JPanel topPanel = new JPanel();
	private ZscoreViewer zscore = new ZscoreViewer();
	private JPanel jPanel = null;
	private JLabel zscoreLabel = null; // @jve:decl-index=0:visual-constraint="41,9"
	private JLabel positionLabel = null; // @jve:decl-index=0:visual-constraint="52,44"
	private JPanel namesPanel = null;
	private JTextArea namesArea = null;
	private int[] sdps;
	private ScreenPanel alignmentPanel = null;
	private Project project = null;
	private GroupingChangeListener groupingChangeListener = new GroupingChangeListener(){
		public void groupingChange() {
			if(project != null){
				setAlignment(project);
			}
		};
	};
	
	public GroupingChangeListener getGroupingChangeListener(){
		return groupingChangeListener;
	}

	/**
	 * This is the default constructor
	 */
	public AlignmentPanel() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(300, 200);
		this.setLayout(new BorderLayout());
		this.add(getJSplitPane(), BorderLayout.CENTER);
		zscore.addThresholdListener(new ThresholdChangeListener(){
			public void thresholdChanged(ThresholdEvent e){
				changeThreshold(e);
			};
		});
	}
	
	public void changeThreshold(ThresholdEvent e){
		int[] biggest, smallest;
		Color newColor;
		if(e.positions.length == sdps.length) return;
		if(e.positions.length > sdps.length){
			biggest = e.positions;
			smallest = sdps;
			newColor = Color.RED;
		}else{
			biggest = sdps;
			smallest = e.positions;
			newColor = Color.WHITE;
		}
		int smallIndex = 0;
		for(int i=0;i<biggest.length;i++){
			if(smallIndex < smallest.length && biggest[i] == smallest[smallIndex]){
				smallIndex++;
			}else{
				setBackgroundForColumn(biggest[i],newColor);
			}
		}
			zscore.setThreshold(e.threshold);
		sdps = e.positions;
		alignmentPanel.myRepaint();
	}

	/**
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setLeftComponent(getNames());
			jSplitPane.setRightComponent(getBody());
			jSplitPane.addPropertyChangeListener(
					JSplitPane.DIVIDER_LOCATION_PROPERTY,
					new PropertyChangeListener() {
						public void propertyChange(PropertyChangeEvent evt) {
							getNamesArea().setBounds(getNamesArea().getX(),
									getNamesArea().getY(),
									((Integer) evt.getNewValue()).intValue(),
									getNamesArea().getHeight());
						}
					});
			getNamesArea();

		}
		return jSplitPane;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getNames() {
		if (names == null) {
			names = new JPanel();
			names.setMinimumSize(new Dimension(40, 20));
			names.setPreferredSize(new Dimension(110, 20));
			names.setLayout(new BorderLayout());
			names.add(getJPanel(), BorderLayout.NORTH);
			names.add(getNamesPanel(), BorderLayout.CENTER);
		}
		return names;
	}
	
	private void setBackgroundForColumn(int column, Color bg){
		for(int i=0;i<alignmentPanel.getLineCount();i++){
			alignmentPanel.setBackground(bg, column, i);
		}
	}
	
	public void addThresholdListener(ThresholdChangeListener l){
		zscore.addThresholdListener(l);
	}
	
	/**
	 * All changes in grouping must be performed by this method
	 * @param p
	 */
	public void setAlignment(Project p) {
		project = p;
		if(alignmentPanel != null)
			body.remove(alignmentPanel);
		alignmentPanel = new ScreenPanel(12);
		namesArea.setBounds(new Rectangle(0, 0, jSplitPane.getDividerLocation(),alignmentPanel.getCharHeight()* (p.getGroupCount() + p.getSeqCount())));
		topPanel.setBounds(0, 0, alignmentPanel.getCharWidth()*p.getAlinmentLength(), 20 + zscore.getHeight());
		numbers.setBounds(0, zscore.getHeight(), alignmentPanel.getCharWidth()*p.getAlinmentLength(), 20);
		StringBuffer nams = new StringBuffer(1000);
		sdps = p.getSortedForAlignmentPositionSDP();
		int[][] grouping = p.getGroupng();
		ScreenChar[] freeline = new ScreenChar[p.getAlinmentLength()];
		int sdpNo = 0;
		for (int i = 0; i < p.getAlinmentLength(); i++) {
			if (sdpNo >= sdps.length || sdps[sdpNo] != i) {
				freeline[i] = new ScreenChar(' ', Color.BLACK, Color.WHITE);
			} else {
				freeline[i] = new ScreenChar(' ', Color.BLACK, Color.RED);
				sdpNo++;
			}
		}
		for (int g = 0; g < grouping.length; g++) {
			alignmentPanel.addLine(freeline);
			nams.append(p.getGroupName(g)).append("\n");
			for (int s = 1; s <= grouping[g][0]; s++) {
				nams.append("  ").append(p.getSeqName(grouping[g][s])).append("\n");
				sdpNo = 0;
				ScreenChar[] seq = new ScreenChar[p.getAlinmentLength()];
				for (int a = 0; a < seq.length; a++) {
					if (sdpNo >= sdps.length || sdps[sdpNo] != a) {
						seq[a] = new ScreenChar(p.getChar(grouping[g][s], a),
								Color.BLACK, Color.WHITE);
					} else {
						seq[a] = new ScreenChar(p.getChar(grouping[g][s], a),
								Color.BLACK, Color.RED);
						sdpNo++;
					}
				}
				alignmentPanel.addLine(seq);
			}
		}
		namesArea.setText(nams.toString());
		body.add(alignmentPanel, BorderLayout.CENTER);
		alignmentPanel.setMinimumSize(new Dimension(100, 100));
		numbers.setText(StaticFunction.generateAligmentNumberString(p
				.getAlinmentLength(), 10, " ").substring(0,p.getAlinmentLength()-1));
		double[] t = p.getAllZscoreSortedForAlignmentPosition();
		zscore.setWidthOfColumn(alignmentPanel.getCharWidth());
		if (t != null)
			zscore.setData(t, p.getThresholdPositionNamber());
		alignmentPanel.addAdjustmentListenerForVerticalScroll(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent e) {
				namesArea.setLocation(namesArea.getX(), -e.getValue());
			}
		});
		alignmentPanel.addAdjustmentListenerForHorisontalScroll(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent e) {
				topPanel.setLocation(-e.getValue(), topPanel.getLocation().y);
			}
		});
	}

	/**
	 * This method initializes body
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getBody() {
		if (body == null) {
			JPanel northPanel = new JPanel();
			northPanel.setLayout(null);
			northPanel.add(topPanel);
			northPanel
					.setMinimumSize(new Dimension(20, 20 + zscore.getHeight()));
			northPanel.setPreferredSize(new Dimension(20, 20 + zscore
					.getHeight()));
			numbers = new JLabel("null");
			numbers.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
			numbers.setBorder(new EtchedBorder(1));
			body = new JPanel();
			body.setLayout(new BorderLayout());
			topPanel.setLayout(null);
			topPanel.add(zscore);
			zscore.setBounds(0, 0, 3000, zscore.getHeight());
			topPanel.add(numbers);
			body.add(northPanel, BorderLayout.NORTH);
		}
		return body;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setMinimumSize(new Dimension(40, 20 + zscore.getHeight()));
			jPanel.setPreferredSize(new Dimension(40, 20 + zscore.getHeight()));
			jPanel.setLayout(null);
			jPanel.add(getZscoreLabel());
			zscoreLabel.setBounds(0, (zscore.getHeight() - 20) / 2, 300, 20);
			jPanel.add(getPositionLabel());
			positionLabel.setBounds(0, zscore.getHeight(), 300, 20);
		}
		return jPanel;
	}

	/**
	 * This method initializes zscoreLabel
	 * 
	 * @return javax.swing.JLabel
	 */
	private JLabel getZscoreLabel() {
		if (zscoreLabel == null) {
			zscoreLabel = new JLabel();
			zscoreLabel.setMinimumSize(new Dimension(300, 20));
			zscoreLabel.setPreferredSize(new Dimension(300, 20));
			zscoreLabel.setText("Z-scores:");
		}
		return zscoreLabel;
	}

	/**
	 * This method initializes positionLabel
	 * 
	 * @return javax.swing.JLabel
	 */
	private JLabel getPositionLabel() {
		if (positionLabel == null) {
			positionLabel = new JLabel();
			positionLabel.setMinimumSize(new Dimension(300, 20));
			positionLabel.setPreferredSize(new Dimension(300, 20));
			positionLabel.setText("Alignment position:");
		}
		return positionLabel;
	}

	/**
	 * This method initializes namesPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getNamesPanel() {
		if (namesPanel == null) {
			namesPanel = new JPanel();
			namesPanel.setLayout(null);
			namesPanel.add(getNamesArea(), null);
		}
		return namesPanel;
	}

	/**
	 * This method initializes namesArea
	 * 
	 * @return javax.swing.JTextArea
	 */
	private JTextArea getNamesArea() {
		if (namesArea == null) {
			namesArea = new JTextArea();
			namesArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
		}
		return namesArea;
	}

}
