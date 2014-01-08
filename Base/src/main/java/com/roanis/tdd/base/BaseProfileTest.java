package com.roanis.tdd.base;

import atg.nucleus.Nucleus;
import atg.repository.MutableRepository;
import atg.repository.MutableRepositoryItem;
import atg.servlet.ServletUtil;
import atg.userprofiling.Profile;
import atg.userprofiling.ProfileTools;
import atg.userprofiling.PropertyManager;

public class BaseProfileTest extends RoanisTestCase {
	protected ProfileTools mProfileTools;
	private Profile mBaseProfile;
			
	@Override
	public void setUp() throws Exception {
		super.setUp();
		
		resolveProfileTools();        
        setupProfile();	
	}

	protected void resolveProfileTools() throws Exception {
		mProfileTools = (ProfileTools) Nucleus.getGlobalNucleus().resolveName("/atg/userprofiling/ProfileTools");
        if (null == mProfileTools) {
            throw new Exception("ProfileTools cannot be resolved.");
        }
	}			
		
	protected void setupProfile() throws Exception {
		MutableRepositoryItem baseUser = getUser(TestConstants.BASE_USER_ID);
		mBaseProfile = new Profile();
		mBaseProfile.setDataSource(baseUser);	
		ServletUtil.setCurrentUserProfile(mBaseProfile);
	}

	protected MutableRepositoryItem getUser(String id) throws Exception {
		MutableRepositoryItem user = (MutableRepositoryItem) getProfileRepository().getItem(id, mProfileTools.getDefaultProfileType());
		if (null == user){
			throw new MissingDataException("Can't find repository data for user: " + id);
		}
		return user;
	}
	
	public MutableRepository getProfileRepository () throws Exception {
    	return mProfileTools.getProfileRepository();
    }
    
    public PropertyManager getPropertyManager() throws Exception{
    	return mProfileTools.getPropertyManager();
    }		
	
	@Override
	public void tearDown() throws Exception {
		mProfileTools = null;
		setBaseProfile(null);
		super.tearDown();
	}

	public Profile getBaseProfile() {
		return mBaseProfile;
	}

	public void setBaseProfile(Profile profile) {
		mBaseProfile = profile;
	}
}
