package com.qa;



import com.qa.page.SettingPage;
import com.qa.util.TestUtils;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class MenuPage extends BaseTest {
	@AndroidFindBy(xpath="//android.view.ViewGroup[@content-desc=\"test-Menu\"]/android.view.ViewGroup/android.widget.ImageView")  
	@iOSXCUITFindBy (xpath="//XCUIElementTypeOther[@name=\"test-Menu\"]/XCUIElementTypeOther")
	private MobileElement settingBtn; 
	
	  TestUtils testUtils = new TestUtils(); 
	  
	public SettingPage pressBtn() {  
		testUtils.log("Press setting button");
		click(settingBtn); 
		return new SettingPage(); 
	}
	
}
