package DAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import Controller.Controller;
import Util.JDBCUtil;

public class PayDAO {
	
	public int usingMileage;
	
	private static PayDAO instance = null;
	private PayDAO() {}
	private JDBCUtil jdbc = JDBCUtil.getInstance();
	
	public static PayDAO getInstance() {
		if(instance == null) instance = new PayDAO();
		return instance;
	}
	
	//총액 조회
	public int getPrice(String ordersNo) {
		Object loginInfoO = Controller.sessionStorage.get("loginInfo");
		Map<String, Object> loginInfo = (Map<String, Object>) loginInfoO;
		
		ordersNo = Controller.sessionStorage.get("orderNo") + "";
//	    String custNo = (String) loginInfo.get("CUST_NO");
	    
	    String sql = " select sum(m.menu_price * d.menu_cnt) sum " + 
				" from menu m, cartdtl d, ORDERS o" + 
				" where d.menu_no = m.menu_no and o.ORDERS_NO=d.ORDERS_NO and o.orders_no = "+ordersNo;
	    Map<String, Object> pay = jdbc.selectOne(sql);
		int total = Integer.parseInt(pay.get("SUM").toString());
		return total;
	}
	
	
	//마일리지 조회
	public int getMileage(String custTelNo){
		Object loginInfoO = Controller.sessionStorage.get("loginInfo");
		Map<String, Object> loginInfo = (Map<String, Object>) loginInfoO;
		custTelNo = (String) loginInfo.get("CUST_TEL_NO").toString();
		
		String sql = "select cust_mile from customer where cust_tel_no = "+custTelNo;
		List<Object> p = new ArrayList<>();
		p.add(custTelNo);
		Map<String, Object> user = jdbc.selectOne(sql);
		return Integer.parseInt(user.get("CUST_MILE").toString());
	}
	
	//마일리지 사용하는 결제
	public int paymentUsingMileage(String custTelNo){
		Object loginInfoO = Controller.sessionStorage.get("loginInfo");
		Map<String, Object> loginInfo = (Map<String, Object>) loginInfoO;
		
		String ordersNo = Controller.sessionStorage.get("orderNo") + "";
	    String custNo = (String) loginInfo.get("CUST_NO");
	    
		String sql = " select sum(m.menu_price * d.menu_cnt) sum " + 
				" from menu m, cartdtl d, ORDERS o" + 
				" where d.menu_no = m.menu_no and o.ORDERS_NO=d.ORDERS_NO and o.orders_no = "+ordersNo;
		
		Map<String, Object> pum = jdbc.selectOne(sql);
		int total = Integer.parseInt(pum.get("SUM").toString());
		return (total - usingMileage);
	}
	
	//일반 결제 
	public int usualPayment(){
		Object loginInfoO = Controller.sessionStorage.get("loginInfo");
		Map<String, Object> loginInfo = (Map<String, Object>) loginInfoO;
		
		String ordersNo = Controller.sessionStorage.get("orderNo") + "";
	    String custNo = (String) loginInfo.get("CUST_NO");
	    
		String sql = " select sum(m.menu_price * d.menu_cnt) sum " + 
				" from menu m, cartdtl d, ORDERS o" + 
				" where d.menu_no = m.menu_no and o.ORDERS_NO=d.ORDERS_NO and o.orders_no = "+ordersNo;
		
		Map<String, Object> pay = jdbc.selectOne(sql);
		int total = Integer.parseInt(pay.get("SUM").toString());
		return total;
	}
	
	//일반 결제시 새로 적립되는 마일리지
	public int usualSavingMileage() {
		int mile = (int)(usualPayment()*0.05);
		return mile;
	}

	//결제에 마일리지를 사용한 경우 적립되는 마일리지
	public int SavingMileageWhenUsingMileage(String custTelNo) {
		Object loginInfoO = Controller.sessionStorage.get("loginInfo");
		Map<String, Object> loginInfo = (Map<String, Object>) loginInfoO;
		custTelNo = (String) loginInfo.get("CUST_TEL_NO").toString();
		
		int mile = (int)(paymentUsingMileage(custTelNo)*0.05);
		return mile;
	}
	
	//결제 후 마일리지
	public int currentMileageUsual(String custTelNo){
		Object loginInfoO = Controller.sessionStorage.get("loginInfo");
		Map<String, Object> loginInfo = (Map<String, Object>) loginInfoO;
		custTelNo = (String) loginInfo.get("CUST_TEL_NO").toString();
		
		String sql = "update customer set cust_mile = cust_mile + " + usualSavingMileage()
					+" where cust_Tel_No = "+custTelNo;
		jdbc.update(sql);
		int curmile = getMileage(custTelNo);
		
		return curmile;
	}
	
	//마일리지를 사용한 결제 후 마일리지
	public int currentMileageUsingMileage(String custTelNo){
		Object loginInfoO = Controller.sessionStorage.get("loginInfo");
		Map<String, Object> loginInfo = (Map<String, Object>) loginInfoO;
		custTelNo = (String) loginInfo.get("CUST_TEL_NO").toString();
		
		String sql = "update customer set cust_mile = cust_mile - " + usingMileage + "+" + SavingMileageWhenUsingMileage(custTelNo)
		+" where cust_Tel_No = "+custTelNo;
		
		jdbc.update(sql);
		int curmile = getMileage(custTelNo);
		
		return curmile;
	}
	
	
}
