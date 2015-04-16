package com.mobileauto.test;

import io.selendroid.client.SelendroidDriver;
import io.selendroid.common.SelendroidCapabilities;
import io.selendroid.standalone.SelendroidConfiguration;
import io.selendroid.standalone.SelendroidLauncher;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Base Test to demonstrate how to test native android apps with Selendroid. App under test is:
 * src/main/resources/selendroid-test-app-0.10.0.apk
 * 
 * @author ddary
 */
public class UserRegistrationTest {
  private WebDriver driver = null;
  private static SelendroidLauncher selendroidServer = null;

  @BeforeClass
  public void setup() throws Exception {
	  
	if (selendroidServer != null) {
	selendroidServer.stopSelendroid();
	}
	SelendroidConfiguration config = new SelendroidConfiguration();
	config.addSupportedApp("lib/selendroid-test-app-0.15.0.apk");
	selendroidServer = new SelendroidLauncher(config);
	selendroidServer.launchSelendroid();
	    
    driver = new SelendroidDriver(new SelendroidCapabilities("io.selendroid.testapp:0.15.0"));
  }

  @Test
  public void assertUserAccountCanRegistered() throws Exception {
    // Initialize test data
    UserDO user = new UserDO("u$erNAme1", "me@myserver.com", "mySecret", "John Doe", "Python");

    registerUser(user);
    verifyUser(user);
  }

  private void registerUser(UserDO user) throws Exception {
    driver.get("and-activity://io.selendroid.testapp.RegisterUserActivity");

    WebElement username = driver.findElement(By.id("inputUsername"));
    Thread.sleep(3000);
    username.sendKeys(user.username);

    driver.findElement(By.name("email of the customer")).sendKeys(user.email);
//    Thread.sleep(3000);
    driver.findElement(By.id("inputPassword")).sendKeys(user.password);

    WebElement nameInput = driver.findElement(By.xpath("//EditText[@id='inputName']"));
    Assert.assertEquals(nameInput.getText(), "Mr. Burns");
    nameInput.clear();
    nameInput.sendKeys(user.name);

    driver.findElement(By.tagName("Spinner")).click();
    Thread.sleep(3000);
    driver.findElement(By.linkText(user.programmingLanguage)).click();

    Thread.sleep(3000);
//    TouchActions taCheckbox = new TouchActions(driver);
//    WebElement checkBoxButton = driver.findElement(By.className("android.widget.CheckBox"));
    driver.findElement(By.className("android.widget.CheckBox")).click();

//    taCheckbox.flick(checkBoxButton , 0, -200, 0).perform();
//    checkBoxButton.click();
//    driver.findElement(By.id("input_adds")).click();
    
    Thread.sleep(3000);
    TouchActions taRegisterButton = new TouchActions(driver);
    WebElement registerButton = driver.findElement(By.linkText("Register User (verify)"));
    taRegisterButton.flick(registerButton , 0, -200, 0).perform();
    
    registerButton.click();
//    driver.findElement(By.className("android.widget.Button")).click();
//    driver.findElement(By.linkText("Register User (verify)")).click();
//    driver.findElement(By.id("btnRegisterUser")).click();
    
    Thread.sleep(5000);
    Assert.assertEquals(driver.getCurrentUrl(), "and-activity://VerifyUserActivity");
  }

  private void verifyUser(UserDO user) throws Exception {
    Assert.assertEquals(driver.findElement(By.id("label_username_data")).getText(), user.username);
    Assert.assertEquals(driver.findElement(By.id("label_email_data")).getText(), user.email);
    Assert.assertEquals(driver.findElement(By.id("label_password_data")).getText(), user.password);
    Assert.assertEquals(driver.findElement(By.id("label_name_data")).getText(), user.name);
    Assert.assertEquals(driver.findElement(By.id("label_preferedProgrammingLanguage_data"))
        .getText(), user.programmingLanguage);
    Assert.assertEquals(driver.findElement(By.id("label_acceptAdds_data")).getText(), "true");
  }

  @AfterClass
  public void teardown() {
    driver.quit();
  }
}