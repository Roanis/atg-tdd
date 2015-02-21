package com.roanis.tdd.core.commerce.order;

import java.util.Map;

import javax.transaction.TransactionManager;

import atg.commerce.CommerceException;
import atg.commerce.order.CommerceItem;
import atg.commerce.order.CommerceItemManager;
import atg.commerce.order.HandlingInstructionManager;
import atg.commerce.order.Order;
import atg.commerce.order.OrderHolder;
import atg.commerce.order.OrderManager;
import atg.commerce.order.PaymentGroupManager;
import atg.commerce.order.ShippingGroup;
import atg.commerce.order.ShippingGroupManager;
import atg.commerce.order.purchase.PurchaseProcessHelper;
import atg.commerce.pricing.PricingConstants;
import atg.commerce.pricing.PricingModelHolder;
import atg.commerce.pricing.PricingTools;
import atg.commerce.profile.CommerceProfileTools;
import atg.commerce.profile.CommercePropertyManager;
import atg.repository.RepositoryItem;
import atg.service.pipeline.PipelineManager;
import atg.servlet.ServletUtil;
import atg.userprofiling.Profile;

import com.google.common.collect.Maps;
import com.roanis.tdd.core.TestHelper;

public class OrderTestHelper implements TestHelper{
	
	public void setAsCurrent(String pOrderId) throws Exception {
		Order order = getOrderManager().loadOrder(pOrderId);
		getShoppingCart().setCurrent(order);
	}
	
	public void resetCart(){
		getShoppingCart().setCurrent(null);
	}
	
	public void reloadCart() throws Exception{
		resetCart();
		setAsCurrent(OrderTestConstants.BASE_INCOMPLETE_ORDER_ID);
	}
	
	public CommerceItem addItemToOrder(Order order, String skuId, String productId, Long quantity) throws CommerceException{
		CommerceItem item = mOrderManager.getCommerceItemManager().
				createCommerceItem(skuId, productId, quantity);
		mOrderManager.getCommerceItemManager().addItemToOrder(order, item);
		ShippingGroup sg = (ShippingGroup) order.getShippingGroups().get(0);
		mOrderManager.getCommerceItemManager().addItemQuantityToShippingGroup(order, item.getId(), sg.getId(), item.getQuantity());
		return item;
	}
	
	public void removeItemFromOrder(Order order, CommerceItem item) throws CommerceException{
		mOrderManager.getCommerceItemManager().removeItemFromOrder(order, item.getId());
	}
	
	public Map<String, Object> createDefaultRepriceMap(){
		Profile profile = (Profile) ServletUtil.getCurrentUserProfile();
		RepositoryItem priceList = (RepositoryItem) profile.getPropertyValue("myPriceList");	
		
		Map<String, Object> repriceMap = Maps.newHashMap();
		repriceMap.put("OrderManager", mOrderManager);
		repriceMap.put("Order", getShoppingCart().getCurrent());
		repriceMap.put("Profile", profile);
		repriceMap.put("PricingModels", getUserPricingModels());
		repriceMap.put("PricingOp", PricingConstants.OP_REPRICE_ORDER_TOTAL);

		Map<String, Object> extraParams = Maps.newHashMap();
		extraParams.put("priceList", priceList);
		
		repriceMap.put("ExtraParameters", extraParams);
		return repriceMap;
	}
	
	public OrderHolder getShoppingCart(){
		return (OrderHolder) ServletUtil.getCurrentRequest().resolveName("/atg/commerce/ShoppingCart");
	}
	
	public PricingModelHolder getUserPricingModels(){
		return (PricingModelHolder) ServletUtil.getCurrentRequest().resolveName("/atg/commerce/pricing/UserPricingModels");
	}
	
	private CommerceItemManager mCommerceItemManager;

	public CommerceItemManager getCommerceItemManager() {
		return mCommerceItemManager;
	}

	public void setCommerceItemManager(CommerceItemManager pCommerceItemManager) {
		mCommerceItemManager = pCommerceItemManager;
	}
	
	private HandlingInstructionManager mHandlingInstructionManager;

	public HandlingInstructionManager getHandlingInstructionManager() {
		return mHandlingInstructionManager;
	}

	public void setHandlingInstructionManager(HandlingInstructionManager pHandlingInstructionManager) {
		mHandlingInstructionManager = pHandlingInstructionManager;
	}
	
	private OrderManager mOrderManager;

	public OrderManager getOrderManager() {
		return mOrderManager;
	}

	public void setOrderManager(OrderManager pOrderManager) {
		mOrderManager = pOrderManager;
	}
	
	private PaymentGroupManager mPaymentGroupManager;

	public PaymentGroupManager getPaymentGroupManager() {
		return mPaymentGroupManager;
	}

	public void setPaymentGroupManager(PaymentGroupManager pPaymentGroupManager) {
		mPaymentGroupManager = pPaymentGroupManager;
	}
	
	private PipelineManager mPipelineManager;

	public PipelineManager getPipelineManager() {
		return mPipelineManager;
	}

	public void setPipelineManager(PipelineManager pPipelineManager) {
		mPipelineManager = pPipelineManager;
	}
	
	private CommerceProfileTools mCommerceProfileTools;

	public CommerceProfileTools getCommerceProfileTools() {
		return mCommerceProfileTools;
	}

	public void setCommerceProfileTools(CommerceProfileTools pCommerceProfileTools) {
		mCommerceProfileTools = pCommerceProfileTools;
	}
	
	private ShippingGroupManager mShippingGroupManager;

	public ShippingGroupManager getShippingGroupManager() {
		return mShippingGroupManager;
	}

	public void setShippingGroupManager(ShippingGroupManager pShippingGroupManager) {
		mShippingGroupManager = pShippingGroupManager;
	}
	
	private TransactionManager mTransactionManager;

	public TransactionManager getTransactionManager() {
		return mTransactionManager;
	}

	public void setTransactionManager(TransactionManager pTransactionManager) {
		mTransactionManager = pTransactionManager;
	}
	
	private PurchaseProcessHelper mPurchaseProcessHelper;

	public PurchaseProcessHelper getPurchaseProcessHelper() {
		return mPurchaseProcessHelper;
	}

	public void setPurchaseProcessHelper(PurchaseProcessHelper pPurchaseProcessHelper) {
		mPurchaseProcessHelper = pPurchaseProcessHelper;
	}
	
	private CommercePropertyManager mCommercePropertyManager;

	public CommercePropertyManager getCommercePropertyManager() {
		return mCommercePropertyManager;
	}

	public void setCommercePropertyManager(CommercePropertyManager pCommercePropertyManager) {
		mCommercePropertyManager = pCommercePropertyManager;
	}
	
	private PricingTools mPricingTools;

	public PricingTools getPricingTools() {
		return mPricingTools;
	}

	public void setPricingTools(PricingTools pPricingTools) {
		mPricingTools = pPricingTools;
	}		

	@Override
	public String getName() {
		return getClass().getCanonicalName();
	}	
}
