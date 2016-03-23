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
import utils.jsonUtil;
import com.dbDao.CreateQueryFromBean;
import com.dbDao.DaoImpl;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCursor;
import bean.School;

public class getmajor extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public getmajor() {
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
		String schoolid=request.getParameter("schoolid");
		
		School school=new School();
		school.set_id(new ObjectId(schoolid));
		
		BasicDBObject pro=new BasicDBObject();
		pro.put(StaticString.School_id, 0);
		pro.put(StaticString.School_Major, 1);

		try{
			
			MongoCursor <Document> cursor=DaoImpl.GetSelectCursor(School.class, CreateQueryFromBean.EqualObj(school), 1, pro);
			while(cursor.hasNext()){
				Document doc=cursor.next();
				JSONObject obj=jsonUtil.ParaFromDocument(doc);
				JSONArray array=obj.getJSONArray("Major");
				result=array.toString();
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
