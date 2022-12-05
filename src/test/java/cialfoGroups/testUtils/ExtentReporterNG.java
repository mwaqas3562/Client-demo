package cialfoGroups.testUtils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReporterNG {
	public static ExtentReports getReporterObject()
	{
		String reportsPath = System.getProperty("user.dir")+"//reports//index.html";
		ExtentSparkReporter reporter = new ExtentSparkReporter(reportsPath);
		reporter.config().setReportName("Appium Automation Results");
		reporter.config().setDocumentTitle("Appium Tests");
		ExtentReports extent = new ExtentReports();
		extent.attachReporter(reporter);
		extent.setSystemInfo("Tester", "Waqas");
		return extent;
	}
}
