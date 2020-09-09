package com.qa.page;

import com.qa.BaseTest;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class SettingPage extends BaseTest{ 
	@AndroidFindBy(accessibility="test-LOGOUT") 
	private MobileElement logOutTextLink; 
	
	
	public LoginPage pressBtn() {  
		System.out.println("Press logout text link");
		click(logOutTextLink); 
		return new LoginPage(); 
	}

}
