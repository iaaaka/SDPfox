package painter;

import javax.swing.JPanel;
import java.awt.font.*;
import java.awt.*;
import java.awt.geom.*;

public class LogoPainter extends JPanel {
	public final int WIDTH = 20;
	public final int HEIGHT = 200;
	public final int TEXT_PART = HEIGHT/20;
	private AffineTransform mainTransform;
	private boolean isImage = false;
	private int realHeight;
	private int realWidth;
	private int[] absiluteHeights;
	private double[][] parts;
	private int[][] absolutePositions;
	private char[] chars;
	private int[] positions;
	private final Color[] colors = {Color.BLACK,Color.RED,Color.BLUE,Color.ORANGE,Color.CYAN,Color.GREEN,Color.MAGENTA,Color.PINK,Color.YELLOW};
	private final int FONT_SIZE = 100;
	private Graphics graphics;
	private Font FONT = new Font(Font.MONOSPACED,Font.PLAIN,FONT_SIZE);

	/**
	 * ���� ����������� ����� ���� ������ ����� �������������� ��� ��������� �� BufferedImage
	 * ���������� ����� c.length = parts[].length. revHeights.length=part.length=pos.length.
	 * @param c ����� ���� ������� ���� ��������. �������� ������������
	 * @param revHeights ������ �������� � ������ ��������. �������� �������� �������
	 * @param parts ���� ������ ���� � ������ �������. [�������][�����].
	 * @param pos ������ ������ ��� ��������.
	 * @param width - ������ ��������
	 * @param height - ������ ��������
	 */
	public LogoPainter(char[] c,double[] revHeights,double[][] parts,int[] pos,int width,int height,Graphics g){
		this(c,revHeights,parts,pos);
		isImage = true;
		realHeight = height;
		realWidth = width;
		graphics = g;
	}
	
	/**
	 * ���������� ����� c.length = parts[].length. revHeights.length=part.length=pos.length.
	 * @param c ����� ���� ������� ���� ��������. �������� ������������
	 * @param revHeights ������ �������� � ������ ��������. �������� �������� �������
	 * @param parts ���� ������ ���� � ������ �������. [�������][�����].
	 * @param pos ������ ������ ��� ��������.
	 */
	public LogoPainter(char[] c,double[] revHeights,double[][] parts,int[] pos){
		chars = c;
		double mxH = 0;
		positions = pos;
		for(int i=0;i<revHeights.length;i++){
			mxH = Math.max(mxH, revHeights[i]);
		}
		absiluteHeights = new int[revHeights.length];
		this.parts = new double[parts.length][parts[0].length];
		absolutePositions = new int[parts.length][parts[0].length+1];
		for(int i=0;i<revHeights.length;i++){
			absiluteHeights[i] = (int) Math.floor((double)(HEIGHT - TEXT_PART)*revHeights[i]/mxH);
			double total = 0;
			for(int j=0;j<chars.length;j++){
				total += parts[i][j];
			}
			absolutePositions[i][0] = this.HEIGHT - this.TEXT_PART - absiluteHeights[i];
			this.parts[i][0] = parts[i][0]/total;
			for(int j=1;j<chars.length;j++){
				this.parts[i][j] = parts[i][j]/total;
				absolutePositions[i][j] = absolutePositions[i][j-1] + (int) Math.floor((double)absiluteHeights[i]*this.parts[i][j-1]);
			}
			absolutePositions[i][parts[0].length] = this.HEIGHT - this.TEXT_PART-1;
		}
		this.setSize(parts.length*this.WIDTH, this.HEIGHT);
		this.setBackground(Color.WHITE);
	}
	
	public void paintComponent(Graphics gr){
		setMainTransform();
		Graphics2D g = (Graphics2D) gr;
		g.setTransform(this.mainTransform);
		gr.setFont(FONT);
		for(int i=0;i<absolutePositions.length;i++){
			paintPosition(i,g);
		}
		gr.setColor(Color.BLACK);
		setMainTransform();
		g.setTransform(this.mainTransform);
		g.drawLine(0, this.HEIGHT - this.TEXT_PART, this.WIDTH*absolutePositions.length, this.HEIGHT - this.TEXT_PART);
	}
	
	private void paintPosition(int pos,Graphics2D g){
		int colorInx=0;
		AffineTransform m = (AffineTransform) mainTransform.clone();
		m.concatenate(AffineTransform.getTranslateInstance(pos*this.WIDTH, 0));
		for(int i=0;i<chars.length;i++){
			AffineTransform current =(AffineTransform) m.clone();
			current.concatenate(AffineTransform.getTranslateInstance(0, absolutePositions[pos][i+1]));
			TextLayout tl = new TextLayout(""+chars[i],FONT,new FontRenderContext(new AffineTransform(),true,true));
			AffineTransform FontTr = AffineTransform.getScaleInstance((double)this.WIDTH/(tl.getBounds().getWidth()), (double)(absolutePositions[pos][i+1]-absolutePositions[pos][i])/(tl.getBounds().getHeight()));
			Font tmp = FONT.deriveFont(FontTr);
			tl = new TextLayout(""+chars[i],tmp,new FontRenderContext(new AffineTransform(),true,true));
			g.setFont(tmp);
			g.setColor(this.colors[colorInx]);
			g.setTransform(current);
			if(tl.getBounds().getHeight()>2)tl.draw(g, -Math.round(tl.getBounds().getMinX()), -Math.round(tl.getBounds().getMaxY()));
			if(colorInx == this.colors.length-1){
				colorInx = 0;
			}
			else{
				colorInx++;
			}
		}
		m = (AffineTransform) mainTransform.clone();
		g.setFont(new Font(Font.MONOSPACED,Font.PLAIN,8));
		m.concatenate(AffineTransform.getTranslateInstance(pos*this.WIDTH,  this.HEIGHT - this.TEXT_PART+7));
		g.setTransform(m);
		g.setColor(Color.BLACK);
		String l = ""+this.positions[pos];
		int x = (20-l.length()*5)/2;
		g.drawString(l, x,0);
		g.setFont(FONT);
	}
	
	private void setMainTransform(){
		int h = this.getHeight(), w = this.getWidth();
		if(isImage){
			w = this.realWidth;
			h = this.realHeight;
		}
		mainTransform = AffineTransform.getScaleInstance((double)w/(this.WIDTH*absolutePositions.length), (double)h/this.HEIGHT);
	}
}
