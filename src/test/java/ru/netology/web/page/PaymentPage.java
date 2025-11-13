package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class PaymentPage {

    private SelenideElement numberField = $("[placeholder='0000 0000 0000 0000']");
    private SelenideElement monthField = $("[placeholder='08']");
    private SelenideElement yearField = $("[placeholder='22']");
    private SelenideElement ownerField = $$("input.input__control").get(3);
    private SelenideElement cvcField = $("[placeholder='999']");
    private SelenideElement continueButton = $$("button.button").find(exactText("Продолжить"));

    private final SelenideElement notification = $(".notification");
    private final SelenideElement notificationTitle = notification.$(".notification__title");
    private final SelenideElement notificationContent = notification.$(".notification__content");

    public void fillForm(DataHelper.CardInfo info) {
        numberField.setValue(info.getCardNumber());
        monthField.setValue(info.getMonth());
        yearField.setValue(info.getYear());
        ownerField.setValue(info.getOwner());
        cvcField.setValue(info.getCvc());
        continueButton.click();
    }

    public void successMessage() {
        notificationTitle.shouldHave(text("Успешно"), Duration.ofSeconds(15)).shouldBe(visible);
        notificationContent.shouldHave(text("Операция одобрена Банком"), Duration.ofSeconds(15)).shouldBe(visible);
    }

    public void errorMessage() {
        notificationTitle.shouldHave(text("Ошибка"), Duration.ofSeconds(15)).shouldBe(visible);
        notificationContent.shouldHave(text("Банк отказал в проведении операции"), Duration.ofSeconds(15)).shouldBe(visible);
    }

}
