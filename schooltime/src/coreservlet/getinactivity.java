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
import org.json.JSONObject;
import staticData.StaticString;
import bean.ActivityComment;
import bean.ActivityJudge;
import bean.InActivity;
import com.dbDao.CreateAndQuery;
import com.dbDao.CreateQueryFromBean;
import com.dbDao.DaoImpl;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCursor;

public class getinactivity extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public getinactivity() {
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
		
		
		String schoolid=request.getParameter("schoolid").toString();
		String currentid=request.getParameter("currentid").toString();
				
		BasicDBObject query=null;
		
		BasicDBObject sort=new BasicDBObject(StaticString.InActivity_id,-1);
		
		BasicDBObject projection=new BasicDBObject();
		projection.put(StaticString.InActivity_CategoryName, 1);
		projection.put(StaticString.InActivity_DeadLine, 1);
		projection.put(StaticString.InActivity_ImgUrl, 1);
		projection.put(StaticString.InActivity_Name, 1);
		projection.put(StaticString.InActivity_OnlyTeam, 1);
		projection.put(StaticString.InActivity_RunTime, 1);
		
		if(currentid.equals("0")){
			
			InActivity ac=new InActivity();
			ac.setSchoolId(new ObjectId(schoolid));
			try {
				query=CreateQueryFromBean.EqualObj(ac);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
			BasicDBObject query0=null,query1=null;
			
			InActivity ac=new InActivity();
			ac.set_id(new ObjectId(currentid));
			InActivity ac1=new InActivity();
			ac1.setSchoolId(new ObjectId(schoolid));
			try {
				query0=CreateQueryFromBean.LtObj(ac);
				query1=CreateQueryFromBean.EqualObj(ac1);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			CreateAndQuery and=new CreateAndQuery();
			and.Add(query0);
			and.Add(query1);
			query=and.GetResult();
		}
		
		JSONArray array=new JSONArray();
		MongoCursor<Document> cursor=null;
		try {
			cursor = DaoImpl.GetSelectCursor(InActivity.class, query,sort,3, projection);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(cursor.hasNext()){
			Document doc=cursor.next();
			
			try {
				ActivityComment ac=new ActivityComment();
				ac.setActivityId(doc.getObjectId("_id"));
				ac.setActivityType(0);
				long x1=DaoImpl.GetSelectCount(ActivityComment.class, CreateQueryFromBean.EqualObj(ac));
				ActivityJudge acc=new ActivityJudge();
				acc.setActivityId(doc.getObjectId("_id"));
				acc.setActivityType(0);
				long x2=DaoImpl.GetSelectCount(ActivityJudge.class, CreateQueryFromBean.EqualObj(acc));
				ActivityJudge accc=new ActivityJudge();
				accc.setActivityId(doc.getObjectId("_id"));
				accc.setType(1);
				accc.setActivityType(0);
				long x3=DaoImpl.GetSelectCount(ActivityJudge.class, CreateQueryFromBean.EqualObj(accc));
			
				JSONObject obj=new JSONObject();					
				obj.put("id", doc.getObjectId("_id").toString());
				obj.put("title", doc.getString("Name"));
				obj.put("category",doc.getString("CategoryName"));
				obj.put("deadline",doc.getDate("DeadLine").toString());
				obj.put("time",doc.getString("RunTime"));
				obj.put("imgurl", doc.getString("ImgUrl"));
				obj.put("onlyteam",doc.getInteger("OnlyTeam"));
				obj.put("pridenum", x3);
				obj.put("opposenum", (x2-x3));
				obj.put("commentnum",x1 );
				
				array.put(obj);
			} catch (Exception e) {

			}
			
		}
		out.println(array.toString());
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
