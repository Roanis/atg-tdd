package com.roanis.tdd.core;

import javax.transaction.TransactionManager;

import org.junit.After;
import org.junit.Before;

import atg.commerce.pricing.priceLists.PriceListException;
import atg.dtm.TransactionDemarcation;
import atg.dtm.TransactionDemarcationException;
import atg.nucleus.Nucleus;
import atg.repository.RepositoryException;

import com.roanis.tdd.core.commerce.catalog.CatalogTestHelper;
import com.roanis.tdd.core.commerce.inventory.InventoryTestHelper;
import com.roanis.tdd.core.commerce.pricing.priceLists.PriceListTestHelper;
import com.roanis.tdd.core.multisite.SiteTestHelper;
import com.roanis.tdd.core.userprofiling.ProfileTestHelper;

public abstract class BaseCommerceTest extends RoanisTestCase {
	private SiteTestHelper mSiteTestHelper;	
	private ProfileTestHelper mProfileTestHelper;
	private CatalogTestHelper mCatalogTestHelper;	
	private PriceListTestHelper mPriceListTestHelper;	
	private InventoryTestHelper mInventoryTestHelper;	
	private TransactionDemarcation mTransactionDemarcation;
	private TransactionManager mTransactionManager;

	            
    @Override
    @Before
    public void setUp () throws Exception {
        super.setUp();        
        setupTestHelpers();    
        setupTransaction();
    }    	

	protected void setupTestHelpers() {	
		mSiteTestHelper = (SiteTestHelper) Nucleus.getGlobalNucleus().resolveName("/roanis/tdd/base/site/SiteTestHelper");
		mProfileTestHelper = (ProfileTestHelper) Nucleus.getGlobalNucleus().resolveName("/roanis/tdd/base/profile/ProfileTestHelper");
		mCatalogTestHelper = (CatalogTestHelper) Nucleus.getGlobalNucleus().resolveName("/roanis/tdd/base/commerce/catalog/CatalogTestHelper");
		mPriceListTestHelper = (PriceListTestHelper) Nucleus.getGlobalNucleus().resolveName("/roanis/tdd/base/commerce/pricing/priceLists/PriceListTestHelper");
		mInventoryTestHelper = (InventoryTestHelper) Nucleus.getGlobalNucleus().resolveName("/roanis/tdd/base/commerce/inventory/InventoryTestHelper");
	}
	
	protected void setupTransaction() throws TransactionDemarcationException {
		mTransactionManager = (TransactionManager) Nucleus.getGlobalNucleus().resolveName("/atg/dynamo/transaction/TransactionManager");
		mTransactionDemarcation = new TransactionDemarcation();
		mTransactionDemarcation.begin(mTransactionManager);
	}
	
	protected void setupSites() throws Exception {	
		getSiteTestHelper().defaultCurrentSite();
	}
	
	protected void setupProfiles() throws Exception {
		getProfileTestHelper().defaultCurrentProfile();
	}
	
	protected void setupCatalogs() throws RepositoryException{
		getCatalogTestHelper().defaultCurrentCatalog();
	}
	
	protected void setupProfilePriceLists() throws PriceListException{
		getPriceListTestHelper().defaultCurrentProfilePriceLists();
	}

	@Override
    @After
    public void tearDown () throws Exception {
    	tearDownTestComponents(); 
    	rollbackTransaction();
    	
    	mTransactionDemarcation = null;
        super.tearDown();
    }
    
	protected void rollbackTransaction() throws TransactionDemarcationException {
		mTransactionDemarcation.end(true);		
	}

    protected void tearDownTestComponents() {
    	mSiteTestHelper = null;		
    	mProfileTestHelper = null;
    	mCatalogTestHelper = null;
    	mPriceListTestHelper = null;
    	mInventoryTestHelper = null;
	}

	public void setupMocks () {
    }    
    
    public void tearDownMocks () {
    }    
    
    public SiteTestHelper getSiteTestHelper() {
		return mSiteTestHelper;
	}

	public void setSiteTestHelper(SiteTestHelper pSiteTestHelper) {
		mSiteTestHelper = pSiteTestHelper;
	}
	
	public ProfileTestHelper getProfileTestHelper(){
		return mProfileTestHelper;
	}
	
	public void setProfileTestHelper(ProfileTestHelper pProfileTestHelper){
		mProfileTestHelper = pProfileTestHelper;
	}
	
	public CatalogTestHelper getCatalogTestHelper() {
		return mCatalogTestHelper;
	}

	public void setCatalogTestHelper(CatalogTestHelper pCatalogTestHelper) {
		mCatalogTestHelper = pCatalogTestHelper;
	}
	
	public PriceListTestHelper getPriceListTestHelper() {
		return mPriceListTestHelper;
	}

	public void setPriceListTestHelper(PriceListTestHelper pPriceListTestHelper) {
		mPriceListTestHelper = pPriceListTestHelper;
	}
	
	public InventoryTestHelper getInventoryTestHelper() {
		return mInventoryTestHelper;
	}

	public void setInventoryTestHelper(InventoryTestHelper pInventoryTestHelper) {
		mInventoryTestHelper = pInventoryTestHelper;
	}
	
	public TransactionDemarcation getTransactionDemarcation() {
		return mTransactionDemarcation;
	}

	public void setTransactionDemarcation(TransactionDemarcation pTransactionDemarcation) {
		mTransactionDemarcation = pTransactionDemarcation;
	}
    
	public TransactionManager getTransactionManager() {
		return mTransactionManager;
	}

	public void setTransactionManager(TransactionManager pTransactionManager) {
		mTransactionManager = pTransactionManager;
	}
	
     
}
