package Global;

import java.sql.Statement;
import java.util.ArrayList;

public class PublicMethod 
{
	public void delRowInTable(ArrayList<Integer> rowToDelAry,String tableName)
	{ for(int i=0;i<(rowToDelAry.size()-1);i++)
	{String delRow="delete from "+tableName+" where rowId=" +
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

