package scenarios.stepDefinitions;

import context.TestContext;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import pages.Dashboard.HomePage;
import utilities.ConfigLoader;

public class HomeStepDef {
    public TestContext testContext;
    public ConfigLoader configLoader;
    public WebDriver driver;
    public HomePage homepage;
    public Scenario scenario;

    public HomeStepDef(TestContext testContext) {
        this.testContext = testContext;
        this.configLoader = testContext.configLoader;
        this.driver = testContext.driver;
        this.homepage = testContext.homepage;
        this.scenario = testContext.scenario;
    }

    @Given("user click on products")
    public void userClickOnProducts() throws InterruptedException {
        homepage.goTo(configLoader.getProperty("url"));
        homepage.clickproduct();
    }

    @When("user click on add to cart")
    public void userClickOnAddToCart() {
        System.out.println("the product link is click successfully");
        homepage.clickAddCart();
    }

    @Then("User click on cart link")
    public void userClickOnCartLink() {

    }

}
