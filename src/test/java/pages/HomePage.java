package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {

    private final By mainContainer = By.cssSelector("a.logout, a[title='Log out'], a[title='Sign out']");
    private final By clothesLink = By.cssSelector("a[href*='/3-clothes']");
    private final By menLink = By.cssSelector("a[href*='/4-men']");

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
        if ("clothes".equalsIgnoreCase(category) && "men".equalsIgnoreCase(subCategory)) {
            click(clothesLink);
            click(menLink);
        } else {
            By categoryLink = By.linkText(category);
            click(categoryLink);
            By subCategoryLink = By.linkText(subCategory);
            click(subCategoryLink);
        }
    }
}

