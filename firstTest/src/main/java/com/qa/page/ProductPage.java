package com.qa.page;

import com.qa.BaseTest;
import com.qa.MenuPage;
import com.qa.util.TestUtils;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class ProductPage extends MenuPage{ 
	  @AndroidFindBy(xpath="//android.widget.ScrollView[@content-desc=\"test-PRODUCTS\"]/preceding-sibling::android.view.ViewGroup/android.widget.TextView") 
	  @iOSXCUITFindBy (xpath ="//XCUIElementTypeOther[@name=\"test-Toggle\"]/parent::*[1]/preceding-sibling::*[1]")
	  private MobileElement titlePage;  
	  
	  @AndroidFindBy(xpath="(//android.widget.TextView[@content-desc=\"test-Item title\"])[1]")  
	  @iOSXCUITFindBy (xpath = "(//XCUIElementTypeStaticText[@name=\"test-Item title\"])[1]")
	  private MobileElement titleProduct; 
	  
	  @AndroidFindBy(xpath="(//android.widget.TextView[@content-desc=\"test-Price\"])[1]")  
	  @iOSXCUITFindBy (xpath = "(//XCUIElementTypeStaticText[@name=\"test-Price\"])[1]")
	  private MobileElement priceProduct; 
	  
	  TestUtils testUtils = new TestUtils(); 
	  
public String getTitle() { 
	String title = getText(titlePage); 
	testUtils.log("Title is " + title);
	return title;  
}  


public String getProductName() { 
	String title = getText(titleProduct); 
	testUtils.log("Name product is " + title);
	return title;  
}  

public String getPriceProduct() {  
	String title = getText(priceProduct); 
	testUtils.log("Price product is " + title);
	return title; 
}   


public ProductDetailPage clickTitle() { 
	testUtils.log("Navigate to product detail page");
	click(titleProduct); 
	return new ProductDetailPage();
} 

}
