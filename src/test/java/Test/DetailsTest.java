package Test;

import Java.AddUser;
import Java.DeleteUser;
import Java.DetailsPage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class DetailsTest {
    private static final String BASE_URL = "http://localhost:4200/users";
    private WebDriver driver;
    private DetailsPage detailsPage;

    @BeforeClass
    public void launchBrowser() {
        System.out.println("launching browser");
        driver = new ChromeDriver();
        driver.get(BASE_URL);
        detailsPage = new DetailsPage(driver);
    }

    @Test
    public void giveNewUser_expectDetails() {
        addNewUser();
        try {
            Thread.sleep(500);
        } catch (InterruptedException ie) {
        }
        detailsPage.getDetailsButton().click();
        driver.get(BASE_URL);
        driver.navigate().refresh();

        Assert.assertEquals("Calin1", detailsPage.getInsertedUsername().getText());
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
