package testclasses.e2e;

import PageComponents.LoginForm;
import PageComponents.OrderForm;
import com.demoblaze.CartPage;
import com.demoblaze.HomePage;
import io.qameta.allure.*;
import org.testng.annotations.Test;
import utils.models.E2eTestData;
import utils.models.LoginTestData;

import static utilities.common.assertions.AssertionManager.assertFalse;
import static utilities.common.assertions.AssertionManager.assertTrue;

@Feature( "User Buy Items Feature")
public class UserBuyItemsTest {
    E2eTestData testData;
    LoginTestData validLoginCredentials;
    E2eTestData.OrderForm orderFormData;

    public UserBuyItemsTest(E2eTestData e2eTestData, LoginTestData loginTestData){
        this.testData=e2eTestData;
        this.validLoginCredentials=loginTestData;
        orderFormData = testData.orderForm;
    }


    @Story("Complete purchase of selected products")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verifies that a user can log in, add products to the cart, place an order, and complete a successful purchase.")
    @Test(groups = "E2E")
    public void testProductsPurchase() {
        HomePage homePage = new HomePage();
        homePage.load();
        LoginForm<HomePage> loginForm = homePage.navBar.clickLogin();
        homePage= loginForm.login(validLoginCredentials.username, validLoginCredentials.password);
        assertTrue(homePage.navBar.isLoggedIn());
        homePage.addItemsToCart(testData.getProductsNames());
        CartPage cartPage = homePage.navBar.clickCart();
        OrderForm orderForm = cartPage.clickPlaceOrder();
        orderForm.fillOrderForm(orderFormData.name, orderFormData.country,
                orderFormData.city, orderFormData.card, orderFormData.month,
                orderFormData.year);
        orderForm.clickPurchase();
        assertTrue(orderForm.isSuccessful(), "Order was not successful.");
        homePage= orderForm.clickOk();
        homePage= homePage.navBar.clickLogOut();
        assertFalse(homePage.navBar.isLoggedIn(), "User was not logged out.");
    }
}
