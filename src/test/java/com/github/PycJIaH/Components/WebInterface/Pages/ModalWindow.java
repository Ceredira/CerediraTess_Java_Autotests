package com.github.PycJIaH.Components.WebInterface.Pages;

import com.github.PycJIaH.Components.WebInterface.Auxiliary_functionality.Roles;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class ModalWindow {

    protected WebDriver driver;

    final Logger log = LoggerFactory.getLogger(ModalWindow.class);

    public ModalWindow(WebDriver driver) {
        this.driver = driver;
    }

    //Локатор кнопки "Сохранить"
    private By saveButtonBy = new By.ByXPath("//*[@id=\"fa_modal_window\"]/..//input[@value='Сохранить']");

    public void saveButtonClick() {
        log.info("Нажать на кнопку \"Сохранить\"");
        driver.findElement(saveButtonBy).click();
    }


}
