package com.roanis.tdd.samples.request;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;

import atg.commerce.order.OrderHolder;
import atg.servlet.DynamoHttpServletRequest;
import atg.servlet.DynamoHttpServletResponse;
import atg.servlet.ServletUtil;

import com.roanis.tdd.annotation.NucleusRequired;
import com.roanis.tdd.annotation.NucleusWithCommerce;
import com.roanis.tdd.junit4.runner.NucleusAwareJunit4ClassRunner;

@NucleusRequired(modules={"TDD.MyModule"})
@NucleusWithCommerce()
@RunWith(NucleusAwareJunit4ClassRunner.class)

public class CurrentRequestTest {
	
	@Test
	public void resolveProfile(){
		DynamoHttpServletRequest currentRequest = ServletUtil.getCurrentRequest();
		Object user = currentRequest.resolveName("/atg/userprofiling/Profile");
		assertNotNull(user);
	}
	
	@Test
	public void resolveCart(){
		DynamoHttpServletRequest currentRequest = ServletUtil.getCurrentRequest();
		OrderHolder cart = (OrderHolder) currentRequest.resolveName("/atg/commerce/ShoppingCart");
		assertNotNull(cart);
	}
	
	@Test
	public void getCurrentResponse(){
		DynamoHttpServletResponse response = ServletUtil.getCurrentResponse();
		assertNotNull(response);
	}
	
	@Test
	public void getFormHandler(){
		DynamoHttpServletRequest currentRequest = ServletUtil.getCurrentRequest();
		Object fh = currentRequest.resolveName("/atg/commerce/order/purchase/CartModifierFormHandler");
		assertNotNull(fh);
	}

}
