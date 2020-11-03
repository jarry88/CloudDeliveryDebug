package com.lw.clouddelivery.ui;

import java.io.File;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.BaseActivity;
import com.base.util.MyToast;
import com.lw.clouddelivery.R;
import com.lw.clouddelivery.util.DCUtil;
import com.lw.clouddelivery.util.DialogItemSelectListener;
import com.lw.clouddelivery.util.DialogUtils;
import com.lw.clouddelivery.util.FileUtils;
import com.lw.clouddelivery.util.ImageUtils;

public class AuthenticationActivity extends BaseActivity implements OnClickListener{
	
	private EditText authenticationname,authenticationcardid,authenticationtall,authenticationweight;
	private Button authentication_commit,uploadimgthreebtn,uploadimgtwobtn,uploadimgonebtn;
	private ImageView uploadimgone,uploadimgtwo,uploadimgthree;
	private String authenticationnamestr,authenticationcardidstr,authenticationsexstr,authenticationtallstr,authenticationweightstr;
	private LinearLayout authenticationsexlayout;
	private TextView authenticationsex;
	private File img1,img2,img3;
	
	private static final int GETPICFROM_CAMERA = 0;
	private static final int GETPICFROM_GALLERY = 1;
	private String cameraImagePath;
	private String filePath1,filePath2,filePath3;
	private int fpath=0;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	
		setContentLayout(R.layout.activity_authentication);
		getTitleBar().initTitleText("填写资料");
		
		findViews();
		
		authentication_commit.setOnClickListener(this);
		uploadimgthreebtn.setOnClickListener(this);
		uploadimgtwobtn.setOnClickListener(this);
		uploadimgonebtn.setOnClickListener(this);
		authenticationsexlayout.setOnClickListener(this);
	}
	
	@Override
	public void findViews() {
		super.findViews();
		
		authenticationname=(EditText) findViewById(R.id.authenticationname);
		authenticationcardid=(EditText) findViewById(R.id.authenticationcardid);
		authenticationsex=(TextView) findViewById(R.id.authenticationsex);
		authenticationtall=(EditText) findViewById(R.id.authenticationtall);
		authenticationweight=(EditText) findViewById(R.id.authenticationweight); 
		
		uploadimgone=(ImageView) findViewById(R.id.uploadimgone);
		uploadimgtwo=(ImageView) findViewById(R.id.uploadimgtwo);
		uploadimgthree=(ImageView) findViewById(R.id.uploadimgthree);
		
		authentication_commit=(Button) findViewById(R.id.authentication_commit);
		uploadimgthreebtn=(Button) findViewById(R.id.uploadimgthreebtn);
		uploadimgtwobtn=(Button) findViewById(R.id.uploadimgtwobtn);
		uploadimgonebtn=(Button) findViewById(R.id.uploadimgonebtn);
		
		authenticationsexlayout=(LinearLayout) findViewById(R.id.authenticationsexlayout);
		
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.authentication_commit:
			
			authenticationnamestr=authenticationname.getText().toString().trim();
			authenticationcardidstr=authenticationcardid.getText().toString().trim();
			authenticationsexstr=authenticationsex.getText().toString().trim();
			authenticationtallstr=authenticationtall.getText().toString().trim();
			authenticationweightstr=authenticationweight.getText().toString().trim();
			
			if(TextUtils.isEmpty(authenticationnamestr)){
				MyToast.showText(this, "请填写真实姓名");
				break;
			}else if(TextUtils.isEmpty(authenticationcardidstr)){
				MyToast.showText(this, "请填写身份证号");
				break;
			}else if(TextUtils.isEmpty(authenticationtallstr)){
				MyToast.showText(this, "请填写身高");
				break;
			}else if(TextUtils.isEmpty(authenticationweightstr)){
				MyToast.showText(this, "请填写体重");
				break;
			}else if(TextUtils.isEmpty(authenticationsexstr)){
				MyToast.showText(this, "请填写性别");
				break;
			}else{
				
				DCUtil.postCommitInfo(AuthenticationActivity.this, pd, authenticationnamestr,authenticationcardidstr,"1",
						authenticationtallstr, authenticationweightstr, img1, img2, img3, new Handler() {
							@Override
							public void handleMessage(Message msg) {
								super.handleMessage(msg);
								MyToast.showText(AuthenticationActivity.this,"填写资料成功");
								AuthenticationActivity.this.finish();
							}
				});
				
			}
			
			
			break;
		case R.id.uploadimgthreebtn:
			fpath=3;
			DialogUtils.alertItemDialog(this, "选择上传身份证反面", "相机", "图库", new DialogItemSelectListener(){

				@Override
				public void onItemOneClick() {
					// TODO Auto-generated method stub
					// 打开相机
					cameraImagePath = FileUtils.getImagePath();
					Uri pathUri = Uri.fromFile(new File(cameraImagePath));
				    Intent	intent = ImageUtils.openCamera(pathUri);
					startActivityForResult(intent, GETPICFROM_CAMERA);
					DialogUtils.dismissDialog();
				}

				@Override
				public void onItemTwoClick() {
					// TODO Auto-generated method stub
					// 打开图库
					Intent intent = new Intent();
					intent = ImageUtils.openGallery();
					startActivityForResult(intent, GETPICFROM_GALLERY);
					DialogUtils.dismissDialog();
				}

			});
			break;
		case R.id.uploadimgtwobtn:
			fpath=2;
			DialogUtils.alertItemDialog(this, "选择上传身份证正面", "相机", "图库", new DialogItemSelectListener(){

				@Override
				public void onItemOneClick() {
					// TODO Auto-generated method stub
					// 打开相机
					cameraImagePath = FileUtils.getImagePath();
					Uri pathUri = Uri.fromFile(new File(cameraImagePath));
				    Intent	intent = ImageUtils.openCamera(pathUri);
					startActivityForResult(intent, GETPICFROM_CAMERA);
					DialogUtils.dismissDialog();
				}

				@Override
				public void onItemTwoClick() {
					// TODO Auto-generated method stub
					// 打开图库
					Intent intent = new Intent();
					intent = ImageUtils.openGallery();
					startActivityForResult(intent, GETPICFROM_GALLERY);
					DialogUtils.dismissDialog();
				}

			});
			break;
		case R.id.uploadimgonebtn:
			fpath=1;
			DialogUtils.alertItemDialog(this, "上传证件照", "相机", "图库", new DialogItemSelectListener(){

				@Override
				public void onItemOneClick() {
					// TODO Auto-generated method stub
					// 打开相机
					cameraImagePath = FileUtils.getImagePath();
					Uri pathUri = Uri.fromFile(new File(cameraImagePath));
				    Intent	intent = ImageUtils.openCamera(pathUri);
					startActivityForResult(intent, GETPICFROM_CAMERA);
					DialogUtils.dismissDialog();
				}

				@Override
				public void onItemTwoClick() {
					// TODO Auto-generated method stub
					// 打开图库
					Intent intent = new Intent();
					intent = ImageUtils.openGallery();
					startActivityForResult(intent, GETPICFROM_GALLERY);
					DialogUtils.dismissDialog();
				}

			});
			break;
			
		case R.id.authenticationsexlayout:
			DialogUtils.alertItemDialog(this, "选择性别", "男", "女", new DialogItemSelectListener(){

				@Override
				public void onItemOneClick() {
					// TODO Auto-generated method stub
					authenticationsexstr = "男";
					authenticationsex.setText(authenticationsexstr);
					DialogUtils.dismissDialog();
				}

				@Override
				public void onItemTwoClick() {
					// TODO Auto-generated method stub
					authenticationsexstr = "女";
					authenticationsex.setText(authenticationsexstr);
					DialogUtils.dismissDialog();
				}

			});
			break;
		default:
			break;
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (resultCode != RESULT_OK)
			return;
		switch (requestCode) {
		case GETPICFROM_CAMERA:
			Bitmap bitmap =FileUtils.getBitmapFromFilePath(cameraImagePath,300);
			if(fpath==3){
				filePath3 = cameraImagePath;
				uploadimgthree.setImageBitmap(bitmap);
			}else if(fpath==2){
				filePath2 = cameraImagePath;
				uploadimgtwo.setImageBitmap(bitmap);
			}else if(fpath==1){
				filePath1 = cameraImagePath;
				uploadimgone.setImageBitmap(bitmap);
			}
			
			// 把网络访问的代码放在这里
			new Thread() {
				@Override
				public void run() {
					// 把网络访问的代码放在这里
					Message msg = new Message();
					msg.what = 1;
					handler.sendMessage(msg); // 向Handler发送消息，更新UI
				}
			}.start();

			break;

		case GETPICFROM_GALLERY:
			Uri uri = data.getData();
			Log.e("ImageShow170", "" + uri);
			Bitmap bitmaps = FileUtils.getBitmapFromFilePath(this, uri);
			if(fpath==3){
				filePath3 = FileUtils.getImageAbsolutePath(this, uri);
				uploadimgthree.setImageBitmap(bitmaps);
			}else if(fpath==2){
				filePath2 =  FileUtils.getImageAbsolutePath(this, uri);
				uploadimgtwo.setImageBitmap(bitmaps);
			}else if(fpath==1){
				filePath1 =  FileUtils.getImageAbsolutePath(this, uri);
				uploadimgone.setImageBitmap(bitmaps);
			}
			// 把网络访问的代码放在这里
			new Thread() {
				@Override
				public void run() {
					// 把网络访问的代码放在这里
					Message msg = new Message();
					msg.what = 1;
					handler.sendMessage(msg); // 向Handler发送消息，更新UI
				}
			}.start();
			break;

		default:
			break;
		}
	}
	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			String nameimg = "";
			switch (msg.what) {
			case 1:
				new Thread() {
					@Override
					public void run() {
						// 把网络访问的代码放在这里
						Looper.prepare();
						if(fpath==3){
							img3 = new File(filePath3);
						}else if(fpath==2){
							img2 = new File(filePath2);
						}else if(fpath==1){
							img1 = new File(filePath1);
						}
						Looper.loop();
					}
				}.start();
				break;
			default:
				break;
			}
		}

	};
}
