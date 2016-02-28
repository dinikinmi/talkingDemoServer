import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Global.GlobalVar;


public class ChildRequestHandler extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public ChildRequestHandler() {
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
			throws ServletException, IOException {}



	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
        Long wantedId=Long.valueOf(request.getParameter("wantedId"));
		
		String selectChild="select * from relationship where supperClass="
				+wantedId+";";
		try {
		  ResultSet Rs=GlobalVar.st.executeQuery(selectChild);
		  String Reply="";
		
		  if(Rs.next()&&!Rs.isClosed())
		  {Reply=GlobalVar.ResultSet_JSON(Rs);
		   out.print(Reply);
		   System.out.println(Reply);
		  }else
		  {out.print("18028628619");}// wantedId has no child class
		}
		catch (SQLException e) 
		  {e.printStackTrace();out.print("462323679");}
		    catch(Exception e){out.print("462323679");}
			
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

}
