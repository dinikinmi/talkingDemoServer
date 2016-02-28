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
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.beanutils.converters.StringArrayConverter;


public class QuestionListRequest extends HttpServlet {
	String sqlURL="jdbc:mysql://127.0.0.1:3306/eng_app";
    String sqlUserName="root";
    String sqlPSW="";
    public ResultSet questionResult=null;
    int i=0;
 
    
	/**
	 * Constructor of the object.
	 */
    String Offset;
	String Direction;
	int rowCount;
	String selectRowCount="select count(*) from questions_2";
	String selectFromBottom; 
    
	public QuestionListRequest() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() 
	{
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
		System.out.println("1");
		       
        response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String Offse=null;
		String Direction=null;
		int rowCount;
		String selectRowCount="select count(*) from questions_2";
		String selectFromBottom; 
		try 
		{
		Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e)
		{e.printStackTrace();}
		
		Connection conn;
		try
		{	conn = DriverManager.getConnection(sqlURL,sqlUserName,sqlPSW);
		    java.sql.Statement st=conn.createStatement();
		    String requestQuestionData="";
		     Direction=request.getParameter("Direction");
		     System.out.println(Direction);
/*	
    for(int i=0;i<50;i++)
	{
	String  insert_2="insert into questions_2 (questionId,fromId,fromUserName,Title,Content,contentAbbr,abbrFlag)" +
	"values ("+ i +",'"+ i +"','"+i+"','bbb','ddd','"+i+"',true);";
	st.execute(insert_2);
	} 
*/	
    	 switch(Integer.valueOf(Direction))
		    {case 0:
		    	{
		    	questionResult=selectFromBottom(st);
		    	break;
		    	}
		     case 1:
		         { int Offset;
		           Offset=Integer.valueOf(request.getParameter("Offset"));
		           questionResult=selectUpWard(st,Offset);
		           break;
		          }	
		     case 2:
		        {int Offset; 
		    	  Offset=Integer.valueOf(request.getParameter("Offset"));
		    	  questionResult=selectDownWard(st,Offset);
		    	  break;
		    	 }
		      }//switch case end	
		    System.out.println("switch end");
		    String questionJSON=resultSet_JSON(questionResult);
//		    byte[] questionJSON_byte=questionJSON.getBytes();
//		    ServletOutputStream Ops=response.getOutputStream();
		    System.out.println("2");
		    out.print(questionJSON);
//		    Ops.write(questionJSON_byte);
		    System.out.println(questionJSON);
		    out.flush();
		    out.close();
	}catch(Exception e)
	{}
	}
	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}
	public ResultSet selectFromBottom(Statement st)
	{   ResultSet questionList = null;
	    ResultSet countRs=null;
	    String bottomSelect=null;
	    System.out.println("select bottom");
		try 
		{
	     countRs=st.executeQuery(selectRowCount);
	     countRs.next();
	    int rows=countRs.getInt(1);
	    if(rows>10)
	    {
	    bottomSelect="select * from questions_2 limit "+
	    (rows-10)+",10;";
        
	    }
	    else
	     {
	    bottomSelect="select * from questions_2 ;";
	      }
	    questionList=st.executeQuery(bottomSelect);
	    } catch (SQLException e)
	     {e.printStackTrace();}
		 return questionList;
	}
	public ResultSet selectUpWard(Statement st,int Offset)
	{   ResultSet questionList=null;
		String selectUp="select * from questions_2 where rowId>"+
	   (Offset-10) +" and rowId<"+Offset+";";
	    try{
	    questionList=st.executeQuery(selectUp);
	    }catch(SQLException e)
	    {e.printStackTrace();}
		return questionList;
	}
	public ResultSet selectDownWard(Statement st,int Offset)
	    {
		ResultSet questionList=null;
		try{
		String selectDown="select * from questions_2 where rowId>"
        		+Offset+" limit 10;";
        questionList=st.executeQuery(selectDown);
		}catch(Exception e)
		{}
        return questionList;
		}
	
    public String resultSet_JSON(ResultSet questionResult)
    { boolean rsB;
    ArrayList<Map<String,String>> questionsArray=new ArrayList<Map<String,String>>();
    String questionJsonStr = null; 
    try{
    if((rsB=questionResult.next())==true)
    {  for(questionResult.first();questionResult.isAfterLast()==false;questionResult.next())
         {Map<String,String> map=new HashMap<String,String>();
    	  map.put("questionId",""+questionResult.getLong("questionId"));
    	  System.out.println(""+questionResult.getLong("questionId"));
          map.put("fromId",""+questionResult.getLong("fromId"));
          map.put("fromUserName",questionResult.getString("fromUserName"));
          map.put("Title",questionResult.getString("Title"));
          map.put("contentAbbr",questionResult.getString("contentAbbr"));
          map.put("abbrFlag",""+questionResult.getBoolean("abbrFlag"));
          map.put("Credit",""+questionResult.getInt("Credit"));
          map.put("Sort",questionResult.getString("Sort"));
          map.put("rowId",""+questionResult.getInt("rowId"));
          questionsArray.add(map);
         }
          JSONArray J_send=JSONArray.fromObject(questionsArray);
    	  questionJsonStr=J_send.toString();	


    	  System.out.println("123");

    	  System.out.println("123"+questionJsonStr+"123");
      }
    }catch(SQLException e)
          {e.printStackTrace();}
     return questionJsonStr;
    	  }
    
}

    