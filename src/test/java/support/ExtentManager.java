package support;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {

    private static ExtentReports extentReports;
    private static final ThreadLocal<ExtentTest> currentTest = new ThreadLocal<>();

    public static void initReport() {
        if (extentReports == null) {
            ExtentSparkReporter htmlReporter = new ExtentSparkReporter("target/StoreRegressionReport.html");
            extentReports = new ExtentReports();
            extentReports.attachReporter(htmlReporter);
        }
    }

    public static ExtentReports getExtentReports() {
        return extentReports;
    }

    public static void flush() {
        if (extentReports != null) {
            extentReports.flush();
        }
    }

    public static void setCurrentTest(ExtentTest test) {
        currentTest.set(test);
    }

    public static ExtentTest getCurrentTest() {
        return currentTest.get();
    }
}

