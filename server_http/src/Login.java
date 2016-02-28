import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import Global.GlobalVar;


public class Login extends HttpServlet {
    public Statement st;
	/**
	 * Constructor of the object.
	 */
	public Login() {
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
}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		String userName=request.getParameter("userName");
		String Password=request.getParameter("Password");
		String checkLogin="select Id from user_details where userName='"
				+userName+"' and Password='"+Password+"';";
		try {
			ResultSet rs=st.executeQuery(checkLogin);
		    if(rs.next())
		    {String toSend=Rs_Json(rs);
		     out.print(toSend); 
		     
		    }
		} catch (SQLException e) {e.printStackTrace();}
		  catch(Exception e){out.write("462323679");}
		
		
		out.flush();
		out.close();
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException 
	{	// Put your code here;
		try{Class.forName("com.mysql.jdbc.Driver");
		Connection conn=DriverManager.getConnection(GlobalVar.sqlURL,GlobalVar.sqlUserName,GlobalVar.sqlPSW);
	    st=conn.createStatement();
		}catch (SQLException e) {e.printStackTrace();}
		 catch(ClassNotFoundException c){c.printStackTrace();}
		
	}
    
	public String Rs_Json(ResultSet Rs) throws SQLException
	{   ArrayList<Map<String,String>> al=new ArrayList<Map<String,String>>();
	    Map<String,String> map=new HashMap<String,String>();
	    map.put("successFlag","330346275");
	    map.put("Id",""+Rs.getLong("Id"));
	    al.add(map);
	    JSONArray ja=JSONArray.fromObject(al);
	    String returnString=ja.toString();
	    return returnString;
		
		
		
	}
	
}
