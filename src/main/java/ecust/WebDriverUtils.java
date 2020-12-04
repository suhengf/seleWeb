package ecust;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;
import org.slf4j.LoggerFactory;

import java.util.Set;

public class WebDriverUtils {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(WebDriverUtils.class);

    public static Boolean check(WebDriver driver, By seletor) {
        try {
            driver.findElement(seletor);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean switchToWindowByTitle(WebDriver driver,String windowTitle){
        boolean flag = false;
        try {
            String currentHandle = driver.getWindowHandle();
            Set<String> handles = driver.getWindowHandles();
            for (String s : handles) {
                if (s.equals(currentHandle)) {
                    continue;
                } else {
                    driver.switchTo().window(s);
                    if (driver.getTitle().contains(windowTitle)) {
                        flag = true;
                        System.out.println("Switch to window: "
                                + windowTitle + " successfully!");
                        break;
                    } else {
                        continue;
                    }
                }
            }
        } catch (NoSuchWindowException e) {
            logger.error("Window: " + windowTitle  + " cound not found!", e.fillInStackTrace());
            flag = false;
        }
        return flag;
    }


    public static void closeAlert(WebDriver driver){
        Boolean check = WebDriverUtils.check(driver, By.xpath("/html/body/div[11]/div/a"));
        if (check) {
            driver.findElement(By.xpath("/html/body/div[11]/div/a")).click();
        }
    }

}
