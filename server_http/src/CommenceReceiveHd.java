import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class CommenceReceiveHd extends HttpServlet {
    Long comPosterId;
    Long answerId;
    Long ansPosterId;
    String comPosterName;
    String comContent;
    String ansAbbr;
    HttpServletRequest request;
	/**
	 * Constructor of the object.
	 */
	public CommenceReceiveHd() {
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
        this.request=request;
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
        System.out.println("commence Receive");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		this.request=request;
		getParameter();
		
		insertCommenceTable();
		insertNotification();
		
		
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

	private void getParameter()
	{comPosterId=Long.valueOf(request.getParameter("comPosterId"));
	 answerId=Long.valueOf(request.getParameter("answerId"));
	 ansPosterId=Long.valueOf(request.getParameter("ansPosterId"));
	 comContent=request.getParameter("commenceText");
	 ansAbbr=request.getParameter("ansAbbr");
	 comPosterName=request.getParameter("comPosterName");
	}
	private boolean insertCommenceTable()
	{String insert="insert into commence " +
			"(comPosterId,answerId,ansPosterId,comContent,comPosterName)"
		+ " values ("+comPosterId+","+answerId+","+ansPosterId+",'"+comContent+"','"+comPosterName+"');";
		try
		{
	    Global.GlobalVar.st.execute(insert);
		}catch(SQLException sqle)
		{sqle.printStackTrace();
		 return false;
		}catch(Exception e){return false;}
		
		return true;
	}
	
	private boolean insertNotification()
	// the Conetne shall input the Abrreviated Content of oject Answer 
	{String insert="insert into notifications " +
			"(Type,Content,objectId," +
			"toId,fromUserName,fromId)"
              +"values("+1+",'"+ansAbbr+"',"+answerId+","
			+ansPosterId+",'"+comPosterName+"',"+comPosterId+");";
	try{Global.GlobalVar.st.execute(insert);}
	   catch(SQLException se){se.printStackTrace();return false;}
	   catch(Exception e){return false;}
	
	return true;
	}
	
	
}
