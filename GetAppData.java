package ru.neoflex.nr.base;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.thucydides.core.util.EnvironmentVariables;


public class GetAppData {

    private static final Logger LOGGER = LoggerFactory.getLogger(GetAppData.class);
    public static final String PATH_JSON_DATA = "src/test/resources/converter/sampledata.json";
    public static final String PATH_DRIVER_DATA = "src/test/resources/IEDriverServer.exe";
    private long time = 0;
    public static WebDriver wd;

    public static WebDriver init() {
        DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
        capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
        capabilities.setCapability("ignoreZoomSetting", true);
        System.setProperty("webdriver.ie.driver", PATH_DRIVER_DATA);
        return wd = new InternetExplorerDriver(capabilities);
    }

    public static void tearDown() {
        wd.quit();
    }

    public Logger getLogger() {
        return LOGGER;
    }

    public void step(int num) {
        long ellapsed = (System.currentTimeMillis() - time) / 1000;
        String mess = String.format("%02d:%02d  -------- %d ---------\n", (int) (ellapsed / 60), ellapsed % 60, num);
        LOGGER.info(mess);
    }

    public void resetTime() {
        time = System.currentTimeMillis();
    }

    protected void initDbConnectionData(EnvironmentVariables environmentVariables) {
        DBUtils.getDbUtilsInstance(environmentVariables.getProperty("nfo.db.url"),
                environmentVariables.getProperty("nfo.db.user"), environmentVariables.getProperty("nfo.db.password"));
        resetTime();
    }

    public JsonNode getApplicationJson(int caseId) throws IOException {
        byte[] jsonData = Files.readAllBytes(Paths.get(PATH_JSON_DATA));

        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode rootNode = objectMapper.readTree(jsonData);
        return rootNode.path("applications").get(caseId - 1);
    }

    public JsonNode getJsonNode(int caseId, String type) throws IOException {
        byte[] jsonData = Files.readAllBytes(Paths.get(PATH_JSON_DATA));

        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode rootNode = objectMapper.readTree(jsonData);
        JsonNode applications = rootNode.path(type);
        Iterator<JsonNode> iter = applications.elements();
        while (iter.hasNext()) {
            JsonNode jsonNode = iter.next();
            JsonNode ids = jsonNode.path("caseIds");
            Iterator<JsonNode> iterId = ids.elements();
            while (iterId.hasNext()) {
                if (caseId == iterId.next().asInt()) {
                    return jsonNode;
                }
            }
        }
        return null;
    }
}

