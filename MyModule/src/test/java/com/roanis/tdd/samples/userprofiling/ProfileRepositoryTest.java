package com.roanis.tdd.samples.userprofiling;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import atg.repository.MutableRepository;
import atg.repository.MutableRepositoryItem;
import atg.repository.RepositoryException;
import atg.repository.RepositoryItem;

import com.google.common.base.Strings;
import com.roanis.tdd.annotation.NucleusComponent;
import com.roanis.tdd.annotation.NucleusRequired;
import com.roanis.tdd.annotation.NucleusWithProfile;
import com.roanis.tdd.junit4.runner.NucleusAwareJunit4ClassRunner;


@NucleusRequired(modules={"TDD.MyModule"})
@NucleusWithProfile()
@RunWith(NucleusAwareJunit4ClassRunner.class)
public class ProfileRepositoryTest {
	@NucleusComponent("/atg/userprofiling/ProfileAdapterRepository")
	private MutableRepository mProfileRepositoy;
	
	private RepositoryItem mUser;
	
	@Before
	public void setup() throws Exception{
		mUser = mProfileRepositoy.getItem("baseUser","user");
	}

	@Test
	public void userExists() {
		assertNotNull(mUser);
	}
	
	@Test
	public void userHasFirstName(){
		String firstName = (String) mUser.getPropertyValue("firstname");
		assertFalse(Strings.isNullOrEmpty(firstName));
	}
	
	@Test
	public void setPropertyValue() throws RepositoryException{
		MutableRepositoryItem mutableUser = mProfileRepositoy.getItemForUpdate(mUser.getRepositoryId(), mUser.getItemDescriptor().getItemDescriptorName());
		mutableUser.setPropertyValue("firstName", "meh");
		mProfileRepositoy.updateItem(mutableUser);
	}
	
	@Test
	public void transactionsAreRolledBackAfterEachTest() throws Exception {
		// the updates from the previous are automatically rolled back after the test finishes, 
		// meaning each test is starting with the same test data.
		String firstName = (String) mUser.getPropertyValue("firstname");
		assertNotNull(firstName);
		assertFalse(firstName.equals("meh"));
	}

}
