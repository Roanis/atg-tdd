package com.roanis.tdd.base;

import atg.servlet.ServletUtil;
import atg.userprofiling.Profile;

import com.roanis.tdd.tools.ProfileTestTools;

public class BaseProfileTest extends RoanisTestCase {
	private Profile mBaseProfile;

	public Profile getBaseProfile() {
		return mBaseProfile;
	}

	public void setBaseProfile(Profile pBaseProfile) {
		mBaseProfile = pBaseProfile;
	}
	
	private ProfileTestTools mProfileTestTools;
	public ProfileTestTools getProfileTestTools(){
		return mProfileTestTools;
	}
	
	
	@Override
	public void setUpComponents() throws Exception {
		super.setUpComponents();
		
		mProfileTestTools = new ProfileTestTools();
	}
	
	@Override
	public void setupTestData() throws Exception {		
		super.setupTestData();
		setupProfile();		
	}

	protected void setupProfile() throws Exception {
		mBaseProfile = mProfileTestTools.create();		
		ServletUtil.setCurrentUserProfile(mBaseProfile);
	}
	
	@Override
	public void tearDownComponents() {
		mProfileTestTools = null;
		super.tearDownComponents();
	}
	
	@Override
	public void tearDownTestData() throws Exception {
		mBaseProfile = null;
		super.tearDownTestData();
	}
}
