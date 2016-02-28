package Global;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import SocketExtrangePackage.ChatObjAryObj;
import SocketExtrangePackage.ChatObject;
import SocketExtrangePackage.GeneralReqObj;

public class LoopServiceAssign extends Thread
{   Socket sk;
    public ChatObjAryObj coAryObj;
    public ArrayList<Integer> rowToDelAry_Tb_chatting;
    public ObjectOutputStream objOps;
    public ObjectInputStream objInps;
    public PublicMethod publicMethod;
    
 public LoopServiceAssign(Socket sk)
	 {this.sk=sk; }
	 
 @Override
   public void run()
 {  while(!sk.isClosed())
   {
	 try
	   { sleep(500);
		 System.out.println("select service");
		 sk.setSoTimeout(60000);//wait one minutes
	     objInps=new ObjectInputStream(sk.getInputStream());
	     objOps=new ObjectOutputStream(sk.getOutputStream());
	     GeneralReqObj requestObj=new GeneralReqObj();
	     requestObj=(GeneralReqObj)objInps.readObject();
//    DataInputStream dataInps=new DataInputStream(sk.getInputStream());
	     System.out.println("requestCode = "+ requestObj.wantedService);
	     serviceSelect(requestObj);
//    dataInps.close();
	   }
	 catch(IOException ioe)
	 {ioe.printStackTrace();
	  System.out.println("ioexception");
	  }
	 catch(Exception e)
	   {System.out.println("unknown exceptin in run()");
	    }
   }
   }
  
	 private void serviceSelect(GeneralReqObj requestObj) 
	 {
	 switch(requestObj.wantedService)
	  {case 1:
		try{
	    System.out.println("select 1");  
	    ResultSet unreadRs=selectUnreadMsg(requestObj.requestorId);
		loadRs_To_coAL(unreadRs);
		objOps.writeObject(coAryObj);
		if(objInps.readBoolean())
		publicMethod.delRowInTable(rowToDelAry_Tb_chatting, "chatting");
		}catch(Exception e)
		{}
		break;
	  }
	 }
	 
	 private ResultSet selectUnreadMsg(Long requestorId) {
			String selectUnreadMsg="select * from chatting where toId="+requestorId+";";
			Statement st;
			ResultSet selectRs=null;
			try
			{st=ReceptionServerSocket.sqlConnection.createStatement(); 
			 selectRs=st.executeQuery(selectUnreadMsg);
			 System.out.print("unread Msg ResultSet is "+selectRs.toString());
			}catch(Exception e){System.out.println("selectUnrean unknown excp");}
			 return selectRs;
			}
	 
	 private void loadRs_To_coAL(ResultSet unreadMsgRs) throws Exception 
	 {ChatObject chatObj=new ChatObject();
	    {
	 	 if(unreadMsgRs.next())
	 	 {
	 		for(unreadMsgRs.next();!unreadMsgRs.isAfterLast();unreadMsgRs.next()) 
	 		{
	 	 chatObj.Content=unreadMsgRs.getString("Content");
	 	 chatObj.Duration=unreadMsgRs.getInt("Duration");
	 	 chatObj.fromId=unreadMsgRs.getLong("fromId");
	 	 chatObj.fromUserName=unreadMsgRs.getString("fromName");
	 	 chatObj.msgId=unreadMsgRs.getString("msgId");
	 	 chatObj.msgType=unreadMsgRs.getInt("msgType");
	 	 chatObj.row_In_Server=unreadMsgRs.getInt("rowId");
	 	 chatObj.Time=unreadMsgRs.getLong("buildTime");
	 	 coAryObj.chatObjArray.add(chatObj);
	 	 rowToDelAry_Tb_chatting.add(chatObj.row_In_Server);	
	 		}
	 	  }
	    }
	 }
	 	 
	 private void deleteRow_chatting() 
	 {  for(int i=0;i<(rowToDelAry_Tb_chatting.size()-1);i++)
	 	{String delRow="delete from chatting where rowId=" +
	 	 rowToDelAry_Tb_chatting.get(i)+";";
	 	try
	 	{
	 	Statement st=ReceptionServerSocket.sqlConnection.createStatement();
	 	st.execute(delRow);
	 	}catch(Exception e)
	 	{}
	 	}
	 }
	 
}

