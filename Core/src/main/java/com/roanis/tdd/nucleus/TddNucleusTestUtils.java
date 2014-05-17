package com.roanis.tdd.nucleus;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;

import atg.nucleus.Nucleus;
import atg.nucleus.NucleusTestUtils;
import atg.nucleus.NucleusTestUtils.NucleusStartupOptions;
import atg.nucleus.ServiceException;

/**
 * Utility methods for working with Nucleus.
 * 
 * @author rory
 *
 */
public class TddNucleusTestUtils {
	public static final Logger log = Logger.getLogger(TddNucleusTestUtils.class);
	
	/**
	 * Start Nucleus with the specified module list.
	 * 
	 * @param moduleList - the list of ATG modules to start
	 * @param testClass - the test class or suite which is invoking this method
	 * @return - the running Nucleus
	 * @throws Exception - if an error occurred starting Nucleus
	 */
	public static Nucleus startNucleus (List<String> moduleList, Class<?> testClass) throws Exception {	
		System.out.println("TDD by Rory Curtis: rory_curtis@roanis.com");
        if ((null == moduleList) || (0 == moduleList.size())) {
            throw new Exception("A module list must be specfied when starting Nucleus.");
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
	
	/**
	 * Stop the specified running Nucleus.
	 * 
	 * @param nucleus - a running Nucleus.
	 */
	public static void shutdownNucleus (Nucleus nucleus) {
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

}
