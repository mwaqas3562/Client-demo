package cialfoGroups.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

public class AppiumUtils {
	
	public double removeDollarSignFromAmount(String amountString)
	{
		return Double.parseDouble(amountString.substring(1));
	}
	
	public void waitForElementToAppear()
	{
		
	}
	
	public List<HashMap<String,String>> getJsonData(String filePath) throws IOException
	{
		//convert to json string
		String jsonContent = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);
		
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String, String>> data = mapper.readValue(jsonContent, 
				new TypeReference<List<HashMap<String, String>>> (){
			
		});
		return data;
	}
	
	public AppiumDriverLocalService startAppiumServer(String ipAddress, int port) throws IOException
	{
		Properties prop = new Properties();
		FileInputStream  fis = new FileInputStream(System.getProperty("user.dir")+"//src//main//java//cialfoGroups//resources//data.properties");
		prop.load(fis);
		String appiumMainJsPath = prop.getProperty("appiumMainJsPath");
		AppiumDriverLocalService service = new AppiumServiceBuilder().withAppiumJS(new File(appiumMainJsPath))
				.withIPAddress(ipAddress).usingPort(port).build();
		service.start();
		return service;
	}
	
	public String getScreenshotPath(AppiumDriver driver, String testName)
	{
		String screenShotPath = System.getProperty("user.dir")+"//reports//"+testName+".png";
		try {
			File source = driver.getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(source, new File(screenShotPath));
		} catch (Exception e) {
			try {
				screenShotPath = "data:image/jpeg;base64,"+driver.getScreenshotAs(OutputType.BASE64);	
			}catch(Exception childE)
			{				
				return "screenshot store error";
			}
		}
		return screenShotPath;
		
	}
	
	public String getRandomEmail()
	{
		String[] emailProviders = {"hitmail.cm","rxdoc.iz","cox.co","google.co", "outlook.de", "yahoo.co", "aol.de", "icloud.co", "mailnaitor.com"};
		int emailRnd = new Random().nextInt(emailProviders.length);
		Random rnd = new Random();
        String AlphaNumericString = "abcdefghijklmnopqrstuvxyz";
        String numberString = "123456789";
        StringBuilder sb = new StringBuilder(8);
        for (int i = 0; i < 8; i++) {
  
            int index
                = (int)(AlphaNumericString.length()
                        * Math.random());
  
            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                          .charAt(index));
        }
        return sb.toString()+(10 + rnd.nextInt(20))+"@"+emailProviders[emailRnd];
	}
}
