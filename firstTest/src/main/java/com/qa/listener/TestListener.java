package com.qa.listener;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.junit.Assert;
import org.testng.ITestListener;
import org.testng.ITestResult; 

//CLass of TestNG, customize lai thanh cua minh
public class TestListener implements ITestListener { 
	
	public void onTestFailure(ITestResult result) {
		if(result.getThrowable() != null) {
			  StringWriter sw = new StringWriter(); 
			  PrintWriter pw = new PrintWriter(sw); 
			  result.getThrowable().printStackTrace(pw); 
			  System.out.println("Error is " + sw.toString());
		}
	}
}
