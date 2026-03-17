package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    private final By usernameInput = By.id("user");
    private final By passwordInput = By.id("pass");
    private final By loginButton = By.id("btnLogin");
    private final By loginError = By.cssSelector(".alert.alert-danger");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void open(String url) {
        driver.get(url);
    }

    public void login(String user, String pass) {
        type(usernameInput, user);
        type(passwordInput, pass);
        click(loginButton);
    }

    public boolean isErrorVisible() {
        try {
            return !getText(loginError).isEmpty();
        } catch (Exception e) {
            return false;
        }
    }
}

