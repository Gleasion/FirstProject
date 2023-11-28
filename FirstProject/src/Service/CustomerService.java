package Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import Controller.Controller;
import DAO.CustomerDAO;
import DAO.MenuDAO;
import DAO.OrdersDAO;
import Util.ScanUtil;
import Util.View;

public class CustomerService {

	private static CustomerService instance = new CustomerService();

	private CustomerService() {
	}

	private static CustomerDAO customerDAO = CustomerDAO.getInstance();

	public static CustomerService getInstance() {
		return instance;
	}

	public int signIn() {
		//로그인 시작 ///////////////////////////////////////////////////////////
		System.out.print("───────────────────────────────────────\n");
		System.out.println("                 로그인");
		System.out.print("───────────────────────────────────────\n");
		System.out.print("전화번호 >> ");
		String custTelNo = ScanUtil.nextLine();
		Map<String, Object> customer = customerDAO.signIn(Collections.singletonList(custTelNo));
		if (customer == null || customer.isEmpty()) {
			System.out.println("♡ 잘못된 접근입니다. ♡");
			System.out.println("확인 >> Enter ");
			ScanUtil.nextLine();
			System.out.print("───────────────────────────────────────\n");
			return View.SIGNIN;
		} else {
			Controller.sessionStorage.put("loginInfo", customer);
			
			int ordersNumber = MenuDAO.getInstance().generateOrderNumber();
			Map<String, Object> param = new HashMap<>();
	        param.put("ORDERS_NO", ordersNumber);
	        
	        Controller.sessionStorage.put("orderNo", ordersNumber);
	        
	        OrdersDAO.getInstance().insertOrder(param);
			System.out.println(">> " + customer.get("CUST_NAME") + "님, 환영합니다! <<");
			System.out.print("───────────────────────────────────────\n");
			return View.MENU;
		}//end if
		//로그인 끝 ///////////////////////////////////////////////////////////
	}
	
	public int signNon() { //비회원 로그인
		 Map<String, Object> customer = new HashMap<>();
		    customer.put("CUST_NO", "Z999");
		    customer.put("CUST_TELNO", "00000000000");
		    Controller.sessionStorage.put("loginInfo", customer);

		    int ordersNumber = MenuDAO.getInstance().generateOrderNumber();
		    Controller.sessionStorage.put("orderNo", ordersNumber);

		    OrdersDAO.getInstance().insertOrder(new HashMap<>());
		    
		    return 0;
	}
	
	public int signUp() {
		System.out.print("───────────────────────────────────────\n");
		System.out.println("                  회원가입");
		System.out.print("───────────────────────────────────────\n");

		String custName;
		while (true) {
			System.out.print("이름 >> ");
			custName = ScanUtil.nextLine();

			if (normalizationName(custName)) {
				break;
			}
		}
		System.out.println();

		String custTelNo;
		while (true) {
			System.out.print("전화번호 >> ");
			custTelNo = ScanUtil.nextLine();

			if (normalizationTel(custTelNo)) {
				break;
			}
		}
		Map<String, Object> telExist = customerDAO.isTelExist(custTelNo);

		if (telExist.size() > 0) {
			System.out.println(">> 이미 가입된 번호입니다. 다시 입력해주세요. <<");
			System.out.println();
			System.out.print("전화번호 >> ");
			custTelNo = ScanUtil.nextLine();

		}

		Map<String, Object> param = new HashMap<>();
		param.put("CUST_NAME", custName);
		param.put("CUST_TEL_NO", custTelNo);

		int result = customerDAO.signUp(param);
		if (result > 0) {
			System.out.println(">> 회원가입이 완료되었습니다. <<");
			  System.out.println("확인 >> Enter ");
				ScanUtil.nextLine();
		} else {
			System.out.println(">> 회원가입에 실패했습니다. <<");
			  System.out.println("확인 >> Enter ");
				ScanUtil.nextLine();
		}

		return View.MAIN;
	}

	// 이름 정규화 true : 정규화 완 | false : 다시 입력
	private boolean normalizationName(String custName) {
		boolean result = true;
		if (!custName.matches("^[가-힣]*$")) {
			System.out.println(">> 문자만 입력가능합니다. <<");
			result = false;
		} else if (custName.length() < 2) {
			System.out.println(">> 올바른 입력이 아닙니다. <<");
			result = false;
		} else
			return result;
		System.out.println();
		System.out.println(">> 다시 입력하세요. <<");
		System.out.println();
		return result;
	}

	// 전화번호 정규화 true : 정규화 완 | false : 다시 입력
	private boolean normalizationTel(String custTelNo) {
		boolean result = true;
		if (!(custTelNo.matches("^[0-9]*")) || (custTelNo.matches("^[가-힣]*$")) || (custTelNo.matches("^[a-zA-Z]*$"))) {
			System.out.println(">> 숫자만 입력 가능합니다. <<");
			result = false;
		} else if (!custTelNo.matches("^01(?:0|1|[6-9])(?:\\d{4})\\d{4}$")) {
			System.out.println(">> 올바른 전화번호 형식이 아닙니다. <<");
			result = false;
		} else
			return result;
		System.out.println();
		System.out.println(">> 다시 입력하세요. <<");
		System.out.println();
		return result;
	}

}