package coreservlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.dbDao.CreateQueryFromBean;
import com.dbDao.DaoImpl;
import bean.StudentInfo;

public class phoneregister extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public phoneregister() {
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
		
		String phone=request.getParameter("phone").toString().trim();
		StudentInfo stu=new StudentInfo();
		stu.setPhone(phone);
		try {
			long x=DaoImpl.GetSelectCount(StudentInfo.class, CreateQueryFromBean.EqualObj(stu));
			if(x==0){
				result="OK";
			}
			else{
				result="Wrong";
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
