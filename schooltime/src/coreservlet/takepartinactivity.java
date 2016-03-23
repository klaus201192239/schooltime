package coreservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.types.ObjectId;

import com.dbDao.DaoImpl;

import bean.ActivityJudge;

public class takepartinactivity extends HttpServlet {


	private static final long serialVersionUID = 1L;

	public takepartinactivity() {
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
		String userid=request.getParameter("userid").toString();
		String activityid=request.getParameter("activityid").toString();
		int type=Integer.parseInt(request.getParameter("type").toString());
		
		ActivityJudge judge=new ActivityJudge();
		judge.set_id(new ObjectId());
		judge.setActivityId(new ObjectId(activityid));
		judge.setActivityType(0);
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
