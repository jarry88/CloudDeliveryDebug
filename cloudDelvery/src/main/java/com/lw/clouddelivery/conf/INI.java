package com.lw.clouddelivery.conf;

public class INI {

	public static final String UID = "";
	public static final String LAT = "";
	public static final String LON = "";
	
	public static final String SERVER_PHONE = "4007759697"; //客服电话
//	public static final String SERVER_PHONE = "4006406808";
	public static class U {
//		public final static String BASE_URL = "http://iyunsong.com/yunsong/interface/";//云送正式服务器
//		public final static String BASE_URL = "http://101.200.238.193:80/yunsong/interface/";  //新
//		public final static String BASE_URL = "http://120.26.41.195:88/shida/interface/"; //旧  本地服务器
//		public final static String BASE_URL = "http://zhuoguosoft.com/shida/interface/"; //旧  本地服务器
		
		public static String BASE = "http://ayibuda.com";//壹步达  服务器
//		public static String BASE = "http://192.168.1.2:8080/ys";//壹步达  服务器
		
		public final static String BASE_URL = BASE+"/interface/"; 
//		public final static String BASE_URL = "http://192.168.1.2:8080/ys/interface/"; //壹步达  服务器
		
		
		//登陆
		public final static String LOGIN = BASE_URL + "userlogin";
		//用户信息
		public final static String USER_INFO = BASE_URL + "userInfo";
		//消息列表
		public final static String MESSAGE_LIST = BASE_URL + "messageList";
		//帮助列表
		public final static String HELP_LIST =  BASE_URL + "helpList";
		//帮助详情
		public final static String HELP_DETAIL = BASE_URL + "helpbyid";
		//今日订单
		public final static String ORDER_TODAY = BASE_URL + "jrorderList";
		//交通工具列表
		 public final static String VEHICLE_LIST = BASE_URL + "mediatypeList";
		 //选择自己的交通工具
		 public final static String SELECT_VEHICLE = BASE_URL + "MyMedia";
		 //今日订单完成情况
		 public final static String ORDER_COUNT = BASE_URL + "myjrorderCount";
		 //意见反馈
		  public final static String FEEDBACE = BASE_URL + "insertfeedback";
		 //关闭抢单
		  public final static String CLOSEORDER = BASE_URL + "qdorderIsNew";
		  //抢单
		  public final static String QIANGDAN = BASE_URL + "qdorder";
		  //待抢单数据列表
		  public final static String QIANGDAN_LIST = BASE_URL + "qdorderList";
		 //发送短信验证码
		  public final static String SEND_SMS = BASE_URL + "interfacepwd";
		  //验证取件密码
		  public final static String CHECK_QUJIAN_SMS = BASE_URL + "yzqpwd";
		  //验证收件密码 
		  public final static String CHECK_SHOUJIAN_SMS = BASE_URL + "sdorder";
		  //我的收入接口
		  public final static String MY_INCOMING = BASE_URL + "myrorderCount"; 
		  //我的订单
		  public final static String MY_ORDERLIST = BASE_URL + "myorderList"; 
		  //现金账户
		  public final static String CASH_ACCOUNT = BASE_URL + "mypay";
		  //收支明细列表
		  public final static String SHOUZHI_LIST = BASE_URL + "szlist";
		  //奖惩列表
		  public final static String JC_LIST = BASE_URL + "jclogList";
		  //上传图片
		  public final static String UPDATE_IMG = BASE_URL + "qwdimg";
		  //修改密码
		  public final static String RESET_PWD = BASE_URL + "Resetpassword";
		  //提现申请
		  public final static String TIXIAN = BASE_URL + "txshenqing";
		  //提交银行卡信息
		  public final static String BANKINPUT = BASE_URL + "updateUserbankInfo";
		  // 发送修改密码的短信验证码
		  public final static String SEND_PWD_SMS = BASE_URL + "interfaceorderyzm";
		  //是否可提现
		  public final static String CAN_TIXIAN = BASE_URL + "txCount";
		  //版本列表
		  public final static String VERSION_LIST = BASE_URL + "veisonList";
		  
		  //注册
		  public final static String REGISTERURL = BASE_URL + "registration";
		  //填写个人信息
		  public final static String UPDATEUSERINFO = BASE_URL + "upUserInfo";
		  // 更新考试成绩
		  public final static String EXAMUPDATESOURCE = BASE_URL + "upscore";
		  
		  
	}
	
	public static class BROADCASE  {
		public static String ORDER = "com.lw.clouddelivery.Broadcase.OrderReceiver";
	}
	public static class P {
		public static String MOBILE ="mobile";
		public static String PASSWORD = "password";
		public static String TOKEN = "tokenStr";
		public static String ID = "id";
		public static String CURRENT_PAGE = "currentPage";
		public static String TYPE = "type";
		public static String LON  = "long1";
		public static String LAT = "lat1";
		public static String UID = "uid";
		public static String TITLE = "title";
		public static String CONTENT = "content";
		public static String PWD = "pwd"; 
		public static String CTYPE = "ctype"; // 是否重置密码
		public static String RECARD_TYPE = "recard_type"; //记录类型
		public static String ORDERID = "orderId";//订单id
		public static String MONEY = "money";
		public static String NAME = "name"; //持卡人姓名
		public static String CARD_NO = "cardno"; //银行卡卡号
		public static String BANK_TYPE = "banktype"; //开户行
		public static String BANK_NAME = "bankname"; //开户行名称
		public static String SOURS = "sours"; //重置密码短信验证码
		public static String rmobileNo = "rmobileNo";//邀请人手机号
		public static String TALLER = "height";//身高
		public static String WEIGHT = "weight";//体重
		public static String IMGONE = "img1";//体重
		public static String IMGTWO = "img2";//体重
		public static String IMGTHREE = "img3";//体重
		public static String CARDID = "card";//身份证
		public static String SEX = "sex";//性别
		public static String SCORE = "score";//分数
		
		}
	
	public static String getBaseUrl() {
		return U.BASE_URL;
	}
	
	public static class CODE { 
		public static String OK = "00";
	}
	
	public static class SP {
		public static String PHONE = "phoneNum";
		public static String PASSWORD = "password";
		public static String UNAME = "username";
		public static String UID = "uid";
		public static String TOKEN = "token";
		public static String LAT = "lat";
		public static String LON = "lon";
		public static String LATEST_ORDER_TIME = "latest_order_time"; //最近的订单时间
		public static String VEHICLE_INDEX= "vehicleIndex"; //交通工具的位置
		
		public static String SETTING_VOICE = "setting_voice"; // 设置.声音
		public static String SETTING_VERBOSE = "setting_verbose";  //设置.震动
		public static String SETTING_CONFIEM = "setting_confirm"; //设置.单击抢单时确认
		//记录上次最新通知时间
		public static String MESSAGE_LAST_TIME = "message_last_time"; 
		
		//银行卡信息
		public static String BANK = "banktype";  //开户行
		public static String CARD_NAME = "cardname";  //持卡人姓名
		public static String CARD_NO = "cardno"; //银行卡卡号 
		public static String BANK_NAME = " bankname";//开户行名称 
		
		//个人资料
		public static String ADD = "add";
		public static String IMG1 = "img1";
		public static String IMG2 = "img2";
		public static String IMG3 = "img3";
		public static String HEIGHT = "height";
		public static String WEIGHT = "weight";
		public static String SCORE = "score";
		
	}
	
	public static class STATE {
		public static final String ORDER_WAITING  = "1"; //待抢单
		public static final String ORDER_TAKING = "2"; //取单中
		public static final String ORDER_DELIVERY = "3";//闪送中
		public static final String ORDER_FINISHED = "4"; //已完成
		public static final String ORDER_CANCELLING = "5";//取消中
		public static final String ORDER_CANCEL = "6"; //已取消
	}
	
	public static class ACTION {
		public static final int A_1 = 1;
		public static final int A_2 = 2;
		public static final int A_3 = 3;
		public static final int A_4 = 4;
		public static final int A_5 = 5;
		public static final int A_6 = 6;
		public static final int A_7 = 7;
		public static final int A_8 = 8;
	}
}
