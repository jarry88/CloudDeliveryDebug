package com.lw.clouddelivery.util;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.tsz.afinal.FinalDb;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

@SuppressLint("NewApi")
public class CommTool {
	private static String TAG = "CommTool";
	/**
	 * @Title: getScreenWidth
	 * @Description: 获取手机屏幕宽度
	 * @param context
	 * @return
	 * @throws
	 */
	public static int getScreenWidth(Context context) {
		if (context != null) {
			WindowManager wm = (WindowManager) context
					.getSystemService(Context.WINDOW_SERVICE);
			DisplayMetrics dm = new DisplayMetrics();
			wm.getDefaultDisplay().getMetrics(dm);
			return dm.widthPixels;
		}
		return 0;
	}

	/**
	 * 得到状态栏高度
	 * @return
	 */
	public static int getStatusBarHeight(Activity act) {
		
		/*
		 * 方法一，荣耀3c无效 Rect frame = new Rect();
		 * act.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		 * int statusBarHeight = frame.top; return statusBarHeight;
		 */

		/*
		 * 方法二，荣耀3c无效 Rect rectgle= new Rect(); Window window= act.getWindow();
		 * window.getDecorView().getWindowVisibleDisplayFrame(rectgle); int
		 * StatusBarHeight= rectgle.top; int contentViewTop=
		 * window.findViewById(Window.ID_ANDROID_CONTENT).getTop(); int
		 * statusBar = contentViewTop - StatusBarHeight; return statusBar;
		 */
		
		//方法三，荣耀3c有效
		Class<?> c = null;
		Object obj = null;
		Field field = null;
		int x = 0, sbar = 0;
		try {
			c = Class.forName("com.android.internal.R$dimen");
			obj = c.newInstance();
			field = c.getField("status_bar_height");
			x = Integer.parseInt(field.get(obj).toString());
			sbar = act.getResources().getDimensionPixelSize(x);
			return sbar;
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return 0;
	}

	/**
	 * @Title: getScreenHeight
	 * @Description: 获取手机屏幕高度
	 * @param context
	 * @return
	 * @throws
	 */
	public static int getScreenHeight(Context context) {
		if (context != null) {
			WindowManager wm = (WindowManager) context
					.getSystemService(Context.WINDOW_SERVICE);
			DisplayMetrics dm = new DisplayMetrics();
			wm.getDefaultDisplay().getMetrics(dm);
			return dm.heightPixels;
		}
		return 0;
	}

	/**
	 * @Title: dpToPx
	 * @Description: dp转换为px
	 * @param context
	 * @param dp
	 * @return
	 * @throws
	 */
	public static int dpToPx(Context context, int dp) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dp * scale + 0.5f);
	}
	
	public static int dpToPx(Context context, double dp) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dp * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int pxToDp(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 首字母转大写
	 * 
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
	 * 
	 * @param secMills
	 * @return
	 */
	public static String date2CNStr(long secMills) {
		Date date = new Date(secMills);
		String str = new SimpleDateFormat("yyyy-MM-dd").format(date);
		return str;
	}

	/**
	 * 检查网络是否连接
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isNetworkConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager
					.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
		}
		
		return false;
	}

	/**
	 * 判断程序权限
	 * 
	 * @param context
	 */
	public void hasContactPermission(Context context) {
		StringBuffer appNameAndPermissions = new StringBuffer();
		PackageManager pm = context.getPackageManager();
		List<ApplicationInfo> packages = pm
				.getInstalledApplications(PackageManager.GET_META_DATA);
		for (ApplicationInfo applicationInfo : packages) {
			try {
				PackageInfo packageInfo = pm.getPackageInfo(
						applicationInfo.packageName,
						PackageManager.GET_PERMISSIONS);
				appNameAndPermissions.append(packageInfo.packageName + "*:\n");
				// Get Permissions
				String[] requestedPermissions = packageInfo.requestedPermissions;
				if (requestedPermissions != null) {
					for (int i = 0; i < requestedPermissions.length; i++) {
						Log.d("test", requestedPermissions[i]);
						appNameAndPermissions.append(requestedPermissions[i]
								+ "\n");
					}
					appNameAndPermissions.append("\n");
				}
			} catch (NameNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 获取程序版本号
	 * 
	 * @return 当前应用的版本号
	 */
	public static String getAppVersion(Context context) {
		try {
			PackageManager manager = context.getPackageManager();
			PackageInfo info = manager.getPackageInfo(context.getPackageName(),
					0);
			String version = info.versionName;
			return version;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 获取当前设备型号
	 * @param context
	 * @return
	 */
	public static String getDeviceModel(Context context) {
		Build bd = new Build();    
		String model = bd.MODEL;   
		return model;
	}
	
	/**
	 * 获取当前设备系统版本
	 * @param context
	 * @return
	 */
	public static String getSDKVersion(Context context) {
		String model = Build.VERSION.RELEASE;   
		return model;
	}
	

	public static String removeTagFromText(String content) {
		Pattern p = null;
		Matcher m = null;
		String value = null;

		// 去掉<>标签
		p = Pattern.compile("(<[^>]*>)");
		m = p.matcher(content);
		String temp = content;
		while (m.find()) {
			value = m.group(0);
			temp = temp.replace(value, "");
		}

		// 去掉换行或回车符号
		p = Pattern.compile("(/r+|/n+)");
		m = p.matcher(temp);
		while (m.find()) {
			value = m.group(0);
			temp = temp.replace(value, " ");
		}

		return temp;
	}

	// 删除表中所有数据
	public static void deleteFromTable(FinalDb db, Class<?> cls) {

		// strWhere 为空时，删除表中所有数据
		db.deleteByWhere(cls, null);
	}

	/**
	 * 从文件路径中解析出文件名
	 * 
	 * @param path
	 * @return
	 */
	public static String getFileNameFromPath(String path) {
		int divideIndex = path.lastIndexOf("/");
		return path.substring(divideIndex);
	}

	/**
	 * 读取当前程序包名
	 * @param context
	 * @return
	 */
	public static String getPackageName(Context context) {
		PackageInfo info;
		try {
			info = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
			return info.packageName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 验证手机号码的合法性(1开头，11位数字)
	 * 
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
	 * 实现文本复制功能
	 * 
	 * @param content
	 */
	public static void copy(String content, Context context) {
		// 得到剪贴板管理器
		ClipboardManager cmb = (ClipboardManager) context
				.getSystemService(Context.CLIPBOARD_SERVICE);
		cmb.setText(content.trim());
	}

	/**
	 * 实现粘贴功能 a
	 * 
	 * @param context
	 * @return
	 */
	public static String paste(Context context) {
		// 得到剪贴板管理器
		ClipboardManager cmb = (ClipboardManager) context
				.getSystemService(Context.CLIPBOARD_SERVICE);
		return cmb.getText().toString().trim();
	}


	/**
	 * 将不是手机号剔除
	 * 
	 * @param mobiles
	 * @return
	 */
	private static boolean negMobileNum(String mobiles) {
		mobiles = mobiles.replaceAll(" ", "");
		if (mobiles.trim().length() < 11) {
			return true;
		} else {
			String subMobile = mobiles.substring(mobiles.length() - 11,
					mobiles.length());

			Pattern p = Pattern
					.compile("^((13[0-9])|(15[^4,\\D])|(14[0,0-9])|(18[0,0-9]))\\d{8}$");
			Matcher m = p.matcher(subMobile);
			boolean isMatches = m.matches();
			return !isMatches;
		}
	}
	
	
	/**
	 * 获取状态栏的高度
	 * @param context
	 * @return
	 */
	public static int getStatusHeight(Context context){
        int statusHeight = 0;
        Rect localRect = new Rect();
        ((Activity) context).getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);
        statusHeight = localRect.top;
        if (0 == statusHeight){
            Class<?> localClass;
            try {
                localClass = Class.forName("com.android.internal.R$dimen");
                Object localObject = localClass.newInstance();
                int i5 = Integer.parseInt(localClass.getField("status_bar_height").get(localObject).toString());
                statusHeight = context.getResources().getDimensionPixelSize(i5);
            } catch (Exception e) {
                e.printStackTrace();
            } 
        }
        return statusHeight;
    }
	/**
	 * 判断密码设定密码(6-12位字母数字组合)
	 * @param str
	 * @return
	 */
	public static boolean match(String str){
		String regex ="^[a-zA-Z0-9-x21-x7e]{6,12}$";
		boolean b=str.matches(regex);
		return b;
	}
	
}

