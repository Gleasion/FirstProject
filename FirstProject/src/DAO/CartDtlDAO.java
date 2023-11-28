package DAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import Util.JDBCUtil;

import VO.CartDtlVO;

public class CartDtlDAO {
	private static CartDtlDAO instance;
    private CartDtlDAO() {}
    private static JDBCUtil jdbc = JDBCUtil.getInstance();

    public static CartDtlDAO getInstance() {
        if (instance == null) {
            instance = new CartDtlDAO();
        }
        return instance;
    }
       
    // 장바구니 조회
    public List<Map<String, Object>> getCartDtlList(String ordersNo) {
        String sql = " SELECT B.MENU_NO, B.MENU_NAME, B.MENU_PRICE, A.MENU_CNT, B.MENU_PRICE * A.MENU_CNT AS TOTAL_PRICE " +
                     " FROM CARTDTL A, MENU B " +
                     " WHERE A.MENU_NO = B.MENU_NO " +
                     " AND A.ORDERS_NO = ? " +
                     " ORDER BY REG_DATE ";
        List<Object> params = new ArrayList<>();
        params.add(ordersNo);
        return jdbc.selectList(sql, params);
    }
    
    // 장바구니 수량 변경
    public static int updateCartDtlMenuCount(String ordersNo, String menuNo, String menuCnt) {
    	
    	String sql =  "UPDATE CARTDTL SET MENU_CNT = ? WHERE ORDERS_NO = ? AND MENU_NO = ?";
    	List<Object> params = new ArrayList<>();
    	params.add(menuCnt);
    	params.add(ordersNo);
    	params.add(menuNo);
		return jdbc.update(sql, params);
	}
    
    // 장바구니 메뉴 삭제
    public int deleteCartDtlMenu(String ordersNo, String menuNo) {
    	String sql = " DELETE FROM CART_DTL"
    			   + " WHERE ORDERS_NO = ? "
    			   + " AND Menu_No = ? ";
    	List<Object> params = new ArrayList<>();
    	params.add(ordersNo);
    	params.add(menuNo);
		return jdbc.update(sql, params);
	}
  
    //CARTDTL 테이블에 insert
    //params : ordersNo, menuNo : '남민주뮤직큐'
    public int insertCartDtl(List<Object> params) {
    	String sql = "INSERT INTO CARTDTL(ORDERS_NO, MENU_NO, MENU_CNT, REG_DATE) " + 
    			" VALUES(?,(SELECT MENU_NO FROM MENU WHERE MENU_NAME = ?),1,SYSDATE)";
    	
    	return jdbc.update(sql, params);
    }
}