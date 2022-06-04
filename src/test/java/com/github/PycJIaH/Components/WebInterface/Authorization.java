package com.github.PycJIaH.Components.WebInterface;

import com.github.PycJIaH.Components.WebInterface.Pages.LoginPage;
import org.junit.jupiter.api.*;
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

    @BeforeAll
    public static void beforeAll() {
        System.setProperty("webdriver.chrome.driver", "C:\\WebDriver\\bin\\chromedriver.exe");
    }

    @BeforeEach
    public void beforeEach() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
    }

    @Test
    @DisplayName("1. Проверка входа администратора")
    public void check_admin_authorization() {
        LoginPage lp = new LoginPage(driver);

        lp.load();
        lp.checkTitle();
        lp.checkHeader();

        lp.usernameIsDisplayed();
        lp.passwordIsDisplayed();
        lp.rememberIsSelected();
        lp.submitIsDisplayed();
        lp.setUsername("admin");
        lp.setPassword("admin");
        lp.clickSubmit();

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
    }

    @Test
    @DisplayName("2. Ошибка входа. Неправильный пароль")
    public void check_wrong_password_authorization() {
        LoginPage lp = new LoginPage(driver);

        lp.load();
        lp.setUsername("admin");
        lp.setPassword("1234");
        lp.clickSubmit();
        log.info("5. Появилось сообщение об ошибке \"Invalid password\"");
        String expectedErrorMessage = "Invalid password";
        String actualErrorMessage = driver.findElement(new By.ByXPath("//li[text()='Invalid password']")).getText();
        assertEquals(expectedErrorMessage, actualErrorMessage);
        lp.checkHeader();
    }

    @Test
    @DisplayName("3. Ошибка входа. Пользователь не существует")
    public void check_wrong_login_authorization() {
        LoginPage lp = new LoginPage(driver);

        lp.load();
        lp.setUsername("admin23");
        lp.setPassword("1234");
        lp.clickSubmit();
        log.info("5. Появилось сообщение об ошибке \"Specified user does not exist\"");
        String expectedErrorMessage = "Specified user does not exist";
        String actualErrorMessage = driver.findElement(new By.ByXPath("//li[text()='Specified user does not exist']")).getText();
        assertEquals(expectedErrorMessage, actualErrorMessage);
        lp.checkHeader();
    }

    @Test
    @DisplayName("4. Ошибка входа. Параметр Логин пустой")
    public void check_login_empty_authorization() {
        LoginPage lp = new LoginPage(driver);

        lp.load();
        lp.clickSubmit();
        lp.usernameCheckRequired();
        lp.checkHeader();
    }

    @Test
    @DisplayName("5. Ошибка входа. Параметр Пароль пустой")
    public void check_password_empty_authorization() {
        LoginPage lp = new LoginPage(driver);

        lp.load();
        lp.setUsername("admin");
        lp.clickSubmit();
        lp.passwordCheckRequired();
        lp.checkHeader();
    }

    @AfterEach
    public void afterEach() {
        if (driver != null) {
            try {
                log.info("Закрытие браузера");
                driver.close();
                driver.quit();
            } catch (Exception ex) {
                log.error(ex.getMessage());
            }
        }
    }
}

