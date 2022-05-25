package com.github.PycJIaH.Components.WebInterface.Auxiliary_functionality;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class Scripts {
    final Logger log = LoggerFactory.getLogger(Scripts.class);
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
            log.info("4. В поле \"Агенты\" выбрать значение \"CerediraTess\" - Поле отсутствует в данном релизе программы");
            //Локатор поля "Имя скрипта"
            WebElement scriptName = driver.findElement(new By.ByXPath("//*[@id=\"name\"]"));
            log.info("5. В поле \"Имя скрипта\" ввести значение \"test_1.bat\"");
            String scriptNameValue = "test_1_" + new Random().ints(0, 100).findFirst().getAsInt() + ".bat";
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

            log.info("СЛЕДУЮЩИЕ ШАГИ ПРИМЕНИМЫ ИЗ-ЗА ОТСУТСТВИЯ 4 ШАГА В ДАННОМ КЕЙСЕ И В НОВОМ РЕЛИЗЕ БУДУТ УДАЛЕНЫ");
            // Локатор раздела "Администрирование"
            WebElement administration2 = driver.findElement(new By.ByXPath("//a[normalize-space()='Администрирование']"));
            //Локатор раздела "Агенты"
            WebElement agents = driver.findElement(new By.ByXPath("//a[normalize-space()='Агенты']"));
            log.info("В главном меню перейти в раздел \"Администрирование -> Агенты\"");
            administration2.click();
            agents.click();
            //Локатор кнопки "Редактировать запись"
            WebElement editAgentCerediraTessButton = driver.findElement(new By.ByXPath("//td[normalize-space()='CerediraTess']/..//a[@title='Редактировать запись']"));
            log.info("Нажать на иконку \"Редактировать запись\" агента с названием \"CerediraTess\"");
            editAgentCerediraTessButton.click();
            //Локатор поля "Скрипты"
            WebElement scriptsField = driver.findElement(new By.ByXPath("//*[@id=\"s2id_scripts\"]/ul"));
            log.info("Выбрать в поле \"Скрипты\" ...");
            scriptsField.click();
            log.info("...значение вновь созданного имени скрипта");
            //Локатор значения вновь созданного скрипта
            WebElement chooseScriptNameValue = driver.findElement(new By.ByXPath("//*[contains(@id,\"select2-result-label-\") and text()='" + scriptNameValue + "']"));
            chooseScriptNameValue.click();
            //Локатор кнопки "Сохранить"
            WebElement saveButton2 = driver.findElement(new By.ByXPath("//*[@id=\"fa_modal_window\"]/..//input[@value='Сохранить']"));
            log.info("Нажать на кнопку \"Сохранить\"");
            saveButton2.click();

        } finally {
            driver.quit();
        }
    }

    @Test
    @DisplayName("2. Удаление записи скрипта, когда остался 1 скрипт")
    public void deleteScriptWhen1ScriptRemains() {

        try {
            log.info("1. Войти на сайт с пользователем \"admin\"");
            permanentAuthorization();
            log.info("2. Создать скрипт \"test_2.bat\"");
            String scriptNameValue = "test_2_" + new Random().ints(101, 200).findFirst().getAsInt() + ".bat";
            createScript(scriptNameValue);
            //Локатор имени скрипта вновь созданного (в скриптах)
            WebElement scriptForDelete = driver.findElement(new By.ByXPath("//td[normalize-space()='" + scriptNameValue + "']"));
            //Локатор кнопки "Удалить запись"
            WebElement deleteUserRoleButton = driver.findElement(new By.ByXPath("//td[normalize-space()='" + scriptNameValue + "']/..//button[@title='Delete record']"));
            log.info("3. Нажать на иконку \"Удалить запись\" скрипта с именем \"test_2.bat\"");
            deleteUserRoleButton.click();
            log.info("4. Появилось модальное диалоговое окно с текстом \"Вы уверены что хотите удалить эту запись?\"");
            Alert ConfirmDelete = driver.switchTo().alert();
            log.info("5. Нажать на кнопку \"ОК\"");
            ConfirmDelete.accept();
            log.info("6. В списке отсутствует строка со значением \"test_2.bat\" в столбце \"Имя скрипта\"");
            assertFalse(isElementExists(scriptForDelete), "Скрипт не удален");
            log.info("7. В списке появилась надпись \"Нет элементов в таблице.\"");
            assertTrue(driver.findElement(new By.ByXPath("//div[normalize-space()='Нет элементов в таблице.']")).isDisplayed(), "В таблице присутствуют другие записи скриптов");

        } finally {
            driver.quit();
        }
    }

    @Test
    @DisplayName("3. Удаление записи скрипта, когда скрипт не последний")
    public void deleteScriptWhenScriptNotLast() {

        try {
            log.info("1. Войти на сайт с пользователем \"admin\"");
            permanentAuthorization();
            log.info("2. Создать скрипт \"test_3_1.bat\"");
            String scriptNameValue1 = "test_3_" + new Random().ints(0, 100).findFirst().getAsInt() + ".bat";
            createScript(scriptNameValue1);
            log.info("3. Создать скрипт \"test_3_2.bat\"");
            String scriptNameValue2 = "test_3_" + new Random().ints(101, 200).findFirst().getAsInt() + ".bat";
            createScript(scriptNameValue2);
            log.info("4. Создать скрипт \"test_3_3.bat\"");
            String scriptNameValue3 = "test_3_" + new Random().ints(201, 300).findFirst().getAsInt() + ".bat";
            createScript(scriptNameValue3);
            //Локатор имени скрипта вновь созданного (в скриптах)
            WebElement scriptForDelete2 = driver.findElement(new By.ByXPath("//td[normalize-space()='" + scriptNameValue2 + "']"));
            //Локатор кнопки "Удалить запись"
            WebElement deleteUserRoleButton = driver.findElement(new By.ByXPath("//td[normalize-space()='" + scriptNameValue2 + "']/..//button[@title='Delete record']"));
            log.info("5. Нажать на иконку \"Удалить запись\" скрипта с именем \"test_3_2.bat\"");
            deleteUserRoleButton.click();
            log.info("6. Появилось модальное диалоговое окно с текстом \"Вы уверены что хотите удалить эту запись?\"");
            Alert ConfirmDelete = driver.switchTo().alert();
            log.info("7. Нажать на кнопку \"ОК\"");
            ConfirmDelete.accept();
            log.info("8. В списке отсутствует строка со значением \"test_3_2.bat\" в столбце \"Имя скрипта\"");
            assertFalse(isElementExists(scriptForDelete2), "Скрипт созанный вторым не удален");

        } finally {
            driver.quit();
        }
    }

    @Test
    @DisplayName("4. Изменение записи скрипта, изменение поля Описание")
    public void changeScriptChangeFieldDescription() {

        try {
            log.info("1. Войти на сайт с пользователем \"admin\"");
            permanentAuthorization();
            log.info("2. Создать скрипт \"test_4_1.bat\"");
            String scriptNameValue = "test_4_" + new Random().ints(0, 100).findFirst().getAsInt() + ".bat";
            createScript(scriptNameValue);
            //Локатор кнопки "Редактировать запись"
            WebElement editUserButton = driver.findElement(new By.ByXPath("//td[normalize-space()='" + scriptNameValue + "']/..//a[@title='Редактировать запись']"));
            log.info("3. Нажать на иконку \"Редактировать запись\" скрипта с именем \"test_4.bat\"");
            editUserButton.click();
            //Локатор поля "Описание"
            WebElement descriptionField = driver.findElement(new By.ByXPath("//*[@id=\"description\"]"));
            //Локатор кнопки "Сохранить"
            WebElement saveButton = driver.findElement(new By.ByXPath("//*[@id=\"fa_modal_window\"]/..//input[@value='Сохранить']"));
            log.info("4. Ввести в поле \"Описание\" значение \"Новое описание\"");
            String editedDescription = "Новое описание" + new Random().ints(0, 100).findFirst().getAsInt();
            descriptionField.clear();
            descriptionField.sendKeys(editedDescription);
            log.info("5. Нажать на кнопку \"Сохранить\"");
            saveButton.click();
            log.info("6. В таблице \"Список\" отображается строка со значением \"Новое описание\" в столбце \"Описание\"");
            assertTrue(driver.findElement(new By.ByXPath("//td[normalize-space()='" + editedDescription + "']")).isDisplayed(), "Отсутствует изменённое описание скрипта в списке");

        } finally {
            driver.quit();
        }
    }

    @Test
    @DisplayName("5. Изменение записи скрипта, изменение поля Имя скрипта")
    public void changeScriptChangeFieldName() {

        try {
            log.info("1. Войти на сайт с пользователем \"admin\"");
            permanentAuthorization();
            log.info("2. Создать скрипт \"test_5_1.bat\"");
            String scriptNameValue = "test_5_1_" + new Random().ints(0, 100).findFirst().getAsInt() + ".bat";
            createScript(scriptNameValue);
            //Локатор кнопки "Редактировать запись"
            WebElement editUserButton = driver.findElement(new By.ByXPath("//td[normalize-space()='" + scriptNameValue + "']/..//a[@title='Редактировать запись']"));
            log.info("3. Нажать на иконку \"Редактировать запись\" скрипта с именем \"test_5_1.bat\"");
            editUserButton.click();
            //Локатор поля "Имя скрипта"
            WebElement scriptNameField = driver.findElement(new By.ByXPath("//*[@id=\"name\"]"));
            //Локатор кнопки "Сохранить"
            WebElement saveButton = driver.findElement(new By.ByXPath("//*[@id=\"fa_modal_window\"]/..//input[@value='Сохранить']"));
            log.info("4. Ввести в поле \"Имя скрипта\" значение \"test_5_2.bat\"");
            String editedScriptName = "test_5_2_" + new Random().ints(0, 100).findFirst().getAsInt() + ".bat";
            scriptNameField.clear();
            scriptNameField.sendKeys(editedScriptName);
            log.info("5. Нажать на кнопку \"Сохранить\"");
            saveButton.click();
            log.info("6. В таблице \"Список\" отображается строка со значением \"test_5_2.bat\" в столбце \"Имя скрипта\"");
            assertTrue(driver.findElement(new By.ByXPath("//td[normalize-space()='" + editedScriptName + "']")).isDisplayed(), "Отсутствует изменённое имя скрипта в списке");

        } finally {
            driver.quit();
        }
    }

    @Test
    @DisplayName("6. Просмотр записи скрипта")
    public void viewScriptEntry() {

        try {
            log.info("1. Войти на сайт с пользователем \"admin\"");
            permanentAuthorization();
            log.info("2. Создать скрипт \"test_6.bat\"");
            String scriptNameValue = "test_6_" + new Random().ints(0, 100).findFirst().getAsInt() + ".bat";
            createScript(scriptNameValue);
            //Локатор кнопки "Просмотр записи"
            WebElement ViewScriptButton = driver.findElement(new By.ByXPath("//td[normalize-space()='" + scriptNameValue + "']/..//a[@title='Просмотр записи']"));
            log.info("3. Нажать на иконку \"Просмотр записи\" скрипта с именем \"test_6.bat\"");
            ViewScriptButton.click();
            // Ждем появления формы
            WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait1.until(webDriver -> driver.findElement(new By.ByXPath("//h3[contains(text(), 'Просмотр записи')]")).isDisplayed());
            //Локатор значения поля "Имя скрипта":
            WebElement scriptViewNameValue = driver.findElement(new By.ByXPath("//table//b[text()='Имя скрипта']/../../td[2]"));
            //Локатор значения поля "Описание":
            WebElement descriptionValue = driver.findElement(new By.ByXPath("//table//b[text()='Описание']/../../td[2]"));
            log.info("4. В поле \"Имя скрипта\" присутствует значение \"test_6.bat\"");
            assertTrue(driver.findElement(new By.ByXPath("//table//b[text()='Имя скрипта']")).isDisplayed(), "Поле \"Имя скрипта\" отсутствует");
            assertTrue(scriptViewNameValue.isDisplayed(), "Значение поля \"Имя скрипта\" отсутствует");
            String expectedScriptViewNameValue = scriptNameValue;
            String actualScriptViewNameValue = scriptViewNameValue.getText();
            assertEquals(expectedScriptViewNameValue, actualScriptViewNameValue);
            log.info("5. В поле \"Описание\" отсутствует значение");
            assertTrue(driver.findElement(new By.ByXPath("//table//b[text()='Описание']")).isDisplayed(), "Поле \"Описание\" отсутствует");
            assertTrue(descriptionValue.getText().isEmpty(), "В поле \"Описание\" присутствует значение ");
            log.info("6. В поле \"Агенты\" отсутствует значение - Поле отсутствует в данном релизе программы");

        } finally {
            driver.quit();
        }
    }

    @Test
    @DisplayName("7. Дублирование записи скрипта, позитивный сценарий (1 способ)")
    public void duplicateScriptEntryFirstWay() {

        try {
            log.info("1. Войти на сайт с пользователем \"admin\"");
            permanentAuthorization();
            log.info("2. Создать скрипт \"test_7_1.bat\"");
            String scriptNameValue = "test_7_1_" + new Random().ints(0, 100).findFirst().getAsInt() + ".bat";
            createScript(scriptNameValue);
            //Локатор кнопки "Дублировать запись" скрипта с именем "test_7_1.bat"
            WebElement duplicateScriptButton = driver.findElement(new By.ByXPath("//td[normalize-space()='" + scriptNameValue + "']/..//a[@title='Duplicate Row']"));
            log.info("3.Нажать на иконку \"Дублировать запись\" скрипта с именем \"test_7_1.bat\"");
            duplicateScriptButton.click();
            //Локатор поля "Имя скрипта"
            WebElement scriptNameField = driver.findElement(new By.ByXPath("//*[@id=\"name\"]"));
            //Локатор кнопки "Сохранить"
            WebElement saveButton = driver.findElement(new By.ByXPath("//*[@class=\"form-group\"]/..//input[@value='Сохранить']"));
            log.info("4.Ввести в поле \"Имя скрипта\" значение \"test_7_2.bat\"");
            String editedScriptName = "test_7_2_" + new Random().ints(0, 100).findFirst().getAsInt() + ".bat";
            scriptNameField.clear();
            scriptNameField.sendKeys(editedScriptName);
            log.info("5.Нажать на кнопку \"Сохранить\"");
            saveButton.click();
            log.info("6. В таблице \"Список\" отображается строка со значением \"test_7_2.bat\" в столбце \"Имя скрипта\"");
            assertTrue(driver.findElement(new By.ByXPath("//td[@class='col-name' and normalize-space()='" + editedScriptName + "']")).isDisplayed());

        } finally {
            driver.quit();
        }
    }

    @Test
    @DisplayName("8. Дублирование записи скрипта, позитивный сценарий (2 способ)")
    public void duplicateScriptEntrySecondWay() {

        try {
            log.info("1. Войти на сайт с пользователем \"admin\"");
            permanentAuthorization();
            log.info("2. Создать скрипт \"test_8_1.bat\"");
            String scriptNameValue = "test_8_1_" + new Random().ints(0, 100).findFirst().getAsInt() + ".bat";
            createScript(scriptNameValue);
            //Локатор кнопки "Дублировать запись" скрипта с именем "test_8_1.bat"
            WebElement duplicateScriptButton = driver.findElement(new By.ByXPath("//td[normalize-space()='" + scriptNameValue + "']/..//a[@title='Duplicate Row']"));
            log.info("3.Нажать на иконку \"Дублировать запись\" скрипта с именем \"test_8_1.bat\"");
            duplicateScriptButton.click();
            //Локатор поля "Имя скрипта"
            WebElement scriptNameField = driver.findElement(new By.ByXPath("//*[@id=\"name\"]"));
            //Локатор кнопки "Сохранить и добавить новый объект"
            WebElement saveAndAddButton = driver.findElement(new By.ByXPath("//input[@value='Сохранить и добавить новый объект']"));
            log.info("4.Ввести в поле \"Имя скрипта\" значение \"test_8_2.bat\"");
            String editedScriptName = "test_8_2_" + new Random().ints(0, 100).findFirst().getAsInt() + ".bat";
            scriptNameField.clear();
            scriptNameField.sendKeys(editedScriptName);
            log.info("5.Нажать на кнопку \"Сохранить и добавить новый объект\"");
            saveAndAddButton.click();
            log.info("6. Текущая страница ${url}:7801/admin/Script/duplicate/?id=${id дублируемого скрипта}\n");
            assertTrue(driver.getCurrentUrl().matches("https?:\\/\\/.*:7801\\/admin\\/Script\\/duplicate\\/\\?id=\\d+"));
            log.info("7. В строке \"Имя скрипта\" осталось прежнее название \"test_8_1.bat\"");
            String expectedNameScriptField = scriptNameValue;
            String actualNameScriptField = driver.findElement(new By.ByXPath("//input[@value='" + scriptNameValue + "']")).getAttribute("value");
            assertEquals(expectedNameScriptField, actualNameScriptField);
            //Локатор вкладки "Список"
            WebElement listRole = driver.findElement(new By.ByXPath("//a[text()='Список']"));
            log.info("8. Перейти во вкладку \"Список\"");
            listRole.click();
            log.info("9. В таблице \"Список\" отображается строка со значением \"test_8_2.bat\" в столбце \"Имя скрипта\"");
            assertTrue(driver.findElement(new By.ByXPath("//td[@class='col-name' and normalize-space()='" + editedScriptName + "']")).isDisplayed());

        } finally {
            driver.quit();
        }
    }

    @Test
    @DisplayName("9. Дублирование записи скрипта, негативный сценарий")
    public void duplicateScriptEntryNegativeWay() {

        try {
            log.info("1. Войти на сайт с пользователем \"admin\"");
            permanentAuthorization();
            log.info("2. Создать скрипт \"test_9.bat\"");
            String scriptNameValue = "test_9_" + new Random().ints(0, 100).findFirst().getAsInt() + ".bat";
            createScript(scriptNameValue);
            //Локатор кнопки "Дублировать запись" скрипта с именем "test_9.bat"
            WebElement duplicateScriptButton = driver.findElement(new By.ByXPath("//td[normalize-space()='" + scriptNameValue + "']/..//a[@title='Duplicate Row']"));
            log.info("3. Нажать на иконку \"Дублировать запись\" скрипта с именем \"test_9.bat\"");
            duplicateScriptButton.click();
            //Локатор поля "Имя скрипта"
            WebElement scriptNameField = driver.findElement(new By.ByXPath("//*[@id=\"name\"]"));
            //Локатор кнопки "Сохранить"
            WebElement saveButton = driver.findElement(new By.ByXPath("//*[@class=\"form-group\"]/..//input[@value='Сохранить']"));
            log.info("4. Ввести в поле \"Имя скрипта\" значение \"test_9.bat\"");
            scriptNameField.clear();
            scriptNameField.sendKeys(scriptNameValue);
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
            log.info("8. В таблице \"Список\" содержится только 1 строка со значением \"test_9.bat\" в столбце \"Имя скрипта\"");
            List<WebElement> countОfLines = driver.findElements(new By.ByXPath("//td[@class='col-name' and normalize-space()='" + scriptNameValue + "']"));
            int expectedCountList = 1;
            int actualCountList = countОfLines.size();
            assertEquals(expectedCountList, actualCountList);

        } finally {
            driver.quit();
        }
    }

    @Test
    @DisplayName("10. Удаление записи скрипта через выбор")
    public void removeCreatedScriptViaSelected() {

        try {
            log.info("1. Войти на сайт с пользователем \"admin\"");
            permanentAuthorization();
            log.info("2. Создать скрипт \"test_10.bat\"");
            String scriptNameValue = "test_10_" + new Random().ints(0, 100).findFirst().getAsInt() + ".bat";
            createScript(scriptNameValue);
            //Локатор имени пользователя вновь созданного
            WebElement scriptNameForDelete = driver.findElement(new By.ByXPath("//td[normalize-space()='" + scriptNameValue + "']"));
            //Локатор чекбокса в таблице пользователей+
            WebElement checkBoxTable = driver.findElement(new By.ByXPath("//td[normalize-space()='" + scriptNameValue + "']/..//input[@type='checkbox']"));
            log.info("3. Нажать на чекбокс \"Выберите запись\" скрипта с именем \"test_10.bat\"");
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
            log.info("7. Нажать на кнопку \"ОК\"");
            ConfirmDelete.accept();
            log.info("8. В таблице \"Список\" отсутствует строка со значением \"test_10.bat\" в столбце \"Имя скрипта\"");
            assertFalse(isElementExists(scriptNameForDelete), "Скрипт не удален");

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
        }
    }

    public void createScript(String scriptName) {

        try {
            //Локатор раздела "Администрирование":
            WebElement administration = driver.findElement(new By.ByXPath("//a[normalize-space()='Администрирование']"));
            // Локатор раздела "Скрипты"
            WebElement scripts = driver.findElement(new By.ByXPath("//a[normalize-space()='Скрипты']"));
            log.info("      1. В главном меню перейти в раздел \"Администрирование -> Скрипты\"");
            administration.click();
            scripts.click();
            //Локатор кнопки "Создать"
            WebElement createButton = driver.findElement(new By.ByXPath("//a[normalize-space()='Создать']"));
            log.info("      2. Нажать на вкладку \"Создать\"");
            createButton.click();
            //Локатор поля "Имя скрипта"
            WebElement scriptNameValue = driver.findElement(new By.ByXPath("//*[@id=\"name\"]"));
            //Локатор кнопки "Сохранить"
            WebElement saveButton = driver.findElement(new By.ByXPath("//*[@id=\"fa_modal_window\"]/..//input[@value='Сохранить']"));
            log.info("      3. В поле \"Имя скрипта\" вставить \"${test_name}\"");
            scriptNameValue.sendKeys(scriptName);
            log.info("      4. Нажать на кнопку \"Сохранить\"");
            saveButton.click();
            log.info("      5. В таблице \"Список\" отображается строка со значением \"${test_name}\" в столбце \"Имя скрипта\"");
            assertTrue(driver.findElement(new By.ByXPath("//td[@class='col-name' and normalize-space()='" + scriptName + "']")).isDisplayed());

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
