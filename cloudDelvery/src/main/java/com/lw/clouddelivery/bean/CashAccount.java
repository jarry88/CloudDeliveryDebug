package com.lw.clouddelivery.bean;

/**
 * 现金账户
 * @author leon
 *
 */
public class CashAccount {
	private int id;
	private int uid;
	private double djPrice; //冻结金额
	private double kyPrice; //可用金额
	private double totlePrice; //总金额
	private double yjPrice; //月结金额
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public double getDjPrice() {
		return djPrice;
	}
	public void setDjPrice(double djPrice) {
		this.djPrice = djPrice;
	}
	public double getKyPrice() {
		return kyPrice;
	}
	public void setKyPrice(double kyPrice) {
		this.kyPrice = kyPrice;
	}
	public double getTotlePrice() {
		return totlePrice;
	}
	public void setTotlePrice(double totlePrice) {
		this.totlePrice = totlePrice;
	}
	public double getYjPrice() {
		return yjPrice;
	}
	public void setYjPrice(double yjPrice) {
		this.yjPrice = yjPrice;
	}
}
