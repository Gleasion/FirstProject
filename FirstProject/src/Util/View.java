package Util;

public interface View {
	public static final int HOME = 0;				// 홈 화면
	public static final int MAIN = 1;		        // 메인 화면
	public static final int ADMIN_LOGIN = 101;		// 관리자 로그인 화면
	public static final int ADMIN_HOME = 102; 		// 관리자 홈 화면
	public static final int MENU_ADD = 10;          // 메뉴 추가
	public static final int MENU_UPDATE = 20;       // 메뉴 수정
	public static final int MENU_DELETE = 30;       // 메뉴 삭제
	public static final int TOTAL_SALES = 40;       // 총매출
	public static final int DAILY_SALES =50;        // 일매출  
	public static final int DELETE = 60;	        // 회원 탈퇴 화면
	public static final int SIGNIN = 103;			// 로그인 화면
	public static final int SIGNUP = 104;			// 회원가입 화면

	public static final int MENU = 2;				// 메뉴선택 화면
	public static final int COFFEE = 201;		    // 커피메뉴
	public static final int NONCOFFEE = 202;		// 논커피메뉴
	public static final int ADE = 203;				// 에이드메뉴
	public static final int SMOOTHIE = 204;			// 스무디메뉴
	public static final int TEA = 205;				// 차 메뉴
	public static final int DESSERT = 206;			// 디저트메뉴
	public static final int OPTION = 207;			// 기타 옵션
	public static final int SELECTMENU = 208;		// 메뉴선택 옵션
	
	public static final int CART = 3;				// 장바구니 화면
	public static final int CARTCOUNTCHANGE = 301;	// 장바구니 수량변경
	public static final int MILEAGE = 4;			// 마일리지 화면
	public static final int PAY = 5;				// 결제 화면
	public static final int RECEIPT = 501;			// 영수증 화면
	
	
	public static final int ERROR = 999;			// 미구현 기능
	public static final int END = 998;				// 종료화면
}