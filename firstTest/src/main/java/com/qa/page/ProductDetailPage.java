package com.qa.page;

import com.qa.BaseTest;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class ProductDetailPage extends BaseTest{ 

	  @AndroidFindBy(xpath="//android.view.ViewGroup[@content-desc=\"test-Description\"]/android.widget.TextView[1]") 
	  private MobileElement titleProduct; 
	  
	  @AndroidFindBy(xpath="//android.view.ViewGroup[@content-desc=\"test-Description\"]/android.widget.TextView[2]") 
	  private MobileElement detailProduct; 
	  
	  @AndroidFindBy(accessibility="test-BACK TO PRODUCTS")  
	  private MobileElement backBtn; 
	  
	  
public String getProductTitle() {
	return getText(titleProduct); 
}  


public String getProductDesc() {
	return getText(detailProduct); 
}  

public ProductPage backToProduct() {
	click(backBtn); 
	return new ProductPage();
}   

}
