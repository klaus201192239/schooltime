package coreservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.bson.Document;
import org.bson.types.ObjectId;
import staticData.StaticString;
import bean.TableContentInfo;
import bean.TableInfo;
import com.dbDao.CreateOrQuery;
import com.dbDao.CreateQueryFromBean;
import com.dbDao.DaoImpl;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCursor;


public class quitmyactivity extends HttpServlet {

	private static final long serialVersionUID = 1L;


	public quitmyactivity() {
		super();
	}

	public void destroy() {
		super.destroy(); 
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		String result="error";
		String userid=request.getParameter("userid");
		String activityid=request.getParameter("activityid");
		
		
		TableInfo table=new TableInfo();
		table.setActivityId(new ObjectId(activityid));

		
		BasicDBObject pro=new BasicDBObject(StaticString.TableInfo_id,1);
		
		try{
			ArrayList<ObjectId> list=new ArrayList<ObjectId>();
			
			MongoCursor<Document> cur =DaoImpl.GetSelectCursor(TableInfo.class, CreateQueryFromBean.EqualObj(table), pro);
			while(cur.hasNext()){
				list.add(cur.next().getObjectId("_id"));
			}
			
			
			CreateOrQuery or=new CreateOrQuery();
			
			for(int i=0;i<list.size();i++){
				TableContentInfo ta=new TableContentInfo();
				ta.setStuId(new ObjectId(userid));
				ta.setTableId(list.get(i));
				BasicDBObject query=CreateQueryFromBean.EqualObj(ta);
				or.Add(query);
			}
			
			DaoImpl.DeleteDocment(TableContentInfo.class, or.GetResult());
						
			result="ok";
			
		}catch(Exception e){
			result="error";
		}		
		
		out.println(result);
		out.flush();
		out.close();
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the POST method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	public void init() throws ServletException {

	}

}
