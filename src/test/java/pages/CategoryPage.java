package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CategoryPage extends BasePage {

    private final By firstProductContainer = By.cssSelector(".product:first-of-type");
    private final By firstProductPrice = By.cssSelector(".product:first-of-type .price");
    private final By firstProductAddButton = By.cssSelector(".product:first-of-type .btn-add");
    private final By quantityInput = By.id("quantity");
    private final By popup = By.id("cart-popup");
    private final By popupMessage = By.cssSelector("#cart-popup .message");
    private final By popupTotal = By.cssSelector("#cart-popup .total");
    private final By popupCheckoutButton = By.cssSelector("#cart-popup .btn-checkout");

    public CategoryPage(WebDriver driver) {
        super(driver);
    }

    public double getFirstProductUnitPrice() {
        String priceText = getText(firstProductPrice).replace("$", "").trim();
        return Double.parseDouble(priceText);
    }

    public void addFirstProductWithQuantity(int quantity) {
        WebElement product = waitForVisible(firstProductContainer);
        product.findElement(quantityInput).clear();
        product.findElement(quantityInput).sendKeys(String.valueOf(quantity));
        product.findElement(firstProductAddButton).click();
    }

    public boolean isPopupVisible() {
        try {
            return waitForVisible(popup).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getPopupMessage() {
        return getText(popupMessage);
    }

    public double getPopupTotal() {
        String totalText = getText(popupTotal).replace("$", "").trim();
        return Double.parseDouble(totalText);
    }

    public void goToCheckoutFromPopup() {
        click(popupCheckoutButton);
    }
}

