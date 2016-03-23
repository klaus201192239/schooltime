package coreservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;
import staticData.StaticString;
import com.dbDao.CreateQueryFromBean;
import com.dbDao.DaoImpl;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCursor;
import bean.Manager;
import bean.Organization;

public class loginweb extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public loginweb() {
		super();
	}


	public void destroy() {
		super.destroy(); 
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");//post 可以直接设置中文字符编号
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		String result="error";
		String userId=request.getParameter("phone");
		String pwd=request.getParameter("pwd");
		
		Manager manager=new Manager();
		manager.setPwd(pwd);
		manager.setUserId(userId);
		ArrayList<Manager> mlist=new ArrayList<Manager>();
		mlist.add(manager);
		
		
		Organization org=new Organization();
		org.setManager(mlist);
		
		BasicDBObject projection=new BasicDBObject();
		projection.put(StaticString.Organization_id, 1);
		projection.put(StaticString.Organization_Name, 1);
		
		
		JSONArray array=new JSONArray();
		
		try{
			
			MongoCursor<Document> cur=DaoImpl.GetSelectCursor(Organization.class, CreateQueryFromBean.EqualObj(org),  projection);
			while(cur.hasNext()){
				Document doc=cur.next();
				JSONObject obj=new JSONObject();
				obj.put("id", doc.getObjectId("_id").toString());
				obj.put("name", doc.getString("Name"));
				array.put(obj);
			}
			if(array.length()==0){
				result="wrong";
			}else{
				result="ok"+array.toString();
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
