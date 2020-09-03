package com.qa.page;

import com.qa.BaseTest;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class LoginPage extends BaseTest{ 
	  @AndroidFindBy(accessibility="test-Username") private MobileElement userNameTxtFld;   
	  @AndroidFindBy(accessibility="test-Password") private MobileElement passwordTextFld;  
	  @AndroidFindBy(accessibility="test-LOGIN") private MobileElement buttonLogin;  
	  @AndroidFindBy(xpath="//android.view.ViewGroup[@content-desc=\"test-Error message\"]/android.widget.TextView") private MobileElement err;  
	  

public LoginPage enterUserName(String userName) {
	sendkeys( userNameTxtFld, userName); 
	return this;
}  

public LoginPage enterPassword(String password) {
	sendkeys( passwordTextFld, password); 
	return this;
}   

public ProductPage pressLoginBtn() {
	click(buttonLogin); 
	return new ProductPage();
}  

public String errTxt() {
	return getAttribute(err, "text");
}

}
