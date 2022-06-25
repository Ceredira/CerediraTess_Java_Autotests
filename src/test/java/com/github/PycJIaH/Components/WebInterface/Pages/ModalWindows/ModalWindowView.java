package com.github.PycJIaH.Components.WebInterface.Pages.ModalWindows;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ModalWindowView {

    protected WebDriver driver;

    final Logger log = LoggerFactory.getLogger(ModalWindowView.class);

    public ModalWindowView(WebDriver driver) {
        this.driver = driver;
    }

    //Локатор кнопки "Фильтровать"
    private By filterFieldBy = new By.ByXPath("//*[@id=\"fa_modal_window\"]/..//*[@id=\"fa_filter\"]");

    public void filterFieldSendKeys(String filterName) {
        log.info("Ввести значение " + filterName + " в поле  \"Фильтровать\"");
        driver.findElement(filterFieldBy).clear();
        driver.findElement(filterFieldBy).sendKeys(filterName);
    }


}
