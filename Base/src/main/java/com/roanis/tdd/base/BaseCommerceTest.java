package com.roanis.tdd.base;

import com.roanis.tdd.base.commerce.CommerceTestConfiguration;
import com.roanis.tdd.base.commerce.catalog.CatalogTestConstants;
import com.roanis.tdd.base.site.SiteTestConstants;

import atg.commerce.dp.CatalogContext;
import atg.commerce.order.Order;
import atg.multisite.Site;
import atg.multisite.SiteContextImpl;
import atg.multisite.SiteContextManager;
import atg.nucleus.Nucleus;
import atg.repository.MutableRepositoryItem;
import atg.repository.RepositoryException;
import atg.repository.RepositoryItem;

public class BaseCommerceTest extends BaseProfileTest {		
		
	private String mOrderId;		
	private Order mBaseOrder;
	
	private RepositoryItem mBaseCatalog;
	private String mCatalogId = CatalogTestConstants.BASE_CATALOG_ID;
		
	private CommerceTestConfiguration mCommerceTestConfiguration;
	
	@Override
	public void setUp() throws Exception {
		super.setUp();				
		setupCatalog();
		setupOrder();
	}
	
	@Override
	protected void setupTestConfiguration() {		
		super.setupTestConfiguration();
		
		mCommerceTestConfiguration = (CommerceTestConfiguration) Nucleus.getGlobalNucleus().resolveName("/roanis/tdd/base/commerce/CommerceTestConfiguration");
	}
	
	protected void setupCatalog() throws RepositoryException {
		mBaseCatalog = getCommerceTestConfiguration().getCatalogTools().getCatalog().getItem(getCatalogId(), "catalog"); 
		CatalogContext.setCurrentCatalog(mBaseCatalog); 		
	}
	
	protected void setupOrder() {
		// TODO Auto-generated method stub
		
	}	

	@Override
	public void tearDown() throws Exception {
		mOrderId = null;
		mBaseOrder = null;
		mCatalogId = null;
		mBaseCatalog = null;
		mCommerceTestConfiguration = null;
		super.tearDown();
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
	
	public String getCatalogId() {
		return mCatalogId;
	}

	public void setCatalogId(String pCatalogId) {
		mCatalogId = pCatalogId;
	}

	public RepositoryItem getBaseCatalog() {
		return mBaseCatalog;
	}

	public void setBaseCatalog(RepositoryItem pBaseCatalog) {
		mBaseCatalog = pBaseCatalog;
	}
	
}
