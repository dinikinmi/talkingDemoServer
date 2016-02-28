package Global;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;

public class GlobalVar {

    public static String sqlURL="jdbc:mysql://127.0.0.1:3306/eng_app";
    public static String sqlUserName="root";
    public static String sqlPSW="";
    public static Statement st;
    public static Connection conn;
 
    public static String ResultSet_JSON(ResultSet rs) throws SQLException
    {rs.beforeFirst();
     ArrayList<Map<String,String>> al=new ArrayList<Map<String,String>>();
     ResultSetMetaData rsd= rs.getMetaData();
  
     int columnCount=rsd.getColumnCount();
    
     for(rs.next();!(rs.isAfterLast());rs.next())
     {Map<String,String> map=new HashMap<String,String>(); 	 
       for(int i=1;i<(columnCount+1);i++)
     { map.put(rsd.getColumnName(i),rs.getString(i));}
       al.add(map); 
     }
     JSONArray ja=JSONArray.fromObject(al);
     return ja.toString();}
    
}
