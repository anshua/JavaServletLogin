import java.io.PrintWriter;
import java.sql.*;
import java.util.*;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ServletMain
 */
@WebServlet("/ServletMain")
public class ServletMain extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletMain() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//sponse.getWriter().append("Served at: ").append(request.getContextPath());
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		doGet(request, response);
//		PrintWriter out = response.getWriter(); 
//		String name = request.getParameter("name"); 
//	    out.println("<h1>" + name + "</h1>");
//		PrintWriter out = response.getWriter();
		PrintWriter out = response.getWriter(); 
		 
	    
	    out.println("<html><body>"+
	    		"<form action=\"ServletMain\" method=\"get\"> \n" + 
	    		"Username: <input type=\"text\" placeholder=\"username\" name = \"uname\"><br>\n" +
	    		"Password: <input type=\"password\" placeholder=\"password\" name = \"psw\"><br>\n" +
	    		"<input type=\"submit\" value = \"Submit\"> \n" + 
	    		"       </form> </body></html>");
	    String uname = request.getParameter("uname");
		String psw = request.getParameter("psw");
	    // Fetching data from database
	    if(psw==null || psw.equals("") || uname == null || uname.equals("")) {
	    	out.println("<h2>username or password can not be null</h2>");
	    }
	    else {
	    	try(
        	    Connection conn = DriverManager.getConnection(
        	    		"jdbc:postgresql://localhost:5770/postgres", "labuser", "");
    	    	Statement stmt = conn.createStatement();
        		){
    	    	conn.setAutoCommit(false);
    	    	ResultSet rset;
    	    	rset = stmt.executeQuery("select * from password where id = '" + uname + "'");
    	    	int i = 0;
    	    	String pswd = "";
    	    	while(rset.next()) {
    	    		pswd = rset.getString(2);
    	    		i++;
    	    	}
    	    	if(i == 0) {
    	    		out.println("<h2> no entry found</h2>");
    	    	}
    	    	else if(pswd.equals(psw)) {
    	    		out.println("<h2> Hello" + " world </h2>" );
    	    		HttpSession session = request.getSession();
    	    		session.setAttribute("uname", uname);
    	    		response.sendRedirect("home");
    	    	}
    	    	else {
    	    		out.println("<h2> Wrong password </h2>");
    	    	}
    	    }
    	    catch(Exception e) {
    	    	System.out.println("Exception : " + e);
    	    }
	    } 
	}

}
