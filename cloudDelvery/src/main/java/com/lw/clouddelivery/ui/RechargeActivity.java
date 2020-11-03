package com.lw.clouddelivery.ui;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.base.BaseActivity;
import com.base.util.MyToast;
import com.lw.clouddelivery.R;
import com.lw.clouddelivery.bean.ShouZhiItem;
import com.lw.clouddelivery.conf.INI;
import com.lw.clouddelivery.ui.adapter.ShouZhiListAdapter;
import com.lw.clouddelivery.util.DCUtil;
import com.lw.clouddelivery.util.MySPTool;
import com.lw.clouddelivery.util.PayResult;
import com.lw.clouddelivery.util.SignUtils;
import com.lw.clouddelivery.util.UIHelper;

/**
 *  充值界面
 * @author wxl
 *
 */
public class RechargeActivity extends BaseActivity{

	// 商户PID
	public static final String PARTNER = "2088421706625395";
	// 商户收款账号
	public static final String SELLER = PARTNER;
	// 商户私钥，pkcs8格式
	public static final String RSA_PRIVATE = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAKXtpwzcXVnWHZZaISyNSQIpICNQIKjoMjJ97COJiWyt0bGwvtLowLhpgnoUoDh0DdLo8TfkUMkmffv7BeE87OdVkcLxLc3JFU7I79V7ZLfkILnFypZpoEGKEZZKWhGBnA624Id/HHGILQiM0zyDURMiMGEVXu5SKgikoakcFirfAgMBAAECgYEAok4nO5NK1Rd2dZ3QGW5SXgSwZRp59/65K161X8WnYWFdXvzOjCwUZ879TEFkwBP1ebtWSR/SThCS56qSA9K+v66f7zBj5iDOlDY+J49Nhrt8ZxfUzUEQAptXW+UQ/a6kocteKJv4Q3WK5tbRvKtMMcfQU0SU1Vfev4yAOZ+N7LECQQDbO2US9TWxPJ6vuH/giztmW4kGATYq+o1Nm1a6yxoyQLG4kJ7nXpRd4t8D0w8N4ShzfnTEwp9a2E228tVGQ1wtAkEAwcGxk3wIR8/qEjnsQvNnOflZh1gy9dOrU1YOgRtaw0J5h7d9NcfJKLsX+zmnBXF2PDhePA1el+iuuDyBG/DuuwJBAJrHAyPwRNj9oFcVHgssoWIxi7rWjiSnwa0h14iHLdZ+wDo6uEVHC96A9sxfbQhyhzmK9OuZMCJWBf/8z0AM/H0CQQCryMhhI5HP4ZX9LN+9CELYUqw+5ELiUxXOIDlh3FffULuhSVy45vYBp8d+VMIxgEQ85hkekxfwBTuLWxF5LWbrAkAjhUbtq7vX6SSXhbYuJcoLFyf3g5I/j/5NQiWc81AV69E3R8/Ydwil4rYTOx/9RKLYUUve9Yj9F7wxDbHvcYQk";
	// 支付宝公钥
	public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCl7acM3F1Z1h2WWiEsjUkCKSAjUCCo6DIyfewjiYlsrdGxsL7S6MC4aYJ6FKA4dA3S6PE35FDJJn37+wXhPOznVZHC8S3NyRVOyO/Ve2S35CC5xcqWaaBBihGWSloRgZwOtuCHfxxxiC0IjNM8g1ETIjBhFV7uUioIpKGpHBYq3wIDAQAB";
	private static final int SDK_PAY_FLAG = 1;

	private static final int SDK_CHECK_FLAG = 2;
	private RelativeLayout relative_recharge;
	private RelativeLayout relative_alipay;
	private TextView recharge_num;
	private Button recharge_btn;
	private int choose = 0;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentLayout(R.layout.activity_recharge);
		getTitleBar().initTitleText("充值");
		
		findViews();
	}
	
	@Override
	public void findViews() {
		super.findViews();
		relative_recharge = (RelativeLayout)this.findViewById(R.id.relative_recharge);
		relative_alipay = (RelativeLayout)this.findViewById(R.id.relative_alipay);
		recharge_num = (TextView)this.findViewById(R.id.recharge_num);
		recharge_btn = (Button)this.findViewById(R.id.recharge_btn);

		relative_recharge.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {new  AlertDialog.Builder(RechargeActivity.this)  
			.setTitle("请选择" )  
			.setIcon(android.R.drawable.ic_dialog_info)                  
			.setSingleChoiceItems(new  String[] {"50元","100元", "200元", "500元" , "1000元"},  0 ,
			  new  DialogInterface.OnClickListener() {  
			     public   void  onClick(DialogInterface dialog,  int  which) {  
			        switch (which) {
					case 0:
						choose = 0;
						recharge_num.setText("50.00元");
						
						break;
					case 1:
						choose = 1;
						recharge_num.setText("100.00元");
						break;
					case 2:
						choose = 2;
						recharge_num.setText("200.00元");
						break;
					case 3:
						choose = 3;
						recharge_num.setText("500.00元");
						break;
					case 4:
						choose = 4;
						recharge_num.setText("1000.00元");
						break;

					default:
						break;
					} 
			        dialog.dismiss();
			     }  
			  }  
			)  
			.setNegativeButton("取消" ,  null )  
			.show();
			}
		});
		relative_alipay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
			}
		});
		recharge_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if ("请选择充值金额".equals(recharge_num.getText().toString())) {
					new AlertDialog.Builder(RechargeActivity.this)
							.setTitle("提示")
							.setMessage("请选择要充值的金额！")
							.setPositiveButton("确定",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialoginterface, int i) {
											
										}
									}).show();
					return;
				} 
				String orderInfo = null;
		        switch (choose) {
				case 0:
					// 订单
					orderInfo = getOrderInfo("充值金额", MySPTool.getUid(RechargeActivity.this), "50.00");
					break;
				case 1:
					// 订单
					orderInfo = getOrderInfo("充值金额", MySPTool.getUid(RechargeActivity.this), "100.00");
					break;
				case 2:
					// 订单
					orderInfo = getOrderInfo("充值金额", MySPTool.getUid(RechargeActivity.this), "200.00");
					break;
				case 3:
					// 订单
					orderInfo = getOrderInfo("充值金额", MySPTool.getUid(RechargeActivity.this), "500.00");
					break;
				case 4:
					// 订单
					orderInfo = getOrderInfo("充值金额", MySPTool.getUid(RechargeActivity.this), "1000.00");
					break;

				default:
					break;
				}  
				Log.i("aaa", "orderInfo:" + orderInfo);

				// 对订单做RSA 签名
				String sign = sign(orderInfo);
				try {
					// 仅需对sign 做URL编码
					sign = URLEncoder.encode(sign, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}

				// 完整的符合支付宝参数规范的订单信息
				final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
						+ getSignType();

				Runnable payRunnable = new Runnable() {

					@Override
					public void run() {
						// 构造PayTask 对象
						PayTask alipay = new PayTask(RechargeActivity.this);
						// 调用支付接口，获取支付结果
						String result = alipay.pay(payInfo);

						Message msg = new Message();
						msg.what = SDK_PAY_FLAG;
						msg.obj = result;
						mHandler.sendMessage(msg);
					}
				};

				// 必须异步调用
				Thread payThread = new Thread(payRunnable);
				payThread.start();
			}
		});
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SDK_PAY_FLAG: {
				PayResult payResult = new PayResult((String) msg.obj);

				// 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
				String resultInfo = payResult.getResult();

				String resultStatus = payResult.getResultStatus();

				// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
				if (TextUtils.equals(resultStatus, "9000")) {
					MyToast.showText(RechargeActivity.this, "支付成功");
					finish();
				} else {
					// 判断resultStatus 为非“9000”则代表可能支付失败
					// “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
					if (TextUtils.equals(resultStatus, "8000")) {
						MyToast.showText(RechargeActivity.this, "支付结果确认中");

					} else {
						// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
						MyToast.showText(RechargeActivity.this, "支付失败");

					}
				}
				break;
			}
			case SDK_CHECK_FLAG: {
				Toast.makeText(RechargeActivity.this, "检查结果为：" + msg.obj,
						Toast.LENGTH_SHORT).show();
				break;
			}
			default:
				break;
			}
		};
	};

	/**
	 * check whether the device has authentication alipay account.
	 * 查询终端设备是否存在支付宝认证账户
	 * 
	 */
	public void check(View v) {
		Runnable checkRunnable = new Runnable() {

			@Override
			public void run() {
				// 构造PayTask 对象
				PayTask payTask = new PayTask(RechargeActivity.this);
				// 调用查询接口，获取查询结果
				boolean isExist = payTask.checkAccountIfExist();

				Message msg = new Message();
				msg.what = SDK_CHECK_FLAG;
				msg.obj = isExist;
				mHandler.sendMessage(msg);
			}
		};

		Thread checkThread = new Thread(checkRunnable);
		checkThread.start();

	}

	/**
	 * get the sdk version. 获取SDK版本号
	 * 
	 */
	public void getSDKVersion() {
		PayTask payTask = new PayTask(this);
		String version = payTask.getVersion();
		Toast.makeText(this, version, Toast.LENGTH_SHORT).show();
	}

	/**
	 * create the order info. 创建订单信息
	 * 
	 */
	public String getOrderInfo(String subject, String body, String price) {

		// 签约合作者身份ID
		String orderInfo = "partner=" + "\"" + PARTNER + "\"";

		// 签约卖家支付宝账号
		orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

		// 商户网站唯一订单号
		orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";

		// 商品名称
		orderInfo += "&subject=" + "\"" + subject + "\"";

		// 商品详情
		orderInfo += "&body=" + "\"" + body + "\"";

		// 商品金额
		orderInfo += "&total_fee=" + "\"" + price + "\"";

		// 服务器异步通知页面路径
		orderInfo += "&notify_url=" + "\"" + INI.U.BASE+"/alipay/appchongzhiCallBack"+ "\"";

		// 服务接口名称， 固定值
		orderInfo += "&service=\"mobile.securitypay.pay\"";

		// 支付类型， 固定值
		orderInfo += "&payment_type=\"1\"";
		// 支付类型， 固定值
		orderInfo += "&gmt_payment=\"" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "\"";

		// 参数编码， 固定值
		orderInfo += "&_input_charset=\"utf-8\"";

		// 设置未付款交易的超时时间
		// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
		// 取值范围：1m～15d。
		// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
		// 该参数数值不接受小数点，如1.5h，可转换为90m。
		orderInfo += "&it_b_pay=\"30m\"";

		// extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
		// orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

		// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
		orderInfo += "&return_url=\"m.alipay.com\"";

		// 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
		// orderInfo += "&paymethod=\"expressGateway\"";

		return orderInfo;
	}

	/**
	 * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
	 * 
	 */
	public String getOutTradeNo() {
		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss",Locale.getDefault());
		Date date = new Date();
		String key = format.format(date);

		Random r = new Random();
		key = key + r.nextInt();
		key = key.substring(0, 15);
		return key;
	}

	/**
	 * sign the order info. 对订单信息进行签名
	 * 
	 * @param content
	 *            待签名订单信息
	 */
	public String sign(String content) {
		return SignUtils.sign(content, RSA_PRIVATE);
	}

	/**
	 * get the sign type we use. 获取签名方式
	 * 
	 */
	public String getSignType() {
		return "sign_type=\"RSA\"";
	}
}
