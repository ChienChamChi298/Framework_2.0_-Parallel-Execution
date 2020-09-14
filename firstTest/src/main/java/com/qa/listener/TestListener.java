package com.qa.listener;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.OutputType;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.qa.BaseTest;
import com.qa.util.TestUtils; 

//CLass of TestNG, customize lai thanh cua minh
public class TestListener implements ITestListener {  
	TestUtils testUtils = new TestUtils(); 
	public void onTestFailure(ITestResult result) {
		if(result.getThrowable() != null) {
			  StringWriter sw = new StringWriter(); 
			  PrintWriter pw = new PrintWriter(sw); 
			  result.getThrowable().printStackTrace(pw); 
			  testUtils.log("Error is " + sw.toString());
		} 
		 
		//Chup anh man hinh
		BaseTest baseTest = new BaseTest(); 
		File file = baseTest.getDriver().getScreenshotAs(OutputType.FILE);  
		
		Map<String, String> params = new HashMap<String, String>(); 
		params = result.getTestContext().getCurrentXmlTest().getAllParameters(); 
		
		String imagePath = "Screenshot" + File.separator + params.get("platformName") + "_" + params.get("platformVersion") + "_" + params.get("deviceName") 
		+ File.separator + baseTest.getDateTime() + File.separator + result.getTestClass() +  File.separator + result.getName() + ".png"; 
		
		String completeImagePath = System.getProperty("user.dir") + File.separator + imagePath;
		
		try {
			FileUtils.copyFile(file, new File(imagePath));  
			
			//Chen anh vao FILE test-output/emailable-report.html
			Reporter.log("This is image"); 
			Reporter.log("<a href='"+ completeImagePath + "'><img src='"+ completeImagePath+"' height=500 width=500 /></a>");
		
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}  
		
		
		
		
	    
		
	}
}
