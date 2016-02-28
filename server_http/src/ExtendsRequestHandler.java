import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Global.GlobalVar;


public class ExtendsRequestHandler extends HttpServlet {
    public String relationshipCheckRs;
    public int Response;
    public boolean insertRelationship;
    public String superClassName;
    public String childClassName;
    public Long superClassId;
    public Long childClassId;
    
	/**
	 * Constructor of the object.
	 */
	public ExtendsRequestHandler() {
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
			throws ServletException, IOException {}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
	    superClassId=Long.valueOf(request.getParameter("addedId"));
	    childClassId=Long.valueOf(request.getParameter("requestorId"));
	    superClassName=request.getParameter("supperClassName");
	    childClassName=request.getParameter("childClassName");
	    //check wehter beAddedId is requestor's direct supper class
	    String checkDirectUpper="select supperClass from relationship where childClass="+
	    		   childClassId+";";
	    try {//get my supperClass;
		ResultSet directRs=GlobalVar.st.executeQuery(checkDirectUpper);
		if(directRs.next())
	    {insertRelationship=false;//if there is already a record in table,don't insert new record
		 Long getDirectId=directRs.getLong("supperClass");
	     Long mins=getDirectId-superClassId;
	        if(mins!=0)//if my now superClass is not the user i intent to extends 
	        {
	        Response=checkRelationShip(childClassId,superClassId);
	        }else
	        {//if mins=0,means the user i intent to extends from is already my supperClass
	        Response=3;
	        }
	    } 
		else//if i don't have a supperClass
		{   insertRelationship=true;//if no record of mine,insert new one 
			Response=checkRelationShip(childClassId,superClassId);
		}
	    }catch (SQLException e1)
		  {e1.printStackTrace();Response=2;}
	    catch(Exception e)
	      {System.out.print("111"); Response=2; }
	    
	    
	    System.out.print(Response);
	    switch(Response)
	    {case 0:
	     	try{if(insertRelationship)
	    	{String relationshipOperation="insert into relationship (supperClass,childClass,supperClassName,childClassName)" +
	    			" values" +
	    			"("+superClassId+","+childClassId+",'"+superClassName+"','"+childClassName +  "');";
	    	 GlobalVar.st.execute(relationshipOperation);
	    	
	    	 String updateUserDetails="update user_details set superClassId=" +
	    	 		superClassId+" , superClassName='"+superClassName+"' where Id=" +
	    	 				childClassId+";";
	    	 GlobalVar.st.execute(updateUserDetails);
	    	 
	    	 out.print("330346275");//it's clear;;
	    	 }else
	    	 {String relationshipOperation="update relationship set supperClass="
	    	 + superClassId +  ",supperClassName='"  + superClassName+ "' where childClass="+childClassId+";";
		     GlobalVar.st.execute(relationshipOperation);
		     out.print("330346275");//it's clear;;
	    	 }   	   
	    	   }catch(SQLException sqle){sqle.printStackTrace();out.print("18028628619");}
	    	    catch(Exception e){out.print("18028628619");}
	    	break;
	    
	    case 1:
	    	out.print("462323679");//the user to be added is in the children branch
	        break;
	    case 2:
	    	out.print("18028628619");//Exception .unknownError
	        break;
	    case 3://already your teacher
	    	out.print("13580578945");break;
	    }
		
		out.flush();
		out.close();
	}

	public void init() throws ServletException
	{// Put your code here
	}
	
	public int checkRelationShip(Long childClassId,Long superClassId)
	 { 
//		int Response=2;//0 for clear,1 for "it is your childlen;;2 for Exception"
	   try{//check wether the toAddedId is Directly supper class of RequestorId
       String getSuperClass="select supperClass from relationship where childClass="+
	   superClassId+";";
	   ResultSet Rs=GlobalVar.st.executeQuery(getSuperClass);
	   if(!(Rs.next()))
	   {Response=0;return Response;}
	   Long supperClassId=Rs.getLong("supperClass");
	   if((supperClassId-childClassId)==0)
	       {Response=1;
	        System.out.print(Response+"  digui");
	        return Response;
        }
	    checkRelationShip(childClassId,supperClassId);
		}catch(SQLException sqlEx){sqlEx.printStackTrace();return Response;} 
	     catch(Exception e){return Response;}
	     return Response;//this line will never be run.....since all exceptions are caught uppper;
	 }
}
