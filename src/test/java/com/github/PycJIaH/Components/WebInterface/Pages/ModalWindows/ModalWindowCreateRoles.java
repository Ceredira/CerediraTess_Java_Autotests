package com.github.PycJIaH.Components.WebInterface.Pages.ModalWindows;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ModalWindowCreateRoles extends ModalWindowCreate {

    final Logger log = LoggerFactory.getLogger(ModalWindowCreateRoles.class);

    public ModalWindowCreateRoles(WebDriver driver) {
        super(driver);
    }

    //Локатор поля "Агенты"
    private By agentFieldBy = new By.ByXPath("//*[@id=\"s2id_agents\"]/ul");

    //Локатор поля "Пользователи"
    private By usersFieldBy = new By.ByXPath("//*[@id=\"s2id_users\"]/ul");

    //Локатор поля "Название роли"
    private By nameRoleFieldBy = new By.ByXPath("//*[@id=\"name\"]");

    //Локатор поля "Название роли" при функции "Просмотр записи"
    private By nameRoleFieldFromViewBy = new By.ByXPath("//table//b[text()='Название роли']");

    //Локатор значения поля "Название роли" при функции "Просмотр записи"
    private By roleNameValueFromViewBy = new By.ByXPath("//table//b[text()='Название роли']/../../td[2]");

    //Локатор поля "Описание" при функции "Просмотр записи"
    private By descriptionFieldFromViewBy = new By.ByXPath("//table//b[text()='Описание']");

    //Локатор значения поля "Описание" при функции "Просмотр записи"
    private By descriptionValueFromViewBy = new By.ByXPath("//table//b[text()='Описание']/../../td[2]");

    //Локатор поля "Описание"
    private By descriptionFieldBy = new By.ByXPath("//*[@id=\"description\"]");

    //Локатор выпадающего элемента "CerediraTess"
    private By dropDownElementBy = new By.ByXPath("//*[contains(@id,\"select2-result-label-\") and text()='CerediraTess']");

    //Локатор значения "admin" в поле Пользователи
    private By adminChoiceBy = new By.ByXPath("//*[contains(@id,\"select2-result-label-\") and text()='admin']");

    public void roleNameIsDisplayed(String viewedRoleName) {

        log.info("В поле \"Название роли\" присутствует значение " + viewedRoleName + "");
        assertTrue(driver.findElement(nameRoleFieldFromViewBy).isDisplayed(), "Поле \"Название роли\" отсутствует");
        assertTrue(driver.findElement(roleNameValueFromViewBy).isDisplayed(), "Значение поля \"Название роли\" отсутствует");
        String expectedRoleNameValue = viewedRoleName;
        String actualRoleNameValue = driver.findElement(roleNameValueFromViewBy).getText();
        assertThat("Значение в поле \"Название роли\" не соответствует значению " + viewedRoleName + "", expectedRoleNameValue, is(actualRoleNameValue));
    }

    public void descriptionValueIsEmpty() {
        log.info("В поле \"Описание\" отсутствует значение");
        assertTrue(driver.findElement(descriptionFieldFromViewBy).isDisplayed(), "Поле \"Описание\" отсутствует");
        assertTrue(driver.findElement(descriptionValueFromViewBy).getText().isEmpty(), "В поле \"Описание\" присутствует значение ");
    }

    public void agentFieldClick() {
        log.info("В поле \"Агенты\"...");
        driver.findElement(agentFieldBy).click();
    }

    public void dropDownElementClick() {
        log.info("...выбрать значение \"CerediraTess\"");
        driver.findElement(dropDownElementBy).click();
    }

    public void usersFieldClick() {
        log.info("В поле \"Пользователи\"...");
        driver.findElement(usersFieldBy).click();
    }

    public void adminChoiceClick() {
        log.info("...выбрать значение \"admin\"");
        driver.findElement(adminChoiceBy).click();
    }

    public void nameRoleFieldSendKeys(String testerName) {
        log.info("В поле \"Название роли\" ввести значение " + testerName + "");
        driver.findElement(nameRoleFieldBy).clear();
        driver.findElement(nameRoleFieldBy).sendKeys(testerName);
    }

    public void descriptionFieldSendKeys(String description) {
        log.info("В поле \"Описание\" ввести значение " + description + "");
        driver.findElement(descriptionFieldBy).clear();
        driver.findElement(descriptionFieldBy).sendKeys(description);
    }
}
