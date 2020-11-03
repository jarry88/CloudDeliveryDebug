package com.lw.clouddelivery.ui;

import android.os.Bundle;
import android.widget.CheckBox;

import com.base.BaseActivity;
import com.lw.clouddelivery.R;
import com.lw.clouddelivery.conf.INI;
import com.lw.clouddelivery.util.MySPTool;

public class SettingActivity extends BaseActivity {

	private CheckBox cbVoice,cbVerbose,cbConfirm;
	
	private boolean settingVoice,settingVerbose,settingConfirm;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentLayout(R.layout.activity_setting);
		
		cbVoice = (CheckBox)this.findViewById(R.id.setting_voice);
		cbVerbose = (CheckBox)this.findViewById(R.id.setting_verbose);
		cbConfirm = (CheckBox)this.findViewById(R.id.setting_confirm);
		
		settingConfirm = MySPTool.getBoolean(SettingActivity.this, INI.SP.SETTING_CONFIEM, false);
		settingVerbose = MySPTool.getBoolean(SettingActivity.this, INI.SP.SETTING_VERBOSE, true);
		settingVoice = MySPTool.getBoolean(SettingActivity.this, INI.SP.SETTING_VOICE, true);
		
		cbConfirm.setChecked(settingConfirm);
		cbVerbose.setChecked(settingVerbose);
		cbVoice.setChecked(settingVoice);
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		MySPTool.putBoolean(this, INI.SP.SETTING_VERBOSE, cbVerbose.isChecked());
		MySPTool.putBoolean(this, INI.SP.SETTING_CONFIEM, cbConfirm.isChecked());
		MySPTool.putBoolean(this, INI.SP.SETTING_VOICE, cbVoice.isChecked());
	}
}
