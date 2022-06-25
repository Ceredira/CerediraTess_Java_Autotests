package com.github.PycJIaH.Components.WebInterface.Pages.ModalWindows;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ModalWindowDuplicate {

    protected WebDriver driver;

    final Logger log = LoggerFactory.getLogger(ModalWindowDuplicate.class);

    public ModalWindowDuplicate(WebDriver driver) {
        this.driver = driver;
    }


    //Локатор кнопки "Сохранить"
    private By saveButtonBy = new By.ByXPath("//input[@value='Сохранить']");

    //Локатор кнопки "Сохранить и добавить новый объект"
    private By saveAndAddButtonBy = new By.ByXPath("//input[@value='Сохранить и добавить новый объект']");

    //Локатор кнопки "Сохранить и добавить новый объект"
    private By saveAndContinueEditingButtonBy = new By.ByXPath("//input[@value='Сохранить и продолжить редактирование']");

    //Локатор вкладки "Список"
    private By listRoleBy = new By.ByXPath("//a[text()='Список']");


    public void saveButtonClick() {
        log.info("Нажать на кнопку \"Сохранить\"");
        driver.findElement(saveButtonBy).click();
    }

    public void saveAndAddButtonClick() {
        log.info("Нажать на кнопку \"Сохранить и добавить новый объект\"");
        driver.findElement(saveAndAddButtonBy).click();
    }

    public void saveAndContinueEditingButtonClick() {
        log.info("Нажать на кнопку \"Сохранить и продолжить редактирование\"");
        driver.findElement(saveAndContinueEditingButtonBy).click();
    }


    public void listRoleClick() {
        log.info("Перейти во вкладку \"Список\"");
        driver.findElement(listRoleBy).click();
    }

}
