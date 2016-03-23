package bean;

import java.util.Date;
import org.bson.types.ObjectId;

public class ActivityIntegralTable {
	private ObjectId _id;
	private String Name;
	private Date CreateTime;
	public ObjectId get_id() {
		return _id;
	}
	public void set_id(ObjectId _id) {
		this._id = _id;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public Date getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(Date createTime) {
		CreateTime = createTime;
	}
	
}
