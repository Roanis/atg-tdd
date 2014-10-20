package com.roanis.tdd.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.roanis.tdd.core.commerce.catalog.CatalogTestConstants;
import com.roanis.tdd.core.commerce.order.OrderTestConstants;
import com.roanis.tdd.core.commerce.pricing.priceLists.PriceListTestConstants;
import com.roanis.tdd.core.multisite.SiteTestConstants;
import com.roanis.tdd.core.userprofiling.ProfileTestConstants;

/**
 * A shortcut for using all the other annotations together, i.e. indicates that the following 
 * should be set up:<br/>
 * <ul>
 * <li>site</li>
 * <li>profile</li>
 * <li>catalog</li>
 * <li>price list</li>
 * <li>sale price list</li>
 * <li>order</li>
 * </ul>
 * 
 * <p>Different ids can be specified for each property i.e. @NucleusWithCommerce(site="1", profile="2" ...).</p>
 * @author rory
 *
 */
@NucleusTestSetup
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface NucleusWithCommerce {
	String site() default SiteTestConstants.BASE_SITE_ID;
	String profile() default ProfileTestConstants.BASE_USER_ID;
	String catalog() default CatalogTestConstants.BASE_CATALOG_ID;
	String priceList() default PriceListTestConstants.BASE_PRICE_LIST_ID;
	String salePriceList() default PriceListTestConstants.BASE_SALE_PRICE_LIST_ID;
	String order() default OrderTestConstants.BASE_INCOMPLETE_ORDER_ID;
}
