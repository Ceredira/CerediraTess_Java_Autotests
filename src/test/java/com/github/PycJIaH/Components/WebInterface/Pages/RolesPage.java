package com.github.PycJIaH.Components.WebInterface.Pages;

import org.openqa.selenium.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RolesPage {

    protected WebDriver driver;

    final Logger log = LoggerFactory.getLogger(RolesPage.class);

    public RolesPage(WebDriver driver) {
        this.driver = driver;
    }

    //Локатор кнопки "Создать"
    private By createButtonBy = new By.ByXPath("//a[normalize-space()='Создать']");

    public void countOfLinesWithNameAdmin() {
        log.info("В таблице \"Список\" отображается 1 строка со значением \"admin\" в столбце \"Название роли\"");
        //Проверка на кол-во записей в таблице
        List<WebElement> countOfLines = driver.findElements(new By.ByXPath("//td[@class='col-name' and normalize-space()='admin']"));
        int expectedCountList = 1;
        int actualCountList = countOfLines.size();
        assertThat("В списке отображается больше 1 строки со значением \"admin\"", expectedCountList, is(actualCountList));
    }

    public ModalWindowRoles createButtonClick() {
        log.info("Нажать на вкладку \"Создать\"");
        driver.findElement(createButtonBy).click();

        return new ModalWindowRoles(driver);
    }

    public void createdRoleIsDisplayed(String value) {
        log.info("В таблице \"Список\" отображается строка со значением " + value + " в столбце \"Название роли\"");
        assertTrue(driver.findElement(new By.ByXPath("//td[@class='col-name' and normalize-space()='" + value + "']")).isDisplayed(), "Строка со значением " + value + " отсутствует");
    }

    public WebElement getRoleCell(String value) {
        log.debug("В таблице \"Список\" ищется строка со значением " + value + " в столбце \"Название роли\"");
        WebElement createdRoleCell = driver.findElement(new By.ByXPath("//td[@class='col-name' and normalize-space()='" + value + "']"));

        return createdRoleCell;
    }

    public void createRole(String testerName) {
        LoginPage lp = new LoginPage(driver);
        MainPage mp = new MainPage(driver);
        lp.permanentAuthorization();
        mp.moveToAdministrationRoles();
        ModalWindowRoles mwr = createButtonClick();
        mwr.nameRoleFieldSendKeys(testerName);
        mwr.saveButtonClick();
        createdRoleIsDisplayed(testerName);
    }

    public void deleteUserRoleButtonClick(String testerNameDelete) {
        log.info("Нажать на иконку \"Удалить запись\" роли с названием " + testerNameDelete + "");
        driver.findElement(new By.ByXPath("//td[normalize-space()='" + testerNameDelete + "']/..//button[@title='Delete record']")).click();
    }

    public ModalWindowRoles editUserButtonClick(String editableUser) {
        log.info("Нажать на иконку \"Редактировать запись\" роли с названием " + editableUser + "");
        driver.findElement(new By.ByXPath("//td[normalize-space()='" + editableUser + "']/..//a[@title='Редактировать запись']")).click();

        return new ModalWindowRoles(driver);
    }

    public void confirmDeleteClick() {
        log.info("Появилось модальное диалоговое окно с текстом \"Вы уверены что хотите удалить эту запись?\"");
        Alert ConfirmDelete = driver.switchTo().alert();
        log.info("Нажать на кнопку \"ОК\"");
        ConfirmDelete.accept();
    }

    public void isRoleNameRemoved(WebElement roleCell) {
        log.info("В таблице \"Список\" отсутствует строка с удалённым пользователем в столбце \"Название роли\"");
        assertFalse(isElementExists(roleCell), "Пользователь не удален");
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
