package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.imageio.*;
import Objects.Project;
import painter.Graph;
import java.io.*;
import java.awt.image.*;
import java.awt.geom.*;
import java.awt.*;

/**
 * Servlet implementation class for Servlet: Plot
 *
 */
 public class Plot extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   public static final int width=700,height=500;
   
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public Plot() {
		super();
	}
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int width=700,height=500;
		Project project = (Project)request.getSession().getAttribute("project");
		BufferedImage img = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		Graphics gr = img.getGraphics();
		gr.setColor(Color.WHITE);
		gr.fillRect(0, 0, width, height);
		Graph p = project.getProbabilityGraph();
		if(p==null){
		    return;
		}
		p.paintAll(gr,width,height);
	    response.setContentType("image/png");
	    response.setDateHeader("Expires", 0);
	    response.setHeader("Pragma", "No-cache");
	    response.setHeader("Cache-Control", "no-cache, must-revalidate");
	    OutputStream os = response.getOutputStream();
	    ImageIO.write(img, "png", os);
	    os.flush();
	    os.close();

	}  	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Project project = (Project)request.getSession().getAttribute("project");
		double pos = Double.parseDouble(request.getParameter("position"));
		double thr = (double) (pos*width - Graph.YAXES_DIST)/(width-Graph.YAXES_DIST)*project.getAlinmentLength();
	    project.setThreshold((int)thr);
		PrintWriter pw = response.getWriter();
		if(project.getSortedForZscoreSDP().length==0){
			pw.print("no");
		}
		else{
	    pw.println(project.getSDPs());
	    pw.println(project.getSDPIntervals());
		}
	    pw.close();
	}   	  	    
}