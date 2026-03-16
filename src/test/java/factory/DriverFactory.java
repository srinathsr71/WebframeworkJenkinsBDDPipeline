package factory;

import constants.Browser;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class DriverFactory {
    public final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public WebDriver getDriver() {
        return driver.get();
    }

    public void setDriver(WebDriver driver2) {
        driver.set(driver2);
    }

    public WebDriver initializeDriver() throws MalformedURLException {
        WebDriver driver;

        String browser = System.getProperty("browser");

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);

        switch (Browser.valueOf(browser.toUpperCase())) {
            case CHROME -> {
                WebDriverManager.chromedriver().setup();

                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);
                chromeOptions.addArguments("--disable-extensions");
                chromeOptions.addArguments("--disable-gpu");
                chromeOptions.addArguments("--no-sandbox");
                chromeOptions.addArguments("--disable-dev-shm-usage");
                chromeOptions.setExperimentalOption("prefs", prefs);
                chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
                if (System.getProperty("browserMode", "normal").equalsIgnoreCase("headless")) {
                    chromeOptions.addArguments("--headless");
                    chromeOptions.addArguments("window-size=1920,1080");
                    chromeOptions.addArguments("--disable-gpu");
                }

                setDriver(new ChromeDriver(chromeOptions));
                getDriver().manage().deleteAllCookies();
            }
            case FIREFOX -> {
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (System.getProperty("browserMode", "normal").equalsIgnoreCase("headless")) {
                    firefoxOptions.addArguments("--headless");
                }

                setDriver(new FirefoxDriver(firefoxOptions));
                getDriver().manage().deleteAllCookies();

            }
            default -> throw new RuntimeException("Invalid Browser: " + browser);
        }

        getDriver().manage().window().maximize();

        return getDriver();
    }

    public void closeWebDriver() {
        if (driver.get() != null) {
            driver.remove();
        }
    }
}
