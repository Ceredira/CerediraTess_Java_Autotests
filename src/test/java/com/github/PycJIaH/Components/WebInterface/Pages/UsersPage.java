package com.github.PycJIaH.Components.WebInterface.Pages;

import org.hamcrest.Matchers;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.MatcherAssert.assertThat;


public class UsersPage {

    protected WebDriver driver;

    final Logger log = LoggerFactory.getLogger(UsersPage.class);

    public UsersPage(WebDriver driver) {
        this.driver = driver;
    }

    public void countOfLinesWithUsers(String userName) {

        log.info("В списке есть 1 запись, где \"Логин пользователя\" равен " + userName + "");
        String expectedUserName = userName;
        String actualUserName = driver.findElement(new By.ByXPath("//td[@class='col-username' and normalize-space()='" + userName + "']")).getText();
        assertThat("В списке нет пользователя со значением " + userName + "", expectedUserName, Matchers.is(actualUserName));
    }


}
