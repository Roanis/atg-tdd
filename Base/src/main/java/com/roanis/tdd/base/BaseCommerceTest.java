package com.roanis.tdd.base;

import com.roanis.tdd.base.commerce.CommerceTestConfiguration;
import com.roanis.tdd.base.site.SiteTestConstants;

import atg.commerce.order.Order;
import atg.multisite.Site;
import atg.multisite.SiteContextImpl;
import atg.multisite.SiteContextManager;
import atg.nucleus.Nucleus;
import atg.repository.RepositoryException;
import atg.repository.RepositoryItem;

public class BaseCommerceTest extends BaseProfileTest {
		
	private String mOrderId;		
	private Order mBaseOrder;
	
	private CommerceTestConfiguration mCommerceTestConfiguration;
	
	@Override
	public void setUp() throws Exception {
		super.setUp();				
		setupSite();
	}
	
	@Override
	public void tearDown() throws Exception {
		mCommerceTestConfiguration = null;
		super.tearDown();
	}
		
	@Override
	protected void setupTestConfiguration() {		
		super.setupTestConfiguration();
		
		mCommerceTestConfiguration = (CommerceTestConfiguration) Nucleus.getGlobalNucleus().resolveName("/roanis/tdd/base/commerce/CommerceTestConfiguration");
	}

	
	
	public Order getBaseOrder() {
		return mBaseOrder;
	}

	public void setBaseOrder(Order pBaseOrder) {
		mBaseOrder = pBaseOrder;
	}
		
		

	public CommerceTestConfiguration getCommerceTestConfiguration() {
		return mCommerceTestConfiguration;
	}

	public void setCommerceTestConfiguration(CommerceTestConfiguration pCommerceTestConfiguration) {
		mCommerceTestConfiguration = pCommerceTestConfiguration;
	}
	
	
	
	public String getOrderId() {
		return mOrderId;
	}

	public void setOrderId(String pOrderId) {
		mOrderId = pOrderId;
	}
	
}
