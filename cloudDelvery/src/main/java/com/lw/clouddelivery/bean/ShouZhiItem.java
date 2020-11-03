package com.lw.clouddelivery.bean;

/**
 * 收支明细列表项
 * @author leon
 *
 */
public class ShouZhiItem {

	private int uid;
	private int id;
	private String recard_type; //记录类型，A001充值，A002提现，A003消费，A004退款
	private String recard_no;  //记录编号
	private double recard_date; 
	private double income; //收入
	private String instruction; //说明
	private double surplus = 0; //剩余余额
	private double spending = 0; //支出
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRecard_type() {
		return recard_type;
	}
	public void setRecard_type(String recard_type) {
		this.recard_type = recard_type;
	}
	public String getRecard_no() {
		return recard_no;
	}
	public void setRecard_no(String recard_no) {
		this.recard_no = recard_no;
	}
	public String getInstruction() {
		return instruction;
	}
	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}
	public double getRecard_date() {
		return recard_date;
	}
	public void setRecard_date(double recard_date) {
		this.recard_date = recard_date;
	}
	public double getIncome() {
		return income;
	}
	public void setIncome(double income) {
		this.income = income;
	}
	public double getSurplus() {
		return surplus;
	}
	public void setSurplus(double surplus) {
		this.surplus = surplus;
	}
	public double getSpending() {
		return spending;
	}
	public void setSpending(double spending) {
		this.spending = spending;
	}
}
