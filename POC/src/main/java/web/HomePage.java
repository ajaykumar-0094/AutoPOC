package web;

import org.openqa.selenium.*;

public class HomePage {
    WebDriver driver;
    private WaitUtils waitUtils; 
    
    By fromInput = By.xpath("//input[@placeholder='Where from?']");
    By fromCityOption = By.xpath("//p[contains(text(),'Delhi')]");
    By toInput = By.xpath("//input[@placeholder='Where to?']");
    By toCityOption = By.xpath("//p[contains(text(),'Mumbai')]");
    By searchButton = By.xpath("//h4[contains(text(),'Search flights')]");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.waitUtils = new WaitUtils(driver);
    }

    public void searchFlight(String fromCity, String toCity) {
    	driver.findElement(fromInput).sendKeys(fromCity);
    	waitUtils.waitUntilClickable(fromCityOption, 10);
        driver.findElement(fromCityOption).click();

        driver.findElement(toInput).sendKeys(toCity);
        waitUtils.waitUntilClickable(toCityOption, 10);
        driver.findElement(toCityOption).click();

        driver.findElement(searchButton).click();
    }
}
