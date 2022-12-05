package cialfoGroups.pageObjects.android;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import cialfoGroups.utils.AndroidActions;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class ProfilePage extends AndroidActions{
	
	AndroidDriver driver;
	public ProfilePage(AndroidDriver driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}
	
	@AndroidFindBy(xpath="//android.widget.TextView[@text='Log out']")
	private WebElement logoutButton;
	
	public void logout()
	{
		scrollToText("Log out");
		logoutButton.click();
	}

}
