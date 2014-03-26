Selenium 2 Utils
================

This small Java project contains some useful methods for Selenium 2 / WebDriver. I created this methods while my three years work with this nice open source testing framework.

Public and Protected
--------------------

Protected methods are only for PageObjects. Your PageObject classes extends from Functions.java and can use the methods, but not in tests.

Public methods are for PageObjects and tests. In tests you can use the methods about the PageObject class you use.

Notes
-----

Selenium 2 / WebDriver Version: `2.35.0`

I learned...
------------

- how to use the PageObject pattern of Selenium 2 / WebDriver.
- as it is to work with an big open source project. (Incl. advantages and disadvantages.)
- as it is to develop GUI tests for a website whose GUI regular changed.
- how to develop GUI tests with acceptance criterias.
- JUnit (incl. features like `TestWatcher`, `Parameters` and `ErrorCollector`).
- Maven (incl. interaction with Jenkins).
- Notice: That all deals with my three years experience above mentioned and not only this simple utils class.
