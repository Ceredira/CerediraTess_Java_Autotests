package com.github.PycJIaH.Components.WebInterface.Pages.ModalWindows;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class ModalWindowCreate {

    protected WebDriver driver;

    final Logger log = LoggerFactory.getLogger(ModalWindowCreate.class);

    public ModalWindowCreate(WebDriver driver) {
        this.driver = driver;
    }

    //Локатор кнопки "Сохранить"
    private By saveButtonBy = new By.ByXPath("//*[@id=\"fa_modal_window\"]/..//input[@value='Сохранить']");

    public void saveButtonClick() {
        log.info("Нажать на кнопку \"Сохранить\"");
        driver.findElement(saveButtonBy).click();
    }


}
