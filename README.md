atg-tdd
=======

A framework to simplify TDD with Oracle Web Commerce (ATG)

#IDEA
Test Driven Development with ATG (Oracle Web Commerce) can be difficult. The idea behind this project, was to to make writing unit tests easier and quicker, by building on the great work already done by [JUnit](https://github.com/junit-team/junit) and [ATG Dust](http://atgdust.sourceforge.net/project-info.html).

The goal is to end up with simple test classes that aren't littered with "plumbing" code, or a lot of base classes that must be understood. This project enables test classes like this:

```java
public class CurrentProfileTest {

	@Test
	public void currentUserExists() {
		assertNotNull(ServletUtil.getCurrentUserProfile());
	}
	
	@Test
	public void isStaffMember(){
		assertTrue((Boolean) ServletUtil.getCurrentUserProfile().getPropertyValue("isStaff"));
	}

}
```

Obviously, a lot of things must happen before this test can succeed. That's exactly what is provided here. 

**Note** you don't have to understand how ATG DUST works, to use this framework. 

## How it works
The following table shows the logical setup of the project:

| Module  | Description |
| ------------- | ------------- |
| Core  | This contains everything needed to write tests like the one above. It provides an annotion driven framework for unit testing. (The other modules provided are simple examples of how Core is used.)|
| MyModule  | A sample custom module, extending ATG Commerce, as a client would typically do. This is the code you'd want to write unit tests for. It's provided here to complete the example, of how to structure your modules for unit testing.  |
| Samples  | The test module, which contains the unit tests for MyModule |


### Core
TDD.Core is the module, which you plug into your ATG installation, to enable annotation driven unit testing. It contains all the code, configuration and data needed to get started. Having a module, rather than just a jar, enables us to change configuration to meet the needs of the tests. For example, the following is done:

1. Change some session scoped components to be global scope, for the tests (ShoppingCart, Profile, etc).
2. Turn off functionality that is not required during unit tests, e.g. ScenarioManager
3. Create new components specifically for unit testing (TestConfiguration, XXXTestHelper, etc)

As well as configuration, the Core module provides the following:

1. A set of custom Java annotations, which can start stop Nucleus, set up the ShoppingCart with test order, load a Profile, etc. The annotations are described below. See XXX
2. A set of sample repository data, containing a catalog, site, profile, orders, inventory, etc. This data can be extended, or changed, in your own test module. It can also be ignored completely, using configuration. See XXX
3. Custom [JUnit](https://github.com/junit-team/junit) extensions which recognise the new annotations and act on them. See XXX.

### MyModule
This is a simple module, which represents your own custom ATG module(s). It is provided as an example, to show how test modules can run unit tests against the code inside it.

### Samples
This module contains your unit tests. It has suites of unit tests, which test the functionality in MyModule. It uses all the annotations provided by Core. The MANIFEST file for this module, shows that it depends on MyModule and Core. This is typically how your tests will be structured. If you have multiple modules you want to test, then you'll want multile test modules too.

Notice how the unit tests are structured:

1. AllTests.java contains the top level test suite. It is annotated with ```@RunNucleus``` to start Nuclues with the correct modules, before the test suite runs. Nucleus is automatically stopped after the test suite by the annotation.
2. The ```@RunWith(NucleusAwareSuite.class)``` is used, so that the custom JUnit runner picks up the ```@RunNucleus``` annotation and acts on it.
3. ```@SuiteClasses({ProfileTestSuite.class ...})``` is a standard JUnit annotation, which runs all the test suites provided, as part of the overall suite.
2. The build is configured to only run test classes with a name of AllTests. This ensures we start Nucleus once, run all the tests and then stop Nucleus at the end. 

**Note** Ideally, we wouldn't need a separate module for the unit tests at all. They'd be in MyModule/src/test/java. To make that work, we'd need a custom configuration layer (e.g. ATG-testConfig-Path: testconfig) in MyModule, which would only be used during testing. However, custom layers don't seem to work in DUST. So that option isn't available. 

## Limitations
The focus of this framework is on testing global components i.e. Services, Managers, Tools, etc. That's where the vast majority of your business logic will live. Request and Session scoped components, are outside the scope of this project.










