package com.qa.page;

import com.qa.BaseTest;
import com.qa.MenuPage;

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
	  
	  
public String getTitle() { 
	String title = getText(titlePage); 
	System.out.println("Title is " + title);
	return title;  
}  


public String getProductName() { 
	String title = getText(titleProduct); 
	System.out.println("Name product is " + title);
	return title;  
}  

public String getPriceProduct() {  
	String title = getText(priceProduct); 
	System.out.println("Price product is " + title);
	return title; 
}   


public ProductDetailPage clickTitle() { 
	System.out.println("Navigate to product detail page");
	click(titleProduct); 
	return new ProductDetailPage();
} 

}
