package com.qa.listener;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.OutputType;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.qa.BaseTest; 

//CLass of TestNG, customize lai thanh cua minh
public class TestListener implements ITestListener { 
	
	public void onTestFailure(ITestResult result) {
		if(result.getThrowable() != null) {
			  StringWriter sw = new StringWriter(); 
			  PrintWriter pw = new PrintWriter(sw); 
			  result.getThrowable().printStackTrace(pw); 
			  System.out.println("Error is " + sw.toString());
		} 
		
		BaseTest baseTest = new BaseTest(); 
		File file = baseTest.getDriver().getScreenshotAs(OutputType.FILE);  
		
		String imagePath = "Screenshots"
		
		try {
			FileUtils.copyFile(file, new File("Sample.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
