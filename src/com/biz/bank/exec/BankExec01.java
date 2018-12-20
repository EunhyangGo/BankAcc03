package com.biz.bank.exec;

import com.biz.bank.service.BankService;
import com.biz.bankVO.BankVO;

public class BankExec01 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		BankVO[] banks = new BankVO[10];
		banks[0] = new BankVO();
		
		banks[0].setStrID("001");

		String strFile = "src/com/biz/bank/bankBalance.txt";
		String saveFile ="sec/com/biz/bank/iolist/추가.txt";
		BankService bs = new BankService(strFile);
		
		bs.readBalance();
		bs.bankMenu();
	
	}

}
