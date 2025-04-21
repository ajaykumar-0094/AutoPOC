package web;

import java.util.Map;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;
import io.github.bonigarcia.wdm.WebDriverManager;

public class CleartripTest {

	 WebDriver driver;
	 HomePage homePage;
	 ResultPage resultPage;

    @BeforeClass
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.cleartrip.com/flights");
        homePage = new HomePage(driver);
		resultPage = new ResultPage(driver);
    }

    @Test
    public void searchAndFetchFlightDetails(){
        homePage.searchFlight("Delhi", "Mumbai");
        
        resultPage.sortByDeparture();
		Map<String, String> secondFlight = resultPage.getSecondLowestFlight();
			  if (secondFlight != null) {
			  System.out.println("Second Lowest Priced Flight (Sorted by Early Departure):"); 
			  System.out.println("Airline: " + secondFlight.get("airline"));
			  System.out.println("Price: ₹" + secondFlight.get("price")); 
			  } 
			  else {
			  System.out.println("Less than 2 flights found."); 
			  } 
    }
}


