package Test;

import Java.AddUser;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class AddUserTestB {
    String driverPath = "/usr/local/bin/geckodriver";
    String baseUrl = "http://localhost:4200/users";
    String actualUrl = "";
    public WebDriver driver;
    private AddUser addUser;

    @BeforeTest
    public void launchBrowser() {
        System.out.println("launching browser");
        System.setProperty("webdriver.gecko.driver", driverPath);
        driver = new ChromeDriver();
        driver.get(baseUrl);
        addUser = new AddUser(driver);

    }

    @Test(priority = 1)
    public void insertIncompleteData() {
        driver.get(baseUrl);
        boolean isPresent = false;
        addUser.getAddUserButton().click();

        addUser.getUsername().sendKeys("Calin123");
        addUser.getEmail().sendKeys("calin@calin.ro123");
        addUser.getFullName().sendKeys("Calin User123");
        addUser.getPassword().sendKeys("Calin123");
        addUser.getTraitPerfectionist().click();
//        addUser.getGenderMale().sendKeys(Keys.SPACE);
        addUser.getSubmitButton().sendKeys(Keys.ENTER);

        isPresent= driver.findElements(By.xpath("button[id^='OKButton']")).size()!=0;
        System.out.println(isPresent);
        try{
            Thread.sleep(2000);
        }
        catch(InterruptedException ie){
        }

        if (driver.findElements(By.xpath("(//span[contains(.,'Calin123')])[1]")).size() == 0) {
            isPresent = false;
        }

        Assert.assertFalse(isPresent,"Item was not inserted!");

    }

}