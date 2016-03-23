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
import utils.jsonUtil;
import com.dbDao.CreateQueryFromBean;
import com.dbDao.DaoImpl;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCursor;
import bean.TableInfo;

public class getsignupinfo extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public getsignupinfo() {
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
		
		String activityid=request.getParameter("activityid");
		
		log("hahahaha");
		log("~~"+activityid);
		
		int type=0;
		String result="error";
		
		TableInfo ta=new TableInfo();
		ta.setActivityId(new ObjectId(activityid));
		ta.setType(type);
		BasicDBObject pro=new BasicDBObject();
		pro.put(StaticString.TableInfo_TableInfoColumn, 1);		
		
		try {
			MongoCursor<Document> cursor= DaoImpl.GetSelectCursor(TableInfo.class, CreateQueryFromBean.EqualObj(ta),pro);
			while(cursor.hasNext()){
				
				Document doc=cursor.next();
				JSONObject obj=jsonUtil.ParaFromDocument(doc);
				result=obj.toString();
			}
		} catch (Exception e1) {
			result="error";
		}
		
		log(result);
		
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
