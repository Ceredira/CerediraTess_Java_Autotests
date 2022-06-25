package com.github.PycJIaH.Components.WebInterface.Pages.ModalWindows;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ModalWindowEditRoles extends ModalWindowCreate {

    final Logger log = LoggerFactory.getLogger(ModalWindowEditRoles.class);

    public ModalWindowEditRoles(WebDriver driver) {
        super(driver);
    }

    //Локатор поля "Агенты"
    private By agentFieldBy = new By.ByXPath("//*[@id=\"s2id_agents\"]/ul");

    //Локатор поля "Пользователи"
    private By usersFieldBy = new By.ByXPath("//*[@id=\"s2id_users\"]/ul");

    //Локатор поля "Название роли"
    private By nameRoleFieldBy = new By.ByXPath("//*[@id=\"name\"]");

    //Локатор поля "Описание"
    private By descriptionFieldBy = new By.ByXPath("//*[@id=\"description\"]");

    //Локатор выпадающего элемента "CerediraTess"
    private By dropDownElementBy = new By.ByXPath("//*[contains(@id,\"select2-result-label-\") and text()='CerediraTess']");

    //Локатор значения "admin" в поле Пользователи
    private By adminChoiceBy = new By.ByXPath("//*[contains(@id,\"select2-result-label-\") and text()='admin']");


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
