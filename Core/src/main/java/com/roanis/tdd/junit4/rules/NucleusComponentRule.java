package com.roanis.tdd.junit4.rules;

import java.lang.reflect.Field;

import org.junit.rules.ExternalResource;

import atg.nucleus.Nucleus;
import atg.nucleus.naming.ComponentName;

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
		Object component = Nucleus.getGlobalNucleus().resolveName(ComponentName.getComponentName(mNucleusComponentPath));
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
