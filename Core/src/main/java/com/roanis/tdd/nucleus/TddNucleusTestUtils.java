package com.roanis.tdd.nucleus;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import atg.nucleus.Nucleus;
import atg.nucleus.NucleusTestUtils;
import atg.nucleus.NucleusTestUtils.NucleusStartupOptions;
import atg.nucleus.ServiceException;

import com.google.common.base.Strings;

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
	 * @throws Exception - if an error occurred starting Nucleus
	 */
	public static Nucleus startNucleus (List<String> moduleList, boolean isUseTestConfigLayer, Class<?> testClass) throws Exception {	
		System.out.println("TDD by Rory Curtis: rory_curtis@roanis.com");
        if ((null == moduleList) || (0 == moduleList.size())) {
            throw new Exception("A module list must be specfied when starting Nucleus.");
        }
        
        if(isUseTestConfigLayer){
        	enhanceManifestFiles(moduleList);
        }
        
        // Fire up Nucleus - make sure DYNAMO_HOME and DUST_HOME are set.
        String initialComponent = "/atg/dynamo/Configuration";
        NucleusStartupOptions startupOptions = new NucleusStartupOptions(moduleList.toArray(new String[0]), testClass, initialComponent);        

        Nucleus nucleus = NucleusTestUtils.startNucleusWithModules(startupOptions);

        if (null == nucleus) {
            throw new Exception("Unable to start Nucleus for unit tests.");
        }
                
        return nucleus;
    }
	
	
	public static void enhanceManifestFiles(List<String> moduleList) throws IOException {
		String atgHome = getATGHome();
		for (String moduleName : moduleList) {
			String moduleDir = atgHome + moduleName.replace(".", File.separator);
			addTestConfigToManifest(moduleDir);
		}
	}

	protected static void addTestConfigToManifest(String moduleDir) throws IOException {
		if (Strings.isNullOrEmpty(moduleDir)){
			throw new IllegalArgumentException("A module directory must be specified when adding a test config layer.");
		}
		backupManifestFile(moduleDir);
		updateManifest(moduleDir);		
	}

	protected static boolean testConfigLayerExists(String moduleDir) {
		String testConfigPath = moduleDir + File.separator + TEST_CONFIG_LAYER_NAME;
		File testConfigDir = new File(testConfigPath);
		if(testConfigDir.exists() && testConfigDir.isDirectory()){
			return true;
		}
				
		return false;
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
	
	protected static void updateManifest(String moduleDir){
		String manifestPath = getManifestPath(moduleDir);
		File manifestFile = new File(manifestPath);
		Charset charset = Charset.forName("UTF-8");
		 
		try {
			List<String> content = FileUtils.readLines(manifestFile, charset);
			updateATGConfigPath(content);
			addTddDependencies(content);
			FileUtils.writeLines(manifestFile, content);			
		} catch (IOException e) {
			restoreManifestFile(moduleDir);
			throw new RuntimeException("Unable to add test config layer to Manifest file: " + manifestPath.toString(), e);
		}		
	}	

	protected static void updateATGConfigPath(List<String> manifestContent) {		
		for (int i = 0; i < manifestContent.size(); i++) {
			String manifestEntry = manifestContent.get(i);
			if(manifestEntry.startsWith(ATG_CONFIG_PATH_ATTRIBUTE_NAME)){
				String newConfigPath = manifestEntry + " " + TEST_CONFIG_LAYER_NAME;
				manifestContent.set(i, newConfigPath);
				return;
			}
		}
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

	protected static void restoreManifestFile(String moduleDir) {
		if(! testConfigLayerExists(moduleDir)){
			return;
		}
		
		File manifestFile = new File(getManifestPath(moduleDir));
		File backupManifestFile = new File(getBackupManifestPath(moduleDir));
		try {
			FileUtils.copyFile(backupManifestFile, manifestFile);
			backupManifestFile.delete();
		} catch (IOException e) {
			System.out.println("ERROR: unable to restore manifest files for module:["+moduleDir+"]. Please review the manifest files in this directory.");
		}
	}
	
	public static void restoreAllManifestFiles(List<String> moduleList) {
		String atgHome = getATGHome();
		for (String moduleName : moduleList) {
			String moduleDir = atgHome + moduleName.replace(".", File.separator);
			restoreManifestFile(moduleDir);
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
		if(isUseTestConfigLayer){
			restoreAllManifestFiles(moduleList);		
		}
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

	protected static void removeTestConfigLayer(List<String> moduleList) {
	}
	
	public static String findDynamoRootDir(){
		String dynamoRoot = fromDynamoHome();
		if(Strings.isNullOrEmpty(dynamoRoot)){
			dynamoRoot = fromATGHome();
		}
						
		if(! Strings.isNullOrEmpty(dynamoRoot)){
			System.setProperty("atg.dynamo.root", dynamoRoot);
		}
		return dynamoRoot;
	}
	
	private static String fromDynamoHome(){
		String dynamoHome = System.getenv("DYNAMO_HOME");
		if(! Strings.isNullOrEmpty(dynamoHome)){
			return dynamoHome + File.separator + ".." + File.separator;
		}
		return null;
	}


	private static String fromATGHome(){
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

}
