package com.github.PycJIaH.Components.WebInterface.Pages.ModalWindows;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

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

}
