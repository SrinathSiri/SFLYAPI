package com.sfly.base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.sfly.identity.OAuth2WithAutomationKey;
import com.sfly.reports.ExtentManager;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class BaseTest {

    protected static String idToken;
    protected static ExtentReports extent;
    protected static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    protected static String accountId;
    protected static String baseURI;
    protected static String xApiKey;
    protected static Properties config;

    @BeforeSuite
    public void startReport() {
        extent = ExtentManager.getInstance();
        loadConfig();
    }

    private void loadConfig() {
        config = new Properties();
        try (FileInputStream fis = new FileInputStream("src/test/resources/config.properties")) {
            config.load(fis);
            accountId = config.getProperty("accountId");
            baseURI = config.getProperty("baseURI");
            xApiKey = config.getProperty("xApiKey");
            System.out.println("✅ Config loaded successfully: " + accountId);
        } catch (IOException e) {
            throw new RuntimeException("❌ Failed to load config.properties", e);
        }
    }

    @BeforeClass
    public void setupToken() {
        if (idToken == null) {
            idToken = OAuth2WithAutomationKey.getIdToken();
            System.out.println("✅ Token generated and ready for use.");
        }
    }

    @BeforeMethod
    public void createTest(ITestResult result) {
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName());
        test.set(extentTest);
        test.get().log(Status.INFO, "Starting test: " + result.getMethod().getMethodName());
    }

    @AfterMethod
    public void getResult(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE)
            test.get().log(Status.FAIL, "❌ Test Failed: " + result.getThrowable());
        else if (result.getStatus() == ITestResult.SUCCESS)
            test.get().log(Status.PASS, "✅ Test Passed");
        else
            test.get().log(Status.SKIP, "⚠️ Test Skipped");
    }

    @AfterSuite
    public void endReport() {
        extent.flush();
        System.out.println("📄 Extent Report generated successfully.");
    }
}

