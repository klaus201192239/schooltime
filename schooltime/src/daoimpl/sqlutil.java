package daoimpl;


import java.util.Map;

public class sqlutil {
	public static String insertSql(String JavaBeanName, Object obj) {
		String keyList = "";
		String valuesList = "";
		Map<String, Object> map = null;
		try {
			map = utils.beanReflect.getReflectMap(obj);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (String key : map.keySet()) {
			keyList = keyList + key + ",";
			valuesList = valuesList + "'" + map.get(key) + "',";
		}
		String sql = "insert into " + JavaBeanName + "("
				+ keyList.substring(0, keyList.length() - 1) + ")" + " values"
				+ "(" + valuesList.substring(0, valuesList.length() - 1) + ")";
		return sql;
	}

	public static String deleteSql(String JavaBeanName, Object obj) {
		String sql="delete from "+JavaBeanName+" where";
		Map<String, Object> map = null;
		try {
			map = utils.beanReflect.getReflectMap(obj);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (String key : map.keySet()) {
			sql=sql+" "+key+"='"+map.get(key)+"' and";
		}
		return sql.substring(0, sql.length()-3);
	}
	public static String updateSql(String JavaBeanName, Object Oldobj,Object Newobj) {
		String newList = "";
		String oldList = "";
		String sql="update "+JavaBeanName +" set ";
		Map<String, Object> map = null;
		try {
			map = utils.beanReflect.getReflectMap(Newobj);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (String key : map.keySet()) {
			newList=newList+key+"='"+map.get(key)+"',";
		}
		map.clear();
		map=null;
		try {
			map = utils.beanReflect.getReflectMap(Oldobj);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (String key : map.keySet()) {
			oldList=oldList+key+"='"+map.get(key)+"' and ";
		}
		sql=sql+newList.substring(0, newList.length()-1)+" where "+oldList.substring(0, oldList.length()-4);
		return sql;
	}
	public static String selectSql(String JavaBeanName, Object obj) {	
		String whereList = "";
		if(obj==null){
			String sql="select * from "+JavaBeanName ;
			return sql;
		}else{
			String sql="select * from "+JavaBeanName +" where ";
			Map<String, Object> map = null;
			try {
				map = utils.beanReflect.getReflectMap(obj);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for (String key : map.keySet()) {
				whereList=whereList+key+"='"+map.get(key)+"' and ";
			}
			map.clear();
			map=null;
			sql=sql+whereList.substring(0, whereList.length()-4);
			return sql;
		}
		
	}
}
