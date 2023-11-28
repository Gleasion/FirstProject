package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import Controller.Controller;
import Util.JDBCUtil;

public class OrdersDAO {
	
	private static OrdersDAO instance = null;
	private OrdersDAO() {}
	
	private static JDBCUtil jdbc = JDBCUtil.getInstance();
	CustomerDAO customerDAO = CustomerDAO.getInstance();
	
	public static OrdersDAO getInstance() {
		if(instance == null) instance = new OrdersDAO();
		return instance;
	}

	public int insertOrder(Map<String, Object> param) {
	    String sql = "INSERT INTO ORDERS (ORDERS_NO, ORDERS_PRICE, ORDERS_BUY, CUST_NO, ORDERS_DATE) VALUES (?, 0, 'N', ?, SYSDATE)";
	    List<Object> obj = new ArrayList<>();
	    
	    Object loginInfoO = Controller.sessionStorage.get("loginInfo");
		Map<String, Object> loginInfo = (Map<String, Object>) loginInfoO;
		
		String ordersNo = Controller.sessionStorage.get("orderNo") + "";
	    String custNo = (String) loginInfo.get("CUST_NO");
	    
	    obj.add(ordersNo);
	    obj.add(custNo);

	    jdbc.update(sql, obj);
	    return 0;
	}
        
	public int updateOrdersBuy(String ordersNo) {
		String sql = "UPDATE ORDERS SET ORDERS_BUY = 'Y' WHERE ORDERS_NO = ? ";
		try (Connection conn = jdbc.getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setString(1, ordersNo);
			int result = pstmt.executeUpdate();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public int updateOrdersPrice(String ordersNo) {
		Object loginInfoO = Controller.sessionStorage.get("loginInfo");
		Map<String, Object> loginInfo = (Map<String, Object>) loginInfoO;
		
		ordersNo = Controller.sessionStorage.get("orderNo") + "";
		
		String sql = "UPDATE ORDERS SET ORDERS_PRICE = ORDERS_PRICE + "
                + String.valueOf(PayDAO.getInstance().getPrice(ordersNo))
                + " WHERE ORDERS_NO = '" + ordersNo+"'";
		
		jdbc.update(sql);
		
		return 0;
	}
}
