///////*********************************************************************************//////
/////// Tests in this file require running Selendroid Server using commandline
///////*********************************************************************************//////

package com.mobileauto.test;

import io.selendroid.client.SelendroidDriver;
import io.selendroid.common.SelendroidCapabilities;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SelendroidCapabilitiesTest {
	 
      @Test()
      public void APKTest() throws Exception {
    	  SelendroidCapabilities capa = new SelendroidCapabilities("io.selendroid.testapp:0.15.0");

    	  WebDriver driver = new SelendroidDriver(capa);
    	  WebElement inputField = driver.findElement(By.id("my_text_field"));
    	  Assert.assertEquals("true", inputField.getAttribute("enabled"));
    	  inputField.sendKeys("Selendroid");
    	  Assert.assertEquals("Selendroid", inputField.getText());
    	  driver.quit();
      }
      
      @Test()
      public void MobileWeb() throws Exception {
    	  SelendroidCapabilities capa = new SelendroidCapabilities("android");
    	  WebDriver driver = new SelendroidDriver(capa);
    	  driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);    	  
    	  
//    	  WebDriver driver = new FirefoxDriver();
    	  
    	  driver.get("https://www.google.com");
    	  WebElement element1 = driver.findElement(By.name("q"));
    	  element1.click();
    	  element1.sendKeys("This is my MobileWeb Automation.");
    	  Thread.sleep(5000);
    	  driver.findElement(By.name("btnG")).click();
    	  Thread.sleep(10000);
    	  driver.findElement(By.xpath(".//*[@id='rso']/descendant::a[1]")).click();
    	  Thread.sleep(10000);
    	  Assert.assertEquals(driver.getTitle(), "The Basics Of Test Automation For Apps, Games, Mobile Web");
    	  driver.quit();
      }
      
      
}