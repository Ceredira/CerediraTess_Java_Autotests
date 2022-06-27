package com.github.PycJIaH.Components.WebInterface.Auxiliary_functionality;

import com.github.PycJIaH.Components.WebInterface.Pages.*;
import com.github.PycJIaH.Components.WebInterface.Pages.ModalWindows.ModalWindowCreateRoles;
import com.github.PycJIaH.Components.WebInterface.Pages.ModalWindows.ModalWindowDuplicateRoles;
import com.github.PycJIaH.Components.WebInterface.Pages.ModalWindows.ModalWindowEditRoles;
import com.github.PycJIaH.Components.WebInterface.Pages.ModalWindows.ModalWindowsViewRoles;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class Roles {

    final Logger log = LoggerFactory.getLogger(Roles.class);
    WebDriver driver;

    @BeforeAll
    public static void beforeAll() {
        System.setProperty("webdriver.chrome.driver", "C:\\WebDriver\\bin\\chromedriver.exe");
    }

    @BeforeEach
    public void beforeEach() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
    }


    @Test
    @DisplayName("1. Существование, при старте с нуля, роли по умолчанию \"admin\"")
    public void existenceCheckRoleAdmin() {
        LoginPage lp = new LoginPage(driver);
        MainPage mp = new MainPage(driver);

        lp.permanentAuthorization();
        RolesPage rp = mp.moveToAdministrationRoles();
        rp.countOfLinesWithNameAdmin();

    }


    @Test
    @DisplayName("2. Создание роли")
    public void createRoleTest() {
        LoginPage lp = new LoginPage(driver);
        MainPage mp = new MainPage(driver);

        lp.permanentAuthorization();
        RolesPage rp = mp.moveToAdministrationRoles();
        ModalWindowCreateRoles mwcr = rp.createButtonClick();
        mwcr.agentFieldClick();
        mwcr.dropDownElementClick();
        mwcr.usersFieldClick();
        mwcr.adminChoiceClick();
        String testerName = ("tester_" + new Random().ints(0, 100).findFirst().getAsInt());
        mwcr.nameRoleFieldSendKeys(testerName);
        mwcr.descriptionFieldSendKeys("Тестирует систему");
        mwcr.saveButtonClick();
        rp.createdRoleIsDisplayed(testerName);
    }

    @Test
    @DisplayName("3. Удаление созданной роли")
    public void removeCreatedRole() {
        RolesPage rp = new RolesPage(driver);

        String testerName = "tester_" + new Random().ints(101, 200).findFirst().getAsInt();
        rp.createRole(testerName);
        WebElement roleCell = rp.getRoleCell(testerName);
        rp.deleteUserRoleButtonClick(testerName);
        rp.confirmDeleteClick();
        rp.isRoleNameRemoved(roleCell, testerName);

    }

    @Test
    @DisplayName("4. Изменение роли, изменение поля Имя роли")
    public void editNameCreatedRole() {
        RolesPage rp = new RolesPage(driver);

        String testerName = "tester_" + new Random().ints(201, 300).findFirst().getAsInt();
        String editedTesterName = "tester_" + new Random().ints(301, 400).findFirst().getAsInt();
        rp.createRole(testerName);
        ModalWindowEditRoles mwer = rp.editUserButtonClick(testerName);
        mwer.nameRoleFieldSendKeys(editedTesterName);
        mwer.saveButtonClick();
        rp.createdRoleIsDisplayed(editedTesterName);

    }

    @Test
    @DisplayName("5. Изменение роли, изменение поля Описание")
    public void editDescriptionCreatedRole() {
        RolesPage rp = new RolesPage(driver);

        String testerName = "tester_" + new Random().ints(401, 500).findFirst().getAsInt();
        String editedDescription = "Описание роли_" + new Random().ints(301, 400).findFirst().getAsInt();
        rp.createRole(testerName);
        ModalWindowEditRoles mwer = rp.editUserButtonClick(testerName);
        mwer.descriptionFieldSendKeys(editedDescription);
        mwer.saveButtonClick();
        rp.editedDescriptionIsDisplayed(editedDescription);
    }

    @Test
    @DisplayName("6. Просмотр роли")
    public void checkViewRoleAdmin() {
        LoginPage lp = new LoginPage(driver);
        MainPage mp = new MainPage(driver);

        lp.permanentAuthorization();
        RolesPage rp = mp.moveToAdministrationRoles();
        ModalWindowsViewRoles mwvr = rp.ViewUserButtonClick("admin");
        mwvr.roleNameIsDisplayed("admin");
        mwvr.descriptionValueIsEmpty();
        log.info("В поле \"Агенты\" присутствует значение \"CerediraTess\" - Поле отсутствует в данном релизе программы");
        log.info("В поле \"Пользователи\" присутствует значение \"admin\" - Поле отсутствует в данном релизе программы");

    }

    @Test
    @DisplayName("7. Дублирование записи роли, позитивный сценарий (1 способ)")
    public void duplicateRoleEntryFirstWay() {
        LoginPage lp = new LoginPage(driver);
        MainPage mp = new MainPage(driver);

        String adminName = "admin_" + new Random().ints(1, 100).findFirst().getAsInt();
        lp.permanentAuthorization();
        RolesPage rp = mp.moveToAdministrationRoles();
        ModalWindowDuplicateRoles mwdr = rp.duplicateRolesClick("admin");
        mwdr.nameRoleFieldSendKeys(adminName);
        mwdr.saveButtonClick();
        rp.createdRoleIsDisplayed(adminName);

    }

    @Test
    @DisplayName("8. Дублирование записи роли, позитивный сценарий (2 способ)")
    public void duplicateRoleEntrySecondWay() {
        LoginPage lp = new LoginPage(driver);
        MainPage mp = new MainPage(driver);

        String adminName = "admin_" + new Random().ints(101, 200).findFirst().getAsInt();
        lp.permanentAuthorization();
        RolesPage rp = mp.moveToAdministrationRoles();
        ModalWindowDuplicateRoles mwdr = rp.duplicateRolesClick("admin");
        mwdr.nameRoleFieldSendKeys(adminName);
        mwdr.saveAndAddButtonClick();
        mwdr.currentUrlModal("url:7801/admin/Role/duplicate/?id=${id дублируемой роли}", "https?:\\/\\/.*:7801\\/admin\\/Role\\/duplicate\\/\\?id=\\d+");
        mwdr.currentNameRoleIsDisplayed("admin");
        mwdr.listRoleClick();
        rp.createdRoleIsDisplayed(adminName);

    }

    @Test
    @DisplayName("9. Дублирование записи роли, позитивный сценарий (3 способ)")
    public void duplicateRoleEntryThirdWay() {
        LoginPage lp = new LoginPage(driver);
        MainPage mp = new MainPage(driver);

        String adminName = "admin_" + new Random().ints(201, 300).findFirst().getAsInt();
        lp.permanentAuthorization();
        RolesPage rp = mp.moveToAdministrationRoles();
        ModalWindowDuplicateRoles mwdr = rp.duplicateRolesClick("admin");
        mwdr.nameRoleFieldSendKeys(adminName);
        mwdr.saveAndContinueEditingButtonClick();
        mwdr.currentUrlModal("url:7801/admin/Role/duplicate/?id=${id дублируемой роли}", "https?:\\/\\/.*:7801\\/admin\\/Role\\/edit\\/\\?id=\\d+&url=.*");
        mwdr.currentNameRoleIsDisplayed(adminName);
        mwdr.listRoleClick();
        rp.createdRoleIsDisplayed(adminName);

    }

    @Test
    @DisplayName("10. Дублирование записи роли, негативный сценарий")
    public void duplicateRoleEntryNegativeWay() {
        LoginPage lp = new LoginPage(driver);
        MainPage mp = new MainPage(driver);

        lp.permanentAuthorization();
        RolesPage rp = mp.moveToAdministrationRoles();
        ModalWindowDuplicateRoles mwdr = rp.duplicateRolesClick("admin");
        mwdr.nameRoleFieldSendKeys("admin");
        mwdr.saveButtonClick();
        mwdr.errorMessageIsDisplayed();
        mwdr.listRoleClick();
        rp.countOfLinesWithNameAdmin();
    }

    @Test
    @DisplayName("11. Удаление записи роли admin, негативный сценарий")
    //ЭТОТ ТЕСТ В ДАННОМ РЕЛИЗЕ НЕ ЗАПУСКАТЬ, ВСЁ ЛОМАЕТ
    public void DeletingRoleEntryAdminNegativeWay() {
        LoginPage lp = new LoginPage(driver);
        MainPage mp = new MainPage(driver);

        lp.permanentAuthorization();
        RolesPage rp = mp.moveToAdministrationRoles();
        rp.deleteUserRoleButtonClick("admin");
        fail("ЭТОТ ТЕСТ В ДАННОМ РЕЛИЗЕ УДАЛЯЕТ УЧЁТНУЮ ЗАПИСЬ admin");
        rp.confirmDeleteClick();
        log.info("В таблице \"Список\" присутствует строка со значением \"admin\" в столбце \"Название роли\" - не реализовано в данном релизе программы");
        log.info("Под главным меню появилось сообщение \"Ошибка удаления записи: Нельзя удалить роль admin.\" - не реализовано в данном релизе программы");

    }

    @Test
    @DisplayName("12. Удаление записи роли через выбор")
    public void removeCreatedRoleViaSelected() {
        RolesPage rp = new RolesPage(driver);

        String testerName = "tester_" + new Random().ints(501, 600).findFirst().getAsInt();
        rp.createRole(testerName);
        WebElement roleCell = rp.getRoleCell(testerName);
        rp.checkBoxTableClick(testerName);
        rp.withSelectedClick();
        rp.deleteUserButtonWithSelectedClick();
        rp.confirmDeleteClick();
        rp.isRoleNameRemoved(roleCell, testerName);
    }

    @Test
    @DisplayName("13. Удаление записей ролей через выбор Все, негативный сценарий")
    public void removeAllCreatedRolesViaSelectedAllNegativeWay() {
        LoginPage lp = new LoginPage(driver);
        MainPage mp = new MainPage(driver);
        RolesPage rp = new RolesPage(driver);

        String testerName = "tester_" + new Random().ints(601, 700).findFirst().getAsInt();
        String testerName2 = "tester_" + new Random().ints(701, 800).findFirst().getAsInt();
        String testerName3 = "tester_" + new Random().ints(801, 900).findFirst().getAsInt();

        lp.permanentAuthorization();
        mp.moveToAdministrationRoles();
        ModalWindowCreateRoles mwr = rp.createButtonClick();
        mwr.nameRoleFieldSendKeys(testerName);
        mwr.saveButtonClick();
        rp.createdRoleIsDisplayed(testerName);
        rp.createButtonClick();
        mwr.nameRoleFieldSendKeys(testerName2);
        mwr.saveButtonClick();
        rp.createdRoleIsDisplayed(testerName2);
        rp.createButtonClick();
        mwr.nameRoleFieldSendKeys(testerName3);
        mwr.saveButtonClick();
        rp.createdRoleIsDisplayed(testerName3);
        rp.checkBoxAllTableClick();
        rp.withSelectedClick();
        rp.deleteUserButtonWithSelectedClick();
        fail("ЭТОТ ТЕСТ В ДАННОМ РЕЛИЗЕ УДАЛЯЕТ УЧЁТНУЮ ЗАПИСЬ admin");
        rp.confirmDeleteClick();
        log.info("В таблице \"Список\" отсутствуют 3 строки со значениями - \"tester_13_1\", \"tester_13_2\", \"tester_13_3\" и присутствует 1 строка со значением \"admin\", в столбце \"Название роли\"");
        log.info("Под главным меню появилось сообщение \"Ошибка удаления записи: Нельзя удалить роль admin.\"");
    }

    @AfterEach
    public void afterEach() {
        if (driver != null) {
            try {
                log.info("Закрытие браузера");
                driver.close();
                driver.quit();
            } catch (Exception ex) {
                log.error(ex.getMessage());
            }
        }
    }
}
