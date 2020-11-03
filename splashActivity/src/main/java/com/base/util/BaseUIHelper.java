package com.base.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.widget.Toast;

public class BaseUIHelper {

	public final static int REQUEST_OPEN_GALLERY = 0x6001;
	public final static int REQUEST_TAKE_PHOTO = 0x6002;
	public final static int REQUEST_CROP_IMAGE = 0x6003;
	/**
	 * 打开系统相册
	 */
	public static void openGallery(Activity act) {
		Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
		photoPickerIntent.setType("image/*");
		act.startActivityForResult(photoPickerIntent, REQUEST_OPEN_GALLERY);
	}
	
	/**
	 * 调用拍照界面
	 * @param mFileTemp
	 */
	public static void takePhoto(Activity act,File mFileTemp) {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		try {
			Uri mImageCaptureUri = null;
			String state = Environment.getExternalStorageState();
			if (Environment.MEDIA_MOUNTED.equals(state)) {
				mImageCaptureUri = Uri.fromFile(mFileTemp);
			} else {
				mImageCaptureUri = InternalStorageContentProvider.CONTENT_URI;
			}
			intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,
					mImageCaptureUri);
			intent.putExtra("return-data", true);
			act.startActivityForResult(intent, REQUEST_TAKE_PHOTO);
		} catch (ActivityNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取图片，打开相册或者拍照
	 * @param c
	 * @param tempPhotoFile 拍照临时文件
	 */
	public static void getPhoto(final Activity c,final File tempPhotoFile) {
		AlertDialog.Builder builder = new AlertDialog.Builder(c);
		builder.setTitle("请选择图片来源")
		.setItems(new String[]{"相册","拍照"}, new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch(which) {
				case 0:
					openGallery(c);
					break;
				case 1:
//					takePhoto(c, tempPhotoFile);
					Toast.makeText(c, "功能开发中", 1000).show();
					break;
				}
			}
		}).show();
	}
}
