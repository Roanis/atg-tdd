package com.roanis.tdd.samples.commerce.order;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import atg.commerce.CommerceException;
import atg.commerce.order.Order;
import atg.servlet.ServletUtil;

import com.roanis.tdd.base.commerce.order.BaseOrderTest;

@RunWith(JUnit4.class)
public class OrderManagerTest extends BaseOrderTest {
	
	@Test
	public void create() throws CommerceException{
		Order newOrder = getOrderManager().createOrder(ServletUtil.getCurrentUserProfile().getRepositoryId());
		assertNotNull(newOrder);
	}
	
	@Test
	public void update() throws CommerceException{
		Order currentOrder = getShoppingCart().getCurrent();
		currentOrder.setDescription("new desc");		
		getOrderManager().updateOrder(currentOrder);
		assertEquals("new desc", currentOrder.getDescription());
	}
	
	@Test
	public void delete() throws CommerceException{
		getOrderManager().removeOrder("incompleteOrder");
		assertFalse(getOrderManager().orderExists("incompleteOrder"));
	}

}
