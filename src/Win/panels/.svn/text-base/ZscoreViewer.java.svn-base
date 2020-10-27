package Win.panels;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.util.*;
import Win.panels.Event.*;

public class ZscoreViewer extends JPanel {
	private BufferedImage image;
	private double thr;
	private int width = 10;
	private int height = 50;
	private Color background = Color.white;
	private Color minColor = Color.blue;
	private Color maxColor = Color.red;
	private Color thrColor = Color.black;
	private double[] paintedData;
	private int freeTopPart = 2;
	private Vector<ThresholdChangeListener> thresholdListeners;
	private double min;
	private double max;

	public ZscoreViewer() {
		super();
		this.setBackground(Color.white);
		thresholdListeners = new Vector<ThresholdChangeListener>();
		this.addMouseListener(new MouseAdapter(){
			 public void mouseClicked(MouseEvent e){
				 thr = Math.min(1, (double)((double)(freeTopPart+height-e.getY())/height));
				 int[] positions = new int[paintedData.length];
				 int index = 0;
				 for(int i=0;i<positions.length;i++){
					 if(paintedData[i]>=thr){
						 positions[index] = i;
						 index++;
					 }
				 }
				 int[] sdps = new int[index];
				 for(int i=0;i<index;i++){
					 sdps[i] = positions[i];
				 }
				 for(int i=0;i<thresholdListeners.size();i++){
					thresholdListeners.get(i).thresholdChanged(new ThresholdEvent(sdps,thr*(max-min)+min));
				 }
			 };
		});
		this.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
	}
	
	public ZscoreViewer(double[] data, int thr) {
		this();
		this.setData(data, thr);
	}
	
	public void setWidthOfColumn(int w){
		if(image != null && width != w){
			width = w;
			paintImage();
			this.repaint();
		}else		
			width = w;
	}
	
	public void setThreshold(int t){
		this.thr = paintedData[t];
		repaint();
	}
	
	public void setThreshold(double t){
		this.thr = (t-min)/(max-min);
		repaint();
	}
	
	public void addThresholdListener(ThresholdChangeListener l){
		thresholdListeners.add(l);
	}

	private Color getColorForColumn(double data) {
		return new Color((int) (maxColor.getRed() * data + minColor.getRed()
				* (1 - data)), (int) (maxColor.getGreen() * data + minColor
				.getGreen()
				* (1 - data)), (int) (maxColor.getBlue() * data + minColor
				.getBlue()
				* (1 - data)));
	}
	
	public int getWidth(){
		if(image == null)
			return 0;
		else return image.getWidth();
	}
	
	public int getHeight(){
		return this.height+freeTopPart;
	}
	
	public void paintComponent(Graphics gr) {
		if(image != null){
			gr.drawImage(image, 0, 0, null);
			gr.setColor(this.thrColor);
			gr.drawLine(0, (int)(this.height*(1-this.thr))+this.freeTopPart, image.getWidth(), (int)(this.height*(1-this.thr))+this.freeTopPart);
		}
	}

	public void setData(double[] data, int thr) {
		min = data[0];
		max = data[0];
		paintedData = new double[data.length];
		for (int i = 0; i < data.length; i++) {
			min = Math.min(min, data[i]);
			max = Math.max(max, data[i]);
		}
		for (int i = 0; i < data.length; i++) {
			paintedData[i] = (data[i] - min) / (max - min);
		}
		this.thr = paintedData[thr];
		paintImage();
		repaint();
	}
	
	private void paintImage(){
		image = new BufferedImage(this.width * paintedData.length, this.height+freeTopPart,
				BufferedImage.TYPE_INT_RGB);
		this.setMinimumSize(new Dimension(this.width * paintedData.length, this.height+freeTopPart));
		this.setPreferredSize(new Dimension(this.width * paintedData.length, this.height+freeTopPart));
		Graphics2D gr = (Graphics2D) image.getGraphics();
		gr.setColor(background);
		gr.fillRect(0, 0, image.getWidth(), image.getHeight());
		gr.setTransform(AffineTransform.getTranslateInstance(0, freeTopPart));
		for(int i=0;i<paintedData.length;i++){
			gr.setColor(this.getColorForColumn(paintedData[i]));
			gr.fillRect(i*width, (int)((1-paintedData[i])*height), width, height - (int)((1-paintedData[i])*height));
			gr.setColor(Color.black);
			gr.drawRect(i*width, (int)((1-paintedData[i])*height), width, height - (int)((1-paintedData[i])*height));
		}
	}

}
