package com.roanis.tdd.samples.commerce.order;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import atg.commerce.CommerceException;
import atg.commerce.order.Order;
import atg.commerce.order.OrderHolder;
import atg.commerce.order.OrderManager;
import atg.servlet.ServletUtil;

import com.roanis.tdd.annotation.NucleusComponent;
import com.roanis.tdd.annotation.NucleusWithOrder;
import com.roanis.tdd.annotation.NucleusWithProfile;
import com.roanis.tdd.junit4.runner.NucleusAwareJunit4ClassRunner;

@NucleusWithOrder()
@NucleusWithProfile()
@RunWith(NucleusAwareJunit4ClassRunner.class)
public class OrderManagerTest {	
	
	@NucleusComponent("/atg/commerce/ShoppingCart")
	private OrderHolder mShoppingCart;
	
	@NucleusComponent("/atg/commerce/order/OrderManager")
	private OrderManager mOrderManager;
	
	@Before
	public void setUp(){
		mShoppingCart.setProfile(ServletUtil.getCurrentUserProfile());
	}
	
	@After
	public void tearDown(){		
		mShoppingCart.setProfile(null);		
	}
	
	@Test
	public void create() throws CommerceException{
		Order newOrder = mOrderManager.createOrder(ServletUtil.getCurrentUserProfile().getRepositoryId());
		assertNotNull(newOrder);
	}
	
	@Test
	public void update() throws CommerceException{
		Order currentOrder = mShoppingCart.getCurrent();
		currentOrder.setDescription("new desc");		
		mOrderManager.updateOrder(currentOrder);
		assertEquals("new desc", currentOrder.getDescription());
	}
	
	@Test
	public void delete() throws CommerceException{
		mOrderManager.removeOrder("incompleteOrder");
		assertFalse(mOrderManager.orderExists("incompleteOrder"));
	}

}
