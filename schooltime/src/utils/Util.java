package utils;

public class Util {
	public static String DoGetString(String str) throws Exception{
		//byte [] bs = str.getBytes("ISO8859-1");
		//return new String(bs,"UTF-8");
		return str;
	}
	public static String GetOutActivityCategory(String x){
		if(x.equals("1")){
			return "讲座";
		}
		if(x.equals("2")){
			return "比赛";
		}
		if(x.equals("3")){
			return "公益";
		}
		if(x.equals("4")){
			return "其他";
		}
		if(x.equals("5")){
			return "其他";
		}
		return "其他";
	}
}
