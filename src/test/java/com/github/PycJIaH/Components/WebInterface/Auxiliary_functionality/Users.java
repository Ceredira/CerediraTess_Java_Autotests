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

    @Test
    @DisplayName("2. Создание нового пользователя")
    public void createNewUser() {

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
            //Локатор кнопки "Создать"
            WebElement CreateButton = driver.findElement(new By.ByXPath("/html/body/div[1]/ul/li[2]/a"));
            //3. Выбрать вкладку "Создать"
            CreateButton.click();
            //Локатор поля "Логин пользователя" в окне "Создать новую запись"
            WebElement CreateNewLogin = driver.findElement(new By.ByXPath("//*[@id=\"username\"]"));
            //Локатор поля "Пароль" в окне "Создать новую запись"
            WebElement CreateNewPassword = driver.findElement(new By.ByXPath("//*[@id=\"password\"]"));
            //Локатор кнопки "Сохранить" в окне "Создать новую запись"
            WebElement SaveButton = driver.findElement(new By.ByXPath("//*[@id=\"fa_modal_window\"]/div/div/form/fieldset/div[2]/input"));
            //4.Ввести обязательные поля:
            //4.1. В поле "Логин пользователя" ввести значение "test"
            CreateNewLogin.sendKeys("test");
            //4.2. В поле "Пароль" ввести значение "test"
            CreateNewPassword.sendKeys("test");
            //Временная строка удалить:
            driver.findElement(new By.ByXPath("//*[@id=\"fs_uniquifier\"]")).sendKeys("test");
            //5. Нажать на кнопку "Сохранить"
            SaveButton.click();
            //6. В таблице "Список" отображается строка со значением "test" в столбце "Логин пользователя":
            String expectedUserName = "test";
            String actualUserName = driver.findElement(new By.ByXPath("/html/body/div[1]/div[2]/table/tbody/tr[2]/td[3]")).getText();
            assertEquals(expectedUserName, actualUserName);


        } finally {
            driver.quit();
        }
    }


}
