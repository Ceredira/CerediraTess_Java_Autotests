package com.github.PycJIaH.Components.WebInterface.Auxiliary_functionality;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

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

    public void createNewUser(String username) {

        try {
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
            CreateNewLogin.sendKeys(username);
            //4.2. В поле "Пароль" ввести значение "test"
            CreateNewPassword.sendKeys("test");
            //Временная строка удалить:
            driver.findElement(new By.ByXPath("//*[@id=\"fs_uniquifier\"]")).sendKeys(username);
            //5. Нажать на кнопку "Сохранить"
            SaveButton.click();
            //6. В таблице "Список" отображается строка со значением "test" в столбце "Логин пользователя":
            String expectedUserName = username;
            String actualUserName = driver.findElement(new By.ByXPath("//td[3][normalize-space()='" + username + "']")).getText();
            assertEquals(expectedUserName, actualUserName);

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
    public void createNewUserTest() {

        try {
            //1. Войти на сайт с пользователем "admin"
            permanentAuthorization();
            //Создание пользователя:
            createNewUser("test");

        } finally {
            driver.quit();
        }
    }

    @Test
    @DisplayName("3. Удаление пользователя")
    public void deleteNewUser() {

        try {
            //1. Войти на сайт с пользователем "admin"
            permanentAuthorization();
            //2. Создать пользователя "user"
            String username = "test" + new Random().ints(2000, 3000).findFirst().getAsInt();
            createNewUser(username);
            //Локатор кнопки "Удалить запись"
            WebElement DeleteUserButton = driver.findElement(new By.ByXPath("//td[normalize-space()='" + username + "']/..//button[@title='Delete record']"));
            //3. Нажать на иконку корзины "Удалить запись" пользователя с логином "user"
            DeleteUserButton.click();
            //4. Появилось модальное диалоговое окно с текстом "Вы уверены что хотите удалить эту запись?"
            Alert ConfirmDelete = driver.switchTo().alert();
            //5. Нажать на кнопку "ОК"
            ConfirmDelete.accept();
            //6. В таблице "Список" отсутствует строка со значением "user" в столбце "Логин пользователя"
            assertTrue(isElementPresent(username), "Пользователь не удалён");

        } finally {
            driver.quit();
        }


    }

    @Test
    @DisplayName("4. Изменение записи пользователя, изменение поля \"Логин пользователя\"")
    public void changeLoginUser() {

        try {
            //1. Войти на сайт с пользователем "admin"
            permanentAuthorization();
            //2. Создать пользователя "user"
            String username = "user" + new Random().ints(2000, 3000).findFirst().getAsInt();
            createNewUser(username);
            //Локатор кнопки "Редактировать запись"
            WebElement EditUserButton = driver.findElement(new By.ByXPath("//td[normalize-space()='" + username + "']/..//a[@title='Редактировать запись']"));
            //3. Нажать на иконку "Редактировать запись" пользователя с логином "user"
            EditUserButton.click();
            //Локатор поля "Логин пользователя"
            WebElement LoginUserEdit = driver.findElement(new By.ByXPath("//*[@id=\"username\"]"));
            //4. Ввести в поле "Логин пользователя" значение "user2"
            LoginUserEdit.clear(); //Очистить поле Логина
            String editedusername = "user" + new Random().ints(3000, 4000).findFirst().getAsInt();
            LoginUserEdit.sendKeys(editedusername);
            //Локатор кнопки "Сохранить"
            WebElement SaveButtonEdit = driver.findElement(new By.ByXPath("//input[@type='submit']"));
            //5. Нажать на кнопку "Сохранить"
            SaveButtonEdit.click();
            //6. В таблице "Список" присутствует строка со значением "user2" в столбце "Логин пользователя"
            assertTrue(driver.findElement(new By.ByXPath("//td[3][normalize-space()='" + editedusername + "']")).isDisplayed());

        } finally {
            driver.quit();
        }
    }

    private boolean isElementPresent(String element) {
        Boolean result = false;
        try {
            if (element != null) {
                result = true;
            }
        } catch (Exception e) {
        }
        return result;
    }
}

