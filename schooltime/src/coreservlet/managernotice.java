package coreservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.bson.Document;
import org.bson.types.ObjectId;
import staticData.StaticString;
import utils.Util;

import com.dbDao.CreateQueryFromBean;
import com.dbDao.DaoImpl;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCursor;

import bean.SchoolNotice;
import bean.TableContentInfo;
import bean.TableInfo;

public class managernotice extends HttpServlet {


	private static final long serialVersionUID = 1L;

	public managernotice() {
		super();
	}


	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
				
		String result="ok";
		String activityid=request.getParameter("activityid");
		String oganizationid=request.getParameter("oganizationid");
		String oganization=request.getParameter("oganization");
		String title=request.getParameter("title");
		String content=request.getParameter("content");
		
		log("dasdcfsa");
		
		try{
			
			int type=0;
			ObjectId tableid=null;
			
			TableInfo tableinfo=new TableInfo();
			tableinfo.setActivityId(new ObjectId(activityid));
			tableinfo.setType(0);
			tableinfo.setOrganizationId(new ObjectId(oganizationid));
			
			MongoCursor<Document> cursor=DaoImpl.GetSelectCursor(TableInfo.class, CreateQueryFromBean.EqualObj(tableinfo),new BasicDBObject(StaticString.TableInfo_id,1));

			while(cursor.hasNext()){
				
				Document doc=cursor.next();
				
				type=1;
				
				tableid=doc.getObjectId("_id");
			}
			
			if(type==1){
				
				TableContentInfo tablecontent=new TableContentInfo();
				tablecontent.setTableId(tableid);
				
				BasicDBObject pro=new BasicDBObject();
				pro.put(StaticString.TableContentInfo_StuId, 1);
				pro.put(StaticString.TableContentInfo_id, 0);
				
				ArrayList<ObjectId> stuidList=new ArrayList<ObjectId>();
				
				MongoCursor<Document> cursor1=DaoImpl.GetSelectCursor(TableContentInfo.class, CreateQueryFromBean.EqualObj(tablecontent),pro);

				int type1=0;
				
				while(cursor1.hasNext()){
					
					Document doc=cursor1.next();
					
					stuidList.add(doc.getObjectId("StuId"));
					
					type1=2;
				}
				
				if(type1==2){
					
					SchoolNotice notice=new SchoolNotice();
					Calendar ca=Calendar.getInstance();
					notice.setReleaseTime(ca.getTime());
					notice.setTitle(Util.DoGetString(title));
					notice.setContent(Util.DoGetString(content));
					notice.setOrganizationId(new ObjectId(oganizationid));
					notice.setOrganizationName(Util.DoGetString(oganization));
					
					int len=stuidList.size();
					for(int i=0;i<len;i++){
						
						
						log("hahahahah"+stuidList.get(i).toString());
						
						notice.set_id(new ObjectId());					
						notice.setReceiver(stuidList.get(i));
						
						DaoImpl.InsertWholeBean(notice);
					}
				}
			}
			if(type==0){
				result="error";
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
