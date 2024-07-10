package com.appstack.wishlist.cucumber;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;

//@Suite
@IncludeEngines("cucumber")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.appstack.wishlist.cucumber.stepDefs")
public class CucumberTest {}