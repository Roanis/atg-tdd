package com.roanis.tdd.junit4.rules;

import org.junit.rules.TestRule;

/**
 * An {@link org.junit.rules.ExternalResource} {@link TestRule}, which loads the following components
 * before a test is run and then cleans up after the test:
 * 
 * <ul>
 * <li>site</li>
 * <li>profile</li>
 * <li>catalog</li>
 * <li>price list</li>
 * <li>sale price list</li>
 * <li>order</li>
 * </ul>
 * 
 * @author rory
 *
 */
public class CommerceData extends ExternalNucleusData {
	private String mSiteId;
	private String mProfileId;
	private String mCatalogId;
	private String mOrderId;
		
	
	@Override
	protected void before() throws Throwable {
		super.before();
		
		getTestConfiguration().getSiteTestHelper().setAsCurrent(mSiteId);
		getTestConfiguration().getProfileTestHelper().setAsCurrent(mProfileId);
		getTestConfiguration().getCatalogTestHelper().setAsCurrent(mCatalogId);		
		getTestConfiguration().getOrderTestHelper().setAsCurrent(mOrderId);				
	}

	@Override
	protected void after() {
		getTestConfiguration().getSiteTestHelper().reset();
		getTestConfiguration().getProfileTestHelper().reset();
		getTestConfiguration().getCatalogTestHelper().reset();		
		getTestConfiguration().getOrderTestHelper().resetCart();
		
		super.after();
	}
	
	public static class Builder {
		private String mSiteId;
		String mProfileId;
		String mCatalogId;
		String mPriceListId;
		String mSalePriceListId;
		String mOrderId;
		
		public Builder site(String siteId){
			mSiteId = siteId;
			return this;
		}
		
		public Builder profile(String profileId){
			mProfileId = profileId;
			return this;
		}
		
		public Builder catalog(String catalogId){
			mCatalogId = catalogId;
			return this;
		}
		
		public Builder priceList(String priceListId){
			mPriceListId = priceListId;
			return this;
		}
		
		public Builder salePriceList(String salePriceListId){
			mSalePriceListId = salePriceListId;
			return this;
		}
		
		public Builder order(String orderId){
			mOrderId = orderId;
			return this;
		}
		
		public CommerceData build(){
			return new CommerceData(this);
		}
		
    }
	
	private CommerceData(Builder builder){
		mSiteId = builder.mSiteId;
		mProfileId = builder.mProfileId;
		mCatalogId = builder.mCatalogId;
		mOrderId = builder.mOrderId;
	}
	
	

}
