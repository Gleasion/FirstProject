package DAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import Util.JDBCUtil;

public class CustomerDAO {

	private static CustomerDAO instance;

	private CustomerDAO() {
	}

	private JDBCUtil jdbc = JDBCUtil.getInstance();

	public static CustomerDAO getInstance() {
		if (instance == null) {
			instance = new CustomerDAO();
		}
		return instance;
	}

	// param : {CUST_TEL_NO=1, CUST_NAME=ㅁ}
	public int signUp(Map<String, Object> param) { // 회원가입
		JDBCUtil jdbc = JDBCUtil.getInstance(); // JDBCUtil 객체 생성

		String sql = "INSERT INTO CUSTOMER(CUST_NAME, CUST_TEL_NO, CUST_NO, CUST_MILE) VALUES (?, ?, ?, NULL)";
		List<Object> p = new ArrayList<>();
		String custName = (String) param.get("CUST_NAME");
		String custTelNo = (String) param.get("CUST_TEL_NO");

		if (custName == null || custName.isEmpty()) {
			return 0; // 이름과 전화번호가 필수 입력 사항이므로 둘 중 하나라도 비어있으면 회원가입 실패로 간주하고 0을 반환
		}

		p.add(custName);
		p.add(custTelNo);

		String custNoPrefix = custTelNo != null ? "A" : "Z";
		String custNoSql = "SELECT MAX(CUST_NO) AS MAX_CUST_NO FROM CUSTOMER WHERE CUST_NO LIKE ?";
		List<Object> custNoParams = new ArrayList<>();
		custNoParams.add(custNoPrefix + "%%");
		Map<String, Object> maxCustNo = jdbc.selectOne(custNoSql, custNoParams);
		int nextCustNo = maxCustNo == null ? 1
				: Integer.parseInt(maxCustNo.get("MAX_CUST_NO").toString().substring(1)) + 1;
		String custNo = custNoPrefix + String.format("%03d", nextCustNo);
		p.add(custNo);

		// System.out.println("signUp->p : " + p);
		// p : [a, 2, A019]
		int result = jdbc.update(sql, p);

		if (result > 0) { // 회원가입 성공 시 고객 번호 생성
			String insertSql = "UPDATE CUSTOMER SET CUST_MILE = ? WHERE CUST_NO = ?";
			List<Object> insertParams = new ArrayList<>();
			insertParams.add(1500); // 새로 가입한 고객에게 1500 마일리지 적립
			insertParams.add(custNo);
			// [A019, 1500]
			jdbc.update(insertSql, insertParams);
		}

		return result;
	}

	public Map<String, Object> signIn(List<Object> param) { // 로그인
		String sql = "SELECT * FROM CUSTOMER WHERE CUST_TEL_NO = ?";
		return jdbc.selectOne(sql, param);
	}

	public Map<String, Object> isTelExist(String custTelNo) {

		// System.out.println("isTelExist->custTelNo : " + custTelNo);

		String sql = "SELECT * FROM CUSTOMER WHERE CUST_TEL_NO = ?";
		List<Object> params = new ArrayList<Object>();
		params.add(custTelNo);
		Map<String, Object> map = jdbc.selectOne(sql, params);
		// System.out.println("isTelExist->map : " + map);
		return map;
	}

	public int mileage(String custNo, int custMile) { // 마일리지
		String sql = "UPDATE CUSTOMER SET CUST_MILE = ? WHERE CUST_NO = ?";
		List<Object> params = new ArrayList<>();
		params.add(custMile);
		params.add(custNo);
		return jdbc.update(sql, params);
	}
}
