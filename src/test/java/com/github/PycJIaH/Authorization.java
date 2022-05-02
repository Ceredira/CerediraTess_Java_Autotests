package com.github.PycJIaH;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class Authorization {

    WebDriver driver;

    @Test
    @DisplayName("1. Проверка входа администратора")
    public void check_admin_authorization() {
        driver = new ChromeDriver();
        System.setProperty("webdriver.chrome.driver", "C:\\WebDriver\\bin\\chromedriver.exe");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        try {
            //Перейти на страницу http://{url}:7801/
            driver.get("http://192.168.242.128:7801/");
            //Заголовок страницы равен "Вход в систему"
            String expectedTitle = "Вход в систему";
            String actualTitle = driver.getTitle();
            assertEquals(actualTitle, expectedTitle);
            //Шапка страницы равна "Вход в систему"
            String hatHomePage = "Вход в систему";
            String actualHatHomePage = driver.findElement(new By.ByXPath("/html/body/div[1]/div[1]/div/h4/b")).getText();
            assertEquals(hatHomePage, actualHatHomePage);
            // Поле "Логин"
            WebElement login = driver.findElement(new By.ByXPath("//*[@id=\"username\"]"));
            //Есть поле "Логин"
            assertTrue(login.isDisplayed(), "Поле Логин отсутствует");
            //Есть поле "Пароль"
            assertTrue(driver.findElement(new By.ByXPath("//*[@id=\"password\"]")).isDisplayed(), "Поле Пароль отсутстует");
            //Есть неактивный чекбокс "Запомнить меня"
            assertFalse(driver.findElement(new By.ByXPath("//*[@id=\"remember\"]")).isSelected(), "Чек бокс Запомнить меня выделен, хотя не должен быть");


            //selectCheckBox(driver.findElement(new By.ByXPath("//*[@id=\"remember\"]")), false);

            //В поле "Логин" вводим значение "admin"
            login.sendKeys("admin");
            //В поле "Пароль" вводим значение "admin"
            driver.findElement(new By.ByXPath("//*[@id=\"password\"]")).sendKeys("admin");
            //Нажать на кнопку "Войти"
            driver.findElement(new By.ByXPath("//*[@id=\"submit\"]")).click();


        } finally {
            driver.findElement(new By.ByLinkText("Выполнение запросов"));
        }
    }

    private void selectCheckBox(WebElement el, boolean isSelected) {
        boolean check = el.isSelected();
        if (check != isSelected) {
            el.click();
        }
    } //selectCheckBox(driver.findElement(new By.ByXPath("//*[@id=\"remember\"]")), true);

//    @AfterEach
//    public void close_chrome() {
//        if (driver != null) {
//            driver.quit();
//        }
}

