package painter;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

class point{
	final double X, Y;
	point(double x, double y){
		X = x;
		Y = y;
	}
}

public class Graph extends JPanel {
	private final point[] POINTS;
	private final double XMIN,XMAX,YMIN,YMAX;
	private final double[] XDIVISION;
	private final double[] YDIVISION;
	public static final int YAXES_DIST = 50;
	public static final int XAXES_DIST = 30;
	private DecimalFormat f	= new DecimalFormat("####0.#####");
	
	/**
	 * 
	 * @param data Передаются точки для графика. Точки определяются так: (data[0][i],data[1][i]);
	 */
	public Graph(double[][] data){
		double XMINt,XMAXt,YMINt,YMAXt;
		POINTS = new point[Math.min(data[0].length, data[1].length)];
		XMINt = XMAXt = YMINt = YMAXt = data[0][0];
		for(int i=0;i<POINTS.length;i++){
			POINTS[i] = new point(data[0][i],data[1][i]);
			XMINt = Math.min(XMINt, data[0][i]);
			YMINt = Math.min(YMINt, data[1][i]);
			XMAXt = Math.max(XMAXt, data[0][i]);
			YMAXt = Math.max(YMAXt, data[1][i]);
		}
		XMIN = XMINt;
		YMAX = YMINt;
		XMAX = XMAXt;
		YMIN = YMAXt*1.05;
		XDIVISION = calculateDivision(XMIN,XMAX);
		YDIVISION = calculateDivision(YMAX,YMIN);
		this.setMinimumSize(new Dimension(50,50));
		this.setBackground(Color.WHITE);
	}
	
	private double[] calculateDivision(double min, double max){
		double difference = max - min;
		double division = Math.pow(10,Math.round(Math.log10(difference))-1);
		if(difference/division>=90){
			division *= 10;
		}
		if(difference/division>=50){
			division *= 5;
		}
		if(difference/division>=30){
			division *= 4;
		}
		if(difference/division>=20){
			division *= 2;
		}
		if(difference/division<=2){
			division /= 5;
		}
		if(difference/division<=4){
			division /= 4;
		}
		if(difference/division<=5){
			division /= 2;
		}
		if(division == 0.5){
			f	= new DecimalFormat("####0.0####");
		}
		if(division == 0.25){
			f	= new DecimalFormat("####0.00###");
		}
		double[] result = new double[(int)(difference/division)+1];
		result[0] = division*((int)(min/division));
		for(int j=1;j<result.length;j++){
			result[j] = result[j-1]+division;
		}	
		return result;
	}
	
	private int transformCoordinates(double min, double max, double data, int beg, int end){
		return (int)((data-min)*((double)end - (double)beg)/(max-min)+(double)beg);
	}
	
	public void paintAll(Graphics g,int width, int height){
		paintXYAxis(g,width,height);
		paintGraph(g,width,height);
	}
	
	public void paintComponent(Graphics gr){	
		paintAll(gr,this.getWidth(),this.getHeight());
	}
	
	private void paintGraph(Graphics g,int width, int height){
		g.setColor(Color.BLUE);
		for(int i=1;i<POINTS.length;i++){
			int x1 = transformCoordinates(XMIN,XMAX,POINTS[i-1].X,YAXES_DIST,width);
			int x2 = transformCoordinates(XMIN,XMAX,POINTS[i].X,YAXES_DIST,width);
			int y1 = transformCoordinates(YMIN,YMAX,POINTS[i-1].Y,XAXES_DIST,height);
			int y2 = transformCoordinates(YMIN,YMAX,POINTS[i].Y,XAXES_DIST,height);
			g.drawLine(x1, y1, x2, y2);
		}
	}
	
	private void paintXYAxis(Graphics gr,int width, int height){
		gr.setFont(new Font(Font.MONOSPACED,Font.PLAIN,14));
		gr.setColor(Color.BLACK);
		gr.drawLine(0, XAXES_DIST, width, XAXES_DIST);
		gr.drawLine(YAXES_DIST,0,YAXES_DIST,height);
		for(int i=0;i<YDIVISION.length;i++){
			int y = transformCoordinates(YMIN,YMAX,YDIVISION[i],XAXES_DIST,height);
			gr.drawLine(YAXES_DIST-5,y,YAXES_DIST,y);
			gr.drawString(f.format(YDIVISION[i]), 0, y+7);
		}
		for(int i=0;i<XDIVISION.length;i++){
			int x = transformCoordinates(XMIN,XMAX,XDIVISION[i],YAXES_DIST,width);
			gr.drawLine(x, XAXES_DIST, x, XAXES_DIST-5);
			gr.drawString(f.format(XDIVISION[i]), x-10, XAXES_DIST-15);
		}
	}
}
