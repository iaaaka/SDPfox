package painter;

import javax.swing.JFrame;
import java.awt.event.*;

public class ColorFrame extends JFrame {
	ColorPanel colorpanel;

	public ColorFrame(double[][] test, String name) {
		super(name);
		colorpanel = new ColorPanel(test);
		init();
	}

	private void init() {
		this.getContentPane().add(colorpanel);
		this.setSize(400, 400);
		this.setVisible(true);
		this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);

	}
}
