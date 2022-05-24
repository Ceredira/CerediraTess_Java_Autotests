package com.github.PycJIaH.Components.WebInterface.Auxiliary_functionality;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class Scripts {
    final Logger log = LoggerFactory.getLogger(Roles.class);
    WebDriver driver;

    @Test
    @DisplayName("1. Создание скрипта (новой записи)")
    public void createScriptNewEntry() {

        try {
            log.info("1. Войти на сайт с пользователем \"admin\"");
            permanentAuthorization();
            // Локатор раздела "Администрирование"
            WebElement administration = driver.findElement(new By.ByXPath("//a[normalize-space()='Администрирование']"));
            // Локатор раздела "Скрипты"
            WebElement scripts = driver.findElement(new By.ByXPath("//a[normalize-space()='Скрипты']"));
            log.info("2. В главном меню перейти в раздел \"Администрирование -> Скрипты\"");
            administration.click();
            scripts.click();
            //Локатор кнопки "Создать"
            WebElement createButton = driver.findElement(new By.ByXPath("//a[normalize-space()='Создать']"));
            log.info("3. Нажать на вкладку \"Создать\"");
            createButton.click();
            log.info("4. В поле \"Агенты\" выбрать значение \"CerediraTess\" - - Поле отсутствует в данном релизе программы");
            //Локатор поля "Имя скрипта"
            WebElement scriptName = driver.findElement(new By.ByXPath("//*[@id=\"name\"]"));
            log.info("5. В поле \"Имя скрипта\" ввести значение \"test_1.bat\"");
            String scriptNameValue = "test_" + new Random().ints(0, 100).findFirst().getAsInt() + ".bat";
            scriptName.sendKeys(scriptNameValue);
            //Локатор поля "Описание"
            WebElement descriptionScript = driver.findElement(new By.ByXPath("//*[@id=\"description\"]"));
            log.info("6. В поле \"Описание\" ввести значение \"Описание скрипта\"");
            descriptionScript.sendKeys("Описание скрипта");
            //Локатор кнопки "Сохранить"
            WebElement saveButton = driver.findElement(new By.ByXPath("//*[@id=\"fa_modal_window\"]/..//input[@value='Сохранить']"));
            log.info("7. Нажать на кнопку \"Сохранить\"");
            saveButton.click();
            log.info("8. В таблице \"Список\" отображается строка со значением \"test_1.bat\" в столбце \"Имя скрипта\"");
            assertTrue(driver.findElement(new By.ByXPath("//td[@class='col-name' and normalize-space()='" + scriptNameValue + "']")).isDisplayed());

        } finally {
            driver.quit();
        }
    }

    private void permanentAuthorization() {
        System.setProperty("webdriver.chrome.driver", "C:\\WebDriver\\bin\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

        try {
            log.info("    1. Перейти на страницу http://{url}:7801/");
            driver.get("http://192.168.242.128:7801/");
            // Локатор поля "Логин"
            WebElement login = driver.findElement(new By.ByXPath("//*[@id=\"username\"]"));
            // Локатор поля "Пароль"
            WebElement password = driver.findElement(new By.ByXPath("//*[@id=\"password\"]"));
            // Локатор Кнопки "Войти"
            WebElement submit = driver.findElement(new By.ByXPath("//*[@id=\"submit\"]"));
            log.info("    2. В поле \"Логин\" вводим значение \"admin\"");
            login.sendKeys("admin");
            log.info("    3. В поле \"Пароль\" вводим значение \"admin\"");
            password.sendKeys("admin");
            log.info("    4. Нажать на кнопку \"Войти\"");
            submit.click();

        } finally {
            ;
        }
    }
}
