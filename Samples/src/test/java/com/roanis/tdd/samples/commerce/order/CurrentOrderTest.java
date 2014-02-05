package com.roanis.tdd.samples.commerce.order;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import atg.commerce.order.CreditCard;
import atg.commerce.order.HardgoodShippingGroup;
import atg.commerce.order.Order;
import atg.commerce.states.OrderStates;
import atg.commerce.states.StateDefinitions;

import com.roanis.tdd.base.commerce.order.BaseOrderTest;

@RunWith(JUnit4.class)
public class CurrentOrderTest extends BaseOrderTest {
	
	private Order mCurrentOrder;
		
	@Override
    public void setUp() throws Exception {    	
    	super.setUp();
    	mCurrentOrder = getShoppingCart().getCurrent();
    }
    
    @Override
    public void tearDown() throws Exception {    	
    	super.tearDown();
    	mCurrentOrder = null;
    }
       
    @Test
	public void exists(){
		assertNotNull(mCurrentOrder);
	}
	
	@Test
	public void isIncomplete(){		
		assertEquals(StateDefinitions.ORDERSTATES.getStateValue(OrderStates.INCOMPLETE), mCurrentOrder.getState());
	}
	
	@Test
	public void hasOneItem(){		
		assertEquals(1, mCurrentOrder.getCommerceItems().size());
	}
	
	@Test
	public void hasShippingAddress(){		
		HardgoodShippingGroup sg = (HardgoodShippingGroup) mCurrentOrder.getShippingGroups().get(0);
		assertNotNull(sg.getShippingAddress().getAddress1());
	}
	
	@Test
	public void hasCreditCardPayment(){		
		CreditCard cc = (CreditCard) mCurrentOrder.getPaymentGroups().get(0);
		assertNotNull(cc.getCreditCardNumber());
	}		

}
