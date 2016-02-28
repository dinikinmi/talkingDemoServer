package SocketExtrangePackage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import Global.ReceptionServerSocket;

import com.mysql.jdbc.log.Log;


public class Loop_ChatObjectRequest 
{   Socket sk;
	public ChatObjAryObj coAryObj;
    public ArrayList<Integer> rowToDelAry;
    public DataInputStream dataInps;
    public DataOutputStream dataOps;
    public ObjectOutputStream objOps;
	
    public Loop_ChatObjectRequest(Socket sk,Long requestorId)
	{this.sk=sk;}
 
	public void startService()
	{System.out.println("co loop run");
	While: while(!sk.isClosed())
	{System.out.println("co loop run,socket is open");
	try
	 {
	if(tellClientSendId())
	{Long requestorId=getRequestorId();
	System.out.println("requestorId from client end is "+requestorId);
    if(requestorId>0)
    messageLoop(requestorId);  
    else
     {try{sk.close();}catch(Exception e)
     {break While;}
    }
	}
	 }catch(Exception e)
	{break While;}
	}
	} 
     	
private void getIOStreamReady() throws IOException
 {dataInps=new DataInputStream(sk.getInputStream());
  dataOps=new DataOutputStream(sk.getOutputStream());
  objOps=new ObjectOutputStream(sk.getOutputStream());
 }


private long getRequestorId() throws IOException
 {  Long requestorId=0L;
	System.out.println("waitting for the client to give me Id");
//DataInputStream dataInps=new DataInputStream(sk.getInputStream()); 
   dataInps=new DataInputStream(sk.getInputStream()); 
   requestorId=dataInps.readLong(); 
   System.out.println("got requestorId finished");
   return requestorId;
 }


private boolean tellClientSendId() throws IOException
  {
//DataOutputStream dataOps=new DataOutputStream(sk.getOutputStream());
    dataOps=new DataOutputStream(sk.getOutputStream()); 
	dataOps.writeInt(1);
	System.out.println("tell client to give me the Id");

	return true;   
    }


private void messageLoop(Long requestorId)
{
 ResultSet unreadMsgRs=selectUnreadMsg(requestorId);
 coAryObj=new ChatObjAryObj();
 rowToDelAry=new ArrayList<Integer>(); 
 
 try{
 loadRs_To_chatOAL(unreadMsgRs);	 
 sendChatArray(coAryObj);
 String clientResponseMsg=getClientResponse();
 if(clientResponseMsg.equals("got it,you can delete"));
 deleteRow_chatting();
 if(clientResponseMsg.equals("the size is 0"))
  return;
 }catch(Exception e){return;}
 } 

private void loadRs_To_chatOAL(ResultSet unreadMsgRs) throws Exception 
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
	 rowToDelAry.add(chatObj.row_In_Server);	
		}
	  }
  
 
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

private void sendChatArray(ChatObjAryObj chatOAL)
 {try
   {
//  ObjectOutputStream objectOps=new ObjectOutputStream(sk.getOutputStream());
  objOps.writeObject(chatOAL);
   }catch(Exception e){}
 }

private String getClientResponse()
{ String clientResponseMsg="";
	try {
	clientResponseMsg=dataInps.readUTF();
	return clientResponseMsg;
	} catch (IOException e) 
	{e.printStackTrace();}
	return clientResponseMsg;
	
}

private void deleteRow_chatting() 
{  for(int i=0;i<(rowToDelAry.size()-1);i++)
	{String delRow="delete from chatting where rowId=" +
			rowToDelAry.get(i)+";";
	try
	{
	Statement st=ReceptionServerSocket.sqlConnection.createStatement();
	st.execute(delRow);
	}catch(Exception e)
	{}
	}
}

}
