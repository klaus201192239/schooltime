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
import com.dbDao.CreateQueryFromBean;
import com.dbDao.DaoImpl;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCursor;
import bean.ActivityComment;
import staticData.StaticString;

public class getcomment extends HttpServlet {

	private static final long serialVersionUID = 1L;


	public getcomment() {
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
		
		String result="error";
		
		String activityid=request.getParameter("activityid");
		int activitytype=Integer.parseInt(request.getParameter("activitytype"));
		
		ActivityComment com=new ActivityComment();
		com.setActivityId(new ObjectId(activityid));
		com.setActivityType(activitytype);
		
		BasicDBObject pro=new BasicDBObject();
		pro.put(StaticString.ActivityComment_id, 0);
		pro.put(StaticString.ActivityComment_Content, 1);
		
		BasicDBObject sort=new BasicDBObject(StaticString.ActivityComment_id,1);
		
		try{
		    MongoCursor<Document> cursor=DaoImpl.GetSelectCursor(ActivityComment.class, CreateQueryFromBean.EqualObj(com), sort, pro);
		    JSONArray array=new JSONArray();
		    while(cursor.hasNext()){
		    	
		    	Document doc=cursor.next();
		    	
		    	JSONObject obj=new JSONObject();
		    	obj.put("comment", doc.getString("Content"));
		    	array.put(obj);
		    }
		    result=array.toString();
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
