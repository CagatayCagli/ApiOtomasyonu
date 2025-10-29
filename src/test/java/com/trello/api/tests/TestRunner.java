package com.trello.api.tests;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

/**
 * Test runner class for running all Trello API tests
 * This class can be used to run all test classes together
 */
@Suite
@SelectClasses({TrelloApiTest.class})
public class TestRunner {
    // This class serves as a test suite runner
    // All test classes are selected via @SelectClasses annotation
}
