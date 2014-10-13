package com.roanis.tdd.samples.userprofiling;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;

import atg.servlet.ServletUtil;

import com.roanis.tdd.annotation.NucleusWithProfile;
import com.roanis.tdd.junit4.runner.NucleusAwareJunit4ClassRunner;

@NucleusWithProfile()
@RunWith(NucleusAwareJunit4ClassRunner.class)
public class CurrentProfileTest {

	@Test
	public void currentUserExists() {
		assertNotNull(ServletUtil.getCurrentUserProfile());
	}
	
	@Test
	// Let's have a look at that extended Profile property from MyModule!
	public void isStaffMember(){
		assertTrue((Boolean) ServletUtil.getCurrentUserProfile().getPropertyValue("isStaff"));
	}

}
