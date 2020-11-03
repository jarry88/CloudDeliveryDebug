package com.lw.clouddelivery.util;

import android.content.Context;

import com.base.util.BaseSPTool;
import com.lw.clouddelivery.conf.INI;

public class MySPTool extends BaseSPTool {

	/**
	 * 读取用户id
	 * @param c
	 * @return
	 */
	public static String getUid(Context c) {
		return getString(c, INI.SP.UID, INI.UID);
	}
	public static String getLat(Context c) {
		return getString(c, INI.SP.LAT, INI.LAT);
	}
	public static String getLon(Context c) {
		return getString(c, INI.SP.LON, INI.LON);
	}
}
