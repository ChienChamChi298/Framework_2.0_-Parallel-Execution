package com.qa;

import org.testng.annotations.Test; 

import com.qa.util.TestUtils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.FindsByAndroidUIAutomator;
import io.appium.java_client.InteractsWithApps;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod; 

import io.appium.java_client.screenrecording.CanRecordScreen;

public class BaseTest {
 protected static AppiumDriver driver;
 protected static Properties props;  
 protected static String platform; 
 protected static String dateTime; 
 
 InputStream inputStream; 
 InputStream stringIs;
 TestUtils testUltils;  
 
 protected static HashMap <String, String> strings = new HashMap <String, String>();
 
 public BaseTest() {
	 PageFactory.initElements(new AppiumFieldDecorator(driver), this);
 }
 
 
 @BeforeMethod   
 public void beforeMethod() { 
	 System.out.println("Before method base test");
	 ((CanRecordScreen) driver).startRecordingScreen();
 } 
 
 @AfterMethod 
 public void afterMethod(ITestResult result) { 
	 System.out.println("After method base test");
	 String media = ((CanRecordScreen) driver).stopRecordingScreen(); 
	 
	 //Record video khi testcase fail
	 if (result.getStatus() == 2 ) { 
		 Map <String, String> params = result.getTestContext().getCurrentXmlTest().getAllParameters();
		 
		 String dir = "Video" + File.separator + params.get("platformName") + "_" + params.get("platformVersion") + "_" + params.get("deviceName") + File.separator + 
				      dateTime + File.separator + result.getTestClass().getClass().getSimpleName();  
		 System.out.println( result.getTestClass()); 
		 System.out.println( result.getTestClass().getClass());
		 
		 File videoDir = new File(dir); 
		 
		 if (!videoDir.exists()) { 
			 videoDir.mkdirs();
		 } 
		 
		 try {
			 
			FileOutputStream stream = new FileOutputStream(videoDir + File.separator + result.getName() + ".mp4"); 
			stream.write(Base64.decodeBase64(media)); 
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }

 }
 
 @Parameters({"platformName", "platformVersion", "deviceName", "emulator", "udid"})
  @BeforeTest
  public void beforeTest(String platformName, String platformVersion , String deviceName, String emulator, String udid) throws Exception  {  
	 	testUltils = new  TestUtils(); 
	 	dateTime = testUltils.getDateTime();
	 	URL url;
	    try {  
            platform = platformName;
	    	props = new Properties(); 
	    	String propFileName="config.properties"; 
	    	String xmlString = "stringExpect/string.xml";
	    	
	    	inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
	    	props.load(inputStream);
	    	
	    	stringIs = getClass().getClassLoader().getResourceAsStream(xmlString); 
	    	strings = testUltils.parseStringXML(stringIs);
	    	
	    	DesiredCapabilities  capabilities = new DesiredCapabilities(); 
	   	
			//Cach 1
			//capabilities.setCapability("app",  "C:\\Users\\CSM\\Downloads\\Android.SauceLabs.Mobile.Sample.app.2.3.0.apk");  
			
			//Cach 2 
			//String urlApp = getClass().getResource(props.getProperty("androidAppLocation")).getFile(); 
			//capabilities.setCapability("app",  urlApp);   
			//Explain why ? 
			
		
			capabilities.setCapability("platformName", platformName);
		
			capabilities.setCapability("deviceName", deviceName); 
            switch(platformName) {
	            case "Android" :    
	            	
	            	String urlAppAndroid = getClass().getResource(props.getProperty("androidAppLocation")).getFile();    
	            	String appLocation = new File(urlAppAndroid).toString(); // String above include '\' at first character so we want remove it 
	            	System.out.println("Absolute path file apk is " + appLocation);
	            	capabilities.setCapability("app",  appLocation); 
	            	
					capabilities.setCapability("automationName", props.getProperty("androidAutomationName"));   
					capabilities.setCapability("appActivity", props.getProperty("androidAppActivity"));
					capabilities.setCapability("appPackage",  props.getProperty("androidAppPackage"));   
					
					if (emulator.equalsIgnoreCase("true")) { 
						//capabilities.setCapability("avd", deviceName);   
						capabilities.setCapability("platformVersion", platformVersion);
					} else { 
						capabilities.setCapability("udid", udid); 
					} 

					url = new URL("http://127.0.0.1:4723/wd/hub");  
					driver = new AndroidDriver(url, capabilities); 
					break;  
					
	            case "iOS" :  
	            	// Install app
	            	//String urlAppIOS = getClass().getResource(props.getProperty("iOSAppLocation")).getFile();  
	            	//capabilities.setCapability("app",  urlAppIOS);
	            	
	            	//If installed app, just use app 
	            	capabilities.setCapability("bundleId",  props.getProperty("iOSBundleId")); 
	            	
	            	capabilities.setCapability("platformVersion", platformVersion);
					capabilities.setCapability("automationName", props.getProperty("iOSAutomationName"));   
					url = new URL("http://127.0.0.1:4723/wd/hub");  
					driver = new IOSDriver(url, capabilities);  
					break ; 
					
				default :  
					throw new Exception("Invaild platform " + platformName);  
		    }  
          
		} catch (Exception e) {
	    	e.printStackTrace();
	    } finally {
	    	if (inputStream != null) {
	    		inputStream.close();
	    	} 
	    	
	    	if (stringIs != null) {
	    		stringIs.close();
	    	}
	    }
} 
 
 public void waitForVisibility(MobileElement e) {
	 WebDriverWait wait = new WebDriverWait(driver, TestUtils.WAIT);
	 wait.until(ExpectedConditions.visibilityOf(e));
	 
 } 
 
 public void click(MobileElement e) { 
	 	waitForVisibility(e); 
	 	e.click();
 } 
 
 public void sendkeys(MobileElement e, String txt) {
	 	waitForVisibility(e); 
	 	e.sendKeys(txt);
}
 
public String getAttribute(MobileElement e, String attribute) {
 	    waitForVisibility(e); 
 		return e.getAttribute(attribute);
}
 
public String getText(MobileElement e) { 
	switch(platform) {
	case "Android" : 
		return getAttribute(e, "text");  
	case "iOS" : 
		return getAttribute(e, "label"); 
	} 
	return null;
}

public void clear(MobileElement e) { 
	waitForVisibility(e); 
	e.clear();
}
public void closeApp() {
	((InteractsWithApps)driver).closeApp(); // Exit app
}  

public void launchApp() {
	((InteractsWithApps)driver).launchApp(); // Launch app
}

public MobileElement scrollElement() {
	return (MobileElement) ((FindsByAndroidUIAutomator) driver).findElementByAndroidUIAutomator( 
		"new UiScrollable(new UiSelector()" + ".description(\"test-Inventory item page\")).scrollIntoView(" + "new UiSelector().description(\"test-Price\"));");
			
//	return (MobileElement) ((FindsByAndroidUIAutomator) driver).findElementByAndroidUIAutomator( 
//		"new UiScrollable(new UiSelector()" + ".scrollable(true).scrollIntoView(" + "new UiSelector().description(\"test-Price\"));"); //if only one parent emelent can scroll
} 


public void iOSScrollToElement() {
	  RemoteWebElement parent = (RemoteWebElement)driver.findElement(By.className("XCUIElementTypeScrollView"));
	  String parentID = parent.getId();
	  HashMap<String, String> scrollObject = new HashMap<String, String>();
	  scrollObject.put("element", parentID);
	  scrollObject.put("predicateString", "label == 'ADD TO CART'");
	  driver.executeScript("mobile:scroll", scrollObject);
}  


public AppiumDriver getDriver() {
	return driver;
} 

public String getDateTime() {
	return dateTime;
}

  @AfterTest
  public void afterTest() { 
	  driver.quit();
  }
  
 
}
