import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Global.GlobalVar;

import com.mysql.jdbc.ResultSet;


public class SupperClassRequestHd extends HttpServlet {
    Long Id;
	/**
	 * Constructor of the object.
	 */
	public SupperClassRequestHd() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
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
		
		Id=Long.valueOf(request.getParameter("userId"));
		java.sql.ResultSet Rs=getSelectResult();
		try 
		{if(Rs.next())
		  {String sendData=GlobalVar.ResultSet_JSON(Rs);
		   out.print(sendData);
		   System.out.print(sendData);
		  }else
		    {out.print("18028628619");}//no supper class
		} catch (SQLException e) 
		  {e.printStackTrace();out.print("462323679");}
		  catch(Exception e){out.print("462323679");};
		
		
		
		out.flush();
		out.close();
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}
	
	public java.sql.ResultSet getSelectResult()
	 {
	
		String selectSC="select * from relationship where "
				+"childClass="+Id;
		try {
			java.sql.ResultSet Rs=GlobalVar.st.executeQuery(selectSC);
		    return Rs;
		} catch (SQLException e) 
		{  e.printStackTrace();
			return null;
		}
	 }

}
