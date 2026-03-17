package steps;

import com.aventstack.extentreports.ExtentTest;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import support.DriverManager;
import support.ExtentManager;

public class Hooks {

    @Before
    public void setUp(Scenario scenario) {
        DriverManager.initDriver();
        ExtentManager.initReport();
        ExtentTest test = ExtentManager.getExtentReports()
                .createTest(scenario.getName());
        ExtentManager.setCurrentTest(test);
    }

    @After
    public void tearDown(Scenario scenario) {
        WebDriver driver = DriverManager.getDriver();
        if (driver != null) {
            if (scenario.isFailed()) {
                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", "Failure screenshot");
                ExtentManager.getCurrentTest()
                        .fail("Escenario fallido")
                        .addScreenCaptureFromBase64String(((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64));
            } else {
                ExtentManager.getCurrentTest().pass("Escenario exitoso");
            }
        }
        DriverManager.quitDriver();
        ExtentManager.flush();
    }
}

