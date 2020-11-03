package com.lw.clouddelivery.util;


import com.base.util.LogWrapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lw.clouddelivery.bean.Vehicle;


public class JsonDataParse<T> {
	
	private static Gson gson = new Gson();
	
	public  JsonArrayBase<T> createArrayEntity(String jsonData,TypeToken typeToken) {
		try {
			JsonArrayBase<T> arrayBase = gson.fromJson(jsonData,typeToken.getType());
			return arrayBase;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public  JsonObjBase<T> createObjEntity(String jsonData,TypeToken typeToken) {
		try {
			JsonObjBase<T> objBase = gson.fromJson(jsonData,typeToken.getType());
			return objBase;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 *   简单接口返回JsonBase对象
	 * @param jsonData
	 * @return
	 */
	public JsonBase creaBaseEntity(String jsonData) {
		try {
			JsonBase jsonBase = gson.fromJson(jsonData, JsonBase.class);
			return jsonBase;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
