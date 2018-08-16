import java.sql.*;
import java.util.*;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class home
 */
@WebServlet("/home")
public class home extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public home() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		String uname = session.getAttribute("uname").toString();
//		
//		System.out.println(uname);
		out.println("<h1> Hello " + uname + "</h1>");
		try(
    	    Connection conn = DriverManager.getConnection(
    	    		"jdbc:postgresql://localhost:5770/postgres", "labuser", "");
	    	Statement stmt = conn.createStatement();
    		){
	    	conn.setAutoCommit(false);
	    	ResultSet rset;
	    	rset = stmt.executeQuery("select dept_name from student where id = '" + uname + "'");
	    	int i = 0;
	    	String department = "";
	    	while(rset.next()) {
	    		department = rset.getString(1);
	    		i++;
	    	}
	    	if(i == 1) {
	    		out.println("<h2>" + uname + " " + department + "</h2>");
	    		out.println("<h4><a href = \"http://localhost:8080/Assignment5/dispGrades\">displayGrades </a></h4>");
	    	}
	    	else {
	    		rset = stmt.executeQuery("select dept_name from instructor where id = '" + uname + "'");
	    		while(rset.next()) {
		    		department = rset.getString(1);
		    	}
	    		out.println("<h2>" + uname + " " + department + "</h2>");
	    		out.println("<h2> Instructor  </h2>");
	    	}
	    }
	    catch(Exception e) {
	    	e.printStackTrace();
	    }
//		out.println("<form action=\"logout\" method=\"post\"> " + 
//				"<input type=\"submit\" value = \"Logout\"> " +  
//				"</form>");
		out.println("<button> <a href = \"http://localhost:8080/Assignment5/logout\" style=\"text-decoration:none\"> Logout </a></button>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
