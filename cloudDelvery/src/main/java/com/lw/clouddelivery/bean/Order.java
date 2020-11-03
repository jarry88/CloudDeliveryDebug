package com.lw.clouddelivery.bean;

import java.io.Serializable;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Table;

@Table(name="_Order")
public class Order implements Serializable {
	@Id
	private int _id;//主键
	private int id; 
	private String uid;
	private String weight;
	private String deli_mobile; //寄件人手机号
	private String orderno; //订单号
	private String juli; //距离 
	private String sname; //收件人姓名
	private String type; //1普通用户，3企业用户
	private String jprice; // 物品价值
	private String dLat;//收件人纬度
	private String dLng; //收件人经度
	private long createtime; //字段名不一样，有一个是 creatime
	private String payState; //已支付，未支付
	private String yemp; //快递员
	private String paytype; //寄付
	private String reamaker; //用户备注
	private String ylat;//快递员纬度
	private String deli_name; //寄件人姓名
	private int totalamount; //订单总金额
	private String spwd; //收件密码
	private String Lat; //纬度
	private String ytime; //预约时间
	private String Lng; //经度
	private String ylng; //快递员所在经度
	private String creamaker;//处理备注
	private String deli_address; //寄件人地址
	private String ordername; //
	private String shopjl; //距离寄件位置距离
	private String saddress; //寄件人地址
	private String qpwd; //取件密码
	private String smobile; //寄件人号码
	//orderstatus表的id,1待抢单，2待取单，3闪送中，4已完成，5取消中(未处理)，6取消中(已处理)
	private String orderstatusId;
	private int step = -1; //1联系寄件方，2我已就位，3发送取件验证码，4物品确认，5联系收件方，6我已就位，7发送收件验证码，8物品确认
	public long qiangdanTime = -1;
	public long qujianTime = -1;
	public long shoujianTime = -1; //抢单时间，取单时间，收件时间
	
	public int isnew;//是否是最新订单 
	@Override
	public String toString() {
		return "Order [id=" + id + ", uid=" + uid + ", orderno=" + orderno
				+ ", juli=" + juli + ", sname=" + sname + ", type=" + type
				+ ", jprice=" + jprice + ", deli_name=" + deli_name
				+ ", totalamount=" + totalamount + ", spwd=" + spwd
				+ ", ordername=" + ordername + ", shopjl=" + shopjl + ", qpwd="
				+ qpwd + ", orderstatusId=" + orderstatusId + ",qiangdanTime=" + qiangdanTime 
				+ ",Lat=" + Lat +",Lng=" + Lng 
				+ ",ylat=" + ylat +",ylng=" + ylng 
				+ ",dLat=" + dLat +",dLng=" + dLng +"]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIsnew() {
		return isnew;
	}
	public void setIsnew(int isnew) {
		this.isnew = isnew;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getDeli_mobile() {
		return deli_mobile;
	}
	public void setDeli_mobile(String deli_mobile) {
		this.deli_mobile = deli_mobile;
	}
	public String getOrderno() {
		return orderno;
	}
	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}
	public String getJuli() {
		return juli;
	}
	public void setJuli(String juli) {
		this.juli = juli;
	}
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getJprice() {
		return jprice;
	}
	public void setJprice(String jprice) {
		this.jprice = jprice;
	}
	public String getDLat() {
		return dLat;
	}
	public void setDLat(String dLat) {
		this.dLat = dLat;
	}
	public String getDLng() {
		return dLng;
	}
	public void setDLng(String dLng) {
		this.dLng = dLng;
	}
	public long getCreatetime() {
		return createtime;
	}
	public void setCreatetime(long createtime) {
		this.createtime = createtime;
	}
	public String getPayState() {
		return payState;
	}
	public void setPayState(String payState) {
		this.payState = payState;
	}
	public String getYemp() {
		return yemp;
	}
	public void setYemp(String yemp) {
		this.yemp = yemp;
	}
	public String getPaytype() {
		return paytype;
	}
	public void setPaytype(String paytype) {
		this.paytype = paytype;
	}
	public String getReamaker() {
		return reamaker;
	}
	public void setReamaker(String reamaker) {
		this.reamaker = reamaker;
	}
	public String getYlat() {
		return ylat;
	}
	public void setYlat(String ylat) {
		this.ylat = ylat;
	}
	public String getDeli_name() {
		return deli_name;
	}
	public void setDeli_name(String deli_name) {
		this.deli_name = deli_name;
	}
	public int getTotalamount() {
		return totalamount;
	}
	public void setTotalamount(int totalamount) {
		this.totalamount = totalamount;
	}
	public String getSpwd() {
		return spwd;
	}
	public void setSpwd(String spwd) {
		this.spwd = spwd;
	}
	public String getLat() {
		return Lat;
	}
	public void setLat(String lat) {
		Lat = lat;
	}
	public String getYtime() {
		return ytime;
	}
	public void setYtime(String ytime) {
		this.ytime = ytime;
	}
	public String getLng() {
		return Lng;
	}
	public void setLng(String lng) {
		Lng = lng;
	}
	public String getYlng() {
		return ylng;
	}
	public void setYlng(String ylng) {
		this.ylng = ylng;
	}
	public String getCreamaker() {
		return creamaker;
	}
	public void setCreamaker(String creamaker) {
		this.creamaker = creamaker;
	}
	public String getDeli_address() {
		return deli_address;
	}
	public void setDeli_address(String deli_address) {
		this.deli_address = deli_address;
	}
	public String getOrdername() {
		return ordername;
	}
	public void setOrdername(String ordername) {
		this.ordername = ordername;
	}
	public String getShopjl() {
		return shopjl;
	}
	public void setShopjl(String shopjl) {
		this.shopjl = shopjl;
	}
	public String getSaddress() {
		return saddress;
	}
	public void setSaddress(String saddress) {
		this.saddress = saddress;
	}
	public String getQpwd() {
		return qpwd;
	}
	public void setQpwd(String qpwd) {
		this.qpwd = qpwd;
	}
	public String getOrderstatusId() {
		return orderstatusId;
	}
	public void setOrderstatusId(String orderstatusId) {
		this.orderstatusId = orderstatusId;
	}
	public long getQiangdanTime() {
		return qiangdanTime;
	}
	public void setQiangdanTime(long qiangdanTime) {
		this.qiangdanTime = qiangdanTime;
	}
	public long getQujianTime() {
		return qujianTime;
	}
	public void setQujianTime(long qujianTime) {
		this.qujianTime = qujianTime;
	}
	public long getShoujianTime() {
		return shoujianTime;
	}
	public void setShoujianTime(long shoujianTime) {
		this.shoujianTime = shoujianTime;
	}
	public String getdLat() {
		return dLat;
	}
	public void setdLat(String dLat) {
		this.dLat = dLat;
	}
	public String getdLng() {
		return dLng;
	}
	public void setdLng(String dLng) {
		this.dLng = dLng;
	}
	public String getSmobile() {
		return smobile;
	}
	public void setSmobile(String smobile) {
		this.smobile = smobile;
	}
	public int getStep() {
		return step;
	}
	public void setStep(int step) {
		this.step = step;
	}
	public int get_id() {
		return _id;
	}
	public void set_id(int _id) {
		this._id = _id;
	}
	
}
