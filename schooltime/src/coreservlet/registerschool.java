package coreservlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.bson.Document;
import org.json.JSONArray;
import staticData.StaticString;
//import utils.MemcachedUtil;
import utils.Util;
import utils.jsonUtil;
import bean.School;
import com.dbDao.CreateQueryFromBean;
import com.dbDao.DaoImpl;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCursor;

public class registerschool extends HttpServlet {


	private static final long serialVersionUID = 1L;

	public registerschool() {
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
		String schoolp="";
		String schoolc="";
		try {
			schoolp=Util.DoGetString(request.getParameter("schoolpro").toString());
			schoolc=Util.DoGetString(request.getParameter("schoolcity").toString());
		} catch (Exception e1) {
			
		}
		
		
		
		//MemcachedUtil.start();
		
		//Object object= MemcachedUtil.get(StaticString.Request_Uri+"registerschool?schoolpro="+schoolp+"&"+"schoolcity="+schoolc);
		
		//MemcachedUtil.stop();

		
//		if(object!=null){
	//		result=object.toString();
	//	}else{
			
			log(schoolp);
			System.out.println(schoolp);
			log(schoolc);
			System.out.println(schoolc);
			
			JSONArray array=new JSONArray();
			School sch=new School();
			sch.setAddressP(schoolp);
			sch.setAddressC(schoolc);
				
			BasicDBObject obj=new BasicDBObject();
			obj.put(StaticString.School_id, 1);
			obj.put(StaticString.School_ShowUrl, 1);
			obj.put(StaticString.School_Major, 1);
			obj.put(StaticString.School_Name, 1);
			
			try {
				int temp=0;
				MongoCursor<Document> cursor= DaoImpl.GetSelectCursor(School.class, CreateQueryFromBean.EqualObj(sch),obj);
				
				while(cursor.hasNext()){
					temp=1;
					array=jsonUtil.ParaFromMongoCursor(cursor);
				}
				if(temp==0){
					result="nothing";
				}else{
					result=array.toString();
				}
				
			} catch (Exception e1) {
				result="error";
			}
			
			log(result);
			
			System.out.println(result);
			
	//	}

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
		
	}
	
}
