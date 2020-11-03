package com.lw.clouddelivery.bean;

import java.io.Serializable;

/**
 * @Title: User.java
 * @Package com.fuqi.app_shanke.entity
 * @Description: 用户信息Entity
 * @author user_tq
 * @date 2015-8-24 下午1:21:25
 * @version V1.0
 */
public class User implements Serializable  {
	private int totalcore;//积分
	private int logid;// UserInfo 表ID
	private int infoId;//UserId
	private int sex;//性别
	private String uname;//用户姓名
	private String mobile;//用户手机号
	private String birthday;//生日
	private String headsrc;//头像地址
	private String roleid;//系统角色
	private String password;// 用户密码
	private String crediId;// 积分表ID
	private String regtime;// 注册或新增时间
	private String address;// 地址
	private String card;// 证件号
	private String Code;// 请求状态码
	
	private String score;//考试分数
	

	private String img2;// "http://192.168.1.2:8080/ys/upload/2016-09-13/1520160913110014.png",
	private String img1;// "http://192.168.1.2:8080/ys/upload/2016-09-13/1020160913110014.png",
	private String weight;// "12",
	private String add;// "city",
	private String height;// "12",
	private String img3;// "http://192.168.1.2:8080/ys/upload/2016-09-13/920160913110014.png",
    
    
    
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getImg2() {
		return img2;
	}
	public void setImg2(String img2) {
		this.img2 = img2;
	}
	public String getImg1() {
		return img1;
	}
	public void setImg1(String img1) {
		this.img1 = img1;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getAdd() {
		return add;
	}
	public void setAdd(String add) {
		this.add = add;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getImg3() {
		return img3;
	}
	public void setImg3(String img3) {
		this.img3 = img3;
	}
	public int getTotalcore() {
		return totalcore;
	}
	public void setTotalcore(int totalcore) {
		this.totalcore = totalcore;
	}
	public int getLogid() {
		return logid;
	}
	public void setLogid(int logid) {
		this.logid = logid;
	}
	public int getInfoId() {
		return infoId;
	}
	public void setInfoId(int infoId) {
		this.infoId = infoId;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getHeadsrc() {
		return headsrc;
	}
	public void setHeadsrc(String headsrc) {
		this.headsrc = headsrc;
	}
	public String getRoleid() {
		return roleid;
	}
	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCrediId() {
		return crediId;
	}
	public void setCrediId(String crediId) {
		this.crediId = crediId;
	}
	public String getRegtime() {
		return regtime;
	}
	public void setRegtime(String regtime) {
		this.regtime = regtime;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCard() {
		return card;
	}
	public void setCard(String card) {
		this.card = card;
	}
	public String getCode() {
		return Code;
	}
	public void setCode(String code) {
		Code = code;
	}
}
