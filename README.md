atg-tdd
=======

A framework to simplify TDD with Oracle Web Commerce (ATG)

#IDEA
Test Driven Development with ATG (Oracle Web Commerce) can be difficult. The idea behind this project, was to to make writing unit tests easier and quicker, by building on the good work already done by [ATG Dust](http://atgdust.sourceforge.net/project-info.html).

**Note** you don't have to understand how ATG DUST works, to use this framework. 

## The Problem
To enable proper TDD with ATG, i.e. writing tests before production code, there's a lot of plumbing in the test layer, that may need to happen:

1. Start Nucleus with the correct modules.
2. Seed repositories with sample test data e.g. site, catalog, profile, inventory, price lists, orders, etc.
3. Set up data and components, so that a current order, profile, site, etc. can be retrieved.
4. Start a transaction before a test and roll it back afterwards, so that the data is restored (and consistent) between tests
5. Tear down the data set up in 3. so that each test starts from a known state.

The goal of this framework, is to take care of all the above plumbing, so that you can get on with the job of writing tests for your own code.

## The Solution
The approach taken to solve the issues above, is as follows:

1. A set of custom annotations, which solve 1,3,4 and 5 in the problem description above
2. A set of sample repository data which solves 2 above.
3. Custom [JUnit](https://github.com/junit-team/junit) extensions which recognise the new annotations and act on them.

## Limitations
The focus of this framework is on testing global components i.e. Services, Managers, Tools, etc. That's where the vast majority of your business logic will (or should) live. Request and Session scoped components, are outside the scope of this project.

*Some common ATG session components have been "forced" to global scope, so that they're accessible to the tests. They are described later.*








