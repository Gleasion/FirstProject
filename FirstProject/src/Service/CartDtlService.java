package Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import Controller.Controller;
import DAO.CartDtlDAO;
import DAO.MenuDAO;
import Util.PrintUtil;
import Util.ScanUtil;
import Util.View;
import VO.OrdersVO;

public class CartDtlService {
	private static CartDtlService instance = null;
	private CartDtlService() {}
	
	public static CartDtlService getInstance() {
		if(instance == null) {
			instance = new CartDtlService();
		}
		return instance;
	}
	public String getMenuNo(String ordersNo) {
		System.out.print("\n───────────────────────────────────────────────────\n");
	    System.out.print(" 변경할 메뉴 번호를 입력하세요 >> ");
	    return ScanUtil.nextLine();
	}
	
	CartDtlDAO cartDtlDao = CartDtlDAO.getInstance();
	
	//카트 목록 보기 공통
	public List<Map<String, Object>> showCart(String ordersNo) {
		PrintUtil.showcartBar1();
		System.out.print("    메뉴번호\t             메뉴명\t     단가\t          수량\t\t       금액");	
		// 뭘 담았는지 장바구니 조회
		List<Map<String, Object>> cartDtlList = cartDtlDao.getCartDtlList(ordersNo);
		
		int cnt = 1;
	    for (int i = 0; i < cartDtlList.size(); i++) {
	    	Map<String, Object> cart = cartDtlList.get(i);
	    	String menuNo = (String)cart.get("MENU_NO");
	        String menuName = (String)cart.get("MENU_NAME");
	        BigDecimal menuPrice = (BigDecimal)cart.get("MENU_PRICE");
	        BigDecimal menuCount = (BigDecimal)cart.get("MENU_CNT");
	        BigDecimal totalPrice = (BigDecimal)cart.get("TOTAL_PRICE");
	            
	        System.out.printf("\n%5d \t %10s \t %5d \t %5d  \t %5d",
	        		menuNo, menuName, menuPrice.intValue(), menuCount.intValue(), totalPrice.intValue());
	        cnt++;
	    }
	        
	    return cartDtlList;   
	}
	
	//장바구니 선택창 조회
	public int selectCart() {
		showCart(Controller.sessionStorage.get("orderNo")+"");
		PrintUtil.showcartBar2();
				
		switch (ScanUtil.nextInt()){
		case 1: return View.MENU;
		case 2: return View.CARTCOUNTCHANGE;	
		case 3: return View.PAY;
		case 0: return View.MAIN;
		default:
			System.out.println(" * 잘못된 접근입니다.");
			return View.MAIN;
		}
	}
	// 수량변경
		public int updateMenuCountInCart() {
		    // 장바구니 목록 보여주기
			Object ordersNoO = Controller.sessionStorage.get("orderNo");
		    String ordersNo;

		    if (ordersNoO instanceof Integer) {
		        Integer ordersNoInteger = (Integer) ordersNoO;
		        ordersNo = String.valueOf(ordersNoInteger);
		    } else if (ordersNoO instanceof String) {
		        ordersNo = (String) ordersNoO;
		    } else {
		        System.out.println("주문번호가 유효하지 않습니다.");
		        return 0;
		    }
		    showCart(ordersNo);
		    
		    // 메뉴번호 가져오기
		    String menuNo = getMenuNo(ordersNo); // 메뉴번호를 가져오는 로직을 추가해야 합니다.
		    System.out.println();
		    System.out.print(" 변경할 수량을 입력하세요 >> ");
		    String menuCnt = ScanUtil.nextLine();
		    System.out.println("\n───────────────────────────────────────────────────\n");
		    // CartDtlDAO 인스턴스 생성
		    CartDtlDAO cartDtlDao = CartDtlDAO.getInstance();

		    // 장바구니 항목의 수량을 변경
		    int rowsUpdated = cartDtlDao.updateCartDtlMenuCount(ordersNo, menuNo, menuCnt);

		    if (rowsUpdated > 0) {
		        System.out.println("장바구니 수량이 변경되었습니다.");
		        System.out.println("주문번호: " + ordersNo);
		        System.out.println("메뉴번호: " + menuNo);
		        System.out.println("변경된 수량: " + menuCnt);
		    } else {
		        System.out.println("장바구니 수량 변경에 실패했습니다.");
		    }
			return selectCart();
		}    
			    

		// 메뉴삭제
		public int deleteMenu(String ordersNo, String menuNo) {
			PrintUtil.showcartBar1();
			//카트목록보기 공통
			List<Map<String, Object>> cartDtlList =showCart(ordersNo);
					
			System.out.print(" ───────────────────────────────────────────────────\n");
			System.out.println("\t 삭제할 메뉴 번호 >>");
			menuNo = ScanUtil.nextLine();
			
			// 입력한 메뉴 번호가 유효한지 확인
			boolean isValidMenuNo = false;
				for(Map<String,Object> cart : cartDtlList) {
					String cartMenuNo = (String) cart.get("MENU_NO");
					if(cartMenuNo.equals(menuNo)) {
						isValidMenuNo = true;
						break;
						}
					}
			
			if(isValidMenuNo) {
				cartDtlDao.deleteCartDtlMenu(ordersNo, menuNo);
				System.out.print(" ───────────────────────────────────────────────────\n");
				System.out.println("			삭제 되었습니다.");
			}else {
				System.out.print(" ───────────────────────────────────────────────────\n");
				System.out.println(" 	               유효하지 않은 메뉴 번호 입니다.");
			}
			return View.CART;
		}
		
		
		
	}