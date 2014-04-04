package com.roanis.tdd.junit4.rules;

import javax.transaction.TransactionManager;

import org.junit.rules.ExternalResource;

import atg.dtm.TransactionDemarcation;
import atg.dtm.TransactionDemarcationException;
import atg.nucleus.Nucleus;

public class NucleusWithTransaction extends ExternalResource  {
	private TransactionManager mTransactionManager;
	private TransactionDemarcation mTransactionDemarcation;
	
	@Override
	protected void before() throws Throwable {		
		super.before();
				
		startTransaction();
	}

	private void startTransaction() throws TransactionDemarcationException {
		mTransactionManager = (TransactionManager) Nucleus.getGlobalNucleus().resolveName("/atg/dynamo/transaction/TransactionManager");
		mTransactionDemarcation = new TransactionDemarcation();
		mTransactionDemarcation.begin(mTransactionManager);
	}
	
	@Override
	protected void after() {
		try {
			rollbackTransaction();
		} catch (TransactionDemarcationException e) {			
			e.printStackTrace();
		}
		super.after();
	}
	
	protected void rollbackTransaction() throws TransactionDemarcationException {
		mTransactionDemarcation.end(true);		
	}

}
