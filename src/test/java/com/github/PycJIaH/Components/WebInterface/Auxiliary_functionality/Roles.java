package com.github.PycJIaH.Components.WebInterface.Auxiliary_functionality;

import com.google.common.cache.AbstractCache;
import com.google.common.cache.Cache;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Roles {

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
    @DisplayName("1. Существование, при старте с нуля, роли по умолчанию \"admin\"")
    public void ExistenceCheckRoleAdmin() {

        try {
            //1. Войти на сайт с пользователем "admin"
            permanentAuthorization();
            //Локатор раздела "Администрирование":
            WebElement Administration = driver.findElement(new By.ByXPath("//a[normalize-space()='Администрирование']"));
            //Локатор вкладки "Роли":
            WebElement Roles = driver.findElement(new By.ByXPath("//a[text()='Роли']"));
            //2. В главном меню перейти в раздел "Администрирование -> Роли"
            Administration.click();
            Roles.click();
            //3. В таблице "Список" отображается 1 строка со значением "admin" в столбце "Название роли"
            //Проверка на кол-во записей в таблице
            List<WebElement> countОfLines = driver.findElements(new By.ByXPath("//td[@class='col-name' and normalize-space()='admin']"));
            int expectedCountList = 1;
            int actualCountList = countОfLines.size();
            assertEquals(expectedCountList, actualCountList);
        } finally {
            driver.quit();
        }
    }
}
