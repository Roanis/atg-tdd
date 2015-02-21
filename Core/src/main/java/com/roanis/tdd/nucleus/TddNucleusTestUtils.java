package com.roanis.tdd.nucleus;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import atg.nucleus.Nucleus;
import atg.nucleus.NucleusTestUtils;
import atg.nucleus.NucleusTestUtils.NucleusStartupOptions;
import atg.nucleus.ServiceException;
import atg.nucleus.naming.ComponentName;
import atg.servlet.DynamoHttpServletRequest;
import atg.servlet.ServletTestUtils;
import atg.servlet.ServletTestUtils.TestingDynamoHttpServletRequest;
import atg.servlet.ServletUtil;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;

/**
 * Utility methods for working with Nucleus.
 * 
 * @author rory
 *
 */
public class TddNucleusTestUtils {
	public static final Logger log = Logger.getLogger(TddNucleusTestUtils.class);
	
	public static final String META_INF_DIR_NAME = "META-INF";
	public static final String MANIFEST_FILE_NAME = "MANIFEST.MF";
	public static final String MANIFEST_BACKUP_EXTENSION = ".BAK";
	public static final String ATG_CONFIG_PATH_ATTRIBUTE_NAME = "ATG-Config-Path: ";
	public static final String ATG_REQUIRED_PATH_ATTRIBUTE_NAME = "ATG-Required: ";
	public static final String TDD_REQUIRED_MODULES = "TDD.Core";
	public static final String TEST_CONFIG_LAYER_NAME = "testconfig";
	
	/**
	 * Start Nucleus with the specified module list.
	 * 
	 * @param moduleList - the list of ATG modules to start
	 * @param testClass - the test class or suite which is invoking this method
	 * @return - the running Nucleus
	 * @throws Throwable 
	 */
	public static Nucleus startNucleus (List<String> moduleList, boolean isUseTestConfigLayer, Class<?> testClass) throws Throwable {	
		preStartNucleus(moduleList, isUseTestConfigLayer, testClass);        
        Nucleus nucleus = doStartNucleus(moduleList, isUseTestConfigLayer, testClass);
        postStartNucleus(nucleus, moduleList, isUseTestConfigLayer, testClass);        
        return nucleus;
    }
	
	private static void preStartNucleus(List<String> moduleList, boolean isUseTestConfigLayer, Class<?> testClass) throws Throwable {
		System.out.println("TDD by Rory Curtis: rory_curtis@roanis.com");
        
		validateModules(moduleList);        
        validateATGInstall(); 
        
        if(isUseTestConfigLayer){
			addTestConfigPath(moduleList, testClass);
        }
        addTddToModules(moduleList);
	}	
		
	protected static void addTddToModules(List<String> moduleList) {
		moduleList.add(TDD_REQUIRED_MODULES);
	}

	private static Nucleus doStartNucleus(List<String> moduleList, boolean isUseTestConfigLayer, Class<?> testClass) throws Throwable {
		// Fire up Nucleus - make sure DYNAMO_HOME and DUST_HOME are set.				
        String initialComponent = "/atg/dynamo/Configuration";
        NucleusStartupOptions startupOptions = null;
        
        // Make sure to get the testConfigDir, before adding TDD.Core to the front of the module list.
        String testConfigDir=getTestConfigPath(moduleList);
        
        if(isUseTestConfigLayer){
        	startupOptions = new NucleusStartupOptions(moduleList.toArray(new String[0]), testClass, testConfigDir, initialComponent);
        } else { 
        	startupOptions = new NucleusStartupOptions(moduleList.toArray(new String[0]), testClass, initialComponent);
        }
                
        Nucleus nucleus = NucleusTestUtils.startNucleusWithModules(startupOptions);

        if (null == nucleus) {
            throw new Exception("Unable to start Nucleus for unit tests.");
        }
		return nucleus;
	}

	
	private static void addTestConfigPath(List<String> moduleList, Class<?> testClass) throws Throwable {
		String testConfigPath=getTestConfigPath(moduleList);
		File testConfigDir=getTestConfigDirAsFile(moduleList);
		updateNucleusConfigProperty(testClass, testConfigPath, testConfigDir);
		return;
	}

	@SuppressWarnings("unchecked")
	private static void updateNucleusConfigProperty(Class<?> testClass, String testConfigPath, File testConfigDir) throws NoSuchFieldException, IllegalAccessException {
		Field field = NucleusTestUtils.class.getDeclaredField("sConfigDir");
		field.setAccessible(true);		
		Map<Class<?>, Map<String, File>> value = (Map<Class<?>, Map<String, File>>) field.get(null);
		Map<String, File> testConfig= Maps.newHashMap();
		testConfig.put(testConfigPath, testConfigDir);
		value.put(testClass, testConfig);
	}

	private static void postStartNucleus(Nucleus nucleus, List<String> moduleList, boolean isUseTestConfigLayer, Class<?> testClass) {
		initialiseRequestResponsePair();
	}


	public static void validateModules(List<String> moduleList) throws Exception {
		if ((null == moduleList) || (0 == moduleList.size())) {
            throw new Exception("A module list must be specfied when starting Nucleus.");
        }
	}
		
	public static void validateATGInstall() {
		if(! isATGInstallAvailable()){
			throw new RuntimeException("No ATG install could be found from DYNAMO_HOME["+dynamoHomeAsString()+"], or ATG_HOME ["+atgHomeAsString()+"]");
		}		
	}
	
	public static String getTestConfigPath(List<String> moduleList) throws IOException {
		String atgHome = getATGHome();
		String moduleName=moduleList.get(0);
		String moduleDir = atgHome + moduleName.replace(".", File.separator);
		String testConfigPath=moduleDir + File.separator + TEST_CONFIG_LAYER_NAME;
		return testConfigPath;
	}


	protected static boolean testConfigLayerExists(List<String> moduleList) throws Throwable {
		return null != getTestConfigDirAsFile(moduleList);
	}

	protected static File getTestConfigDirAsFile(List<String> moduleList) throws Throwable {
		File testConfigDir = new File(getTestConfigPath(moduleList));
		return testConfigDir;
	}

	protected static void backupManifestFile(String moduleDir) throws IOException {			
		String manifestBackupPath = getBackupManifestPath(moduleDir);	
		File manifestBackupFile = new File(manifestBackupPath);
		if(manifestBackupFile.exists()){
			System.out.println("WARN: A backup of the manifest file already exists in:[" +moduleDir +"]. This usually happens when a previous test run has been terminated early. It can be fixed by just doing a full test run." );
			return;
		}
		
		String manifestPath = getManifestPath(moduleDir);
		FileUtils.copyFile(new File(manifestPath), manifestBackupFile);
	}
	
	protected static String getManifestPath(String moduleDir){
		String manifestFileLocation = getManifestFileLocation(moduleDir);
		File manifestFile = new File(manifestFileLocation);
		if(! manifestFile.exists()){
			throw new RuntimeException("The specified Manifest file doesn't exist: " + manifestFileLocation);
		}
		return manifestFileLocation;
	}

	protected static String getManifestFileLocation(String moduleDir) {
		String manifestLocation = new StringBuilder(moduleDir).append(File.separator).append(META_INF_DIR_NAME).append(File.separator).append(MANIFEST_FILE_NAME).toString();
		return manifestLocation;
	}
	
	protected static String getBackupManifestPath(String moduleDir){
		return getManifestFileLocation(moduleDir) + ".BAK";		
	}
	
	protected static void addTddDependencies(List<String> manifestContent) {
		for (int i = 0; i < manifestContent.size(); i++) {
			String manifestEntry = manifestContent.get(i);
			if(manifestEntry.startsWith(ATG_REQUIRED_PATH_ATTRIBUTE_NAME)){				
				String moduleList = manifestEntry.substring(ATG_REQUIRED_PATH_ATTRIBUTE_NAME.length());
				String newDependencyList = TDD_REQUIRED_MODULES + " " + moduleList;
				String newAtgRequired = ATG_REQUIRED_PATH_ATTRIBUTE_NAME + newDependencyList;
				manifestContent.set(i, newAtgRequired);
				return;
			}
		}
	}	

	protected static String getATGHome() {
		String dynamoRoot = findDynamoRootDir();
		if(Strings.isNullOrEmpty(dynamoRoot)){
			throw new RuntimeException("Couldn't find an ATG install. Either set a DYNAMO_HOME or ATG_HOME environment variable. Alternatively, if you don't have an ATG install, the tests can be ignored by setting the tdd.ignoreMissingATGInstall system property to true.");
		}
		return dynamoRoot;
	}

	/**
	 * Stop the specified running Nucleus.
	 * 
	 * @param nucleus - a running Nucleus.
	 */
	public static void shutdownNucleus (Nucleus nucleus, List<String> moduleList, boolean isUseTestConfigLayer) {
		if (nucleus != null) {
            try {
                NucleusTestUtils.shutdownNucleus(nucleus);
            } catch (ServiceException e) {
            	log.error(e);				
            } catch (IOException e) {
            	log.error(e);            
            }
        }
    }

	public static String findDynamoRootDir(){
		String dynamoRoot = dynamoHomeAsString();
		if(Strings.isNullOrEmpty(dynamoRoot)){
			dynamoRoot = atgHomeAsString();
		}
						
		if(! Strings.isNullOrEmpty(dynamoRoot)){
			System.setProperty("atg.dynamo.root", dynamoRoot);
		}
		return dynamoRoot;
	}
	
	public static String dynamoHomeAsString(){
		String dynamoHome = System.getenv("DYNAMO_HOME");
		if(! Strings.isNullOrEmpty(dynamoHome)){
			return dynamoHome + File.separator + ".." + File.separator;
		}
		return null;
	}


	public static String atgHomeAsString(){
		return System.getenv("ATG_HOME");		
	}

	public static boolean isATGInstallAvailable() {
		String dynamoRoot = findDynamoRootDir();
		if(Strings.isNullOrEmpty(dynamoRoot)){
			return false;
		}
		File file = new File(dynamoRoot);
		return file.exists();
	}  
	
	public static void initialiseRequestResponsePair(){
		ServletTestUtils servletTestUtils = new ServletTestUtils();
		TestingDynamoHttpServletRequest request =  servletTestUtils.createDynamoHttpServletRequestForSession(Nucleus.getGlobalNucleus(), "1234", null);
		request.setLoggingWarning(false);
		request.prepareForRead();
		ServletUtil.setCurrentRequest(request);
		ServletUtil.setCurrentResponse(servletTestUtils.createDynamoHttpServletResponse());
	}
	
	public static Object resolveComponent(String componentPath){
		ComponentName componentName = ComponentName.getComponentName(componentPath);
		Object component = null;
		try {
			DynamoHttpServletRequest currentRequest = ServletUtil.getCurrentRequest();
			if(null != currentRequest){
				component =  currentRequest.resolveName(componentName);
			}
			
		} catch (IllegalStateException e){
			component = Nucleus.getGlobalNucleus().resolveName(componentName);
		}
		return component;
	}

}
