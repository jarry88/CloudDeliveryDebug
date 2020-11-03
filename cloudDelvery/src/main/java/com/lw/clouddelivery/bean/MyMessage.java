package com.lw.clouddelivery.bean;

public class MyMessage {
	private String uid;
	private String id;
	private long time;
	private String reason;
	private String uname;
	private String xiaoxi;
	private String type;
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getXiaoxi() {
		return xiaoxi;
	}
	public void setXiaoxi(String xiaoxi) {
		this.xiaoxi = xiaoxi;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "Message [uid=" + uid + ", id=" + id + ", time=" + time
				+ ", reason=" + reason + ", uname=" + uname + ", xiaoxi="
				+ xiaoxi + ", type=" + type + "]";
	}
}
