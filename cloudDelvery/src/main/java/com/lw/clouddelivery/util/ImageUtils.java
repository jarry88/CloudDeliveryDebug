package com.lw.clouddelivery.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.ImageColumns;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageUtils {
	
	/**
	 * 打开相机
	 */
	public static Intent openCamera(Uri uri){
		Intent intent = new Intent();
		intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		return intent;
	}
	/**
	 * 打开图库
	 * @return
	 */
	public static Intent openGallery(){
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_PICK);
		intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
		return intent;
	}
	/**
	 * uri 转化为path
	 * @param context
	 * @param uri
	 * @return
	 */
	public static String getRealFilePath( final Context context, final Uri uri ) {
	    if ( null == uri ) return null;
	    final String scheme = uri.getScheme();
	    String data = null;
	    if ( scheme == null )
	        data = uri.getPath();
	    else if ( ContentResolver.SCHEME_FILE.equals( scheme ) ) {
	        data = uri.getPath();
	    } else if ( ContentResolver.SCHEME_CONTENT.equals( scheme ) ) {
	        Cursor cursor = context.getContentResolver().query( uri, new String[] { ImageColumns.DATA }, null, null, null );
	        if ( null != cursor ) {
	            if ( cursor.moveToFirst() ) {
	                int index = cursor.getColumnIndex( ImageColumns.DATA );
	                if ( index > -1 ) {
	                    data = cursor.getString( index );
	                }
	            }
	            cursor.close();
	        }
	    }
	    return data;
	}

	/**
	 * 根据图片uri获取图片地址
	 * @param context
	 * @param uri
	 * @return
	 */
	public static String getAlbumImagePath(Context context ,Uri uri){
		String path = "";
		//查询的列
		String [] prog = {MediaStore.Images.Media.DATA}; 
		Cursor cursor = context.getContentResolver().query(uri, prog, null, null, null);
		//查处索引
		int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		//游标归零
		cursor.moveToFirst();
		//通过游标查数值
		path = cursor.getString(columnIndex);
		cursor.close();
		return path;
	}
	
	@SuppressLint("SimpleDateFormat")
	public static String getFormatDate(String format){
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.format(date);
	}
	
	public static String returnTimeByHtml(long milliseconds,int dat) {
		String string = "";
//		long s = 1000;
		long s = 1;
		long m = s * 60;
		long h = m * 60;
		long d = h * 24;
 
		long _d = milliseconds % d;
		long _h = _d % h;
   		long _m = _h % m;

		StringBuffer buffer = new StringBuffer();
		buffer.append(dat+"天")
//				.append(to2Str(milliseconds / d)).append("天")
				.append(to2Str(_d / h))
				.append("时")
		 		.append(to2Str(_h / m))
				.append("分") 
				.append(to2Str(_m / s))
				.append("秒");
		string = buffer.toString();
		return string;
	}
	private static String to2Str(long data) {
		if (data < 10) {
			return "0" + data;
		} else
			return String.valueOf(data);
	}
	
	public static int string2Int(String s){
		int i = Integer.MAX_VALUE;
		if(s == null)return 0;
		try{
			i = Integer.valueOf(s.trim());
		}catch(Exception e){}
		return i;
	}
	
	
	public static void showText(String text,TextView view){
		if(TextUtils.isEmpty(text) || view == null)return;
		view.setText(text);
	}
	/**
	 * 检测网络是否可用
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isNetWorkConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
		}

		return false;
	}
	/**
	 * 往Shared中存数据的方法
	 * 
	 * @param context
	 *            上下文对象
	 * @param filename
	 *            文件名字
	 * @param name
	 *            存放数据的key
	 * @param content
	 *            存放数据的value
	 */
	public static void setShareData(Context context, String filename,
			String name, String content) {
		SharedPreferences sp = context.getSharedPreferences(filename,
				Context.MODE_PRIVATE);
		// SharedPreferences sp = context.getSharedPreferences("PersonInfo",
		// Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putString(name, content);
		editor.commit();
	}

	/**
	 * 取得Shared中数据的方法
	 * 
	 * @param name
	 *            文件名
	 * 
	 * @param context
	 *            上下文对象
	 * @param PersonInfo
	 *            存放数据的名字
	 * 
	 */
	public static String getShareData(Context context, String PersonInfo,
			String name) {
		SharedPreferences sp = context.getSharedPreferences(name,
				Context.MODE_PRIVATE);

		return sp.getString(PersonInfo, "");
	}
	/**
	 * 
	 * @Title: setShareBooleanData
	 * @Description: 往Shared中存记住状态数据的方法
	 * @param @param context 上下文对象
	 * @param @param name 存放数据的键值
	 * @param @param content 存放数据的内容
	 * @return void
	 * @throws
	 */
	public static void setShareBooleanData(Context context, String name,
			Boolean content) {
		SharedPreferences sp = context.getSharedPreferences("STATE",
				Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putBoolean(name, content);
		editor.commit();
	}

	/**
	 * 
	 * @Title: getShareBooleanData
	 * @Description: 取得Shared中记住状态数据的方法
	 * @param @param context 上下文对象
	 * @param @param name 存放数据的键值
	 * @param @param content 取得数据的默认值
	 * @return void
	 * @throws
	 */
	public static boolean getShareBooleanData(Context context, String name,
			Boolean content) {
		SharedPreferences sp = context.getSharedPreferences("STATE",
				Context.MODE_PRIVATE);
		return sp.getBoolean(name, content);
	}
}
