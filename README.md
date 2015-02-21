atg-tdd
=======

A framework to simplify TDD with Oracle Web Commerce (ATG)

#Idea
I believe production code should be covered by unit tests, where practical. There's just a huge benefit to having suites of unit tests, constantly validating that our code is working as expected. Not only during the inital implementation phase but as code changes over time. The tests keep on verifying nothing is broken, build after build. This is nothing new, of course. TDD has been around for a long time and most developers, managers, etc. accept the advantages of it. 

So, why then, are unit tests regularly ignored, or half-baked at best? 

There's typically a number of reasons but the main one is, it's just too difficult to get started. Sometimes, there's just too much effort in creating the tests, to justify writing them. The bigger and more complex the platform, the more data and setup is required for each test suite. To the point where they just don't get done!

We need a mechanism, that makes it extremely easy, to write tests for projects based on ATG/Oracle Web Commerce. In other words, Test Driven Development should be painless.  

That's exactly the driving force behind this project. 

The aim is to provide an annotation driven framework, which takes care of a lot of the typical setup needed. By building on the great work already done by [JUnit](https://github.com/junit-team/junit) and [ATG Dust](http://atgdust.sourceforge.net/project-info.html), the aim is to make writing unit tests, as easy as possible. 

We can now write test classes, that look like this:

```java
@NucleusRequired(modules={"TDD.MyModule"})
@NucleusWithProfile()
@RunWith(NucleusAwareJunit4ClassRunner.class)
public class CurrentProfileTest {

	@Test
	public void currentUserExists() {
		assertNotNull(ServletUtil.getCurrentUserProfile());
	}
	
	@Test
	// Let's have a look at that extended Profile property from MyModule!
	public void isStaffMember(){
		assertTrue((Boolean) ServletUtil.getCurrentUserProfile().getPropertyValue("isStaff"));
	}

}
````

Notice there is no base class that must be extended and no setup in the test class. It has all been provided by framework.

**Note** you don't have to understand how ATG DUST works, to use this framework. 

#Structure
The following table shows the logical structure of the project:

| Module  | Description |
| ------------- | ------------- |
| Core  | This provides the annotion driven framework for unit testing. (The other modules provided are simple examples of how Core is used.)|
| MyModule  | A sample custom module, extending ATG Commerce, as a client would typically do. This is the module you'd like to unit test.


###Core
[TDD.Core](https://github.com/Roanis/atg-tdd/tree/master/Core) is the module, which you plug into your ATG installation, to enable annotation driven unit testing. It contains all the code, configuration and data needed to get started. Having a module, rather than just a jar, enables us to change configuration to meet the needs of the tests. For example, the following is done:

1. Change some session scoped components to be global scope, for the tests (ShoppingCart, Profile, etc).
2. Turn off functionality that is typically not required during unit tests, e.g. ScenarioManager
3. Create new components, to aid unit testing (TestConfiguration, TestHelper components, etc)

As well as configuration, the Core module provides the following:

1. A set of custom Java [annotations](https://github.com/Roanis/atg-tdd/tree/master/Core/src/main/java/com/roanis/tdd/annotation), which can start/stop Nucleus, set up the ShoppingCart with test order, load a Profile, etc. The annotations provide meta data about the individual test classes. For example ```@NucleusWithOrder``` will load the ShoopingCart with a sample order. By default, an order from the [test data layer](https://github.com/Roanis/atg-tdd/tree/master/Core/data) is used but a different order can be specified by passing an id to the annotation e.g. ```@NucleusWithOrder(MyOrderId)```. All of the other annotations follow a similar pattern. 
2. A set of [sample repository data](https://github.com/Roanis/atg-tdd/tree/master/Core/data), containing a catalog, site, profile, orders, inventory, etc. This data can be extended, or changed, in your own test module. It can also be ignored completely, using [configuration](https://github.com/Roanis/atg-tdd/tree/master/Core/config/atg/commerce).
3. Custom [JUnit](https://github.com/junit-team/junit) extensions which recognise the new annotations and act on them. See the [Class runner and Suite runner](https://github.com/Roanis/atg-tdd/tree/master/Core/src/main/java/com/roanis/tdd/junit4/runner). It's not important to know how they work, just that your tests should be annotated with these custom runners i.e. ```@RunWith(NucleusAwareJunit4ClassRunner.class)``` if using an annotation to set test data or ```@RunWith(NucleusAwareSuite.class)``` for the top level test suite to start Nucleus. The [samples](https://github.com/Roanis/atg-tdd/tree/master/Samples/src/test/java/com/roanis/tdd/samples) module has plenty of examples.

###MyModule
This is a simple [module](https://github.com/Roanis/atg-tdd/tree/master/MyModule), which represents your own custom ATG module(s). It is provided as an example, to show how TDD can be performed when writing code using ATG. The module contains a bunch of sample test classes, which use the annotations and data provided by Core.

Notice how the unit tests are structured:

1. Each test class has a NucleusRequired annotation. This denotes which Nucleus modules are required for testing this class. Typically, each module will "start itself", for unit testing purposes.
 
```java
@NucleusRequired(modules={"TDD.Samples"})
@RunWith(NucleusAwareJunit4ClassRunner.class)
@NucleusWithCatalog()
public class CatalogToolsTest {

    @NucleusComponent("/atg/commerce/catalog/CatalogTools")
	private CatalogTools mCatalogTools;
	
	@Test
	public void letsStartTesting(){ 
		mCatalogTools.findSku(...);
	  	....
	}

}
````

2. The ```@RunWith(NucleusAwareJunit4ClassRunner.class)``` is used, to denote that a custom JUnit runner should be invoked. This runner recognises and acts on the provided annotations, for example, starting Nuclues with the specified modules, if it's not already running.
3. The ```@NucleusWithCatalog``` annotation specifies that only catalog components need set up for this test class. There are several of these annotations provided, though most often, NucleusWithCommerce can be used to just bootstrap everything.
4. The ```@NucleusComponent``` annotation provides a mechanism to auto inject Nucleus components directly into the test class, before any tests are ran.

#Prerequisites
ATG DUST 1.2.2 is required to build this project. It should be made available in a local maven repository, following the normal naming conventions (i.e. /atg/dust/1.2.2/dust-1.2.2.jar). You can download the jar from the [DUST download page](http://sourceforge.net/projects/atgdust/). 

As per the requirement of DUST, if your ATG installation requires license files, then DUST_HOME should be set to point to the location of those files.

#Getting Started
Packaged releases of the project can be downloaded from the [releases](https://github.com/Roanis/atg-tdd/releases) section. Do the following to get up and running:

1. Download the release and extract the tdd-x.x.jar.
2. Copy the TDD folder into your ATG install under $DYNAMO_HOME/../ i.e. at the same level as the other modules (e.g. DAS, DCS, etc). 
3. Make the file Core/libs/core-x.x.jar available to your project/build. 
4. See the MyModule [build file](https://github.com/Roanis/atg-tdd/blob/master/Core/build.gradle) for which transitive dependencies are needed and add those to your project/build.
5. Start writing tests! 

Alternatively, if you want to compile the project, you can clone this repository to your hard drive. Then copy (or symlink) the TDD folder to your ATG installation under $DYNAMO_HOME/../. Now you're ready to build, so run ```gradlew clean build``` from atg-tdd folder. Both modules will be built and the tests in MyModile will be executed.

#Limitations
Whilst not really a limitation, it should be noted that the Framework expects to be started with a single module, in the ```@NucleusRequired``` annotation. This is because the testconfig layer is added only for the first module in the list. For example, if you have ```@NucleusRequired(modules={"MyModule"})``` at the top of your test class, then the Manifest for MyModule would have all it's required dependencies (otherwise your base ATG app wouldn't work anyway :) ). By starting MyModule, you will automatically pull in all the other dependencies, so there is no need to list multiple modules in the annotation.

#Supported ATG Versions
| TDD Version  | ATG Version |
| ------------- | ------------- |
| 1.0  | 10.2|
| 1.1, 1.2  | 11.0, 11.1|
| 1.3, 1.4  | 10.2, 11.0, 11.1|







