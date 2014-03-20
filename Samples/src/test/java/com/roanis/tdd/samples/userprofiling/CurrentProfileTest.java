package com.roanis.tdd.samples.userprofiling;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import atg.servlet.ServletUtil;

import com.roanis.tdd.core.userprofiling.BaseProfileTest;
import com.roanis.tdd.junit4.runner.NucleusAwareJunit4ClassRunner;

@RunWith(JUnit4.class)
public class CurrentProfileTest extends BaseProfileTest {

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
