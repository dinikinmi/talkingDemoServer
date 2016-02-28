package Global;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import SocketExtrangePackage.Loop_ChatObjectRequest;

public class ReceptionServerSocket
{public static Connection sqlConnection;
 public static  ServerSocket ssk;
 
 
 public static  void getConnect()
 {try{
    System.out.println("waitting for connect");
	 Socket sk=ssk.accept();
	 sk.setSoTimeout(60000);
     LoopServiceAssign loopServiceAssign=new LoopServiceAssign(sk);
     loopServiceAssign.start();
 }catch(IOException e){e.printStackTrace();}
 
 }
	 
 
 


 
 
 
public static void main(String[] arg)
 {  try{
	ssk=new ServerSocket(1889);
       }catch(IOException e){e.printStackTrace();}
	System.out.println("loop start");
	sqlConnection=getSqlConnection();
   	while(true)
   {getConnect();}
	
 }








private static Connection getSqlConnection() {
	String mySqlURL="jdbc:mysql://127.0.0.1:3306/eng_app";
	Connection sqlConnection=null;
	try
	{
	Class.forName("com.mysql.jdbc.Driver");
	sqlConnection=DriverManager.getConnection(mySqlURL);
	}catch(Exception e)
	{}
    return sqlConnection;
}

}
