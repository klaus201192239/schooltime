package coreservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.types.ObjectId;

import bean.ActivityJudge;

import com.dbDao.DaoImpl;

public class takepartoutactivity extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public takepartoutactivity() {
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
		String userid=request.getParameter("id").toString();
		String activityid=request.getParameter("activityid").toString();
		int type=Integer.parseInt(request.getParameter("type").toString());
		
		
		ActivityJudge judge=new ActivityJudge();
		judge.set_id(new ObjectId());
		judge.setActivityId(new ObjectId(activityid));
		judge.setActivityType(1);
		judge.setOccurrenceTime(new Date());
		judge.setStuId(new ObjectId(userid));
		judge.setType(type);
		
		try {
			DaoImpl.InsertWholeBean(judge);
			result="ok";
		} catch (Exception e) {
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
