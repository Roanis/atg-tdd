package com.roanis.tdd.junit4.rules;

import org.junit.rules.TestRule;

/**
 * An {@link org.junit.rules.ExternalResource} {@link TestRule}, which loads the specified Profile, before a test
 * and sets it as ServletUtil.currentUserProfile. After the test runs, ServletUtil.currentUserProfile is set back to null.
 * 
 * @author rory
 *
 */
public class ProfileData extends ExternalNucleusData {
	
	private String mProfileId;
	
	public ProfileData(String profileId){
		mProfileId = profileId;
	}

	@Override
	protected void before() throws Throwable {		
		super.before();
		getTestConfiguration().getProfileTestHelper().setAsCurrent(mProfileId);
	}
	
	@Override
	protected void after() {	
		getTestConfiguration().getProfileTestHelper().reset();
		super.after();
	}


}
