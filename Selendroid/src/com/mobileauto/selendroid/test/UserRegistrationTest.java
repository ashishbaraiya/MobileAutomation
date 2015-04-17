package com.mobileauto.selendroid.test;

import io.selendroid.client.SelendroidDriver;
import io.selendroid.common.SelendroidCapabilities;
import io.selendroid.standalone.SelendroidConfiguration;
import io.selendroid.standalone.SelendroidLauncher;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

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
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}

	@Test
	public void assertUserAccountCanRegistered() throws Exception {
		UserDO user = new UserDO("abaraiya", "ashish@test.com", "password", "Ashish Baraiya", "Python");

		registerUser(user);
		verifyUser(user);
	}

	private void registerUser(UserDO user) throws Exception {
		driver.get("and-activity://io.selendroid.testapp.RegisterUserActivity");

		WebElement username = driver.findElement(By.id("inputUsername"));
		Thread.sleep(3000);
		username.sendKeys(user.username);

		driver.findElement(By.name("email of the customer")).sendKeys(user.email);
		driver.findElement(By.id("inputPassword")).sendKeys(user.password);

		WebElement nameInput = driver.findElement(By.xpath("//EditText[@id='inputName']"));
		Assert.assertEquals(nameInput.getText(), "Mr. Burns");
		nameInput.clear();
		nameInput.sendKeys(user.name);

		driver.findElement(By.tagName("Spinner")).click();
		Thread.sleep(3000);
		driver.findElement(By.linkText(user.programmingLanguage)).click();


		Thread.sleep(3000);
		TouchActions taCheckbox = new TouchActions(driver);
		WebElement checkBoxButton = driver.findElement(By.className("android.widget.CheckBox"));
		Thread.sleep(3000);
		taCheckbox.flick(checkBoxButton , 0, -200, 0).perform();
		Thread.sleep(3000);
		checkBoxButton.click();

		while (!checkBoxButton.isSelected())
		{
			checkBoxButton.click();
		}

		Thread.sleep(3000);
		TouchActions taRegisterButton = new TouchActions(driver);
		WebElement registerButton = driver.findElement(By.linkText("Register User (verify)"));
		taRegisterButton.flick(registerButton , 0, -200, 0).perform();
		Thread.sleep(3000);    
		registerButton.click();
		Thread.sleep(4000);

		try
		{
			while(registerButton.isDisplayed() == true)
			{
				registerButton.click();
				Thread.sleep(3000);
			}
		}
		catch(Exception e)     
		{       
		}       

		Assert.assertEquals(driver.getCurrentUrl(), "and-activity://VerifyUserActivity");
	}

	private void verifyUser(UserDO user) throws Exception 
	{
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