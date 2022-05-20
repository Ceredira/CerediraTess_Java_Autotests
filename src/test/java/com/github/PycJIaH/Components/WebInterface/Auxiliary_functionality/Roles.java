package com.github.PycJIaH.Components.WebInterface.Auxiliary_functionality;

import com.google.common.cache.AbstractCache;
import com.google.common.cache.Cache;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

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
            String testerName = "tester_" + new Random().ints(0, 100).findFirst().getAsInt();
            nameRoleField.sendKeys(testerName);
            //7. В поле "Описание" ввести значение "Тестирует систему"
            descriptionField.sendKeys("Тестирует систему");
            //8. Нажать на кнопку "Сохранить"
            saveButton.click();
            //9. В таблице "Список" отображается строка со значением "tester_2" в столбце "Название роли"
            assertTrue(driver.findElement(new By.ByXPath("//td[@class='col-name' and normalize-space()='" + testerName + "']")).isDisplayed());

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

    @Test
    @DisplayName("4. Изменение роли, изменение поля Имя роли")
    public void editNameCreatedRole() {

        try {
            //1.Войти на сайт с пользователем "admin"
            permanentAuthorization();
            //2. Создать роль "tester_3"
            String testerName = "tester_" + new Random().ints(201, 300).findFirst().getAsInt();
            String editedTesterName = "tester_" + new Random().ints(301, 400).findFirst().getAsInt();
            createRole(testerName);
            //Локатор кнопки "Редактировать запись"
            WebElement editUserButton = driver.findElement(new By.ByXPath("//td[normalize-space()='" + testerName + "']/..//a[@title='Редактировать запись']"));
            //3. Нажать на иконку "Редактировать запись" роли с названием "tester_4_1"
            editUserButton.click();
            //Локатор поля "Название роли"
            WebElement nameRoleField = driver.findElement(new By.ByXPath("//*[@id=\"name\"]"));
            //Локатор кнопки "Сохранить"
            WebElement saveButton = driver.findElement(new By.ByXPath("//*[@id=\"fa_modal_window\"]/..//input[@value='Сохранить']"));
            //4. Ввести в поле "Название роли" значение "tester_4_2"
            nameRoleField.clear();
            nameRoleField.sendKeys(editedTesterName);
            //5. Нажать на кнопку "Сохранить"
            saveButton.click();
            //6. В таблице "Список" отображается строка со значением "tester_4_2" в столбце "Название роли"
            assertTrue(driver.findElement(new By.ByXPath("//td[normalize-space()='" + editedTesterName + "']")).isDisplayed(), "Отсутствует изменённый пользователь в списке");

        } finally {
            driver.quit();
        }
    }

    @Test
    @DisplayName("5. Изменение роли, изменение поля Описание")
    public void editDescriptionCreatedRole() {

        try {
            //1.Войти на сайт с пользователем "admin"
            permanentAuthorization();
            //2. Создать роль "tester_3"
            String testerName = "tester_" + new Random().ints(401, 500).findFirst().getAsInt();
            String editedDescription = "Описание роли" + new Random().ints(301, 400).findFirst().getAsInt();
            createRole(testerName);
            //Локатор кнопки "Редактировать запись"
            WebElement editUserButton = driver.findElement(new By.ByXPath("//td[normalize-space()='" + testerName + "']/..//a[@title='Редактировать запись']"));
            //3. Нажать на иконку "Редактировать запись" роли с названием "tester_4_1"
            editUserButton.click();
            //Локатор поля "Описание"
            WebElement descriptionField = driver.findElement(new By.ByXPath("//*[@id=\"description\"]"));
            //Локатор кнопки "Сохранить"
            WebElement saveButton = driver.findElement(new By.ByXPath("//*[@id=\"fa_modal_window\"]/..//input[@value='Сохранить']"));
            //4. Ввести в поле "Описание" значение "Описание роли"
            descriptionField.clear();
            descriptionField.sendKeys(editedDescription);
            //5. Нажать на кнопку "Сохранить"
            saveButton.click();
            //6. В таблице "Список" отображается строка со значением "tester_4_2" в столбце "Название роли"
            assertTrue(driver.findElement(new By.ByXPath("//td[normalize-space()='" + editedDescription + "']")).isDisplayed(), "Отсутствует изменённое описание роли в списке");

        } finally {
            driver.quit();
        }
    }

    @Test
    @DisplayName("6. Просмотр роли")
    public void checkViewRoleAdmin() {

        try {
            //1.Войти на сайт с пользователем "admin"
            permanentAuthorization();
            //Локатор раздела "Администрирование":
            WebElement administration = driver.findElement(new By.ByXPath("//a[normalize-space()='Администрирование']"));
            //Локатор вкладки "Роли":
            WebElement roles = driver.findElement(new By.ByXPath("//a[text()='Роли']"));
            //2. В главном меню перейти в раздел "Администрирование -> Роли"
            administration.click();
            roles.click();
            //Локатор кнопки "Просмотр записи"
            WebElement ViewUserButton = driver.findElement(new By.ByXPath("//td[normalize-space()='admin']/..//a[@title='Просмотр записи']"));
            //3. Нажать на иконку "Просмотр записи" пользователя с логином "admin"
            ViewUserButton.click();
            // Ждем появления формы
            WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait1.until(webDriver -> driver.findElement(new By.ByXPath("//h3[contains(text(), 'Просмотр записи')]")).isDisplayed());
            //Локатор значения поля "Название роли":
            WebElement roleNameValue = driver.findElement(new By.ByXPath("//table//b[text()='Название роли']/../../td[2]"));
            //Локатор значения поля "Описание":
            WebElement descriptionValue = driver.findElement(new By.ByXPath("//table//b[text()='Описание']/../../td[2]"));
            //4. В поле "Название роли" присутствует значение "admin"
            assertTrue(driver.findElement(new By.ByXPath("//table//b[text()='Название роли']")).isDisplayed(), "Поле \"Название роли\" отсутствует");
            assertTrue(roleNameValue.isDisplayed(), "Значение поля \"Название роли\" отсутствует");
            String expectedRoleNameValue = "admin";
            String actualRoleNameValue = roleNameValue.getText();
            assertEquals(expectedRoleNameValue, actualRoleNameValue);
            //5. В поле "Описание" отсутствует значение
            assertTrue(driver.findElement(new By.ByXPath("//table//b[text()='Описание']")).isDisplayed(), "Поле \"Описание\" отсутствует");
            assertTrue(descriptionValue.getText().isEmpty(), "В поле \"Описание\" присутствует значение ");
            //6. В поле "Агенты" присутствует значение "CerediraTess" - Поле отсутствует в данном релизе программы
            //7. В поле "Пользователи" присутствует значение "admin" - Поле отсутствует в данном релизе программы

        } finally {
            driver.quit();
        }
    }

    @Test
    @DisplayName("7. Дублирование записи роли, позитивный сценарий (1 способ)")
    public void duplicateRoleEntryFirstWay() {

        try {
            //1.Войти на сайт с пользователем "admin"
            permanentAuthorization();
            //Локатор раздела "Администрирование":
            WebElement administration = driver.findElement(new By.ByXPath("//a[normalize-space()='Администрирование']"));
            //Локатор вкладки "Роли":
            WebElement roles = driver.findElement(new By.ByXPath("//a[text()='Роли']"));
            //2. В главном меню перейти в раздел "Администрирование -> Роли"
            administration.click();
            roles.click();
            //Локатор кнопки "Дублировать запись"
            WebElement duplicateRowButton = driver.findElement(new By.ByXPath("//td[normalize-space()='admin']/..//a[@title='Duplicate Row']"));
            //3.Нажать на иконку "Дублировать запись" роли с названием "admin"
            duplicateRowButton.click();
            //Локатор поля "Название роли"
            WebElement nameRoleField = driver.findElement(new By.ByXPath("//*[@id=\"name\"]"));
            //Локатор кнопки "Сохранить"
            WebElement saveButton = driver.findElement(new By.ByXPath("//input[@value='Сохранить']"));
            //4. Ввести в поле "Название роли" значение "admin_7"
            String adminName = "admin_" + new Random().ints(1, 100).findFirst().getAsInt();
            nameRoleField.clear();
            nameRoleField.sendKeys(adminName);
            //5. Нажать на кнопку "Сохранить"
            saveButton.click();
            //6. В таблице "Список" отображается строка со значением "admin_7" в столбце "Название роли"
            assertTrue(driver.findElement(new By.ByXPath("//td[@class='col-name' and normalize-space()='" + adminName + "']")).isDisplayed());

        } finally {
            driver.quit();
        }
    }

    @Test
    @DisplayName("8. Дублирование записи роли, позитивный сценарий (2 способ)")
    public void duplicateRoleEntrySecondWay() {

        try {
            //1.Войти на сайт с пользователем "admin"
            permanentAuthorization();
            //Локатор раздела "Администрирование":
            WebElement administration = driver.findElement(new By.ByXPath("//a[normalize-space()='Администрирование']"));
            //Локатор вкладки "Роли":
            WebElement roles = driver.findElement(new By.ByXPath("//a[text()='Роли']"));
            //2. В главном меню перейти в раздел "Администрирование -> Роли"
            administration.click();
            roles.click();
            //Локатор кнопки "Дублировать запись"
            WebElement duplicateRowButton = driver.findElement(new By.ByXPath("//td[normalize-space()='admin']/..//a[@title='Duplicate Row']"));
            //3.Нажать на иконку "Дублировать запись" роли с названием "admin"
            duplicateRowButton.click();
            //Локатор поля "Название роли"
            WebElement nameRoleField = driver.findElement(new By.ByXPath("//*[@id=\"name\"]"));
            //Локатор кнопки "Сохранить и добавить новый объект"
            WebElement saveAndAddButton = driver.findElement(new By.ByXPath("//input[@value='Сохранить и добавить новый объект']"));
            //4. Ввести в поле "Название роли" значение "admin_7"
            String adminName = "admin_" + new Random().ints(101, 200).findFirst().getAsInt();
            nameRoleField.clear();
            nameRoleField.sendKeys(adminName);
            //5. Нажать на кнопку "Сохранить и добавить новый объект"
            saveAndAddButton.click();
            //6. Текущая страница ${url}:7801/admin/Role/duplicate/?id=${id дублируемой роли}
            assertTrue(driver.getCurrentUrl().matches("https?:\\/\\/.*:7801\\/admin\\/Role\\/duplicate\\/\\?id=\\d+"));
            //7. В строке "Название роли" осталось прежнее название "admin"
            String expectedNameRoleField = "admin";
            String actualNameRoleField = driver.findElement(new By.ByXPath("//input[@value='admin']")).getAttribute("value");
            assertEquals(expectedNameRoleField, actualNameRoleField);
            //Локатор вкладки "Список"
            WebElement listRole = driver.findElement(new By.ByXPath("//a[text()='Список']"));
            //8. Перейти во вкладку "Список"
            listRole.click();
            //9. В таблице "Список" отображается строка со значением "admin_8" в столбце "Название роли"
            assertTrue(driver.findElement(new By.ByXPath("//td[@class='col-name' and normalize-space()='" + adminName + "']")).isDisplayed());

        } finally {
            driver.quit();
        }
    }

    @Test
    @DisplayName("9. Дублирование записи роли, позитивный сценарий (3 способ)")
    public void duplicateRoleEntryThirdWay() {

        try {
            //1.Войти на сайт с пользователем "admin"
            permanentAuthorization();
            //Локатор раздела "Администрирование":
            WebElement administration = driver.findElement(new By.ByXPath("//a[normalize-space()='Администрирование']"));
            //Локатор вкладки "Роли":
            WebElement roles = driver.findElement(new By.ByXPath("//a[text()='Роли']"));
            //2. В главном меню перейти в раздел "Администрирование -> Роли"
            administration.click();
            roles.click();
            //Локатор кнопки "Дублировать запись"
            WebElement duplicateRowButton = driver.findElement(new By.ByXPath("//td[normalize-space()='admin']/..//a[@title='Duplicate Row']"));
            //3.Нажать на иконку "Дублировать запись" роли с названием "admin"
            duplicateRowButton.click();
            //Локатор поля "Название роли"
            WebElement nameRoleField = driver.findElement(new By.ByXPath("//*[@id=\"name\"]"));
            //Локатор кнопки "Сохранить и добавить новый объект"
            WebElement saveAndContinueEditingButton = driver.findElement(new By.ByXPath("//input[@value='Сохранить и продолжить редактирование']"));
            //4. Ввести в поле "Название роли" значение "admin_9"
            String adminName = "admin_" + new Random().ints(201, 300).findFirst().getAsInt();
            nameRoleField.clear();
            nameRoleField.sendKeys(adminName);
            //5. Нажать на кнопку "Сохранить и продолжить редактирование"
            saveAndContinueEditingButton.click();
            //6. Текущая страница ${url}:7801/admin/Role/duplicate/?id=${id дублируемой роли}
            assertTrue(driver.getCurrentUrl().matches("https?:\\/\\/.*:7801\\/admin\\/Role\\/edit\\/\\?id=\\d+&url=.*"));
            //7. В строке "Название роли" осталось прежнее название "admin"
            assertTrue(driver.findElement(new By.ByXPath("//input[@value='" + adminName + "']")).isDisplayed());
            //Локатор вкладки "Список"
            WebElement listRole = driver.findElement(new By.ByXPath("//a[text()='Список']"));
            //8. Перейти во вкладку "Список"
            listRole.click();
            //9. В таблице "Список" отображается строка со значением "admin_8" в столбце "Название роли"
            assertTrue(driver.findElement(new By.ByXPath("//td[@class='col-name' and normalize-space()='" + adminName + "']")).isDisplayed());

        } finally {
            driver.quit();
        }
    }

    @Test
    @DisplayName("10. Дублирование записи роли, негативный сценарий")
    public void duplicateRoleEntryNegativeWay() {

        try {
            //1.Войти на сайт с пользователем "admin"
            permanentAuthorization();
            //Локатор раздела "Администрирование":
            WebElement administration = driver.findElement(new By.ByXPath("//a[normalize-space()='Администрирование']"));
            //Локатор вкладки "Роли":
            WebElement roles = driver.findElement(new By.ByXPath("//a[text()='Роли']"));
            //2. В главном меню перейти в раздел "Администрирование -> Роли"
            administration.click();
            roles.click();
            //Локатор кнопки "Дублировать запись"
            WebElement duplicateRowButton = driver.findElement(new By.ByXPath("//td[normalize-space()='admin']/..//a[@title='Duplicate Row']"));
            //3.Нажать на иконку "Дублировать запись" роли с названием "admin"
            duplicateRowButton.click();
            //Локатор поля "Название роли"
            WebElement nameRoleField = driver.findElement(new By.ByXPath("//*[@id=\"name\"]"));
            //Локатор кнопки "Сохранить"
            WebElement saveButton = driver.findElement(new By.ByXPath("//input[@value='Сохранить']"));
            //4. Ввести в поле "Название роли" значение "admin"
            nameRoleField.clear();
            nameRoleField.sendKeys("admin");
            //5. Нажать на кнопку "Сохранить"
            saveButton.click();
            //6. Дубликат роли "admin" не создался
            //Локатор ошибки целостности
            WebElement errorMessage = driver.findElement(new By.ByXPath("//div[@class=\"alert alert-danger alert-dismissable\" and contains(string(), \"Ошибка целостности.\")]"));
            //7. Под основным меню появилось сообщение "Ошибка целостности."
            assertTrue(errorMessage.isDisplayed());

        } finally {
            driver.quit();
        }
    }

    @Test
    @DisplayName("11. Удаление записи роли admin, негативный сценарий") //ЭТОТ ТЕСТ В ДАННОМ РЕЛИЗЕ НЕ ЗАПУСКАТЬ, ВСЁ ЛОМАЕТ
    public void DeletingRoleEntryAdminNegativeWay() {

        try {
            //1.Войти на сайт с пользователем "admin"
            permanentAuthorization();
            //Локатор раздела "Администрирование":
            WebElement administration = driver.findElement(new By.ByXPath("//a[normalize-space()='Администрирование']"));
            //Локатор вкладки "Роли":
            WebElement roles = driver.findElement(new By.ByXPath("//a[text()='Роли']"));
            //2. В главном меню перейти в раздел "Администрирование -> Роли"
            administration.click();
            roles.click();
            //Локатор кнопки "Удалить запись" для admin
            WebElement deleteAdminUserButton = driver.findElement(new By.ByXPath("//td[normalize-space()='admin']/..//button[@title='Delete record']"));
            //3. Нажать на иконку "Удалить запись" роли с названием "admin"
            deleteAdminUserButton.click();
            //4. Появилось модальное диалоговое окно с текстом "Вы уверены что хотите удалить эту запись?"
            Alert ConfirmDelete = driver.switchTo().alert();
            //5. Нажать на кнопку "ОК"
            ConfirmDelete.accept();
            //6. В таблице "Список" присутствует строка со значением "admin" в столбце "Название роли" - не реализовано в данном релизе программы
            //7. Под основным меню появилось сообщение "Ошибка удаления записи: Нельзя удалить роль admin." - не реализовано в данном релизе программы

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
            //Локатор поля "Название роли"
            WebElement nameRoleField = driver.findElement(new By.ByXPath("//*[@id=\"name\"]"));
            //Локатор кнопки "Сохранить"
            WebElement saveButton = driver.findElement(new By.ByXPath("//*[@id=\"fa_modal_window\"]/..//input[@value='Сохранить']"));
            //4. В поле "Название роли" ввести значение "${userrole}"
            nameRoleField.sendKeys(testerName);
            //5. Нажать на кнопку "Сохранить"
            saveButton.click();
            //6. В таблице "Список" отображается строка со значением "${userrole}" в столбце "Название роли"
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
