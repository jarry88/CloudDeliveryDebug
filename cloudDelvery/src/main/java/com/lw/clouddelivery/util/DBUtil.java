package com.lw.clouddelivery.util;

import java.util.ArrayList;

import net.tsz.afinal.FinalDb;
import net.tsz.afinal.db.sqlite.DbModel;
import android.content.Context;
import android.database.SQLException;

import com.base.util.LogWrapper;
import com.lw.clouddelivery.bean.Order;
import com.lw.clouddelivery.ui.YS_Step1Activity;

public class DBUtil {

	private static DBUtil instance = null;
	private static FinalDb db;
	private DBUtil() {}
	
	public synchronized static DBUtil getInstance(Context c) {
		if(instance == null) {
			instance = new DBUtil();
			db = FinalDb.create(c,"ys.db");
		}
		return instance;
	}
	
	/**
	 * 保存或更新Order数据
	 * @param order
	 */
	public void saveOrder(Order order) {
		DbModel dbModel = null;
		try {
			dbModel = db.findDbModelBySQL("select * from  _Order where id=" + order.getId());
		} catch(SQLException e) {
			e.printStackTrace();
		}
		if(dbModel == null) {
			db.save(order);
			LogWrapper.e(DBUtil.class,"保存订单,id=" + order.getId());
		} else {
			updateOrder(order);
			LogWrapper.e(DBUtil.class,"更新订单,id=" + order.getId());
		}
		
		ArrayList<Order> orderList2 = (ArrayList<Order>) db.findAll(Order.class);
		LogWrapper.e(DBUtil.class, "从数据库读取到" + orderList2.size() + "条订单数据");
		for(int i=0;i<orderList2.size();i++) {
			LogWrapper.e(DBUtil.class, orderList2.get(i).toString());
		}
	}
	
//	/**
//	 *  保存或更新Order数组数据
//	 * @param orderList
//	 */
//	public void saveOrderList(ArrayList<Order> orderList) {
//		for(int i=0;i<orderList.size();i++) {
//			Order order = orderList.get(i);
//			DbModel dbModel = null;
//			try {
//				dbModel = db.findDbModelBySQL("select * from  _Order where orderno=\"" + order.getOrderno() + "\"");
//			} catch(SQLException e) {
//				e.printStackTrace();
//			}
//			if(dbModel == null) {
//				db.save(order);
//			} else {
//				updateOrder(order);
//			}
//		}
//		ArrayList<Order> orderList2 = (ArrayList<Order>) db.findAll(Order.class);
//		LogWrapper.e(DBUtil.class, "从数据库读取到" + orderList2.size() + "条订单数据");
//		for(int i=0;i<orderList2.size();i++) {
//			LogWrapper.e(DBUtil.class, orderList2.get(i).toString());
//		}
//	}
	
	
	/**
	 * 更新订单的当前动作
	 */
	private void updateOrder(Order order) {
		db.update(order, "id=" + order.getId());
	}
	
	/**
	 * 删除指定订单记录
	 */
	public void deleteOrder(Order order) {
		db.deleteByWhere(Order.class, "id=\"" + order.getId() + "\"");
	}
	
	public long getQiangdanTime(Order order) {
		ArrayList<Order> dbOrder  = (ArrayList<Order>) db.findAllByWhere(Order.class,"id=" + order.getId());
		if(dbOrder != null && dbOrder.size() > 0) {
			return dbOrder.get(0).getQiangdanTime();
		} else {
			return -1;
		}
	}
//	public void updateOrderStep(Order order,int step) {
//		ArrayList<Order> dbOrder  = (ArrayList<Order>) db.findAllByWhere(Order.class,"orderno=\"" + order.getOrderno() + "\"");
//		if(dbOrder != null && dbOrder.size() > 0) {
//			Order order2 = dbOrder.get(0);
//			order2.setStep(step);
//			db.update(order2);
//		} 
//	}
	
	public int getOrderStep(Order order) {
		ArrayList<Order> dbOrder  = (ArrayList<Order>) db.findAllByWhere(Order.class,"id=" + order.getId());
		if(dbOrder != null && dbOrder.size() > 0) {
			return dbOrder.get(0).getStep();
		} else {
			return -1;
		}
	}
}
