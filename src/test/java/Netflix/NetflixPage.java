package Netflix;

import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NetflixPage {
    WebDriver driver;


    public NetflixPage(WebDriver driver){
        this.driver = driver;
    }

    public void sendEmails(String email){
        driver.findElement(By.name("email")).sendKeys(email);
        driver.findElement(By.xpath("//span[contains(text(), 'COMIENZA YA')]")).click();
    }
}
