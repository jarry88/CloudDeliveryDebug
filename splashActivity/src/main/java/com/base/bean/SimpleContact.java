package com.base.bean;
/**
 * 简单联系人实体类
 * @author leon
 *
 */
public class SimpleContact {

	private String name;
	private String[] phone; // 联系人有的手机号码
	public SimpleContact() {};
	public SimpleContact(String name, String[] phone) {
		super();
		this.name = name;
		this.phone = phone;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String[] getPhone() {
		return phone;
	}
	public void setPhone(String[] phone) {
		this.phone = phone;
	}
}
