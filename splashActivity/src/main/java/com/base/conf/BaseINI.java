package com.base.conf;

import java.io.File;

import com.base.util.BaseCommTool;

import android.os.Environment;

public class BaseINI {
public static boolean DEBUG = true; //调试/开发模式
	private static final String APP_LABEL = "baseapp";
	public static final String SHARE_PRE_NAME = APP_LABEL;
	
	public static final String UTF8 = "utf-8";
	
	public static final int FEED_COMMENT_LINE_LIMIT = 5; //动态列表中评论的最大行数
	
	public static final int FEED_PAGE_SIZE = 10; //每页动态的请求条数
	
	public static final int MSG_PAGE_SIZE = 20; //每页消息的请求条数
	
	public static final int IMG_MAX_WIDTH = 1080; //上传图片的最大宽度
	
	public static final float titlebarH = 0.100f; //Titlebar的高度
	
	public static final int UPLOAD_TIMEOUT = 5 * 60 * 1000; //多图上传超时时间
	
	public static final String WX_ID = "wx28016022c9964645";
	
	
	public static final int NOTIFICATION_DOWNLOAD = 0x45; //文件下载id
	
	public static final int update_alert = 3 * 24 * 3600 * 1000; // 版本更新提示框，延后时间(三天)
	
	public static final int IMAGE_UPLOAD_COMPRESS_SIZE = 72; //图片上传压缩尺寸,60时大约1/10，100表示未压缩

	public static final String NOMEDIA = ".nomedia"; //隐藏文件夹中的图片
	
	public static final int CALENDAR_START = 2010;
	public static final int CALENDAR_END = 2020;
	
	  public static class RESULT {
		  public static final int ALBUM_LIST = 0x123; //从相册列表返回
		  public static final int ALBUM_DETAIL = 0x234; //从具体某个相册的图片列表返回
		  
		  public static final String SELECTED_PATH = "selectedPath"; //已选择的图片路径数组的"键"
		  public static final String IAMGE_ITEMLIST = "imageItemList"; //相册图片列表数据，ImageItem
	  }
	
		//约定：文件夹路径后都要跟"/"
		public static class FILE_PATH{
			public static final String SDCARD = Environment.getExternalStorageDirectory().getPath();
			public static final String BASE = SDCARD + File.separator + "YS";
			public static final String AUDIO = BASE+ File.separator +"audio" + File.separator;
			public static final String CARSH = BASE+ File.separator +"crash" + File.separator;
			public static final String IMAGE = BASE+ File.separator +"image" + File.separator;
			public static final String COLLECT = BASE+ File.separator +"collect" + File.separator;
			public static final String IMAGE_THUMB = BASE+ File.separator +"image" + File.separator + "thumb" + File.separator;
			//图片滤镜临时目录
			public static final String PIC_FILTER = BASE+ File.separator +"image" + File.separator + "filter" + File.separator;
			//图片上传临时目录
			public static final String PIC_UPLOAD = BASE+ File.separator +"image" + File.separator + "upload" + File.separator;
			//离线发布动态文件放置目录
			public static final String OFFLINE_PUB = BASE+ File.separator +"pub" + File.separator;
			//隐藏配置文件
			public static final String CONFIG_FILENAME = "config.txt";
			public static final String CONFIG_PATH = BASE + File.separator + CONFIG_FILENAME;
			public static final String LOG_FILE = BASE + File.separator + "log.txt";
		}
	public final static int MAX_AVATER = 100; //头像缩略图尺寸
}
