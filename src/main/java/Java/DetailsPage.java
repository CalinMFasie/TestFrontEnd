package Java;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class DetailsPage {
    private WebDriver driver;

    @FindBy(xpath = "//h1[contains(.,'Calin User1')]")
    private WebElement detailsButton;

    @FindBy(xpath = "(//span[contains(.,'Calin1')])[1]")
    private WebElement insertedUsername;

    public DetailsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public WebElement getDetailsButton() {
        return detailsButton;
    }

    public WebElement getInsertedUsername() {
        return insertedUsername;
    }
}
