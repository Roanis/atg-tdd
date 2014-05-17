package com.roanis.tdd.junit4.rules;

import javax.transaction.TransactionManager;

import org.junit.rules.ExternalResource;

import atg.dtm.TransactionDemarcationException;
import atg.nucleus.Nucleus;

/**
 * An {@link org.junit.rules.ExternalResource} {@link TestRule}, which starts a transaction in the
 * before method and calls rollback the after method. This is typically used to wrap
 * each test method in it's own transaction and then rollback the changes after the test method finishes.
 * This ensures that changes in one test do not affect the data used by the remaining tests.<br/>
 * 
 * <p>NOTE: Test classes should not use this directly. The framwework already provides transactional support in 
 * {@link com.roanis.tdd.junit4.runner.NucleusAwareJunit4ClassRunner}</p>
 * 
 * @author rory
 *
 */
public class NucleusWithTransaction extends ExternalResource  {
	private TransactionManager mTransactionManager;
	
	@Override
	protected void before() throws Throwable {		
		super.before();
				
		startTransaction();
	}

	private void startTransaction() throws TransactionDemarcationException {
		mTransactionManager = (TransactionManager) Nucleus.getGlobalNucleus().resolveName("/atg/dynamo/transaction/TransactionManager");
		try {
			mTransactionManager.begin();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void after() {
		try {
			mTransactionManager.rollback();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		super.after();
	}	

}
