package com.roanis.tdd.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.roanis.tdd.core.commerce.order.OrderTestConstants;

/**
 * This annotation indicates that an Order should be loaded from the repository
 * and set as ShoppingCart.current.
 * 
 *  <p>The order used can be changed by specifying a different id e.g. @NucleusWithOrder("myOrderID").</p>
 * 
 * @author rory
 *
 */
@NucleusTestSetup
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface NucleusWithOrder {
	
	String value() default OrderTestConstants.BASE_INCOMPLETE_ORDER_ID;
}
