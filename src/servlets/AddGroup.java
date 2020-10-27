package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import Exception.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Objects.Project;

/**
 * Servlet implementation class for Servlet: AddGroup
 *
 */
 public class AddGroup extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public AddGroup() {
		super();
	}   	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Project project = (Project)request.getSession().getAttribute("project");
	    PrintWriter pw = response.getWriter();
	    int i=-1;
		try{
		i = project.addNewGroup(request.getParameter("newGrName"));
		}catch(NumeratorException e){
			pw.print("bad name");
		    pw.flush();
		    pw.close();
		    return;
		}
	    pw.print(i);
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