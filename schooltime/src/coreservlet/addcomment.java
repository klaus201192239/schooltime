package coreservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONObject;

import staticData.StaticInteger;
import staticData.StaticString;
import utils.MemcachedUtil;
import utils.Util;
import bean.ActivityComment;

import com.dbDao.DaoImpl;

public class addcomment extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public addcomment() {
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
		
		String userid=request.getParameter("userid");
		String activityid=request.getParameter("activityid");
		int activitytype=Integer.parseInt(request.getParameter("activitytype"));
		String comment="";
		try {
			comment=Util.DoGetString(request.getParameter("comment").toString());
			
		} catch (Exception e1) {
			
		}
		
		ActivityComment com=new ActivityComment();
		com.set_id(new ObjectId());
		com.setActivityId(new ObjectId(activityid));
		com.setActivityType(activitytype);
		com.setContent(comment);
		com.setOccurrenceTime(new Date());
		com.setStuId(new ObjectId(userid));
		
		try{
			DaoImpl.InsertWholeBean(com);
			result="ok";
			
		}catch(Exception e){
			result="error";
		}
		
		try{
			
			MemcachedUtil.start();
			
			Object object= MemcachedUtil.get(StaticString.Request_Uri+"getcomment?activityid="+activityid+"&activitytype="+activitytype);

			if(object!=null){
				JSONArray array=(JSONArray)object; 
				
				JSONObject obj=new JSONObject();
		    	obj.put("comment", comment);
		    	array.put(obj);
				
		    	MemcachedUtil.put(StaticString.Request_Uri+"getcomment?activityid="+activityid+"&activitytype="+activitytype, array.toString(),StaticInteger.InActivityTime);
		    	
			}
			
			MemcachedUtil.stop();
			
		}catch(Exception e){
			
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
