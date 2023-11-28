package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import Util.JDBCUtil;
import VO.OrdersVO;

public class MenuDAO {
	private static MenuDAO instance;
    public MenuDAO() {}
    private static JDBCUtil jdbc = JDBCUtil.getInstance();

    public static MenuDAO getInstance() {
        if (instance == null) {
            instance = new MenuDAO();
        }
        return instance;
    }

    public List<Map<String, Object>> getCoffeeMenus() {
		String sql = "SELECT * FROM menu WHERE MENU_NO <199 AND MENU_NO > 100";
		return jdbc.selectList(sql);
	}

	public List<Map<String, Object>> getNonCoffeeMenus() {
		String sql = "SELECT * FROM menu WHERE MENU_NO <299 AND MENU_NO > 200";
		return jdbc.selectList(sql);
	}

	public List<Map<String, Object>> getAdeMenus() {
		String sql = "SELECT * FROM menu WHERE MENU_NO <399 AND MENU_NO > 300";
		return jdbc.selectList(sql);
	}

	public List<Map<String, Object>> getSmmothieMenus() {
		String sql = "SELECT * FROM menu WHERE MENU_NO <499 AND MENU_NO > 400";
		return jdbc.selectList(sql);
	}

	public List<Map<String, Object>> getTeaMenus() {
		String sql = "SELECT * FROM menu WHERE MENU_NO <599 AND MENU_NO > 500";
		return jdbc.selectList(sql);
	}

	public List<Map<String, Object>> getDessertMenus() {
		String sql = "SELECT * FROM menu WHERE MENU_NO <699 AND MENU_NO > 600";
		return jdbc.selectList(sql);
	}

	public List<Map<String, Object>> getOptionMenus() {
		String sql = "SELECT * FROM menu WHERE MENU_NO <999 AND MENU_NO > 900";
		return jdbc.selectList(sql);
	}

	public static int addMenu(int menuNo, String food, int price) {
	    String sql = "INSERT INTO MENU(MENU_NO, MENU_NAME, MENU_PRICE) VALUES (?, ?, ?)";
	    try (Connection conn = jdbc.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setInt(1, menuNo);
	        pstmt.setString(2, food);
	        pstmt.setInt(3, price);
	        int result = pstmt.executeUpdate();
	        return result;
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return 0;
	}

	public static int updateMenu(int menuNo, String newFood, int newPrice) {
	    String sql = "UPDATE MENU SET MENU_NAME=?, MENU_PRICE=? WHERE MENU_NO=?";
	    try (Connection conn = jdbc.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setString(1, newFood);
	        pstmt.setInt(2, newPrice);
	        pstmt.setInt(3, menuNo);
	        int result = pstmt.executeUpdate();
	        return result;
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return 0;
	}

	public static int deleteMenu(int menuNo) {
	    String sql = "DELETE FROM MENU WHERE MENU_NO=?";
	    try (Connection conn = jdbc.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setInt(1, menuNo);
	        int result = pstmt.executeUpdate();
	        return result;
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return 0;
	}
	
	//주문번호 생성
	 public static int generateOrderNumber() {
	        String sql = "SELECT ORDERS_NO.NEXTVAL FROM DUAL"; // DUAL 테이블 사용
	        int orderNumber = 0;

	        try (Connection conn = jdbc.getConnection();
	             PreparedStatement pstmt = conn.prepareStatement(sql);
	             ResultSet rs = pstmt.executeQuery()) {

	            if (rs.next()) {
	                orderNumber = rs.getInt(1);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return orderNumber;
	 }

	 public void insertOrder(OrdersVO order) {
		    String sql = "INSERT INTO ORDERS (ORDERS_NO) VALUES (?)";
		    List<Object> param = new ArrayList<>();
		    param.add(order.getOrderNo());

		    jdbc.update(sql, param);
		}

	    // ... existing code ...
	}