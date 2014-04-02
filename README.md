atg-tdd
=======

A framework to simplify TDD with Oracle Web Commerce (ATG)

#Idea
I believe production code should be covered by unit tests, where practical. There's just a huge benefit to having suites of unit tests, constantly validating that our code is working as expected. Not only during the inital implementation phase but as code changes over time. The tests keep on verifying nothing is broken, build after build. This is nothing new, of course. TDD has been around for a long time and most developers, managers, etc. accept the advantages of it. 

So, why then, are unit tests regularly ignored, or half-baked at best? 

There's typically a number of reasons but the main one is, it's just too difficult to get started. Sometimes, there's just too much effort in creating the tests, to justify writing them. The bigger and more complex the platform, the more data and setup is required for each test suite. To the point where they just don't get done!

We need a mechanism, that makes it extremely easy, to write tests for projects based on ATG/Oracle Web Commerce. In other words, Test Driven Development should be painless.  

That's exactly the driving force behind this project. 

The aim is to provide an annotation driven framework, which takes care of a lot of the typical setup needed. By building on the great work already done by [JUnit](https://github.com/junit-team/junit) and [ATG Dust](http://atgdust.sourceforge.net/project-info.html), the aim is to make writing unit tests, as easy as possible. Ideally, we'd like to write test classes like this:

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

**Note** you don't have to understand how ATG DUST works, to use this framework. 

#Structure
The following table shows the logical structure of the project:

| Module  | Description |
| ------------- | ------------- |
| Core  | This provides the annotion driven framework for unit testing. (The other modules provided are simple examples of how Core is used.)|
| MyModule  | A sample custom module, extending ATG Commerce, as a client would typically do. This is the code you'd want to write unit tests for. It's provided here to complete the example, of how to structure your modules for unit testing.  |
| Samples  | The module, which contains the unit tests for MyModule |


###Core
TDD.Core is the module, which you plug into your ATG installation, to enable annotation driven unit testing. It contains all the code, configuration and data needed to get started. Having a module, rather than just a jar, enables us to change configuration to meet the needs of the tests. For example, the following is done:

1. Change some session scoped components to be global scope, for the tests (ShoppingCart, Profile, etc).
2. Turn off functionality that is typically not required during unit tests, e.g. ScenarioManager
3. Create new components, to aid unit testing (TestConfiguration, XXXTestHelper, etc)

As well as configuration, the Core module provides the following:

1. A set of custom Java annotations, which can start stop Nucleus, set up the ShoppingCart with test order, load a Profile, etc. The annotations are described below. See XXX
2. A set of sample repository data, containing a catalog, site, profile, orders, inventory, etc. This data can be extended, or changed, in your own test module. It can also be ignored completely, using configuration. See XXX
3. Custom [JUnit](https://github.com/junit-team/junit) extensions which recognise the new annotations and act on them. See XXX.

###MyModule
This is a simple module, which represents your own custom ATG module(s). It is provided as an example, to show how test modules can run unit tests against the code inside it.

###Samples
This module contains a bunch of sample test suites, which use the annotations and data provided by Core. It's a reference for how you'd typically write unit tests, for your own module (MyModule in this case). 

The MANIFEST file, shows that it depends on MyModule and Core. This is typically how your tests will be structured. If you have multiple modules you want to test, then you'll want multile test modules too.

Notice how the unit tests are structured:

1. AllTests.java contains the top level test suite. It is annotated with ```@RunNucleus``` to start Nuclues with the correct modules, before the test suite runs. Each test suite is executed and then Nucleus is automatically stopped by the annotation.
 
```java
@RunNucleus(modules={"TDD.Samples"})
@RunWith(NucleusAwareSuite.class)
@SuiteClasses({ProfileTestSuite.class, SiteTestSuite.class, CatalogTestSuite.class, PriceListTestSuite.class, InventoryTestSuite.class, OrderTestSuite.class})
public class AllTests {

}
````

2. The ```@RunWith(NucleusAwareSuite.class)``` is used, so that the custom JUnit runner picks up the ```@RunNucleus``` annotation and acts on it.
3. ```@SuiteClasses({ProfileTestSuite.class ...})``` is a standard JUnit annotation, which runs all the test suites provided, as part of the overall suite.
4. The build is configured to only run test classes with a name of AllTests. This ensures we start Nucleus once, run all the tests and then stop Nucleus at the end. 

**Note** Ideally, we wouldn't need a separate module for the unit tests at all. They'd be in MyModule/src/test/java. To make that work, we'd need a custom configuration layer (e.g. ATG-testConfig-Path: testconfig) in MyModule, which would only be used during testing. However, custom layers don't seem to work in DUST. So that option isn't available. 

#Prerequisites
ATG DUST 1.2.2 is required to build this project. It should be made available in a local maven repository, following the normal naming conventions (i.e. /atg/DUST/1.2.2/DUST-1.2.2.jar). You can download the jar from the [DUST download page](http://sourceforge.net/projects/atgdust/). 

**Note**, you may need the pom.xml file too, to pull in all the required dependencies for DUST.

As per the requirement of DUST, if your ATG installation requires license files, then DUST_HOME should be set to point to the location of those files.

#Getting Started
Simply clone this repository to your hard drive. Then copy (or symlink) the TDD folder to your ATG installation under $DYNAMO_HOME/../. Now you're ready to build, so run ```gradlew install``` from TDD folder. Everything should build and the tests in Samples will be executed.

Obviously, when writing tests for your own ATG module, you don't need Samples and MyModule, so they can be removed.

#Limitations
The focus of this framework is on testing global components i.e. Services, Managers, Tools, etc. That's where the vast majority of your business logic will live. Request and Session scoped components, are outside the scope of this project.

#Supported ATG Versions
| TDD Version  | ATG Version |
| ------------- | ------------- |
| 1.x  | 10.2|








