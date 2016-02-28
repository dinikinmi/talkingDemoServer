import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Statement;



import Global.ChatObject;
import Global.GlobalVar;


public class ChatObjectReceive extends HttpServlet {
   private boolean successFlag=true;
   String Content ;
   String msgId;
   String fromId;
   String fromUserName;
   String toId;
   int msgType;
  public  java.sql.Statement st=GlobalVar.st;;
	/**
	 * Constructor of the object.
	 */
	public ChatObjectReceive() 
	{
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
        System.out.println("Ok999999");
        PrintWriter out=response.getWriter();
        out.write(1);
		ObjectInputStream objIns=new ObjectInputStream(request.getInputStream());
		response.setContentType("int/html");
		PrintWriter out1 = response.getWriter();
		
		try
		{
		ChatObject chatObject=(ChatObject)objIns.readObject();
		out1.write((int)chatObject.fromId);
		System.out.println(chatObject.fromId);
		out1.flush();
		out1.close();
		}catch(Exception e)
		 {}
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
        System.out.println("1111");
        
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
   
        
        Content = request.getParameter("Content");
        msgId = request.getParameter("msgId");
        fromId=request.getParameter("fromId");
        fromUserName=request.getParameter("fromUserName");
        toId=request.getParameter("toId");
        msgType=Integer.valueOf(request.getParameter("msgType"));
        
        System.out.println(Content+" "+msgId+"  "+fromId+" "+fromUserName+" "+toId+" "+msgType+" ");
        
        long buildTime=System.currentTimeMillis();
        if(!checkChild())//if fromId is not sending to his childId
        	{out.print("18028628619");return;}//return this message and end program..
        try
        {Class.forName("com.mysql.jdbc.Driver");} 
        catch (ClassNotFoundException e) 
		{e.printStackTrace();
		successFlag=false;}
        String sqlURL="jdbc:mysql://127.0.0.1:3306/eng_app";
        String sqlUserName="root";
        String sqlPSW="";
        try {
			Connection conn=DriverManager.getConnection(sqlURL,sqlUserName,sqlPSW);
		    st=conn.createStatement();
		    String insert="insert into chatting (fromId,fromName,toId,msgId,Content,msgType,buildTime)"
		   	+"values("+fromId+",'"+fromUserName+"',"+toId+","+msgId+",'"+Content+"',"+msgType+","+buildTime+")";
            st.execute(insert);     
        } catch (SQLException e) 
         {e.printStackTrace();
			successFlag=false;}
         if(successFlag=true)    
        {out.print("1");}
        else
        {out.print("2");}
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
	
	public boolean checkChild()
	{String checkChild="select superClassId from user_details where " +
			"Id="+ toId +" and superClassId="+fromId+";";
	 
	  try
	  {   System.out.println(checkChild);
		  ResultSet rs=st.executeQuery(checkChild);
	    if(rs.next())//if  toId is my childClass
	    return true; 
	    else return false;
	   }catch(SQLException sqle){return false;}
	    
	}

}
