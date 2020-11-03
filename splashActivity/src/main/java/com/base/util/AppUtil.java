package com.base.util;

import java.util.HashMap;

public class AppUtil {

	/**
	 * 根据网络请求返回的code值，查找对应的描述文本，用于开发调试
	 * @param code
	 */
	public static String getErrorMsg(String code) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("InvalidInputFormat"	, "输入参数格式错误");
		map.put("ObjectNotFound"	, "对象不存在");
		map.put("InvalidRequestMethod"	, "错误的请求方法");
		map.put("ThirdPlatformServiceError"	, "第三方平台服务不可用");
		map.put("DataConflict"	, "数据冲突(重复，已定义)");
		map.put("Unknown"	, "未知错误");
		map.put("HasBanWord","输入有违禁词");
		map.put("InvalidValueType","数据类型错误");
		map.put("RequiredParameterMissing","缺少必选参数");
		String str = map.get(code);
		if(str != null) {
			return map.get(code);
		} else {
			return "未知异常";
		}
	}
}
