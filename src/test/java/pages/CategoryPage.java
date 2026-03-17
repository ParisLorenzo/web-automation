package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CategoryPage extends BasePage {

    // Listado de productos en categoria
    private final By firstProductPrice = By.cssSelector(".product-miniature .price");
    private final By firstProductLink = By.cssSelector(".product-miniature a.product-thumbnail, .product-miniature h2 a");

    // Ficha de producto
    private final By quantityInput = By.id("quantity_wanted");
    private final By addToCartButton = By.cssSelector("button[data-button-action='add-to-cart']");

    // Popup de carrito
    private final By popup = By.id("blockcart-modal");
    private final By popupMessage = By.cssSelector("#blockcart-modal .modal-body");
    private final By popupTotal = By.cssSelector("#blockcart-modal .cart-content .value, #blockcart-modal .cart-content span.value");
    private final By popupCheckoutButton = By.cssSelector("#blockcart-modal a.btn.btn-primary");

    public CategoryPage(WebDriver driver) {
        super(driver);
    }

    public double getFirstProductUnitPrice() {
        String priceText = getText(firstProductPrice);
        priceText = priceText.replace("PEN", "")
                .replace("$", "")
                .replaceAll("[^0-9.,]", "")
                .trim();
        return Double.parseDouble(priceText);
    }

    public void addFirstProductWithQuantity(int quantity) {
        // Abrir ficha del primer producto
        WebElement productLink = waitForClickable(firstProductLink);
        productLink.click();

        // En ficha de producto, setear cantidad y agregar al carrito
        WebElement qty = waitForVisible(quantityInput);
        qty.clear();
        qty.sendKeys(String.valueOf(quantity));

        click(addToCartButton);
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
        String totalText = getText(popupTotal);
        totalText = totalText.replace("PEN", "")
                .replace("$", "")
                .replaceAll("[^0-9.,]", "")
                .trim();
        return Double.parseDouble(totalText);
    }

    public void goToCheckoutFromPopup() {
        click(popupCheckoutButton);
    }
}

