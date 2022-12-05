package cialfoGroups.pageObjects.android;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import cialfoGroups.utils.AndroidActions;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class LoginPage extends AndroidActions{
	AndroidDriver driver;
	public LoginPage(AndroidDriver driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}
	
	@AndroidFindBy(xpath="//android.widget.EditText[@hint='Enter your email or phone number']")
	public WebElement emailOrPhoneNumberField;
	
	@AndroidFindBy(xpath="//android.widget.EditText[@hint='●●●●●●●●']")
	private WebElement passwordField;
	
	@AndroidFindBy(xpath="//android.widget.TextView[@text='Login']")
	private WebElement loginButton;
	
	@AndroidFindBy(xpath="//android.widget.TextView[@text=''Forgot password?]")
	private WebElement forgotPasswordLink;
	
	@AndroidFindBy(xpath="//android.widget.TextView[@text='Continue with Google']")
	private WebElement googleLoginButton;
	
	@AndroidFindBy(xpath="//android.widget.TextView[@text='Sign up for account']")
	private WebElement signUpButton;
	
	@AndroidFindBy(xpath="//android.widget.TextView[@text='Email and password combination is wrong. If you are a guardian, please login through the web application.']")
	public WebElement loginFailMessage;
	
	@AndroidFindBy(xpath="/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.ScrollView/android.widget.LinearLayout/android.widget.Button")
	public WebElement loginFailOkButton;
	
	public void clickSignupButton()
	{
		scrollToText("Sign up for account");
		signUpButton.click();	
	}
	
	public void clickLoginButton()
	{
		loginButton.click();
	}
	
	public void setEmailOrPhone(String email)
	{
		emailOrPhoneNumberField.sendKeys(email);
		driver.hideKeyboard();
	}
	
	public void setPassword(String password)
	{
		passwordField.sendKeys(password);
		driver.hideKeyboard();
	}
}
