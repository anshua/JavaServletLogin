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
 * Servlet implementation class dispGrades
 */
@WebServlet("/dispGrades")
public class dispGrades extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public dispGrades() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		String uname = session.getAttribute("uname").toString();
//		
		System.out.println(uname);
		try(
    	    Connection conn = DriverManager.getConnection(
    	    		"jdbc:postgresql://localhost:5770/postgres", "labuser", "");
	    	Statement stmt = conn.createStatement();
    		){
	    	conn.setAutoCommit(false);
	    	ResultSet rset;
	    	rset = stmt.executeQuery("select t.course_id, c.title, t.sec_id, t.semester, t.year, t.grade from takes t, course c where id = '" + uname + "' and c.course_id = t.course_id");
	    	ResultSetMetaData rsmd = rset.getMetaData();
	    	int x = 1;
	    	String result = "<tr>";
	    	while(x <= rsmd.getColumnCount()) {
	    		result = result + "<th> " + rsmd.getColumnName(x) + "</th>";
	    		x++;
	    	}
	    	result = result + "</tr>";
	    	while(rset.next()) {
	    		result = result + "<tr>";
	    		for(int i = 1; i <= rsmd.getColumnCount(); i++) {
	    			result = result + "<td>";
	    			result = result + rset.getString(i);
	    			result = result + "</td>";
	    		}
	    		result = result + "</tr>";
	    	}
	    	out.println("<table>" + result + "</table>");
	    }
	    catch(Exception e) {
	    	e.printStackTrace();
	    }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
