package Service; 

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import Controller.Controller;
import DAO.CartDtlDAO;
import DAO.OrdersDAO;
import DAO.PayDAO;
import Util.PrintUtil;
import Util.ScanUtil;
import Util.View;

public class PayService {
	
	private static PayService instance = null;
	private PayService() {}
	
	private String custTelNo;
	
	public static PayService getInstance() {
		if(instance == null) instance = new PayService();
		return instance;
	}
	
	PayDAO payDAO = PayDAO.getInstance();
	OrdersDAO ordersDAO = OrdersDAO.getInstance();
	CartDtlDAO cartDtlDao = CartDtlDAO.getInstance();
	
	public int paymenthome() {
		PrintUtil.payBar1();
		
		Object loginInfoO = Controller.sessionStorage.get("loginInfo");
		Map<String, Object> loginInfo = (Map<String, Object>) loginInfoO;
		
		String ordersNo = Controller.sessionStorage.get("orderNo") + "";
		custTelNo = (String) loginInfo.get("CUST_TEL_NO").toString();
		
		switch (ScanUtil.nextInt()){
		case 1: 
			payDAO.usualPayment();
		    System.out.println("결제가 완료되었습니다. \n현재 적립금 : "+payDAO.currentMileageUsual(custTelNo)+"원");
			ordersDAO.updateOrdersBuy(ordersNo);
			ordersDAO.updateOrdersPrice(ordersNo);
			
			break;
			
		case 2: 
			PrintUtil.mileageBar1();
			System.out.println("현재 보유하신 적립금은 "+payDAO.getMileage(custTelNo)+"원 입니다.");
			System.out.println("사용하실 적립금을 입력해주세요(최소사용금액 3000원, 100원 단위 사용 가능)");
			System.out.print(">>>>>	");
			payDAO.usingMileage = ScanUtil.nextInt();
			
				if(payDAO.usingMileage>=3000 && payDAO.usingMileage %100 == 0 && payDAO.usingMileage<=payDAO.getMileage(custTelNo)) {
					payDAO.paymentUsingMileage(custTelNo);
					System.out.println("결제가 완료되었습니다. \n현재 적립금 : "+payDAO.currentMileageUsingMileage(custTelNo));
					ordersDAO.updateOrdersBuy(ordersNo);
					ordersDAO.updateOrdersPrice(ordersNo);
					
					
				}else if(payDAO.usingMileage>payDAO.getMileage(custTelNo)){
					System.out.println("보유한 적립금을 초과하여 사용할 수 없습니다. 다시 진행해주세요.");
					payDAO.usingMileage = 0;
					return View.PAY;
				}else {
					System.out.println("적립금 사용 조건에 맞지 않습니다. 사용조건에 맞게 다시 입력하거나 일반 결제로 진행해주세요.");
					payDAO.usingMileage = 0;
					return View.PAY;
				}
				break;
				
				
			
		case 0: return View.MAIN;
		
		default:
			System.out.println(" * 잘못된 접근입니다.");
			return View.HOME;
		}
		System.out.println();
		System.out.println();
		System.out.println("영수증 출력을 위해 Enter키를 눌러주세요");
		System.out.println(ScanUtil.nextLine());
		System.out.println(receipt());
		return View.END;	
	}
	
	public int receipt() {
		System.out.println();
		System.out.println();
		System.out.println(" ───────────────────────────────────────────────────────");
		System.out.println("\t\t[영\t수\t증]\t\t\t\n");
		System.out.println("\t\t\t\t\t상호명 : 차차 생각해볼게");
		System.out.println("\t\t\t\t\t         대표명 : 재드래곤");
		System.out.println("\t\t\t      주소 : 대전광역시 중구 계룡로 846, 3-4층");
		System.out.println("\t\t\t         주문 일시 : " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		System.out.println("\n ───────────────────────────────────────────────────────");
		System.out.println("    메뉴번호\t             메뉴명\t     단가\t          수량\t\t       금액");	
		System.out.println(" ───────────────────────────────────────────────────────");	
		
		Object loginInfoO = Controller.sessionStorage.get("loginInfo");
		Map<String, Object> loginInfo = (Map<String, Object>) loginInfoO;
		String ordersNo = Controller.sessionStorage.get("orderNo") + "";
		
		List<Map<String, Object>> cartDtlList = cartDtlDao.getCartDtlList(ordersNo);
		int cnt = 1;
	    for (int i = 0; i < cartDtlList.size(); i++) {
	    	Map<String, Object> cart = cartDtlList.get(i);
	        String menuName = (String)cart.get("MENU_NAME");
	        BigDecimal menuPrice = (BigDecimal)cart.get("MENU_PRICE");
	        BigDecimal menuCount = (BigDecimal)cart.get("MENU_CNT");
	        BigDecimal totalPrice = (BigDecimal)cart.get("TOTAL_PRICE");
	            
	        System.out.printf("\n%5d \t %10s \t %5d \t %5d  \t %5d",
	                cnt, menuName, menuPrice.intValue(), menuCount.intValue(), totalPrice.intValue());
	        cnt++;
	    }
		
		System.out.println("\n\n────────────────────────────────────────────────────────\n");
		System.out.println("   합계   :          \t\t\t\t" + payDAO.getPrice(ordersNo));
		System.out.println("   사용한 적립금   : \t\t\t\t\t" + payDAO.usingMileage);
		System.out.println("   받을 금액    : \t\t\t\t\t" + (payDAO.getPrice(ordersNo)-payDAO.usingMileage));
		System.out.println("   받은 금액    : \t\t\t\t\t" + (payDAO.getPrice(ordersNo)-payDAO.usingMileage));
		System.out.println("\n─────────────────────────────────────────────────────────");
		System.out.println("║▌│█║▌│ █║▌│█│║▌║║▌│█║▌│ █║▌│█│║▌║║▌│█║▌│ █║▌│█│║▌║║▌║║▌║");
		System.out.println("║▌│█║▌│ █║▌│█│║▌║║▌│█║▌│ █║▌│█│║▌║║▌│█║▌│ █║▌│█│║▌║║▌║║▌║");
		System.out.println("║▌│█║▌│ █║▌│█│║▌║║▌│█║▌│ █║▌│█│║▌║║▌│█║▌│ █║▌│█│║▌║║▌║║▌║");
		System.out.println("─────────────────────────────────────────────────────────");
		System.out.println("\n\t\t     주문 번호 : " + ordersNo);
		System.out.println("\n\n\n\n");
		System.out.println("확인 >> Enter");
		ScanUtil.nextLine();
		return View.HOME;
	}
}
