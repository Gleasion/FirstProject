package Service;

import DAO.CustomerDAO;
import DAO.HomeDAO;
import DAO.MenuDAO;
import Util.JDBCUtil;
import Util.ScanUtil;
import Util.View;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeService {

	private static HomeService instance = null;
	private HomeDAO homeDAO;
	private JDBCUtil jdbcUtil;
	private CustomerDAO customerDAO;
	private Object month;

	private HomeService() {
		jdbcUtil = JDBCUtil.getInstance();
		Connection connection = jdbcUtil.getConnection();
		homeDAO = new HomeDAO(connection);
		customerDAO = CustomerDAO.getInstance();
	}

	public static HomeService getInstance() {
		if (instance == null)
			instance = new HomeService();
		return instance;
	}

	// 홈화면
	public int home() {
	System.out.println();
		System.out.println();
		System.out.println(" ___________________________________________");
		System.out.println("|  이벤트 발생!		        [-] [ㅁ] [x] |");
		System.out.println("|―――――――――――――――――――――――――――――――――――――――――――|");
		System.out.println("| <차차생각해볼게> 커피가 둘이 먹다 하나 죽어도 모른다고 합니다    |");
		System.out.println("|   확인해보시겠습니까?                           |");
		System.out.println("|       __________         __________       |");
		System.out.println("|      |  1. 매장     |       |  2. 포장     |      |");
		System.out.println("|       ――――――――――         ――――――――――       | ");
		System.out.println("|___________________________________________|");
		System.out.println();
		System.out.println("         화장실 및 와이파이 비밀번호는 . . .");
		System.out.println();
		System.out.println("       █████╗      ███╗   ███╗");
		System.out.println("       ██╔══██╗    ████╗ ████║");
		System.out.println("       ██║  ██║    ████╗ ████║");
		System.out.println("       ██║  ██║    ██╔████╔██║");
		System.out.println("       ██████╔╝    ██║╚██╔╝██║  부탁드립니다🙏");
		System.out.println("       ╚═════╝     ╚═╝     ╚═╝");
		System.out.println("────────────────────────────────────────────\n");
		System.out.print(" >> ");

		switch (ScanUtil.nextInt()) {
		case 1:
			System.out.println("        매장 이용을 선택하셨습니다.");
			System.out.println("            /⌒  ヽ　        / ⌒ \\");
			System.out.println("          ｜    ｜|　  　|｜    |");
			System.out.println("          ｜    ｜|　  　|｜    |");
			System.out.println("          ｜    ｜|　  　|｜    |");
			System.out.println("          ｜      ｜   ー  ｜       |");
			System.out.println("          ｜  ／　  　　＼    |");
			System.out.println("          /　　　　　  　      🌸");
			System.out.println("         /　          　　  　　       🌸");
			System.out.println("       (  　  ●　　ο　　●　    )");
			System.out.println("/////             /////   수줍...");
			return View.MAIN;
		case 2:
			System.out.println("포장은 일회용컵이 제공되므로, 매장 이용이 불가능합니다.");
			System.out.println("            /⌒  ヽ　        / ⌒ \\");
			System.out.println("          ｜    ｜|　  　|｜    |");
			System.out.println("          ｜    ｜|　  　|｜    |");
			System.out.println("          ｜    ｜|　  　|｜    |");
			System.out.println("          ｜      ｜   ー  ｜       |");
			System.out.println("          ｜  ／　  　　＼    |");
			System.out.println("          /　　　　　  　      🌸");
			System.out.println("         /　          　　  　　       🌸");
			System.out.println("       (  　  ●　　ο　　●　    )");
			System.out.println("/////             /////   머쓱...");
			return View.MAIN;

		case 00000:
			return View.ADMIN_LOGIN;
		default:
			System.out.println("♡ 잘못된 접근입니다. ♡");
			System.out.println("확인 >> Enter ");
			ScanUtil.nextLine();
			return View.HOME;
		}
	}

	// 메인화면
	public int main() {
		System.out.print("────────────────────────────────────────────\n");
		System.out.println("           1. 회원");
		System.out.println("           2. 회원가입");
		System.out.println("           0. 이전 페이지");
		System.out.print("────────────────────────────────────────────\n");
		System.out.print(" >> ");
		switch (ScanUtil.nextInt()) {
		case 1:
			System.out.println("로그인 정보를 입력해주세요.");
			return View.SIGNIN;
		case 2:
			return View.SIGNUP;
		case 0:
			return View.HOME;
		default:
			System.out.println("♡ 잘못된 접근입니다. ♡");
			System.out.println("확인 >> Enter ");
			ScanUtil.nextLine();
			return View.MAIN;
		}
	}

	// 관리자 로그인
	public int adminLogin() {
		System.out.print("────────────────────────────────────────────\n");
		System.out.println("              [관리자 로그인]");
		System.out.print("────────────────────────────────────────────\n");
		System.out.println();
		System.out.print("            비밀번호를 입력하세요: ");

		String password = ScanUtil.nextLine();

		if (password.equals("7777")) {
			System.out.println("♡ 로그인 성공 ♡");
			System.out.println("확인 >> Enter ");
			ScanUtil.nextLine();
			return View.ADMIN_HOME;
		} else {
			System.out.println("♡ 비밀번호가 일치하지 않습니다. ♡");
			System.out.println("확인 >> Enter ");
			ScanUtil.nextLine();
			return View.ADMIN_LOGIN;
		}
	}

	// 관리자 홈 화면
	public int adminHome() {
		System.out.print("────────────────────────────────────────────\n");
		System.out.println("           [관리자 메뉴]");
		System.out.print("────────────────────────────────────────────\n");
		System.out.println();
		System.out.println("           1. 메뉴 등록");
		System.out.println("           2. 메뉴 수정");
		System.out.println("           3. 메뉴 삭제");
		System.out.println("           4. 월별 매출 조회");
		System.out.println("           5. 일별 매출 목록");
		System.out.println("           6. 회원 탈퇴");
		System.out.println("           0. 이전 페이지");
		System.out.println();
		System.out.print("────────────────────────────────────────────\n");
		System.out.print(" >> ");
		switch (ScanUtil.nextInt()) {
		case 1:
			return View.MENU_ADD;
		case 2:
			return View.MENU_UPDATE;
		case 3:
			return View.MENU_DELETE;
		case 4:
			return View.TOTAL_SALES;
		case 5:
			return View.DAILY_SALES;
		case 6:
			return View.DELETE;
		case 0:
			return View.HOME;
		default:
			System.out.println("♡ 잘못된 접근입니다. ♡");
			System.out.println("확인 >> Enter ");
			ScanUtil.nextLine();
			return View.ADMIN_HOME;
		}
	}

	// 메뉴 추가
	public int menuAdd() {
		System.out.print("추가할 메뉴번호> ");
		int menuNo = ScanUtil.nextInt();
		System.out.print("추가할 메뉴명 > ");
		String food = ScanUtil.nextLine();
		System.out.print("위 메뉴의 가격 > ");
		int price = ScanUtil.nextInt();
		System.out.println("확인 >> Enter ");
		ScanUtil.nextLine();

		MenuDAO menuDAO = MenuDAO.getInstance();
		int result = MenuDAO.addMenu(menuNo, food, price);

		if (result == 1) {
			System.out.printf("메뉴번호 : %s, 메뉴명 : %s, 가격 : %d 등록 완료\n", menuNo, food, price);
			System.out.println("확인 >> Enter ");
			ScanUtil.nextLine();
			return View.ADMIN_HOME;
		} else {
			System.out.println("메뉴 등록에 실패하였습니다.");
			System.out.println("확인 >> Enter ");
			ScanUtil.nextLine();
		}
		return result;
	}

	// 메뉴 수정
	public int menuUpdate() {
		System.out.print("수정할 메뉴 번호 > ");
		int menuNo = ScanUtil.nextInt();
		ScanUtil.nextLine();

		System.out.print("새로운 메뉴명 > ");
		String newFood = ScanUtil.nextLine();
		System.out.print("새로운 가격 > ");
		int newPrice = ScanUtil.nextInt();
		System.out.println("확인 >> Enter ");
		ScanUtil.nextLine();

		MenuDAO menuDAO = MenuDAO.getInstance();
		int result = menuDAO.updateMenu(menuNo, newFood, newPrice);

		if (result == 1) {
			System.out.printf("메뉴번호 : %s, 메뉴명 : %s, 가격 : %d 수정 완료\n", menuNo, newFood, newPrice);
			System.out.println("확인 >> Enter ");
			ScanUtil.nextLine();
			return View.ADMIN_HOME;
		} else {
			System.out.println("메뉴 수정에 실패하였습니다.");
			System.out.println("확인 >> Enter ");
			ScanUtil.nextLine();
		}
		return result;
	}

	// 메뉴 삭제
	public int menuDelete() {
		System.out.print("삭제할 메뉴 번호 > ");
		int menuNo = ScanUtil.nextInt();

		MenuDAO menuDAO = MenuDAO.getInstance();
		int result = menuDAO.deleteMenu(menuNo);

		if (result == 1) {
			System.out.println("메뉴가 삭제되었습니다.");
			System.out.println("확인 >> Enter ");
			ScanUtil.nextLine();
			return View.ADMIN_HOME;
		} else {
			System.out.println("삭제에 실패하였습니다.");
			System.out.println("확인 >> Enter ");
			ScanUtil.nextLine();
		}
		return result;
	}

	public int totalSales() {
		System.out.print("조회할 월을 입력하세요 (1-12): ");
		int month = ScanUtil.nextInt();
		ScanUtil.nextLine();

		// 월별 매출액을 저장하기 위한 Map 객체 생성
		Map<String, Integer> monthlySales = new HashMap<>();

		// 월별 매출액 조회
		List<Map<String, Object>> salesList = homeDAO.getMonthlySales();
		for (Map<String, Object> sale : salesList) {
			String saleMonth = (String) sale.get("MONTH");
			int sales = (int) sale.get("SALES");
			monthlySales.put(saleMonth, sales);
		}

		// 입력받은 월에 해당하는 매출총액 계산
		String targetMonth = String.format("%02d", month);
		int targetSales = monthlySales.getOrDefault(targetMonth, 0);

		// 결과 출력
		System.out.println(targetMonth + "월 매출총액: " + targetSales);
		System.out.println("확인 >> Enter ");
		ScanUtil.nextLine();
		return View.ADMIN_HOME;
	}

	// 일매출 조회 메서드
	public int dailySales() {
		System.out.print("조회할 날짜를 입력하세요 (YY/MM/DD): ");
		String date = ScanUtil.nextLine();

		List<String> dailySalesList = homeDAO.getDailySales(date);

		if (dailySalesList.isEmpty()) {
			System.out.println("해당 날짜에 대한 매출 데이터가 없습니다.");
		} else {
			System.out.println("날짜별 매출액:");
			for (String dailySales : dailySalesList) {
				System.out.println(dailySales);

			}
		}
		System.out.println("확인 >> Enter ");
		ScanUtil.nextLine();
		return View.ADMIN_HOME;
	}

	// 회원 탈퇴
	public int delete() {
		System.out.print("탈퇴할 회원의 전화번호를 입력하세요: ");
		String telNo = ScanUtil.nextLine();

		int result = homeDAO.delete(telNo);

		if (result == 1) {
			System.out.println("회원 탈퇴가 완료되었습니다.");
			System.out.println("확인 >> Enter ");
			ScanUtil.nextLine();
			return View.ADMIN_HOME;
		} else {
			System.out.println("회원 탈퇴에 실패하였습니다.");
			System.out.println("확인 >> Enter ");
			ScanUtil.nextLine();
			return View.ADMIN_HOME;
		}
	}

}