package cialfoGroups.RegisterTests;

import java.time.Duration;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import cialfoGroups.pageObjects.android.DashboardPage;
import cialfoGroups.pageObjects.android.LoginPage;
import cialfoGroups.pageObjects.android.OnboardingPages;
import cialfoGroups.pageObjects.android.ProfilePage;
import cialfoGroups.pageObjects.android.RegisterPage;
import cialfoGroups.pageObjects.android.WelcomePage;
import cialfoGroups.testUtils.AndroidBaseTest;

public class testWithoutOnboarding extends AndroidBaseTest{
	String firstNameValue;
	String lastNameValue;
	String emailValue;
	String passwordValue;
	@Test(priority=0)
	@Parameters({"instituteName"})
	public void REGISTER_SELECT_INSTITUTE(@Optional("te") String instituteName) throws InterruptedException
	{
		WelcomePage welcomePage = new WelcomePage(driver);
		Thread.sleep(5000);
		try {
			if(welcomePage.updateAlertTitle.isDisplayed())
			{
				welcomePage.clickUpdateContinueButton();
				Boolean isProgressButtonVisible = welcomePage.isProgressPopupVisible();
				try {
					do {
						isProgressButtonVisible = welcomePage.isProgressPopupVisible();
					}while(isProgressButtonVisible);
				} catch (Exception e) {
					
				}
			}
		} catch (Exception e) {
			
		}
		welcomePage.fillInstituteInputField(instituteName);
		Assert.assertTrue(welcomePage.getNumberOfSuggestions(instituteName)>1);
		welcomePage.selectInstituteSuggestion(instituteName, 0);

		LoginPage loginPage = new LoginPage(driver);
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(15));
		wait.until(ExpectedConditions.visibilityOf(loginPage.emailOrPhoneNumberField));
		driver.hideKeyboard();
		loginPage.clickSignupButton();
	}
	
	@Test(priority=1)
	@Parameters({"firstName", "lastName", "email", "password"})
	public void REGISTER_WITHOUT_INPUT(@Optional("first") String firstName, @Optional("last") String lastName, @Optional("")String email, @Optional("12345678") String password)
	{
		firstNameValue = firstName;
		lastNameValue = lastName;
		emailValue = (email.length()==0) ? getRandomEmail():email ;
		passwordValue = password;
		RegisterPage registerPage = new RegisterPage(driver);
		Assert.assertFalse(registerPage.checkSubmitButtonEnabled());
	}
	
	@Test(priority=2)
	public void REGISTER_WITHOUT_EMAIL()
	{
		RegisterPage registerPage = new RegisterPage(driver);
		registerPage.setFirstName(firstNameValue);
		registerPage.setLastName(lastNameValue);
		registerPage.setEmail("");
		registerPage.setPassword(passwordValue);
		registerPage.setConfirmPassword(passwordValue);
		registerPage.acceptTermsPrivacy();
		Assert.assertFalse(registerPage.checkSubmitButtonEnabled());
	}
	@Test(priority=3)
	public void REGISTER_WITHOUT_PASSWORD()
	{
		RegisterPage registerPage = new RegisterPage(driver);
		registerPage.setEmail(emailValue);
		registerPage.setPassword("");
		registerPage.setConfirmPassword("");
		Assert.assertFalse(registerPage.checkSubmitButtonEnabled());
	}
	
	@Test(priority=4)
	public void REGISTER_WITH_UNEQUAL_PASSWORD()
	{
		RegisterPage registerPage = new RegisterPage(driver);
		registerPage.setPassword(passwordValue);
		registerPage.setConfirmPassword(passwordValue+"1");
		Assert.assertTrue(registerPage.getElementsIncludingText("Password and confirm password does not match").size() > 0);
		Assert.assertFalse(registerPage.checkSubmitButtonEnabled());
	}
	@Test(priority=5)
	public void REGISTER_LENGTH_LESS_THAN_8_PASSWORD()
	{
		RegisterPage registerPage = new RegisterPage(driver);
		registerPage.setPassword("12345");
		registerPage.setConfirmPassword("12345");
		Assert.assertTrue(registerPage.getElementsIncludingText("Password must be at least 8 characters long").size() > 0);
		Assert.assertFalse(registerPage.checkSubmitButtonEnabled());
	}
	
	@Test(priority=6)
	public void REGISTER_WIHOUT_TERMS_CONDITION()
	{
		RegisterPage registerPage = new RegisterPage(driver);
		registerPage.setPassword(passwordValue);
		registerPage.setConfirmPassword(passwordValue);
		registerPage.acceptTermsPrivacy();
		Assert.assertTrue(registerPage.getElementsIncludingText("Password and confirm password does not match").size() == 0);
		Assert.assertTrue(registerPage.getElementsIncludingText("Password must be at least 8 characters long").size() == 0);
		Assert.assertFalse(registerPage.checkSubmitButtonEnabled());
	}
	
	@Test(priority=7)
	public void REGISTER_WITH_SPACES_IN_FIRSTNAME_LASTNAME()
	{
		RegisterPage registerPage = new RegisterPage(driver);
		registerPage.setFirstName("   ");
		registerPage.setLastName("   ");
		registerPage.acceptTermsPrivacy();
		Assert.assertFalse(registerPage.checkSubmitButtonEnabled());
	}
	
	@Test(priority=8)
	public void REGISTER_WITH_INVALID_EMAIL()
	{
		RegisterPage registerPage = new RegisterPage(driver);
		registerPage.setEmail("email@");
		Assert.assertTrue(registerPage.getElementsIncludingText("Please enter a valid email address").size() > 0);
		Assert.assertFalse(registerPage.checkSubmitButtonEnabled());
	}
	
	@Test(priority=9)
	public void REGISTER_WITH_POSITIVE_FLOW()
	{
		RegisterPage registerPage = new RegisterPage(driver);
		registerPage.setEmail(emailValue);
		registerPage.setFirstName(firstNameValue);
		registerPage.setLastName(lastNameValue);
		Assert.assertTrue(registerPage.checkSubmitButtonEnabled());
		registerPage.submitForm();
	}
}