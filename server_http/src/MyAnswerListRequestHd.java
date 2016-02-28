import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class MyAnswerListRequestHd extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public MyAnswerListRequestHd() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

		public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
        System.out.println("MyAnswerListReuqest");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		Long ansPosterId=Long.valueOf(request.getParameter("ansPosterId"));
		ResultSet rs=selectAnsTable(ansPosterId);
		try{
		if(rs!=null && rs.next())
		{String sendData=Global.GlobalVar.ResultSet_JSON(rs);
		 out.print(sendData);
		 System.out.println(sendData);
		}
	   }catch(SQLException e){e.printStackTrace();out.print("462323679");}
	    
		
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

	private ResultSet selectAnsTable(long ansPosterId)
	{ String select="select answerId,parentId,fromId,contentAbbr," +
			"abbrFlag,rowId,fromUserName,parentTitle,quePosterId from answers where fromId="+ansPosterId+";";
	  ResultSet rs = null; 
	try
	  {rs=Global.GlobalVar.st.executeQuery(select);
//	   return rs;
	  }
	 catch(SQLException e){e.printStackTrace();}
	 catch(Exception e){e.printStackTrace();}
	  return rs;
		
	}
	
}
