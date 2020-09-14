package com.qa.page;

import com.qa.BaseTest;
import com.qa.util.TestUtils;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class LoginPage extends BaseTest{ 
	  @AndroidFindBy(accessibility="test-Username")  
	  @iOSXCUITFindBy(id="test-Username")
	  private MobileElement userNameTxtFld;    
	  
	  
	  @AndroidFindBy(accessibility="test-Password")  
	  @iOSXCUITFindBy(id="test-Password")
	  private MobileElement passwordTextFld; 
	  
	  
	  @AndroidFindBy(accessibility="test-LOGIN")  
	  @iOSXCUITFindBy(id="test-LOGIN")
	  private MobileElement buttonLogin;   
	  
	  @AndroidFindBy(xpath="//android.view.ViewGroup[@content-desc=\"test-Error message\"]/android.widget.TextView")  
	  @iOSXCUITFindBy (xpath = "//XCUIElementTypeOther[@name=\"test-Error message\"]/child::XCUIElementTypeStaticText")
	  private MobileElement err;  
	  
	  TestUtils testUtils = new TestUtils();

public LoginPage enterUserName(String userName) {  
	testUtils.log("Login with username: " + userName);
	clear(userNameTxtFld);
	sendkeys( userNameTxtFld, userName);  
	return this;
}  

public LoginPage enterPassword(String password) {   
	testUtils.log("Login with password: " + password);
	clear(passwordTextFld);
	sendkeys( passwordTextFld, password);  
	return this;
}   

public ProductPage pressLoginBtn() { 
	testUtils.log("Press login button");
	click(buttonLogin);  
	return new ProductPage();
}  

public String errTxt() {
	return getText(err);
}

public ProductPage login(String userName, String password) {  
	enterUserName(userName); 
	enterPassword(password); 
	return pressLoginBtn();
}

}
