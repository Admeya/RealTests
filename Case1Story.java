package ru.neoflex.nr.stories;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

import com.fasterxml.jackson.databind.JsonNode;

import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.ManagedPages;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.pages.Pages;
import net.thucydides.junit.annotations.Concurrent;
import net.thucydides.junit.runners.ThucydidesRunner;
import ru.neoflex.nr.administration.cognos.configuration.newImport.ImportPackageForReportPageSteps;
import ru.neoflex.nr.administration.cognos.fillReportParameters.FillReportParametersPageSteps;
import ru.neoflex.nr.administration.cognos.links.NavigateOnMenuPageSteps;
import ru.neoflex.nr.administration.cognos.runReport.RunSelectedReportPageSteps;
import ru.neoflex.nr.base.CommonPage;
import ru.neoflex.nr.base.GetAppData;
import ru.neoflex.nr.base.LoginPageSteps;

@RunWith(ThucydidesRunner.class)
@Concurrent(threads = "1")
public class Case1Story extends GetAppData {
    
    public static final int CASE_ID = 1;
    public static final String IMPORT_REPORT_JSON = "importReport";
    public static final String RUN_REPORT_JSON = "runReport";
    public static final String PARAM_REPORT_JSON = "paramReport";
    
    private JsonNode application;
    private JsonNode impReport;
    private JsonNode runReport;
    private JsonNode paramReport; // NOPMD

    @Managed(uniqueSession = true)
    private WebDriver webDriver; // NOPMD

    @ManagedPages(defaultUrl = CommonPage.SERVER_IP_AND_PORT)
    private Pages pages;

    @Steps
    private NavigateOnMenuPageSteps navigateOnMenuPageSteps = new NavigateOnMenuPageSteps(pages);
    @Steps
    private ImportPackageForReportPageSteps importPackageForReportPageSteps;
    @Steps
    private RunSelectedReportPageSteps runSelectedReportPageSteps;
    @Steps
    private FillReportParametersPageSteps fillReportParametersPageSteps;
    @Steps
    private LoginPageSteps loginPageSteps;

    @Before
    public void initWebDriver() {
        webDriver = GetAppData.init();
    }

    @Before
    public void setupTestData() throws IOException {
        application = getApplicationJson(CASE_ID);
        impReport = application.get(IMPORT_REPORT_JSON);
        runReport = application.get(RUN_REPORT_JSON);
        paramReport = application.get(PARAM_REPORT_JSON);
    }

    // @Before
    // public void setupDbConnection() {
    // initDbConnectionData(pages.getConfiguration().getEnvironmentVariables());
    // }

    @Test
    public void case1() throws InterruptedException {
        try {
            loginPageSteps.openStartPage(CommonPage.SERVER_IP_AND_PORT);
            navigateOnMenuPageSteps.clickToCognosAdministration();
            navigateOnMenuPageSteps.clickToConfigurationTab();
            navigateOnMenuPageSteps.clickToContentAdministration();
            importPackageForReportPageSteps.configureReport(impReport);
            navigateOnMenuPageSteps.clickToContentAdministration();
            runSelectedReportPageSteps.runReport(runReport);
            fillReportParametersPageSteps.fillParameters102(paramReport);

            GetAppData.tearDown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
