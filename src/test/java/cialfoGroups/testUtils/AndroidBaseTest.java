package cialfoGroups.testUtils;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.beust.jcommander.Parameter;
import com.google.common.collect.ImmutableMap;

import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import cialfoGroups.utils.AppiumUtils;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.remote.MobileCapabilityType;

public class AndroidBaseTest extends AppiumUtils{
	public AndroidDriver driver;
	public AppiumDriverLocalService service;
	public String appName;
	@BeforeClass(alwaysRun=true)
	public void configureAppium() throws IOException {
		// READ GLOBAL PROPERTIES
		Properties prop = new Properties();
		FileInputStream  fis = new FileInputStream(System.getProperty("user.dir")+"//src//main//java//cialfoGroups//resources//data.properties");
		prop.load(fis);
		String ipAddress = (System.getProperty("ipAddress") == null) ? prop.getProperty("ipAddress"):System.getProperty("ipAddress") ;
		int port = (System.getProperty("port") == null) ? Integer.parseInt(prop.getProperty("port")):Integer.parseInt(System.getProperty("port"));
		String AndroidDeviceName = (System.getProperty("AndroideDeviceName") == null) ? prop.getProperty("AndroideDeviceName"):System.getProperty("AndroideDeviceName");
		String browser = (System.getProperty("browser") == null) ? prop.getProperty("chrome"):System.getProperty("browser");
		appName = System.getProperty("user.dir")+"//src//test//java//cialfoGroups//resources//cialfo.apk";
		//START APPIUM SERVER
		service = startAppiumServer(ipAddress, port);
		
		//CREATE UIAUTOMATOR2 DRIVER AND LAUNCH APP IN DEVICE
		UiAutomator2Options options = new UiAutomator2Options();
		
		//SET CHROME DRIVER
		//options.setChromedriverExecutable("//home//muhammad//Downloads//chromedriver_linux64");
		//options.setApp(System.getProperty("user.dir")+"//src//test//java//resources//ApiDemos-debug.apk");
		
		options.setDeviceName(AndroidDeviceName);
		options.setApp(appName);
		options.setCapability("noReset", true);
//		options.setCapability("isHeadless", true);
		driver = new AndroidDriver(service.getUrl(), options);
		
		// WAIT FOR 15 SECONDS IF ELEMENT IS NOT VISIBLE
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
	}
	
	@AfterClass(alwaysRun=true)
	public void tearDown()
	{
		//STOP THE ANDROID DRIVER
		driver.quit();
		
		service.stop();
	}
}
   