package com.lw.clouddelivery.util;

import java.util.List;

import android.widget.Toast;

import com.google.gson.annotations.SerializedName;
/**
 * data部分是数组的json
 * @author leon
 * @param <T>
 *
 * @param <T>
 */
public class JsonArrayBase<T> extends JsonBase {
	
	@SerializedName("date")
	List<T> date;

	public List<T> getDate() {
		return date;
	}
	public void setDate(List<T> date) {
		this.date = date;
	}	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<date.size();i++) {
			sb.append(date.get(i).toString());
			System.out.println(date.size()+"zzzzzzz");
		}
		return sb.toString();
	}
	
}
