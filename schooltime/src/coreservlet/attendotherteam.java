package coreservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONObject;

import com.dbDao.CreateQueryFromBean;
import com.dbDao.DaoImpl;

import utils.Util;

import bean.Member;
import bean.TableContentColumn;
import bean.TableContentInfo;
import bean.TeamInfo;

public class attendotherteam extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public attendotherteam() {
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
		String userid=request.getParameter("userid").toString();
		String teamid=request.getParameter("teamid").toString();
		String json=request.getParameter("info").toString();
		String tableid=request.getParameter("tableid").toString();
		String abstra=request.getParameter("abstra").toString();
		String idcard=request.getParameter("idcard").toString();
		String sname=request.getParameter("sname").toString();
		String smajor=request.getParameter("smajor").toString();
		String sdegree=request.getParameter("sdegree").toString();
		String sphone=request.getParameter("sphone").toString();
		String sgrade=request.getParameter("sgrade").toString();
		String schoolid=request.getParameter("schoolid").toString();
		
		try{
			TeamInfo team=new TeamInfo();
			team.set_id(new ObjectId(teamid));

			ArrayList<Member> mlist=new ArrayList<Member>();
			Member member=new Member();
			member.setAbstract(Util.DoGetString(abstra));
			member.setDegree(Integer.parseInt(sdegree));
			member.setGrade(Integer.parseInt(sgrade));
			member.setMajorName(Util.DoGetString(smajor));
			member.setName(Util.DoGetString(sname));
			member.setPhone(sphone);
			member.setSchoolId(new ObjectId(schoolid));			
			member.setState(0);
			member.setIdCard(idcard);
			member.setStuId(new ObjectId(userid));
			member.setNowTime(new Date());
			mlist.add(member);
			
			DaoImpl.InsertSonBean(TeamInfo.class, CreateQueryFromBean.EqualObj(team),Member.class , mlist);
			
			ArrayList<TableContentColumn> list=new  ArrayList<TableContentColumn>();
			
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
		String userid=request.getParameter("userid").toString();
		String teamid=request.getParameter("teamid").toString();
		String json=request.getParameter("info").toString();
		String tableid=request.getParameter("tableid").toString();
		String abstra=request.getParameter("abstra").toString();
		String idcard=request.getParameter("idcard").toString();
		String sname=request.getParameter("sname").toString();
		String smajor=request.getParameter("smajor").toString();
		String sdegree=request.getParameter("sdegree").toString();
		String sphone=request.getParameter("sphone").toString();
		String sgrade=request.getParameter("sgrade").toString();
		String schoolid=request.getParameter("schoolid").toString();
		
		try{
			TeamInfo team=new TeamInfo();
			team.set_id(new ObjectId(teamid));

			ArrayList<Member> mlist=new ArrayList<Member>();
			Member member=new Member();
			member.setAbstract(Util.DoGetString(abstra));
			member.setDegree(Integer.parseInt(sdegree));
			member.setGrade(Integer.parseInt(sgrade));
			member.setMajorName(Util.DoGetString(smajor));
			member.setName(Util.DoGetString(sname));
			member.setPhone(sphone);
			member.setSchoolId(new ObjectId(schoolid));			
			member.setState(0);
			member.setIdCard(idcard);
			member.setStuId(new ObjectId(userid));
			member.setNowTime(new Date());
			mlist.add(member);
			
			DaoImpl.InsertSonBean(TeamInfo.class, CreateQueryFromBean.EqualObj(team),Member.class , mlist);
			
			ArrayList<TableContentColumn> list=new  ArrayList<TableContentColumn>();
			
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
