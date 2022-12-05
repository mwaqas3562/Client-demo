package cialfoGroups.RegisterTests;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.google.common.collect.ImmutableMap;

import cialfoGroups.pageObjects.android.DashboardPage;
import cialfoGroups.pageObjects.android.LoginPage;
import cialfoGroups.pageObjects.android.OnboardingPages;
import cialfoGroups.pageObjects.android.ProfilePage;
import cialfoGroups.pageObjects.android.RegisterPage;
import cialfoGroups.pageObjects.android.WelcomePage;
import cialfoGroups.testUtils.AndroidBaseTest;

public class testWithOnboarding extends AndroidBaseTest{
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
	
	Double progressBarValue;
	@Test(priority=10)
	@Parameters({"graduationYear"})
	public void REGISTER_Q1_TEST(@Optional("2012") String graduationYear) throws InterruptedException
	{
		Thread.sleep(2000);
		progressBarValue = 0.00;
		OnboardingPages onboardingPages = new OnboardingPages(driver);
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(15));
		wait.until(ExpectedConditions.visibilityOf(onboardingPages.getStartedBtn));
		Thread.sleep(2000);
		onboardingPages.getStartedBtn.click();
		Thread.sleep(20000);
//		Assert.assertFalse(onboardingPages.Q1nextButton.isEnabled());
		onboardingPages.graduationYearDropDown.click();
		onboardingPages.searchAndSelect(graduationYear);
		Assert.assertTrue(onboardingPages.Q1nextButton.isEnabled());
		onboardingPages.Q1nextButton.click();
	}
	@Test(priority=11)
	@Parameters({"curriculum"})
	public void REGISTER_Q2_TEST(@Optional("Other") String curriculum)
	{
		OnboardingPages onboardingPages = new OnboardingPages(driver);
		Double progressbarNewValue = onboardingPages.getProgressBarValue();
		Assert.assertTrue(progressbarNewValue > progressBarValue);
		progressBarValue = progressbarNewValue;
//		Assert.assertFalse(onboardingPages.Q2nextButton.isEnabled());
		onboardingPages.curriculumDropDown.click();
		onboardingPages.searchAndSelect(curriculum);
		Assert.assertTrue(onboardingPages.Q2nextButton.isEnabled());
		onboardingPages.Q2nextButton.click();
	}
	
	@Test(priority=12)
	@Parameters({"citizenShip"})
	public void REGISTER_Q3_SELECT_DESELECT_TEST(@Optional("Pakistan") String citizenShip)
	{
		OnboardingPages onboardingPages = new OnboardingPages(driver);
		Double progressbarNewValue = onboardingPages.getProgressBarValue();
		Assert.assertTrue(progressbarNewValue > progressBarValue);
		progressBarValue = progressbarNewValue;
//		Assert.assertFalse(onboardingPages.Q3nextButton.isEnabled());
		onboardingPages.citizenshipDropDown.click();
		onboardingPages.searchAndSelect(citizenShip);
		onboardingPages.citizenshipDropDown.click();
		onboardingPages.searchAndSelect(citizenShip);
		Assert.assertFalse(onboardingPages.Q3nextButton.isEnabled());
	}
	
	@Test(priority=13)
	@Parameters({"citizenShip"})
	public void REGISTER_Q3_TEST(@Optional("Pakistan") String citizenShip)
	{
		OnboardingPages onboardingPages = new OnboardingPages(driver);
		onboardingPages.citizenshipDropDown.click();
		onboardingPages.searchAndSelect(citizenShip);
		Assert.assertTrue(onboardingPages.Q3nextButton.isEnabled());
		onboardingPages.Q3nextButton.click();
	}
	
	@Test(priority=14)
	@Parameters({"region"})
	public void REGISTER_Q4_SELECT_DESELECT_TEST(@Optional("Pakistan") String citizenShip)
	{
		OnboardingPages onboardingPages = new OnboardingPages(driver);
		Double progressbarNewValue = onboardingPages.getProgressBarValue();
		Assert.assertTrue(progressbarNewValue > progressBarValue);
		progressBarValue = progressbarNewValue;
//		Assert.assertFalse(onboardingPages.Q4nextButton.isEnabled());
		onboardingPages.otherRegionsDropDown.click();
		onboardingPages.searchAndSelect(citizenShip);
		onboardingPages.otherRegionsDropDown.click();
		onboardingPages.searchAndSelect(citizenShip);
		Assert.assertFalse(onboardingPages.Q4nextButton.isEnabled());
	}
	
	@Test(priority=15)
	@Parameters({"region"})
	public void REGISTER_Q4_TEST(@Optional("Pakistan") String citizenShip)
	{
		OnboardingPages onboardingPages = new OnboardingPages(driver);
		onboardingPages.otherRegionsDropDown.click();
		onboardingPages.searchAndSelect(citizenShip);
		onboardingPages.scrollToText("I am not sure");
		Assert.assertTrue(onboardingPages.Q4nextButton.isEnabled());
		onboardingPages.Q4nextButton.click();
	}
	
	@Test(priority=16)
	@Parameters({"course"})
	public void REGISTER_Q5_SELECT_DESELECT_TEST(@Optional("Food Research") String course)
	{
		OnboardingPages onboardingPages = new OnboardingPages(driver);
		Double progressbarNewValue = onboardingPages.getProgressBarValue();
		Assert.assertTrue(progressbarNewValue > progressBarValue);
		progressBarValue = progressbarNewValue;
//		Assert.assertFalse(onboardingPages.Q5nextButton.isEnabled());
		onboardingPages.ChooseCourseDropDown.click();
		onboardingPages.searchAndSelect(course);
		onboardingPages.ChooseCourseDropDown.click();
		onboardingPages.searchAndSelect(course);
		Assert.assertFalse(onboardingPages.Q5nextButton.isEnabled());
	}
	
	@Test(priority=17)
	@Parameters({"course"})
	public void REGISTER_Q5_TEST(@Optional("Food Research") String course)
	{
		OnboardingPages onboardingPages = new OnboardingPages(driver);
		onboardingPages.ChooseCourseDropDown.click();
		onboardingPages.searchAndSelect(course);
		Assert.assertTrue(onboardingPages.Q5nextButton.isEnabled());
		onboardingPages.Q5nextButton.click();
	}
	
	@Test(priority=18)
	public void REGISTER_Q6_TEST() throws InterruptedException
	{
		OnboardingPages onboardingPages = new OnboardingPages(driver);
//		Assert.assertFalse(onboardingPages.Q6nextButton.isEnabled());
		onboardingPages.notSureButton.click();
		onboardingPages.takeMeToDashboardButton.click();
		DashboardPage dashboardPage = new DashboardPage(driver);
		Thread.sleep(2000);
		Assert.assertTrue(dashboardPage.profileImageButton.isDisplayed());
	}
	
//	@Test(priority=19)
//	@Parameters({"line1","line2","line3","city", "postCode", "country"})
//	public void LOGIN_ADDRESS_QUESTIONS(@Optional("line1") String line1, @Optional("line2") String line2, @Optional("line3") String line3, @Optional("city") String city, @Optional("51000") String postCode, @Optional("Pakistan") String country) throws InterruptedException
//	{
//		OnboardingPages onboardingPages = new OnboardingPages(driver);
//		onboardingPages.addressLine1InputField.sendKeys(line1);
//		onboardingPages.addressLine1NextBtn.click();
//		
//		onboardingPages.addressLine2InputField.sendKeys(line2);
//		onboardingPages.addressLine2NextBtn.click();
//		
//		onboardingPages.addressLine3InputField.sendKeys(line3);
//		onboardingPages.addressLine3NextBtn.click();
//		
//		onboardingPages.addressCityInputField.sendKeys(city);
//		onboardingPages.addressCityNextBtn.click();
//		
//		onboardingPages.addressPostCodeInputField.sendKeys(postCode);
//		onboardingPages.addressPostCodeNextBtn.click();
//		
//		onboardingPages.addressCountryDropDown.click();
//		onboardingPages.searchAndSelect(country);
//		onboardingPages.addressCountryNextBtn.click();
//	}
	
//	@Test(priority=19)
//	public void REGISTER_RECOMMENDATION_CAROUSEL() throws InterruptedException
//	{
//		DashboardPage dashboardPage = new DashboardPage(driver);
//		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(100));
//		wait.until(ExpectedConditions.visibilityOf(dashboardPage.profileImageButton));
//		Thread.sleep(5000);
//		boolean recommendationTextVisible = false;
//		do {
//			dashboardPage.scrollElementByPercentage(1.0, "down", dashboardPage.mainContent);
//			recommendationTextVisible = dashboardPage.recommendationText.isDisplayed();
//		}while(!recommendationTextVisible);
//		dashboardPage.scrollElementByPercentage(1.0, "right", dashboardPage.RecommendedBasedOnPreferenceScrollView);
//	}
}
