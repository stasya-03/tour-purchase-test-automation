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
}
