package ru.neoflex.nr.base;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.thucydides.core.annotations.findby.FindBy;
import net.thucydides.core.pages.PageObject;
import net.thucydides.core.pages.WebElementFacade;

public class LoginPage extends PageObject {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginPage.class);

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//*[contains(text(),'LogOn')]")
    private WebElementFacade LogOn;

    public void getMainPage(String url) {
        getDriver().get(url);
        LOGGER.info("Перешли на главную страницу");
    }

    public void clickLogOnLink() {
        LogOn.click();
    }
}

