package com.base.util;

import java.io.File;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.base.bean.SimpleContact;

@SuppressLint("NewApi")
public class BaseCommTool {
	
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
			LogWrapper.e(BaseCommTool.class, "get status bar height fail");
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

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int pxToDp(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
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
	 * @return 如2.1，4.0.1， 5.0
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
	 * 获取程序label
	 * @param context
	 * @return
	 */
	public static String getAppLabel(Context context) {
		PackageInfo info;
		try {
			info = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
			return (String) info.applicationInfo.loadLabel(context.getPackageManager());
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
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
	 * 读取本地联系人数据
	 * @param context
	 * @return
	 */
	public static ArrayList<SimpleContact> getLocalContacts(Context context) {
		String[] PHONE_SELECTOIN = new String[] { Phone.DISPLAY_NAME,
				Phone.NUMBER, Phone.CONTACT_ID };
		int PHONE_DISPLAY_NAME_INDEX = 0;
		int PHONE_NUMBER_INDEX = 1;

		ArrayList<SimpleContact> contacts = new ArrayList<SimpleContact>();

		ContentResolver contentResolver = context.getContentResolver();
		Cursor cursor = contentResolver.query(Phone.CONTENT_URI,
				PHONE_SELECTOIN, null, null, "sort_key asc"); // 排序
		if (cursor != null) {

			while (cursor.moveToNext()) {

				String phoneNumber = cursor.getString(PHONE_NUMBER_INDEX)
						.replaceAll(" ", ""); // 获取电话
				if (TextUtils.isEmpty(phoneNumber)) {
					continue;
				}
				if (negMobileNum(phoneNumber)) {
					continue;
				}
				String name = cursor.getString(PHONE_DISPLAY_NAME_INDEX);
				SimpleContact bean = new SimpleContact(name,
						new String[] { phoneNumber });
				contacts.add(bean);
			}
		}
		return contacts;
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
	 * 判断是否有sim卡
	 * 
	 * @param act
	 * @return
	 */
	private static boolean isSimAvailable(Activity act) {

		try {
			TelephonyManager mgr = (TelephonyManager) act
					.getSystemService(Context.TELEPHONY_SERVICE);

			return TelephonyManager.SIM_STATE_READY == mgr.getSimState();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 调用系统发短信界面
	 */
	public static void callSystemSmsSend(Activity act,String phone,String content) {
		if(isSimAvailable(act)) {
			Intent intent = new Intent();

			//系统默认的action，用来打开默认的短信界面
			intent.setAction(Intent.ACTION_SENDTO);

			//需要发短息的号码
			intent.setData(Uri.parse("smsto:"+phone));
			intent.putExtra("sms_body", content);
			
			act.startActivity(intent);

		} else {
			MyToast.showText(act,"未发现有效的sim卡");
		}
	}
	
	/**
	 * 向EditText的光标位置插入内容
	 */
	public static void insertTextET(EditText editText,String text) {
		int index = editText.getSelectionStart();
		Editable editable = editText.getText();
		editable.insert(index,text);
	}
	
	/**
	 * 从毫秒数中解析出Date的字符串
	 * @param 0--yyyy/MM
	 * @param mills
	 * @return
	 */
	public static String getFormatStrFromMills(long mills,int format) {
		String formatStr = "";
		switch(format) {
		case 0:
			formatStr = "yyyy/MM";
			break;
		}
		SimpleDateFormat sdf= new SimpleDateFormat(formatStr);
		//前面的lSysTime是秒数，先乘1000得到毫秒数，再转为java.util.Date类型
		java.util.Date dt = new Date(mills);  
		String sDateTime = sdf.format(dt);  //得到精确到秒的表示：08/31/2006 21:08:00
		return sDateTime;
	}
	
	/**
	 * 从毫秒数中解析出Date的字符串
	 * @param 0--yyyy/MM
	 * @param mills
	 * @return
	 */
	public static String getFormatStrFromDate(Date date,int format) {
		String formatStr = "";
		switch(format) {
		case 0:
			formatStr = "yyyy/MM";
			break;
		}
		SimpleDateFormat sdf= new SimpleDateFormat(formatStr);
		//前面的lSysTime是秒数，先乘1000得到毫秒数，再转为java.util.Date类型
		String sDateTime = sdf.format(date);  //得到精确到秒的表示：08/31/2006 21:08:00
		return sDateTime;
	}
	
	/**
	 * 将格式化的日期字符串转成long型数据
	 * @param formatStr
	 * @return
	 */
	public static long convertStrToDate(String formatStr) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = null;
		try {
			date = sdf.parse(formatStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date.getTime();
	}
	
	/**
	 * 得到某一月包含的天数
	 * @param ymStr 格式yyyy/MM
	 * @return
	 */
	public static int getDaysInMonth(int year,int month) {
		Calendar calendar = Calendar.getInstance();  
		calendar.set(Calendar.YEAR, year);  
		calendar.set(Calendar.MONTH,month);  
		int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		return maxDay;
	}
	
	/**
	 * 判断是否位于前台
	 * @param context
	 * @return
	 */
	public static boolean isRunningForeground (Context context)  
	{  
	    ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);  
	    ComponentName cn = am.getRunningTasks(1).get(0).topActivity;  
	    String currentPackageName = cn.getPackageName();  
	    if(!TextUtils.isEmpty(currentPackageName) && currentPackageName.equals(context.getPackageName()))  
	    {  
	        return true ;  
	    }  
	    return false ;  
	}  
	
	
	
	/**
	 * 显示软键盘
	 * @param context
	 * @param view
	 */
	public void showKeyboard(Context context,View view){
		((InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(view, 0);  
	}
	
	/**
	 * 隐藏软键盘
	 * @param context
	 * @param view
	 */
	public void hideKeyBoard(Context context,View view){
		InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE); 
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}
	/**
	 * 组件截图
	 * @param view
	 * @return
	 */
	public static Bitmap convertViewToBitmap(View view){
//	    view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
//	        view.layout(0, 0, view.getMeasuredWidth() , view.getMeasuredHeight() + view.getPaddingBottom());
//	        view.buildDrawingCache();
//	        Bitmap bitmap = view.getDrawingCache();
		Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
	        view.draw(new Canvas(bitmap));
	        return bitmap;
	}
	
	/**
	 * 判断指定名称的service是否已经启动
	 * @param context
	 * @param serviceClassString 要检测匹配的service的全路径字符串
	 * @return
	 */
	public static boolean isServiceWorked(Context context,String serviceClassString) {
		ActivityManager myManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		ArrayList<RunningServiceInfo> runningService = (ArrayList<RunningServiceInfo>) myManager
				.getRunningServices(1000);
		for (int i = 0; i < runningService.size(); i++) {
			LogWrapper.e(BaseCommTool.class, runningService.get(i).service
					.getClassName()
					.toString());
			if (runningService.get(i).service
					.getClassName()
					.toString()
					.equals(serviceClassString)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 *  到处数据库文件到指定目录下
	 * @param context
	 * @param destPath 导出的指定目录
	 */
	 public static void exportDBFile(Context context,String destPath,String dbName) {
		 String packageName = getPackageName(context);
		 String DATABASE_PATH = Environment.getExternalStorageDirectory() 
				 + File.separator + packageName + File.separator +dbName; 
		 File inputFile = new File(DATABASE_PATH);
		 File outputFile = new File(FileUtil.getSDFloder(context) + File.separator + dbName);
		 
//		 FileUtil.copyStream(input, output);
	 }
	 
	 //拨打电话
	 public static void callPhone(Context c,String phoneNum) {
			Intent phoneIntent = new Intent("android.intent.action.CALL",
					Uri.parse("tel:" + phoneNum));
			c.startActivity(phoneIntent);
		}
}

