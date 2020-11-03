package com.lw.clouddelivery;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.os.Handler;

import com.baidu.location.LocationClient;
import com.baidu.mapapi.SDKInitializer;
import com.base.BaseApp;

public class CloudDeliveryAPP extends BaseApp {
	public static List<Integer> tempList = new ArrayList<Integer>();
    public LocationClient mLocationClient;
    private static CloudDeliveryAPP instance;
    public static int loginTime = 40;
    public static CloudDeliveryAPP getInstance() {
    		if(instance == null) {
    			instance = new CloudDeliveryAPP();
    		}
    		return instance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        SDKInitializer.initialize(this);
        mLocationClient = new LocationClient(this.getApplicationContext());
    }
    
    /**
	 * 保存对象
	 * @param ser
	 * @param file
	 * @throws IOException
	 */
	public boolean saveObject(Serializable ser, String file) {
//		file = SPTool.getUid(getApplicationContext()) + "_" + file; //按用户id区分文件,切换账号时用到
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try{
			fos = openFileOutput(file, MODE_PRIVATE);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(ser);
			oos.flush();
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}finally{
			try {
				oos.close();
			} catch (Exception e) {}
			try {
				fos.close();
			} catch (Exception e) {}
		}
	}
	
	/**
	 * 读取对象
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public Serializable readObject(String file){
//		file = SPTool.getUid(getApplicationContext()) + "_" + file; //按用户id区分文件
		if(!isExistDataCache(file))
			return null;
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try{
			fis = openFileInput(file);
			ois = new ObjectInputStream(fis);
			return (Serializable)ois.readObject();
		}catch(FileNotFoundException e){
		}catch(Exception e){
			e.printStackTrace();
			//反序列化失败 - 删除缓存文件
			if(e instanceof InvalidClassException){
				File data = getFileStreamPath(file);
				data.delete();
			}
		}finally{
			try {
				ois.close();
			} catch (Exception e) {}
			try {
				fis.close();
			} catch (Exception e) {}
		}
		return null;
	}
	
	/**
	 * 判断缓存是否存在
	 * @param cachefile
	 * @return
	 */
	private boolean isExistDataCache(String cachefile)
	{
		boolean exist = false;
		File data = getApplicationContext().getFileStreamPath(cachefile);
		if(data.exists())
			exist = true;
		return exist;
	}
	
	
	RegisterCodeRunnable runRegisterCode;
	public RegisterCodeRunnable getRegisterCodeRunnable(Handler handler){
		if(runRegisterCode == null)
			runRegisterCode = new RegisterCodeRunnable(handler);
		else
			runRegisterCode.setHandler(handler);
		return runRegisterCode;
	}
	
	public class RegisterCodeRunnable implements Runnable{
		Handler handler;
		boolean con;
		public RegisterCodeRunnable(Handler handler){this.handler = handler;}
		
		void setHandler(Handler handler){
			this.handler = handler;
		}
		@Override
		public void run() {
			if(con) return;
			do{
				con = true;
				CloudDeliveryAPP.loginTime --;
				if(handler != null)
				handler.sendEmptyMessage(0);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}while(CloudDeliveryAPP.loginTime > 0 && con);
			if(handler != null)
			handler.sendEmptyMessage(1);
			CloudDeliveryAPP.loginTime = 60;
			con = false;
		}
	}
}
