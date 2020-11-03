package com.lw.clouddelivery.ui.exam;

import java.util.ArrayList;

public class DataLoader {

	VoteSubmitItem testItem;
	ArrayList<VoteSubmitItem> testItems;
	public static String[] voteQuestion = { "壹步达专员按照标准服务时间上门取件，每增加5公里，送达时间增加（ ）分钟。（单选）",
			"辱骂、骚扰工作人员，壹步达专员将受这个惩罚措施( ).(单选）",
			"壹步达专员取件开箱验收时，如发现所寄物品为国家明令禁止物品，壹步达专员是否可拒绝提供服务。（单选）",
			"刷单等严重作弊，壹步达专员将受这个惩罚措施（ ）。（单选）", 
			"对用户服务态度不好（辱骂，骚扰用户等其他行为），导致用户对服务的投诉或差评，壹步达专员将罚款（ ）元。（单选）",
			"壹步达专员账户中需保留（ ）元作为账户预留费用？",
			"壹步达专员完成收件后，如配送距离在5公里内，则需要在（ ）分钟之内完成送达。（单选）",
			"壹步达专员每次提现都需扣除（ ）元的银行手续费。（单选）",
			"壹步达专员提现的时候，必须是本人提供在上海实名字开户的卡号，默认的是哪家银行？",
			"在壹步达专员APP上是否可以直接修改手机号码？（单选）" };
	public static  String[][] voteAnswers = { { "A 10", "B 20", "C 30" },
			{ "A 冻结", "B 罚款50元", "C 禁闭1周"},
			{ "A 是", "B 否", "C 无所谓"  },
			{ "A 冻结", "B 罚款100元", "C 禁闭1个月" },
			{ "A 20", "B 50", "C 100" },
			{ "A 100", "B 150", "C 200"  },
			{ "A 15", "B 30", "C 40" },
			{ "A 0", "B 5", "C 10" },
			{ "A 上海建设银行", "B 上海招商银行", "C 上海工商银行" },
			{ "A 是", "B 否", "C 不清楚" }};
	
	// 1C 2a  3a 4a 5c 6c  7c 8a  9b 10a

	/**
	 * @return 测试数据
	 */
	public ArrayList<VoteSubmitItem> getTestData() {

		testItems = new ArrayList<VoteSubmitItem>();
		for (int i = 0; i < voteQuestion.length; i++) {
			testItem = new VoteSubmitItem();
			testItem.itemId = i;
			testItem.voteQuestion = voteQuestion[i];
			for (int j = 0; j < voteAnswers[i].length; j++) {
				testItem.voteAnswers.add(voteAnswers[i][j]);
			}
			testItems.add(testItem);
		}
		return testItems;
	}

}
