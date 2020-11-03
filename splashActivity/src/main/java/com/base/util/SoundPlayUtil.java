package com.base.util;

import java.util.HashMap;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

public class SoundPlayUtil {

	private int resId;
	private Context mContext;
	// 引用mideaPlayer和SoundPool
	MediaPlayer mMediaPlayer;
	SoundPool soundPool;
	HashMap<Integer, Integer> soundPoolMap;
	
	public SoundPlayUtil(Context context,int resId) {
		this.resId = resId;
		this.mContext = context;
		
		// 初始化声音
		mMediaPlayer = MediaPlayer.create(mContext, resId);
	}
	
	
	
	/**
	 * 播放音效的方法
	 */
	public void playSound(){
		
		if (!mMediaPlayer.isPlaying()) {
			mMediaPlayer.start();
		}
	}
	
	public void stopSound() {
		if(mMediaPlayer.isPlaying()) {
			mMediaPlayer.stop();
		}
	}
}
