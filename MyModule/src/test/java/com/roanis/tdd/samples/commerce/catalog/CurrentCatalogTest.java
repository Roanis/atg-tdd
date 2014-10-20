package com.roanis.tdd.samples.commerce.catalog;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;

import atg.commerce.dp.CatalogContext;

import com.roanis.tdd.annotation.NucleusRequired;
import com.roanis.tdd.annotation.NucleusWithCatalog;
import com.roanis.tdd.core.commerce.catalog.CatalogTestConstants;
import com.roanis.tdd.junit4.runner.NucleusAwareJunit4ClassRunner;

@NucleusRequired(modules={"TDD.MyModule"})
@NucleusWithCatalog()
@RunWith(NucleusAwareJunit4ClassRunner.class)
public class CurrentCatalogTest {

	@Test
	public void currentCatalogExists() {
		assertNotNull(CatalogContext.getCurrentCatalog());
	}
	
	@Test
	public void baseCatalogIsCurrent(){
		assertEquals(CatalogTestConstants.BASE_CATALOG_ID, CatalogContext.getCurrentCatalog().getRepositoryId());
	}

}
