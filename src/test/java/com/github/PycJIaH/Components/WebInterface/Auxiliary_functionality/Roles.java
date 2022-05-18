package com.github.PycJIaH.Components.WebInterface.Auxiliary_functionality;

import com.google.common.cache.AbstractCache;
import com.google.common.cache.Cache;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.nio.file.WatchEvent;
import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class Roles {

    WebDriver driver;

    @Test
    @DisplayName("1. Существование, при старте с нуля, роли по умолчанию \"admin\"")
    public void existenceCheckRoleAdmin() {

        try {
            //1. Войти на сайт с пользователем "admin"
            permanentAuthorization();
            //Локатор раздела "Администрирование":
            WebElement administration = driver.findElement(new By.ByXPath("//a[normalize-space()='Администрирование']"));
            //Локатор вкладки "Роли":
            WebElement roles = driver.findElement(new By.ByXPath("//a[text()='Роли']"));
            //2. В главном меню перейти в раздел "Администрирование -> Роли"
            administration.click();
            roles.click();
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


    @Test
    @DisplayName("2. Создание роли")
    public void createRoleTest() {

        try {
            permanentAuthorization();
            createRole("tester_" + new Random().ints(1, 100).findFirst().getAsInt());

        } finally {
            driver.quit();
        }
    }

    @Test
    @DisplayName("3. Удаление созданной роли")
    public void removeCreatedRole() {

        try {
            //1.Войти на сайт с пользователем "admin"
            permanentAuthorization();
            //2. Создать роль "tester_3"
            String testerName = "tester_" + new Random().ints(101, 200).findFirst().getAsInt();
            createRole(testerName);
            //Локатор кнопки "Удалить запись"
            WebElement deleteUserRoleButton = driver.findElement(new By.ByXPath("//td[normalize-space()='" + testerName + "']/..//button[@title='Delete record']"));
            //Локатор имени пользователя вновь созданного (в ролях)
            WebElement rolenameForDelete = driver.findElement(new By.ByXPath("//td[normalize-space()='" + testerName + "']"));
            //3. Нажать на иконку "Удалить запись" роли с названием "tester_3"
            deleteUserRoleButton.click();
            //4. Появилось модальное диалоговое окно с текстом "Вы уверены что хотите удалить эту запись?"
            Alert ConfirmDelete = driver.switchTo().alert();
            //5. Нажать на кнопку "ОК"
            ConfirmDelete.accept();
            //6. В таблице "Список" отсутствует строка со значением "tester_3" в столбце "Название роли"
            assertFalse(isElementExists(rolenameForDelete), "Пользователь не удален");

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

    public void createRole(String testerName) {

        try {
            //Локатор раздела "Администрирование":
            WebElement administration = driver.findElement(new By.ByXPath("//a[normalize-space()='Администрирование']"));
            //Локатор вкладки "Роли":
            WebElement roles = driver.findElement(new By.ByXPath("//a[text()='Роли']"));
            //2. В главном меню перейти в раздел "Администрирование -> Роли"
            administration.click();
            roles.click();
            //Локатор кнопки "Создать"
            WebElement createButton = driver.findElement(new By.ByXPath("//a[normalize-space()='Создать']"));
            //3. Нажать на вкладку "Создать"
            createButton.click();
            //Локатор поля "Агенты"
            WebElement agentField = driver.findElement(new By.ByXPath("//*[@id=\"s2id_agents\"]/ul"));
            //Локатор поля "Пользователи"
            WebElement usersField = driver.findElement(new By.ByXPath("//*[@id=\"s2id_users\"]/ul"));
            //Локатор поля "Название роли"
            WebElement nameRoleField = driver.findElement(new By.ByXPath("//*[@id=\"name\"]"));
            //Локатор поля "Описание"
            WebElement descriptionField = driver.findElement(new By.ByXPath("//*[@id=\"description\"]"));
            //Локатор кнопки "Сохранить"
            WebElement saveButton = driver.findElement(new By.ByXPath("//*[@id=\"fa_modal_window\"]/..//input[@value='Сохранить']"));
            //4.В поле "Агенты"...
            agentField.click();
            //Локатор выпадающего элемента "CerediraTess"
            WebElement dropDownElement = driver.findElement(new By.ByXPath("//*[contains(@id,\"select2-result-label-\") and text()='CerediraTess']"));
            //4. ...выбрать значение "CerediraTess"
            dropDownElement.click();
            //5. В поле "Пользователи"...
            usersField.click();
            //Локатор значения "admin" в поле Пользователи
            WebElement adminChoice = driver.findElement(new By.ByXPath("//*[contains(@id,\"select2-result-label-\") and text()='admin']"));
            //5. ...выбрать значение "admin"
            adminChoice.click();
            //6. В поле "Название роли" ввести значение "tester_2"
            nameRoleField.sendKeys(testerName);
            //7. В поле "Описание" ввести значение "Тестирует систему"
            descriptionField.sendKeys("Тестирует систему");
            //8. Нажать на кнопку "Сохранить"
            saveButton.click();
            //9. В таблице "Список" отображается строка со значением "tester_2" в столбце "Название роли"
            assertTrue(driver.findElement(new By.ByXPath("//td[@class='col-name' and normalize-space()='" + testerName + "']")).isDisplayed());

        } finally {

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
