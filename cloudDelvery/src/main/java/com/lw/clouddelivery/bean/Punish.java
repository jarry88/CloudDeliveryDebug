package com.lw.clouddelivery.bean;

/*奖惩
 *  
 */
public class Punish {
	private int id;
	private String recard_no;
	private long recard_date;
	private String recard_type; //A001充值，A002提现，AA003消费，A004退款
	private double income;
	private double spending;
	private double surplus;
	private String instruction;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRecard_no() {
		return recard_no;
	}
	public void setRecard_no(String recard_no) {
		this.recard_no = recard_no;
	}
	public long getRecard_date() {
		return recard_date;
	}
	public void setRecard_date(long recard_date) {
		this.recard_date = recard_date;
	}
	public String getRecard_type() {
		return recard_type;
	}
	public void setRecard_type(String recard_type) {
		this.recard_type = recard_type;
	}
	public double getIncome() {
		return income;
	}
	public void setIncome(double income) {
		this.income = income;
	}
	public double getSpending() {
		return spending;
	}
	public void setSpending(double spending) {
		this.spending = spending;
	}
	public double getSurplus() {
		return surplus;
	}
	public void setSurplus(double surplus) {
		this.surplus = surplus;
	}
	public String getInstruction() {
		return instruction;
	}
	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}
}
