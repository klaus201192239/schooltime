package coreservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.bson.types.ObjectId;
import com.dbDao.CreateQueryFromBean;
import com.dbDao.DaoImpl;
import bean.ActivityCallOver;

public class callover extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public callover() {
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
		
		String result="";
		
		String activityid=request.getParameter("activityid").toString();
		int hour=Integer.parseInt(request.getParameter("hour"));
		int minute=Integer.parseInt(request.getParameter("minute"));
		double x=Double.parseDouble(request.getParameter("localx"));
		double y=Double.parseDouble(request.getParameter("localy"));

		log(hour+"HOUR");
		log(minute+"MINUTE");
		
		ActivityCallOver callover=new ActivityCallOver();
		
		callover.setActivityId(new ObjectId(activityid));
		
		
		try{
			long num=DaoImpl.GetSelectCount(ActivityCallOver.class, CreateQueryFromBean.EqualObj(callover));
			if(num>0){
				result="wrong";
			}else{
				callover.set_id(new ObjectId());
				if(hour==0&&minute==0){
					  Calendar c = Calendar.getInstance();
					  c.set(2900, 1, 1, 0, 0, 0);
					  callover.setDeadline(c.getTime());
				}else{
					Calendar c = Calendar.getInstance();
					c.add(minute, Calendar.MINUTE);
					c.add(hour, Calendar.HOUR);
					callover.setDeadline(c.getTime());
					
					log(c.getTime().toString());
					
				}
				callover.setPositionX(x);
				callover.setPositionY(y);
				DaoImpl.InsertWholeBean(callover);
				result="ok";
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
	}

}
