package com.qa.page;

import com.qa.BaseTest;

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
	  

public LoginPage enterUserName(String userName) {  
	System.out.println("Login with: " + userName);
	clear(userNameTxtFld);
	sendkeys( userNameTxtFld, userName);  
	return this;
}  

public LoginPage enterPassword(String password) {   
	System.out.println("Login with: " + password);
	clear(passwordTextFld);
	sendkeys( passwordTextFld, password);  
	return this;
}   

public ProductPage pressLoginBtn() { 
	System.out.println("Press login button");
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
