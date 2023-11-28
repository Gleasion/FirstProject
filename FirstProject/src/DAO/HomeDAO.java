package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Util.JDBCUtil;

public class HomeDAO {
	private Connection connection;
	private JDBCUtil jdbc;

	public HomeDAO(Connection connection) {
		this.connection = connection;
		this.jdbc = new JDBCUtil(); // JdbcUtil 객체를 생성하여 초기화
	}

	// 월별 매출액
	public List<Map<String, Object>> getMonthlySales() {
		String query = "SELECT TO_CHAR(ORDERS_DATE, 'MM') AS MONTH, SUM(ORDERS_PRICE) AS SALES " + "FROM ORDERS "
				+ "WHERE UPPER(ORDERS_BUY) = 'Y' " + "GROUP BY TO_CHAR(ORDERS_DATE, 'MM')";

		List<Map<String, Object>> salesList = new ArrayList<>();
		try (PreparedStatement statement = connection.prepareStatement(query);
				ResultSet resultSet = statement.executeQuery()) {
			while (resultSet.next()) {
				String salesMonth = resultSet.getString("MONTH");
				int sales = resultSet.getInt("SALES");

				Map<String, Object> sale = new HashMap<>();
				sale.put("MONTH", salesMonth);
				sale.put("SALES", sales);
				salesList.add(sale);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return salesList;
	}

	// 일별 매출 목록
	public List<String> getDailySales(String date) {
		List<String> dailySalesList = new ArrayList<>();
		String query = "SELECT orders_date, SUM(orders_price) AS daily_sales " + "FROM orders "
				+ "WHERE to_char(orders_date) = ? AND UPPER(orders_buy) = 'Y'" + "GROUP BY orders_date";

		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, date);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				String salesDate = resultSet.getString("orders_date");
				int dailySales = resultSet.getInt("daily_sales");
				String dailySalesRecord = "날짜: " + salesDate + ", 매출액: " + dailySales;
				dailySalesList.add(dailySalesRecord);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return dailySalesList;
	}



	public int delete(String custTelNo) { // 탈퇴
		String sql = "DELETE FROM CUSTOMER WHERE CUST_TEL_NO = ?";
		List<Object> params = new ArrayList<>();
		params.add(custTelNo);
		return jdbc.update(sql, params);
	}
}