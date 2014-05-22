package com.roanis.tdd.samples.commerce.pricing;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import atg.commerce.order.CommerceItem;
import atg.commerce.order.Order;
import atg.commerce.order.OrderHolder;
import atg.commerce.order.OrderManager;

import com.roanis.tdd.annotation.NucleusWithCommerce;
import com.roanis.tdd.core.commerce.catalog.CatalogTestConstants;
import com.roanis.tdd.core.commerce.order.OrderTestHelper;
import com.roanis.tdd.junit4.runner.NucleusAwareJunit4ClassRunner;
import com.roanis.tdd.util.TestUtils;

@NucleusWithCommerce()
@RunWith(NucleusAwareJunit4ClassRunner.class)

public class RepriceOrderTest {
	private static final String repriceChainId="repriceOrder";
	
	private OrderManager mOrderManager;
	private OrderTestHelper mOrderTestHelper;	
	private OrderHolder mShoppingCart;
	private Order mOrder;	
	
	@Before
	public void setUp(){	
		mOrderTestHelper = TestUtils.getTestConfiguration().getOrderTestHelper();
		mOrderManager = mOrderTestHelper.getOrderManager();
		mShoppingCart = mOrderTestHelper.getShoppingCart();		
		mOrder = mShoppingCart.getCurrent();				
	}
	
	@After
	public void tearDown() throws Throwable{		
		mOrderTestHelper.reloadCart();		
		
		mOrderTestHelper = null;
		mOrderManager = null;
		mShoppingCart = null;
		mOrder = null;		
	}	

	@Test
	public void singleItem() throws Throwable {
		// By default, Base Order has 1 item, sku skuCasualBeltBlackSmall (19.99) and no promos
		@SuppressWarnings("unchecked")
		List<CommerceItem> items = mOrder.getCommerceItems();
		assertEquals(1, items.size());
		
		mOrderManager.getPipelineManager().runProcess(repriceChainId, mOrderTestHelper.createDefaultRepriceMap());
		assertEquals(19.99D, mOrder.getPriceInfo().getTotal(), 0.00D);
	}
	
	@Test
	public void addItem() throws Exception {		
		// Add 2 Jackets (85.00 each)
		mOrderTestHelper.addItemToOrder(mOrder, CatalogTestConstants.JACKET_LARGE_SKU_ID, CatalogTestConstants.MENS_JACKET_PRODUCT_ID, 2L);		
		assertEquals(3, mOrder.getTotalCommerceItemCount());  // 2 Jackets plus original item.
		
		mOrderManager.getPipelineManager().runProcess(repriceChainId, mOrderTestHelper.createDefaultRepriceMap());
		assertEquals(189.99D, mOrder.getPriceInfo().getTotal(), 0.00D);				
	}
	
	@Test
	public void removeItem() throws Exception {
		mOrderTestHelper.addItemToOrder(mOrder, CatalogTestConstants.JACKET_LARGE_SKU_ID, CatalogTestConstants.MENS_JACKET_PRODUCT_ID, 2L);		
		assertEquals(3, mOrder.getTotalCommerceItemCount());  // 2 Jackets plus original item.
		
		mOrderManager.getPipelineManager().runProcess(repriceChainId, mOrderTestHelper.createDefaultRepriceMap());
		assertEquals(189.99D, mOrder.getPriceInfo().getTotal(), 0.00D);		
		
		// Remove original item
		mOrderTestHelper.removeItemFromOrder(mOrder, (CommerceItem) mOrder.getCommerceItems().get(0));
		assertEquals(2, mOrder.getTotalCommerceItemCount());
		
		mOrderManager.getPipelineManager().runProcess(repriceChainId, mOrderTestHelper.createDefaultRepriceMap());
		assertEquals(170.00D, mOrder.getPriceInfo().getTotal(), 0.00D);
	}
	
	@Test
	public void changeQuantity() throws Exception {
		CommerceItem item = (CommerceItem) mOrder.getCommerceItems().get(0);
		assertEquals(1, item.getQuantity());
		
		// Change qty to 2.
		mOrderTestHelper.getPurchaseProcessHelper().adjustItemRelationshipsForQuantityChange(mOrder, item, 2L);
		
		mOrderManager.getPipelineManager().runProcess(repriceChainId, mOrderTestHelper.createDefaultRepriceMap());
		assertEquals(39.98D, mOrder.getPriceInfo().getTotal(), 0.00D);
	}
	
	@Test
	public void noItems() throws Exception {
		mOrderTestHelper.removeItemFromOrder(mOrder, (CommerceItem) mOrder.getCommerceItems().get(0));
		assertEquals(0, mOrder.getCommerceItems().size());
		
		mOrderManager.getPipelineManager().runProcess(repriceChainId, mOrderTestHelper.createDefaultRepriceMap());
		assertEquals(0.00D, mOrder.getPriceInfo().getTotal(), 0.00D);		
	}	
}
