package Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Controller.Controller;
import DAO.CartDtlDAO;
import DAO.MenuDAO;
import DAO.OrdersDAO;
import Util.PrintUtil;
import Util.ScanUtil;
import Util.View;

public class MenuService {
	private MenuDAO menuDao;
	private CartDtlDAO cartDtlDAO = CartDtlDAO.getInstance();

	private static MenuService instance;

	private MenuService() {
		menuDao = MenuDAO.getInstance();
		if (menuDao == null) {
			menuDao = new MenuDAO();
		}
	}

	public static MenuService getInstance() {
		if (instance == null) {
			instance = new MenuService();
		}
		return instance;
	}

	public int selectMenu() {
			
		while (true) {
//			System.out.println("\n\t\t주문번호\t" + ordersNumber + "\n");
			PrintUtil.selectMenuBar1();
			switch (ScanUtil.nextInt()) {
			case 1:	return printMenu();
			case 2:	return View.CART;
			case 0:	return View.HOME;
			default:
				System.out.println("\n\n \t\t😡잘못된 선택입니다😡");
				break;
			}
		}
	}

	// 옵션 목록 중 하나를 선택할 수 있도록 도와주는 메소드
	public int option() {
		PrintUtil.optionBar1();
		List<Map<String, Object>> optionList = menuDao.getOptionMenus();
		
			int cnt = 1;
			Map<String, Object> selectedOption = null;

			for (Map<String, Object> option : optionList) {
				String optionName = (String) option.get("MENU_NAME");
				BigDecimal optionPrice = (BigDecimal) option.get("MENU_PRICE");
				System.out.printf("\n%3d.", cnt);
				System.out.printf("%7s ", optionName);
				System.out.print("\t" + optionPrice.intValue() + " 원 \n");
				cnt++;

			}
			//선택할 옵션을 입력하세요 ᐳᐳ
			PrintUtil.optionBar2();
			int option = ScanUtil.nextInt();

			if (option >= 1 && option <= optionList.size()) {
				selectedOption = optionList.get(option - 1);
				String selectedOptionName = (String) selectedOption.get("MENU_NAME");
				BigDecimal selectedOptionPrice = (BigDecimal) selectedOption.get("MENU_PRICE");
				System.out.println("옵션 : " + selectedOptionName);
				System.out.println("가격 : " + selectedOptionPrice.intValue());
				System.out.println("\n─────────────────────────────────────────────────────\n");
				
				//* 옵션
				//CARTDTL 테이블에 insert
			    //params : ordersNo, menuNo : '남민주뮤직큐'
				Map<String, Object> map = optionList.get(option-1);
				
				List<Object> params = new ArrayList<Object>();
            	params.add(Controller.sessionStorage.get("orderNo") + "");
            	params.add((String) map.get("MENU_NAME"));
            	int result = cartDtlDAO.insertCartDtl(params);
            	System.out.println("result : " + result);
				
				return selectMenu();
				
			} else {
				System.out.println("\n─────────────────────────────────────────────────────\n");
				System.out.println("\n \t      😡유효하지 않은 옵션 선택입니다😡");
				System.out.println("\n\n \t              옵션을 다시 선택하세요");
				return option();
			}
		}

	public int printMenu() {
		PrintUtil.menuBar1();
		while (true) {
			int selectedMenu = ScanUtil.nextInt();
			
			switch (selectedMenu) {
			case 1:
				PrintUtil.coffee();
				List<Map<String, Object>> coffeeMenuList = menuDao.getCoffeeMenus();
				int cnt = 1;
				for (Map<String, Object> menu : coffeeMenuList) {
					String menuName = (String) menu.get("MENU_NAME");
					BigDecimal price = (BigDecimal) menu.get("MENU_PRICE");
					System.out.print("  \t  ");
					System.out.printf("%d.    \t", cnt);
					System.out.printf("%15s \t", menuName);
					System.out.printf("%15d 원 \n", price.intValue());
					cnt++;

				}
				PrintUtil.menuBar2();
                int selectedCoffee = ScanUtil.nextInt();
                if (selectedCoffee >= 1 && selectedCoffee <= coffeeMenuList.size()) {//선택 잘함
                	
                	Map<String, Object> map = coffeeMenuList.get(selectedCoffee-1);
                	//CARTDTL 테이블에 insert
                	List<Object> params = new ArrayList<Object>();
                	params.add(Controller.sessionStorage.get("orderNo") + "");
                	params.add((String) map.get("MENU_NAME"));
                	int result = cartDtlDAO.insertCartDtl(params);
                	System.out.println("result : " + result);
                	
                	return option();
                	
                } else {
                    System.out.println("\n\n\t\t😡유효하지 않은 메뉴 세부 선택입니다😡");
                    PrintUtil.menuBar3();
                }
                break;
               

			case 2:
				PrintUtil.nonCoffee();
				List<Map<String, Object>> nonCoffeeMenuList = menuDao.getNonCoffeeMenus();
				int cnt2 = 1;
				for (Map<String, Object> menu : nonCoffeeMenuList) {
					String menuName = (String) menu.get("MENU_NAME");
					BigDecimal price = (BigDecimal) menu.get("MENU_PRICE");
					System.out.print("  \t  ");
					System.out.printf("%d.    \t", cnt2);
					System.out.printf("%15s \t", menuName);
					System.out.printf("%15d 원 \n", price.intValue());
					cnt2++;

				}
				PrintUtil.menuBar2();
                int selectedNonCoffee = ScanUtil.nextInt();
                if (selectedNonCoffee >= 1 && selectedNonCoffee <= nonCoffeeMenuList.size()) {
                	
                	Map<String, Object> map = nonCoffeeMenuList.get(selectedNonCoffee-1);
                	//CARTDTL 테이블에 insert
                	List<Object> params = new ArrayList<Object>();
                	params.add(Controller.sessionStorage.get("orderNo") + "");
                	params.add((String) map.get("MENU_NAME"));
                	int result = cartDtlDAO.insertCartDtl(params);
                	System.out.println("result : " + result);
                	
                	return option();
                	
                } else {
                    System.out.println("\n\n\t\t😡유효하지 않은 메뉴 세부 선택입니다😡");
                    PrintUtil.menuBar3();
                }
                break;

			case 3:
				PrintUtil.ade();

				List<Map<String, Object>> AdeMenuList = menuDao.getAdeMenus();
				int cnt3 = 1;
				for (Map<String, Object> menu : AdeMenuList) {
					String menuName = (String) menu.get("MENU_NAME");
					BigDecimal price = (BigDecimal) menu.get("MENU_PRICE");
					System.out.print("  \t  ");
					System.out.printf("%d.    \t", cnt3);
					System.out.printf("%15s \t", menuName);
					System.out.printf("%15d 원 \n", price.intValue());
					cnt3++;

				}
				PrintUtil.menuBar2();
                int selectedAde = ScanUtil.nextInt();
                if (selectedAde >= 1 && selectedAde <= AdeMenuList.size()) {
                	
                	Map<String, Object> map = AdeMenuList.get(selectedAde-1);
                	//CARTDTL 테이블에 insert
                	List<Object> params = new ArrayList<Object>();
                	params.add(Controller.sessionStorage.get("orderNo") + "");
                	params.add((String) map.get("MENU_NAME"));
                	int result = cartDtlDAO.insertCartDtl(params);
                	System.out.println("result : " + result);
                	
                	return option();
                	
                } else {
                    System.out.println("\n\n\t\t😡유효하지 않은 메뉴 세부 선택입니다😡");
                    PrintUtil.menuBar3();
                }
                break;

			case 4:
				PrintUtil.smoothie();

				List<Map<String, Object>> SmmothieMenuList = menuDao.getSmmothieMenus();
				int cnt4 = 1;
				for (Map<String, Object> menu : SmmothieMenuList) {
					String menuName = (String) menu.get("MENU_NAME");
					BigDecimal price = (BigDecimal) menu.get("MENU_PRICE");
					System.out.print("  \t  ");
					System.out.printf("%d.    \t", cnt4);
					System.out.printf("%15s \t", menuName);
					System.out.printf("%15d 원 \n", price.intValue());
					cnt4++;

				}
				PrintUtil.menuBar2();
                int selectedsmoothie = ScanUtil.nextInt();
                if (selectedsmoothie >= 1 && selectedsmoothie <= SmmothieMenuList.size()) {
                	
                	Map<String, Object> map = SmmothieMenuList.get(selectedsmoothie-1);
                	//CARTDTL 테이블에 insert
                	List<Object> params = new ArrayList<Object>();
                	params.add(Controller.sessionStorage.get("orderNo") + "");
                	params.add((String) map.get("MENU_NAME"));
                	int result = cartDtlDAO.insertCartDtl(params);
                	System.out.println("result : " + result);
                	
                	return option();
                	
                } else {
                    System.out.println("\n\n\t\t😡유효하지 않은 메뉴 세부 선택입니다😡");
                    PrintUtil.menuBar3();
                }
                break;

			case 5:
				PrintUtil.tea();

				List<Map<String, Object>> TeaMenuList = menuDao.getTeaMenus();
				int cnt5 = 1;
				for (Map<String, Object> menu : TeaMenuList) {
					String menuName = (String) menu.get("MENU_NAME");
					BigDecimal price = (BigDecimal) menu.get("MENU_PRICE");
					System.out.print("  \t  ");
					System.out.printf("%d.    \t", cnt5);
					System.out.printf("%15s \t", menuName);
					System.out.printf("%15d 원 \n", price.intValue());
					cnt5++;

				}
				PrintUtil.menuBar2();
                int selectedTea = ScanUtil.nextInt();
                if (selectedTea >= 1 && selectedTea <= TeaMenuList.size()) {
                	
                	Map<String, Object> map = TeaMenuList.get(selectedTea-1);
                	//CARTDTL 테이블에 insert
                	List<Object> params = new ArrayList<Object>();
                	params.add(Controller.sessionStorage.get("orderNo") + "");
                	params.add((String) map.get("MENU_NAME"));
                	int result = cartDtlDAO.insertCartDtl(params);
                	System.out.println("result : " + result);
                	
                	return option();
                	
                } else {
                    System.out.println("\n\n\t\t😡유효하지 않은 메뉴 세부 선택입니다😡");
                    PrintUtil.menuBar3();
                }
                break;

			case 6:
				PrintUtil.dessert();

				List<Map<String, Object>> DessertMenuList = menuDao.getDessertMenus();
				int cnt6 = 1;
				for (Map<String, Object> menu : DessertMenuList) {
					String menuName = (String) menu.get("MENU_NAME");
					BigDecimal price = (BigDecimal) menu.get("MENU_PRICE");
					System.out.print("  \t  ");
					System.out.printf("%d.    \t", cnt6);
					System.out.printf("%15s \t", menuName);
					System.out.printf("%15d 원 \n", price.intValue());
					cnt6++;

				}
				PrintUtil.menuBar2();
                int selectedDessert = ScanUtil.nextInt();
                if (selectedDessert >= 1 && selectedDessert <= DessertMenuList.size()) {
                	
                	Map<String, Object> map = DessertMenuList.get(selectedDessert-1);
                	//CARTDTL 테이블에 insert
                	List<Object> params = new ArrayList<Object>();
                	params.add(Controller.sessionStorage.get("orderNo") + "");
                	params.add((String) map.get("MENU_NAME"));
                	int result = cartDtlDAO.insertCartDtl(params);
                	System.out.println("result : " + result);
                	
                	return selectMenu();
                	
                } else {
                    System.out.println("\n\n\t\t😡유효하지 않은 메뉴 세부 선택입니다😡");
                    PrintUtil.menuBar3();
                }
                break;

			default:
				System.out.println("\n\n \t\t😡유효하지 않은 메뉴 선택입니다😡");
				PrintUtil.menuBar3();
				break;
			}
		}

	}
}