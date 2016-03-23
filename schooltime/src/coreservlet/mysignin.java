package coreservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.Document;
import org.bson.types.ObjectId;

import staticData.StaticString;
import bean.ActivityCallOver;
import bean.TableContentColumn;
import bean.TableContentInfo;
import bean.TableInfo;

import com.dbDao.CreateQueryFromBean;
import com.dbDao.DaoImpl;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCursor;

public class mysignin extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private static final double EARTH_RADIUS = 6378.137;

	public mysignin() {
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
		
		String userid=request.getParameter("userid");
		String activityid=request.getParameter("activityid");
		double x=Double.parseDouble(request.getParameter("localx"));
		double y=Double.parseDouble(request.getParameter("localy"));
		ObjectId tableId=null;
		String result="ok";
		
		
		TableInfo table=new TableInfo();
		table.setActivityId(new ObjectId(activityid));
		table.setType(0);
		BasicDBObject pro=new BasicDBObject(StaticString.TableInfo_id,1);
		
		try{
			int tag=0;
			
			ActivityCallOver callover=new ActivityCallOver();
			callover.setActivityId(new ObjectId(activityid));

			MongoCursor<Document> cur0=DaoImpl.GetSelectCursor(ActivityCallOver.class, CreateQueryFromBean.EqualObj(callover), new BasicDBObject());
			while(cur0.hasNext()){
				Document doc=cur0.next();
				
				Date da=doc.getDate("Deadline");
				double localX=doc.getDouble("PositionX");
				double localY=doc.getDouble("PositionY");
				
				Calendar c=Calendar.getInstance();
				Date db=c.getTime();
				if(db.after(da)){
					tag=2;//时间已经过了点名时间
					result="timeover";

				}
				else{
					if(localX!=0.0&&localY!=0.0){

						
						if(x==0||y==0){
							tag=1;//允许签到
						}
						else{
							double lenth=GetDistance(y,x,localY,localX);

							if(lenth>500){
		
								tag=3;//超越了地理位置
								result="long";
								
							}else{	
								tag=1;//允许签到
							}
						}
						
					}else{
						tag=1;//允许签到
					}
				}
				
			}
			
			if(tag==1){
				MongoCursor<Document> cur=DaoImpl.GetSelectCursor(TableInfo.class, CreateQueryFromBean.EqualObj(table), pro);
				while(cur.hasNext()){
					Document doc=cur.next();
					tableId=doc.getObjectId("_id");
				}
				
				
				TableContentColumn column=new TableContentColumn();
				column.setName("SignIn");
				ArrayList<TableContentColumn > clist=new ArrayList<TableContentColumn>();
				clist.add(column);
				
				TableContentInfo tainfo=new TableContentInfo();
				tainfo.setTableId(tableId);
				tainfo.setStuId(new ObjectId(userid));
				tainfo.setTableContentColumn(clist);
				
				TableContentColumn column1=new TableContentColumn();
				column1.setContent("1");
				ArrayList<TableContentColumn > clist1=new ArrayList<TableContentColumn>();
				clist1.add(column1);
				
				TableContentInfo tainfo1=new TableContentInfo();
				tainfo1.setTableContentColumn(clist1);
				
				
				DaoImpl.update(CreateQueryFromBean.EqualObj(tainfo), tainfo1,true);
				
			}
			if(tag==0){
				result="wrong";
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
		// Put your code here
	}
	
	private double rad(double d)
	{
	   return d * Math.PI / 180.0;
	}
	
	public double GetDistance(double lat1, double lng1, double lat2, double lng2)
	{
	   double radLat1 = rad(lat1);
	   double radLat2 = rad(lat2);
	   double a = radLat1 - radLat2;
	   double b = rad(lng1) - rad(lng2);
	   double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) + Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
	   s = s * EARTH_RADIUS;
	   s = Math.round(s * 10000) / 10000;
	   return s;
	}
	
	public static void main(String[] args) {
		double lat2=39.09294;double lng2=121.82478; double lat1=39.092595; double lng1=121.824955;
		double radLat1 = radd(lat1);
		   double radLat2 = radd(lat2);
		   double a = radLat1 - radLat2;
		   double b = radd(lng1) - radd(lng2);
		   double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) + Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
		   s = s * EARTH_RADIUS;
		   s = Math.round(s * 10000) / 10000;
		   
		   System.out.println(s*1000);
	}
	
	private static double radd(double d)
	{
	   return d * Math.PI / 180.0;
	}
	
}
