package com.github.PycJIaH.Components.WebInterface;

import com.github.PycJIaH.Components.WebInterface.Auxiliary_functionality.Roles;
import com.github.PycJIaH.Components.WebInterface.Auxiliary_functionality.Users;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class Authorization {

    final Logger log = LoggerFactory.getLogger(Authorization.class);
    WebDriver driver;

    @Test
    @DisplayName("1. Проверка входа администратора")
    public void check_admin_authorization() {
        System.setProperty("webdriver.chrome.driver", "C:\\WebDriver\\bin\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

        try {
            log.info("1. Перейти на страницу http://{url}:7801/");
            driver.get("http://192.168.242.128:7801/");
            // Локатор поля "Логин"
            WebElement login = driver.findElement(new By.ByXPath("//*[@id=\"username\"]"));
            // Локатор поля "Пароль"
            WebElement password = driver.findElement(new By.ByXPath("//*[@id=\"password\"]"));
            // Локатор Чек-бокса "Запомнить меня"
            WebElement checkBox = driver.findElement(new By.ByXPath("//*[@id=\"remember\"]"));
            // Локатор Кнопки "Войти"
            WebElement submit = driver.findElement(new By.ByXPath("//*[@id=\"submit\"]"));
            log.info("1.1. Заголовок страницы равен \"Вход в систему\"");
            String expectedTitle = "Вход в систему";
            String actualTitle = driver.getTitle();
            assertEquals(actualTitle, expectedTitle);
            log.info("1.2. Шапка страницы равна \"Вход в систему\"");
            String hatHomePage = "Вход в систему";
            String actualHatHomePage = driver.findElement(new By.ByXPath("//b[text()='Вход в систему']")).getText();
            assertEquals(hatHomePage, actualHatHomePage);
            log.info("1.3. Есть поле \"Логин\"");
            assertTrue(login.isDisplayed(), "Поле \"Логин\" отсутствует");
            log.info("1.4. Есть поле \"Пароль\"");
            assertTrue(password.isDisplayed(), "Поле \"Пароль\" отсутстует");
            log.info("1.5. Есть неактивный чекбокс \"Запомнить меня\"");
            assertFalse(checkBox.isSelected(), "Чек бокс \"Запомнить меня\" выделен, хотя не должен быть");
            log.info("1.6. Есть кнопка \"Войти\"");
            assertTrue(submit.isDisplayed(), "Кнопка \"Войти\" отсутстует");
            log.info("2. В поле \"Логин\" вводим значение \"admin\"");
            login.sendKeys("admin");
            log.info("3. В поле \"Пароль\" вводим значение \"admin\"");
            password.sendKeys("admin");
            log.info("4. Нажать на кнопку \"Войти\"");
            submit.click();

            //Следующая страница:
            //Локатор "Выполнение запросов"
            WebElement performingRequests = driver.findElement(new By.ByXPath("//*[@id=\"navbarSupportedContent\"]//a[normalize-space()='Выполнение запросов']"));
            //Локатор "Блокировка агентов"
            WebElement blockAgents = driver.findElement(new By.ByXPath("//*[@id=\"navbarSupportedContent\"]//a[normalize-space()='Блокировка агентов']"));
            //Локатор кнопки "Обовить скрипты"
            WebElement updateScriptsButton = driver.findElement(new By.ByXPath("//*[@id=\"updateCT\"]"));
            //Локатор кнопки "admin - Выход"
            WebElement adminExitButton = driver.findElement(new By.ByXPath("//*[@id=\"navbarSupportedContent\"]//a[normalize-space()='admin - Выход']"));
            log.info("4.1. В главном меню есть пункт \"Выполнение запросов\"");
            assertTrue(performingRequests.isDisplayed(), "Нет пункта \"Выполнение запросов\"");
            log.info("4.2. В главном меню есть пункт \"Блокировка агентов\"");
            assertTrue(blockAgents.isDisplayed(), "Нет пункта \"Блокировка агентов\"");
            log.info("4.3. Заголовок страницы равен \"CerediraTess - Выполнение запросов\"");
            String expectedTitle2 = "CerediraTess - Выполнение запросов";
            String actualTitle2 = driver.getTitle();
            assertEquals(actualTitle2, expectedTitle2);
            log.info("4.4. На странице есть кнопка \"Обновить скрипты\"");
            assertTrue(updateScriptsButton.isDisplayed(), "Нет кнопки \"Обновить скрипты\"");
            log.info("4.5. В главном меню есть пункт \"admin - Выход\"");
            assertTrue(adminExitButton.isDisplayed(), "Нет кнопки \"admin - Выход\"");
            log.info("5. Нажать на пункт меню \"admin - Выход\"");
            adminExitButton.click();

        } finally {
            driver.quit();
        }
    }

    @Test
    @DisplayName("2. Ошибка входа. Неправильный пароль")
    public void check_wrong_password_authorization() {
        System.setProperty("webdriver.chrome.driver", "C:\\WebDriver\\bin\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

        try {
            log.info("1. Перейти на страницу http://{url}:7801/");
            driver.get("http://192.168.242.128:7801/");
            // Локатор поля "Логин"
            WebElement login = driver.findElement(new By.ByXPath("//*[@id=\"username\"]"));
            // Локатор поля "Пароль"
            WebElement password = driver.findElement(new By.ByXPath("//*[@id=\"password\"]"));
            // Локатор Кнопки "Войти"
            WebElement submit = driver.findElement(new By.ByXPath("//*[@id=\"submit\"]"));
            log.info("2. В поле \"Логин\" вводим значение \"admin\"");
            login.sendKeys("admin");
            log.info("3. В поле \"Пароль\" вводим значение \"1234\"");
            password.sendKeys("1234");
            log.info("4. Нажать на кнопку \"Войти\"");
            submit.click();
            log.info("5. Появилось сообщение об ошибке \"Invalid password\"");
            String expectedErrorMessage = "Invalid password";
            String actualErrorMessage = driver.findElement(new By.ByXPath("//li[text()='Invalid password']")).getText();
            assertEquals(expectedErrorMessage, actualErrorMessage);
            log.info("6. Шапка страницы равна \"Вход в систему\"");
            String hatHomePage = "Вход в систему";
            String actualHatHomePage = driver.findElement(new By.ByXPath("//b[text()='Вход в систему']")).getText();
            assertEquals(hatHomePage, actualHatHomePage);

        } finally {
            driver.quit();
        }

    }

    @Test
    @DisplayName("3. Ошибка входа. Пользователь не существует")
    public void check_wrong_login_authorization() {
        System.setProperty("webdriver.chrome.driver", "C:\\WebDriver\\bin\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

        try {
            log.info("1. Перейти на страницу http://{url}:7801/");
            driver.get("http://192.168.242.128:7801/");
            // Локатор поля "Логин"
            WebElement login = driver.findElement(new By.ByXPath("//*[@id=\"username\"]"));
            // Локатор поля "Пароль"
            WebElement password = driver.findElement(new By.ByXPath("//*[@id=\"password\"]"));
            // Локатор Кнопки "Войти"
            WebElement submit = driver.findElement(new By.ByXPath("//*[@id=\"submit\"]"));
            log.info("2. В поле \"Логин\" вводим значение \"admin23\"");
            login.sendKeys("admin23");
            log.info("3. В поле \"Пароль\" вводим значение \"1234\"");
            password.sendKeys("1234");
            log.info("4. Нажать на кнопку \"Войти\"");
            submit.click();
            log.info("5. Появилось сообщение об ошибке \"Specified user does not exist\"");
            String expectedErrorMessage = "Specified user does not exist";
            String actualErrorMessage = driver.findElement(new By.ByXPath("//li[text()='Specified user does not exist']")).getText();
            assertEquals(expectedErrorMessage, actualErrorMessage);
            log.info("6. Шапка страницы равна \"Вход в систему\"");
            String hatHomePage = "Вход в систему";
            String actualHatHomePage = driver.findElement(new By.ByXPath("//b[text()='Вход в систему']")).getText();
            assertEquals(hatHomePage, actualHatHomePage);

        } finally {
            driver.quit();
        }

    }

    @Test
    @DisplayName("4. Ошибка входа. Параметр Логин пустой")
    public void check_login_empty_authorization() {
        System.setProperty("webdriver.chrome.driver", "C:\\WebDriver\\bin\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

        try {
            log.info("1. Перейти на страницу http://{url}:7801/");
            driver.get("http://192.168.242.128:7801/");
            // Локатор Кнопки "Войти"
            WebElement submit = driver.findElement(new By.ByXPath("//*[@id=\"submit\"]"));
            // Локатор поля тултипы "Заполнить это поле"
            WebElement tooltip = driver.findElement(new By.ByXPath("//*[@id=\"username\"]"));
            log.info("2. Нажать на кнопку \"Войти\"");
            submit.click();
            log.info("3. Появилось сообщение об ошибке \"Заполните это поле\" - так как проверка текста не представляется возможным, данная проверка только на наличие тултипы \"Заполните это поле\"");
            assertTrue(isAttributePresent(tooltip, "required"), "Атрибут required у поля Логин отсуствует");
            log.info("4. Шапка страницы равна \"Вход в систему\"");
            String hatHomePage = "Вход в систему";
            String actualHatHomePage = driver.findElement(new By.ByXPath("//b[text()='Вход в систему']")).getText();
            assertEquals(hatHomePage, actualHatHomePage);

        } finally {
            driver.quit();
        }

    }

    @Test
    @DisplayName("5. Ошибка входа. Параметр Пароль пустой")
    public void check_password_empty_authorization() {
        System.setProperty("webdriver.chrome.driver", "C:\\WebDriver\\bin\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

        try {
            log.info("1. Перейти на страницу http://{url}:7801/");
            driver.get("http://192.168.242.128:7801/");
            // Локатор поля "Логин"
            WebElement login = driver.findElement(new By.ByXPath("//*[@id=\"username\"]"));
            // Локатор Кнопки "Войти"
            WebElement submit = driver.findElement(new By.ByXPath("//*[@id=\"submit\"]"));
            // Локатор поля тултипы "Заполнить это поле"
            WebElement tooltip = driver.findElement(new By.ByXPath("//*[@id=\"password\"]"));
            log.info("2. В поле \"Логин\" вводим значение \"admin\"");
            login.sendKeys("admin");
            log.info("3. Нажать на кнопку \"Войти\"");
            submit.click();
            log.info("4. Появилось сообщение об ошибке \"Заполните это поле\" - так как проверка текста не представляется возможным, данная проверка только на наличие тултипы \"Заполните это поле\"");
            assertTrue(isAttributePresent(tooltip, "required"), "Атрибут required у поля Пароль отсуствует");
            log.info("5. Шапка страницы равна \"Вход в систему\"");
            String hatHomePage = "Вход в систему";
            String actualHatHomePage = driver.findElement(new By.ByXPath("//b[text()='Вход в систему']")).getText();
            assertEquals(hatHomePage, actualHatHomePage);

        } finally {
            driver.quit();
        }

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
    } //selectCheckBox(driver.findElement(new By.ByXPath("//*[@id=\"remember\"]")), true);

//    @AfterEach
//    public void close_chrome() {
//        if (driver != null) {
//            driver.quit();
//        }
}

