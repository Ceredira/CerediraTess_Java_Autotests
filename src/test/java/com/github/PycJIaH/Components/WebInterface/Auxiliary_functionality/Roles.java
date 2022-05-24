package com.github.PycJIaH.Components.WebInterface.Auxiliary_functionality;

import com.google.common.cache.AbstractCache;
import com.google.common.cache.Cache;
import dev.failsafe.internal.util.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.WatchEvent;
import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class Roles {

    final Logger log = LoggerFactory.getLogger(Roles.class);
    WebDriver driver;

    @Test
    @DisplayName("1. Существование, при старте с нуля, роли по умолчанию \"admin\"")
    public void existenceCheckRoleAdmin() {

        try {
            log.info("1. Войти на сайт с пользователем \"admin\"");
            permanentAuthorization();
            // Локатор раздела "Администрирование"
            WebElement administration = driver.findElement(new By.ByXPath("//a[normalize-space()='Администрирование']"));
            // Локатор вкладки "Роли"
            WebElement roles = driver.findElement(new By.ByXPath("//a[text()='Роли']"));
            log.info("2. В главном меню перейти в раздел \"Администрирование -> Роли\"");
            administration.click();
            roles.click();
            log.info("3. В таблице \"Список\" отображается 1 строка со значением \"admin\" в столбце \"Название роли\"");
            //Проверка на кол-во записей в таблице
            List<WebElement> countOfLines = driver.findElements(new By.ByXPath("//td[@class='col-name' and normalize-space()='admin']"));
            int expectedCountList = 1;
            int actualCountList = countOfLines.size();
            assertEquals(expectedCountList, actualCountList);
        } finally {
            driver.quit();
        }
    }


    @Test
    @DisplayName("2. Создание роли")
    public void createRoleTest() {

        try {
            log.info("1. Войти на сайт с пользователем \"admin\"");
            permanentAuthorization();
            //Локатор раздела "Администрирование":
            WebElement administration = driver.findElement(new By.ByXPath("//a[normalize-space()='Администрирование']"));
            //Локатор вкладки "Роли":
            WebElement roles = driver.findElement(new By.ByXPath("//a[text()='Роли']"));
            log.info("2. В главном меню перейти в раздел \"Администрирование -> Роли\"");
            administration.click();
            roles.click();
            //Локатор кнопки "Создать"
            WebElement createButton = driver.findElement(new By.ByXPath("//a[normalize-space()='Создать']"));
            log.info("3. Нажать на вкладку \"Создать\"");
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
            log.info("4.В поле \"Агенты\"...");
            agentField.click();
            //Локатор выпадающего элемента "CerediraTess"
            WebElement dropDownElement = driver.findElement(new By.ByXPath("//*[contains(@id,\"select2-result-label-\") and text()='CerediraTess']"));
            log.info("4. ...выбрать значение \"CerediraTess\"");
            dropDownElement.click();
            log.info("5. В поле \"Пользователи\"...");
            usersField.click();
            //Локатор значения "admin" в поле Пользователи
            WebElement adminChoice = driver.findElement(new By.ByXPath("//*[contains(@id,\"select2-result-label-\") and text()='admin']"));
            log.info("5. ...выбрать значение \"admin\"");
            adminChoice.click();
            log.info("6. В поле \"Название роли\" ввести значение \"tester_2\"");
            String testerName = "tester_" + new Random().ints(0, 100).findFirst().getAsInt();
            nameRoleField.sendKeys(testerName);
            log.info("7. В поле \"Описание\" ввести значение \"Тестирует систему\"");
            descriptionField.sendKeys("Тестирует систему");
            log.info("8. Нажать на кнопку \"Сохранить\"");
            saveButton.click();
            log.info("9. В таблице \"Список\" отображается строка со значением \"tester_2\" в столбце \"Название роли\"");
            assertTrue(driver.findElement(new By.ByXPath("//td[@class='col-name' and normalize-space()='" + testerName + "']")).isDisplayed());

        } finally {
            driver.quit();
        }
    }

    @Test
    @DisplayName("3. Удаление созданной роли")
    public void removeCreatedRole() {

        try {
            log.info("1.Войти на сайт с пользователем \"admin\"");
            permanentAuthorization();
            log.info("2. Создать роль \"tester_3\"");
            String testerName = "tester_" + new Random().ints(101, 200).findFirst().getAsInt();
            createRole(testerName);
            //Локатор кнопки "Удалить запись"
            WebElement deleteUserRoleButton = driver.findElement(new By.ByXPath("//td[normalize-space()='" + testerName + "']/..//button[@title='Delete record']"));
            //Локатор имени пользователя вновь созданного (в ролях)
            WebElement rolenameForDelete = driver.findElement(new By.ByXPath("//td[normalize-space()='" + testerName + "']"));
            log.info("3. Нажать на иконку \"Удалить запись\" роли с названием \"tester_3\"");
            deleteUserRoleButton.click();
            log.info("4. Появилось модальное диалоговое окно с текстом \"Вы уверены что хотите удалить эту запись?\"");
            Alert ConfirmDelete = driver.switchTo().alert();
            log.info("5. Нажать на кнопку \"ОК\"");
            ConfirmDelete.accept();
            log.info("6. В таблице \"Список\" отсутствует строка со значением \"tester_3\" в столбце \"Название роли\"");
            assertFalse(isElementExists(rolenameForDelete), "Пользователь не удален");

        } finally {
            driver.quit();
        }
    }

    @Test
    @DisplayName("4. Изменение роли, изменение поля Имя роли")
    public void editNameCreatedRole() {

        try {
            log.info("1.Войти на сайт с пользователем \"admin\"");
            permanentAuthorization();
            log.info("2. Создать роль \"tester_3\"");
            String testerName = "tester_" + new Random().ints(201, 300).findFirst().getAsInt();
            String editedTesterName = "tester_" + new Random().ints(301, 400).findFirst().getAsInt();
            createRole(testerName);
            //Локатор кнопки "Редактировать запись"
            WebElement editUserButton = driver.findElement(new By.ByXPath("//td[normalize-space()='" + testerName + "']/..//a[@title='Редактировать запись']"));
            log.info("3. Нажать на иконку \"Редактировать запись\" роли с названием \"tester_4_1\"");
            editUserButton.click();
            //Локатор поля "Название роли"
            WebElement nameRoleField = driver.findElement(new By.ByXPath("//*[@id=\"name\"]"));
            //Локатор кнопки "Сохранить"
            WebElement saveButton = driver.findElement(new By.ByXPath("//*[@id=\"fa_modal_window\"]/..//input[@value='Сохранить']"));
            log.info("4. Ввести в поле \"Название роли\" значение \"tester_4_2\"");
            nameRoleField.clear();
            nameRoleField.sendKeys(editedTesterName);
            log.info("5. Нажать на кнопку \"Сохранить\"");
            saveButton.click();
            log.info("6. В таблице \"Список\" отображается строка со значением \"tester_4_2\" в столбце \"Название роли\"");
            assertTrue(driver.findElement(new By.ByXPath("//td[normalize-space()='" + editedTesterName + "']")).isDisplayed(), "Отсутствует изменённый пользователь в списке");

        } finally {
            driver.quit();
        }
    }

    @Test
    @DisplayName("5. Изменение роли, изменение поля Описание")
    public void editDescriptionCreatedRole() {

        try {
            log.info("1.Войти на сайт с пользователем \"admin\"");
            permanentAuthorization();
            log.info("2. Создать роль \"tester_3\"");
            String testerName = "tester_" + new Random().ints(401, 500).findFirst().getAsInt();
            String editedDescription = "Описание роли" + new Random().ints(301, 400).findFirst().getAsInt();
            createRole(testerName);
            //Локатор кнопки "Редактировать запись"
            WebElement editUserButton = driver.findElement(new By.ByXPath("//td[normalize-space()='" + testerName + "']/..//a[@title='Редактировать запись']"));
            log.info("3. Нажать на иконку \"Редактировать запись\" роли с названием \"tester_4_1\"");
            editUserButton.click();
            //Локатор поля "Описание"
            WebElement descriptionField = driver.findElement(new By.ByXPath("//*[@id=\"description\"]"));
            //Локатор кнопки "Сохранить"
            WebElement saveButton = driver.findElement(new By.ByXPath("//*[@id=\"fa_modal_window\"]/..//input[@value='Сохранить']"));
            log.info("4. Ввести в поле \"Описание\" значение \"Описание роли\"");
            descriptionField.clear();
            descriptionField.sendKeys(editedDescription);
            log.info("5. Нажать на кнопку \"Сохранить\"");
            saveButton.click();
            log.info("6. В таблице \"Список\" отображается строка со значением \"tester_4_2\" в столбце \"Название роли\"");
            assertTrue(driver.findElement(new By.ByXPath("//td[normalize-space()='" + editedDescription + "']")).isDisplayed(), "Отсутствует изменённое описание роли в списке");

        } finally {
            driver.quit();
        }
    }

    @Test
    @DisplayName("6. Просмотр роли")
    public void checkViewRoleAdmin() {

        try {
            log.info("1.Войти на сайт с пользователем \"admin\"");
            permanentAuthorization();
            //Локатор раздела "Администрирование":
            WebElement administration = driver.findElement(new By.ByXPath("//a[normalize-space()='Администрирование']"));
            //Локатор вкладки "Роли":
            WebElement roles = driver.findElement(new By.ByXPath("//a[text()='Роли']"));
            log.info("2. В главном меню перейти в раздел \"Администрирование -> Роли\"");
            administration.click();
            roles.click();
            //Локатор кнопки "Просмотр записи"
            WebElement ViewUserButton = driver.findElement(new By.ByXPath("//td[normalize-space()='admin']/..//a[@title='Просмотр записи']"));
            log.info("3. Нажать на иконку \"Просмотр записи\" пользователя с логином \"admin\"");
            ViewUserButton.click();
            // Ждем появления формы
            WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait1.until(webDriver -> driver.findElement(new By.ByXPath("//h3[contains(text(), 'Просмотр записи')]")).isDisplayed());
            //Локатор значения поля "Название роли":
            WebElement roleNameValue = driver.findElement(new By.ByXPath("//table//b[text()='Название роли']/../../td[2]"));
            //Локатор значения поля "Описание":
            WebElement descriptionValue = driver.findElement(new By.ByXPath("//table//b[text()='Описание']/../../td[2]"));
            log.info("4. В поле \"Название роли\" присутствует значение \"admin\"");
            assertTrue(driver.findElement(new By.ByXPath("//table//b[text()='Название роли']")).isDisplayed(), "Поле \"Название роли\" отсутствует");
            assertTrue(roleNameValue.isDisplayed(), "Значение поля \"Название роли\" отсутствует");
            String expectedRoleNameValue = "admin";
            String actualRoleNameValue = roleNameValue.getText();
            assertEquals(expectedRoleNameValue, actualRoleNameValue);
            log.info("5. В поле \"Описание\" отсутствует значение");
            assertTrue(driver.findElement(new By.ByXPath("//table//b[text()='Описание']")).isDisplayed(), "Поле \"Описание\" отсутствует");
            assertTrue(descriptionValue.getText().isEmpty(), "В поле \"Описание\" присутствует значение ");
            log.info("6. В поле \"Агенты\" присутствует значение \"CerediraTess\" - Поле отсутствует в данном релизе программы");
            log.info("7. В поле \"Пользователи\" присутствует значение \"admin\" - Поле отсутствует в данном релизе программы");

        } finally {
            driver.quit();
        }
    }

    @Test
    @DisplayName("7. Дублирование записи роли, позитивный сценарий (1 способ)")
    public void duplicateRoleEntryFirstWay() {

        try {
            log.info("1.Войти на сайт с пользователем \"admin\"");
            permanentAuthorization();
            //Локатор раздела "Администрирование":
            WebElement administration = driver.findElement(new By.ByXPath("//a[normalize-space()='Администрирование']"));
            //Локатор вкладки "Роли":
            WebElement roles = driver.findElement(new By.ByXPath("//a[text()='Роли']"));
            log.info("2. В главном меню перейти в раздел \"Администрирование -> Роли\"");
            administration.click();
            roles.click();
            //Локатор кнопки "Дублировать запись"
            WebElement duplicateRowButton = driver.findElement(new By.ByXPath("//td[normalize-space()='admin']/..//a[@title='Duplicate Row']"));
            log.info("3.Нажать на иконку \"Дублировать запись\" роли с названием \"admin\"");
            duplicateRowButton.click();
            //Локатор поля "Название роли"
            WebElement nameRoleField = driver.findElement(new By.ByXPath("//*[@id=\"name\"]"));
            //Локатор кнопки "Сохранить"
            WebElement saveButton = driver.findElement(new By.ByXPath("//input[@value='Сохранить']"));
            log.info("4. Ввести в поле \"Название роли\" значение \"admin_7\"");
            String adminName = "admin_" + new Random().ints(1, 100).findFirst().getAsInt();
            nameRoleField.clear();
            nameRoleField.sendKeys(adminName);
            log.info("5. Нажать на кнопку \"Сохранить\"");
            saveButton.click();
            log.info("6. В таблице \"Список\" отображается строка со значением \"admin_7\" в столбце \"Название роли\"");
            assertTrue(driver.findElement(new By.ByXPath("//td[@class='col-name' and normalize-space()='" + adminName + "']")).isDisplayed());

        } finally {
            driver.quit();
        }
    }

    @Test
    @DisplayName("8. Дублирование записи роли, позитивный сценарий (2 способ)")
    public void duplicateRoleEntrySecondWay() {

        try {
            log.info("1.Войти на сайт с пользователем \"admin\"");
            permanentAuthorization();
            //Локатор раздела "Администрирование":
            WebElement administration = driver.findElement(new By.ByXPath("//a[normalize-space()='Администрирование']"));
            //Локатор вкладки "Роли":
            WebElement roles = driver.findElement(new By.ByXPath("//a[text()='Роли']"));
            log.info("2. В главном меню перейти в раздел \"Администрирование -> Роли\"");
            administration.click();
            roles.click();
            //Локатор кнопки "Дублировать запись"
            WebElement duplicateRowButton = driver.findElement(new By.ByXPath("//td[normalize-space()='admin']/..//a[@title='Duplicate Row']"));
            log.info("3.Нажать на иконку \"Дублировать запись\" роли с названием \"admin\"");
            duplicateRowButton.click();
            //Локатор поля "Название роли"
            WebElement nameRoleField = driver.findElement(new By.ByXPath("//*[@id=\"name\"]"));
            //Локатор кнопки "Сохранить и добавить новый объект"
            WebElement saveAndAddButton = driver.findElement(new By.ByXPath("//input[@value='Сохранить и добавить новый объект']"));
            log.info("4. Ввести в поле \"Название роли\" значение \"admin_7\"");
            String adminName = "admin_" + new Random().ints(101, 200).findFirst().getAsInt();
            nameRoleField.clear();
            nameRoleField.sendKeys(adminName);
            log.info("5. Нажать на кнопку \"Сохранить и добавить новый объект\"");
            saveAndAddButton.click();
            log.info("6. Текущая страница ${url}:7801/admin/Role/duplicate/?id=${id дублируемой роли}");
            assertTrue(driver.getCurrentUrl().matches("https?:\\/\\/.*:7801\\/admin\\/Role\\/duplicate\\/\\?id=\\d+"));
            log.info("7. В строке \"Название роли\" осталось прежнее название \"admin\"");
            String expectedNameRoleField = "admin";
            String actualNameRoleField = driver.findElement(new By.ByXPath("//input[@value='admin']")).getAttribute("value");
            assertEquals(expectedNameRoleField, actualNameRoleField);
            //Локатор вкладки "Список"
            WebElement listRole = driver.findElement(new By.ByXPath("//a[text()='Список']"));
            log.info("8. Перейти во вкладку \"Список\"");
            listRole.click();
            log.info("9. В таблице \"Список\" отображается строка со значением \"admin_8\" в столбце \"Название роли\"");
            assertTrue(driver.findElement(new By.ByXPath("//td[@class='col-name' and normalize-space()='" + adminName + "']")).isDisplayed());

        } finally {
            driver.quit();
        }
    }

    @Test
    @DisplayName("9. Дублирование записи роли, позитивный сценарий (3 способ)")
    public void duplicateRoleEntryThirdWay() {

        try {
            log.info("1.Войти на сайт с пользователем \"admin\"");
            permanentAuthorization();
            //Локатор раздела "Администрирование":
            WebElement administration = driver.findElement(new By.ByXPath("//a[normalize-space()='Администрирование']"));
            //Локатор вкладки "Роли":
            WebElement roles = driver.findElement(new By.ByXPath("//a[text()='Роли']"));
            log.info("2. В главном меню перейти в раздел \"Администрирование -> Роли\"");
            administration.click();
            roles.click();
            //Локатор кнопки "Дублировать запись"
            WebElement duplicateRowButton = driver.findElement(new By.ByXPath("//td[normalize-space()='admin']/..//a[@title='Duplicate Row']"));
            log.info("3.Нажать на иконку \"Дублировать запись\" роли с названием \"admin\"");
            duplicateRowButton.click();
            //Локатор поля "Название роли"
            WebElement nameRoleField = driver.findElement(new By.ByXPath("//*[@id=\"name\"]"));
            //Локатор кнопки "Сохранить и добавить новый объект"
            WebElement saveAndContinueEditingButton = driver.findElement(new By.ByXPath("//input[@value='Сохранить и продолжить редактирование']"));
            log.info("4. Ввести в поле \"Название роли\" значение \"admin_9\"");
            String adminName = "admin_" + new Random().ints(201, 300).findFirst().getAsInt();
            nameRoleField.clear();
            nameRoleField.sendKeys(adminName);
            log.info("5. Нажать на кнопку \"Сохранить и продолжить редактирование\"");
            saveAndContinueEditingButton.click();
            log.info("6. Текущая страница ${url}:7801/admin/Role/duplicate/?id=${id дублируемой роли}");
            assertTrue(driver.getCurrentUrl().matches("https?:\\/\\/.*:7801\\/admin\\/Role\\/edit\\/\\?id=\\d+&url=.*"));
            log.info("7. В строке \"Название роли\" осталось прежнее название \"admin\"");
            assertTrue(driver.findElement(new By.ByXPath("//input[@value='" + adminName + "']")).isDisplayed());
            //Локатор вкладки "Список"
            WebElement listRole = driver.findElement(new By.ByXPath("//a[text()='Список']"));
            log.info("8. Перейти во вкладку \"Список\"");
            listRole.click();
            log.info("9. В таблице \"Список\" отображается строка со значением \"admin_8\" в столбце \"Название роли\"");
            assertTrue(driver.findElement(new By.ByXPath("//td[@class='col-name' and normalize-space()='" + adminName + "']")).isDisplayed());

        } finally {
            driver.quit();
        }
    }

    @Test
    @DisplayName("10. Дублирование записи роли, негативный сценарий")
    public void duplicateRoleEntryNegativeWay() {

        try {
            log.info("1.Войти на сайт с пользователем \"admin\"");
            permanentAuthorization();
            //Локатор раздела "Администрирование":
            WebElement administration = driver.findElement(new By.ByXPath("//a[normalize-space()='Администрирование']"));
            //Локатор вкладки "Роли":
            WebElement roles = driver.findElement(new By.ByXPath("//a[text()='Роли']"));
            log.info("2. В главном меню перейти в раздел \"Администрирование -> Роли\"");
            administration.click();
            roles.click();
            //Локатор кнопки "Дублировать запись"
            WebElement duplicateRowButton = driver.findElement(new By.ByXPath("//td[normalize-space()='admin']/..//a[@title='Duplicate Row']"));
            log.info("3.Нажать на иконку \"Дублировать запись\" роли с названием \"admin\"");
            duplicateRowButton.click();
            //Локатор поля "Название роли"
            WebElement nameRoleField = driver.findElement(new By.ByXPath("//*[@id=\"name\"]"));
            //Локатор кнопки "Сохранить"
            WebElement saveButton = driver.findElement(new By.ByXPath("//input[@value='Сохранить']"));
            log.info("4. Ввести в поле \"Название роли\" значение \"admin\"");
            nameRoleField.clear();
            nameRoleField.sendKeys("admin");
            log.info("5. Нажать на кнопку \"Сохранить\"");
            saveButton.click();
            //Локатор ошибки целостности
            WebElement errorMessage = driver.findElement(new By.ByXPath("//div[@class=\"alert alert-danger alert-dismissable\" and contains(string(), \"Ошибка целостности.\")]"));
            log.info("6. Под главным меню появилось сообщение \"Ошибка целостности.\"");
            assertTrue(errorMessage.isDisplayed());
            //Локатор вкладки "Список"
            WebElement listRole = driver.findElement(new By.ByXPath("//a[text()='Список']"));
            log.info("7. Перейти во вкладку \"Список\"");
            listRole.click();
            log.info("8. В таблице \"Список\" содержится только 1 строка со значением \"admin\" в столбце \"Название роли\"");
            List<WebElement> countОfLines = driver.findElements(new By.ByXPath("//td[@class='col-name' and normalize-space()='admin']"));
            int expectedCountList = 1;
            int actualCountList = countОfLines.size();
            assertEquals(expectedCountList, actualCountList);

        } finally {
            driver.quit();
        }
    }

    @Test
    @DisplayName("11. Удаление записи роли admin, негативный сценарий")
    //ЭТОТ ТЕСТ В ДАННОМ РЕЛИЗЕ НЕ ЗАПУСКАТЬ, ВСЁ ЛОМАЕТ
    public void DeletingRoleEntryAdminNegativeWay() {

        try {
            log.info("1.Войти на сайт с пользователем \"admin\"");
            permanentAuthorization();
            //Локатор раздела "Администрирование":
            WebElement administration = driver.findElement(new By.ByXPath("//a[normalize-space()='Администрирование']"));
            //Локатор вкладки "Роли":
            WebElement roles = driver.findElement(new By.ByXPath("//a[text()='Роли']"));
            log.info("2. В главном меню перейти в раздел \"Администрирование -> Роли\"");
            administration.click();
            roles.click();
            //Локатор кнопки "Удалить запись" для admin
            WebElement deleteAdminUserButton = driver.findElement(new By.ByXPath("//td[normalize-space()='admin']/..//button[@title='Delete record']"));
            log.info("3. Нажать на иконку \"Удалить запись\" роли с названием \"admin\"");
            deleteAdminUserButton.click();
            log.info("4. Появилось модальное диалоговое окно с текстом \"Вы уверены что хотите удалить эту запись?\"");
            Alert ConfirmDelete = driver.switchTo().alert();
            log.info("5. Нажать на кнопку \"ОК\"");
//            ConfirmDelete.accept();
            fail("ЭТОТ ТЕСТ В ДАННОМ РЕЛИЗЕ УДАЛЯЕТ УЧЁТНУЮ ЗАПИСЬ admin");
            log.info("6. В таблице \"Список\" присутствует строка со значением \"admin\" в столбце \"Название роли\" - не реализовано в данном релизе программы");
            log.info("7. Под главным меню появилось сообщение \"Ошибка удаления записи: Нельзя удалить роль admin.\" - не реализовано в данном релизе программы");

        } finally {
            driver.quit();
        }
    }

    @Test
    @DisplayName("12. Удаление записи роли через выбор")
    public void removeCreatedRoleViaSelected() {

        try {
            log.info("1.Войти на сайт с пользователем \"admin\"");
            permanentAuthorization();
            log.info("2. Создать роль \"tester_12\"");
            String testerName = "tester_" + new Random().ints(501, 600).findFirst().getAsInt();
            createRole(testerName);
            //Локатор имени пользователя вновь созданного
            WebElement usernameForDelete = driver.findElement(new By.ByXPath("//td[normalize-space()='" + testerName + "']"));
            //Локатор чекбокса в таблице пользователей+
            WebElement checkBoxTable = driver.findElement(new By.ByXPath("//td[normalize-space()='" + testerName + "']/..//input[@type='checkbox']"));
            log.info("3. Нажать на чекбокс \"Выберите запись\" роли с названием \"tester_12\"");
            checkBoxTable.click();
            //Локатор вкладки "С выбранным"
            WebElement withSelected = driver.findElement(new By.ByXPath("//a[text()='С выбранным']"));
            log.info("4. Перейти во вкладку \"С выбранным\"");
            withSelected.click();
            //Локатор пункта меню "Удалить"
            WebElement deleteUserButton = driver.findElement(new By.ByXPath("//a[text()='Удалить']"));
            log.info("5. Выбрать пункт из меню \"Удалить\"");
            deleteUserButton.click();
            log.info("6. Появилось модальное диалоговое окно с текстом \"Вы уверены что хотите удалить?\"");
            Alert ConfirmDelete = driver.switchTo().alert();
            log.info("7. Нажать на \"ОК\"");
            ConfirmDelete.accept();
            log.info("8. В списке отсутствует строка со значением \"user\" в столбце \"Логин пользователя\"");
            assertFalse(isElementExists(usernameForDelete), "Пользователь не удален");

        } finally {
            driver.quit();
        }
    }
    @Test
    @DisplayName("13. Удаление записей ролей через выбор Все, негативный сценарий")
    public void removeAllCreatedRolesViaSelectedAllNegativeWay() {

        try {
            log.info("1.Войти на сайт с пользователем \"admin\"");
            permanentAuthorization();
            log.info("2. Создать роль \"tester_13_1\"");
            String testerName = "tester_" + new Random().ints(601, 700).findFirst().getAsInt();
            createRole(testerName);
            log.info("3. Создать роль \"tester_13_2\"");
            String testerName2 = "tester_" + new Random().ints(701, 800).findFirst().getAsInt();
            createRole(testerName2);
            log.info("4. Создать роль \"tester_13_3\"");
            String testerName3 = "tester_" + new Random().ints(801, 900).findFirst().getAsInt();
            createRole(testerName3);
            //Локатор чекбокса в таблице пользователей+
            WebElement checkBoxAllTable = driver.findElement(new By.ByXPath("//input[@type='checkbox']/..//input[@title='Выбрать все записи']"));
            log.info("5. Нажать на чекбокс \"Выбрать все записи\"");
            checkBoxAllTable.click();
            //Локатор вкладки "С выбранным"
            WebElement withSelected = driver.findElement(new By.ByXPath("//a[text()='С выбранным']"));
            log.info("6. Перейти во вкладку \"С выбранным\"");
            withSelected.click();
            //Локатор пункта меню "Удалить"
            WebElement deleteUserButton = driver.findElement(new By.ByXPath("//a[text()='Удалить']"));
            log.info("7. Выбрать пункт из меню \"Удалить\"");
            deleteUserButton.click();
            log.info("8. Появилось модальное диалоговое окно с текстом \"Вы уверены что хотите удалить?\"");
            Alert ConfirmDelete = driver.switchTo().alert();
            log.info("9. Нажать на \"ОК\"");
//            ConfirmDelete.accept();
            fail("ЭТОТ ТЕСТ В ДАННОМ РЕЛИЗЕ УДАЛЯЕТ УЧЁТНУЮ ЗАПИСЬ admin");
            log.info("10. В таблице \"Список\" отсутствуют 3 строки со значениями - \"tester_13_1\", \"tester_13_2\", \"tester_13_3\" и присутствует 1 строка со значением \"admin\", в столбце \"Название роли\"");
            log.info("11. Под главным меню появилось сообщение \"Ошибка удаления записи: Нельзя удалить роль admin.\"");

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

    public void createRole(String testerName) {

        try {
            //Локатор раздела "Администрирование":
            WebElement administration = driver.findElement(new By.ByXPath("//a[normalize-space()='Администрирование']"));
            //Локатор вкладки "Роли":
            WebElement roles = driver.findElement(new By.ByXPath("//a[text()='Роли']"));
            log.info("      1. В главном меню перейти в раздел \"Администрирование -> Роли\"");
            administration.click();
            roles.click();
            //Локатор кнопки "Создать"
            WebElement createButton = driver.findElement(new By.ByXPath("//a[normalize-space()='Создать']"));
            log.info("      2. Нажать на вкладку \"Создать\"");
            createButton.click();
            //Локатор поля "Название роли"
            WebElement nameRoleField = driver.findElement(new By.ByXPath("//*[@id=\"name\"]"));
            //Локатор кнопки "Сохранить"
            WebElement saveButton = driver.findElement(new By.ByXPath("//*[@id=\"fa_modal_window\"]/..//input[@value='Сохранить']"));
            log.info("      3. В поле \"Название роли\" ввести значение \"${userrole}\"");
            nameRoleField.sendKeys(testerName);
            log.info("      4. Нажать на кнопку \"Сохранить\"");
            saveButton.click();
            log.info("      5. В таблице \"Список\" отображается строка со значением \"${userrole}\" в столбце \"Название роли\"");
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
