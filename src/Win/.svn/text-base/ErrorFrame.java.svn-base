package Win;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.BorderLayout;
import javax.swing.JDialog;
import java.awt.GridBagLayout;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.Dimension;

public class ErrorFrame extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JPanel jPanel = null;
	private JTextArea message = null;
	private JButton ok = null;  //  @jve:decl-index=0:visual-constraint="535,69"
	private JScrollPane jScrollPane = null;
	/**
	 * @param owner
	 */
	public ErrorFrame(Frame owner) {
		super(owner,true);
		initialize();
	}
	
	public ErrorFrame(Frame owner,Exception e) {
		this(owner);
		setError(e);
		this.setVisible(true);
	}
	
	public void setError(Exception e){
		java.io.CharArrayWriter cw = new java.io.CharArrayWriter(); 
		java.io.PrintWriter pw = new java.io.PrintWriter(cw,true); 
		e.printStackTrace(pw); 
		message.setText(cw.toString());
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(300, 200);
		this.setTitle("Error");
		this.setContentPane(getJContentPane());
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJPanel(), BorderLayout.SOUTH);
			jContentPane.add(getJScrollPane(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setMinimumSize(new Dimension(10,40));
			jPanel.setLayout(new GridBagLayout());
			jPanel.add(getOk());
		}
		return jPanel;
	}

	/**
	 * This method initializes message	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getMessage() {
		if (message == null) {
			message = new JTextArea();		
			message.setEditable(false);
		}
		return message;
	}

	/**
	 * This method initializes ok	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getOk() {
		if (ok == null) {
			ok = new JButton();
			ok.setText("OK");
			ok.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return ok;
	}

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getMessage());
		}
		return jScrollPane;
	}

}
