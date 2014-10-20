package com.roanis.tdd.nucleus;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

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
		String testConfigDir = moduleDir + File.separator + TEST_CONFIG_LAYER_NAME;
		Path testConfigPath = Paths.get(testConfigDir);
		if(Files.exists(testConfigPath) && Files.isDirectory(testConfigPath)){
			return true;
		}
				
		return false;
	}

	protected static void backupManifestFile(String moduleDir) throws IOException {		
		Path manifestBackupPath = getBackupManifestPath(moduleDir);	
		
		if(Files.exists(manifestBackupPath)){
			System.out.println("WARN: A backup of the manifest file already exists in:[" +moduleDir +"]. This usually happens when a previous test run has been terminated early. It can be fixed by just doing a full test run." );
			return;
		}
		
		Path manifestPath = getManifestPath(moduleDir);
		Files.copy(manifestPath, manifestBackupPath);
	}
	
	protected static Path getManifestPath(String moduleDir){
		String manifestFileLocation = getManifestFileLocation(moduleDir);
		Path manifestPath = Paths.get(manifestFileLocation);
		if(! Files.exists(manifestPath)){
			throw new RuntimeException("The specified Manifest file doesn't exist: " + manifestFileLocation);
		}
		return manifestPath;
	}

	protected static String getManifestFileLocation(String moduleDir) {
		String manifestLocation = new StringBuilder(moduleDir).append(File.separator).append(META_INF_DIR_NAME).append(File.separator).append(MANIFEST_FILE_NAME).toString();
		return manifestLocation;
	}
	
	protected static Path getBackupManifestPath(String moduleDir){
		String backupManifestFileLocation = getManifestFileLocation(moduleDir) + ".BAK";
		Path backupPath = Paths.get(backupManifestFileLocation);		
		return backupPath;
	}
	
	protected static void updateManifest(String moduleDir){
		Path manifestPath = getManifestPath(moduleDir);
		Charset charset = StandardCharsets.UTF_8;
		 
		try {
			List<String> content = Files.readAllLines(manifestPath, charset);
			updateATGConfigPath(content);
			addTddDependencies(content);
			Files.write(manifestPath, content, charset);
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
		
		Path manifestPath = getManifestPath(moduleDir);
		Path backupManifestPath = getBackupManifestPath(moduleDir);
		try {
			Files.move(backupManifestPath, manifestPath, StandardCopyOption.REPLACE_EXISTING);
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
		String dynamoHome = System.getenv("DYNAMO_HOME");
		if(Strings.isNullOrEmpty(dynamoHome)){
			throw new RuntimeException("The DYNAMO_HOME environment variable is not set.");
		}
		return dynamoHome + File.separator + ".." + File.separator;
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

}
