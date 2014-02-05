package com.roanis.tdd.base.commerce.order;

import atg.commerce.CommerceException;
import atg.commerce.order.Order;
import atg.commerce.order.OrderHolder;
import atg.commerce.order.OrderManager;
import atg.commerce.pricing.PricingModelHolder;
import atg.nucleus.Nucleus;
import atg.servlet.ServletUtil;

import com.roanis.tdd.base.BaseCommerceTest;

public class BaseOrderTest extends BaseCommerceTest {
	
	private OrderTestHelper mOrderTestHelper;
	private Order mIncompleteOrder;
	private OrderHolder mShoppingCart;
		
	@Override
	public void setUp() throws Exception {		
		super.setUp();
		setupSites();
		setupProfiles();
		setupCatalogs();
		setupProfilePriceLists();
		setupOrders();
		setupShoppingCart();		
	}
	
	private void setupShoppingCart() {
		mShoppingCart = getOrderTestHelper().getShoppingCart();
		mShoppingCart.setCurrent(mIncompleteOrder);
		mShoppingCart.setProfile(ServletUtil.getCurrentUserProfile());
	}
	
	protected void setupOrders() throws CommerceException {	
		mIncompleteOrder = getOrderManager().loadOrder("incompleteOrder");
	}

	@Override
	protected void setupTestHelpers() {		
		super.setupTestHelpers();
		mOrderTestHelper = (OrderTestHelper) Nucleus.getGlobalNucleus().resolveName("/roanis/tdd/base/commerce/order/OrderTestHelper");
	}
	
	@Override
	protected void tearDownTestComponents() {
		super.tearDownTestComponents();
		mOrderTestHelper = null;
	}		
	
	public OrderTestHelper getOrderTestHelper() {
		return mOrderTestHelper;
	}

	public void setOrderTestHelper(OrderTestHelper pOrderTestHelper) {
		mOrderTestHelper = pOrderTestHelper;
	}
	
	public OrderHolder getShoppingCart() {
		return mShoppingCart;
	}
	
	public Order getIncompleteOrder() {
		return mIncompleteOrder;
	}

	public void setIncompleteOrder(Order pIncompleteOrder) {
		mIncompleteOrder = pIncompleteOrder;
	}		

	public OrderManager getOrderManager() {
		return getOrderTestHelper().getOrderManager();
	}	
	
	public PricingModelHolder getUserPricingModels(){
		return getOrderTestHelper().getUserPricingModels();
	}		

}
