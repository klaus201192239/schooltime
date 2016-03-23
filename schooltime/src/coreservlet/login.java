package coreservlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.bson.Document;
import org.json.JSONObject;
import staticData.StaticString;
import utils.jsonUtil;
import bean.School;
import bean.StudentInfo;
import com.dbDao.CreateQueryFromBean;
import com.dbDao.DaoImpl;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCursor;

public class login extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public login() {
		super();
	}
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");//post 可以直接设置中文字符编号
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		String result="error";
		JSONObject json=null;
		
		String phone=request.getParameter("phone").toString().trim();
		String pwd=request.getParameter("pwd").toString().trim();
		StudentInfo stu=new StudentInfo();
		stu.setPhone(phone);
		stu.setPwd(pwd);
		
		try {
			int temp=0;
			MongoCursor<Document> cur= DaoImpl.GetSelectCursor(StudentInfo.class, CreateQueryFromBean.EqualObj(stu),1,new BasicDBObject());
			while(cur.hasNext()){
				
				Document docu=cur.next();
				School sch=new School();
				sch.set_id(docu.getObjectId("SchoolId"));
				BasicDBObject obj=new BasicDBObject();
				obj.put(StaticString.School_id, 0);
				obj.put(StaticString.School_ShowUrl, 1);
				obj.put(StaticString.School_Name, 1);
				MongoCursor<Document> cursor= DaoImpl.GetSelectCursor(School.class, CreateQueryFromBean.EqualObj(sch),1,obj);
				while(cursor.hasNext()){
					Document document=cursor.next();
					json=jsonUtil.ParaFromDocument(docu);
					json.put("SchoolImg", document.getString("ShowUrl"));
					json.put("SchoolName", document.getString("Name"));					
					temp=1;
					result=json.toString();
				}
			}
			if(temp==0){
				result="LoginError";
			}
		} catch (Exception e1) {
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
