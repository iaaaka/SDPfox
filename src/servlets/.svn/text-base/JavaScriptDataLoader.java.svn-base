package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Objects.Project;

/**
 * Servlet implementation class for Servlet: JavaScriptDataLoader
 *
 */
 public class JavaScriptDataLoader extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public JavaScriptDataLoader() {
		super();
	}   	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Project project = (Project)request.getSession().getAttribute("project");
		PrintWriter pw = response.getWriter();
	    pw.println("allNew();");
		pw.println("sdp = ["+project.getSDPs()+"];");
		pw.println("sdpIntervals = "+project.getSDPIntervals()+";");
		pw.println("delimiter = '"+project.generateAligmentNumberString(20)+"';");
		for(int i=0;i<project.getGroupCount();i++){ 
			pw.println("addGroup('"+project.getGroupName(i)+"');");
		}
		for(int i=0;i<project.getSeqCount();i++){
			pw.println("addSequence('"+project.getSeqName(i)+"','"+project.getSequence(i)+"','"+project.getSequenceGroup(i)+"');");
		}
	    pw.flush();
	    pw.close();
	}  	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}   	  	    
}