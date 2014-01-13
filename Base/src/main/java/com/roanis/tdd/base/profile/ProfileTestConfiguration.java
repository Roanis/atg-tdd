package com.roanis.tdd.base.profile;

import atg.repository.MutableRepository;
import atg.userprofiling.ProfileTools;
import atg.userprofiling.PropertyManager;

public class ProfileTestConfiguration {
	
	private ProfileTools mProfileTools;

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

}
