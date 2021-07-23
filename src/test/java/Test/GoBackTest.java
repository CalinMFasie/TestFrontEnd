package Test;

import Java.GoBack;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class GoBackTest {
    private static final String BASE_URL = "http://localhost:4200/users/";
    private static final String EXPECTED_URL = "http://localhost:4200/";
    private WebDriver driver;
    private GoBack goBack;
    private String actualUrl = "";

    @BeforeTest
    public void launchBrowser() {
        System.out.println("launching browser");
        driver = new ChromeDriver();
        driver.get(BASE_URL);
        goBack = new GoBack(driver);
    }

    @Test
    public void verifyDelete() {
        goBack.getGoBackButton().click();
        actualUrl = driver.getCurrentUrl();
        Assert.assertEquals(actualUrl, EXPECTED_URL);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ie) {
        }
    }

    @AfterClass(alwaysRun = true)
    public void closeDriver() {
        try {
            driver.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        driver.quit();
    }
}
