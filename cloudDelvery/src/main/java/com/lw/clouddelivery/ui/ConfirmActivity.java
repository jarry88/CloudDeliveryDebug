package com.lw.clouddelivery.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.BaseActivity;
import com.base.util.BaseUIHelper;
import com.base.util.FileUtil;
import com.base.util.ImageUtil;
import com.base.util.LogWrapper;
import com.base.util.MyToast;
import com.lw.clouddelivery.R;
import com.lw.clouddelivery.bean.Order;
import com.lw.clouddelivery.conf.INI;
import com.lw.clouddelivery.util.DCUtil;
import com.lw.clouddelivery.util.MySPTool;
import com.lw.clouddelivery.util.UIHelper;

/**
 * 物品确认界面　
 * 
 * @author leon
 *
 */
public class ConfirmActivity extends BaseActivity implements OnClickListener {

	private Button showDialogBtn;
	private Order mOrder;
	private String smsCode = null;

	private TextView confirm_ordername;
	private Button uploadBtn, selectButton;
	private ArrayList<File> fileList = new ArrayList<File>();
	private LinearLayout imageContainer;
	private int step;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentLayout(R.layout.activity_confirm);
		getTitleBar().initTitleText("物品确认");

		 mOrder = (Order) getIntent().getExtras().getSerializable("order");
		
		 step = getIntent().getIntExtra("step", YS_Step1Activity.SEND_SMS);
//		 if(step == YS_Step1Activity.SEND_SMS) {
//		 smsCode = mOrder.getQpwd();
//		 } else {
//		 smsCode = mOrder.getSpwd();
//		 }
		 
		 confirm_ordername =
		 (TextView)this.findViewById(R.id.confirm_ordername);
		 confirm_ordername.setText(mOrder.getOrdername());

		findViews();
		 fillData();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		 if (resultCode == Activity.RESULT_OK) {
			 if (requestCode == BaseUIHelper.REQUEST_TAKE_PHOTO) { // 保存照片
				  String sdStatus = Environment.getExternalStorageState();
		            if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
		                return;
		            }
		            Bundle bundle = intent.getExtras();
		            Bitmap bitmap = (Bitmap) bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式
		            FileOutputStream b = null;
		            File file = new File("/sdcard/yunsong/");
		            file.mkdirs();// 创建文件夹，名称为myimage
		   
		           //照片的命名，目标文件夹下，以当前时间数字串为名称，即可确保每张照片名称不相同。
		            String str=null;
		            Date date=null;
		            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");//获取当前时间，进一步转化为字符串
		            date =new Date();
		            str=format.format(date);
		            String fileName = "/sdcard/yunsong/"+str+".jpg";
		           try {
		                b = new FileOutputStream(fileName);
		                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
		            } catch (FileNotFoundException e) {
		                e.printStackTrace();
		            } finally {
		                try {
		                    b.flush();
		                    b.close();
		                } catch (IOException e) {
		                    e.printStackTrace();
		                }
		                if (intent!= null) {
		                    Bitmap cameraBitmap = (Bitmap) intent.getExtras().get("data");
		                    System.out.println("fdf================="+intent.getDataString());
		                   System.out.println("成功======"+cameraBitmap.getWidth()+cameraBitmap.getHeight());
		                   
		                   //添加路径到图片数组中去
		                   File photoFile = new File(fileName);
		                   fileList.add(photoFile);
		                   refreshSelectedImgs();
		            }
		            }
			} else if (requestCode == BaseUIHelper.REQUEST_OPEN_GALLERY) {
				Bitmap bm = null;
				// 外界的程序访问ContentProvider所提供数据 可以通过ContentResolver接口
				ContentResolver resolver = getContentResolver();
				// 此处的用于判断接收的Activity是不是你想要的那个
				try {
					Uri originalUri = intent.getData(); // 获得图片的uri
					bm = MediaStore.Images.Media.getBitmap(resolver, originalUri);
					// 显得到bitmap图片

					// 这里开始的第二部分，获取图片的路径：
					String[] proj = { MediaStore.Images.Media.DATA };
					// 好像是android多媒体数据库的封装接口，具体的看Android文档
					Cursor cursor = managedQuery(originalUri, proj, null, null,
							null);
					// 按我个人理解 这个是获得用户选择的图片的索引值
					int column_index = cursor
							.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
					// 将光标移至开头 ，这个很重要，不小心很容易引起越界
					cursor.moveToFirst();
					// 最后根据索引值获取图片路径
					String path = cursor.getString(column_index);
					File file = new File(path);
					fileList.add(file);
					refreshSelectedImgs();

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		 }
	}

	/**
	 * 刷新已选图片列表
	 */
	private void refreshSelectedImgs() {
		int limit = fileList.size() <= 5 ? fileList.size() : 5;
		imageContainer.removeAllViews();

		LayoutInflater mInflater = LayoutInflater.from(ConfirmActivity.this);

		for (int i = 0; i < limit; i++) {
			final int j = i;
			RelativeLayout item = (RelativeLayout) mInflater.inflate(
					R.layout.item_selectedimg, null);
			ImageView iv = (ImageView) item.findViewById(R.id.selected_img);
			iv.setScaleType(ScaleType.CENTER_CROP);
			iv.setImageBitmap(ImageUtil.getBitmapByFile(fileList.get(i)));
			ImageView deleteIV = (ImageView) item
					.findViewById(R.id.selected_delete);
			deleteIV.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					imageContainer.removeViewAt(j);
					fileList.remove(j);
				}
			});
			imageContainer.addView(item);
		}
	}

	@Override
	public void fillData() {
		super.fillData();
	}

	@Override
	public void findViews() {
		super.findViews();
		imageContainer = (LinearLayout) this.findViewById(R.id.imageContainer);
		selectButton = (Button) this.findViewById(R.id.confirm_select_pic);
		selectButton.setOnClickListener(this);

		uploadBtn = (Button) this.findViewById(R.id.confirm_update_btn);
		uploadBtn.setOnClickListener(this);

		showDialogBtn = (Button) this.findViewById(R.id.showDialogBtn);
		if (step == YS_Step1Activity.SEND_SMS) { // 验证取件
			showDialogBtn.setText("验证取件密码");
		} else {
			showDialogBtn.setText("验证收件密码");
		}
		showDialogBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				LayoutInflater layoutInflater = LayoutInflater.from(ConfirmActivity.this);
				LinearLayout layout = (LinearLayout) layoutInflater.inflate(R.layout.patch_sms_code, null);
				AlertDialog.Builder builder = new AlertDialog.Builder(ConfirmActivity.this);
				final EditText smsET = (EditText) layout.findViewById(R.id.sms_code_et);
				builder.setTitle("请输入取件验证码")
				.setView(layout).create();
				final AlertDialog dialog = builder.create();
				dialog.show();
				layout.findViewById(R.id.sms_code_btn).setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						dialog.cancel();
						smsCode = smsET.getText().toString().trim();
										if (step == YS_Step1Activity.SEND_SMS) { // 验证取件
											DCUtil.checkQuJianSMS(ConfirmActivity.this,
													mOrder.getId() + "", smsCode,
													new Handler() {
														@Override
														public void handleMessage(Message msg) {
															super.handleMessage(msg);
															MyToast.showText(ConfirmActivity.this,"验证码正确!");
															setResult(step);
															ConfirmActivity.this
																	.finish();
														}
													});
										} else { // 验证收件
											DCUtil.checkShouJianSMS(ConfirmActivity.this,mOrder.getId() + "",smsCode,MySPTool.getUid(ConfirmActivity.this),
													new Handler() {
														@Override
														public void handleMessage(Message msg) {
															super.handleMessage(msg);
															MyToast.showText(ConfirmActivity.this,"验证码正确!");
															setResult(step);
															ConfirmActivity.this.finish();
														}
													});
										}
									}
				});
//				UIHelper.showSMSDialog(ConfirmActivity.this, smsCode,
//						new View.OnClickListener() {
//							@Override
//							public void onClick(View v) {
//								if (step == YS_Step1Activity.SEND_SMS) { // 验证取件
//									DCUtil.checkQuJianSMS(ConfirmActivity.this,
//											mOrder.getId() + "", smsCode,
//											new Handler() {
//												@Override
//												public void handleMessage(
//														Message msg) {
//													super.handleMessage(msg);
//													MyToast.showText(
//															ConfirmActivity.this,
//															"验证码正确!");
//													setResult(step);
//													ConfirmActivity.this
//															.finish();
//												}
//											});
//								} else { // 验证收件
//									DCUtil.checkShouJianSMS(
//											ConfirmActivity.this,
//											mOrder.getId() + "",
//											smsCode,
//											MySPTool.getUid(ConfirmActivity.this),
//											new Handler() {
//												@Override
//												public void handleMessage(
//														Message msg) {
//													super.handleMessage(msg);
//													MyToast.showText(
//															ConfirmActivity.this,
//															"验证码正确!");
//													setResult(step);
//													ConfirmActivity.this
//															.finish();
//												}
//											});
//								}
//							}
//						});
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.confirm_select_pic:
			BaseUIHelper.getPhoto(ConfirmActivity.this,
					new File(FileUtil.getSDFloder(this) + "temptempFile"));
			break;
		case R.id.confirm_update_btn:
			// 上传图片
			new Handler().post(new Runnable() {
				@Override
				public void run() {
					// Looper.prepare();
					String type = step > YS_Step1Activity.SEND_SMS?"1":"2";
					SubmitPost(
							INI.U.UPDATE_IMG,
							fileList, MySPTool.getUid(ConfirmActivity.this),
							mOrder.getId()+"", type, new Handler() {
								@Override
								public void handleMessage(Message msg) {
									super.handleMessage(msg);
									if (msg.what == 1) {
										LogWrapper.e(ConfirmActivity.class,
												"显示LoadingDialog");
										pd.show();
									} else if (msg.what == 2) {
										LogWrapper.e(ConfirmActivity.class,
												"隐藏LoadingDialog");
										pd.dismiss();
										MyToast.showText(ConfirmActivity.this,
												"图片上传成功!");
										afterUploadSucc();
									}
								}
							});
				}
			});
			break;
		}
	}

	// file1与file2在同一个文件夹下 filepath是该文件夹指定的路径
	public void SubmitPost(final String url, final ArrayList<File> fileList,
			final String uid, final String orderid, final String type,
			final Handler handler) {
		new Thread() {
			@Override
			public void run() {
				super.run();
				HttpClient httpclient = new DefaultHttpClient();
				try {

					HttpPost httppost = new HttpPost(url);
					MultipartEntity reqEntity = new MultipartEntity();
					FileBody[] fileBodies = new FileBody[fileList.size()];
					for (int i = 0; i < fileBodies.length; i++) {
						FileBody bin = new FileBody(fileList.get(i));
						reqEntity.addPart("img" + (i + 1), bin); // 要上传的图片参数
					}

					StringBody uidSB = new StringBody(uid);
					StringBody orderidSB = new StringBody(orderid);
					StringBody typeSB = new StringBody(type);

					reqEntity.addPart("uid", uidSB);// filename1为请求后台的普通参数;属性
					reqEntity.addPart("orderId	", orderidSB);// filename1为请求后台的普通参数;属性
					reqEntity.addPart("type", typeSB);// filename1为请求后台的普通参数;属性
					httppost.setEntity(reqEntity);

					Message msg = new Message();
					msg.what = 1;
					handler.sendMessage(msg);
					HttpResponse response = httpclient.execute(httppost);
					int statusCode = response.getStatusLine().getStatusCode();
					if (statusCode == HttpStatus.SC_OK) {
						System.out.println("服务器正常响应.....");
						HttpEntity resEntity = response.getEntity();
						System.out.println(EntityUtils.toString(resEntity));// httpclient自带的工具类读取返回数据
						Message msg2 = new Message();
						msg2.what = 2;
						handler.sendMessage(msg2);
						// handler.sendEmptyMessage(2);
						// System.out.println(resEntity.getContent());
						// EntityUtils.consume(resEntity);
					}
				} catch (ParseException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						httpclient.getConnectionManager().shutdown();
					} catch (Exception ignore) {
					}
				}
			}
		}.start();
	}
	
	/**
	 * 上传成功后，刷新按钮状态
	 */
	private void afterUploadSucc() {
			uploadBtn.setEnabled(false);
			selectButton.setEnabled(false);
	}
}
