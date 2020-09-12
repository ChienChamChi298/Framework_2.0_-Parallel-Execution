package com.qa.test;

import org.testng.annotations.Test;

import com.qa.BaseTest;
import com.qa.page.LoginPage;
import com.qa.page.ProductPage;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.Assert;
import org.testng.annotations.AfterClass;

public class LoginTest extends BaseTest { 
  LoginPage loginPage; 
  ProductPage productPage;
  JSONObject loginUser;
  
  @BeforeClass 
  public void beforeClass() throws Exception {  
	  InputStream dataIs = null;
	  try { 
	  String filePath = "data/LoginUser.json"; 
	  dataIs = getClass().getClassLoader().getSystemResourceAsStream(filePath);  
	  JSONTokener tokener = new JSONTokener(dataIs); 
	  loginUser = new JSONObject(tokener); 
	  } catch (Exception e) {
		  e.printStackTrace();
	  } finally {
		if (dataIs != null) {
			  dataIs.close();
		  }
	  } 
	  closeApp(); 
	  launchApp();
  }
  
  @BeforeMethod
  public void beforeMethod(Method m) { 
	  loginPage = new LoginPage(); 
	  System.out.println("\n" + "******* Starting test: " + m.getName() + " ******** " + "\n");
	  
  }

  @AfterMethod
  public void afterMethod() {
  } 
  
  @Test
  public void invaildUser() { 
	  loginPage.enterUserName(loginUser.getJSONObject("invaildUser").getString("userName")); 
	  loginPage.enterPassword(loginUser.getJSONObject("invaildUser").getString("password")); 
	  loginPage.pressLoginBtn();  
	  String actualErr = loginPage.errTxt(); 
	  String expectErr = getStrings().get("invalid_username_or_password");/*"Username and password do not match any user in this service.";*/
	  System.out.println("Actual err : "+ actualErr + "\n" + "Expect err : " + expectErr); 
	  Assert.assertEquals(expectErr, actualErr);
  }
 
  @Test
  public void invaildPassword() { 
	  loginPage.enterUserName(loginUser.getJSONObject("invaildPassword").getString("userName")); 
	  loginPage.enterPassword(loginUser.getJSONObject("invaildPassword").getString("password")); 
	  loginPage.pressLoginBtn();  
	  String actualErr = loginPage.errTxt(); 
	  String expectErr = getStrings().get("invalid_username_or_password");/*"Username and password do not match any user in this service."*/; 
	  System.out.println("Actual err : "+ actualErr + "\n" + "Expect err : " + expectErr); 
	  Assert.assertEquals(expectErr, actualErr);
  }
  
  @Test
  public void successLogin() { 
	  loginPage.enterUserName(loginUser.getJSONObject("vaildUser").getString("userName")); 
	  loginPage.enterPassword(loginUser.getJSONObject("vaildUser").getString("password")); 
	  productPage = loginPage.pressLoginBtn();  
	  String actualTitle = productPage.getTitle(); 
	  String expectTitle = getStrings().get("product_title");/*"PRODUCTS"*/;
	  System.out.println("Actual title : "+ actualTitle + "\n" + "Expect title : " + expectTitle); 
	  Assert.assertEquals(expectTitle, actualTitle);
  }
}
