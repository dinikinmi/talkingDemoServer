import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Connection;


public class RegistReceive extends HttpServlet {
    public Statement st;
    public int handleResult;
    public boolean successFlag=true;
    
	/**
	 * Constructor of the object.
	 */
	public RegistReceive() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the GET method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
	    try{        
		Long Id=Long.valueOf(request.getParameter("userId"));
		String userName=request.getParameter("userName");
		String Password=request.getParameter("password");
			
		try 
		{ String checkId="select Id from user_details where Id="+Id+";";
		  ResultSet rs=st.executeQuery(checkId);
         //check Id
		  if(rs.next())//if the id already exists		
	      {out.write("462323679");return;}
         //check username
		  String userNameCheck="select userName from user_details where userName='"
		                      +userName+"';";
	      rs=st.executeQuery(userNameCheck);
		  if(rs.next())
		 {out.write("18028628619");return;}//if user_name is seized;
	     }catch (SQLException e){e.printStackTrace();out.write("462323679");}
	     catch(Exception e){out.write("462323679");return;}
	     //start to insert
		 String insert="insert into user_details (Id,userName,Password,Credit)" +
		"values("+Id+",'"+userName+"','"+Password+"',0);";
		 try
		 {
		 st.execute(insert);
		 out.print("330346275");
		 }catch(Exception e){out.print("462323679");return;}
		
		out.flush();
		out.close();
   }catch(Exception e){out.print("462323679");}
	
	}


	public void init() throws ServletException {
	try{
		Class.forName("com.mysql.jdbc.Driver");
	}catch(ClassNotFoundException cnf){cnf.printStackTrace();}
	   try
	   {
	    java.sql.Connection conn=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/eng_app","root","");
	   st=conn.createStatement();
	    } catch (SQLException e)
	    {e.printStackTrace();}  
	 System.out.print("init");
	  
	}

}
