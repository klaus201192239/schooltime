package coreservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.json.JSONArray;

import staticData.StaticString;
import utils.jsonUtil;

import com.dbDao.CreateOrQuery;
import com.dbDao.CreateQueryFromBean;
import com.dbDao.DaoImpl;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCursor;

import bean.Member;
import bean.TeamInfo;

public class getupdateteam extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	public getupdateteam() {
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
		
		String userid=request.getParameter("userid");
		String schoolid=request.getParameter("schoolid");
		String idcard =request.getParameter("idcard");
		
		TeamInfo team1=new TeamInfo();
		team1.setTeamLeader(new ObjectId(userid));
		
		TeamInfo team2=new TeamInfo();
		team2.setTeamLeader(new ObjectId("000000000000000000000000"));
		team2.setSchoolId(new ObjectId(schoolid));
		team2.setIdCard(idcard);
		
		TeamInfo team3=new TeamInfo();
		ArrayList<Member> member=new ArrayList<Member>();
		Member mm=new Member();
		mm.setStuId(new ObjectId(userid));
		member.add(mm);		
		team3.setMember(member);
		
		TeamInfo team4=new TeamInfo();
		ArrayList<Member> member1=new ArrayList<Member>();
		Member mm1=new Member();
		mm1.setStuId(new ObjectId("000000000000000000000000"));
		mm1.setSchoolId(new ObjectId(schoolid));
		mm1.setIdCard(idcard);
		member1.add(mm1);
		team4.setMember(member1);
		
		BasicDBObject pro=new BasicDBObject();
		pro.put(StaticString.TeamInfo_ActivityName, 1);
		pro.put(StaticString.TeamInfo_Name, 1);
		pro.put(StaticString.TeamInfo_LeaderName, 1);
		pro.put(StaticString.TeamInfo_TeamLeader, 1);
		pro.put(StaticString.TeamInfo_IdCard, 1);
		pro.put(StaticString.Member_StuId, 1);
		pro.put(StaticString.Member_Name, 1);
		pro.put(StaticString.Member_MajorName, 1);
		pro.put(StaticString.Member_Degree, 1);
		pro.put(StaticString.Member_Grade, 1);
		pro.put(StaticString.Member_Phone, 1);
		pro.put(StaticString.Member_Abstract, 1);
		pro.put(StaticString.Member_State, 1);
		pro.put(StaticString.Member_IdCard, 1);
		pro.put(StaticString.Member_NowTime, 1);
		
		CreateOrQuery or=new CreateOrQuery();
		try {
			or.Add(CreateQueryFromBean.EqualObj(team1));
			or.Add(CreateQueryFromBean.EqualObj(team2));
			or.Add(CreateQueryFromBean.EqualObj(team3));
			or.Add(CreateQueryFromBean.EqualObj(team4));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		try{
			JSONArray array=new JSONArray();
			MongoCursor<Document> cursor=DaoImpl.GetSelectCursor(TeamInfo.class, or.GetResult(), new BasicDBObject(StaticString.TeamInfo_id,-1), pro);
			while(cursor.hasNext()){
				Document doc=cursor.next();
				array.put(jsonUtil.ParaFromDocument(doc));
			}
			
			result=array.toString();
			
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
		

		out.println("</HTML>");
		out.flush();
		out.close();
	}


	public void init() throws ServletException {
		// Put your code here
	}

	
	public static void main(String[] args) {
		String result="error";
		
		String userid="55e69cf4b9032a1698a66355";
		String schoolid="55e19fb039aeaf07481b08dc";
		String idcard ="201192239";
		
		TeamInfo team1=new TeamInfo();
		team1.setTeamLeader(new ObjectId(userid));
		
		TeamInfo team2=new TeamInfo();
		team2.setSchoolId(new ObjectId(schoolid));
		team2.setIdCard(idcard);
		
		TeamInfo team3=new TeamInfo();
		ArrayList<Member> member=new ArrayList<Member>();
		Member mm=new Member();
		mm.setStuId(new ObjectId(userid));
		member.add(mm);		
		team3.setMember(member);
		
		TeamInfo team4=new TeamInfo();
		ArrayList<Member> member1=new ArrayList<Member>();
		Member mm1=new Member();
		mm1.setSchoolId(new ObjectId(schoolid));
		mm1.setIdCard(idcard);
		member1.add(mm1);
		team4.setMember(member1);
		
		BasicDBObject pro=new BasicDBObject();
		pro.put(StaticString.TeamInfo_ActivityName, 1);
		pro.put(StaticString.TeamInfo_Name, 1);
		pro.put(StaticString.TeamInfo_LeaderName, 1);
		pro.put(StaticString.TeamInfo_TeamLeader, 1);
		pro.put(StaticString.TeamInfo_IdCard, 1);
		pro.put(StaticString.Member_StuId, 1);
		pro.put(StaticString.Member_Name, 1);
		pro.put(StaticString.Member_MajorName, 1);
		pro.put(StaticString.Member_Degree, 1);
		pro.put(StaticString.Member_Grade, 1);
		pro.put(StaticString.Member_Phone, 1);
		pro.put(StaticString.Member_Abstract, 1);
		pro.put(StaticString.Member_State, 1);
		pro.put(StaticString.Member_IdCard, 1);
		
		CreateOrQuery or=new CreateOrQuery();
		try {
			or.Add(CreateQueryFromBean.EqualObj(team1));
			or.Add(CreateQueryFromBean.EqualObj(team2));
			or.Add(CreateQueryFromBean.EqualObj(team3));
			or.Add(CreateQueryFromBean.EqualObj(team4));
			
			System.out.println(or.GetResult().toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		try{
			JSONArray array=new JSONArray();
			MongoCursor<Document> cursor=DaoImpl.GetSelectCursor(TeamInfo.class, or.GetResult(), new BasicDBObject(StaticString.TeamInfo_id,-1), pro);
			while(cursor.hasNext()){
				Document doc=cursor.next();
				System.out.println(doc.toString());
				array.put(jsonUtil.ParaFromDocument(doc));
			}
			
			result=array.toString();
			
		}catch(Exception e){
			result="error";
		}
		
		System.out.println(result);
	}
	
}
