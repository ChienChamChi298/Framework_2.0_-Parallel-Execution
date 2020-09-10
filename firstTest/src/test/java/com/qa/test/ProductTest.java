package com.qa.test;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.qa.BaseTest;
import com.qa.page.LoginPage;
import com.qa.page.ProductDetailPage;
import com.qa.page.ProductPage;
import com.qa.page.SettingPage;

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

public class ProductTest extends BaseTest { 
  LoginPage loginPage; 
  ProductPage productPage;
  SettingPage settingPage;
  ProductDetailPage detailPage; 
  
  InputStream dataIs;
  JSONObject loginUser;
  
  @BeforeClass 
  public void beforeClass() throws Exception { 
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
	  productPage = loginPage.login(loginUser.getJSONObject("vaildUser").getString("userName"), loginUser.getJSONObject("vaildUser").getString("password"));
  }

  @AfterMethod
  public void afterMethod() { 
	  settingPage = productPage.pressBtn(); 
	  loginPage = settingPage.pressBtn();
  } 
  
  @Test
  public void  validateProductOnProductPage() {  
	  
	  SoftAssert sa = new SoftAssert(); 
	  
	  String actualTitle = productPage.getProductName();
	  sa.assertEquals(actualTitle, strings.get("product_page_name_detail"));  
	  
	  String actualPrice = productPage.getPriceProduct(); 
	  sa.assertEquals(actualPrice, strings.get("product_page_price_detail"));   
	  
	  
	  sa.assertAll(); 
  } 
  
  @Test
  public void  validateProductOnProductDetailPage() {  
	  
	  SoftAssert sa = new SoftAssert();
	  
	  detailPage = productPage.clickTitle();
	  String actualTitle = detailPage.getProductTitle();
	  sa.assertEquals(actualTitle, strings.get("product_detail_name"));  
	  
	  detailPage.scrollToPrice();
	  
	  String actualDesc = detailPage.getProductDesc();
	  sa.assertEquals(actualDesc, strings.get("product_detail_desc"));    

	  String actualPrice = detailPage.getPrice();
	  sa.assertEquals(actualPrice, strings.get("product_detail_price"));
	  
	  productPage = detailPage.backToProduct(); 
	  
//Scroll for IOS 
//	  detailPage.iOSScrollToElement();   
//	  sa.assertTrue(detailPage.isAddToCartBtnDisplayed()); 
	  
	  sa.assertAll(); 
  }
  

}
