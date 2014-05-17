package com.roanis.tdd.junit4.rules;


/**
 * An {@link org.junit.rules.ExternalResource} {@link TestRule}, which loads the specified Order, before a test
 * and sets it as ShoppingCart.current. After the test runs, ShoppingCart.current is set back to null.
 * 
 * @author rory
 *
 */
public class OrderData extends ExternalNucleusData {
	private String mOrderId;
	
	public OrderData(String orderId){
		mOrderId = orderId;
	}
	
	@Override
	protected void before() throws Throwable {		
		super.before();
		getTestConfiguration().getOrderTestHelper().setAsCurrent(mOrderId);
	}
	
	@Override
	protected void after() {	
		getTestConfiguration().getOrderTestHelper().resetCart();
		super.after();
	}
	
}
