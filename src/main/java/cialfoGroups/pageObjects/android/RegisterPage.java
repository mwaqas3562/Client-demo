package cialfoGroups.pageObjects.android;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import cialfoGroups.utils.AndroidActions;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class RegisterPage extends AndroidActions{
	AndroidDriver driver;
	public RegisterPage(AndroidDriver driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}
	
	@AndroidFindBy(xpath="//android.widget.EditText[@hint='First name']")
	private WebElement firstNameField;
	
	@AndroidFindBy(xpath="//android.widget.EditText[@hint='Last name']")
	private WebElement lastNameField;
	
	@AndroidFindBy(xpath="//android.widget.EditText[@hint='yourname@school.com']")
	private WebElement EmailField;
	
	@AndroidFindBy(xpath="//android.widget.EditText[@hint='13123456789']")
	private WebElement phoneNumberField;
	
	@AndroidFindBy(xpath="//android.widget.EditText[@hint='●●●●●●●●']")
	private List<WebElement> passwordFields;
	
	@AndroidFindBy(xpath="/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.widget.ScrollView/android.view.ViewGroup/android.view.ViewGroup[10]/android.widget.TextView")
	private WebElement termsAndPrivacyCheckbox;
	
	@AndroidFindBy(xpath="/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.widget.ScrollView/android.view.ViewGroup/android.view.ViewGroup[11]")
	public WebElement createAccountButton;
	
	public void setFirstName(String firstName)
	{
		scrollToText("First name");
		firstNameField.sendKeys(firstName);
	}
	
	public void setLastName(String lastName)
	{
		scrollToText("Last name");
		lastNameField.sendKeys(lastName);
	}
	
	public void setEmail(String email)
	{
		scrollToText("Email");
		EmailField.sendKeys(email);
	}
	
	public void setPassword(String password)
	{
		scrollToText("Password");
		passwordFields.get(0).sendKeys(password);	
	}
	
	public void setConfirmPassword(String password)
	{
		scrollToText("Confirm password");
		passwordFields.get(1).sendKeys(password);	
	}
	
	public void acceptTermsPrivacy()
	{
		scrollToText("I agree to Cialfo's Terms of Service and Privacy Policy");
//		if(termsAndPrivacyCheckbox.getAttribute("checked") != "true")
//		{
//			termsAndPrivacyCheckbox.click();			
//		}
		termsAndPrivacyCheckbox.click();
	}
	
	public void uncheckTermsPrivacy()
	{
		scrollToText("I agree to Cialfo's Terms of Service and Privacy Policy");
		if(termsAndPrivacyCheckbox.getAttribute("checked") == "true")
		{
			termsAndPrivacyCheckbox.click();			
		}
	}
	
	public void submitForm()
	{
		scrollToText("Create my account");
		createAccountButton.click();
	}
	
	public boolean checkSubmitButtonEnabled()
	{
		scrollToText("Create my account");
		return createAccountButton.isEnabled();
	}
	
}
