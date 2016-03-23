package coreservlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.json.JSONArray;

import staticData.StaticString;

import com.dbDao.CreateOrQuery;
import com.dbDao.CreateQueryFromBean;
import com.dbDao.DaoImpl;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCursor;

import bean.TableContentInfo;
import bean.TableInfo;

public class getmyactivityid extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public getmyactivityid() {
		super();
	}

	public void destroy() {
		super.destroy();
	}

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		String result="error";
		String userid=request.getParameter("userid");
		String stuid=request.getParameter("stuid");
		String schoolid=request.getParameter("schoolid");
		
		TableContentInfo ta1=new TableContentInfo();
		ta1.setStuId(new ObjectId("000000000000000000000000"));
		ta1.setIdCard(stuid);
		ta1.setSchoolId(new ObjectId(schoolid));
		ta1.setTableType(0);
		
		TableContentInfo ta2=new TableContentInfo();
		ta2.setStuId(new ObjectId(userid));
		ta2.setTableType(0);
		
		CreateOrQuery query=new CreateOrQuery();
		CreateOrQuery que=new CreateOrQuery();
		
		BasicDBObject pro=new BasicDBObject();
		pro.put(StaticString.TableContentInfo_id, 0);
		pro.put(StaticString.TableContentInfo_TableId, 1);
		
		BasicDBObject pro1=new BasicDBObject();
		pro1.put(StaticString.TableInfo_id, 0);
		pro1.put(StaticString.TableInfo_ActivityId, 1);
		
		JSONArray array=new JSONArray();
		
		try{
			query.Add(CreateQueryFromBean.EqualObj(ta2));
			query.Add(CreateQueryFromBean.EqualObj(ta1));
			
			MongoCursor<Document> cursor= DaoImpl.GetSelectCursor(TableContentInfo.class, query.GetResult(), pro);
			while(cursor.hasNext()){
				Document doc=cursor.next();
				TableInfo ta=new TableInfo();
				ta.set_id(doc.getObjectId("TableId"));				
				que.Add(CreateQueryFromBean.EqualObj(ta));
			}
			
			
			cursor= DaoImpl.GetSelectCursor(TableInfo.class, que.GetResult(), pro1);
			while(cursor.hasNext()){
				Document doc=cursor.next();
				if(!doc.getObjectId("ActivityId").toString().equals("000000000000000000000000")){
					array.put(doc.get("ActivityId"));
				}
			}
			
			result=array.toString();
			log(result);

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
