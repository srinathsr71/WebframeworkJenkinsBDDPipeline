package context;

import io.cucumber.java.Scenario;
import org.openqa.selenium.WebDriver;
import pages.Dashboard.HomePage;
import utilities.ConfigLoader;
import utilities.CredsLoader;

public class TestContext {
    public WebDriver driver;
    public CredsLoader credsLoader;
    public ConfigLoader configLoader;
    public Scenario scenario;

    public HomePage homepage;


}
