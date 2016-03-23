package coreservlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.json.JSONObject;
import staticData.StaticString;
import bean.InActivity;
import com.dbDao.CreateQueryFromBean;
import com.dbDao.DaoImpl;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCursor;

public class getinactivitydetail extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public getinactivitydetail() {
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
		
		String activityid=request.getParameter("activityid");
		String result="error";
		BasicDBObject projection=new BasicDBObject();
		projection.put(StaticString.InActivity_id, 0);
		projection.put(StaticString.InActivity_AttachmentName, 1);
		projection.put(StaticString.InActivity_Content, 1);
		projection.put(StaticString.InActivity_OrganizationName, 1);
		
		InActivity ac=new InActivity();
		ac.set_id(new ObjectId(activityid));
		
		try{
			MongoCursor<Document> cursor=DaoImpl.GetSelectCursor(InActivity.class, CreateQueryFromBean.EqualObj(ac),1, projection);
			while(cursor.hasNext()){
				Document doc=cursor.next();
				JSONObject obj=new JSONObject();
				obj.put("organization", doc.getString("OrganizationName"));
				obj.put("content", doc.getString("Content"));
				obj.put("attachment", doc.getString("AttachmentName"));
				result=obj.toString();
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
