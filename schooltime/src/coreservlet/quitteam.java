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
import com.dbDao.CreateOrQuery;
import com.dbDao.CreateQueryFromBean;
import com.dbDao.DaoImpl;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCursor;
import bean.TableContentInfo;
import bean.TableInfo;
import bean.TeamInfo;

public class quitteam extends HttpServlet {


	private static final long serialVersionUID = 1L;

	public quitteam() {
		super();
	}


	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}


	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		String result="error";
		
		String teamid=request.getParameter("teamid").toString();

		try{
			
			ArrayList<Document> listMember=new ArrayList<Document>();
			
			ObjectId acid=null;

			
			TeamInfo team=new TeamInfo();
			team.set_id(new ObjectId(teamid));
			
			BasicDBObject pro=new BasicDBObject();
			pro.put(StaticString.TeamInfo_id, 0);
			pro.put(StaticString.TeamInfo_Member, 1);
			pro.put(StaticString.TeamInfo_ActivityId, 1);

			
			MongoCursor<Document> cursor=DaoImpl.GetSelectCursor(TeamInfo.class, CreateQueryFromBean.EqualObj(team), pro);
			while(cursor.hasNext()){
				Document cd=cursor.next();
				listMember=(ArrayList<Document>)cd.get("Member");
				acid=cd.getObjectId("ActivityId");
			}
			

			TableInfo table=new TableInfo();
			table.setActivityId(acid);
			table.setType(0);
			
			BasicDBObject pro1=new BasicDBObject();
			pro1.put(StaticString.TableInfo_id, 1);
			
			ObjectId tableid=null;

			MongoCursor<Document> cursor1=DaoImpl.GetSelectCursor(TableInfo.class, CreateQueryFromBean.EqualObj(table), pro1);
			while(cursor1.hasNext()){
				Document cd=cursor1.next();
				tableid=cd.getObjectId("_id");
			}
			
			for(int i=0;i<listMember.size();i++){
				
				Document docc=listMember.get(i);
				
				TableContentInfo con=new TableContentInfo();
				con.setStuId(docc.getObjectId("StuId"));
				con.setTableId(tableid);
				
				TableContentInfo con1=new TableContentInfo();
				con1.setIdCard(docc.getString("IdCard"));
				con1.setSchoolId(docc.getObjectId("SchoolId"));
				con1.setTableId(tableid);

				
				CreateOrQuery or=new CreateOrQuery();
				or.Add(CreateQueryFromBean.EqualObj(con));
				or.Add(CreateQueryFromBean.EqualObj(con1));
				
				DaoImpl.DeleteDocment(TableContentInfo.class,or.GetResult());
				
			}
			
			DaoImpl.DeleteDocment(TeamInfo.class,CreateQueryFromBean.EqualObj(team));
			
			result="ok";
						
		}catch(Exception e){
			
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
		// Put your code here
	}

}
