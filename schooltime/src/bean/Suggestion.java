package bean;

import java.util.Date;
import org.bson.types.ObjectId;

public class Suggestion {
	
	private ObjectId _id;
	private String From;
	private String Content;
	private Date ReleaseTime;
	
	public ObjectId get_id() {
		return _id;
	}
	public void set_id(ObjectId _id) {
		this._id = _id;
	}
	public String getFrom() {
		return From;
	}
	public void setFrom(String from) {
		From = from;
	}
	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}
	public Date getReleaseTime() {
		return ReleaseTime;
	}
	public void setReleaseTime(Date releaseTime) {
		ReleaseTime = releaseTime;
	}
	
}
