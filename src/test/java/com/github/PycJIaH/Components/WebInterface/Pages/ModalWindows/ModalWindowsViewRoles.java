package com.github.PycJIaH.Components.WebInterface.Pages.ModalWindows;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ModalWindowsViewRoles extends ModalWindowView{

    final Logger log = LoggerFactory.getLogger(ModalWindowEditRoles.class);

    public ModalWindowsViewRoles(WebDriver driver) {
        super(driver);
    }

    //Локатор поля "Название роли" при функции "Просмотр записи"
    private By nameRoleFieldFromViewBy = new By.ByXPath("//table//b[text()='Название роли']");

    //Локатор значения поля "Название роли" при функции "Просмотр записи"
    private By roleNameValueFromViewBy = new By.ByXPath("//table//b[text()='Название роли']/../../td[2]");

    //Локатор поля "Описание" при функции "Просмотр записи"
    private By descriptionFieldFromViewBy = new By.ByXPath("//table//b[text()='Описание']");

    //Локатор значения поля "Описание" при функции "Просмотр записи"
    private By descriptionValueFromViewBy = new By.ByXPath("//table//b[text()='Описание']/../../td[2]");

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


}
