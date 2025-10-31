package com.dev.sfly.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentManager {

    private static ExtentReports extent;

    public static ExtentReports getInstance() {
        if (extent == null) {
            // Generate timestamp for unique report name
            String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
            String reportPath = System.getProperty("user.dir") + "/reports/ExtentReport_" + timeStamp + ".html";

            // Create Spark Reporter
            ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
            spark.config().setDocumentTitle("Shutterfly Automation Report");
            spark.config().setReportName("API Automation Suite");
            spark.config().setTheme(Theme.DARK);

            // Initialize ExtentReports
            extent = new ExtentReports();
            extent.attachReporter(spark);

            // Add system/environment info
            extent.setSystemInfo("Environment", "Dev");
            extent.setSystemInfo("Tester", "Srinath");
            extent.setSystemInfo("Report Generated On", new SimpleDateFormat("dd MMM yyyy, HH:mm:ss").format(new Date()));
        }
        return extent;
    }
}
