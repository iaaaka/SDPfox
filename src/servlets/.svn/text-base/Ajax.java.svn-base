package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Objects.*;
import java.io.*;

/**
 * Servlet implementation class for Servlet: Ajax
 *
 */
 public class Ajax extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public Ajax() {
		super();
	}   	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}  	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Project project = (Project)request.getSession().getAttribute("project");
		project.moveSequences(Integer.parseInt(request.getParameter("newGr")) , request.getParameter("seqs"));
	    PrintWriter pw = response.getWriter();
	    pw.println(project.getSDPs());
	    pw.println(project.getSDPIntervals());
	    pw.flush();
	    pw.close();
	}   	  	    
}