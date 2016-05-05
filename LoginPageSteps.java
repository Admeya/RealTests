package ru.neoflex.nr.base;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.pages.Pages;
import net.thucydides.core.steps.ScenarioSteps;


public class LoginPageSteps extends ScenarioSteps {

    private LoginPage loginPage;

    public LoginPage getLoginPage() {
        return getPages().currentPageAt(LoginPage.class);
    }

    public LoginPageSteps(Pages pages) {
        super(pages);
    }

    @Step
    public void openStartPage(String url) {
        loginPage.getMainPage(url);
    }
}
