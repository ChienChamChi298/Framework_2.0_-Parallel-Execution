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
	  
	  @AndroidFindBy(xpath="//android.widget.TextView[@content-desc=\"test-Item title\"])[1]") 
	  private MobileElement titleProduct; 
	  
	  @AndroidFindBy(xpath="//android.widget.TextView[@content-desc=\"test-Price\"])[1]") 
	  private MobileElement priceProduct; 
	  
	  
public String getTitle() {
	return getText(titlePage); 
}  


public String getProductName() {
	return getText(titleProduct); 
}  

public String getPriceProduct() {
	return getText(priceProduct); 
}   


public ProductDetailPage clickTitle() {
	click(titlePage); 
	return new ProductDetailPage();
} 

}
