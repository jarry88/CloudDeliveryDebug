package com.lw.clouddelivery.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.base.widget.LoadingDialog;

import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

/**
 * 
 * @author <a href="mailto:just_szl@hotmail.com"> Geray</a>
 * @version 1.0,2012-6-12 
 */
public class HttpPostArgumentTest2 {

	//file1与file2在同一个文件夹下 filepath是该文件夹指定的路径	
	public void SubmitPost(String url,ArrayList<File> fileList,String uid,String orderid,String type,Handler handler){
		
		HttpClient httpclient = new DefaultHttpClient();
		
		try {
	
			HttpPost httppost = new HttpPost(url);
			
		    MultipartEntity reqEntity = new MultipartEntity();
		    
			FileBody[] fileBodies = new FileBody[fileList.size()];
			for(int i=0;i<fileBodies.length;i++) {
			    FileBody bin = new FileBody(fileList.get(i));
			    reqEntity.addPart("img" + (i+1),bin); //要上传的图片参数
			}
		     
			StringBody uidSB = new StringBody(uid);
			StringBody orderidSB = new StringBody(orderid);
			StringBody typeSB = new StringBody(type);
			    
		     reqEntity.addPart("uid", uidSB);//filename1为请求后台的普通参数;属性	
		     reqEntity.addPart("orderId	", orderidSB);//filename1为请求后台的普通参数;属性	
		     reqEntity.addPart("type", typeSB);//filename1为请求后台的普通参数;属性	
		    httppost.setEntity(reqEntity);
		    
		    handler.sendEmptyMessage(1);
		    HttpResponse response = httpclient.execute(httppost);
		    
			    
		    int statusCode = response.getStatusLine().getStatusCode();
		    
			if(statusCode == HttpStatus.SC_OK){
			    	
				System.out.println("服务器正常响应.....");
				
		    	HttpEntity resEntity = response.getEntity();
			    
		    	System.out.println(EntityUtils.toString(resEntity));//httpclient自带的工具类读取返回数据
		    	
		    	handler.sendEmptyMessage(2);
//		    	System.out.println(resEntity.getContent());   
//		    	EntityUtils.consume(resEntity);
		    }
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
			    try { 
			    	httpclient.getConnectionManager().shutdown(); 
			    } catch (Exception ignore) {
			    	
			    }
			}
		}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		HttpPostArgumentTest2 httpPostArgumentTest2 = new HttpPostArgumentTest2();
//		httpPostArgumentTest2.SubmitPost("http://120.26.41.195:8888/yunsong/interface/qwdimg",
//				"test.xml","test.zip","D://test");
	}
	
}
