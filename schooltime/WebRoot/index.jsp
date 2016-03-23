<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.danga.MemCached.MemCachedClient"%>
<%@page import="com.danga.MemCached.SockIOPool"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  
  <body>
  
  <%
  MemCachedClient mcc = new MemCachedClient();     
  String[] servers ={"114.215.87.133:11211"};             
	         
	    Integer[] weights = { 3 };             
	         
	    //创建一个实例对象SockIOPool           
	    SockIOPool pool = SockIOPool.getInstance();             
	         
	    // set the servers and the weights          
	    //设置Memcached Server          
	    pool.setServers( servers );             
	    pool.setWeights( weights );             
	         
	    // set some basic pool settings             
	    // 5 initial, 5 min, and 250 max conns             
	    // and set the max idle time for a conn             
	    // to 6 hours             
	    pool.setInitConn( 5 );             
	    pool.setMinConn( 5 );             
	    pool.setMaxConn( 250 );             
	    pool.setMaxIdle( 1000 * 60 * 60 * 6 );             
	         
	    // set the sleep for the maint thread             
	    // it will wake up every x seconds and             
	    // maintain the pool size             
	    pool.setMaintSleep( 30 );             
	         
	    // Tcp的规则就是在发送一个包之前，本地机器会等待远程主机          
	              // 对上一次发送的包的确认信息到来；这个方法就可以关闭套接字的缓存，          
	              // 以至这个包准备好了就发；          
	              pool.setNagle( false );             
	    //连接建立后对超时的控制          
	              pool.setSocketTO( 3000 );          
	    //连接建立时对超时的控制          
	              pool.setSocketConnectTO( 0 );             
	         
	    // initialize the connection pool             
	    //初始化一些值并与MemcachedServer段建立连接          
	              pool.initialize();          
	                 
	         
	    // lets set some compression on for the client             
	    // compress anything larger than 64k        
	    mcc.setCompressEnable( true );             
	    mcc.setCompressThreshold( 64 * 1024 );   
	    
	    String str=mcc.get("userid").toString();
		
		if(str==null){
			out.println("BBBBBBB");
		}else{
			out.println(str);
			out.println("AAAAA");
		}
   
  %>
  
    This 状态法. <br>
  </body>
</html>
