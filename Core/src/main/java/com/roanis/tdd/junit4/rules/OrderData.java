package com.roanis.tdd.junit4.rules;


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
