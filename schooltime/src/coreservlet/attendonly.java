package coreservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONObject;

import com.dbDao.DaoImpl;

import utils.Util;

import bean.TableContentColumn;
import bean.TableContentInfo;

public class attendonly extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public attendonly() {
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
		
		String result="ok";
		String tableid=request.getParameter("tableid").toString();
		String userid=request.getParameter("userid").toString();
		String json=request.getParameter("info").toString();
		String schoolid=request.getParameter("schoolid").toString();
		String idcard=request.getParameter("idcard").toString();
	
		ArrayList<TableContentColumn> list=new  ArrayList<TableContentColumn>();
		try{

			JSONArray array=new JSONArray(json);
			int len=array.length();
			for(int i=0;i<len;i++){
				JSONObject obj=array.getJSONObject(i);
				TableContentColumn co=new TableContentColumn();
				co.setContent(Util.DoGetString(obj.getString("txt")));
				co.setName(Util.DoGetString(obj.getString("name")));
				list.add(co);
			}
			TableContentColumn coo=new TableContentColumn();
			coo.setContent("0");
			coo.setName("SignIn");
			list.add(coo);
			
			TableContentInfo ta=new TableContentInfo();
			ta.set_id(new ObjectId());
			ta.setStuId(new ObjectId(userid));
			ta.setTableId(new ObjectId(tableid));
			ta.setTableContentColumn(list);
			ta.setTableType(0);
			ta.setSchoolId(new ObjectId(schoolid));
			ta.setIdCard(idcard);
			DaoImpl.InsertWholeBean(ta);
			result="ok";
		}catch(Exception e){
			result="error";
		}

		out.println(result);
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		String result="ok";
		String tableid=request.getParameter("tableid").toString();
		String userid=request.getParameter("userid").toString();
		String json=request.getParameter("info").toString();
		String schoolid=request.getParameter("schoolid").toString();
		String idcard=request.getParameter("idcard").toString();
	
		ArrayList<TableContentColumn> list=new  ArrayList<TableContentColumn>();
		try{

			JSONArray array=new JSONArray(json);
			int len=array.length();
			for(int i=0;i<len;i++){
				JSONObject obj=array.getJSONObject(i);
				TableContentColumn co=new TableContentColumn();
				co.setContent(Util.DoGetString(obj.getString("txt")));
				co.setName(Util.DoGetString(obj.getString("name")));
				list.add(co);
			}
			TableContentColumn coo=new TableContentColumn();
			coo.setContent("0");
			coo.setName("SignIn");
			list.add(coo);
			
			TableContentInfo ta=new TableContentInfo();
			ta.set_id(new ObjectId());
			ta.setStuId(new ObjectId(userid));
			ta.setTableId(new ObjectId(tableid));
			ta.setTableContentColumn(list);
			ta.setTableType(0);
			ta.setSchoolId(new ObjectId(schoolid));
			ta.setIdCard(idcard);
			DaoImpl.InsertWholeBean(ta);
			result="ok";
		}catch(Exception e){
			result="error";
		}

		out.println(result);
		out.flush();
		out.close();
	}


	public void init() throws ServletException {
		// Put your code here
	}
}
