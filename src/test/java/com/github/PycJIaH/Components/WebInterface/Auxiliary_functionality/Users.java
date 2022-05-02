package com.github.PycJIaH.Components.WebInterface.Auxiliary_functionality;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Users {
    WebDriver driver;

    private void permanentAuthorization() {
        System.setProperty("webdriver.chrome.driver", "C:\\WebDriver\\bin\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

        try {
            //1. Перейти на страницу http://{url}:7801/
            driver.get("http://192.168.242.128:7801/");
            // Локатор поля "Логин"
            WebElement login = driver.findElement(new By.ByXPath("//*[@id=\"username\"]"));
            // Локатор поля "Пароль"
            WebElement password = driver.findElement(new By.ByXPath("//*[@id=\"password\"]"));
            // Локатор Кнопки "Войти"
            WebElement submit = driver.findElement(new By.ByXPath("//*[@id=\"submit\"]"));
            //2. В поле "Логин" вводим значение "admin"
            login.sendKeys("admin");
            //3. В поле "Пароль" вводим значение "admin"
            password.sendKeys("admin");
            //4. Нажать на кнопку "Войти"
            submit.click();
        } finally {
            ;
        }
    }

    @Test
    @DisplayName("1. Существование, при старте с нуля, пользователя по умолчанию admin.md")
    public void Existence_check_at_startup() {

        try {
            //1. Войти на сайт с пользователем "admin"
            permanentAuthorization();
            //Локатор раздела "Администрирование":
            WebElement Administration = driver.findElement(new By.ByXPath("//*[@id=\"navbarDropdown\"]"));
            //Локатор раздела "Пользователи":
            WebElement Users = driver.findElement(new By.ByXPath("//*[@id=\"navbarSupportedContent\"]/ul/li[3]/div/a[4]"));
            //2. В главном меню перейти в раздел "Администрирование -> Пользователи"
            Administration.click();
            Users.click();
            //3. В списке есть 1 запись, где "Логин пользователя" равен "admin"
            String expectedUserName = "admin";
            String actualUserName = driver.findElement(new By.ByXPath("/html/body/div[1]/div[1]/table/tbody/tr/td[3]")).getText();
            assertEquals(expectedUserName, actualUserName);


        } finally {
            driver.quit();
        }
    }


}
