package com.github.PycJIaH.Components.WebInterface;

import com.github.PycJIaH.Components.WebInterface.Pages.LoginPage;
import com.github.PycJIaH.Components.WebInterface.Pages.MainPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;


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
        MainPage mp = lp.clickSubmitNewPage();

        //Следующая страница:
        mp.performingRequestsIsDisplayed();
        mp.blockAgentsIsDisplayed();
        mp.expectedTitleIsDisplayed();
        mp.updateScriptsButtonIsDisplayed();
        mp.adminExitButtonIsDisplayed();
        mp.clickAdminExitButton();
    }

    @Test
    @DisplayName("2. Ошибка входа. Неправильный пароль")
    public void check_wrong_password_authorization() {
        LoginPage lp = new LoginPage(driver);

        lp.load();
        lp.setUsername("admin");
        lp.setPassword("1234");
        lp.clickSubmit();
        lp.invalidPasswordMessage();
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
        lp.userDoesNotExistMessage();
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

