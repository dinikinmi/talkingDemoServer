import java.io.IOException;
import java.io.PrintWriter;
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


public class AnswerListRequest extends HttpServlet {
    public Statement st;
    String sqlURL="jdbc:mysql://127.0.0.1:3306/eng_app";
    String sqlUserName="root";
    String sqlPSW="";
    String answerListJS;
    public boolean successFlag=true;
    /**
	 * Constructor of the object.
	 */
	public AnswerListRequest() {
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
			throws ServletException, IOException 
			{}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
        
		Long questionId=Long.valueOf(request.getParameter("questionId"));
		String selectAnswerList="select * from answers where parentId="+questionId;
	    try 
	    {ResultSet answerListResult=st.executeQuery(selectAnswerList);
	     answerListJS=resultSet_JSON(answerListResult);
	     System.out.print(answerListJS);
	    } catch (SQLException e) 
		{successFlag=false;
		e.printStackTrace();}
		
	    if(successFlag==false)
	    {out.print("2");}
	    if(successFlag==true)
	    {out.print(answerListJS);}
				
		out.flush();
		out.close();
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException
	{try {
	System.out.println("answerListRequest");
	Class.forName("com.mysql.jdbc.Driver");
	java.sql.Connection conn=DriverManager.getConnection(sqlURL,sqlUserName,sqlPSW);
	st=conn.createStatement();
	}catch(Exception e )
	{}
	}
	
	
    public String resultSet_JSON(ResultSet answerListResult)
    { boolean rsB;
    ArrayList<Map<String,String>> questionsArray=new ArrayList<Map<String,String>>();
    String answerListJS = null; 
    try{
    if((rsB=answerListResult.next())==true)
    {  for(answerListResult.first();answerListResult.isAfterLast()==false;answerListResult.next())
         {Map<String,String> map=new HashMap<String,String>();
    	  map.put("questionId",""+answerListResult.getLong("parentId"));
    	  map.put("questionTitle",answerListResult.getString("parentTitle"));
    	  map.put("answerId",""+answerListResult.getLong("answerId"));
//    	  System.out.println(""+answerListResult.getLong("questionId"));
          map.put("fromId",""+answerListResult.getLong("fromId"));
          map.put("fromUserName",answerListResult.getString("fromUserName"));
//          map.put("Title",answerListResult.getString("Title"));
          map.put("contentAbbr",answerListResult.getString("contentAbbr"));
          map.put("abbrFlag",""+answerListResult.getBoolean("abbrFlag"));
    
//          map.put("Credit",""+answerListResult.getInt("Credit"));
//          map.put("Sort",answerListResult.getString("Sort"));
//          map.put("rowId",""+answerListResult.getInt("rowId"));
          questionsArray.add(map);
         }
          JSONArray J_send=JSONArray.fromObject(questionsArray);
    	  answerListJS=J_send.toString();	
    	  
    	  System.out.println("123"+answerListJS+"123");
      }
    }catch(SQLException e)
          {e.printStackTrace();
          successFlag=false; 
          }
     return answerListJS;
    	  }
	

}
