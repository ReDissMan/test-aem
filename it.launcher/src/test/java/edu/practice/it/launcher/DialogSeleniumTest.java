package edu.practice.it.launcher;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class DialogSeleniumTest {

    private static final String PORT = "4502";

    private static final String TITLE_TRANSMIT = "TITLE";

    private static final String HEDING_TRANSMIT = "Heading";


    public DialogSeleniumTest(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    private final String url;

    private final String username;

    private final String password;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
            {"http://localhost:4502/", "admin", "admin" }
        });
    }

    @Test
    public  void testDialog() throws InterruptedException {
        final WebDriver webDriver = new FirefoxDriver();
        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        capabilities.setCapability("marionette", true);

        webDriver.get(url + "editor.html/content/we-retail/language-masters/en/about-us.html");

        webDriver.findElement(By.id("password")).sendKeys(username);
        webDriver.findElement(By.id("username")).sendKeys(password);

        webDriver.findElement(By.id("submit-button")).click();

        Thread.sleep(30000);
        webDriver.switchTo().defaultContent();
        webDriver.findElement(By.id("OverlayWrapper")).click();

        Thread.sleep(1000);
        webDriver.switchTo().defaultContent();
        webDriver.findElement(By.xpath("//div[@id='EditableToolbar']/button[1]")).click();

        Thread.sleep(1000);
        webDriver.findElement(By.name("./heading")).clear();
        webDriver.findElement(By.name("./heading")).sendKeys(HEDING_TRANSMIT);

        webDriver.findElement(By.name("./title")).clear();
        webDriver.findElement(By.name("./title")).sendKeys(TITLE_TRANSMIT);

        webDriver.findElement(By.xpath("//coral-dialog-header/div/button[4]")).click();

        Thread.sleep(1000);
        webDriver.findElement(By.xpath("//button[@data-layer='Preview']")).click();

        Thread.sleep(10000);
        webDriver.switchTo().frame(0);
        final String titleReceive =
            webDriver.findElement(By.xpath("html/body/div[1]/div/div/div[2]/div/div/div/strong")).getText();
        final String headingReceive =
            webDriver.findElement(By.xpath("html/body/div[1]/div/div/div[2]/div/div/div/p")).getText();

        Assert.assertEquals(TITLE_TRANSMIT, titleReceive);
        Assert.assertEquals(HEDING_TRANSMIT, headingReceive);

        webDriver.switchTo().defaultContent();
        webDriver.findElement(By.xpath(
            ".//*[@id='Content']/div[1]/coral-actionbar/coral-actionbar-secondary/coral-actionbar-item[1]/button"))
            .click();
        Thread.sleep(5000);

        webDriver.switchTo().defaultContent();
        webDriver.findElement(By.id("OverlayWrapper")).click();

        Thread.sleep(1000);
        webDriver.switchTo().defaultContent();
        webDriver.findElement(By.xpath("//div[@id='EditableToolbar']/button[1]")).click();

        Thread.sleep(1000);
        webDriver.findElement(By.name("./heading")).clear();
        webDriver.findElement(By.name("./title")).clear();
        webDriver.findElement(By.xpath("//coral-dialog-header/div/button[4]")).click();

        Thread.sleep(1000);
        webDriver.close();
    }
}
