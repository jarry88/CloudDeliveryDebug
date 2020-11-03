package com.base.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.widget.TextView;

public class StringUtil {
	
	public static double parseDouble(String value) {
		try {
			return Double.parseDouble(value);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 首字母转大写
	 * @param s
	 * @return
	 */
	public static String toUpperCaseFirstOne(String s) {
		if (Character.isUpperCase(s.charAt(0)))
			return s;
		else
			return (new StringBuilder())
					.append(Character.toUpperCase(s.charAt(0)))
					.append(s.substring(1)).toString();
	}
	
	/**
	 * 时间毫秒数转时间字符串
	 * @param secMills
	 * @param 如:yyyy年MM月dd日 
	 * @return
	 */
	public static String date2CNStr(long secMills,String dateStrFormat) {
		Date date = new Date(secMills);
		String str = new SimpleDateFormat(dateStrFormat).format(date);
		return str;
	}
	
	/**
	 * 验证手机号码的合法性(1开头，11位数字)
	 * @param phone，待校验手机号
	 * @return true验证通过，false验证失败
	 */
	public static boolean validatePhoneNum(String phone) {
		String regx = "^[1][0-9]{10}$";
		return phone.matches(regx);
	}
	
	/**
	 * 时间字符串转毫秒数
	 * @return
	 */
	public static long dateStr2Long(String dateStr) {
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(dateStr));
		} catch (ParseException e) {
			
			e.printStackTrace();
			return 0;
		}
		return c.getTimeInMillis();
	}
	/**
	 * 判断给定的字符串是否是URL资源地址
	 * @param str
	 * @return
	 */
	public static boolean isURL(String str){
        //转换为小写
        str = str.toLowerCase();
        String regex = "^((https|http|ftp|rtsp|mms)?://)"  
                + "?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?" //ftp的user@  
               + "(([0-9]{1,3}\\.){3}[0-9]{1,3}" // IP形式的URL- 199.194.52.184  
                 + "|" // 允许IP和DOMAIN（域名） 
                 + "([0-9a-z_!~*'()-]+\\.)*" // 域名- www.  
                 + "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\\." // 二级域名  
                + "[a-z]{2,6})" // first level domain- .com or .museum  
                + "(:[0-9]{1,4})?" // 端口- :80  
                + "((/?)|" // a slash isn't required if there is no file name  
                + "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$";  
        return str.matches(regex);
    }
	
	/**
	 * 指定某些字符为粗体
	 * @param text 要填充的文本数据
	 * @param start 加粗开始位置
	 * @param end 加粗结束位置
	 */
	public static void boldTextRange(TextView tv,String text,int start,int end) {
		SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(text);
		spannableStringBuilder.setSpan(new StyleSpan(Typeface.BOLD)
			,start,end,Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		tv.setText(spannableStringBuilder);	
	}
}
