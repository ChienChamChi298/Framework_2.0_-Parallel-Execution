package com.qa.page;

import com.qa.BaseTest;
import com.qa.util.TestUtils;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class ProductDetailPage extends BaseTest{ 

	  @AndroidFindBy(xpath= "//android.view.ViewGroup[@content-desc=\"test-Description\"]/android.widget.TextView[1]")  
	  @iOSXCUITFindBy (xpath = "//XCUIElementTypeOther[@name=\"test-Description\"]/child::XCUIElementTypeStaticText[1]")
	  private MobileElement titleProduct; 
	  
	  @AndroidFindBy(xpath=  "//android.view.ViewGroup[@content-desc=\"test-Description\"]/android.widget.TextView[2]")  
	  @iOSXCUITFindBy (xpath = "//XCUIElementTypeOther[@name=\"test-Description\"]/child::XCUIElementTypeStaticText[2]")
	  private MobileElement detailProduct; 
	  
	  @AndroidFindBy(accessibility="test-BACK TO PRODUCTS")  
	  private MobileElement backBtn;  
	  
	  @AndroidFindBy(accessibility="test-Price")   
	  private MobileElement price;
	  
	  @iOSXCUITFindBy (id = "test-ADD TO CART")
	  private MobileElement addToCart; 
	  
	  TestUtils testUtils = new TestUtils(); 
	  
public String getProductTitle() { 
	String title = getText(titleProduct); 
	testUtils.log("Name product is " + title);
	return title; 
}  


public String getProductDesc() { 
	
	String title = getText(detailProduct); 
	testUtils.log("Description product is " + title);
	return title;  
}  

public ProductPage backToProduct() { 
	testUtils.log("Navigate back to product page");
	click(backBtn); 
	return new ProductPage();
}    

public String getPrice() { 
	String title = getText(price); 
	testUtils.log("Price product is " + title);
	return title;  
} 

public ProductDetailPage scrollToPrice() {
	scrollElement(); 
	return this;
} 

//IOS
public void scrollPageIOS() {
	iOSScrollToElement();
} 

public Boolean isAddToCartBtnDisplayed() { 
	return addToCart.isDisplayed();
}

}
