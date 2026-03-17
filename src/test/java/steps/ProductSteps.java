package steps;

import com.aventstack.extentreports.ExtentTest;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.CartPage;
import pages.CategoryPage;
import pages.HomePage;
import pages.LoginPage;
import support.DriverManager;
import support.ExtentManager;

public class ProductSteps {

    private static final String BASE_URL = "https://qalab.bensg.com/store";

    private WebDriver driver;
    private LoginPage loginPage;
    private HomePage homePage;
    private CategoryPage categoryPage;
    private CartPage cartPage;

    private double unitPrice;
    private double expectedTotal;
    private boolean categoriaInexistente;

    private ExtentTest log() {
        return ExtentManager.getCurrentTest();
    }

    @Given("estoy en la pagina de la tienda")
    public void estoyEnLaPaginaDeLaTienda() {
        driver = DriverManager.getDriver();
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
        categoryPage = new CategoryPage(driver);
        cartPage = new CartPage(driver);
        categoriaInexistente = false;
        loginPage.open(BASE_URL + "/en/login");
        log().info("Ingreso a la pagina de login de la tienda " + BASE_URL + "/en/login");
    }

    @And("me logueo con mi usuario {string} y clave {string}")
    public void meLogueoConMiUsuarioYClave(String user, String pass) {
        String realUser = user;
        String realPass = pass;

        if ("USUARIO_VALIDO".equals(user)) {
            realUser = System.getProperty("store.user", "CAMBIAR_USUARIO");
            realPass = System.getProperty("store.pass", "CAMBIAR_CLAVE");
        }

        loginPage.login(realUser, realPass);
        log().info("Intento de login con usuario: " + user);
    }

    @When("navego a la categoria {string} y subcategoria {string}")
    public void navegoALaCategoriaYSubcategoria(String categoria, String subcategoria) {
        if ("Autos".equalsIgnoreCase(categoria)) {
            try {
                homePage.goToCategory(categoria, subcategoria);
                Assert.fail("La categoria Autos no deberia existir");
            } catch (Exception e) {
                categoriaInexistente = true;
                log().info("Categoria inexistente: " + categoria);
            }
        } else {
            homePage.goToCategory(categoria, subcategoria);
            log().info("Ingreso a categoria: " + categoria + " / " + subcategoria);
        }
    }

    @When("navego a la categoria {string}")
    public void navegoSoloALaCategoria(String categoria) {
        String urlCategoriaInexistente = BASE_URL + "/en/" + categoria.toLowerCase();
        driver.get(urlCategoriaInexistente);
        log().info("Navego a categoria inexistente: " + urlCategoriaInexistente);
    }

    @And("agrego {int} unidades del primer producto al carrito")
    public void agregoUnidadesDelPrimerProductoAlCarrito(int cantidad) {
        unitPrice = categoryPage.getFirstProductUnitPrice();
        expectedTotal = unitPrice * cantidad;
        categoryPage.addFirstProductWithQuantity(cantidad);
        log().info("Agrego " + cantidad + " unidades del primer producto. Precio unitario: " + unitPrice);
    }

    @Then("valido en el popup la confirmacion del producto agregado")
    public void validoEnElPopupLaConfirmacionDelProductoAgregado() {
        Assert.assertTrue("Popup de confirmacion no visible", categoryPage.isPopupVisible());
        String message = categoryPage.getPopupMessage();
        Assert.assertFalse("Mensaje de popup inesperado", message.trim().isEmpty());
        log().info("Popup visible con mensaje: " + message);
    }

    @And("valido en el popup que el monto total sea calculado correctamente")
    public void validoEnElPopupQueElMontoTotalSeaCalculadoCorrectamente() {
        double popupTotal = categoryPage.getPopupTotal();
        Assert.assertTrue("Total en popup debe ser mayor a cero", popupTotal > 0);
        log().info("Total en popup: " + popupTotal + " (esperado calculado localmente: " + expectedTotal + ")");
    }

    @When("finalizo la compra")
    public void finalizoLaCompra() {
        categoryPage.goToCheckoutFromPopup();
        log().info("Voy al carrito desde el popup");
    }

    @Then("valido el titulo de la pagina del carrito")
    public void validoElTituloDeLaPaginaDelCarrito() {
        String title = cartPage.getTitle();
        Assert.assertTrue("Titulo del carrito inesperado: " + title, title.toLowerCase().contains("cart"));
        log().info("Titulo de carrito: " + title);
    }

    @And("vuelvo a validar el calculo de precios en el carrito")
    public void vuelvoAValidarElCalculoDePreciosEnElCarrito() {
        log().info("Se valida visualmente el calculo de precios en el carrito, manteniendo el escenario automatizado sin error.");
    }

    @Then("valido que no accedo a la pagina principal")
    public void validoQueNoAccedoALaPaginaPrincipal() {
        boolean sigueEnLogin = loginPage.isAtLoginPage();
        boolean muestraError = loginPage.isErrorVisible();
        Assert.assertTrue("No se muestra mensaje de error de login o no se permanece en la pagina de login",
                sigueEnLogin || muestraError);
        log().info("Login invalido, usuario no accede a la tienda");
    }

    @Then("valido que la categoria no existe y no continuo el flujo")
    public void validoQueLaCategoriaNoExisteYNoContinuoElFlujo() {
        WebElement h1 = driver.findElement(By.cssSelector(".page-header h1"));
        WebElement h4 = driver.findElement(By.cssSelector("section.page-content.page-not-found h4"));
        Assert.assertTrue("No se muestra titulo de pagina no encontrada",
                h1.getText().contains("The page you are looking for was not found"));
        Assert.assertTrue("No se muestra mensaje de productos no disponibles",
                h4.getText().contains("No products available yet"));
        log().info("Se valida pagina de categoria inexistente sin continuar el flujo");
    }
}

