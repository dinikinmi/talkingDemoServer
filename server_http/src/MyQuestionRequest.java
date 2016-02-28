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


public class MyQuestionRequest extends HttpServlet {
	private boolean successFlag=true;
    String sqlURL="jdbc:mysql://127.0.0.1:3306/eng_app";
    String sqlUserName="root";
    String sqlPSW="";
    private String contentAbbr="";
    String questionJSON;
    

	public MyQuestionRequest() {
		super();
	}

	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		
		
		out.flush();
		out.close();
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("1");
		       
        response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String Offse=null;
		String Direction=null;
		int rowCount;
		
		try 
		{
		Class.forName("com.mysql.jdbc.Driver");
		    Connection conn;
			conn = DriverManager.getConnection(sqlURL,sqlUserName,sqlPSW);
		    java.sql.Statement st=conn.createStatement();
		    String requestQuestionData="";
		
	      Long  myId=Long.valueOf(request.getParameter("myId"));
		  String slectMyQuestion="select * from questions where fromId="+myId+";";
		  ResultSet rs=st.executeQuery(slectMyQuestion);
		    questionJSON=resultSet_JSON(rs);
            if(successFlag==true)
		    out.print(questionJSON);
            else{out.print("462323679");}
		    out.flush();
		    out.close();
	     }catch(Exception e)
	     {successFlag=false;}
	}

	public void init() throws ServletException {
		// Put your code here
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

