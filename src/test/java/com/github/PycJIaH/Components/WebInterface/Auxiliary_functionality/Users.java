package com.github.PycJIaH.Components.WebInterface.Auxiliary_functionality;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.xml.xpath.XPath;
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
            WebElement Administration = driver.findElement(new By.ByXPath("//a[normalize-space()='Администрирование']"));
            //Локатор раздела "Пользователи":
            WebElement Users = driver.findElement(new By.ByXPath("//a[text()='Пользователи']"));
            //2. В главном меню перейти в раздел "Администрирование -> Пользователи"
            Administration.click();
            Users.click();
            //Локатор кнопки "Создать"
            WebElement createButton = driver.findElement(new By.ByXPath("//a[normalize-space()='Создать']"));
            //3. Выбрать вкладку "Создать"
            createButton.click();
            //Локатор поля "Логин пользователя" в окне "Создать новую запись"
            WebElement createNewLogin = driver.findElement(new By.ByXPath("//*[@id=\"username\"]"));
            //Локатор поля "Пароль" в окне "Создать новую запись"
            WebElement createNewPassword = driver.findElement(new By.ByXPath("//*[@id=\"password\"]"));
            //Локатор кнопки "Сохранить" в окне "Создать новую запись"
            WebElement saveButton = driver.findElement(new By.ByXPath("//input[@type='submit']"));
            //4.Ввести обязательные поля:
            //4.1. В поле "Логин пользователя" ввести значение "test"
            createNewLogin.sendKeys(username);
            //4.2. В поле "Пароль" ввести значение "test"
            createNewPassword.sendKeys("test");
            //Временная строка удалить:
            driver.findElement(new By.ByXPath("//*[@id=\"fs_uniquifier\"]")).sendKeys(username);
            //5. Нажать на кнопку "Сохранить"
            saveButton.click();
            //6. В таблице "Список" отображается строка со значением "test" в столбце "Логин пользователя":
            String expectedUserName = username;
            String actualUserName = driver.findElement(new By.ByXPath("//td[3][normalize-space()='" + username + "']")).getText();
            assertEquals(expectedUserName, actualUserName);

        } finally {

        }
    }

    @Test
    @DisplayName("1. Существование, при старте с нуля, пользователя по умолчанию admin.md")
    public void Existence_check_at_startup() {

        try {
            //1. Войти на сайт с пользователем "admin"
            permanentAuthorization();
            //Локатор раздела "Администрирование":
            WebElement administration = driver.findElement(new By.ByXPath("//a[normalize-space()='Администрирование']"));
            //Локатор раздела "Пользователи":
            WebElement users = driver.findElement(new By.ByXPath("//a[text()='Пользователи']"));
            //2. В главном меню перейти в раздел "Администрирование -> Пользователи"
            administration.click();
            users.click();
            //3. В списке есть 1 запись, где "Логин пользователя" равен "admin"
            String expectedUserName = "admin";
            String actualUserName = driver.findElement(new By.ByXPath("//tbody/tr/td[3]")).getText();
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
            WebElement deleteUserButton = driver.findElement(new By.ByXPath("//td[normalize-space()='" + username + "']/..//button[@title='Delete record']"));
            //Локатор имени пользователя вновь созданного
            WebElement usernameForDelete = driver.findElement(new By.ByXPath("//td[normalize-space()='" + username + "']"));
            //3. Нажать на иконку корзины "Удалить запись" пользователя с логином "user"
            deleteUserButton.click();
            //4. Появилось модальное диалоговое окно с текстом "Вы уверены что хотите удалить эту запись?"
            Alert ConfirmDelete = driver.switchTo().alert();
            //5. Нажать на кнопку "ОК"
            ConfirmDelete.accept();
            //6. В таблице "Список" отсутствует строка со значением "user" в столбце "Логин пользователя"
            assertFalse(isElementExists(usernameForDelete), "Пользователь не удалён");

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
            WebElement editUserButton = driver.findElement(new By.ByXPath("//td[normalize-space()='" + username + "']/..//a[@title='Редактировать запись']"));
            //3. Нажать на иконку "Редактировать запись" пользователя с логином "user"
            editUserButton.click();
            //Локатор поля "Логин пользователя"
            WebElement loginUserEdit = driver.findElement(new By.ByXPath("//*[@id=\"username\"]"));
            //4. Ввести в поле "Логин пользователя" значение "user2"
            loginUserEdit.clear(); //Очистить поле Логина
            String editedusername = "user" + new Random().ints(3000, 4000).findFirst().getAsInt();
            loginUserEdit.sendKeys(editedusername);
            //Локатор кнопки "Сохранить"
            WebElement saveButtonEdit = driver.findElement(new By.ByXPath("//input[@type='submit']"));
            //5. Нажать на кнопку "Сохранить"
            saveButtonEdit.click();
            //6. В таблице "Список" присутствует строка со значением "user2" в столбце "Логин пользователя"
            assertTrue(driver.findElement(new By.ByXPath("//td[normalize-space()='" + editedusername + "']")).isDisplayed(), "Отсутствует изменённый пользователь в списке");

        } finally {
            driver.quit();
        }
    }

    @Test
    @DisplayName("5. Просмотр записи пользователя")
    public void checkViewadmin() {

        try {
            //1. Войти на сайт с пользователем "admin"
            permanentAuthorization();
            //Локатор раздела "Администрирование":
            WebElement administration = driver.findElement(new By.ByXPath("//a[normalize-space()='Администрирование']"));
            //Локатор раздела "Пользователи":
            WebElement users = driver.findElement(new By.ByXPath("//a[text()='Пользователи']"));
            //2. В главном меню перейти в раздел "Администрирование -> Пользователи"
            administration.click();
            users.click();
            //Локатор кнопки "Просмотр записи"
            WebElement ViewUserButton = driver.findElement(new By.ByXPath("//td[normalize-space()='admin']/..//a[@title='Просмотр записи']"));
            //3. Нажать на иконку "Просмотр записи" пользователя с логином "admin"
            ViewUserButton.click();
            // Ждем появления формы
            WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait1.until(webDriver -> driver.findElement(new By.ByXPath("//h3[contains(text(), 'Просмотр записи')]")).isDisplayed());
            //4. Отобразились следующие поля и значения:
            //Локатор значения поля Логина пользователя:
            WebElement loginValue = driver.findElement(new By.ByXPath("//table//b[text()='Логин пользователя']/../../td[2]"));
            //Локатор значения поля Блокировки:
            WebElement blockStatus = driver.findElement(new By.ByXPath("//table//b[text()='Блокировка']/../../td[2]"));
            //Локатор значения поля Почты:
            WebElement emailValue = driver.findElement(new By.ByXPath("//table//b[text()='Почта']/../../td[2]"));
            //Локатор значения Даты создания:
            WebElement createDate = driver.findElement(new By.ByXPath("//table//b[text()='Дата создания']/../../td[2]"));
            //Локатор значения Даты последнего обновления:
            WebElement lastUpdateDate = driver.findElement(new By.ByXPath("//table//b[text()='Последнее обновление']/../../td[2]"));
            //Локатор значения Роли
            WebElement roleValue = driver.findElement(new By.ByXPath("//table//b[text()='Роли']/../../td[2]"));
            //4.1. Логин пользователя: admin
            assertTrue(driver.findElement(new By.ByXPath("//table//b[text()='Логин пользователя']")).isDisplayed(), "Поле \"Логин пользователя\" отсутствует");
            assertTrue(loginValue.isDisplayed(), "Значение логина отсутствует");
            String expectedLoginValue = "admin";
            String actualLoginValue = loginValue.getText();
            assertEquals(expectedLoginValue, actualLoginValue);
            //4.2. Блокировка: True
            assertTrue(driver.findElement(new By.ByXPath("//table//b[text()='Блокировка']")).isDisplayed(), "Поле \"Блокировка\" отсутствует");
            assertTrue(blockStatus.isDisplayed(), "Значение блокировки отсутствует");
            String expectedBlockStatus = "True";
            String actualBlockStatus = blockStatus.getText();
            assertEquals(expectedBlockStatus, actualBlockStatus);
            //4.3. Почта: admin@admin.ru
            assertTrue(driver.findElement(new By.ByXPath("//table//b[text()='Почта']")).isDisplayed(), "Поле \"Почта\" отсутствует");
            assertTrue(emailValue.isDisplayed(), "Значение почты отсутствует");
            String expectedEmailValue = "admin@admin.ru";
            String actualEmailValue = emailValue.getText();
            assertEquals(expectedEmailValue, actualEmailValue);
            //4.4. Имя пользователя:
            assertTrue(driver.findElement(new By.ByXPath("//table//b[text()='Имя пользователя']")).isDisplayed(), "Поле \"Имя пользователя\" отсутствует");
            assertTrue(driver.findElement(new By.ByXPath("//table//b[text()='Имя пользователя']/../../td[2]")).isDisplayed(), "Значение имени отсутствует");
            //4.5. Дата создания: yyyy-mm-dd hh:tt:ss.ms
            assertTrue(driver.findElement(new By.ByXPath("//table//b[text()='Дата создания']")).isDisplayed(), "Поле \"Дата создания\" отсутствует");
            assertTrue(createDate.isDisplayed(), "Значение даты создания отсутствует");
            String expectedCreateDate = "2022-04-19 12:59:24.083932";
            String actualCreateDate = createDate.getText();
            assertEquals(expectedCreateDate, actualCreateDate);
            //4.6. Последнее обновление: yyyy-mm-dd hh:tt:ss.ms
            assertTrue(driver.findElement(new By.ByXPath("//table//b[text()='Последнее обновление']")).isDisplayed(), "Поле \"Последнее обновление\" отсутствует");
            assertTrue(lastUpdateDate.isDisplayed(), "Значение даты последнего обновления отсутствует");
            String expectedLastUpdateDate = "2022-04-19 12:59:24.083932";
            String actualLastUpdateDate = lastUpdateDate.getText();
            assertEquals(expectedLastUpdateDate, actualLastUpdateDate);
            //4.7. Роли: admin
            assertTrue(driver.findElement(new By.ByXPath("//table//b[text()='Роли']")).isDisplayed(), "Поле \"Роли\" отсутствует");
            assertTrue(roleValue.isDisplayed(), "Значение Роли отсутствует");
            String expectedRoleValue = "admin";
            String actualRoleValue = roleValue.getText();
            assertEquals(expectedRoleValue, actualRoleValue);
            //4.8. Последний вход - Поле отсутствует в данном релизе программы
            //4.9. Текущий вход - Поле отсутствует в данном релизе программы
            //4.10. Последний адрес входа - Поле отсутствует в данном релизе программы
            //4.11. Текущий адрес входа - Поле отсутствует в данном релизе программы
            //4.12. Количество входов - Поле отсутствует в данном релизе программы
            //4.13. Дата подтверждения УЗ - Поле отсутствует в данном релизе программы

        } finally {
            driver.quit();
        }
    }

    @Test
    @DisplayName("6. Удаление записи пользователя через выбор")
    public void removeUserAccountChoice() {

        try {
            //1. Войти на сайт с пользователем "admin"
            permanentAuthorization();
            //2. Создать пользователя "user"
            String username = "user" + new Random().ints(4000, 5000).findFirst().getAsInt();
            createNewUser(username);
            //Локатор имени пользователя вновь созданного
            WebElement usernameForDelete = driver.findElement(new By.ByXPath("//td[normalize-space()='" + username + "']"));
            //Локатор чекбокса в таблице пользователей+
            WebElement checkBoxTable = driver.findElement(new By.ByXPath("//td[normalize-space()='" + username + "']/..//input[@type='checkbox']"));
            //3. Нажать на чекбокс слева пользователя "user"
            checkBoxTable.click();
            //Локатор вкладки "С выбранным"
            WebElement withSelected = driver.findElement(new By.ByXPath("//a[text()='С выбранным']"));
            //4. Перейти во вкладку "С выбранным"
            withSelected.click();
            //Локатор пункта меню "Удалить"
            WebElement deleteUserButton = driver.findElement(new By.ByXPath("//a[text()='Удалить']"));
            //5. Выбрать пункт из меню "Удалить"
            deleteUserButton.click();
            //6. Появилось модальное диалоговое окно с текстом "Вы уверены что хотите удалить ?"
            Alert ConfirmDelete = driver.switchTo().alert();
            //7. Нажать на "ОК"
            ConfirmDelete.accept();
            //8. В списке отсутствует строка со значением "user" в столбце "Логин пользователя"
            assertFalse(isElementExists(usernameForDelete), "Пользователь не удален");
        } finally {
            driver.quit();
        }
    }

    private boolean isElementExists(WebElement el1) {
        try {
            el1.isDisplayed();
            return true;
        } catch (NoSuchElementException | StaleElementReferenceException e) {
        }
        return false;
    }
}

