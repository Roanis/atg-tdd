package com.roanis.tdd.junit4.rules;

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
