package hooks;



import context.TestContext;
import factory.DriverFactory;
import io.cucumber.java.*;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import pages.Dashboard.HomePage;

import utilities.ConfigLoader;
import utilities.CredsLoader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;

import java.util.Map;


public class Hooks {
    private WebDriver driver;
    private final TestContext context;
    private static Map<String, String> testData;

    public Hooks(TestContext context) {

        this.context = context;
    }

    @Before
    public void setUp(Scenario scenario) throws MalformedURLException {
        driver = new DriverFactory().initializeDriver();

        context.driver = driver;
        context.credsLoader = new CredsLoader();
        context.configLoader = new ConfigLoader();
        context.scenario = scenario;

        context.homepage = new HomePage(context.driver);

        context.scenario = scenario;


    }

//     @BeforeAll
//    public static void clearFailedScenariosFile() {
//        try {
//            FileWriter writer = new FileWriter("target/FailedScenarios.txt", false);
//            writer.write(""); // Clears the file
//            writer.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    @After
    public void tearDown(Scenario scenario) throws IOException {

        if (scenario.isFailed()) {
            try (FileWriter fw = new FileWriter("target/FailedScenarios.txt", true);
                    BufferedWriter bw = new BufferedWriter(fw)) {
                bw.write(scenario.getUri() + ":" + scenario.getLine());
                bw.newLine();
                File sourcePath = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                byte[] fileContent = FileUtils.readFileToByteArray(sourcePath);
                scenario.attach(fileContent, "image/png", "image");
            }

        }
        new DriverFactory().closeWebDriver();
        //driver.quit();
    }



}
