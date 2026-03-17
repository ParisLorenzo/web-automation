package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {

    private final By mainContainer = By.linkText("Clothes");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        try {
            return driver.findElement(mainContainer).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void goToCategory(String category, String subCategory) {
        By categoryLink = By.linkText(category);
        click(categoryLink);
        By subCategoryLink = By.linkText(subCategory);
        click(subCategoryLink);
    }
}

