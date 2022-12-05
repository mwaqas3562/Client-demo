package cialfoGroups.LoginTests;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.google.common.collect.ImmutableMap;

import cialfoGroups.pageObjects.android.DashboardPage;
import cialfoGroups.pageObjects.android.LoginPage;
import cialfoGroups.pageObjects.android.OnboardingPages;
import cialfoGroups.pageObjects.android.WelcomePage;
import cialfoGroups.testUtils.AndroidBaseTest;

public class test001 extends AndroidBaseTest{
	String email;
	String password;
	String wrongPassword;
	String wrongEmail;
	boolean runOnboarding = false;
	
	@Test(priority=0)
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
	}
	
	@Test(priority=1)
	@Parameters({"email", "password", "wrongPassword", "wrongEmail"})
	public void LOGIN_INVALID_EMAIL(String email, String password, String wrongPassword, String wrongEmail)
	{
		this.email = email;
		this.password = password;
		this.wrongEmail = wrongEmail;
		this.wrongPassword = wrongPassword;
		LoginPage loginPage = new LoginPage(driver);
		loginPage.setEmailOrPhone("emai");
		Assert.assertTrue(loginPage.getElementsIncludingText("Enter a valid email or phone number").size() > 0);
	}
	
	@Test(priority=2)
	public void LOGIN_CORRECT_EMAIL_WRONG_PASSWORD()
	{
		LoginPage loginPage = new LoginPage(driver);
		loginPage.setEmailOrPhone(email);
		loginPage.setPassword(wrongPassword);
		loginPage.clickLoginButton();
		Assert.assertTrue(loginPage.loginFailMessage.isDisplayed());
		loginPage.loginFailOkButton.click();
	}
	
	@Test(priority=3)
	public void LOGIN_WRONG_EMAIL_CORRECT_PASSWORD()
	{
		LoginPage loginPage = new LoginPage(driver);
		loginPage.setEmailOrPhone(wrongEmail);
		loginPage.setPassword(password);
		loginPage.clickLoginButton();
		Assert.assertTrue(loginPage.loginFailMessage.isDisplayed());
		loginPage.loginFailOkButton.click();
	}
//	
	@Test(priority=4)
	public void LOGIN_WRONG_EMAIL_WRONG_PASSWORD()
	{
		LoginPage loginPage = new LoginPage(driver);
		loginPage.setEmailOrPhone(wrongEmail);
		loginPage.setPassword(wrongPassword);
		loginPage.clickLoginButton();
		Assert.assertTrue(loginPage.loginFailMessage.isDisplayed());
		loginPage.loginFailOkButton.click();
	}
	
	@Test(priority=5)
	public void LOGIN_CORRECT_EMAIL_CORRECT_PASSWORD() throws InterruptedException
	{
		LoginPage loginPage = new LoginPage(driver);
		loginPage.setEmailOrPhone(email);
		loginPage.setPassword(password);
		loginPage.clickLoginButton();
		Thread.sleep(1000);
		DashboardPage dashboardPage = new DashboardPage(driver);
		try {
			Assert.assertTrue(dashboardPage.profileImageButton.isDisplayed());
		}catch(Exception e) {
			runOnboarding = true;
		}
	}
	
	Double progressBarValue;
	@Test(priority=6)
	@Parameters({"graduationYear"})
	public void LOGIN_Q1_TEST(@Optional("2012") String graduationYear) throws InterruptedException
	{
		if(!runOnboarding)
		{
			throw new SkipException("Skipping this exception");
		}
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
	@Test(priority=7)
	@Parameters({"curriculum"})
	public void LOGIN_Q2_TEST(@Optional("Other") String curriculum)
	{
		if(!runOnboarding)
		{
			throw new SkipException("Skipping this exception");
		}
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
	
	@Test(priority=8)
	@Parameters({"citizenShip"})
	public void LOGIN_Q3_SELECT_DESELECT_TEST(@Optional("Pakistan") String citizenShip)
	{
		if(!runOnboarding)
		{
			throw new SkipException("Skipping this exception");
		}
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
	
	@Test(priority=9)
	@Parameters({"citizenShip"})
	public void LOGIN_Q3_TEST(@Optional("Pakistan") String citizenShip)
	{
		if(!runOnboarding)
		{
			throw new SkipException("Skipping this exception");
		}
		OnboardingPages onboardingPages = new OnboardingPages(driver);
		onboardingPages.citizenshipDropDown.click();
		onboardingPages.searchAndSelect(citizenShip);
		Assert.assertTrue(onboardingPages.Q3nextButton.isEnabled());
		onboardingPages.Q3nextButton.click();
	}
//	
	@Test(priority=10)
	@Parameters({"region"})
	public void LOGIN_Q4_SELECT_DESELECT_TEST(@Optional("Pakistan") String citizenShip)
	{
		if(!runOnboarding)
		{
			throw new SkipException("Skipping this exception");
		}
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
//	
	@Test(priority=11)
	@Parameters({"region"})
	public void LOGIN_Q4_TEST(@Optional("Pakistan") String citizenShip)
	{
		if(!runOnboarding)
		{
			throw new SkipException("Skipping this exception");
		}
		OnboardingPages onboardingPages = new OnboardingPages(driver);
		onboardingPages.otherRegionsDropDown.click();
		onboardingPages.searchAndSelect(citizenShip);
		onboardingPages.scrollToText("I am not sure");
		Assert.assertTrue(onboardingPages.Q4nextButton.isEnabled());
		onboardingPages.Q4nextButton.click();
	}
//	
	@Test(priority=12)
	@Parameters({"course"})
	public void LOGIN_Q5_SELECT_DESELECT_TEST(@Optional("Food Research") String course)
	{
		if(!runOnboarding)
		{
			throw new SkipException("Skipping this exception");
		}
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
//	
	@Test(priority=13)
	@Parameters({"course"})
	public void LOGIN_Q5_TEST(@Optional("Food Research") String course)
	{
		if(!runOnboarding)
		{
			throw new SkipException("Skipping this exception");
		}
		OnboardingPages onboardingPages = new OnboardingPages(driver);
		onboardingPages.ChooseCourseDropDown.click();
		onboardingPages.searchAndSelect(course);
		Assert.assertTrue(onboardingPages.Q5nextButton.isEnabled());
		onboardingPages.Q5nextButton.click();
	}
	
	@Test(priority=14)
	public void LOGIN_Q6_TEST() throws InterruptedException
	{
		if(!runOnboarding)
		{
			throw new SkipException("Skipping this exception");
		}
		OnboardingPages onboardingPages = new OnboardingPages(driver);
//		Assert.assertFalse(onboardingPages.Q6nextButton.isEnabled());
		onboardingPages.notSureButton.click();
		onboardingPages.takeMeToDashboardButton.click();
		DashboardPage dashboardPage = new DashboardPage(driver);
		Thread.sleep(2000);
		Assert.assertTrue(dashboardPage.profileImageButton.isDisplayed());
	}
	
//	@Test(priority=15)
//	@Parameters({"line1","line2","line3","city", "postCode", "country"})
//	public void LOGIN_ADDRESS_QUESTIONS(@Optional("line1") String line1, @Optional("line2") String line2, @Optional("line3") String line3, @Optional("city") String city, @Optional("51000") String postCode, @Optional("Pakistan") String country) throws InterruptedException
//	{
//		if(!runOnboarding)
//		{
//			throw new SkipException("Skipping this exception");
//		}
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
	
//	@Test(priority=15)
//	public void LOGIN_RECOMMENDATION_CAROUSEL() throws InterruptedException
//	{
//		if(!runOnboarding)
//		{
//			throw new SkipException("Skipping this exception");
//		}
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
//	
}
