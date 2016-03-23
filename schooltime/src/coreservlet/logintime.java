package coreservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.types.ObjectId;

import com.dbDao.DaoImpl;

import bean.UserSkimTime;

public class logintime extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public logintime() {
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
		
		log("BBBBBBBBB6666666666");
		
		String result="error";
		String user=request.getParameter("userid");
		String logintime=request.getParameter("logintime");
		
		log(user+logintime);
		
		log("BBBBBBBBB");
		
		int x = logintime.indexOf("~");
		String str1 = logintime.substring(0, x);
		String str2 = logintime.substring(x + 1);
		SimpleDateFormat sfStart = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", java.util.Locale.ENGLISH);
		Date d1 = null, d2 = null;
		try {
			
			log("CCCCCCCCC");
			
			d1 = sfStart.parse(str1);
			d2 = sfStart.parse(str2);
			
			log("DDDDDDDDDDD");
			
			UserSkimTime tt=new UserSkimTime();
			tt.set_id(new ObjectId());
			tt.setExitTime(d2);
			tt.setLoginTime(d1);
			tt.setStuId(new ObjectId(user));
			
			log("EEEEEEEEEEEEE");
			
			DaoImpl.InsertWholeBean(tt);
			log("AAAAAAAAA");
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
