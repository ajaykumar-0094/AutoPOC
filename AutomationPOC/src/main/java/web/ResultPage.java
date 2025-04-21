package web;

import org.openqa.selenium.*;
import java.util.*;

public class ResultPage {
    WebDriver driver;
    private WaitUtils waitUtils;

    By departureSortButton = By.xpath("//span[text()='Departure']");
    By flightBlocks = By.cssSelector("div[data-testid='airlineBlock']");
    By airlineName = By.cssSelector("p.fw-500.fs-2.c-neutral-900");
    By flightPrice = By.cssSelector("p.m-0.fs-5.fw-700.c-neutral-900.ta-right.false");

    public ResultPage(WebDriver driver) {
        this.driver = driver;   
        this.waitUtils = new WaitUtils(driver);
    }

    public void sortByDeparture(){
    	waitUtils.waitForPageToLoad(10);
    	waitUtils.waitUntilClickable(departureSortButton, 15);
        driver.findElement(departureSortButton).isDisplayed();
        driver.findElement(departureSortButton).click();
    }

    public Map<String, String> getSecondLowestFlight() {
        List<WebElement> flights = driver.findElements(flightBlocks);
        List<Map<String, String>> flightData = new ArrayList<>();

        for (WebElement flight : flights) {
            try {
                String airline = flight.findElement(airlineName).getText();
                String priceStr = flight.findElement(flightPrice).getText().replaceAll("[^0-9]", "");
                int price = Integer.parseInt(priceStr);

                Map<String, String> data = new HashMap<>();
                data.put("airline", airline);
                data.put("price", String.valueOf(price));
                flightData.add(data);
            } catch (Exception e) {
              
            }
        }

        flightData.sort(Comparator.comparingInt(f -> Integer.parseInt(f.get("price"))));

        return (flightData.size() >= 2) ? flightData.get(1) : null;
    }
}
