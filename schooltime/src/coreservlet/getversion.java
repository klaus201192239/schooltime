package coreservlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.bson.Document;
import staticData.StaticString;
import utils.jsonUtil;
import com.dbDao.DaoImpl;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCursor;
import bean.AppVersion;

public class getversion extends HttpServlet {


	private static final long serialVersionUID = 1L;

	public getversion() {
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
		
		
		String result="error";
		
		try{
			
			BasicDBObject pro=new BasicDBObject();
			pro.put(StaticString.AppVersion_Version, 1);
			pro.put(StaticString.AppVersion_id, 0);
			pro.put(StaticString.AppVersion_DownloadUrl, 1);
			pro.put(StaticString.AppVersion_Goodness, 1);
			
			MongoCursor<Document>  cur=DaoImpl.GetSelectCursor(AppVersion.class, new BasicDBObject(),new BasicDBObject(StaticString.AppVersion_id,-1), 1,pro);
			while(cur.hasNext()){
				Document doc=cur.next();
				result=jsonUtil.ParaFromDocument(doc).toString();
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
