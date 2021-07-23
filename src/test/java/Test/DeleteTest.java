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

public class DeleteTest {
    private static final String BASE_URL = "http://localhost:4200/users/";
    private WebDriver driver;
    private DeleteUser deleteUser;


    @BeforeClass
    public void setup() {
        System.out.println("launching browser");
        driver = new ChromeDriver();
        driver.get(BASE_URL);
        deleteUser = new DeleteUser(driver);

        addNewUser();
        driver.get(BASE_URL);
        driver.navigate().refresh();
    }


    @Test(priority = 0)
    public void verifyDeleteCancel() {
        driver.get(BASE_URL);
        driver.navigate().refresh();
        int sizeBefore = driver.findElements(By.xpath("(//button[contains(.,'Edit')])")).size();

        deleteUser.getDeleteButton().click();
        deleteUser.getCancelDelete().click();

        int sizeAfter = driver.findElements(By.xpath("(//button[contains(.,'Edit')])")).size();

        Assert.assertEquals(sizeAfter, sizeBefore);
    }

    @Test(priority = 1)
    public void verifyDeleteAccept() {
        int sizeBefore = driver.findElements(By.xpath("(//button[contains(.,'Edit')])")).size();
        deleteUser.getDeleteButton().click();
        deleteUser.getAcceptDelete().click();
        driver.get(BASE_URL);
        driver.navigate().refresh();

        int sizeAfter = driver.findElements(By.xpath("(//button[contains(.,'Edit')])")).size();

        Assert.assertNotEquals(sizeAfter, sizeBefore);
    }

    @AfterClass
    public void afterClass() {
        deleteAllUsers();
        tryloseDriver();
    }

    private void tryloseDriver() {
        try {
            driver.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        driver.quit();
    }

    private void deleteAllUsers() {
        int size = driver.findElements(By.xpath("(//button[contains(.,'Edit')])")).size();
        while (size > 0) {
            deleteUser.getDeleteButton().click();
            deleteUser.getAcceptDelete().click();
            driver.get(BASE_URL);
            driver.navigate().refresh();
            size = driver.findElements(By.xpath("(//button[contains(.,'Edit')])")).size();
        }
    }


    private void addNewUser() {
        AddUser addUser = new AddUser(driver);

        addUser.getAddUserButton().click();

        addUser.getUsername().sendKeys("Calin1");
        addUser.getEmail().sendKeys("calin@calin.ro1");
        addUser.getFullName().sendKeys("Calin User1");
        addUser.getPassword().sendKeys("Calin11");
        addUser.getTraitPerfectionist().click();
        addUser.getGenderMale().sendKeys(Keys.SPACE);
        addUser.getSubmitButton().sendKeys(Keys.ENTER);

    }
}
