package com.lw.clouddelivery.util;


import com.lw.clouddelivery.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DialogUtils {
	private static AlertDialog dialog;
	private static Dialog mDialog;


	public static void AlearImageSeletDialog(Context context,OnClickListener okListener, OnClickListener cacelListener, boolean isCancelable) {
		dismissDialog();
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setCancelable(isCancelable);
		View view = LayoutInflater.from(context).inflate(R.layout.dialog_base, null);
		
		RelativeLayout container = (RelativeLayout) view.findViewById(R.id.container);
		container.setOnClickListener(new  OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (dialog != null && dialog.isShowing()) {
					dialog.cancel();
				} 
			}
		});
		Button cancel = (Button) view.findViewById(R.id.btn_left);
		Button ok = (Button) view.findViewById(R.id.btn_right);
		if(cacelListener!=null)
			cancel.setOnClickListener(cacelListener );
		if (okListener != null)
			ok.setOnClickListener(okListener);
		dialog = builder.create();
		dialog.show();
		dialog.setContentView(view);
	}


	public static void dismissDialog() {
		if (dialog != null && dialog.isShowing())
			dialog.cancel();
		if (mDialog != null && mDialog.isShowing())
			mDialog.cancel();
	}
	public static void alertItemDialog(Context context, String title,String item1, String item2,final DialogItemSelectListener dialogListener) {
		dismissDialog();
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		View view = LayoutInflater.from(context).inflate(R.layout.dialog_select, null);
		LinearLayout bottom_btns = (LinearLayout) view.findViewById(R.id.bottom_btns);
		TextView tv_title = (TextView) view.findViewById(R.id.title_text);
		tv_title.setText(title);
		TextView itemOne = (TextView) view.findViewById(R.id.item_one);
		TextView itemTwo = (TextView) view.findViewById(R.id.item_two);
		itemOne.setText(item1);
		itemTwo.setText(item2);
		
		itemOne.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				dialogListener.onItemOneClick();
				
			}
		});
		
		itemTwo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				dialogListener.onItemTwoClick();
				
			}
		});
		
	
		dialog = builder.create();
		dialog.show();
		dialog.setContentView(view);
	}

}
