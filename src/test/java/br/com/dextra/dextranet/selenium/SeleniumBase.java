package br.com.dextra.dextranet.selenium;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;

public class SeleniumBase {
	
	protected static WebDriver browser;
	
	@BeforeClass
	public void setupTestsEnvironment() {
		//TODO: launch mycontainer
		//TODO: instantiate webdriver
	}
	
	@Before
	public void resetTestsEnvironment() {
		//TODO: do something such as logout
		//TODO: reset database
	}
	
	@AfterClass
	public void shutdownTestsEnvironment() {
		//TODO: shutdown mycontainer
	}
}
