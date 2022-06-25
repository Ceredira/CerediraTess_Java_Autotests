package com.github.PycJIaH.Components.WebInterface.Pages.ModalWindows;

import org.hamcrest.Matchers;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ModalWindowDuplicateRoles extends ModalWindowDuplicate {

    final Logger log = LoggerFactory.getLogger(ModalWindowDuplicateRoles.class);

    public ModalWindowDuplicateRoles(WebDriver driver) {
        super(driver);
    }

    //Локатор поля "Название роли"
    private By nameRoleFieldBy = new By.ByXPath("//*[@id=\"name\"]");

    public void nameRoleFieldSendKeys(String nameRoleFieldValue) {
        log.info("Ввести в поле \"Название роли\" значение " + nameRoleFieldValue + "");
        driver.findElement(nameRoleFieldBy).clear();
        driver.findElement(nameRoleFieldBy).sendKeys(nameRoleFieldValue);
    }

    public void currentUrlModal() {
        log.info("Текущая страница ${url}:7801/admin/Role/duplicate/?id=${id дублируемой роли}");
        assertTrue(driver.getCurrentUrl().matches("https?:\\/\\/.*:7801\\/admin\\/Role\\/duplicate\\/\\?id=\\d+"));
    }

    public void currentNameRoleIsDisplayed(String currentNameRole) {
        log.info("В строке \"Название роли\" осталось прежнее название " + currentNameRole + "");
        String expectedNameRoleField = currentNameRole;
        String actualNameRoleField = driver.findElement(nameRoleFieldBy).getAttribute("value");
        assertThat("Не совпадает значение со значением " + currentNameRole + "", expectedNameRoleField, Matchers.is(actualNameRoleField));
    }


}
