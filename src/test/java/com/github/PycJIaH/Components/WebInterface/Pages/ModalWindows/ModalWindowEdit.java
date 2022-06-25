package com.github.PycJIaH.Components.WebInterface.Pages.ModalWindows;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ModalWindowEdit {

    protected WebDriver driver;

    final Logger log = LoggerFactory.getLogger(ModalWindowEdit.class);

    public ModalWindowEdit(WebDriver driver) {
        this.driver = driver;
    }

    //Локатор кнопки "Сохранить"
    private By saveButtonBy = new By.ByXPath("//*[@id=\"fa_modal_window\"]/..//input[@value='Сохранить']");

    public void saveButtonClick() {
        log.info("Нажать на кнопку \"Сохранить\"");
        driver.findElement(saveButtonBy).click();
    }


}
