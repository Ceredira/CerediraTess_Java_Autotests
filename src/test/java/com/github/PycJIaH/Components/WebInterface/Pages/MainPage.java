package com.github.PycJIaH.Components.WebInterface.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MainPage {

    protected WebDriver driver;

    final Logger log = LoggerFactory.getLogger(MainPage.class);

    //Локатор "Выполнение запросов"
    private By performingRequestsBy = new By.ByXPath("//*[@id=\"navbarSupportedContent\"]//a[normalize-space()='Выполнение запросов']");

    //Локатор "Блокировка агентов"
    private By blockAgentsBy = new By.ByXPath("//*[@id=\"navbarSupportedContent\"]//a[normalize-space()='Блокировка агентов']");

    //Локатор кнопки "Обовить скрипты"
    private By updateScriptsButtonBy = new By.ByXPath("//*[@id=\"updateCT\"]");

    //Локатор кнопки "admin - Выход"
    private By adminExitButtonBy = new By.ByXPath("//*[@id=\"navbarSupportedContent\"]//a[normalize-space()='admin - Выход']");

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    public void performingRequestsIsDisplayed() {
        log.info("В главном меню есть пункт \"Выполнение запросов\"");
        assertTrue(driver.findElement(performingRequestsBy).isDisplayed(), "Нет пункта \"Выполнение запросов\"");
    }

    public void blockAgentsIsDisplayed() {
        log.info("В главном меню есть пункт \"Блокировка агентов\"");
        assertTrue(driver.findElement(blockAgentsBy).isDisplayed(), "Нет пункта \"Блокировка агентов\"");
    }

    public void expectedTitleIsDisplayed() {
        String expectedTitle = "CerediraTess - Выполнение запросов";
        log.info("Заголовок страницы равен: " + expectedTitle);
        String actualTitle = driver.getTitle();
        assertThat("Ошибка заголовка", actualTitle, is(expectedTitle));
    }

    public void updateScriptsButtonIsDisplayed() {
        log.info("На странице есть кнопка \"Обновить скрипты\"");
        assertTrue(driver.findElement(updateScriptsButtonBy).isDisplayed(), "Нет кнопки \"Обновить скрипты\"");
    }

    public void adminExitButtonIsDisplayed() {
        log.info("В главном меню есть пункт \"admin - Выход\"");
        assertTrue(driver.findElement(adminExitButtonBy).isDisplayed(), "Нет кнопки \"admin - Выход\"");
    }

    public void clickAdminExitButton() {
        log.info("Нажать на пункт меню \"admin - Выход\"");
        driver.findElement(adminExitButtonBy).click();
    }

}
