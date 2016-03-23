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
import utils.Util;
import bean.ActivityComment;
import bean.ActivityJudge;
import bean.OutActivity;
import com.dbDao.CreateAndQuery;
import com.dbDao.CreateQueryFromBean;
import com.dbDao.DaoImpl;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCursor;

public class getoutactivity extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public getoutactivity() {
		super();
	}

	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");//post 可以直接设置中文字符编号
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		String currentid = request.getParameter("currentid").toString();
		String classid = request.getParameter("classid").toString();
		
		BasicDBObject projection=new BasicDBObject();
		BasicDBObject sort=new BasicDBObject(StaticString.OutActivity_id,-1);
		projection.put(StaticString.OutActivity_Category, 1);
		projection.put(StaticString.OutActivity_DeadLine, 1);
		projection.put(StaticString.OutActivity_ImgUrl, 1);
		projection.put(StaticString.OutActivity_Name, 1);
		projection.put(StaticString.OutActivity_RunTime, 1);
		
		
		BasicDBObject query=null;
		
		if(currentid.equals("0")){
			if(classid.equals("0")){
				query=new BasicDBObject();
			}else{
				OutActivity ac=new OutActivity();
				ac.setCategory(Util.GetOutActivityCategory(classid));
				try {
					query=CreateQueryFromBean.EqualObj(ac);
				} catch (Exception e) {

				}				
			}
		}else{
			if(classid.equals("0")){
				OutActivity ac=new OutActivity();
				ac.set_id(new ObjectId(currentid));
				try {
					query=CreateQueryFromBean.LtObj(ac);
				} catch (Exception e) {
				}

			}else{
				OutActivity ac=new OutActivity();
				ac.setCategory(Util.GetOutActivityCategory(classid));
				BasicDBObject query0=null;
				try {
					query0 = CreateQueryFromBean.EqualObj(ac);
				} catch (Exception e) {

				}
				
				OutActivity ac1=new OutActivity();
				ac1.set_id(new ObjectId(currentid));
				BasicDBObject query1=null;
				try {
					query1 = CreateQueryFromBean.LtObj(ac1);
				} catch (Exception e) {

				}
				
				CreateAndQuery and=new CreateAndQuery();
				and.Add(query0);
				and.Add(query1);
				query=and.GetResult();
			}
		}
		
		JSONArray array=new JSONArray();
		MongoCursor<Document> cursor=null;
		try {
			cursor = DaoImpl.GetSelectCursor(OutActivity.class, query,sort,3, projection);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(cursor.hasNext()){
			Document doc=cursor.next();
			try {
				ActivityComment ac=new ActivityComment();
				ac.setActivityId(doc.getObjectId("_id"));
				ac.setActivityType(1);
				long x1=DaoImpl.GetSelectCount(ActivityComment.class, CreateQueryFromBean.EqualObj(ac));
				ActivityJudge acc=new ActivityJudge();
				acc.setActivityId(doc.getObjectId("_id"));
				acc.setActivityType(1);
				long x2=DaoImpl.GetSelectCount(ActivityJudge.class, CreateQueryFromBean.EqualObj(acc));
				ActivityJudge accc=new ActivityJudge();
				accc.setActivityId(doc.getObjectId("_id"));
				accc.setType(1);
				accc.setActivityType(1);
				long x3=DaoImpl.GetSelectCount(ActivityJudge.class, CreateQueryFromBean.EqualObj(accc));
			
				JSONObject obj=new JSONObject();					
				obj.put("id", doc.getObjectId("_id").toString());
				obj.put("title", doc.getString("Name"));
				obj.put("imgurl", doc.getString("ImgUrl"));
				obj.put("category",doc.getString("Category"));
				obj.put("deadline",doc.getDate("DeadLine").toString());
				obj.put("time",doc.getString("RunTime"));
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

	public static void main(String[] args) {
		String currentid = "0";
		String classid = "0";
		
		BasicDBObject projection=new BasicDBObject();
		BasicDBObject sort=new BasicDBObject(StaticString.OutActivity_id,-1);
		projection.put(StaticString.OutActivity_Category, 1);
		projection.put(StaticString.OutActivity_DeadLine, 1);
		projection.put(StaticString.OutActivity_ImgUrl, 1);
		projection.put(StaticString.OutActivity_Name, 1);
		projection.put(StaticString.OutActivity_RunTime, 1);
		
		
		BasicDBObject query=null;
		
		if(currentid.equals("0")){
			if(classid.equals("0")){
				query=new BasicDBObject();
			}else{
				OutActivity ac=new OutActivity();
				ac.setCategory(Util.GetOutActivityCategory(classid));
				try {
					query=CreateQueryFromBean.EqualObj(ac);
				} catch (Exception e) {

				}				
			}
		}else{
			if(classid.equals("0")){
				OutActivity ac=new OutActivity();
				ac.set_id(new ObjectId(currentid));
				try {
					query=CreateQueryFromBean.LtObj(ac);
				} catch (Exception e) {
				}

			}else{
				OutActivity ac=new OutActivity();
				ac.setCategory(Util.GetOutActivityCategory(classid));
				BasicDBObject query0=null;
				try {
					query0 = CreateQueryFromBean.EqualObj(ac);
				} catch (Exception e) {

				}
				
				OutActivity ac1=new OutActivity();
				ac1.set_id(new ObjectId(currentid));
				BasicDBObject query1=null;
				try {
					query1 = CreateQueryFromBean.LtObj(ac1);
				} catch (Exception e) {

				}
				
				CreateAndQuery and=new CreateAndQuery();
				and.Add(query0);
				and.Add(query1);
				query=and.GetResult();
			}
		}
		
		JSONArray array=new JSONArray();
		MongoCursor<Document> cursor=null;
		try {
			cursor = DaoImpl.GetSelectCursor(OutActivity.class, query,sort,3, projection);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(cursor.hasNext()){
			Document doc=cursor.next();
			try {
				ActivityComment ac=new ActivityComment();
				ac.setActivityId(doc.getObjectId("_id"));
				ac.setActivityType(1);
				long x1=DaoImpl.GetSelectCount(ActivityComment.class, CreateQueryFromBean.EqualObj(ac));
				ActivityJudge acc=new ActivityJudge();
				acc.setActivityId(doc.getObjectId("_id"));
				acc.setActivityType(1);
				long x2=DaoImpl.GetSelectCount(ActivityJudge.class, CreateQueryFromBean.EqualObj(acc));
				ActivityJudge accc=new ActivityJudge();
				accc.setActivityId(doc.getObjectId("_id"));
				accc.setType(1);
				accc.setActivityType(1);
				long x3=DaoImpl.GetSelectCount(ActivityJudge.class, CreateQueryFromBean.EqualObj(accc));
			
				JSONObject obj=new JSONObject();					
				obj.put("id", doc.getObjectId("_id").toString());
				obj.put("title", doc.getString("Name"));
				obj.put("imgurl", doc.getString("ImgUrl"));
				obj.put("category",doc.getString("Category"));
				obj.put("deadline",doc.getDate("DeadLine").toString());
				obj.put("time",doc.getString("RunTime"));
				obj.put("pridenum", x3);
				obj.put("opposenum", (x2-x3));
				obj.put("commentnum",x1 );
				
				array.put(obj);
			} catch (Exception e) {

			}
			
		}
		System.out.println(array.toString());
	}
	
}
