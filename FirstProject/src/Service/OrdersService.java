package Service;

import java.util.Map;


import DAO.MenuDAO;
import DAO.OrdersDAO;



public class OrdersService {
	private MenuDAO menuDao;
    private OrdersDAO orderDao;
   
    public OrdersService() {
        menuDao = MenuDAO.getInstance();
        orderDao = OrdersDAO.getInstance();
    }

    public void placeOrder(int menuNumber) {
        // 주문 정보를 생성하고, orderNumber와 menuNumber를 사용하여 주문 테이블에 데이터를 삽입하는 코드 작성
        // ...
    }

    public void insertOrder(Map<String, Object> param) {
        String ordersNo = (String) param.get("ORDERS_NO");
        OrdersDAO.getInstance().insertOrder(param);;
    }

}