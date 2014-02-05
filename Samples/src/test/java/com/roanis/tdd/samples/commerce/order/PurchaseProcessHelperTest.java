package com.roanis.tdd.samples.commerce.order;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import atg.commerce.order.CommerceItem;
import atg.commerce.order.Order;
import atg.commerce.order.ShippingGroup;
import atg.commerce.order.purchase.AddCommerceItemInfo;
import atg.commerce.order.purchase.PurchaseProcessHelper;
import atg.repository.RepositoryItem;
import atg.servlet.ServletUtil;

import com.roanis.tdd.base.commerce.catalog.CatalogTestConstants;
import com.roanis.tdd.base.commerce.order.BaseOrderTest;
import com.roanis.tdd.base.commerce.util.TestingPipelineErrorHandler;

@RunWith(JUnit4.class)
public class PurchaseProcessHelperTest extends BaseOrderTest {
	
	@Test
	public void addNewProduct() throws Exception{
		Order order = getShoppingCart().getCurrent();
		ShippingGroup sg = (ShippingGroup) order.getShippingGroups().get(0);
		RepositoryItem profile = ServletUtil.getCurrentUserProfile();
		AddCommerceItemInfo[] itemInfos = new AddCommerceItemInfo[1];
		itemInfos[0] = createItemInfo(CatalogTestConstants.MENS_JACKET_PRODUCT_ID, CatalogTestConstants.JACKET_MEDIUM_SKU_ID, 1L);
		Locale locale = getUserLocale(profile);
		Map<Object, Object> extraParams = createExtraParams(profile);
		
		
		@SuppressWarnings("unchecked")
		List<CommerceItem> items = getPurchaseProcessHelper().addItemsToOrder(getIncompleteOrder(), sg, profile, itemInfos, locale, null, getUserPricingModels(), new TestingPipelineErrorHandler(), extraParams);
		assertEquals(1, items.size());
	}
	
	protected Map<Object, Object> createExtraParams(RepositoryItem profile) {
		Map<Object, Object> params = new HashMap<Object, Object>();
		RepositoryItem priceList = (RepositoryItem) profile.getPropertyValue(getOrderTestHelper().getCommercePropertyManager().getPriceListPropertyName());
		params.put(getOrderTestHelper().getCommercePropertyManager().getPriceListPropertyName(), priceList);
		
		return params;
	}

	protected Locale getUserLocale(RepositoryItem profile) {
		return Locale.UK;
	}

	protected AddCommerceItemInfo[] createItemInfos(Collection<String> productIds, Collection<String> skuIds, Collection<Long> quantities) throws Exception{
		List<AddCommerceItemInfo> itemInfos = new ArrayList<AddCommerceItemInfo>(productIds.size());
		
		for (int i=0; i < productIds.size(); i++) {
			String productId = (String) CollectionUtils.get(productIds, i);
			String skuId = (String) CollectionUtils.get(skuIds, i);
			Long quantity = (Long) CollectionUtils.get(quantities, i);
			itemInfos.add(createItemInfo(productId, skuId, quantity));			
		}
		
		return (AddCommerceItemInfo[]) itemInfos.toArray(new AddCommerceItemInfo[itemInfos.size()]);
	}
	
	protected AddCommerceItemInfo createItemInfo (String productId, String skuId, Long quantity) throws Exception {
		AddCommerceItemInfo itemInfo = new AddCommerceItemInfo();
        itemInfo.setProductId(productId);
        itemInfo.setCatalogRefId(skuId);
        itemInfo.setQuantity(quantity);
        itemInfo.setCommerceItemType("default");

        return itemInfo;
    } 
	
	public PurchaseProcessHelper getPurchaseProcessHelper() {
		return getOrderTestHelper().getPurchaseProcessHelper();
	}
	
}
