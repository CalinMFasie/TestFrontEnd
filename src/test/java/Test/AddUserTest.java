package Test;

import Java.AddUser;
import Java.DeleteUser;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;


public class AddUserTest {
    private final String BASE_URL = "http://localhost:4200/users";
    private WebDriver driver;
    private AddUser addUser;

    @BeforeClass
    public void launchBrowser() {
        System.out.println("launching browser");
        driver = new ChromeDriver();
        driver.get(BASE_URL);
        addUser = new AddUser(driver);
    }

    @Test(priority = 2)
    public void clickOnCancelAdd_shouldCloseTheForm() {
        driver.get(BASE_URL);

        addUser.getAddUserButton().click();
        assertEquals("http://localhost:4200/add", driver.getCurrentUrl());
        addUser.getxButton().click();
        assertEquals(BASE_URL, driver.getCurrentUrl());

    }

    @Test(priority = 0)
    public void givenAllInputData_expectSuccess() {
        driver.get(BASE_URL);
        addUser.getAddUserButton().click();

        addUser.getUsername().sendKeys("Calin1");
        addUser.getEmail().sendKeys("calin@calin.ro1");
        addUser.getFullName().sendKeys("Calin User1");
        addUser.getPassword().sendKeys("Calin11");
        addUser.getTraitPerfectionist().click();
        addUser.getGenderMale().sendKeys(Keys.SPACE);
        addUser.getSubmitButton().sendKeys(Keys.ENTER);

        driver.get(BASE_URL);
        driver.navigate().refresh();

        boolean isUserNameInserted = addUser.getInsertedUsername().isDisplayed();
        Assert.assertTrue(isUserNameInserted);

        boolean isEmailInserted = addUser.getInsertedEmail().isDisplayed();
        Assert.assertTrue(isEmailInserted);

        boolean isFullNameInserted = addUser.getInsertedFullName().isDisplayed();
        Assert.assertTrue(isFullNameInserted);


        try {
            Thread.sleep(1000);
        } catch (InterruptedException ie) {
        }
    }

    @Test(priority = 1)
    public void insertNecessaryData_shouldPass() {
        driver.get(BASE_URL);
        addUser.getAddUserButton().click();

        addUser.getUsername().sendKeys("Calin12");
        addUser.getEmail().sendKeys("calin@calin.ro12");
        addUser.getFullName().sendKeys("Calin User12");
        addUser.getPassword().sendKeys("Calin12");
        addUser.getGenderMale().sendKeys(Keys.SPACE);
        addUser.getSubmitButton().sendKeys(Keys.ENTER);

        driver.get(BASE_URL);
        driver.navigate().refresh();

        boolean isNecessaryUserNameInserted = addUser.getInsertedNecessaryUsername().isDisplayed();
        Assert.assertTrue(isNecessaryUserNameInserted);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ie) {
        }
    }


    @AfterClass
    public void afterClass() {
        deleteAllUsers();

        tryCloseDriver();
    }

    private void deleteAllUsers() {
        DeleteUser deleteUser = new DeleteUser(driver);
        int size = driver.findElements(By.xpath("(//button[contains(.,'Edit')])")).size();

        while (size > 0) {
            deleteUser.getDeleteButton().click();
            deleteUser.getAcceptDelete().click();
            driver.get(BASE_URL);
            driver.navigate().refresh();
            size = driver.findElements(By.xpath("(//button[contains(.,'Edit')])")).size();
        }
    }

    private void tryCloseDriver() {
        try {
            driver.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        driver.quit();
    }
}