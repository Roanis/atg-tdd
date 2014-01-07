package com.roanis.commerce.promotion;

import atg.commerce.order.OrderManager;
import atg.commerce.promotion.GWPManager;

public class RoanisGWPManager extends GWPManager {
	
	private OrderManager mOrderManager;

	public OrderManager getOrderManager() {
		return mOrderManager;
	}

	public void setOrderManager(OrderManager pOrderManager) {
		mOrderManager = pOrderManager;
	}
	
	@Override
	public void performAction() {
		getOrderManager().addOrderMergeListener(this);
	}

}
