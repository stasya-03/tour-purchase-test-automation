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

    private SelenideElement successTitle = $(".notification__title");
    private SelenideElement successMessage = $(".notification__content");
    private SelenideElement errorTitle = $(".notification__title");
    private SelenideElement errorMessage = $(".notification__content");

    public void fillForm(DataHelper.CardInfo info) {
        numberField.setValue(info.getCardNumber());
        monthField.setValue(info.getMonth());
        yearField.setValue(info.getYear());
        ownerField.setValue(info.getOwner());
        cvcField.setValue(info.getCvc());
        continueButton.click();
    }

    public void successMessage() {
        successTitle.shouldHave(exactText("Успешно"), Duration.ofSeconds(15)).shouldBe(visible);
        successMessage.shouldHave(exactText("Операция одобрена Банком."), Duration.ofSeconds(15)).shouldBe(visible);
    }

    public void errorMessage() {
        errorTitle.shouldHave(exactText("Ошибка!"), Duration.ofSeconds(15)).shouldBe(visible);
        errorMessage.shouldHave(exactText("Ошибка! Банк отказал в проведении операции."), Duration.ofSeconds(15)).shouldBe(visible);
    }

}
