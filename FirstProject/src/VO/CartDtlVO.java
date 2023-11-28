package VO;

public class CartDtlVO {
	private String ordersNo;
	private String menuNo;
	private String menuPrice;
	private String menuCnt;
	
	public CartDtlVO() {}

	public String getOrdersNo() {
		return ordersNo;
	}

	public void setOrdersNo(String ordersNo) {
		this.ordersNo = ordersNo;
	}

	public String getMenuNo() {
		return menuNo;
	}

	public void setMenuNo(String menuNo) {
		this.menuNo = menuNo;
	}

	public String getMenuPrice() {
		return menuPrice;
	}

	public void setMenuPrice(String menuPrice) {
		this.menuPrice = menuPrice;
	}

	public String getMenuCnt() {
		return menuCnt;
	}

	public void setMenuCnt(String menuCnt) {
		this.menuCnt = menuCnt;
	}

	
}