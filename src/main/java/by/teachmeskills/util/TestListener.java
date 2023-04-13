package by.teachmeskills.util;

import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {
    @Override
    public void onTestFailure(ITestResult result) {
        takeScreenshot(result);
    }

    private byte[] takeScreenshot(ITestResult result) {
        ITestContext context = result.getTestContext();
        try {
            WebDriver driver = (WebDriver) context.getAttribute("driver");
            if (driver != null) {
                return AllureUtils.takeScreenShot(driver);
            } else {
                return new byte[]{};
            }
        } catch (NoSuchSessionException | IllegalStateException e) {
            System.out.println("Screenshot was not taken " + e.getLocalizedMessage());
            return new byte[]{};
        }
    }
}
