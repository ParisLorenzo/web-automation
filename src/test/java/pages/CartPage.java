package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage extends BasePage {

    private final By title = By.cssSelector("h1");
    private final By totalCell = By.cssSelector(".cart-total .amount");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public String getTitle() {
        return getText(title);
    }

    public double getCartTotal() {
        String text = getText(totalCell).replace("$", "").trim();
        return Double.parseDouble(text);
    }
}

