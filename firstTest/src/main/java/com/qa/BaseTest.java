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
import org.testng.annotations.Optional;
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
 protected static ThreadLocal <AppiumDriver> driver = new  ThreadLocal <AppiumDriver>();
 protected static  ThreadLocal <Properties> props = new  ThreadLocal <Properties>();  
 protected static ThreadLocal <String> platform = new  ThreadLocal <String>(); 
 protected static ThreadLocal <String> dateTime = new  ThreadLocal <String>(); 
 protected static ThreadLocal <HashMap <String, String>> strings = new ThreadLocal <HashMap <String, String>>();

 TestUtils testUltils;  

 
public AppiumDriver getDriver() {
	return driver.get();
}  
 
public void setDriver(AppiumDriver driver2) {
	driver.set(driver2);
}

public String getDateTime() {
	return dateTime.get();
} 

public void setDateTime(String date) {
	 dateTime.set(date);
}  

public Properties getProperties () {
	return props.get();
}

public void setProperties(Properties prop) {
	props.set(prop);
} 

public String getPlatform () {
	return platform.get();
}

public void setPlatform(String plat) {
	platform.set(plat);
}

public  HashMap <String, String> getStrings() {
	return strings.get();
}

public void setStrings( HashMap <String, String> str) {
	strings.set(str);
}




 public BaseTest() {
	 PageFactory.initElements(new AppiumFieldDecorator(getDriver()), this);
 }
 
 
 @BeforeMethod   
 public void beforeMethod() { 
	 System.out.println("Before method base test");
	 ((CanRecordScreen) getDriver()).startRecordingScreen();
 } 
 
 @AfterMethod 
//synchronized = chúng ta muốn cho phép chỉ một luồng được truy cập vào tài nguyên chia sẻ đó. 
// nó sẽ tự động khóa cho đối tượng đó và giải phóng nó khi luồng hoàn thành nhiệm vụ.
 public synchronized void afterMethod(ITestResult result) { 
	 System.out.println("After method base test");
	 String media = ((CanRecordScreen) getDriver()).stopRecordingScreen(); 
	 
	 //Record video khi testcase fail
	 if (result.getStatus() == 2 ) { 
		 Map <String, String> params = result.getTestContext().getCurrentXmlTest().getAllParameters();
		 
		 String dir = "Video" + File.separator + params.get("platformName") + "_" + params.get("platformVersion") + "_" + params.get("deviceName") + File.separator + 
				      getDateTime() + File.separator + result.getTestClass().getClass().getSimpleName();  
		 System.out.println( result.getTestClass()); 
		 System.out.println( result.getTestClass().getClass());
		 
		 File videoDir = new File(dir);  
		 
		 synchronized(videoDir) {
			 if (!videoDir.exists()) { 
				 videoDir.mkdirs();
			 } 
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
 
 @Parameters({"platformName", "platformVersion", "deviceName", "emulator", "udid", "systemPort", "chromeDriverPort", "wdaLocalport", "webkitDebugProxyPort"})
  @BeforeTest
  public void beforeTest(String platformName, String platformVersion , String deviceName, @Optional("androidOnly")String emulator, String udid, @Optional("androidOnly")String systemPort, @Optional("androidOnly")String chromeDriverPort, @Optional("iOSOnly")String wdaLocalPort, @Optional("iOSOnly")String webkitDebugProxyPort) throws Exception  {  
	 	testUltils = new  TestUtils(); 
	 	
	 	setDateTime(testUltils.getDateTimeUtils());
	    setPlatform(platformName);  
	    
	    Properties props = new Properties(); 
	    AppiumDriver driver; 
	    
	 	InputStream inputStream = null; 
	 	InputStream stringIs = null; 
	 	
	 	URL url;
	    try {  
            setProperties(props);
	    	String propFileName="config.properties"; 
	    	String xmlString = "stringExpect/string.xml";
	    	inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
	    	props.load(inputStream);
	    	
	    	stringIs = getClass().getClassLoader().getResourceAsStream(xmlString); 
	    	setStrings(testUltils.parseStringXML(stringIs));
	    	
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
						capabilities.setCapability("avd", deviceName);   
						capabilities.setCapability("platformVersion", platformVersion); 
						capabilities.setCapability("avdLaunchTimeout", 120000);
					} else { 
						capabilities.setCapability("udid", udid); 
					}  
					
					capabilities.setCapability("systemPort",  systemPort); 
					capabilities.setCapability("chromeDriverPort",  chromeDriverPort);
					if (deviceName == "Nexus_5X_API_29") {
						url = new URL(props.getProperty("appiumURL") + "4724/wd/hub"); 
					} else {
						url = new URL(props.getProperty("appiumURL") + "4723/wd/hub"); 
					}
		
					driver = new AndroidDriver(url, capabilities); 
					break;    
				
	            case "Android_2" :    
	            	
	            	String urlAppAndroid2 = getClass().getResource(props.getProperty("androidAppLocation")).getFile();    
	            	String appLocation2 = new File(urlAppAndroid2).toString(); // String above include '\' at first character so we want remove it 
	            	System.out.println("Absolute path file apk is " + appLocation2);
	            	capabilities.setCapability("app",  appLocation2); 
	            	
					capabilities.setCapability("automationName", props.getProperty("androidAutomationName"));   
					capabilities.setCapability("appActivity", props.getProperty("androidAppActivity"));
					capabilities.setCapability("appPackage",  props.getProperty("androidAppPackage"));   
					
					
					if (emulator.equalsIgnoreCase("true")) { 
						capabilities.setCapability("avd", deviceName);   
						capabilities.setCapability("platformVersion", platformVersion);
					} else { 
						capabilities.setCapability("udid", udid); 
					}  
					
					capabilities.setCapability("systemPort",  systemPort); 
					capabilities.setCapability("chromeDriverPort",  chromeDriverPort);
					
					url = new URL(props.getProperty("appiumURL") + "4723/wd/hub");  
					driver = new AndroidDriver(url, capabilities); 
					break;   
					
					
	            case "iOS" :  
	            	//Install app
	            	String urlAppIOS = getClass().getResource(props.getProperty("iOSAppLocation")).getFile();  
	            	capabilities.setCapability("app",  urlAppIOS);
	            	
	            	//If installed app, just use app 
	            	capabilities.setCapability("bundleId",  props.getProperty("iOSBundleId"));  
	            	
	            	capabilities.setCapability("webkitDebugProxyPort",  webkitDebugProxyPort); 
	            	capabilities.setCapability("wdaLocalPort",  wdaLocalPort); 
	            	
	            	capabilities.setCapability("platformVersion", platformVersion);
					capabilities.setCapability("automationName", props.getProperty("iOSAutomationName"));   
					url = new URL(props.getProperty("appiumURL"));  
					driver = new IOSDriver(url, capabilities);  
					break ; 
					
				default :  
					throw new Exception("Invaild platform " + platformName);  
		    }  
            setDriver(driver);
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
	 WebDriverWait wait = new WebDriverWait(getDriver(), TestUtils.WAIT);
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
	switch(getPlatform()) {
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
	((InteractsWithApps) getDriver()).closeApp(); // Exit app
}  

public void launchApp() {
	((InteractsWithApps) getDriver()).launchApp(); // Launch app
}

public MobileElement scrollElement() {
	return (MobileElement) ((FindsByAndroidUIAutomator) getDriver()).findElementByAndroidUIAutomator( 
		"new UiScrollable(new UiSelector()" + ".description(\"test-Inventory item page\")).scrollIntoView(" + "new UiSelector().description(\"test-Price\"));");
			
//	return (MobileElement) ((FindsByAndroidUIAutomator) driver).findElementByAndroidUIAutomator( 
//		"new UiScrollable(new UiSelector()" + ".scrollable(true).scrollIntoView(" + "new UiSelector().description(\"test-Price\"));"); //if only one parent emelent can scroll
} 


public void iOSScrollToElement() {
	  RemoteWebElement parent = (RemoteWebElement)getDriver().findElement(By.className("XCUIElementTypeScrollView"));
	  String parentID = parent.getId();
	  HashMap<String, String> scrollObject = new HashMap<String, String>();
	  scrollObject.put("element", parentID);
	  scrollObject.put("predicateString", "label == 'ADD TO CART'");
	  getDriver().executeScript("mobile:scroll", scrollObject);
}  


 
  @AfterTest
  public void afterTest() { 
	  getDriver().quit();
  }
  
 
}
