package com.roanis.tdd.samples.profile;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import atg.servlet.ServletUtil;

import com.roanis.tdd.base.BaseProfileTest;

@RunWith(JUnit4.class)
public class CurrentProfileTest extends BaseProfileTest {

	@Test
	public void currentUserExists() {
		assertNotNull(ServletUtil.getCurrentUserProfile());
	}

}
