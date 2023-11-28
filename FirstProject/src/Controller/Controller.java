package Controller;

import java.util.HashMap;
import java.util.Map;

import Service.CartDtlService;
import Service.CustomerService;
import Service.HomeService;
import Service.MenuService;
import Service.PayService;
import Util.PrintUtil;
import Util.ScanUtil;
import Util.View;

public class Controller {
	public static Map<String, Object> sessionStorage = new HashMap<String, Object>();

    private HomeService homeService;
    private MenuService menuService;
    private CustomerService customerService;
    private CartDtlService cartDtlService;
    
    private PayService payService;

    public Controller() {
        homeService = HomeService.getInstance();
        menuService = MenuService.getInstance();
        customerService = CustomerService.getInstance();
        cartDtlService = CartDtlService.getInstance();
        payService = PayService.getInstance();
    }

    public static void main(String[] args) {
        Controller controller = new Controller();
        controller.start();
    }

    public void start() {
        int view = View.HOME;
        while (true) {
            switch (view) {
                case View.HOME:
                    view = homeService.home();
                    break;
                case View.MAIN:
                    view = homeService.main();
                    break;
                case View.ADMIN_LOGIN:
                    view = homeService.adminLogin();
                    break;
                case View.ADMIN_HOME:
                    view = homeService.adminHome();
                    break;
                case View.MENU_ADD:
                	view = homeService.menuAdd();
                	break;
                case View.MENU_UPDATE:
                	view = homeService.menuUpdate();
                	break;
                case View.MENU_DELETE:
                	view = homeService.menuDelete();
                	break;
                case View.TOTAL_SALES:
                	view = homeService.totalSales();
                	break;
                case View.DAILY_SALES:
                	view = homeService.dailySales();
                	break;
                case View.SIGNIN:
                    view = customerService.signIn();
                    break;
                case View.SIGNUP:
                    view = customerService.signUp();
                    break;
                case View.DELETE:
                    view = homeService.delete();
                    break;
                case View.MENU:
                    view = menuService.printMenu();
                    break;
                case View.PAY:
                    view = payService.paymenthome();
                    break;
                case View.RECEIPT:
                	view = payService.receipt();
                	break;
                case View.CART:
                    view = cartDtlService.selectCart();
                    break;
                case View.CARTCOUNTCHANGE:
                	view = cartDtlService.updateMenuCountInCart();
                	break;
                case View.END:
                	view = end();
                	break;
                default:
                    System.out.println("잘못된 접근입니다.");
                    view = View.HOME;
                    break;
            }
        }
    } 
    public static int end() {
    	PrintUtil.endPrint();
    	System.out.println("확인 >> Enter ");
		ScanUtil.nextLine();
		return View.HOME;
    	
    }
}
