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

    private final SelenideElement numberInvalid = $$("span.input__inner").get(0);
    private final SelenideElement monthInvalid = $$("span.input__inner").get(1);
    private final SelenideElement yearInvalid = $$("span.input__inner").get(2);
    private final SelenideElement ownerInvalid = $$("span.input__inner").get(3);
    private final SelenideElement cvcInvalid = $$("span.input__inner").get(4);

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

    public void cardNumberInvalid() {
        numberInvalid.shouldHave(text("Неверный формат"));
    }

    public void cardNumberIsEmpty() {
        numberInvalid.shouldHave(text("Поле обязательно для заполнения"));
    }

    public void monthInvalid() {
        monthInvalid.shouldHave(exactText("Неверный формат"));
    }

    public void exceedingMonth() {
        monthInvalid.shouldHave(text("Неверно указан срок действия карты"));
    }

    public void monthIsEmpty() {
        monthInvalid.shouldHave(text("Поле обязательно для заполнения"));
    }

    public void expiredYear() {
        yearInvalid.shouldHave(text("Истёк срок действия карты"));
    }

    public void yearInvalid() {
        yearInvalid.shouldHave(text("Неверный формат"));
    }

    public void yearIsEmpty() {
        yearInvalid.shouldHave(text("Поле обязательно для заполнения"));
    }

    public void ownerInvalid() {
        ownerInvalid.shouldHave(text("Неверный формат"));
    }

    public void ownerIsEmpty() {
        ownerInvalid.shouldHave(text("Поле обязательно для заполнения"));
    }

    public void cvcInvalid() {
        cvcInvalid.shouldHave(text("Неверный формат"));
    }

    public void cvcIsEmpty() {
        cvcInvalid.shouldHave(text("Поле обязательно для заполнения"));
    }


}
