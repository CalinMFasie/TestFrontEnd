package Test;

import Java.AddUser;
import Java.DeleteUser;
import Java.EditUser;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class EditUserTest {
    private static final String BASE_URL = "http://localhost:4200/users";
    private WebDriver driver;
    private EditUser editUser;

    @BeforeClass
    public void launchBrowser() {
        System.out.println("launching browser");
        driver = new ChromeDriver();
        driver.get(BASE_URL);
        editUser = new EditUser(driver);
        addNewUser();
    }

    @Test(priority = 1)
    public void cancelEdit_shouldReturnToBaseUrl() {
        driver.get(BASE_URL);
        editUser.getEditUserButton().click();
        editUser.getxButton().click();

        Assert.assertEquals("http://localhost:4200/users", driver.getCurrentUrl());

    }

    @Test(priority = 0)
    public void editData_expectNewData() {
        editUser.getEditUserButton().click();

        editUser.getUsername().clear();
        editUser.getUsername().sendKeys("Marcel1");

        editUser.getEmail().clear();
        editUser.getEmail().sendKeys("marcel@marcel.ro1");

        editUser.getFullName().clear();
        editUser.getFullName().sendKeys("Marcel User1");

        editUser.getPassword().clear();
        editUser.getPassword().sendKeys("Marcel11");

        editUser.getSubmitButton().sendKeys(Keys.ENTER);
        driver.get(BASE_URL);
        driver.navigate().refresh();


        Assert.assertEquals("Marcel1", editUser.getInsertedUsername().getText());
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
        driver.get(BASE_URL);
        driver.navigate().refresh();
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
