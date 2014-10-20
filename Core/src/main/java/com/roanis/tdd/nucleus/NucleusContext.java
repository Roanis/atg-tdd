package com.roanis.tdd.nucleus;

import java.util.List;

import atg.nucleus.Nucleus;

public class NucleusContext {
	
	private Nucleus mGlobalNucleus;
	private List<String> mRunningModules;
	private boolean mIsUsingTestConfig;
	
	private static NucleusContext mCurrent;
	
	private NucleusContext(){		
	}

	public static NucleusContext getCurrent() {
		if(mCurrent == null){
			mCurrent = new NucleusContext();
		}
		return mCurrent;
	}

	public static void setCurrent(NucleusContext pCurrent) {
		mCurrent = pCurrent;
	}
	
	public static boolean isNucleusRunning(){
		return getCurrent().getGlobalNucleus() != null;
	}
	
	public static void clear(){
		mCurrent.setGlobalNucleus(null);
		mCurrent.setRunningModules(null);
	}

	public Nucleus getGlobalNucleus() {
		return mGlobalNucleus;
	}

	public void setGlobalNucleus(Nucleus pGlobalNucleus) {
		mGlobalNucleus = pGlobalNucleus;
	}
	
	public List<String> getRunningModules() {
		return mRunningModules;
	}

	public void setRunningModules(List<String> pRunningModules) {
		mRunningModules = pRunningModules;
	}
	
	public boolean isUsingTestConfig() {
		return mIsUsingTestConfig;
	}

	public void setIsUsingTestConfig(boolean pIsUsingTestConfig) {
		mIsUsingTestConfig = pIsUsingTestConfig;
	}	
	
}
