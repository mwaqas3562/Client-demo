package cialfoGroups.pageObjects.android;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import cialfoGroups.utils.AndroidActions;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class WelcomePage extends AndroidActions{
	AndroidDriver driver;
	public WelcomePage(AndroidDriver driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}
	
	@AndroidFindBy(id="android:id/alertTitle")
	public WebElement updateAlertTitle;
	
	@AndroidFindBy(id="android:id/button1")
	private WebElement updateAlertContinueBtn;
	
	@AndroidFindBy(xpath="//android.widget.TextView[@text='Downloading Update...']")
	private WebElement updateProgressAlert;
	
	@AndroidFindBy(xpath="//android.widget.EditText[@hint='Riverdale High School']")
	private WebElement instituteNameInputField; 
	
	@AndroidFindBy(xpath="//android.view.ViewGroup")
	private List<WebElement> instituteSuggestions;
	
	public void clickUpdateContinueButton()
	{
		updateAlertContinueBtn.click();
	}
	
	public boolean isProgressPopupVisible()
	{
		return updateProgressAlert.isDisplayed();
	}
	
	public void fillInstituteInputField(String key)
	{
		instituteNameInputField.click();
		instituteNameInputField.sendKeys(key);
		driver.hideKeyboard();
	}
	
	public void selectInstituteSuggestion(String key, int index)
	{
		getElementsIncludingText(key).get(index+1).click();
	}
	
	public int getNumberOfSuggestions(String key)
	{
		return getElementsIncludingText(key).size();
	}
	
}
