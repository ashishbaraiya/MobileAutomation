///////*********************************************************************************//////
/////// Tests in this file doesn't require running Selendroid Server using commandline
///////*********************************************************************************//////

package com.mobileauto.selendroid.test;

import io.selendroid.client.SelendroidDriver;
import io.selendroid.common.SelendroidCapabilities;
import io.selendroid.standalone.SelendroidConfiguration;
import io.selendroid.standalone.SelendroidLauncher;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SelendroidIntegrationTest {
  private static SelendroidLauncher selendroidServer = null;
  private static WebDriver driver = null;

  @Test
  public void selendroidServerTest() {
    WebElement inputField = driver.findElement(By.id("my_text_field"));
    inputField.sendKeys("Mobile Automation Test with Selendroid Server.");
    Assert.assertEquals("Mobile Automation Test with Selendroid Server.", inputField.getText());
  }

  @BeforeClass
  public static void startSelendroidServer() throws Exception {
    if (selendroidServer != null) {
      selendroidServer.stopSelendroid();
    }
    SelendroidConfiguration config = new SelendroidConfiguration();
    config.addSupportedApp("lib/selendroid-test-app-0.15.0.apk");
    selendroidServer = new SelendroidLauncher(config);
    selendroidServer.launchSelendroid();

    SelendroidCapabilities caps =
        new SelendroidCapabilities("io.selendroid.testapp:0.15.0");

    driver = new SelendroidDriver(caps);
  }

  @AfterClass
  public static void stopSelendroidServer() {
    if (driver != null) {
      driver.quit();
    }
    if (selendroidServer != null) {
      selendroidServer.stopSelendroid();
    }
  }

}