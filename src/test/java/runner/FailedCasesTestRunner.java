package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

import static java.util.Objects.requireNonNull;

@CucumberOptions(features = "@target/FailedScenarios.txt", glue = { "scenarios", "hooks" }, plugin = { "pretty",
        "rerun:@target/FailedScenarios.txt", "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:" })

public class FailedCasesTestRunner extends AbstractTestNGCucumberTests {

    @BeforeClass
    public void setupBeforeClass() {
        requireNonNull("\\src\\test\\resources\\devconfiguration.properties", "credentials file not provided");
    }

    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }

}
