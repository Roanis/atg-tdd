package com.roanis.tdd.tools;

import atg.commerce.profile.CommercePropertyManager;
import atg.nucleus.Nucleus;
import atg.repository.MutableRepository;
import atg.repository.MutableRepositoryItem;
import atg.repository.RepositoryItem;
import atg.userprofiling.Profile;
import atg.userprofiling.ProfileTools;
import atg.userprofiling.PropertyManager;

import com.roanis.tdd.base.TestConstants;

public class ProfileTestTools {            
    protected ProfileTools mProfileTools; 
	
	public ProfileTools getProfileTools() throws Exception{
		if (null == mProfileTools) {
			mProfileTools = (ProfileTools) Nucleus.getGlobalNucleus().resolveName("/atg/userprofiling/ProfileTools");
            if (null == mProfileTools) {
                throw new Exception("ProfileTools cannot be resolved.");
            }
        }

        return mProfileTools; 
	}	

    public MutableRepository getProfileRepository () throws Exception {
    	return getProfileTools().getProfileRepository();
    }
    
    public PropertyManager getPropertyManager() throws Exception{
    	return getProfileTools().getPropertyManager();
    }
    
    public Profile create() throws Exception{    	
    	MutableRepositoryItem userItem = (MutableRepositoryItem) getProfileRepository().getItem(TestConstants.BASE_USER_ID, mProfileTools.getDefaultProfileType());
    	if (null == userItem){
    		userItem = getProfileRepository().createItem(TestConstants.BASE_USER_ID, mProfileTools.getDefaultProfileType());
    		userItem.setPropertyValue(getPropertyManager().getFirstNamePropertyName(), "Roanis");
    		userItem.setPropertyValue(getPropertyManager().getLastNamePropertyName(), "User");
    		userItem.setPropertyValue(getPropertyManager().getEmailAddressPropertyName(), "test.user@roanis.com");
    		userItem.setPropertyValue(getPropertyManager().getPasswordPropertyName(), "password");
    		userItem.setPropertyValue(getPropertyManager().getLoginPropertyName(), "test.user@roanis.com");
            getProfileRepository().addItem(userItem);
    	}
    	    	 
        Profile user = new Profile();
        user.setDataSource(userItem);        
        
        return user;
    }
        
    public Profile create (String id, RepositoryItem defaultCatalog) throws Exception {    	
        MutableRepositoryItem userItem = getProfileRepository().createItem(id, mProfileTools.getDefaultProfileType());
        Profile user = new Profile();
        user.setDataSource(userItem);
        user.setPropertyValue(getPropertyManager().getFirstNamePropertyName(), "Roanis");
        user.setPropertyValue(getPropertyManager().getLastNamePropertyName(), "User");
        user.setPropertyValue(getPropertyManager().getEmailAddressPropertyName(), "test.user@roanis.com");
        
        // How to Extend to add this?
        //user.setPropertyValue("homeAddress", createEmptyAddress("test_home_address"));
        
        if (getPropertyManager() instanceof CommercePropertyManager){
        	user.setPropertyValue(((CommercePropertyManager) getPropertyManager()).getCatalogPropertyName(), defaultCatalog);
        	user.setPropertyValue(((CommercePropertyManager) getPropertyManager()).getBillingAddressPropertyName(), createEmptyAddress("billing_address"));
        	user.setPropertyValue(((CommercePropertyManager) getPropertyManager()).getShippingAddressPropertyName(), createEmptyAddress("shipping_address"));
        }
        
        return user;
    }    

    public MutableRepositoryItem createEmptyAddress (String id) throws Exception {
        MutableRepositoryItem contactInfoItem = getProfileRepository().createItem(id, "contactInfo");
        return contactInfoItem;
    }

}
