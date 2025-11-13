package ru.netology.web.data;

import lombok.Value;


public class DataHelper {

    private DataHelper() {
    }

    public static CardInfo getApprovedCard() {
        return new CardInfo("1111 2222 3333 4444", "12", "26", "IVAN IVANOV", "123");
    }

    public static CardInfo getDeclinedCard() {
        return new CardInfo("5555 6666 7777 8888", "12", "26", "IVAN IVANOV", "123");
    }

    public static CardInfo getInvalidCardNumberLetters() {
        return new CardInfo("abcd efgh ijkl mnop", "12", "25", "IVAN IVANOV", "123");
    }

    public static CardInfo getInvalidMonthLetters() {
        return new CardInfo("4444 4444 4444 4444", "aa", "25", "IVAN IVANOV", "123");
    }

    public static CardInfo getInvalidYearLetters() {
        return new CardInfo("4444 4444 4444 4444", "12", "yy", "IVAN IVANOV", "123");
    }

    public static CardInfo getInvalidOwnerDigits() {
        return new CardInfo("4444 4444 4444 4444", "12", "25", "12345", "123");
    }

    public static CardInfo getInvalidCvcLetters() {
        return new CardInfo("4444 4444 4444 4444", "12", "25", "IVAN IVANOV", "abc");
    }



    @Value
    public static class CardInfo {
        String cardNumber;
        String month;
        String year;
        String owner;
        String cvc;
    }


}
