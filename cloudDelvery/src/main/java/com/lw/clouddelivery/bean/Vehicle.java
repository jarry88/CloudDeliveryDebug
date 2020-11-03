package com.lw.clouddelivery.bean;

import java.io.Serializable;

public class Vehicle implements Serializable {
	private String id;
	private long time;
	private String name;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "Vehicle [id=" + id + ", time=" + time + ", name=" + name + "]";
	}
}
