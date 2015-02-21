package com.roanis.tdd.junit4.rules;

import java.lang.reflect.Field;

import org.junit.rules.ExternalResource;

import com.roanis.tdd.nucleus.TddNucleusTestUtils;

public class NucleusComponentRule extends ExternalResource {
	private Field mField;
	private Object mTestInstance;
	private String mNucleusComponentPath;
	
	public NucleusComponentRule(Field field, Object testInstance, String nucleusComponentPath){
		mField = field;
		mTestInstance = testInstance;
		mNucleusComponentPath = nucleusComponentPath;
	}
	
	@Override
	protected void before() throws Throwable {
		Object component = TddNucleusTestUtils.resolveComponent(mNucleusComponentPath);
		if (!mField.isAccessible()){
			mField.setAccessible(true);
		}
		mField.set(mTestInstance, component);
		
		super.before();
	}
	
	@Override
	protected void after() {		
		super.after();
	}

}
