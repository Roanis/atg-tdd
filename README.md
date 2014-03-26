atg-tdd
=======

A framework to simplify TDD with Oracle Web Commerce (ATG)

#IDEA
Test Driven Development with ATG (Oracle Web Commerce) can be difficult. The idea behind this project, was to to make writing unit tests easier and quicker, by building on the good work already done by [ATG Dust](http://atgdust.sourceforge.net/project-info.html).

**Note** you don't have to understand how ATG DUST works, to use this framework. 

## The Problem
To enable proper TDD with ATG, i.e. writing tests before production code, there's a lot of plumbing that may need to happen:

1. Start Nucleus with the correct modules.
2. Seed repositories with sample test data e.g. site, catalog, profile, inventory, price lists, orders, etc.
3. Mimick a user session, so that a current order, profile, site, etc. can be retrieved.
4. Start a transaction before a test and roll it back afterwards, so that the data is restored (and consistent) between tests
5. Tear down the data set up in 3. so that each test starts from a known state.

The goal of this framework, is to take care of all the above plumbing, so that you can get on with the job of writing tests for your own code.

## Limitations
The focus of this framework is on testing global components i.e. Services, Managers, Tools, etc. That's where the vast majority of your business logic will (or should) live. Request and Session scoped components, are outside the scope of this project.

*Some common ATG session components have been "forced" to global scope, so that they're accessible to the tests. They are described later.*








