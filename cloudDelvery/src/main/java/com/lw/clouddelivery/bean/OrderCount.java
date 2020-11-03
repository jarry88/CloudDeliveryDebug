package com.lw.clouddelivery.bean;

/**
 * 订单完成情况
 * @author leon
 *
 */
public class OrderCount {
	private String count;
	private double money = 0;
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
}
