package coreservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.bson.Document;
import org.bson.types.ObjectId;
import staticData.StaticString;
import utils.Util;
import com.dbDao.CreateQueryFromBean;
import com.dbDao.DaoImpl;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCursor;
import bean.StudentInfo;
import bean.Suggestion;

public class submitsuggestion extends HttpServlet {


	private static final long serialVersionUID = 1L;

	public submitsuggestion() {
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
		
		String result="ok";
		
		String userid=request.getParameter("userid");
		String content=request.getParameter("content");
		
		try{
			String from="empty";
			
			StudentInfo student=new StudentInfo();
			student.set_id(new ObjectId(userid));
			
			BasicDBObject pro=new BasicDBObject();
			pro.put(StaticString.StudentInfo_Email, 1);
			pro.put(StaticString.StudentInfo_Phone, 1);
			pro.put(StaticString.StudentInfo_Name, 1);
			pro.put(StaticString.StudentInfo_Sex, 1);
			pro.put(StaticString.StudentInfo_id, 0);

			MongoCursor<Document> cursor= DaoImpl.GetSelectCursor(StudentInfo.class, CreateQueryFromBean.EqualObj(student), pro);
			while(cursor.hasNext()){
				
				Document doc=cursor.next();
				
				from=doc.getString("Name");
				if(doc.getInteger("Sex")==0){
					from=from+"(Boy)";
				}else{
					from=from+"(Girl)";
				}
				from=from+"  phone:"+doc.getString("Phone")+"   Email:"+doc.getString("Email");
			}
			
			if(!from.equals("empty")){
				
				Suggestion sugge=new Suggestion();
				sugge.set_id(new ObjectId());
				sugge.setContent(Util.DoGetString(content));
				sugge.setFrom(from);
				Calendar c=Calendar.getInstance();
				sugge.setReleaseTime(c.getTime());
				
				DaoImpl.InsertWholeBean(sugge);

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
