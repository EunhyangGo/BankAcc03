package com.biz.bank.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.biz.bankVO.BankVO;

public class BankService {
	
	List<BankVO> bankList;
	String balFile;
	Scanner sc;
	
	public BankService(String balFile) {
		bankList = new ArrayList();
		sc = new Scanner(System.in);
		this.balFile = balFile;
	}
	public void readBalance() {
		FileReader fr;
		BufferedReader buffer;
		
		try {
			fr = new FileReader(balFile);
			buffer = new BufferedReader(fr);
			
			while(true) {
			String reader = buffer.readLine();
			if(reader == null) break;
			String[] st = reader.split(":");
			BankVO vo = new BankVO();
			vo.setStrID(st[0]);
			vo.setIntBalance(Integer.valueOf(st[1]));
			vo.setStrLastDate(st[2]);
			bankList.add(vo);
			}
			buffer.close();
			fr.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    public void bankMenu() {
    	
    	while(true) {
    	System.out.println("================================");
    	System.out.println("1.입금   2.출금  3.계좌조회  0.종료");
    	System.out.println("--------------------------------");
    	System.out.println("업무를 선택하세요>>");
    	String strSelect = sc.nextLine();
    	int intSelect = Integer.valueOf(strSelect);
    	if(intSelect == 0) System.out.println("Good bye");
    	if(intSelect == 1) this.bankInput(); //System.out.println("입금");
    	if(intSelect == 2) this.bankOutput(); //System.out.println("출금");
    	if(intSelect == 3) System.out.println("계좌조회");
    	
    	}
    }
    public void bankInput() {
    	System.out.println("계좌번호를 입력하세요>>");
    	String strId = sc.nextLine();
    	BankVO vo = bankFindId(strId);
    	if(vo == null) return;
    	
    	// 계좌번호가 정상이고, vo에는 해당 계좌번호의 정보가 담겨 있다.
    	
    	System.out.println("입금액 >>");
    	String strIO = sc.nextLine();
    	int intIo = Integer.valueOf(strIO);
    	
    	// 새로운 코드
    	// 입금일 경우 vo.strIO에 "입금"문자열 저장
    	// vo.intIOCash에 입금금액을 저장
    	// vo.balance에 + 입금액을 저장한다.
    	
    	vo.setStrIO("입금");
    	vo.setIntIOCash(intIo);
    	vo.setIntBalance(vo.getIntBalance() + intIo);
    	
    	// old java 코드로 현재 날짜 가져오기
    	SimpleDateFormat sm = new SimpleDateFormat("yyyy-mm-dd");
    	Date curDate = new Date();
    	String strDate = sm.format(curDate);
    	
    	// New Java(1.8) 코드로 현재 날짜 가져오기
    	LocalDate ld = LocalDate.now();
    	strDate = ld.toString();
    	vo.setStrLastDate(strDate);
    	
    	System.out.println("입금처리 완료!!");
    		
    	}
    public BankVO bankFindId(String strId) {
    	/*
    	 * 계좌번호를 매개변수로 받아서 bankList에서 계좌를 조회하고
    	 * bankList에 계좌가 있으면, 찾은 BankVO(vo)를 return하고 
    	 * 없으면 null값을 retrun하도록 한다.
    	 */
    	for(BankVO vo : bankList) {
    		if(vo.getStrID().equals(strId)) {
    			return vo;
    		}
    		
    	}
		return null;
    	
    }
    public void bankOutput() {
    	System.out.println("계좌번호를 입력하세요>>");
    	String strId = sc.nextLine();
    	BankVO vo = bankFindId(strId);
    	if(vo == null) { 
    		System.out.println("계좌번호 요류");
    		return;
    	}
    	
    	System.out.println("출금액 >>");
    	String strop = sc.nextLine();
    	int intop = Integer.valueOf(strop);
    	int intBalance = vo.getIntBalance();
    	int intB = intBalance -intop;
    	vo.setIntBalance(intB);
    	if(intBalance < intop) {
    		System.out.println("잔액부족");
    		return;
    	}
    	// 새로운 코드
    	// 출금일 경우 vo.strIO에 "출금"문자열 저장
    	// vo.intIOCash에 출금금액을 저장
    	// vo.balance에 - 출금액을 저장한다.
    	vo.setStrIO("출금");
    	vo.setIntIOCash(intop);
    	vo.setIntBalance(vo.getIntBalance() - intop);
    	
    	this.bankIOWrite(vo);
    	System.out.println("출금처리 완료!!");
    	
    }
    public void bankIOWrite(BankVO vo) { //vo를 매개변수로 넘기는게 중요하다.
    	
    	String filePath = "src/com/biz/bank/iolist/";
    	
    	String strId = vo.getStrID();
    	int intBal = vo.getIntBalance();
    	String strLastDate = vo.getStrLastDate();
    	
    	String strIO = vo.getStrIO();
    	int intIO = vo.getIntIOCash();
    	
    	FileWriter fw;
    	PrintWriter pw;
    	
    	try {
			fw = new FileWriter(filePath + strId,true);
			pw = new PrintWriter(fw);
			
			pw.print(strId);
			pw.print(strLastDate);
			pw.print(strIO);
			if(strIO.equals("입금")) {
				pw.print(intIO);
				pw.print(0);
			}else {
				pw.print(0);
				pw.print(strIO);
			}
			pw.println(intBal);
			pw.close();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	
    	
    	
    }
}
