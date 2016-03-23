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

import bean.Member;
import bean.TableContentInfo;
import bean.TableInfo;
import bean.TeamInfo;

import com.dbDao.CreateOrQuery;
import com.dbDao.CreateQueryFromBean;
import com.dbDao.DaoImpl;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCursor;

public class teammanager extends HttpServlet {


	private static final long serialVersionUID = 1L;


	public teammanager() {
		super();
	}

	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		String result="error";
		
		String teamid=request.getParameter("teamid").toString();
		String userid=request.getParameter("userid").toString();
		String idcard=request.getParameter("idcard").toString();
		String schoolid=request.getParameter("schoolid").toString();
		int type=Integer.parseInt(request.getParameter("type").toString());
		
		Member member=new Member();
		member.setStuId(new ObjectId(userid));
		ArrayList<Member> list=new ArrayList<Member>();
		list.add(member);
		
		TeamInfo team=new TeamInfo();
		team.set_id(new ObjectId(teamid));
		
		try{
			
			if(type==0){
				
				DaoImpl.DeleteSonSomeBean(TeamInfo.class, CreateQueryFromBean.EqualObj(team), Member.class,list);
								
				
				ObjectId acid=null;
				
				BasicDBObject pro=new BasicDBObject();
				pro.put(StaticString.TeamInfo_id, 0);
				pro.put(StaticString.TeamInfo_ActivityId, 1);

				
				MongoCursor<Document> cursor=DaoImpl.GetSelectCursor(TeamInfo.class, CreateQueryFromBean.EqualObj(team), pro);
				while(cursor.hasNext()){
					Document cd=cursor.next();
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
				
				
				TableContentInfo con=new TableContentInfo();
				con.setStuId(new ObjectId(userid));
				con.setTableId(tableid);
				
				TableContentInfo con1=new TableContentInfo();
				con1.setStuId(new ObjectId("000000000000000000000000"));
				con1.setSchoolId(new ObjectId(schoolid));
				con1.setIdCard(idcard);
				con1.setTableId(tableid);
				
				CreateOrQuery or=new CreateOrQuery();
				or.Add(CreateQueryFromBean.EqualObj(con));
				or.Add(CreateQueryFromBean.EqualObj(con1));
				
				DaoImpl.DeleteDocment(TableContentInfo.class,or.GetResult());
				
				result="ok";
				
			}else{
				
				team.setMember(list);

				Member member1=new Member();
				member1.setState(1);
				ArrayList<Member> list1=new ArrayList<Member>();
				list1.add(member1);
				TeamInfo team1=new TeamInfo();
				team1.setMember(list1);
				
				DaoImpl.update(CreateQueryFromBean.EqualObj(team),team1 ,true);
				
				result="ok";
				
			}
			
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
		// Put your code here
	}

}
