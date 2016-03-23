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
import com.dbDao.CreateOrQuery;
import com.dbDao.CreateQueryFromBean;
import com.dbDao.DaoImpl;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCursor;
import bean.ActivityComment;
import bean.ActivityJudge;
import bean.InActivity;

public class getmyactivity extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public getmyactivity() {
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
		
		String json=request.getParameter("jsonid").toString();
		CreateOrQuery or=new CreateOrQuery();
		
		try{
			JSONArray array=new JSONArray(json);
			int len=array.length();
			for(int i=0;i<len;i++){
				InActivity ac=new InActivity();
				ac.set_id(new ObjectId(array.getString(i)));
				BasicDBObject que=CreateQueryFromBean.EqualObj(ac);
				or.Add(que);
			}
		}catch(Exception e){
			
		}
		
		BasicDBObject projection=new BasicDBObject();
		projection.put(StaticString.InActivity_CategoryName, 1);
		projection.put(StaticString.InActivity_DeadLine, 1);
		projection.put(StaticString.InActivity_ImgUrl, 1);
		projection.put(StaticString.InActivity_Name, 1);
		projection.put(StaticString.InActivity_RunTime, 1);
		
		
		JSONArray arrRe=new JSONArray();
		
		MongoCursor<Document> cursor=null;
		try {
			cursor = DaoImpl.GetSelectCursor(InActivity.class, or.GetResult(), new BasicDBObject(StaticString.InActivity_id,-1), projection);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
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
				obj.put("pridenum", x3);
				obj.put("opposenum", (x2-x3));
				obj.put("commentnum",x1 );
				
				arrRe.put(obj);
			} catch (Exception e) {

			}
			
		}
		out.println(arrRe.toString());
		out.flush();
		out.close();
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		String json=request.getParameter("jsonid").toString();
		CreateOrQuery or=new CreateOrQuery();
		
		try{
			JSONArray array=new JSONArray(json);
			int len=array.length();
			for(int i=0;i<len;i++){
				InActivity ac=new InActivity();
				ac.set_id(new ObjectId(array.getString(i)));
				BasicDBObject que=CreateQueryFromBean.EqualObj(ac);
				or.Add(que);
			}
		}catch(Exception e){
			
		}
		
		BasicDBObject projection=new BasicDBObject();
		projection.put(StaticString.InActivity_CategoryName, 1);
		projection.put(StaticString.InActivity_DeadLine, 1);
		projection.put(StaticString.InActivity_ImgUrl, 1);
		projection.put(StaticString.InActivity_Name, 1);
		projection.put(StaticString.InActivity_RunTime, 1);
		
		
		JSONArray arrRe=new JSONArray();
		
		MongoCursor<Document> cursor=null;
		try {
			cursor = DaoImpl.GetSelectCursor(InActivity.class, or.GetResult(), new BasicDBObject(StaticString.InActivity_id,-1), projection);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
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
				obj.put("pridenum", x3);
				obj.put("opposenum", (x2-x3));
				obj.put("commentnum",x1 );
				
				arrRe.put(obj);
			} catch (Exception e) {

			}
			
		}
		out.println(arrRe.toString());
		out.flush();
		out.close();
	}


	public void init() throws ServletException {

	}

}
