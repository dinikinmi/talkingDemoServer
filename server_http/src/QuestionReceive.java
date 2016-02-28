import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




public class QuestionReceive extends HttpServlet {
    private boolean successFlag=true;
    String sqlURL="jdbc:mysql://127.0.0.1:3306/eng_app";
    String sqlUserName="root";
    String sqlPSW="";
    private String contentAbbr="";
	/**
	 * Constructor of the object.
	 */
	public QuestionReceive() {
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

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
        System.out.println("1232");
        System.out.println("tr");
        response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String questionId=request.getParameter("questionId");
		String fromId=request.getParameter("fromId");
		String fromUserName=request.getParameter("fromUserName");
		String Content=request.getParameter("Content");
	    String Title=request.getParameter("Title");
	    System.out.println("tr2");
	    boolean abbrFlag=false;
	    if(Content.length()>30)
	    {this.contentAbbr=Content.substring(0,29)+".....";
	     abbrFlag=true;}
	    else{this.contentAbbr=Content;}
	    
	    try{
	    Class.forName("com.mysql.jdbc.Driver");
	    }catch(ClassNotFoundException e)
	    {successFlag=false;}
	  
	    try
	    {
	    Connection conn=DriverManager.getConnection(sqlURL,sqlUserName,sqlPSW);
	    java.sql.Statement st=conn.createStatement();
	    String insert="";
	    String insert_2="";
	    System.out.println("tr3");
	    if(abbrFlag=true)
	    { insert="insert into questions (questionId,fromId,fromUserName,Title,Content,contentAbbr,abbrFlag)" +
	    		"values ("+Long.valueOf(questionId)+","+Long.valueOf(fromId)+",'"+fromUserName+"','"+Title+"','"+Content+"','"+this.contentAbbr+
	    		"',true);";
	      
	      insert_2="insert into questions_2 (questionId,fromId,fromUserName,Title,Content,contentAbbr,abbrFlag)" +
	    		"values ("+Long.valueOf(questionId)+","+Long.valueOf(fromId)+",'"+fromUserName+"','"+Title+"','"+Content+"','"+this.contentAbbr+
	    		"',true);";
	      
	    }else
	    {insert="insert into questions (questionId,fromId,fromUserName,Title,Content,contentAbbr,abbrFlag)" +
	    		"values ("+Long.valueOf(questionId)+","+Long.valueOf(fromId)+",'"+fromUserName+"','"+Title+"','"+Content+"','"+this.contentAbbr+
	    		"',false);";
	    
	    insert_2="insert into questions_2 (questionId,fromId,fromUserName,Title,Content,contentAbbr,abbrFlag)" +
	    		"values ("+Long.valueOf(questionId)+","+Long.valueOf(fromId)+",'"+fromUserName+"','"+Title+"','"+Content+"','"+this.contentAbbr+
	    		"',false);";
	      
	    }
	    
	    
	    System.out.println("tr4");
	    
	    st.execute(insert);
	    st.execute(insert_2);
	    
	    System.out.println("tr5");
	    }catch(SQLException e)
	    { e.printStackTrace();
	      successFlag=false;
	    }
	    
	    if(successFlag=false)
	    {out.print("1");}
	    else
	    {out.print("462323679");}
	    
	    out.flush();
	    out.close();
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException 
	{
		// Put your code here
	}

}
