package pages.Dashboard;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.BasePage;

public class HomePage extends BasePage {
    public HomePage(WebDriver driver) {
        super(driver);
    }

    private final By product= By.xpath("//a[@href='/products']");
    private final By productCard=By.xpath("(//*[@class='productinfo text-center'])[1]");
    private final By cartClick=By.xpath("(//*[@class='product-overlay']//a[@class='btn btn-default add-to-cart'])[1]");

    public void clickproduct() throws InterruptedException {
        Thread.sleep(4000);
        clickOnElement(product);
    }

    public void clickAddCart(){
        waitFor();
        scrollToElement(productCard);
        hover(productCard);
        visibleElement(cartClick);
        waitFor();
    }

}
