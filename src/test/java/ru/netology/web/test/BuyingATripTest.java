package ru.netology.web.test;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.data.SQLHelper;
import ru.netology.web.page.DashboardPage;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BuyingATripTest {

    DashboardPage dashboardPage;

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SQLHelper.cleanDatabase();
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    void setup() {
        dashboardPage = Selenide.open("http://localhost:8080", DashboardPage.class);
    }

    @Test
    public void shouldBuyATripWithApprovedCard() {
        var dashboardPage = new DashboardPage();
        var paymentPage = dashboardPage.buyWithCard();
        var cardInfo = DataHelper.getApprovedCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.successMessage();
    }

    @Test
    public void shouldNotBuyATripWithDeclinedCard() {
        var dashboardPage = new DashboardPage();
        var paymentPage = dashboardPage.buyWithCard();
        var cardInfo = DataHelper.getDeclinedCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.errorMessage();
    }

    @Test
    public void shouldShowErrorMessageIfAllFieldsEmpty() {
        var paymentPage = dashboardPage.buyWithCard();
        var cardInfo = new DataHelper.CardInfo("", "", "", "", "");
        paymentPage.fillForm(cardInfo);
        paymentPage.errorMessage();
    }

    @Test
    public void shouldShowErrorMessageIfCardNumberInvalid() {
        var paymentPage = dashboardPage.buyWithCard();
        var cardInfo = new DataHelper.CardInfo("1234 5678 9012", "12", "25", "IVAN IVANOV", "123");
        paymentPage.fillForm(cardInfo);
        paymentPage.errorMessage();
    }

    @Test
    public void shouldShowErrorMessageIfExpiredYear() {
        var paymentPage = dashboardPage.buyWithCard();
        var cardInfo = new DataHelper.CardInfo("1111 2222 3333 4444", "12", "20", "IVAN IVANOV", "123");
        paymentPage.fillForm(cardInfo);
        paymentPage.errorMessage();
    }

    @Test
    public void shouldShowErrorIfOwnerEmpty() {
        var paymentPage = dashboardPage.buyWithCard();
        var cardInfo = new DataHelper.CardInfo("1111 2222 3333 4444", "12", "26", "", "123");
        paymentPage.fillForm(cardInfo);
        paymentPage.errorMessage();
    }

    @Test
    public void shouldShowErrorMessageIfCVCInvalid() {
        var paymentPage = dashboardPage.buyWithCard();
        var cardInfo = new DataHelper.CardInfo("1111 2222 3333 4444", "12", "26", "IVAN IVANOV", "1");
        paymentPage.fillForm(cardInfo);
        paymentPage.errorMessage();
    }

    @Test
    public void shouldSaveApprovedPaymentToDB() {
        var paymentPage = dashboardPage.buyWithCard();
        var cardInfo = DataHelper.getApprovedCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.successMessage();
        var status = SQLHelper.getPaymentStatus();
        assertEquals("APPROVED", status);
    }

}
