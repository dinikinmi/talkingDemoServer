import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




public class AnswerReceive extends HttpServlet {
	String sqlURL="jdbc:mysql://127.0.0.1:3306/eng_app";
    String sqlUserName="root";
    String sqlPSW="";
    String contentAbbr="";
    String questionTitle="";
    String fromUserName="";
    Long questionId;
    
	Long answerId;
	Long fromId;
	Long toId;
	Long askerId;
	String answerContent;
	boolean abbrFlag;
	boolean successFlag=true;
	Statement st;
	
	/**
	 * Constructor of the object.
	 */
	public AnswerReceive() 
	{
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
        System.out.println("AR");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		questionTitle=request.getParameter("questionTitle");
		questionId=Long.valueOf(request.getParameter("questionId"));
		questionTitle=request.getParameter("questionTitle");
		answerId=Long.valueOf(request.getParameter("answerId"));
		fromId=Long.valueOf(request.getParameter("fromId"));
		fromUserName=request.getParameter("fromUserName");
		askerId=Long.valueOf(request.getParameter("askerId"));
		answerContent=request.getParameter("answerContent");
		abbrFlag=false;
	    
		if(answerContent.length()>30)
		 {
		 this.contentAbbr=answerContent.substring(0,29)+".....";
		 abbrFlag=true;
		 }
		else{this.contentAbbr=answerContent;}
		try
		{
	    insertAnswers();
	    insertNotification();
		}catch(SQLException e)
		{successFlag=false;
		 e.printStackTrace();}
		
		if(successFlag==true)
		{out.write("1");		
		}else
		{if(successFlag=false)
			{out.write("2");}
		}
		
		
		
		out.flush();
		out.close();
	    }
      
	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException{
		try {
			System.out.println("answerReceive");
			Class.forName("com.mysql.jdbc.Driver");
		    java.sql.Connection conn=DriverManager.getConnection(sqlURL,sqlUserName,sqlPSW);
		  st=conn.createStatement();
		}catch(Exception e )
		{}
	}
	
	private void insertAnswers() throws SQLException
	{String insert="insert into answers (answerId," +
			"parentId,fromId,Content,contentAbbr,abbrFlag,fromUserName,parentTitle,quePosterId)" +
			"values("+answerId+","+questionId+","+fromId+",'"+answerContent
			+"','"+contentAbbr+"',"+abbrFlag+",'"+fromUserName+"','"+questionTitle+"',"+askerId +");";
	 st.execute(insert);
	 }
	private void insertNotification() throws SQLException
	{String insert="insert into notifications (Type,Content,readLabel," +
			"objectId,toId) values (0,'"+questionTitle+"',0,"+questionId+","+askerId+
			");";
	System.out.println(insert);
	 st.execute(insert);	
	}
    
	
}
