package servlets;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import Math.StaticFunction;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import painter.LogoPainter;
import Objects.*;

/**
 * Servlet implementation class for Servlet: Logo
 *
 */
 public class Logo extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public Logo() {
		super();
	}   	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int width,height=400;
		Project project = (Project)request.getSession().getAttribute("project");
		width = project.getSortedForAlignmentPositionSDP().length*60;
		int grNo = Integer.parseInt(request.getParameter("grNo"));
		BufferedImage img = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		Graphics gr = img.getGraphics();
		gr.setColor(Color.WHITE);
		gr.fillRect(0, 0, width, height);
		LogoPainter painter = new LogoPainter(StaticFunction.getAcids(),project.getProfiles().getInformations(grNo),project.getProfiles().getFrequencies(grNo),project.getSortedForZscoreSDP(),width,height,gr);
		painter.paintComponent(gr);
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

	}   	  	    
}