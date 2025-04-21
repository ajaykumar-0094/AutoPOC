package mobile;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;

public class CalculatorPage {
	AndroidDriver driver;

    public CalculatorPage(AndroidDriver driver) {
        this.driver = driver;
    }

    public void clickDigit(String digit) {
    	driver.findElement(AppiumBy.id("com.android.calculator2:id/digit_" + digit)).click();
    }

    public void clickAdd() {
    	driver.findElement(AppiumBy.id("com.android.calculator2:id/op_add")).click();
    }

    public void clickSub() {
    	driver.findElement(AppiumBy.id("com.android.calculator2:id/op_sub")).click();
    }

    public void clickMul() {
    	driver.findElement(AppiumBy.id("com.android.calculator2:id/op_mul")).click();
    }

    public void clickDiv() {
    	driver.findElement(AppiumBy.id("com.android.calculator2:id/op_div")).click();
    }

    public void clickEquals() {
    	driver.findElement(AppiumBy.id("com.android.calculator2:id/eq")).click();
    }

    public void clickClear() {
        try {
        	driver.findElement(AppiumBy.id("com.android.calculator2:id/clr")).click();
        } catch (Exception e) {
        	driver.findElement(AppiumBy.id("com.android.calculator2:id/del")).click();
        }
    }

    public String getResult() {
    	return driver.findElement(AppiumBy.id("com.android.calculator2:id/result")).getText();
    }
}
