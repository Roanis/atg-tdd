package com.roanis.tdd.core.userprofiling;

import atg.repository.MutableRepository;
import atg.repository.MutableRepositoryItem;
import atg.servlet.ServletUtil;
import atg.userprofiling.Profile;
import atg.userprofiling.ProfileTools;
import atg.userprofiling.PropertyManager;

import com.roanis.tdd.core.MissingDataException;
import com.roanis.tdd.core.TestHelper;
import com.roanis.tdd.nucleus.TddNucleusTestUtils;

public class ProfileTestHelper implements TestHelper {
	
	private ProfileTools mProfileTools;
	private String mDefaultProfileId;
	
	public void defaultCurrentProfile() throws Exception{
		setAsCurrent(getDefaultProfileId());
	}
	
	public void setAsCurrent(String pId) throws Exception {
		MutableRepositoryItem user = getUser(pId);
		Profile profile = resolveProfileComponent();
		profile.setDataSource(user);	
		ServletUtil.setCurrentUserProfile(profile);
	}

	public Profile resolveProfileComponent() {
		return (Profile) TddNucleusTestUtils.resolveComponent("/atg/userprofiling/Profile");
	}

	protected MutableRepositoryItem getUser(String pId) throws Exception {
		MutableRepositoryItem user = (MutableRepositoryItem) getProfileRepository().getItem(pId, getProfileTools().getDefaultProfileType());
		if (null == user){
			throw new MissingDataException("Can't find repository data for user: " + pId);
		}
		return user;
	}
	
	public void reset(){
		ServletUtil.setCurrentUserProfile(null);
	}
	
	public String getDefaultProfileId() {
		return mDefaultProfileId;
	}

	public void setDefaultProfileId(String pDefaultProfileId) {
		mDefaultProfileId = pDefaultProfileId;
	}

	public ProfileTools getProfileTools() {
		return mProfileTools;
	}

	public void setProfileTools(ProfileTools pProfileTools) {
		mProfileTools = pProfileTools;
	}
	
	public MutableRepository getProfileRepository () throws Exception {
    	return getProfileTools().getProfileRepository();
    }
    
    public PropertyManager getPropertyManager() throws Exception{
    	return getProfileTools().getPropertyManager();
    }	
    
    @Override
	public String getName() {
		return getClass().getCanonicalName();
	}	

}
