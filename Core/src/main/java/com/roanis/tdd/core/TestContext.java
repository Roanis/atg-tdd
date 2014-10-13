package com.roanis.tdd.core;

import java.lang.reflect.Method;

public class TestContext {
	private Class<?> mTestClass;
	private Object mTestInstance;
	private Method mTestMethod;
	private Throwable mTestException;
	
	public TestContext(Class<?> testClass){
		mTestClass = testClass;
	}
	
	public void updateState(Object testInstance, Method testMethod, Throwable testException) {
		mTestInstance = testInstance;
		mTestMethod = testMethod;
		mTestException = testException;
	}

	public Throwable getTestException() {
		return mTestException;
	}

	public void setTestException(Throwable pTestException) {
		mTestException = pTestException;
	}

	public Method getTestMethod() {
		return mTestMethod;
	}

	public void setTestMethod(Method pTestMethod) {
		mTestMethod = pTestMethod;
	}

	public Object getTestInstance() {
		return mTestInstance;
	}

	public void setTestInstance(Object pTestInstance) {
		mTestInstance = pTestInstance;
	}

	public Class<?> getTestClass() {
		return mTestClass;
	}

	public void setTestClass(Class<?> pTestClass) {
		mTestClass = pTestClass;
	}

}
