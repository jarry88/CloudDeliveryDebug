package com.lw.clouddelivery.util;

import com.google.gson.annotations.SerializedName;

/**
 * data部分是对象的json
 * @author leon
 *
 * @param <T>
 */
public class JsonObjBase<T> extends JsonBase {
	@SerializedName("date")
	T date;

	public T getDate() {
		return date;
	}

	public void setDate(T date) {
		this.date = date;
	}
}
