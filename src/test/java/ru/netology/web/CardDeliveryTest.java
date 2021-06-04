package ru.netology.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999/");
    }

    @Test
    void shouldSendCorrectForm() {
        String orderDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id='city'] input").setValue("Вологда");
        $(byText("Вологда")).click();
        $("[data-test-id=date] input").doubleClick().sendKeys(orderDate);
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79990009900");
        $(".checkbox__box").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=notification]").shouldHave(text("Встреча успешно забронирована на " + orderDate), Duration.ofSeconds(15));
    }

    @Test
    void shouldSendEmptyForm() {
        $$("button").find(exactText("Забронировать")).click();
        $(".input_invalid .input__sub").shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    void shouldSendFormWithIncorrectCity() {
        String orderDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id='city'] input").setValue("Копенгаген");
        $("[data-test-id=date] input").doubleClick().sendKeys(orderDate);
        $("[data-test-id=name] input").setValue("Федотов Иван");
        $("[data-test-id=phone] input").setValue("+79998889900");
        $(".checkbox__box").click();
        $$("button").find(exactText("Забронировать")).click();
        $(".input_invalid .input__sub").shouldHave(text("Доставка в выбранный город недоступна"));
    }
    @Test
    void shouldSendFormWithIncorrectDate() {
        String orderDate = LocalDate.now().plusDays(2).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id='city'] input").setValue("Вологда");
        $(byText("Вологда")).click();
        $("[data-test-id=date] input").doubleClick().sendKeys(orderDate);
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79998889900");
        $(".checkbox__box").click();
        $$("button").find(exactText("Забронировать")).click();
        $(".input_invalid .input__sub").shouldHave(text("Заказ на выбранную дату невозможен"));
    }
    @Test
    void shouldSendFormWithIncorrectName() {
        String orderDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id='city'] input").setValue("Вологда");
        $(byText("Вологда")).click();
        $("[data-test-id=date] input").doubleClick().sendKeys(orderDate);
        $("[data-test-id=name] input").setValue("Ivanov");
        $("[data-test-id=phone] input").setValue("+79998889900");
        $(".checkbox__box").click();
        $$("button").find(exactText("Забронировать")).click();
        $(".input_invalid .input__sub").shouldHave(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldSendFormWithIncorrectPhone() {
        String orderDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id='city'] input").setValue("Вологда");
        $(byText("Вологда")).click();
        $("[data-test-id=date] input").doubleClick().sendKeys(orderDate);
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("79998889900");
        $(".checkbox__box").click();
        $$("button").find(exactText("Забронировать")).click();
        $(".input_invalid .input__sub").shouldHave(text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldSendFormWithoutAgreement() {
        String orderDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id='city'] input").setValue("Вологда");
        $(byText("Вологда")).click();
        $("[data-test-id=date] input").doubleClick().sendKeys(orderDate);
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79998889900");
        $$("button").find(exactText("Забронировать")).click();
        $(".input_invalid .checkbox__text").shouldHave(text("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }
}
