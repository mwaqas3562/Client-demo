package cialfoGroups.utils;

import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;

import com.google.common.collect.ImmutableMap;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;

public class AndroidActions extends AppiumUtils{
	
	AndroidDriver driver;
	
	public AndroidActions(AndroidDriver driver)
	{
		this.driver = driver;
	}
	
	public void longPressAction(WebElement element) {
		((JavascriptExecutor)driver).executeScript("mobile: longClickGesture", ImmutableMap.of(
				"elementId",((RemoteWebElement)element).getId(),
				"duration",2000
		));
	}
	
	public void swipeAction(WebElement element, String direction)
	{
		((JavascriptExecutor)driver).executeScript("mobile: swipeGesture", ImmutableMap.of(
				"elementId", ((RemoteWebElement)element).getId(),
				"direction",direction,
				"percent",0.75
				));
	}
	
	public void scrollToText(String text)
	{
		driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\""+text+"\"))"));
	}
	
	public void scrollElementByPercentage(Double percentage, String direction, WebElement element)
	{
		((JavascriptExecutor) driver).executeScript("mobile: scrollGesture", ImmutableMap.of(
				"elementId", ((RemoteWebElement) element).getId(),
			    "left", 100, "top", 100, "width", 200, "height", 200,
			    "direction", direction,
			    "percent", percentage
			));
	}
	
	public void pressKey(AndroidKey key)
	{
		driver.pressKey(new KeyEvent(key));
	}
	
	public List<WebElement> getElementsIncludingText(String text)
	{
		return driver.findElements(AppiumBy.androidUIAutomator("new UiSelector().textContains(\""+text+"\")"));
	}
	
}
