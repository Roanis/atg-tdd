package com.roanis.tdd.util;

import com.google.common.base.Strings;
import com.roanis.tdd.nucleus.TddNucleusTestUtils;

public class EnvironmentUtils {

	/**
	 * If no ATG install directory is available and
	 * the {@code tdd.ignoreMissingATGInstall} property is true, then
	 * the test class should be ignored. Otherwise, assume it should
	 * run as normal.
	 * 
	 * @param testClass the class under test
	 * @return true if it should be ignored, false otherwise.
	 */
	public static boolean shouldIgnore(Class<?> testClass){
		if(isMissingATGInstall(testClass) && isIgnoreMissingATGInstall(testClass)){
			return true;
		}
		return false;
	}

	/**
	 * Check for an ATG install directory. If one exists, return false.
	 * If no directory exists, check the system property {@code tdd.ignoreMissingATGInstall}
	 * 
	 * @param testClass the class under test
	 * @return true if no ATG install directory exists, false otherwise.
	 */
	public static boolean isMissingATGInstall(Class<?> testClass) {
		if(TddNucleusTestUtils.isATGInstallAvailable()){
			return false;
		}
		return true;
	}

	/**
	 * Checks the {@code tdd.ignoreMissingATGInstall} system property.
	 * 
	 * @param testClass the class under test
	 * @return true if the system property is set and it's value is true, false
	 * otherwise.
	 */
	public static boolean isIgnoreMissingATGInstall(Class<?> testClass) {
		String isIgnoreMissingATGInstall = System.getProperty("tdd.ignoreMissingATGInstall");
		if(Strings.isNullOrEmpty(isIgnoreMissingATGInstall)){
			return false;
		}
		return Boolean.valueOf(isIgnoreMissingATGInstall);
	}
}
