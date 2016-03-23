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

import staticData.StaticInteger;
import staticData.StaticString;
import utils.MemcachedUtil;
import utils.Util;
import bean.Member;
import bean.TableContentColumn;
import bean.TableContentInfo;
import bean.TeamInfo;

import com.dbDao.DaoImpl;

public class attendcreateteam extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public attendcreateteam() {
		super();
	}

	public void destroy() {
		super.destroy(); 
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		String result="ok";
		String activityid=request.getParameter("activityid").toString();
		String userid=request.getParameter("userid").toString();
		String name="", slogan="", abstr="", need="",idcard="",sname="",smajor="",sphone="",activityname="",schoolid="";
		int sdegree=0,sgrade=0;
		
		
		try {
			schoolid=request.getParameter("schoolid");
			name=Util.DoGetString(request.getParameter("name").toString());
			slogan=Util.DoGetString(request.getParameter("slogan").toString());
			abstr=Util.DoGetString(request.getParameter("abstract").toString());
			need=Util.DoGetString(request.getParameter("need").toString());
			idcard=request.getParameter("idcard");
			sname=Util.DoGetString(request.getParameter("sname"));
			smajor=Util.DoGetString(request.getParameter("smajor"));
			sphone=Util.DoGetString(request.getParameter("sphone"));
			sdegree=Integer.parseInt(request.getParameter("sdegree"));
			sgrade=Integer.parseInt(request.getParameter("sgrade"));
			activityname=Util.DoGetString(request.getParameter("activityname"));
		} catch (Exception e1) {

		}		
		int password=Integer.parseInt(request.getParameter("password").toString());
		String json=request.getParameter("info").toString();
		String tableid=request.getParameter("tableid").toString();
		ObjectId teamid=new ObjectId();
		
		ArrayList<Member> amem=new ArrayList<Member>();
		Member me=new Member();
		me.setAbstract("我是队长哦～～");
		me.setDegree(sdegree);
		me.setGrade(sgrade);
		me.setIdCard(idcard);
		me.setMajorName(smajor);
		me.setName(sname);
		me.setPhone(sphone);
		me.setSchoolId(new ObjectId(schoolid));
		me.setState(1);
		me.setStuId(new ObjectId(userid));
		me.setNowTime(new Date());
		amem.add(me);
		
		TeamInfo team=new TeamInfo();
		team.set_id(teamid);
		team.setAbstract(abstr);
		team.setActivityId(new ObjectId(activityid));
		team.setMember(amem);
		team.setName(name);
		team.setNeed(need);
		team.setPassword(password);
		team.setSlogan(slogan);
		team.setTeamLeader(new ObjectId(userid));
		team.setActivityName(activityname);
		team.setIdCard(idcard);
		team.setLeaderName(sname);
		team.setSchoolId(new ObjectId(schoolid));

		ArrayList<TableContentColumn> list=new  ArrayList<TableContentColumn>();
		try{

			DaoImpl.InsertWholeBean(team);
		
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
		
		
		try{
			
			MemcachedUtil.start();
			
			Object object= MemcachedUtil.get(StaticString.Request_Uri+"getteam?activityid="+activityid);

			if(object!=null){
				
				JSONArray array=(JSONArray)object; 
				
				JSONObject obj=new JSONObject();
				obj.put("_id", teamid.toString());
				obj.put("Name", name);
				obj.put("Abstract", abstr);
				obj.put("Need", need);
				obj.put("Password", password);
				obj.put("Slogan",slogan);
				if(sdegree==0){
					obj.put("Leader", sname+"-"+smajor+"-"+sgrade+"级"+"(专)");
				}else{
					if(sdegree==1){
						obj.put("Leader", sname+"-"+smajor+"-"+sgrade+"级"+"(本)");
					}else{
						if(sdegree==2){
							obj.put("Leader", sname+"-"+smajor+"-"+sgrade+"级"+"(研)");
						}else{
							if(sdegree==3){
								obj.put("Leader", sname+"-"+smajor+"-"+sgrade+"级"+"(博)");
							}
						}
					}
				}
				
				array.put(obj);
				
				MemcachedUtil.put(StaticString.Request_Uri+"getteam?activityid="+activityid, array.toString(),StaticInteger.InActivityTime);
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

		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		String result="ok";
		String activityid=request.getParameter("activityid").toString();
		String userid=request.getParameter("userid").toString();
		String name="", slogan="", abstr="", need="",idcard="",sname="",smajor="",sphone="",activityname="",schoolid="";
		int sdegree=0,sgrade=0;
		
		
		try {
			schoolid=request.getParameter("schoolid");
			name=Util.DoGetString(request.getParameter("name").toString());
			slogan=Util.DoGetString(request.getParameter("slogan").toString());
			abstr=Util.DoGetString(request.getParameter("abstract").toString());
			need=Util.DoGetString(request.getParameter("need").toString());
			idcard=request.getParameter("idcard");
			sname=Util.DoGetString(request.getParameter("sname"));
			smajor=Util.DoGetString(request.getParameter("smajor"));
			sphone=Util.DoGetString(request.getParameter("sphone"));
			sdegree=Integer.parseInt(request.getParameter("sdegree"));
			sgrade=Integer.parseInt(request.getParameter("sgrade"));
			activityname=Util.DoGetString(request.getParameter("activityname"));
		} catch (Exception e1) {

		}		
		int password=Integer.parseInt(request.getParameter("password").toString());
		String json=request.getParameter("info").toString();
		String tableid=request.getParameter("tableid").toString();
		ObjectId teamid=new ObjectId();
		
		ArrayList<Member> amem=new ArrayList<Member>();
		Member me=new Member();
		me.setAbstract("我是队长哦～～");
		me.setDegree(sdegree);
		me.setGrade(sgrade);
		me.setIdCard(idcard);
		me.setMajorName(smajor);
		me.setName(sname);
		me.setPhone(sphone);
		me.setSchoolId(new ObjectId(schoolid));
		me.setState(1);
		me.setStuId(new ObjectId(userid));
		me.setNowTime(new Date());
		amem.add(me);
		
		TeamInfo team=new TeamInfo();
		team.set_id(teamid);
		team.setAbstract(abstr);
		team.setActivityId(new ObjectId(activityid));
		team.setMember(amem);
		team.setName(name);
		team.setNeed(need);
		team.setPassword(password);
		team.setSlogan(slogan);
		team.setTeamLeader(new ObjectId(userid));
		team.setActivityName(activityname);
		team.setIdCard(idcard);
		team.setLeaderName(sname);
		team.setSchoolId(new ObjectId(schoolid));

		ArrayList<TableContentColumn> list=new  ArrayList<TableContentColumn>();
		try{

			DaoImpl.InsertWholeBean(team);
		
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
		
		
		try{
			
			MemcachedUtil.start();
			
			Object object= MemcachedUtil.get(StaticString.Request_Uri+"getteam?activityid="+activityid);

			if(object!=null){
				
				JSONArray array=(JSONArray)object; 
				
				JSONObject obj=new JSONObject();
				obj.put("_id", teamid.toString());
				obj.put("Name", name);
				obj.put("Abstract", abstr);
				obj.put("Need", need);
				obj.put("Password", password);
				obj.put("Slogan",slogan);
				if(sdegree==0){
					obj.put("Leader", sname+"-"+smajor+"-"+sgrade+"级"+"(专)");
				}else{
					if(sdegree==1){
						obj.put("Leader", sname+"-"+smajor+"-"+sgrade+"级"+"(本)");
					}else{
						if(sdegree==2){
							obj.put("Leader", sname+"-"+smajor+"-"+sgrade+"级"+"(研)");
						}else{
							if(sdegree==3){
								obj.put("Leader", sname+"-"+smajor+"-"+sgrade+"级"+"(博)");
							}
						}
					}
				}
				
				array.put(obj);
				
				MemcachedUtil.put(StaticString.Request_Uri+"getteam?activityid="+activityid, array.toString(),StaticInteger.InActivityTime);
			}
			
			MemcachedUtil.stop();
			
		}catch(Exception e){
			
		}
		
		out.println(result);
		out.flush();
		out.close();
	}


	public void init() throws ServletException {
		// Put your code here
	}

}
