package coreservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.bson.types.ObjectId;
import org.json.JSONObject;
import utils.Util;

import com.dbDao.CreateOrQuery;
import com.dbDao.CreateQueryFromBean;
import com.dbDao.DaoImpl;
import bean.StudentInfo;

public class registerinfo extends HttpServlet {

	private static final long serialVersionUID = 1L;


	public registerinfo() {
		super();
	}

	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");//post 可以直接设置中文字符编号
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		
		String result="ok";
		ObjectId _id=new ObjectId();
		
		
		StudentInfo student=new StudentInfo();
		student.set_id(_id);
		student.setPhone(request.getParameter("phone").toString());
		student.setPwd(request.getParameter("pwd").toString());
		student.setDegree(Integer.parseInt(request.getParameter("degree").toString()));
		student.setEmail(request.getParameter("email").toString());
		student.setGrade(Integer.parseInt(request.getParameter("grade").toString()));
		student.setMajorId(new ObjectId(request.getParameter("majorid").toString()));
		
		try {
			student.setName(Util.DoGetString(request.getParameter("name").toString()));
			student.setMajorName(Util.DoGetString(request.getParameter("majorname").toString()));
		} catch (Exception e1) {
			
		}	
		student.setSex(Integer.parseInt(request.getParameter("sex").toString()));
		student.setIdCard(request.getParameter("studentid").toString());
		student.setSchoolId(new ObjectId(request.getParameter("schoolid").toString()));
		
		try{
			StudentInfo stu=new StudentInfo();
			stu.setEmail(request.getParameter("email").toString());
			
			StudentInfo stu1=new StudentInfo();
			stu1.setSchoolId(new ObjectId(request.getParameter("schoolid").toString()));
			stu1.setIdCard(request.getParameter("studentid").toString());
			
			CreateOrQuery or=new CreateOrQuery();
			or.Add(CreateQueryFromBean.EqualObj(stu));
			or.Add(CreateQueryFromBean.EqualObj(stu1));
			
			long x=DaoImpl.GetSelectCount(StudentInfo.class, or.GetResult());
			if(x==0){
				student.setCreateTime(new Date());
				DaoImpl.InsertWholeBean(student);
				JSONObject obj=new JSONObject();
				obj.put("_id", _id);
				result=obj.toString();
			}else{
				result="Wrong";
			}
			
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
