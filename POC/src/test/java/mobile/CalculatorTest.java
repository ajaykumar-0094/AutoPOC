package mobile;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.*;
import java.net.URI;
import java.time.Duration;
import java.util.logging.Logger;

public class CalculatorTest {

    private AndroidDriver driver;
    private static final Logger logger = Logger.getLogger(CalculatorTest.class.getName());

    @BeforeClass
    public void setup() throws Exception {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
        caps.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        caps.setCapability("appPackage", "com.android.calculator2");
        caps.setCapability("appActivity", "com.android.calculator2.Calculator");
        caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");

        driver = new AndroidDriver(new URI("http://localhost:4723/wd/hub").toURL(), caps);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    private void clickById(String id) {
    	 driver.findElement(AppiumBy.id(id)).click();
    }

    private String getResultText() {
    	return driver.findElement(AppiumBy.id("com.android.calculator2:id/result")).getText();
    }

    private void clearCalculator() {
        try {
            clickById("com.android.calculator2:id/clr");
        } catch (Exception e) {
            clickById("com.android.calculator2:id/del");
        }
    }

    private void performOperation(String[] digits1, String operatorId, String[] digits2) {
        for (String digit : digits1) clickById("com.android.calculator2:id/digit_" + digit);
        clickById(operatorId);
        for (String digit : digits2) clickById("com.android.calculator2:id/digit_" + digit);
        clickById("com.android.calculator2:id/eq");
    }

    @Test(priority = 1)
    public void testAddition() {
        clearCalculator();
        performOperation(new String[]{"1", "2"}, "com.android.calculator2:id/op_add", new String[]{"8"});
        String result = getResultText();
        logger.info("Addition Result: " + result);
        Assert.assertEquals(result, "20");
    }

    @Test(priority = 2)
    public void testSubtraction() {
        clearCalculator();
        performOperation(new String[]{"5", "0"}, "com.android.calculator2:id/op_sub", new String[]{"2", "0"});
        String result = getResultText();
        logger.info("Subtraction Result: " + result);
        Assert.assertEquals(result, "30");
    }

    @Test(priority = 3)
    public void testMultiplication() {
        clearCalculator();
        performOperation(new String[]{"7"}, "com.android.calculator2:id/op_mul", new String[]{"6"});
        String result = getResultText();
        logger.info("Multiplication Result: " + result);
        Assert.assertEquals(result, "42");
    }

    @Test(priority = 4)
    public void testDivision() {
        clearCalculator();
        performOperation(new String[]{"3", "6"}, "com.android.calculator2:id/op_div", new String[]{"6"});
        String result = getResultText();
        logger.info("Division Result: " + result);
        Assert.assertEquals(result, "6");
    }

    @Test(priority = 5)
    public void testDivideByZero() {
        clearCalculator();
        performOperation(new String[]{"9"}, "com.android.calculator2:id/op_div", new String[]{"0"});
        String result = getResultText();
        logger.info("Division by Zero Result: " + result);
        Assert.assertTrue(result.toLowerCase().contains("error") || result.equals("Can't divide by 0"));
    }

    @Test(priority = 6)
    public void testNegativeNumberAddition() {
        clearCalculator();
        clickById("com.android.calculator2:id/op_sub");
        clickById("com.android.calculator2:id/digit_5");
        clickById("com.android.calculator2:id/op_add");
        clickById("com.android.calculator2:id/digit_3");
        clickById("com.android.calculator2:id/eq");
        String result = getResultText();
        logger.info("Negative Addition Result: " + result);
        Assert.assertEquals(result, "-2");
    }
}
