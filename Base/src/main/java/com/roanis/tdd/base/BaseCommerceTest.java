package com.roanis.tdd.base;

import org.junit.After;
import org.junit.Before;

import atg.commerce.pricing.priceLists.PriceListException;
import atg.nucleus.Nucleus;
import atg.repository.RepositoryException;

import com.roanis.tdd.base.commerce.catalog.CatalogTestHelper;
import com.roanis.tdd.base.commerce.inventory.InventoryTestHelper;
import com.roanis.tdd.base.commerce.pricing.priceLists.PriceListTestHelper;
import com.roanis.tdd.base.multisite.SiteTestHelper;
import com.roanis.tdd.base.userprofiling.ProfileTestHelper;

public abstract class BaseCommerceTest extends RoanisTestCase {
	private SiteTestHelper mSiteTestHelper;	
	private ProfileTestHelper mProfileTestHelper;
	private CatalogTestHelper mCatalogTestHelper;	
	private PriceListTestHelper mPriceListTestHelper;	
	private InventoryTestHelper mInventoryTestHelper;	
                 
    @Override
    @Before
    public void setUp () throws Exception {
        super.setUp();        
        setupTestComponents();        
    }    

	protected void setupTestComponents() {	
		mSiteTestHelper = (SiteTestHelper) Nucleus.getGlobalNucleus().resolveName("/roanis/tdd/base/site/SiteTestHelper");
		mProfileTestHelper = (ProfileTestHelper) Nucleus.getGlobalNucleus().resolveName("/roanis/tdd/base/profile/ProfileTestHelper");
		mCatalogTestHelper = (CatalogTestHelper) Nucleus.getGlobalNucleus().resolveName("/roanis/tdd/base/commerce/catalog/CatalogTestHelper");
		mPriceListTestHelper = (PriceListTestHelper) Nucleus.getGlobalNucleus().resolveName("/roanis/tdd/base/commerce/pricing/priceLists/PriceListTestHelper");
		mInventoryTestHelper = (InventoryTestHelper) Nucleus.getGlobalNucleus().resolveName("/roanis/tdd/base/commerce/inventory/InventoryTestHelper");
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
        super.tearDown();
    }
    
    private void tearDownTestComponents() {
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
    
      
}
