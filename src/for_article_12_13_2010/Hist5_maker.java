package for_article_12_13_2010;

import java.util.HashMap;
import java.util.HashSet;

import util.dataset.CategoryDataset;
import util.dataset.DefaultCategoryDataset;
import util.gnuplot.Gnuplot;
import util.gnuplot.Histogram;

public class Hist5_maker {
	static DefaultCategoryDataset laci_pv;
	static DefaultCategoryDataset laci_s;
	static DefaultCategoryDataset gener1_s;
	static DefaultCategoryDataset gener2_s;
	
	public static void main(String[] args)throws Exception {		
		drawOldLayout(-50.);
	}
	
	
	private static void drawOldLayout(double xtic_rotate) throws Exception {
		setDatasets();
		//Gnuplot g = new Gnuplot("png font \"/var/lib/defoma/gs.d/dirs/fonts/Vera.ttf,20\" size 1280,960",true,false,false,"./");
		Gnuplot g = new Gnuplot("postscript enhanced color solid 16",true,false,true,"./");
		Histogram laci_pv_h = new Histogram(laci_pv);	
		Histogram laci_s_h = new Histogram(laci_s);
		Histogram gener1_s_h = new Histogram(gener1_s);
		Histogram gener2_s_h = new Histogram(gener2_s);	
		g.createMultiplot("hist5", (Integer) 2,(Integer) 2);
		//laci_pv_h.setLog("y");
		laci_pv_h.setYrange(0.,5.,true);
		laci_pv_h.setColors(new String[]{"yellow"});
		laci_pv_h.setGrid("y");
		laci_pv_h.setFormat("y \"10^{-%1.0f}\"");
		laci_pv_h.setMargin(0.);
		laci_pv_h.setMargin('b',7.);
		laci_pv_h.setMargin('l',4.);
		laci_pv_h.setXTicRotate(xtic_rotate);
		//laci_pv_h.setSize(0.5, 0.5);
		//laci_pv_h.setOrigin(.0, 0.5);
		
		
		String[] cols = new String[]{"blue","red"};
		laci_s_h.setColors(cols);
		laci_s_h.setXTicRotate(xtic_rotate);
		laci_s_h.setYrange(0., 1.);
		
		
		gener1_s_h.setColors(cols);
		gener1_s_h.setXTicRotate(xtic_rotate);
		gener1_s_h.setGrid("y");

		
		laci_s_h.setGrid("y");
		gener2_s_h.setColors(cols);
		gener2_s_h.setGrid("y");
		gener2_s_h.setXTicRotate(xtic_rotate);

		
		g.addToMultiplot(laci_pv_h);
		g.addToMultiplot(laci_s_h);
		g.addToMultiplot(gener1_s_h);
		g.addToMultiplot(gener2_s_h);
		g.finalizeMultiplot();		
	}
	
	private static double[] transPV(double[] d){
		double[] r = new double[d.length];
		for(int i=0;i<d.length;i++){
			if(d[i] != -1)
				r[i] = - Math.log10(d[i]);
			else
				r[i] = 0;
			//System.out.println(r[i]);	
		}
		return r;
	}
	
	private static void setDatasets() {
		String[] category_names = new String[] {"Trace Suite II","S3DET","MB-method","S-method",
				"SDPclust","SDPsite","Protein keys"};
		laci_pv = new  DefaultCategoryDataset("LacI, p-value", "", "", category_names);
		laci_pv.addDataLine(transPV(new double[] {0.01501016,-1,0.0243882,-1,
				0.00001762137,0.02170701,0.00005734044}), "");
		laci_s = new  DefaultCategoryDataset("LacI", "", "", category_names);
		laci_s.addDataLine(new double[] {0.733,0,0.128,0,0.086,0.080,0.176}, ""); //sensitivity
		laci_s.addDataLine(new double[] {0.541,0,0.036,0,0.030,0.031,0.066}, ""); //false positive rate
		
		gener1_s = new  DefaultCategoryDataset("Generated family 1", "", "", category_names);
		gener1_s.addDataLine(new double[] {1.000,1.000,1.000,0.000,1.000,1.000,1.000}, ""); //sensitivity
		gener1_s.addDataLine(new double[] {0.021,0.042,0.063,0.011,0.000,0.021,0.074}, ""); //false positive rate
		
		gener2_s = new  DefaultCategoryDataset("Generated family 2", "", "", category_names);
		gener2_s.addDataLine(new double[] {1.000,0.000,0.300,0.000,1.000,0.000,0.000}, ""); //sensitivity
		gener2_s.addDataLine(new double[] {0.089,0.074,0.089,0.026,0.000,0.016,0.126}, ""); //false positive rate
	}
}
