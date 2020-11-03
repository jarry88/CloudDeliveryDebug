package com.lw.clouddelivery.widget;

import com.base.util.LogWrapper;
import com.lw.clouddelivery.R;

import android.content.Context;
import android.provider.ContactsContract.CommonDataKinds.Relation;
import android.util.AttributeSet;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;

public class VerticalSlidingLayout extends RelativeLayout  implements OnTouchListener {

	RelativeLayout patchGetOrder;
	TextView b;
	public VerticalSlidingLayout(final Context context, AttributeSet attrs) {
		super(context, attrs);
		
		
		LayoutInflater inflater = LayoutInflater.from(context);
		patchGetOrder = (RelativeLayout) inflater.inflate(R.layout.patch_get_order, null);
		patchGetOrder.setId(0x1234);
		addView(patchGetOrder);
		
		b = new TextView(context);
		b.setText("Drag me");
		b.setBackgroundColor(0xff123456);
		LayoutParams p2 = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		p2.addRule(RelativeLayout.BELOW, 0x1234);
		addView(b,p2);
		setOnTouchListener(this);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		LogWrapper.e(VerticalSlidingLayout.class, event.getX() + "|" + event.getY() + "|" + event.getRawX() + "|" + event.getRawY());
		if(event.getX() > b.getLeft() && event.getX() < (b.getLeft() + b.getWidth())
				&& event.getY() > b.getTop() && event.getY() < (b.getTop() + b.getHeight())) { //事件触发在按钮上
			patchGetOrder.layout(patchGetOrder.getTop(), patchGetOrder.getLeft(), patchGetOrder.getRight(), (int)(event.getY()));
			patchGetOrder.invalidate();
			b.layout(b.getLeft(), (int)(event.getY()),b.getRight(),(int)(event.getY() + b.getHeight()));
		}
		return true;
	}
}
