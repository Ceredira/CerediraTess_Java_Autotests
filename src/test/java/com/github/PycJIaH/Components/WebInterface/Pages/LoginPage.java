package com.github.PycJIaH.Components.WebInterface.Pages;

import com.github.PycJIaH.Components.WebInterface.Authorization;
import org.apache.log4j.chainsaw.Main;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class LoginPage {
    protected WebDriver driver;

    final Logger log = LoggerFactory.getLogger(LoginPage.class);

    // Локатор поля "Логин"
    private By usernameBy = new By.ByXPath("//*[@id=\"username\"]");

    // Локатор поля "Пароль"
    private By passwordBy = new By.ByXPath("//*[@id=\"password\"]");

    // Локатор Чек-бокса "Запомнить меня"
    private By rememberBy = new By.ByXPath("//*[@id=\"remember\"]");

    // Локатор Кнопки "Войти"
    private By submitBy = new By.ByXPath("//*[@id=\"submit\"]");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void load() {
        log.info("Перейти на страницу http://{url}:7801/");
        driver.get("http://192.168.242.128:7801/");
    }

    public void setUsername(String username) {
        log.info("В поле \"Логин\" вводим значение \"" + username + "\"");
        driver.findElement(usernameBy).sendKeys(username);
    }

    public void setPassword(String password) {
        log.info("В поле \"Пароль\" вводим значение \"" + password + "\"");
        driver.findElement(passwordBy).sendKeys(password);
    }

    public void clickSubmit() {
        log.info("Нажать на кнопку \"Войти\"");
        driver.findElement(submitBy).click();
    }

    public MainPage clickSubmitNewPage() {
        log.info("Нажать на кнопку \"Войти\"");
        driver.findElement(submitBy).click();

        return new MainPage(driver);
    }

    public void usernameIsDisplayed() {
        log.info("Поле \"Логин\" видимо");
        assertTrue(driver.findElement(usernameBy).isDisplayed(), "Поле \"Логин\" отсутствует");
    }

    public void passwordIsDisplayed() {
        log.info("Поле \"Пароль\" видимо");
        assertTrue(driver.findElement(passwordBy).isDisplayed(), "Поле \"Пароль\" отсутстует");
    }

    public void rememberIsSelected() {
        log.info("Есть неактивный чекбокс \"Запомнить меня\"");
        assertFalse(driver.findElement(rememberBy).isSelected(), "Чек бокс \"Запомнить меня\" выделен, хотя не должен быть");
    }

    public void submitIsDisplayed() {
        log.info("Есть кнопка \"Войти\"");
        assertTrue(driver.findElement(submitBy).isDisplayed(), "Кнопка \"Войти\" отсутстует");
    }

    public void usernameCheckRequired() {
        log.info("Рядом с полем \"Логин\" появилось сообщение об ошибке \"Заполните это поле\" - так как проверка текста не представляется возможным, данная проверка только на наличие тултипы \"Заполните это поле\"");
        assertTrue(isAttributePresent(driver.findElement(usernameBy), "required"), "Атрибут required у поля Логин отсуствует");
    }

    public void passwordCheckRequired() {
        log.info("Рядом с полем \"Пароль\" появилось сообщение об ошибке \"Заполните это поле\" - так как проверка текста не представляется возможным, данная проверка только на наличие тултипы \"Заполните это поле\"");
        assertTrue(isAttributePresent(driver.findElement(passwordBy), "required"), "Атрибут required у поля Пароль отсуствует");
    }

    public void checkTitle() {
        log.info("Заголовок страницы равен \"Вход в систему\"");
        String expectedTitle = "Вход в систему";
        String actualTitle = driver.getTitle();
        assertEquals(actualTitle, expectedTitle);
    }

    public void checkHeader() {
        log.info("Шапка страницы равна \"Вход в систему\"");
        String hatHomePage = "Вход в систему";
        String actualHatHomePage = driver.findElement(new By.ByXPath("//b[text()='Вход в систему']")).getText();
        assertEquals(hatHomePage, actualHatHomePage);
    }

    public void invalidPasswordMessage() {
        log.info("Появилось сообщение об ошибке \"Invalid password\"");
        String expectedErrorMessage = "Invalid password";
        String actualErrorMessage = driver.findElement(new By.ByXPath("//li[text()='Invalid password']")).getText();
        assertEquals(expectedErrorMessage, actualErrorMessage);
    }

    public void userDoesNotExistMessage() {
        log.info("Появилось сообщение об ошибке \"Specified user does not exist\"");
        String expectedErrorMessage = "Specified user does not exist";
        String actualErrorMessage = driver.findElement(new By.ByXPath("//li[text()='Specified user does not exist']")).getText();
        assertEquals(expectedErrorMessage, actualErrorMessage);
    }

    public void permanentAuthorization() {
        load();
        setUsername("admin");
        setPassword("admin");
        clickSubmitNewPage();
    }

    private boolean isAttributePresent(WebElement element, String attribute) {
        Boolean result = false;
        try {
            String value = element.getAttribute(attribute);
            if (value != null) {
                result = true;
            }
        } catch (Exception e) {
        }
        return result;
    }

    private void selectCheckBox(WebElement el, boolean isSelected) {
        boolean check = el.isSelected();
        if (check != isSelected) {
            el.click();
        }
    }
}
