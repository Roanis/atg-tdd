package com.roanis.tdd.base.commerce;

import javax.transaction.TransactionManager;

import atg.commerce.catalog.CatalogTools;
import atg.commerce.claimable.ClaimableManager;
import atg.commerce.gifts.GiftlistManager;
import atg.commerce.order.CommerceItemManager;
import atg.commerce.order.HandlingInstructionManager;
import atg.commerce.order.OrderManager;
import atg.commerce.order.PaymentGroupManager;
import atg.commerce.order.ShippingGroupManager;
import atg.commerce.order.purchase.PurchaseProcessHelper;
import atg.commerce.pricing.PricingTools;
import atg.commerce.pricing.priceLists.PriceListManager;
import atg.commerce.profile.CommerceProfileTools;
import atg.commerce.profile.CommercePropertyManager;
import atg.service.pipeline.PipelineManager;

public class CommerceTestConfiguration {
	
	private ClaimableManager mClaimableManager;

	public ClaimableManager getClaimableManager() {
		return mClaimableManager;
	}

	public void setClaimableManager(ClaimableManager pClaimableManager) {
		mClaimableManager = pClaimableManager;
	}
	
	private CommerceItemManager mCommerceItemManager;

	public CommerceItemManager getCommerceItemManager() {
		return mCommerceItemManager;
	}

	public void setCommerceItemManager(CommerceItemManager pCommerceItemManager) {
		mCommerceItemManager = pCommerceItemManager;
	}
	
	private GiftlistManager mGiftlistManager;

	public GiftlistManager getGiftlistManager() {
		return mGiftlistManager;
	}

	public void setGiftlistManager(GiftlistManager pGiftlistManager) {
		mGiftlistManager = pGiftlistManager;
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

}
