package com.lw.clouddelivery.bean;

public class MyIncoming {

	private double yuemoney; //本月收入
	private double kymoney; //可用收入
	private double yjmoney; //月结收入
	private double countmoney; //总收入
	private double zrmoney; //昨日收入
	public double getYuemoney() {
		return yuemoney;
	}
	public void setYuemoney(double yuemoney) {
		this.yuemoney = yuemoney;
	}
	public double getCountmoney() {
		return countmoney;
	}
	public void setCountmoney(double countmoney) {
		this.countmoney = countmoney;
	}
	public double getZrmoney() {
		return zrmoney;
	}
	public void setZrmoney(double zrmoney) {
		this.zrmoney = zrmoney;
	}
	public double getKymoney() {
		return kymoney;
	}
	public void setKymoney(double kymoney) {
		this.kymoney = kymoney;
	}
	public double getYjmoney() {
		return yjmoney;
	}
	public void setYjmoney(double yjmoney) {
		this.yjmoney = yjmoney;
	}
}
