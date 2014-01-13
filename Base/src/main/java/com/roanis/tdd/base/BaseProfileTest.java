package com.roanis.tdd.base;

import atg.nucleus.Nucleus;
import atg.repository.MutableRepositoryItem;
import atg.servlet.ServletUtil;
import atg.userprofiling.Profile;

import com.roanis.tdd.base.profile.ProfileTestConfiguration;
import com.roanis.tdd.base.profile.ProfileTestConstants;

public class BaseProfileTest extends BaseSiteTest {	
	private Profile mBaseProfile;
	private ProfileTestConfiguration mProfileTestConfiguration;
	private String mProfileId = ProfileTestConstants.BASE_USER_ID;
				
	@Override
	public void setUp() throws Exception {
		super.setUp();				 
        setupProfile();	
	}
	
	@Override
	protected void setupTestConfiguration() {		
		super.setupTestConfiguration();
		
		mProfileTestConfiguration = (ProfileTestConfiguration) Nucleus.getGlobalNucleus().resolveName("/roanis/tdd/base/profile/ProfileTestConfiguration");
	}
	
	@Override
	public void tearDown() throws Exception {
		mProfileTestConfiguration = null;
		mBaseProfile = null;
		mProfileId = null;
		super.tearDown();
	}				
		
	protected void setupProfile() throws Exception {
		MutableRepositoryItem baseUser = getUser(getProfileId());
		mBaseProfile = new Profile();
		mBaseProfile.setDataSource(baseUser);	
		ServletUtil.setCurrentUserProfile(mBaseProfile);
	}

	protected MutableRepositoryItem getUser(String pId) throws Exception {
		MutableRepositoryItem user = (MutableRepositoryItem) mProfileTestConfiguration.getProfileRepository().getItem(pId, mProfileTestConfiguration.getProfileTools().getDefaultProfileType());
		if (null == user){
			throw new MissingDataException("Can't find repository data for user: " + pId);
		}
		return user;
	}
	
	public ProfileTestConfiguration getProfileTestConfiguration(){
		return mProfileTestConfiguration;
	}
    
	public Profile getBaseProfile() {
		return mBaseProfile;
	}

	public void setBaseProfile(Profile profile) {
		mBaseProfile = profile;
	}
	
	public String getProfileId() {
		return mProfileId;
	}

	public void setProfileId(String pProfileId) {
		mProfileId = pProfileId;
	}
}
