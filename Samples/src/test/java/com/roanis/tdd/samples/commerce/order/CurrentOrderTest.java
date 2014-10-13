package com.roanis.tdd.samples.commerce.order;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import atg.commerce.order.CreditCard;
import atg.commerce.order.HardgoodShippingGroup;
import atg.commerce.order.Order;
import atg.commerce.order.OrderHolder;
import atg.commerce.states.OrderStates;
import atg.commerce.states.StateDefinitions;

import com.roanis.tdd.annotation.NucleusComponent;
import com.roanis.tdd.annotation.NucleusWithOrder;
import com.roanis.tdd.junit4.runner.NucleusAwareJunit4ClassRunner;

@NucleusWithOrder()
@RunWith(NucleusAwareJunit4ClassRunner.class)
public class CurrentOrderTest{
	
	private Order mCurrentOrder;
	
	@NucleusComponent("/atg/commerce/ShoppingCart")
	private OrderHolder mShoppingCart;
		
	@Before
    public void setUp() throws Exception {    	
		mCurrentOrder = mShoppingCart.getCurrent();
    }
    
    @After
    public void tearDown() throws Exception { 
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
