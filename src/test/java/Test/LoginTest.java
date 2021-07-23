package Test;

import Java.LoginPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class LoginTest {
    private static final String BASE_URL = "http://localhost:4200/";
    private WebDriver driver;
    private LoginPage loginPage;
    private String actualTitle = "";
    private String expectedTitle = "Frontend";

    @BeforeClass
    public void launchBrowser() {
        System.out.println("launching browser");
        driver = new ChromeDriver();
        driver.get(BASE_URL);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException ie) {
        }

        actualTitle = driver.getTitle();
        loginPage = new LoginPage(driver);
    }

    @Test
    public void verifyLogin() {
        loginPage.getLoginButton().click();
        Assert.assertEquals(actualTitle, expectedTitle);
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