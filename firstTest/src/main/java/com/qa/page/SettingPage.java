package com.qa.page;

import com.qa.BaseTest;
import com.qa.util.TestUtils;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class SettingPage extends BaseTest{ 
	@AndroidFindBy(accessibility="test-LOGOUT")  
	@iOSXCUITFindBy (id = "test-LOGOUT")
	private MobileElement logOutTextLink; 
	
	  TestUtils testUtils = new TestUtils(); 
	  
	public LoginPage pressBtn() {  
		testUtils.log("Press logout text link");
		click(logOutTextLink); 
		return new LoginPage(); 
	}

}
